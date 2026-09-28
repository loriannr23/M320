package flightsystem;

import java.util.Objects;

/**
 * Ein Passagier, der auf einem oder mehreren {@link Flug}en gebucht sein kann.
 *
 * <p>Ein Passagier ist unveraenderlich (immutable): Name und Passnummer werden im
 * Konstruktor gesetzt und koennen danach nicht mehr geaendert werden. Dadurch kann ein
 * und dasselbe Passagier-Objekt gefahrlos in mehreren Fluegen verwendet werden.</p>
 *
 * @author Lennis Wirz
 * @version 1.0
 */
public class Passagier {
    private final String name;
    private final String passnummer;

    /**
     * Erzeugt einen neuen Passagier.
     *
     * @param name       vollstaendiger Name des Passagiers, nicht {@code null}
     * @param passnummer Nummer des Reisedokuments, nicht {@code null}
     * @throws NullPointerException wenn ein Parameter {@code null} ist
     */
    public Passagier(String name, String passnummer) {
        this.name = Objects.requireNonNull(name);
        this.passnummer = Objects.requireNonNull(passnummer);
    }

    /**
     * @return der Name des Passagiers
     */
    public String getName() {
        return name;
    }

    /**
     * @return die Passnummer des Passagiers
     */
    public String getPassnummer() {
        return passnummer;
    }

    /**
     * @return Darstellung in der Form {@code "Anna Meier (CH1234567)"}
     */
    @Override
    public String toString() {
        return name + " (" + passnummer + ")";
    }
}
