package ch.tbz.m320.helpdesk;

import java.util.List;
import java.util.Scanner;

public final class Main {
    private final HelpdeskService helpdesk = new HelpdeskService();
    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        System.out.println("Mini Helpdesk – KN-Q3");
        boolean running = true;
        while (running) {
            printMenu();
            String choice = readLine("Auswahl: ").trim();
            try {
                switch (choice) {
                    case "1":
                        createTicket();
                        break;
                    case "2":
                        startNextTicket();
                        break;
                    case "3":
                        resolveTicket();
                        break;
                    case "4":
                        printTickets(helpdesk.listOpenTicketsFIFO(), "Offene Tickets (FIFO)");
                        break;
                    case "5":
                        printTickets(helpdesk.listOpenTicketsByPriority(), "Offene Tickets (Priorität 1 zuerst)");
                        break;
                    case "6":
                        System.out.println(helpdesk.dailyReport());
                        break;
                    case "0":
                        running = false;
                        break;
                    default:
                        System.out.println("Bitte eine Auswahl von 0 bis 6 eingeben.");
                }
            } catch (IllegalArgumentException | IllegalStateException ex) {
                System.out.println("Fehler: " + ex.getMessage());
            }
        }
        System.out.println("Helpdesk beendet.");
    }

    private void printMenu() {
        System.out.println();
        System.out.println("1  Ticket erstellen");
        System.out.println("2  Nächstes Ticket starten (FIFO)");
        System.out.println("3  Ticket abschliessen");
        System.out.println("4  Offene Tickets nach FIFO anzeigen");
        System.out.println("5  Offene Tickets nach Priorität anzeigen");
        System.out.println("6  Tagesbericht anzeigen");
        System.out.println("0  Beenden");
    }

    private void createTicket() {
        String title = readLine("Titel: ");
        String description = readLine("Beschreibung: ");
        int priority = readInteger("Priorität (1 = dringend, 5 = normal): ");
        Ticket ticket = helpdesk.createTicket(title, description, priority);
        System.out.println("Erstellt: " + ticket);
    }

    private void startNextTicket() {
        String technician = readLine("Techniker/in: ");
        Ticket ticket = helpdesk.startNextTicket(technician);
        if (ticket == null) {
            System.out.println("Es warten keine Tickets.");
        } else {
            System.out.println("Gestartet: " + ticket);
        }
    }

    private void resolveTicket() {
        int id = readInteger("Ticket-ID: ");
        Ticket ticket = helpdesk.resolveTicket(id);
        System.out.println("Abgeschlossen: " + ticket);
    }

    private void printTickets(List<Ticket> tickets, String heading) {
        System.out.println("=== " + heading + " ===");
        if (tickets.isEmpty()) {
            System.out.println("Keine Tickets vorhanden.");
            return;
        }
        for (Ticket ticket : tickets) {
            System.out.println(ticket);
            if (ticket.getStatus() == TicketStatus.OPEN) {
                System.out.println("  SLA-Frist: " + ticket.getSlaDeadline());
            }
        }
    }

    private int readInteger(String prompt) {
        while (true) {
            String value = readLine(prompt).trim();
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException ex) {
                System.out.println("Bitte eine ganze Zahl eingeben.");
            }
        }
    }

    private String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}
