package stocksystem;

public final class LondonBoerse extends Boerse {
    public LondonBoerse() {
        super("Börse London (virtuelle Beispielkurse in CHF)");
        registriereAktie("AZN", "AstraZeneca", "118.60");
        registriereAktie("BP", "BP", "5.82");
        registriereAktie("ULVR", "Unilever", "46.35");
    }
}
