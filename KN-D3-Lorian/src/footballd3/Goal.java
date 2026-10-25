package footballd3;

public final class Goal {
    private final int minute;
    private final String scorer;
    private final int scoreTeam1;
    private final int scoreTeam2;
    private final boolean penalty;
    private final boolean ownGoal;
    private final boolean overtime;

    public Goal(int minute, String scorer, int scoreTeam1, int scoreTeam2,
                boolean penalty, boolean ownGoal, boolean overtime) {
        this.minute = minute;
        this.scorer = scorer == null || scorer.trim().isEmpty() ? "Unbekannt" : scorer;
        this.scoreTeam1 = scoreTeam1;
        this.scoreTeam2 = scoreTeam2;
        this.penalty = penalty;
        this.ownGoal = ownGoal;
        this.overtime = overtime;
    }

    public String getMinuteText() {
        return minute <= 0 ? "Minute ?'" : minute + "'";
    }

    public int getMinute() {
        return minute;
    }

    public String getScorer() {
        return scorer;
    }

    public String getScoreText() {
        return scoreTeam1 + ":" + scoreTeam2;
    }

    public int getScoreTeam1() {
        return scoreTeam1;
    }

    public int getScoreTeam2() {
        return scoreTeam2;
    }

    public String getEventDescription() {
        StringBuilder description = new StringBuilder();
        if (penalty) {
            description.append(" [Elfmeter]");
        }
        if (ownGoal) {
            description.append(" [Eigentor]");
        }
        if (overtime) {
            description.append(" [Nachspielzeit]");
        }
        return description.toString();
    }
}
