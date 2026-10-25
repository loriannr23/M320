package footballd3;

import java.util.List;

public interface FootballDataProvider {
    List<Match> getMatchdayMatches(int season, int matchday) throws FootballApiException;

    List<Match> getMatchesByTeam(int season, String teamName) throws FootballApiException;

    Match getMatch(int matchId) throws FootballApiException;
}
