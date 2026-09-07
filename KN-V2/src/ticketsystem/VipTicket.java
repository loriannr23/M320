package ticketsystem;

import java.time.LocalDate;

public class VipTicket extends Ticket {
    private static final double BACKSTAGE_ZUSCHLAG = 50.00;
    private static final double CATERING_ZUSCHLAG = 30.00;

    private final boolean backstage;
    private final boolean catering;

    public VipTicket(String ticketId, String eventName, double basisPreis,
                     LocalDate eventDatum, boolean backstage, boolean catering) {
        super(ticketId, eventName, basisPreis, eventDatum);
        this.backstage = backstage;
        this.catering = catering;
    }

    @Override
    public double berechneEndpreis() {
        double preis = getBasisPreis();
        if (backstage) preis += BACKSTAGE_ZUSCHLAG;
        if (catering) preis += CATERING_ZUSCHLAG;
        return preis;
    }

    @Override
    public boolean darfEintreten(int anzahlPersonen) {
        return istGueltig() && anzahlPersonen == 1;
    }

    @Override
    public String printOut() {
        return super.printOut() + " | VIP-Leistungen: "
                + (backstage ? "Backstage " : "")
                + (catering ? "Catering" : "")
                + (!backstage && !catering ? "keine" : "");
    }
}
