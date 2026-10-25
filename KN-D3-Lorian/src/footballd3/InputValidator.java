package footballd3;

public final class InputValidator {
    private InputValidator() {
    }

    public static int parseSeason(String value, int defaultSeason) throws InvalidInputException {
        if (value == null || value.trim().isEmpty()) {
            return defaultSeason;
        }

        int season = parseInteger(value, "Das Saisonjahr muss eine ganze Zahl sein.");
        if (season < 2000 || season > defaultSeason + 1) {
            throw new InvalidInputException("Das Saisonjahr muss zwischen 2000 und "
                    + (defaultSeason + 1) + " liegen.");
        }
        return season;
    }

    public static int parseMatchday(String value) throws InvalidInputException {
        int matchday = parseInteger(value, "Der Spieltag muss eine ganze Zahl sein.");
        if (matchday < 1 || matchday > 34) {
            throw new InvalidInputException("Der Spieltag muss zwischen 1 und 34 liegen.");
        }
        return matchday;
    }

    public static int parseMatchId(String value) throws InvalidInputException {
        int matchId = parseInteger(value, "Die Match-ID muss eine ganze Zahl sein.");
        if (matchId <= 0) {
            throw new InvalidInputException("Die Match-ID muss positiv sein.");
        }
        return matchId;
    }

    public static String validateTeamName(String value) throws InvalidInputException {
        if (value == null) {
            throw new InvalidInputException("Bitte einen Teamnamen eingeben.");
        }
        String teamName = value.trim();
        if (teamName.length() < 2 || teamName.length() > 50) {
            throw new InvalidInputException("Der Teamname muss 2 bis 50 Zeichen lang sein.");
        }
        if (teamName.contains("/") || teamName.contains("\\") || teamName.contains("?")
                || teamName.contains("#")) {
            throw new InvalidInputException("Der Teamname enthält ungültige Zeichen.");
        }
        return teamName;
    }

    private static int parseInteger(String value, String errorMessage) throws InvalidInputException {
        if (value == null || value.trim().isEmpty()) {
            throw new InvalidInputException(errorMessage);
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException exception) {
            throw new InvalidInputException(errorMessage);
        }
    }
}
