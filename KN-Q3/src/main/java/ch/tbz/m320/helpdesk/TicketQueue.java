package ch.tbz.m320.helpdesk;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

/** A FIFO queue used for ticket processing. Priority never changes its order. */
public final class TicketQueue {
    private final Queue<Ticket> tickets = new LinkedList<Ticket>();

    public void enqueue(Ticket ticket) {
        if (ticket == null) {
            throw new IllegalArgumentException("Ein Ticket darf nicht null sein.");
        }
        if (ticket.getStatus() != TicketStatus.OPEN) {
            throw new IllegalArgumentException("In die Warteschlange dürfen nur offene Tickets.");
        }
        tickets.add(ticket);
    }

    public Ticket dequeue() {
        if (tickets.isEmpty()) {
            throw new NoSuchElementException("Die Ticket-Warteschlange ist leer.");
        }
        return tickets.remove();
    }

    public Ticket peek() {
        if (tickets.isEmpty()) {
            throw new NoSuchElementException("Die Ticket-Warteschlange ist leer.");
        }
        return tickets.element();
    }

    public int size() {
        return tickets.size();
    }

    public boolean isEmpty() {
        return tickets.isEmpty();
    }
}
