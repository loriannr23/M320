public class Main {
    public static void main(String[] args) {
        Shop shop = new Shop();

        Laptop laptop1 = new Laptop("ThinkPad E14", "Lenovo", 899.00, "L-1001", true, 16, 512);
        Laptop laptop2 = new Laptop("MacBook Air", "Apple", 1199.00, "L-1002", true, 8, 256);

        Smartphone smartphone1 = new Smartphone("Galaxy S24", "Samsung", 799.00, "S-2001", true, 6.2, true);
        Smartphone smartphone2 = new Smartphone("iPhone 15", "Apple", 849.00, "S-2002", false, 6.1, false);

        Monitor monitor1 = new Monitor("UltraSharp U2724", "Dell", 349.00, "M-3001", true, 27.0, "2560x1440");
        Monitor monitor2 = new Monitor("Odyssey G5", "Samsung", 299.00, "M-3002", true, 32.0, "2560x1440");

        Drucker drucker1 = new Drucker("LaserJet Pro", "HP", 219.00, "D-4001", true, false, true, 28);
        Drucker drucker2 = new Drucker("EcoTank ET-2850", "Epson", 249.00, "D-4002", false, true, true, 15);

        shop.produktHinzufügen(laptop1);
        shop.produktHinzufügen(laptop2);
        shop.produktHinzufügen(smartphone1);
        shop.produktHinzufügen(smartphone2);
        shop.produktHinzufügen(monitor1);
        shop.produktHinzufügen(monitor2);
        shop.produktHinzufügen(drucker1);
        shop.produktHinzufügen(drucker2);

        System.out.println();
        System.out.println("Alle Produkte im Shop:");
        shop.alleProdukteAnzeigen();

        System.out.println();
        System.out.println("Test: verkaufen()");
        shop.produktVerkaufen("L-1001");

        System.out.println();
        System.out.println("Test: einlagern()");
        smartphone2.einlagern();

        System.out.println();
        System.out.println("Test: Produktsuche anhand der Artikelnummer");
        Produkt gefundenesProdukt = shop.sucheProdukt("M-3001");
        if (gefundenesProdukt != null) {
            System.out.println("Gefunden: " + gefundenesProdukt);
        }

        System.out.println();
        System.out.println("Test: Entfernen eines Produkts");
        shop.produktEntfernen("D-4001");

        System.out.println();
        System.out.println("Produkte nach dem Entfernen:");
        shop.alleProdukteAnzeigen();

        System.out.println();
        System.out.println("Test: Polymorphie mit ArrayList<Produkt>");
        Produkt produktAusVerschiedenerSubklasse = laptop2;
        System.out.println("Laptop als Produkt gespeichert: " + produktAusVerschiedenerSubklasse.getProduktname());
        produktAusVerschiedenerSubklasse = monitor2;
        System.out.println("Monitor als Produkt gespeichert: " + produktAusVerschiedenerSubklasse.getProduktname());

        System.out.println();
        System.out.println("Test: Kunde, Bestellung und Hersteller sind keine Subklassen von Produkt");
        Kunde kunde = new Kunde("Max", "Muster", "K-5001");
        Bestellung bestellung = new Bestellung("B-9001", kunde);
        Hersteller hersteller = new Hersteller("Lenovo", "China", "www.lenovo.com");

        bestellung.produktHinzufügen(laptop2);
        bestellung.produktHinzufügen(monitor2);

        System.out.println(kunde);
        System.out.println(bestellung);
        System.out.println(hersteller);
    }
}
