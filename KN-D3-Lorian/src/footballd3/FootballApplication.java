package footballd3;

import java.util.List;

public final class FootballApplication {
    private final ConsoleView view;
    private final FootballService footballService;

    public FootballApplication() {
        this(new ConsoleView(), new FootballService(new OpenLigaDbClient()));
    }

    FootballApplication(ConsoleView view, FootballService footballService) {
        this.view = view;
        this.footballService = footballService;
    }

    public void run() {
        view.showWelcome();
        boolean running = true;

        while (running) {
            view.showMenu();
            String choice = view.readLine("Auswahl: ").trim();
            try {
                switch (choice) {
                    case "1":
                        showMatchday();
                        break;
                    case "2":
                        showTeamMatches();
                        break;
                    case "3":
                        showMatchDetails();
                        break;
                    case "0":
                        running = false;
                        break;
                    default:
                        view.showMessage("Bitte 0, 1, 2 oder 3 eingeben.");
                }
            } catch (InvalidInputException | FootballApiException exception) {
                view.showError(exception.getMessage());
            }
        }

        view.showMessage("Football Tracker beendet.");
        view.close();
    }

    private void showMatchday() throws InvalidInputException, FootballApiException {
        String season = view.readLine("Saison-Startjahr (Enter = "
                + footballService.getCurrentSeasonStartYear() + "): ");
        String matchday = view.readLine("Spieltag (1-34): ");
        List<Match> matches = footballService.getMatchday(season, matchday);
        view.showMatches(matches);
    }

    private void showTeamMatches() throws InvalidInputException, FootballApiException {
        String team = view.readLine("Teamname oder Teil davon (z. B. Bayern): ");
        String season = view.readLine("Saison-Startjahr (Enter = "
                + footballService.getCurrentSeasonStartYear() + "): ");
        List<Match> matches = footballService.getMatchesForTeam(team, season);
        view.showMatches(matches);
    }

    private void showMatchDetails() throws InvalidInputException, FootballApiException {
        String matchId = view.readLine("Match-ID: ");
        Match match = footballService.getMatchDetails(matchId);
        view.showMatchDetails(match);
    }
}
