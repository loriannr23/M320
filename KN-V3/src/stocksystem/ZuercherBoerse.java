package stocksystem;

public final class ZuercherBoerse extends Boerse {
    public ZuercherBoerse() {
        super("Börse Zürich (virtuelle Beispielkurse in CHF)");
        registriereAktie("NESN", "Nestlé", "95.40");
        registriereAktie("ABBN", "ABB", "48.75");
        registriereAktie("SREN", "Swiss Re", "126.20");
    }
}
