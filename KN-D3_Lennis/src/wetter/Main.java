package wetter;

// Nur Start: Objekte verbinden, App starten
public class Main {

    public static void main(String[] args) {
        new Konsole(new WetterService(new WetterApi())).starten();
    }
}
