package stocksystem;

import java.util.Objects;

public final class Position {
    private final Aktie aktie;
    private final int anzahl;

    public Position(Aktie aktie, int anzahl) {
        this.aktie = Objects.requireNonNull(aktie, "Aktie darf nicht null sein.");
        if (anzahl <= 0) {
            throw new IllegalArgumentException("Die Anzahl muss grösser als 0 sein.");
        }
        this.anzahl = anzahl;
    }

    public Aktie getAktie() {
        return aktie;
    }

    public int getAnzahl() {
        return anzahl;
    }
}
