public class Bank {
    private String name;

    public Bank(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bankkonto erstelleKonto(String inhaber, String kontonummer, double startSaldo) {
        Bankkonto konto = new Bankkonto(inhaber, kontonummer, startSaldo);
        System.out.println(name + " erstellt ein neues Konto für " + inhaber + ".");
        return konto;
    }

    public void druckeKontoauszug(Bankkonto konto) {
        System.out.println("Kontoauszug von " + name + ":");
        konto.zeigeKontostand();
    }
}
