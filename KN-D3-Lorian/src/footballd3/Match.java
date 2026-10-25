package footballd3;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Match {
    private final int id;
    private final int season;
    private final int matchday;
    private final String leagueName;
    private final LocalDateTime matchDateTime;
    private final Team homeTeam;
    private final Team awayTeam;
    private final int scoreTeam1;
    private final int scoreTeam2;
    private final boolean finished;
    private final List<Goal> goals;

    public Match(int id, int season, int matchday, String leagueName, LocalDateTime matchDateTime,
                 Team homeTeam, Team awayTeam, int scoreTeam1, int scoreTeam2,
                 boolean finished, List<Goal> goals) {
        this.id = id;
        this.season = season;
        this.matchday = matchday;
        this.leagueName = leagueName;
        this.matchDateTime = matchDateTime;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.scoreTeam1 = scoreTeam1;
        this.scoreTeam2 = scoreTeam2;
        this.finished = finished;
        this.goals = Collections.unmodifiableList(new ArrayList<Goal>(goals));
    }

    public int getId() {
        return id;
    }

    public int getSeason() {
        return season;
    }

    public int getMatchday() {
        return matchday;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public LocalDateTime getMatchDateTime() {
        return matchDateTime;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public String getScoreText() {
        return scoreTeam1 + ":" + scoreTeam2;
    }

    public boolean isFinished() {
        return finished;
    }

    public List<Goal> getGoals() {
        return goals;
    }

    public String getGroupName() {
        return matchday > 0 ? matchday + ". Spieltag" : "Spieltag unbekannt";
    }

    public String getStatusText() {
        if (finished) {
            return "Beendet";
        }
        if (LocalDateTime.now().isAfter(matchDateTime)) {
            return "Live oder noch nicht final erfasst";
        }
        return "Geplant";
    }
}
