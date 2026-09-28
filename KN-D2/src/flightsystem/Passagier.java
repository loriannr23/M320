package flightsystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Passagier {
    private final String name;
    private final String passnummer;
    private final List<Flug> fluege = new ArrayList<>();

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

    public List<Flug> getFluege() {
        return Collections.unmodifiableList(fluege);
    }

    void addFlug(Flug flug) {
        if (!fluege.contains(flug)) {
            fluege.add(Objects.requireNonNull(flug));
        }
    }

    void removeFlug(Flug flug) {
        fluege.remove(flug);
    }

    @Override
    public String toString() {
        return name + " (" + passnummer + ")";
    }
}
