package wetter;

import java.io.IOException;

// Logik: aus einem Ortsnamen ein Wetter-Objekt machen
// - Daten abrufen: delegiert an WetterApi
public class WetterService {
    private final WetterApi api;

    public WetterService(WetterApi api) {
        this.api = api;
    }

    public Wetter holeWetter(String ort)
            throws UngueltigeEingabeException, IOException, InterruptedException {
        // 1. Ort -> Koordinaten
        double[] koordinaten = api.sucheKoordinaten(ort);
        if (koordinaten == null) {
            throw new UngueltigeEingabeException("Ort \"" + ort + "\" nicht gefunden.");
        }
        // 2. Koordinaten -> Temperatur + Wind
        double[] werte = api.holeAktuellesWetter(koordinaten[0], koordinaten[1]);
        return new Wetter(ort, werte[0], werte[1]);
    }
}
