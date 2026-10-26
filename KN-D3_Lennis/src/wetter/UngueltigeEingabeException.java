package wetter;

// Eigene Exception fuer falsche Eingaben (zu kurz, Zahlen, Ort unbekannt)
public class UngueltigeEingabeException extends Exception {

    public UngueltigeEingabeException(String meldung) {
        super(meldung);
    }
}
