package wetter;

import java.io.IOException;
import java.util.Scanner;

// User-Input + Ausgabe
// - prueft die Eingabe, holt selbst keine Daten
// - Wetter holen: delegiert an WetterService
public class Konsole {
    private static final int MIN_LAENGE = 2;
    private static final int MAX_LAENGE = 50;

    private final Scanner scanner = new Scanner(System.in);
    private final WetterService service;

    public Konsole(WetterService service) {
        this.service = service;
    }

    public void starten() {
        System.out.println("=== Wetter-App ===");
        while (true) {
            System.out.print("Ort (leer = Ende): ");
            // Ctrl+Z / Ctrl+D -> Eingabe zu Ende -> beenden statt Absturz
            if (!scanner.hasNextLine()) {
                break;
            }
            String eingabe = scanner.nextLine().trim();
            if (eingabe.isEmpty()) {
                break;
            }
            try {
                String ort = pruefeOrt(eingabe);
                Wetter wetter = service.holeWetter(ort);
                System.out.println(wetter.ort() + ": " + wetter.temperatur() + " °C, Wind "
                        + wetter.wind() + " km/h");
            } catch (UngueltigeEingabeException e) {
                System.out.println("Fehler: " + e.getMessage());
            } catch (IOException | InterruptedException e) {
                System.out.println("Fehler: Wetterdienst nicht erreichbar.");
            }
        }
        System.out.println("Tschüss!");
    }

    // 2-50 Zeichen, nur Buchstaben, Leerzeichen, Punkt, Bindestrich (St. Gallen)
    private String pruefeOrt(String ort) throws UngueltigeEingabeException {
        if (ort.length() < MIN_LAENGE || ort.length() > MAX_LAENGE) {
            throw new UngueltigeEingabeException(
                    "Ort muss " + MIN_LAENGE + "-" + MAX_LAENGE + " Zeichen lang sein.");
        }
        if (!ort.matches("[\\p{L} .-]+")) {
            throw new UngueltigeEingabeException("Nur Buchstaben, Leerzeichen, . und - erlaubt.");
        }
        return ort;
    }
}
