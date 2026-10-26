package footballd3;

import java.io.IOException;
import java.util.List;

public final class FootballApplication {
    private final ConsoleView view = new ConsoleView();
    private final FootballService service = new FootballService(new OpenLigaDbClient());

    public void run() {
        view.showWelcome();
        try {
            String matchday = view.readLine("Spieltag (1-34): ");
            List<Match> matches = service.getMatchday(matchday);
            view.showMatches(matches);
        } catch (InvalidInputException | IOException exception) {
            view.showError(exception.getMessage());
        } finally {
            view.close();
        }
    }
}
