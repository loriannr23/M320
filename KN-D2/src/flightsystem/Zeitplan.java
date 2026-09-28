package flightsystem;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Der Flugplan eines Flughafens: eine Sammlung von {@link Flug}en mit Auswertungen darauf.
 *
 * <p>Der Zeitplan kennt die Fluege, speichert aber keine Passagierdaten. Auskuenfte wie
 * die Gesamtzahl der Passagiere holt er sich bei den einzelnen Fluegen (Delegation).</p>
 *
 * @author Lennis Wirz
 * @version 1.0
 */
public class Zeitplan {
    private final String flughafen;
    private final List<Flug> fluege = new ArrayList<>();

    /**
     * Erzeugt einen leeren Zeitplan fuer einen Flughafen.
     *
     * @param flughafen Kuerzel oder Name des Flughafens, z.&nbsp;B. {@code "ZRH"},
     *                  nicht {@code null}
     * @throws NullPointerException wenn {@code flughafen} {@code null} ist
     */
    public Zeitplan(String flughafen) {
        this.flughafen = Objects.requireNonNull(flughafen);
    }

    /**
     * Nimmt einen Flug in den Zeitplan auf.
     *
     * @param flug der aufzunehmende Flug, nicht {@code null}
     * @throws NullPointerException wenn {@code flug} {@code null} ist
     */
    public void addFlug(Flug flug) {
        fluege.add(Objects.requireNonNull(flug));
    }

    /**
     * Sucht alle Fluege, die innerhalb eines Zeitfensters starten.
     *
     * <p>Beide Grenzen gehoeren zum Fenster dazu (inklusiv), damit ein Flug genau um
     * {@code von} oder genau um {@code bis} nicht durch das Raster faellt.</p>
     *
     * @param von fruehester Startzeitpunkt (inklusiv)
     * @param bis spaetester Startzeitpunkt (inklusiv)
     * @return eine neue Liste mit den passenden Fluegen, leer wenn keiner passt
     */
    public List<Flug> findByStartZeit(LocalTime von, LocalTime bis) {
        List<Flug> treffer = new ArrayList<>();
        for (Flug flug : fluege) {
            LocalTime start = flug.getStartZeit();
            if (!start.isBefore(von) && !start.isAfter(bis)) {
                treffer.add(flug);
            }
        }
        return treffer;
    }

    /**
     * Zaehlt die Passagiere ueber alle Fluege des Zeitplans.
     *
     * <p>Ein Passagier, der auf mehreren Fluegen gebucht ist, wird entsprechend mehrfach
     * gezaehlt: Gezaehlt werden Buchungen, nicht Personen.</p>
     *
     * @return die Summe der Passagierzahlen aller Fluege
     */
    public int getTotalPassagiere() {
        int total = 0;
        for (Flug flug : fluege) {
            // Delegation: Der Zeitplan fragt jeden Flug nach seiner Anzahl.
            total += flug.getAnzahlPassagiere();
        }
        return total;
    }

    /**
     * Gibt den vollstaendigen Zeitplan auf der Konsole aus.
     */
    public void printPlan() {
        System.out.println("Zeitplan " + flughafen + ":");
        for (Flug flug : fluege) {
            System.out.println("- " + flug);
        }
    }
}
