package ch.tbz.m320.helpdesk;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public final class HelpdeskService {
    private final TicketQueue queue = new TicketQueue();
    private final List<Ticket> allTickets = new ArrayList<Ticket>();
    private final List<Ticket> finishedTickets = new ArrayList<Ticket>();

    public Ticket createTicket(String title, String description, int priority) {
        return createTicket(title, description, priority, LocalDateTime.now());
    }

    public Ticket createTicket(String title, String description, int priority, LocalDateTime createdAt) {
        Ticket ticket = new Ticket(title, description, priority, createdAt);
        allTickets.add(ticket);
        queue.enqueue(ticket);
        return ticket;
    }

    /** Starts the oldest waiting ticket, regardless of its priority. Returns null when none is waiting. */
    public Ticket startNextTicket(String technicianName) {
        return startNextTicket(technicianName, LocalDateTime.now());
    }

    public Ticket startNextTicket(String technicianName, LocalDateTime startedAt) {
        if (technicianName == null || technicianName.trim().isEmpty()) {
            throw new IllegalArgumentException("Der Name der Technikerin oder des Technikers darf nicht leer sein.");
        }
        if (queue.isEmpty()) {
            return null;
        }
        if (startedAt == null) {
            throw new IllegalArgumentException("Der Startzeitpunkt darf nicht null sein.");
        }
        if (startedAt.isBefore(queue.peek().getCreatedAt())) {
            throw new IllegalArgumentException("Ein Ticket kann nicht vor seiner Erstellung gestartet werden.");
        }

        Ticket ticket = queue.dequeue();
        ticket.start(technicianName, startedAt);
        return ticket;
    }

    public Ticket resolveTicket(int ticketId) {
        return resolveTicket(ticketId, LocalDateTime.now());
    }

    public Ticket resolveTicket(int ticketId, LocalDateTime resolvedAt) {
        Ticket ticket = findTicket(ticketId);
        if (ticket.getStatus() != TicketStatus.IN_PROGRESS) {
            throw new IllegalStateException("Ticket #" + ticketId + " ist nicht in Bearbeitung.");
        }

        ticket.resolve(resolvedAt);
        finishedTickets.add(ticket);
        return ticket;
    }

    public List<Ticket> listOpenTicketsFIFO() {
        List<Ticket> openTickets = getOpenTickets();
        Collections.sort(openTickets, new Comparator<Ticket>() {
            @Override
            public int compare(Ticket left, Ticket right) {
                int byCreatedAt = left.getCreatedAt().compareTo(right.getCreatedAt());
                return byCreatedAt != 0 ? byCreatedAt : Integer.compare(left.getId(), right.getId());
            }
        });
        return Collections.unmodifiableList(openTickets);
    }

    public List<Ticket> listOpenTicketsByPriority() {
        List<Ticket> openTickets = getOpenTickets();
        Collections.sort(openTickets);
        return Collections.unmodifiableList(openTickets);
    }

    /** Returns tickets started after their deadline or still unstarted after the deadline. */
    public List<Ticket> findBreachedSlaTickets(LocalDateTime now) {
        List<Ticket> breached = new ArrayList<Ticket>();
        for (Ticket ticket : allTickets) {
            LocalDateTime deadline = ticket.getSlaDeadline();
            if (ticket.getStartedAt() == null) {
                if (now.isAfter(deadline)) {
                    breached.add(ticket);
                }
            } else if (ticket.getStartedAt().isAfter(deadline)) {
                breached.add(ticket);
            }
        }
        Collections.sort(breached, new Comparator<Ticket>() {
            @Override
            public int compare(Ticket left, Ticket right) {
                int byDeadline = left.getSlaDeadline().compareTo(right.getSlaDeadline());
                return byDeadline != 0 ? byDeadline : Integer.compare(left.getId(), right.getId());
            }
        });
        return Collections.unmodifiableList(breached);
    }

    public DailyReport dailyReport() {
        return dailyReport(LocalDateTime.now());
    }

    public DailyReport dailyReport(LocalDateTime now) {
        LocalDate reportDate = now.toLocalDate();
        int createdToday = 0;
        int resolvedToday = 0;
        long totalHandlingMillis = 0;

        for (Ticket ticket : allTickets) {
            if (ticket.getCreatedAt().toLocalDate().equals(reportDate)) {
                createdToday++;
            }
        }

        for (Ticket ticket : finishedTickets) {
            if (ticket.getResolvedAt().toLocalDate().equals(reportDate)) {
                resolvedToday++;
                totalHandlingMillis += Duration.between(ticket.getCreatedAt(), ticket.getResolvedAt()).toMillis();
            }
        }

        double averageMinutes = resolvedToday == 0
                ? 0.0
                : totalHandlingMillis / 60000.0 / resolvedToday;
        return new DailyReport(reportDate, createdToday, resolvedToday,
                averageMinutes, findBreachedSlaTickets(now));
    }

    public int getWaitingCount() {
        return queue.size();
    }

    public List<Ticket> getFinishedTickets() {
        return Collections.unmodifiableList(new ArrayList<Ticket>(finishedTickets));
    }

    private List<Ticket> getOpenTickets() {
        List<Ticket> openTickets = new ArrayList<Ticket>();
        for (Ticket ticket : allTickets) {
            if (ticket.getStatus() != TicketStatus.DONE) {
                openTickets.add(ticket);
            }
        }
        return openTickets;
    }

    private Ticket findTicket(int ticketId) {
        for (Ticket ticket : allTickets) {
            if (ticket.getId() == ticketId) {
                return ticket;
            }
        }
        throw new IllegalArgumentException("Ticket #" + ticketId + " wurde nicht gefunden.");
    }
}
