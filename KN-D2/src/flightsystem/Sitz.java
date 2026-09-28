package flightsystem;

/**
 * Ein einzelner Sitzplatz in einem {@link Flugzeug}, z.&nbsp;B. {@code 2C}.
 *
 * <p>Ein Sitz besteht aus einer Reihennummer und einem Platzbuchstaben und wird
 * ausschliesslich vom {@link Sitzplan} erzeugt.</p>
 *
 * @author Lennis Wirz
 * @version 1.0
 */
public class Sitz {
    private final int reihe;
    private final char platz;

    /**
     * Erzeugt einen Sitz.
     *
     * @param reihe Reihennummer, beginnend bei 1
     * @param platz Platzbuchstabe innerhalb der Reihe, beginnend bei {@code 'A'}
     */
    public Sitz(int reihe, char platz) {
        this.reihe = reihe;
        this.platz = platz;
    }

    /**
     * @return die Reihennummer des Sitzes
     */
    public int getReihe() {
        return reihe;
    }

    /**
     * @return der Platzbuchstabe innerhalb der Reihe
     */
    public char getPlatz() {
        return platz;
    }

    /**
     * @return die uebliche Sitzbezeichnung aus Reihe und Platz, z.&nbsp;B. {@code "2C"}
     */
    @Override
    public String toString() {
        return Integer.toString(reihe) + platz;
    }
}
