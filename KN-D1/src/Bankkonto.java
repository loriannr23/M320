public class Bankkonto {
    private String inhaber;
    private String kontonummer;
    private double saldo;

    public Bankkonto(String inhaber, String kontonummer, double startSaldo) {
        this.inhaber = inhaber;
        this.kontonummer = kontonummer;
        this.saldo = startSaldo;
    }

    public String getInhaber() {
        return inhaber;
    }

    public void setInhaber(String inhaber) {
        this.inhaber = inhaber;
    }

    public String getKontonummer() {
        return kontonummer;
    }

    public double getSaldo() {
        return saldo;
    }

    public void einzahlen(double betrag) {
        if (betrag <= 0) {
            System.out.println("Einzahlung fehlgeschlagen: Der Betrag muss positiv sein.");
            return;
        }

        saldo = saldo + betrag;
        System.out.println(inhaber + " zahlt CHF " + betrag + " ein.");
    }

    public boolean abheben(double betrag) {
        if (betrag <= 0) {
            System.out.println("Abhebung fehlgeschlagen: Der Betrag muss positiv sein.");
            return false;
        }

        if (betrag > saldo) {
            System.out.println("Abhebung fehlgeschlagen: " + inhaber + " hat zu wenig Geld.");
            return false;
        }

        saldo = saldo - betrag;
        System.out.println(inhaber + " hebt CHF " + betrag + " ab.");
        return true;
    }

    public void ueberweisen(Bankkonto empfaenger, double betrag) {
        System.out.println(inhaber + " möchte CHF " + betrag + " an " + empfaenger.getInhaber() + " überweisen.");

        boolean abhebungErfolgreich = abheben(betrag);

        if (abhebungErfolgreich) {
            empfaenger.einzahlen(betrag);
            System.out.println("Überweisung erfolgreich.");
        } else {
            System.out.println("Überweisung abgebrochen.");
        }
    }

    public void zeigeKontostand() {
        System.out.println("Konto " + kontonummer + " (" + inhaber + "): CHF " + saldo);
    }
}
