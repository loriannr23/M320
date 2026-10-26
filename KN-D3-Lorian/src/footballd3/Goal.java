package footballd3;

public final class Goal {
    private final int minute;
    private final String scorer;
    private final int homeScore;
    private final int awayScore;

    public Goal(int minute, String scorer, int homeScore, int awayScore) {
        this.minute = minute;
        this.scorer = scorer == null || scorer.trim().isEmpty() ? "Unbekannt" : scorer.trim();
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }

    public String getMinuteText() {
        return minute > 0 ? minute + "'" : "Minute unbekannt";
    }

    public String getScorer() {
        return scorer;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }
}
