package flightsystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Der Sitzplan eines {@link Flugzeug}s: alle {@link Sitz}e in fester Reihenfolge.
 *
 * <p>Der Sitzplan erzeugt seine Sitze beim Anlegen selbst und bleibt danach unveraendert.
 * Er ist damit die einzige Stelle, die weiss, wie aus Reihen und Plaetzen pro Reihe
 * konkrete Sitzbezeichnungen entstehen.</p>
 *
 * @author Lennis Wirz
 * @version 1.0
 */
public class Sitzplan {
    private final List<Sitz> sitze = new ArrayList<>();

    /**
     * Erzeugt einen rechteckigen Sitzplan mit {@code anzahlReihen * sitzeProReihe} Sitzen.
     *
     * <p>Die Plaetze einer Reihe werden ab {@code 'A'} durchbuchstabiert, deshalb sind
     * hoechstens 26 Sitze pro Reihe moeglich.</p>
     *
     * @param anzahlReihen   Anzahl Sitzreihen, muss groesser als 0 sein
     * @param sitzeProReihe  Anzahl Sitze pro Reihe, muss zwischen 1 und 26 liegen
     * @throws IllegalArgumentException wenn eine der beiden Angaben ausserhalb des
     *                                  gueltigen Bereichs liegt
     */
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

    /**
     * @return die Gesamtzahl der Sitze und damit die Kapazitaet des Flugzeugs
     */
    public int getAnzahlSitze() {
        return sitze.size();
    }

    /**
     * Gibt alle Sitze zurueck.
     *
     * @return eine nicht veraenderbare Sicht auf die Sitzliste; Aenderungsversuche
     *         fuehren zu einer {@link UnsupportedOperationException}
     */
    public List<Sitz> getSitze() {
        return Collections.unmodifiableList(sitze);
    }
}
