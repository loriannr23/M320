package footballd3;

public final class FootballApiException extends Exception {
    public FootballApiException(String message) {
        super(message);
    }

    public FootballApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
