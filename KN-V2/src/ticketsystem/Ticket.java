package ticketsystem;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public abstract class Ticket {
    private static final Set<String> VERGEBENE_IDS = new HashSet<>();

    private final String ticketId;
    private final String eventName;
    private final double basisPreis;
    private final LocalDate eventDatum;
    private boolean verwendet;

    protected Ticket(String ticketId, String eventName, double basisPreis, LocalDate eventDatum) {
        if (ticketId == null || ticketId.isBlank()) {
            throw new IllegalArgumentException("Die Ticket-ID darf nicht leer sein.");
        }
        if (eventName == null || eventName.isBlank()) {
            throw new IllegalArgumentException("Der Eventname darf nicht leer sein.");
        }
        if (basisPreis < 0) {
            throw new IllegalArgumentException("Der Basispreis darf nicht negativ sein.");
        }
        Objects.requireNonNull(eventDatum, "Das Eventdatum darf nicht null sein.");

        synchronized (VERGEBENE_IDS) {
            if (!VERGEBENE_IDS.add(ticketId)) {
                throw new IllegalArgumentException("Die Ticket-ID " + ticketId + " existiert bereits.");
            }
        }

        this.ticketId = ticketId;
        this.eventName = eventName;
        this.basisPreis = basisPreis;
        this.eventDatum = eventDatum;
    }

    public abstract double berechneEndpreis();

    public abstract boolean darfEintreten(int anzahlPersonen);

    public void entwerten() {
        if (verwendet) {
            throw new IllegalStateException("Das Ticket wurde bereits verwendet.");
        }
        verwendet = true;
    }

    public String info() {
        return "%s | ID: %s | Event: %s | Datum: %s | Preis: CHF %.2f | verwendet: %s"
                .formatted(getClass().getSimpleName(), ticketId, eventName, eventDatum,
                        berechneEndpreis(), verwendet ? "ja" : "nein");
    }

    public String printOut() {
        return info();
    }

    protected double getBasisPreis() {
        return basisPreis;
    }

    protected boolean istGueltig() {
        return !verwendet && !LocalDate.now().isAfter(eventDatum);
    }
}
