package flightsystem;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Ein konkreter Flug mit Flugnummer, Ziel, Startzeit, {@link Flugzeug} und Passagierliste.
 *
 * <p>Der Flug verwaltet seine eigene Passagierliste und sorgt selbst dafuer, dass die
 * Kapazitaet des eingesetzten Flugzeugs nicht ueberschritten wird. Passagiere gehoeren
 * dem Flug nicht (Aggregation): Ein {@link Passagier} kann auf mehreren Fluegen gebucht
 * sein.</p>
 *
 * @author Lennis Wirz
 * @version 1.0
 */
public class Flug {
    private final String flugnummer;
    private final String ziel;
    private final LocalTime startZeit;
    private final Flugzeug flugzeug;
    private final List<Passagier> passagiere = new ArrayList<>();

    /**
     * Erzeugt einen Flug ohne Passagiere.
     *
     * @param flugnummer eindeutige Flugnummer, z.&nbsp;B. {@code "LX318"}, nicht {@code null}
     * @param ziel       Zielflughafen oder Zielort, nicht {@code null}
     * @param startZeit  planmaessige Startzeit, nicht {@code null}
     * @param flugzeug   eingesetztes Flugzeug; bestimmt die maximale Passagierzahl,
     *                   nicht {@code null}
     * @throws NullPointerException wenn ein Parameter {@code null} ist
     */
    public Flug(String flugnummer, String ziel, LocalTime startZeit, Flugzeug flugzeug) {
        this.flugnummer = Objects.requireNonNull(flugnummer);
        this.ziel = Objects.requireNonNull(ziel);
        this.startZeit = Objects.requireNonNull(startZeit);
        this.flugzeug = Objects.requireNonNull(flugzeug);
    }

    /**
     * Bucht einen Passagier auf diesen Flug.
     *
     * <p>Doppelbuchungen werden bewusst ignoriert statt als Fehler gemeldet: Wer bereits
     * gebucht ist, bleibt gebucht. Ist das Flugzeug dagegen voll, ist die Buchung nicht
     * erfuellbar und wird mit einer Ausnahme abgelehnt.</p>
     *
     * @param passagier der zu buchende Passagier, nicht {@code null}
     * @throws NullPointerException  wenn {@code passagier} {@code null} ist
     * @throws IllegalStateException wenn alle Sitze des Flugzeugs belegt sind
     */
    public void addPassagier(Passagier passagier) {
        Objects.requireNonNull(passagier);
        if (passagiere.contains(passagier)) {
            return;
        }
        if (passagiere.size() >= flugzeug.getAnzahlSitze()) {
            throw new IllegalStateException("Der Flug " + flugnummer + " ist ausgebucht.");
        }
        passagiere.add(passagier);
    }

    /**
     * Storniert die Buchung eines Passagiers.
     *
     * <p>War der Passagier gar nicht gebucht, bleibt der Aufruf wirkungslos.</p>
     *
     * @param passagier der zu entfernende Passagier
     */
    public void removePassagier(Passagier passagier) {
        passagiere.remove(passagier);
    }

    /**
     * Gibt die gebuchten Passagiere zurueck.
     *
     * @return eine nicht veraenderbare Sicht auf die Passagierliste; Buchungen laufen
     *         ausschliesslich ueber {@link #addPassagier(Passagier)} und
     *         {@link #removePassagier(Passagier)}
     */
    public List<Passagier> getPassagiere() {
        return Collections.unmodifiableList(passagiere);
    }

    /**
     * @return die Anzahl der aktuell gebuchten Passagiere
     */
    public int getAnzahlPassagiere() {
        return passagiere.size();
    }

    /**
     * @return die planmaessige Startzeit des Flugs
     */
    public LocalTime getStartZeit() {
        return startZeit;
    }

    /**
     * Gibt die Passagierliste dieses Flugs auf der Konsole aus.
     */
    public void printPassagierliste() {
        System.out.println("Passagiere für " + flugnummer + ":");
        for (Passagier passagier : passagiere) {
            System.out.println("- " + passagier);
        }
    }

    /**
     * @return Darstellung in der Form
     *         {@code "LX318 nach London um 07:45 mit Airbus A320 (6 Sitze)"}
     */
    @Override
    public String toString() {
        return flugnummer + " nach " + ziel + " um " + startZeit + " mit " + flugzeug;
    }
}
