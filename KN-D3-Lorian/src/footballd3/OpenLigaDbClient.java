package footballd3;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class OpenLigaDbClient implements FootballDataProvider {
    private static final String BASE_URL = "https://api.openligadb.de";
    private final HttpClient httpClient;

    public OpenLigaDbClient() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(8))
                .build();
    }

    @Override
    public List<Match> getMatchdayMatches(int season, int matchday) throws FootballApiException {
        String path = "/getmatchdata/bl1/" + season + "/" + matchday;
        return requestMatchList(path);
    }

    @Override
    public List<Match> getMatchesByTeam(int season, String teamName) throws FootballApiException {
        String encodedTeam = URLEncoder.encode(teamName, StandardCharsets.UTF_8).replace("+", "%20");
        String path = "/getmatchdata/bl1/" + season + "/" + encodedTeam;
        return requestMatchList(path);
    }

    @Override
    public Match getMatch(int matchId) throws FootballApiException {
        Object response = requestJson("/getmatchdata/" + matchId);
        try {
            Map<String, Object> matchData = asObject(response, "Match");
            return mapMatch(matchData);
        } catch (RuntimeException exception) {
            throw new FootballApiException("Die Matchdaten haben ein unerwartetes Format.", exception);
        }
    }

    private List<Match> requestMatchList(String path) throws FootballApiException {
        Object response = requestJson(path);
        try {
            List<?> rawMatches;
            if (response instanceof List<?>) {
                rawMatches = (List<?>) response;
            } else if (response instanceof Map<?, ?>) {
                rawMatches = Collections.singletonList(response);
            } else {
                throw new IllegalArgumentException("Die API-Antwort ist keine Match-Liste.");
            }

            List<Match> matches = new ArrayList<Match>();
            for (Object rawMatch : rawMatches) {
                matches.add(mapMatch(asObject(rawMatch, "Match")));
            }
            return matches;
        } catch (RuntimeException exception) {
            throw new FootballApiException("Die Matchdaten haben ein unerwartetes Format.", exception);
        }
    }

    private Object requestJson(String path) throws FootballApiException {
        URI uri = URI.create(BASE_URL + path);
        HttpRequest request = HttpRequest.newBuilder(uri)
                .timeout(Duration.ofSeconds(15))
                .header("Accept", "application/json")
                .header("User-Agent", "TBZ-M320-KN-D3-FootballTracker/1.0")
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request,
                    HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                if (response.statusCode() == 404) {
                    throw new FootballApiException("Es wurden keine Daten gefunden. Prüfe Saison, Spieltag oder Match-ID.");
                }
                throw new FootballApiException("OpenLigaDB antwortete mit HTTP " + response.statusCode() + ".");
            }
            try {
                return JsonParser.parse(response.body());
            } catch (RuntimeException exception) {
                throw new FootballApiException("Die Antwort der Football-API ist kein gültiges JSON.", exception);
            }
        } catch (IOException exception) {
            throw new FootballApiException("OpenLigaDB ist nicht erreichbar. Prüfe deine Internetverbindung.", exception);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new FootballApiException("Die API-Anfrage wurde unterbrochen.", exception);
        }
    }

    private Match mapMatch(Map<String, Object> data) {
        int matchId = intValue(data.get("matchID"), "matchID", 0);
        int season = intValue(data.get("leagueSeason"), "leagueSeason", 0);
        String leagueName = stringValue(data.get("leagueName"), "Unbekannte Liga");
        Map<String, Object> group = objectValue(data.get("group"));
        int matchday = intValue(group.get("groupOrderID"), "groupOrderID", 0);

        Team home = mapTeam(objectValue(data.get("team1")));
        Team away = mapTeam(objectValue(data.get("team2")));
        List<Goal> goals = mapGoals(arrayValue(data.get("goals")));
        boolean finished = Boolean.TRUE.equals(data.get("matchIsFinished"));
        int[] score = mapScore(data.get("matchResults"), goals, finished);
        LocalDateTime matchTime = mapDateTime(data);

        return new Match(matchId, season, matchday, leagueName, matchTime,
                home, away, score[0], score[1], finished, goals);
    }

    private Team mapTeam(Map<String, Object> data) {
        return new Team(stringValue(data.get("teamName"), "Unbekannt"));
    }

    private List<Goal> mapGoals(List<?> rawGoals) {
        List<Goal> goals = new ArrayList<Goal>();
        for (Object rawGoal : rawGoals) {
            Map<String, Object> data = asObject(rawGoal, "Goal");
            goals.add(new Goal(
                    intValue(data.get("matchMinute"), "matchMinute", 0),
                    stringValue(data.get("goalGetterName"), "Unbekannt"),
                    intValue(data.get("scoreTeam1"), "scoreTeam1", 0),
                    intValue(data.get("scoreTeam2"), "scoreTeam2", 0),
                    Boolean.TRUE.equals(data.get("isPenalty")),
                    Boolean.TRUE.equals(data.get("isOwnGoal")),
                    Boolean.TRUE.equals(data.get("isOvertime"))));
        }
        Collections.sort(goals, new Comparator<Goal>() {
            @Override
            public int compare(Goal left, Goal right) {
                return Integer.compare(left.getMinute(), right.getMinute());
            }
        });
        return goals;
    }

    private int[] mapScore(Object rawResults, List<Goal> goals, boolean finished) {
        if (!finished && !goals.isEmpty()) {
            return scoreFromLastGoal(goals);
        }

        List<?> results = arrayValue(rawResults);
        Map<String, Object> latestResult = null;
        int latestOrder = Integer.MIN_VALUE;
        for (Object rawResult : results) {
            Map<String, Object> result = asObject(rawResult, "MatchResult");
            int order = intValue(result.get("resultOrderID"), "resultOrderID", 0);
            if (latestResult == null || order >= latestOrder) {
                latestResult = result;
                latestOrder = order;
            }
        }

        if (latestResult != null) {
            return new int[] {
                    intValue(latestResult.get("pointsTeam1"), "pointsTeam1", 0),
                    intValue(latestResult.get("pointsTeam2"), "pointsTeam2", 0)
            };
        }
        if (!goals.isEmpty()) {
            return scoreFromLastGoal(goals);
        }
        return new int[] { 0, 0 };
    }

    private int[] scoreFromLastGoal(List<Goal> goals) {
        Goal lastGoal = goals.get(goals.size() - 1);
        return new int[] { lastGoal.getScoreTeam1(), lastGoal.getScoreTeam2() };
    }

    private LocalDateTime mapDateTime(Map<String, Object> data) {
        String value = stringValue(data.get("matchDateTimeUTC"), "");
        if (value.isEmpty()) {
            value = stringValue(data.get("matchDateTime"), "");
        }
        if (value.isEmpty()) {
            return LocalDateTime.MIN;
        }

        try {
            return OffsetDateTime.parse(value).atZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
        } catch (DateTimeParseException ignored) {
            return LocalDateTime.parse(value);
        }
    }

    private static Map<String, Object> asObject(Object value, String description) {
        if (!(value instanceof Map<?, ?>)) {
            throw new IllegalArgumentException(description + " muss ein JSON-Objekt sein.");
        }
        Map<?, ?> raw = (Map<?, ?>) value;
        Map<String, Object> result = new LinkedHashMap<String, Object>();
        for (Map.Entry<?, ?> entry : raw.entrySet()) {
            if (entry.getKey() instanceof String) {
                result.put((String) entry.getKey(), entry.getValue());
            }
        }
        return result;
    }

    private static Map<String, Object> objectValue(Object value) {
        if (value == null) {
            return Collections.emptyMap();
        }
        return asObject(value, "Feld");
    }

    private static List<?> arrayValue(Object value) {
        if (value == null) {
            return Collections.emptyList();
        }
        if (!(value instanceof List<?>)) {
            throw new IllegalArgumentException("Feld muss eine JSON-Liste sein.");
        }
        return (List<?>) value;
    }

    private static String stringValue(Object value, String defaultValue) {
        return value instanceof String ? (String) value : defaultValue;
    }

    private static int intValue(Object value, String field, int defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        if (value instanceof Number) {
            try {
                return new BigDecimal(value.toString()).intValueExact();
            } catch (ArithmeticException | NumberFormatException exception) {
                throw new IllegalArgumentException("Feld " + field + " ist keine ganze Zahl.", exception);
            }
        }
        throw new IllegalArgumentException("Feld " + field + " ist keine Zahl.");
    }
}
