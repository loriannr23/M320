package stocksystem;

import java.util.Objects;

public final class Aktie {
    private final String symbol;
    private final String name;

    public Aktie(String symbol, String name) {
        this.symbol = Objects.requireNonNull(symbol, "Symbol darf nicht null sein.");
        this.name = Objects.requireNonNull(name, "Name darf nicht null sein.");
        if (symbol.trim().isEmpty() || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Symbol und Name dürfen nicht leer sein.");
        }
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return symbol + " – " + name;
    }
}
