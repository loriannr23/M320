import java.util.ArrayList;

public class Bestellung {
    private String bestellnummer;
    private Kunde kunde;
    private ArrayList<Produkt> produkte;

    public Bestellung(String bestellnummer, Kunde kunde) {
        this.bestellnummer = bestellnummer;
        this.kunde = kunde;
        produkte = new ArrayList<>();
    }

    public void produktHinzufügen(Produkt produkt) {
        produkte.add(produkt);
    }

    public double berechneGesamtpreis() {
        double gesamtpreis = 0;

        for (Produkt produkt : produkte) {
            gesamtpreis = gesamtpreis + produkt.getPreis();
        }

        return gesamtpreis;
    }

    @Override
    public String toString() {
        return "Bestellung " + bestellnummer + " für " + kunde
                + ", Anzahl Produkte: " + produkte.size()
                + ", Gesamtpreis: CHF " + berechneGesamtpreis();
    }
}
