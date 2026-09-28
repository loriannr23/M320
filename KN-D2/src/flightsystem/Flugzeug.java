package flightsystem;

import java.util.Objects;

/**
 * Ein Flugzeug eines bestimmten Modells mit dem zugehoerigen {@link Sitzplan}.
 *
 * <p>Das Flugzeug besitzt seinen Sitzplan (Komposition): Der Sitzplan wird im Konstruktor
 * erzeugt und lebt genau so lange wie das Flugzeug. Ein {@link Flug} fragt ueber das
 * Flugzeug nach der verfuegbaren Kapazitaet.</p>
 *
 * @author Lennis Wirz
 * @version 1.0
 */
public class Flugzeug {
    private final String modell;
    private final Sitzplan sitzplan;

    /**
     * Erzeugt ein Flugzeug und legt dazu passend den Sitzplan an.
     *
     * @param modell        Modellbezeichnung, z.&nbsp;B. {@code "Airbus A320"}, nicht {@code null}
     * @param anzahlReihen  Anzahl Sitzreihen, muss groesser als 0 sein
     * @param sitzeProReihe Anzahl Sitze pro Reihe, muss zwischen 1 und 26 liegen
     * @throws NullPointerException     wenn {@code modell} {@code null} ist
     * @throws IllegalArgumentException wenn die Sitzplan-Angaben ungueltig sind
     */
    public Flugzeug(String modell, int anzahlReihen, int sitzeProReihe) {
        this.modell = Objects.requireNonNull(modell);
        this.sitzplan = new Sitzplan(anzahlReihen, sitzeProReihe);
    }

    /**
     * @return die Modellbezeichnung des Flugzeugs
     */
    public String getModell() {
        return modell;
    }

    /**
     * @return der Sitzplan dieses Flugzeugs
     */
    public Sitzplan getSitzplan() {
        return sitzplan;
    }

    /**
     * Liefert die Kapazitaet des Flugzeugs.
     *
     * <p>Die Frage wird an den {@link Sitzplan} delegiert, damit das Flugzeug die
     * Sitzanzahl nicht doppelt speichern muss.</p>
     *
     * @return die Anzahl der vorhandenen Sitze
     */
    public int getAnzahlSitze() {
        return sitzplan.getAnzahlSitze();
    }

    /**
     * @return Darstellung in der Form {@code "Airbus A320 (6 Sitze)"}
     */
    @Override
    public String toString() {
        return modell + " (" + getAnzahlSitze() + " Sitze)";
    }
}
