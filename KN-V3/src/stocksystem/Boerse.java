package stocksystem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public abstract class Boerse implements StockExchange {
    private final String name;
    private final Map<String, Aktie> aktien = new LinkedHashMap<>();
    private final Map<String, BigDecimal> kurse = new LinkedHashMap<>();

    protected Boerse(String name) {
        this.name = name;
    }

    protected final void registriereAktie(String symbol, String aktienName, String kurs) {
        Aktie aktie = new Aktie(symbol, aktienName);
        aktien.put(aktie.getSymbol(), aktie);
        kurse.put(aktie.getSymbol(), new BigDecimal(kurs));
    }

    @Override
    public final String getName() {
        return name;
    }

    @Override
    public final List<Aktie> getKotierteAktien() {
        return Collections.unmodifiableList(new ArrayList<>(aktien.values()));
    }

    @Override
    public final Optional<BigDecimal> getKurs(String symbol) {
        if (symbol == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(kurse.get(symbol.trim().toUpperCase(Locale.ROOT)));
    }
}
