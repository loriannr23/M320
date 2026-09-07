package flightsystem;

import java.util.Objects;

public class Passagier {
    private final String name;
    private final String passnummer;

    public Passagier(String name, String passnummer) {
        this.name = Objects.requireNonNull(name);
        this.passnummer = Objects.requireNonNull(passnummer);
    }

    public String getName() {
        return name;
    }

    public String getPassnummer() {
        return passnummer;
    }

    @Override
    public String toString() {
        return name + " (" + passnummer + ")";
    }
}
