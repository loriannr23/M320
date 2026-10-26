package footballd3;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class OpenLigaDbClient {
    private static final String BASE_URL = "https://api.openligadb.de";
    private final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(8))
            .build();

    public List<Match> getMatchdayMatches(int season, int matchday) throws IOException {
        String url = BASE_URL + "/getmatchdata/bl1/" + season + "/" + matchday;
        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .timeout(Duration.ofSeconds(15))
                .header("Accept", "application/json")
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request,
                    HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            if (response.statusCode() == 404) {
                throw new IOException("Für diesen Spieltag wurden keine Daten gefunden.");
            }
            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                throw new IOException("Die API antwortete mit HTTP " + response.statusCode() + ".");
            }
            return parseMatches(response.body());
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new IOException("Die API-Anfrage wurde unterbrochen.", exception);
        }
    }

    private List<Match> parseMatches(String json) throws IOException {
        Object response;
        try {
            response = JsonParser.parse(json);
        } catch (RuntimeException exception) {
            throw new IOException("Die API lieferte keine gültigen JSON-Daten.", exception);
        }

        if (!(response instanceof List<?>)) {
            throw new IOException("Die API-Antwort hat ein unerwartetes Format.");
        }

        List<Match> matches = new ArrayList<Match>();
        try {
            for (Object item : (List<?>) response) {
                matches.add(toMatch(asObject(item)));
            }
        } catch (RuntimeException exception) {
            throw new IOException("Die Spieldaten haben ein unerwartetes Format.", exception);
        }
        return matches;
    }

    private Match toMatch(Map<?, ?> data) {
        String homeTeam = teamName(data.get("team1"));
        String awayTeam = teamName(data.get("team2"));
        List<Goal> goals = readGoals(data.get("goals"));
        boolean finished = Boolean.TRUE.equals(data.get("matchIsFinished"));
        int[] score = readScore(data.get("matchResults"), goals, finished);
        return new Match(homeTeam, awayTeam, score[0], score[1], goals);
    }

    private String teamName(Object value) {
        if (!(value instanceof Map<?, ?>)) {
            return "Unbekannt";
        }
        Object name = ((Map<?, ?>) value).get("teamName");
        return name instanceof String ? (String) name : "Unbekannt";
    }

    private List<Goal> readGoals(Object value) {
        if (value == null) {
            return Collections.emptyList();
        }
        if (!(value instanceof List<?>)) {
            throw new IllegalArgumentException("Tore müssen als Liste vorliegen.");
        }

        List<Goal> goals = new ArrayList<Goal>();
        for (Object item : (List<?>) value) {
            Map<?, ?> data = asObject(item);
            Object scorer = data.get("goalGetterName");
            goals.add(new Goal(number(data.get("matchMinute")),
                    scorer instanceof String ? (String) scorer : "Unbekannt",
                    number(data.get("scoreTeam1")), number(data.get("scoreTeam2"))));
        }
        return goals;
    }

    private int[] readScore(Object value, List<Goal> goals, boolean finished) {
        if (!finished && !goals.isEmpty()) {
            Goal lastGoal = goals.get(goals.size() - 1);
            return new int[] { lastGoal.getHomeScore(), lastGoal.getAwayScore() };
        }

        int home = 0;
        int away = 0;
        int latestOrder = Integer.MIN_VALUE;
        if (value instanceof List<?>) {
            for (Object item : (List<?>) value) {
                Map<?, ?> result = asObject(item);
                int order = number(result.get("resultOrderID"));
                if (order >= latestOrder) {
                    latestOrder = order;
                    home = number(result.get("pointsTeam1"));
                    away = number(result.get("pointsTeam2"));
                }
            }
        }

        if (latestOrder == Integer.MIN_VALUE && !goals.isEmpty()) {
            Goal lastGoal = goals.get(goals.size() - 1);
            return new int[] { lastGoal.getHomeScore(), lastGoal.getAwayScore() };
        }
        return new int[] { home, away };
    }

    private Map<?, ?> asObject(Object value) {
        if (!(value instanceof Map<?, ?>)) {
            throw new IllegalArgumentException("JSON-Wert muss ein Objekt sein.");
        }
        return (Map<?, ?>) value;
    }

    private int number(Object value) {
        return value instanceof Number ? ((Number) value).intValue() : 0;
    }
}
