package flightsystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Sitzplan {
    private final List<Sitz> sitze = new ArrayList<>();

    public Sitzplan(int anzahlReihen, int sitzeProReihe) {
        if (anzahlReihen <= 0 || sitzeProReihe <= 0 || sitzeProReihe > 26) {
            throw new IllegalArgumentException("Die Anzahl Reihen und Sitze muss gültig sein.");
        }

        for (int reihe = 1; reihe <= anzahlReihen; reihe++) {
            for (int nummer = 0; nummer < sitzeProReihe; nummer++) {
                sitze.add(new Sitz(reihe, (char) ('A' + nummer)));
            }
        }
    }

    public int getAnzahlSitze() {
        return sitze.size();
    }

    public List<Sitz> getSitze() {
        return Collections.unmodifiableList(sitze);
    }
}
