package footballd3;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public final class ConsoleView {
    private static final DateTimeFormatter DATE_TIME = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    private final Scanner scanner = new Scanner(System.in);

    public void showWelcome() {
        System.out.println("=== Football Match Tracker ===");
        System.out.println("Spieldaten und Tore aus der OpenLigaDB (Bundesliga).");
    }

    public void showMenu() {
        System.out.println();
        System.out.println("1  Spiele eines Bundesliga-Spieltags");
        System.out.println("2  Spiele eines Teams suchen");
        System.out.println("3  Spieldetails und Tore per Match-ID");
        System.out.println("0  Beenden");
    }

    public String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public void showMatches(List<Match> matches) {
        if (matches.isEmpty()) {
            System.out.println("Keine Spiele gefunden.");
            return;
        }

        System.out.println();
        for (Match match : matches) {
            System.out.printf("#%d | %s | %s %s %s | %s | %s%n",
                    match.getId(),
                    match.getMatchDateTime().format(DATE_TIME),
                    match.getHomeTeam().getName(),
                    match.getScoreText(),
                    match.getAwayTeam().getName(),
                    match.getGroupName(),
                    match.getStatusText());
            if (!match.getGoals().isEmpty()) {
                System.out.println("    Tore: " + match.getGoals().size()
                        + " (Details über Menüpunkt 3)");
            }
        }
    }

    public void showMatchDetails(Match match) {
        System.out.println();
        System.out.println("=== " + match.getLeagueName() + " ===");
        System.out.println("Match-ID: " + match.getId());
        System.out.println("Zeit: " + match.getMatchDateTime().format(DATE_TIME));
        System.out.println("Spieltag: " + match.getGroupName());
        System.out.println(match.getHomeTeam().getName() + "  " + match.getScoreText()
                + "  " + match.getAwayTeam().getName());
        System.out.println("Status: " + match.getStatusText());
        System.out.println("Tore:");

        if (match.getGoals().isEmpty()) {
            System.out.println("  Noch keine Tore erfasst.");
            return;
        }

        for (Goal goal : match.getGoals()) {
            System.out.println("  " + goal.getMinuteText() + "  " + goal.getScorer()
                    + goal.getEventDescription() + "  (" + goal.getScoreText() + ")");
        }
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void showError(String message) {
        System.out.println("Fehler: " + message);
    }

    public void close() {
        scanner.close();
    }
}
