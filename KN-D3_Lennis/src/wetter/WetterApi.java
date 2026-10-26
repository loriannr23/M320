package wetter;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Fremde Schnittstelle: Open-Meteo (gratis, ohne API-Key)
// - nur HTTP-Aufruf + Werte aus dem JSON lesen
public class WetterApi {
    private static final String GEOCODING_URL = "https://geocoding-api.open-meteo.com/v1/search";
    private static final String WETTER_URL = "https://api.open-meteo.com/v1/forecast";

    private final HttpClient http = HttpClient.newHttpClient();

    // -> {breite, laenge}, null wenn Ort unbekannt
    public double[] sucheKoordinaten(String ort) throws IOException, InterruptedException {
        String json = get(GEOCODING_URL + "?count=1&name="
                + URLEncoder.encode(ort, StandardCharsets.UTF_8));
        if (!json.contains("\"results\"")) {
            return null;
        }
        return new double[] {zahl(json, "latitude"), zahl(json, "longitude")};
    }

    // -> {temperatur in °C, wind in km/h}
    public double[] holeAktuellesWetter(double breite, double laenge)
            throws IOException, InterruptedException {
        String json = get(WETTER_URL + "?latitude=" + breite + "&longitude=" + laenge
                + "&current=temperature_2m,wind_speed_10m");
        return new double[] {zahl(json, "temperature_2m"), zahl(json, "wind_speed_10m")};
    }

    private String get(String url) throws IOException, InterruptedException {
        HttpRequest anfrage = HttpRequest.newBuilder(URI.create(url)).build();
        return http.send(anfrage, HttpResponse.BodyHandlers.ofString()).body();
    }

    // erste Zahl nach "schluessel": (Einheit wie "°C" ist Text -> wird uebersprungen)
    private double zahl(String json, String schluessel) throws IOException {
        Matcher m = Pattern.compile("\"" + schluessel + "\":(-?[0-9.]+)").matcher(json);
        if (!m.find()) {
            throw new IOException("Feld " + schluessel + " fehlt in der Antwort.");
        }
        return Double.parseDouble(m.group(1));
    }
}
