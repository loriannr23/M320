package ch.tbz.m320.helpdesk;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class DailyReport {
    private final LocalDate date;
    private final int createdCount;
    private final int resolvedCount;
    private final double averageHandlingMinutes;
    private final List<Ticket> breachedSlaTickets;

    public DailyReport(LocalDate date, int createdCount, int resolvedCount,
                       double averageHandlingMinutes, List<Ticket> breachedSlaTickets) {
        this.date = date;
        this.createdCount = createdCount;
        this.resolvedCount = resolvedCount;
        this.averageHandlingMinutes = averageHandlingMinutes;
        this.breachedSlaTickets = Collections.unmodifiableList(new ArrayList<Ticket>(breachedSlaTickets));
    }

    public LocalDate getDate() {
        return date;
    }

    public int getCreatedCount() {
        return createdCount;
    }

    public int getResolvedCount() {
        return resolvedCount;
    }

    public double getAverageHandlingMinutes() {
        return averageHandlingMinutes;
    }

    public List<Ticket> getBreachedSlaTickets() {
        return breachedSlaTickets;
    }

    @Override
    public String toString() {
        StringBuilder report = new StringBuilder();
        report.append("=== Tagesbericht ").append(date).append(" ===\n");
        report.append("Erstellte Tickets: ").append(createdCount).append('\n');
        report.append("Abgeschlossene Tickets: ").append(resolvedCount).append('\n');
        if (resolvedCount == 0) {
            report.append("Durchschnittliche Bearbeitungszeit: keine abgeschlossenen Tickets\n");
        } else {
            report.append(String.format("Durchschnittliche Bearbeitungszeit: %.1f Minuten\n", averageHandlingMinutes));
        }
        report.append("SLA-Verletzungen: ").append(breachedSlaTickets.size()).append('\n');
        for (Ticket ticket : breachedSlaTickets) {
            report.append(" - #").append(ticket.getId()).append(" ").append(ticket.getTitle()).append('\n');
        }
        if (breachedSlaTickets.isEmpty()) {
            report.append(" - keine\n");
        }
        return report.toString();
    }
}
