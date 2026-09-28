package flightsystem;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Flug {
    private final String flugnummer;
    private final String ziel;
    private final LocalTime startZeit;
    private final Flugzeug flugzeug;
    private final List<Passagier> passagiere = new ArrayList<>();

    public Flug(String flugnummer, String ziel, LocalTime startZeit, Flugzeug flugzeug) {
        this.flugnummer = Objects.requireNonNull(flugnummer);
        this.ziel = Objects.requireNonNull(ziel);
        this.startZeit = Objects.requireNonNull(startZeit);
        this.flugzeug = Objects.requireNonNull(flugzeug);
    }

    public void addPassagier(Passagier passagier) {
        Objects.requireNonNull(passagier);
        if (passagiere.contains(passagier)) {
            return;
        }
        if (passagiere.size() >= flugzeug.getAnzahlSitze()) {
            throw new IllegalStateException("Der Flug " + flugnummer + " ist ausgebucht.");
        }
        passagiere.add(passagier);
        passagier.addFlug(this);
    }

    public void removePassagier(Passagier passagier) {
        if (passagiere.remove(passagier)) {
            passagier.removeFlug(this);
        }
    }

    public List<Passagier> getPassagiere() {
        return Collections.unmodifiableList(passagiere);
    }

    public int getAnzahlPassagiere() {
        return passagiere.size();
    }

    public LocalTime getStartZeit() {
        return startZeit;
    }

    public void printPassagierliste() {
        System.out.println("Passagiere für " + flugnummer + ":");
        for (Passagier passagier : passagiere) {
            System.out.println("- " + passagier);
        }
    }

    @Override
    public String toString() {
        return flugnummer + " nach " + ziel + " um " + startZeit + " mit " + flugzeug;
    }
}
