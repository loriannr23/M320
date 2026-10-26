package footballd3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Match {
    private final String homeTeam;
    private final String awayTeam;
    private final int homeScore;
    private final int awayScore;
    private final List<Goal> goals;

    public Match(String homeTeam, String awayTeam, int homeScore, int awayScore,
                 List<Goal> goals) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.goals = Collections.unmodifiableList(new ArrayList<Goal>(goals));
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public List<Goal> getGoals() {
        return goals;
    }
}
