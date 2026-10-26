package footballd3;

import java.util.List;
import java.util.Scanner;

public final class ConsoleView {
    private final Scanner scanner = new Scanner(System.in);

    public void showWelcome() {
        System.out.println("=== Bundesliga Football Tracker ===");
        System.out.println("Spiele und Tore aus der OpenLigaDB");
    }

    public String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public void showMatches(List<Match> matches) {
        if (matches.isEmpty()) {
            System.out.println("Für diesen Spieltag wurden keine Spiele gefunden.");
            return;
        }

        for (Match match : matches) {
            System.out.printf("%s %d:%d %s%n",
                    match.getHomeTeam(), match.getHomeScore(),
                    match.getAwayScore(), match.getAwayTeam());

            if (match.getGoals().isEmpty()) {
                System.out.println("  Noch keine Tore erfasst.");
            } else {
                for (Goal goal : match.getGoals()) {
                    System.out.printf("  %s - %s%n",
                            goal.getMinuteText(), goal.getScorer());
                }
            }
        }
    }

    public void showError(String message) {
        System.out.println("Fehler: " + message);
    }

    public void close() {
        scanner.close();
    }
}
