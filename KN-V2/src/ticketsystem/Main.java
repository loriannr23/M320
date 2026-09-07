package ticketsystem;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        LocalDate datum = LocalDate.now().plusMonths(1);

        // Alle Objekte werden polymorph über den Basistyp Ticket verarbeitet.
        List<Ticket> tickets = List.of(
                new StandardTicket("STD-001", "Open Air Zürich", 80, datum, 42, true),
                new VipTicket("VIP-001", "Open Air Zürich", 150, datum, true, true),
                new GruppenTicket("GRP-001", "Open Air Zürich", 60, datum, 5, 15)
        );

        for (Ticket ticket : tickets) {
            System.out.println(ticket.printOut());
            System.out.println("Eintritt für 1 Person: " + ticket.darfEintreten(1));
            System.out.println();
        }

        Ticket gruppenTicket = tickets.get(2);
        System.out.println("Gruppe mit 5 Personen: " + gruppenTicket.darfEintreten(5));
        gruppenTicket.entwerten();
        System.out.println("Nach Entwertung: " + gruppenTicket.darfEintreten(5));

        try {
            gruppenTicket.entwerten();
        } catch (IllegalStateException e) {
            System.out.println("Erwarteter Fehler: " + e.getMessage());
        }
    }
}
