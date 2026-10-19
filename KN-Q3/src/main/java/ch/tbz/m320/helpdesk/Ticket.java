package ch.tbz.m320.helpdesk;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public final class Ticket implements Comparable<Ticket> {
    private static final AtomicInteger NEXT_ID = new AtomicInteger(1);
    private static final DateTimeFormatter DISPLAY_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final int id;
    private final String title;
    private final String description;
    private final LocalDateTime createdAt;
    private final int priority;

    private TicketStatus status;
    private String assignedTo;
    private LocalDateTime startedAt;
    private LocalDateTime resolvedAt;

    public Ticket(String title, String description, int priority) {
        this(title, description, priority, LocalDateTime.now());
    }

    public Ticket(String title, String description, int priority, LocalDateTime createdAt) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Der Titel darf nicht leer sein.");
        }
        if (priority < 1 || priority > 5) {
            throw new IllegalArgumentException("Die Priorität muss zwischen 1 und 5 liegen.");
        }

        this.id = NEXT_ID.getAndIncrement();
        this.title = title.trim();
        this.description = Objects.requireNonNull(description, "Die Beschreibung darf nicht null sein.").trim();
        this.priority = priority;
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt darf nicht null sein.");
        this.status = TicketStatus.OPEN;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public int getPriority() {
        return priority;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public LocalDateTime getResolvedAt() {
        return resolvedAt;
    }

    public LocalDateTime getSlaDeadline() {
        int allowedMinutes = priority <= 2 ? 30 : 120;
        return createdAt.plusMinutes(allowedMinutes);
    }

    public void start(String technicianName, LocalDateTime startedAt) {
        if (status != TicketStatus.OPEN) {
            throw new IllegalStateException("Nur offene Tickets können gestartet werden.");
        }
        if (technicianName == null || technicianName.trim().isEmpty()) {
            throw new IllegalArgumentException("Der Name der Technikerin oder des Technikers darf nicht leer sein.");
        }
        Objects.requireNonNull(startedAt, "startedAt darf nicht null sein.");
        if (startedAt.isBefore(createdAt)) {
            throw new IllegalArgumentException("Ein Ticket kann nicht vor seiner Erstellung gestartet werden.");
        }

        this.assignedTo = technicianName.trim();
        this.startedAt = startedAt;
        this.status = TicketStatus.IN_PROGRESS;
    }

    public void resolve(LocalDateTime resolvedAt) {
        if (status != TicketStatus.IN_PROGRESS) {
            throw new IllegalStateException("Nur Tickets in Bearbeitung können abgeschlossen werden.");
        }
        Objects.requireNonNull(resolvedAt, "resolvedAt darf nicht null sein.");
        if (resolvedAt.isBefore(startedAt)) {
            throw new IllegalArgumentException("Ein Ticket kann nicht vor dem Start abgeschlossen werden.");
        }

        this.resolvedAt = resolvedAt;
        this.status = TicketStatus.DONE;
    }

    @Override
    public int compareTo(Ticket other) {
        int byPriority = Integer.compare(priority, other.priority);
        if (byPriority != 0) {
            return byPriority;
        }

        int byCreationTime = createdAt.compareTo(other.createdAt);
        return byCreationTime != 0 ? byCreationTime : Integer.compare(id, other.id);
    }

    @Override
    public String toString() {
        String technician = assignedTo == null ? "-" : assignedTo;
        return String.format("#%d | P%d | %-11s | %s | erstellt %s | zuständig: %s",
                id, priority, status, title, createdAt.format(DISPLAY_TIME), technician);
    }
}
