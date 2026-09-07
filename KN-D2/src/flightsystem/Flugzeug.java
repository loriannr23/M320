package flightsystem;

import java.util.Objects;

public class Flugzeug {
    private final String modell;
    private final Sitzplan sitzplan;

    public Flugzeug(String modell, int anzahlReihen, int sitzeProReihe) {
        this.modell = Objects.requireNonNull(modell);
        this.sitzplan = new Sitzplan(anzahlReihen, sitzeProReihe);
    }

    public String getModell() {
        return modell;
    }

    public Sitzplan getSitzplan() {
        return sitzplan;
    }

    public int getAnzahlSitze() {
        return sitzplan.getAnzahlSitze();
    }

    @Override
    public String toString() {
        return modell + " (" + getAnzahlSitze() + " Sitze)";
    }
}
