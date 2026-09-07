package ticketsystem;

import java.time.LocalDate;

public class GruppenTicket extends Ticket {
    private final int maxPersonen;
    private final double gruppenRabattProzent;

    public GruppenTicket(String ticketId, String eventName, double basisPreisProPerson,
                         LocalDate eventDatum, int maxPersonen, double gruppenRabattProzent) {
        super(ticketId, eventName, basisPreisProPerson, eventDatum);
        if (maxPersonen < 2) {
            throw new IllegalArgumentException("Ein Gruppenticket benötigt mindestens 2 Personen.");
        }
        if (gruppenRabattProzent < 0 || gruppenRabattProzent > 100) {
            throw new IllegalArgumentException("Der Gruppenrabatt muss zwischen 0 und 100 liegen.");
        }
        this.maxPersonen = maxPersonen;
        this.gruppenRabattProzent = gruppenRabattProzent;
    }

    @Override
    public double berechneEndpreis() {
        double preisOhneRabatt = getBasisPreis() * maxPersonen;
        return preisOhneRabatt * (1 - gruppenRabattProzent / 100.0);
    }

    @Override
    public boolean darfEintreten(int anzahlPersonen) {
        return istGueltig() && anzahlPersonen >= 1 && anzahlPersonen <= maxPersonen;
    }

    @Override
    public String printOut() {
        return super.printOut() + " | maximal " + maxPersonen
                + " Personen | Gruppenrabatt: " + gruppenRabattProzent + " %";
    }
}
