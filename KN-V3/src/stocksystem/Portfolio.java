package stocksystem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class Portfolio {
    private final List<Position> positionen;

    public Portfolio(List<Position> positionen) {
        Objects.requireNonNull(positionen, "Positionen dürfen nicht null sein.");
        this.positionen = Collections.unmodifiableList(new ArrayList<>(positionen));
    }

    public List<Position> getPositionen() {
        return positionen;
    }

    public BigDecimal berechneWert(StockExchange boerse) {
        Objects.requireNonNull(boerse, "Börse darf nicht null sein.");
        BigDecimal total = BigDecimal.ZERO;
        for (Position position : positionen) {
            BigDecimal kurs = boerse.getKurs(position.getAktie().getSymbol()).orElse(null);
            if (kurs != null) {
                total = total.add(kurs.multiply(BigDecimal.valueOf(position.getAnzahl())));
            }
        }
        return total;
    }

    public List<Position> getNichtKotiertePositionen(StockExchange boerse) {
        Objects.requireNonNull(boerse, "Börse darf nicht null sein.");
        List<Position> nichtKotiert = new ArrayList<>();
        for (Position position : positionen) {
            if (!boerse.getKurs(position.getAktie().getSymbol()).isPresent()) {
                nichtKotiert.add(position);
            }
        }
        return Collections.unmodifiableList(nichtKotiert);
    }
}
