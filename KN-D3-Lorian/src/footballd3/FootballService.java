package footballd3;

import java.time.LocalDate;
import java.util.List;

public final class FootballService {
    private final FootballDataProvider dataProvider;

    public FootballService(FootballDataProvider dataProvider) {
        if (dataProvider == null) {
            throw new IllegalArgumentException("Ein FootballDataProvider ist erforderlich.");
        }
        this.dataProvider = dataProvider;
    }

    public int getCurrentSeasonStartYear() {
        LocalDate today = LocalDate.now();
        return today.getMonthValue() >= 7 ? today.getYear() : today.getYear() - 1;
    }

    public List<Match> getMatchday(String seasonText, String matchdayText)
            throws InvalidInputException, FootballApiException {
        int season = InputValidator.parseSeason(seasonText, getCurrentSeasonStartYear());
        int matchday = InputValidator.parseMatchday(matchdayText);
        return dataProvider.getMatchdayMatches(season, matchday);
    }

    public List<Match> getMatchesForTeam(String teamText, String seasonText)
            throws InvalidInputException, FootballApiException {
        String teamName = InputValidator.validateTeamName(teamText);
        int season = InputValidator.parseSeason(seasonText, getCurrentSeasonStartYear());
        return dataProvider.getMatchesByTeam(season, teamName);
    }

    public Match getMatchDetails(String matchIdText) throws InvalidInputException, FootballApiException {
        int matchId = InputValidator.parseMatchId(matchIdText);
        return dataProvider.getMatch(matchId);
    }
}
