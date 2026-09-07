import java.util.ArrayList;

public class Shop {
    private ArrayList<Produkt> produkte;

    public Shop() {
        produkte = new ArrayList<>();
    }

    public Produkt sucheProdukt(String artikelnummer) {
        for (Produkt produkt : produkte) {
            if (produkt.getArtikelnummer().equals(artikelnummer)) {
                return produkt;
            }
        }
        return null;
    }

    public void produktVerkaufen(String artikelnummer) {
        Produkt produkt = sucheProdukt(artikelnummer);

        if (produkt != null) {
            produkt.verkaufen();
        } else {
            System.out.println("Kein Produkt mit Artikelnummer " + artikelnummer + " gefunden.");
        }
    }

    public void produktHinzufügen(Produkt produkt) {
        produkte.add(produkt);
        System.out.println(produkt.getProduktname() + " wurde zum Shop hinzugefügt.");
    }

    public void produktEntfernen(String artikelnummer) {
        Produkt produkt = sucheProdukt(artikelnummer);

        if (produkt != null) {
            produkte.remove(produkt);
            System.out.println(produkt.getProduktname() + " wurde aus dem Shop entfernt.");
        } else {
            System.out.println("Kein Produkt mit Artikelnummer " + artikelnummer + " gefunden.");
        }
    }

    public void alleProdukteAnzeigen() {
        for (Produkt produkt : produkte) {
            System.out.println(produkt);
        }
    }
}
