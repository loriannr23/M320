package flightsystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Ein Passagier, der auf einem oder mehreren {@link Flug}en gebucht sein kann.
 *
 * <p>Name und Passnummer stehen nach dem Konstruktor fest. Veraenderlich ist nur die
 * Liste der gebuchten Fluege – sie bildet die Gegenrichtung der Beziehung
 * {@code Flug &harr; Passagier} ab.</p>
 *
 * <p>Gepflegt wird diese Liste ausschliesslich vom {@link Flug} ueber die
 * paketprivaten Methoden {@link #addFlug(Flug)} und {@link #removeFlug(Flug)}. Von
 * aussen wird immer der Flug gebucht oder storniert, damit beide Seiten der Beziehung
 * nicht auseinanderlaufen koennen.</p>
 *
 * @author Lennis Wirz
 * @version 1.1
 */
public class Passagier {
    private final String name;
    private final String passnummer;
    private final List<Flug> fluege = new ArrayList<>();

    /**
     * Erzeugt einen neuen Passagier ohne Buchungen.
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
     * Gibt die Fluege zurueck, auf denen dieser Passagier gebucht ist.
     *
     * @return eine nicht veraenderbare Sicht auf die Flugliste; gebucht und storniert
     *         wird ueber {@link Flug#addPassagier(Passagier)} und
     *         {@link Flug#removePassagier(Passagier)}
     */
    public List<Flug> getFluege() {
        return Collections.unmodifiableList(fluege);
    }

    /**
     * Vermerkt eine Buchung auf diesem Passagier.
     *
     * <p>Paketprivat, weil nur der {@link Flug} diese Gegenrichtung setzen darf.
     * Ein bereits vermerkter Flug wird nicht doppelt eingetragen.</p>
     *
     * @param flug der Flug, auf dem der Passagier gebucht wurde, nicht {@code null}
     * @throws NullPointerException wenn {@code flug} {@code null} ist
     */
    void addFlug(Flug flug) {
        if (!fluege.contains(flug)) {
            fluege.add(Objects.requireNonNull(flug));
        }
    }

    /**
     * Entfernt eine Buchung von diesem Passagier.
     *
     * <p>Paketprivat, weil nur der {@link Flug} diese Gegenrichtung loesen darf. War der
     * Flug nicht vermerkt, bleibt der Aufruf wirkungslos.</p>
     *
     * @param flug der stornierte Flug
     */
    void removeFlug(Flug flug) {
        fluege.remove(flug);
    }

    /**
     * @return Darstellung in der Form {@code "Anna Meier (CH1234567)"}
     */
    @Override
    public String toString() {
        return name + " (" + passnummer + ")";
    }
}
