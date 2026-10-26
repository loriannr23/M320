package footballd3;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public final class FootballService {
    private final OpenLigaDbClient dataProvider;

    public FootballService(OpenLigaDbClient dataProvider) {
        this.dataProvider = dataProvider;
    }

    public List<Match> getMatchday(String matchdayText)
            throws InvalidInputException, IOException {
        int matchday;
        try {
            matchday = Integer.parseInt(matchdayText.trim());
        } catch (NumberFormatException exception) {
            throw new InvalidInputException("Der Spieltag muss eine Zahl von 1 bis 34 sein.");
        }

        if (matchday < 1 || matchday > 34) {
            throw new InvalidInputException("Der Spieltag muss zwischen 1 und 34 liegen.");
        }

        LocalDate today = LocalDate.now();
        int season = today.getMonthValue() >= 7 ? today.getYear() : today.getYear() - 1;

        // Delegation: Die API-Abfrage wird an den OpenLigaDB-Client weitergegeben.
        return dataProvider.getMatchdayMatches(season, matchday);
    }
}
