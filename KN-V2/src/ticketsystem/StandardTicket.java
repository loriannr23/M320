package ticketsystem;

import java.time.LocalDate;

public class StandardTicket extends Ticket {
    private static final double RABATT = 0.10; // Annahme: 10 %

    private final int sitzplatz;
    private final boolean rabattBerechtigt;

    public StandardTicket(String ticketId, String eventName, double basisPreis,
                          LocalDate eventDatum, int sitzplatz, boolean rabattBerechtigt) {
        super(ticketId, eventName, basisPreis, eventDatum);
        if (sitzplatz <= 0) {
            throw new IllegalArgumentException("Der Sitzplatz muss positiv sein.");
        }
        this.sitzplatz = sitzplatz;
        this.rabattBerechtigt = rabattBerechtigt;
    }

    @Override
    public double berechneEndpreis() {
        return rabattBerechtigt ? getBasisPreis() * (1 - RABATT) : getBasisPreis();
    }

    @Override
    public boolean darfEintreten(int anzahlPersonen) {
        return istGueltig() && anzahlPersonen == 1;
    }

    @Override
    public String printOut() {
        return super.printOut() + " | Sitzplatz: " + sitzplatz
                + " | Rabatt: " + (rabattBerechtigt ? "10 %" : "kein Rabatt");
    }
}
