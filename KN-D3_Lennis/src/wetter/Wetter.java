package wetter;

// Ergebnis fuer einen Ort
// - record -> nur Daten, unveraenderbar
public record Wetter(String ort, double temperatur, double wind) {
}
