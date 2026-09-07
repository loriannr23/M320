public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank("TBZ Bank");

        Bankkonto kontoLorian = bank.erstelleKonto("Lorian", "CH-1001", 500.0);
        Bankkonto kontoLennis = bank.erstelleKonto("Lennis", "CH-1002", 150.0);

        System.out.println();
        System.out.println("Startzustand:");
        bank.druckeKontoauszug(kontoLorian);
        bank.druckeKontoauszug(kontoLennis);

        System.out.println();
        System.out.println("Lorian bekommt Lohn:");
        kontoLorian.einzahlen(200.0);
        kontoLorian.zeigeKontostand();

        System.out.println();
        System.out.println("Lennis kauft etwas:");
        kontoLennis.abheben(50.0);
        kontoLennis.zeigeKontostand();

        System.out.println();
        System.out.println("Lorian überweist Geld an Lennis:");
        kontoLorian.ueberweisen(kontoLennis, 120.0);

        System.out.println();
        System.out.println("Endzustand:");
        bank.druckeKontoauszug(kontoLorian);
        bank.druckeKontoauszug(kontoLennis);
    }
}
