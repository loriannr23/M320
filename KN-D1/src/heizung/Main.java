package heizung;

public class Main {
    public static void main(String[] args) {
        Heizung heizung = new Heizung(18.0);

        Raum klassenzimmer = new Raum("Klassenzimmer", heizung);

        System.out.println("Startzustand:");
        klassenzimmer.zeigeZustand();

        System.out.println();
        System.out.println("Heizung einschalten:");
        heizung.einschalten();
        klassenzimmer.zeigeZustand();

        System.out.println();
        System.out.println("Temperatur auf 21 Grad stellen:");
        klassenzimmer.temperaturAendern(21.0);
        klassenzimmer.zeigeZustand();

        System.out.println();
        System.out.println("Temperatur erneut verändern:");
        klassenzimmer.temperaturAendern(23.5);
        klassenzimmer.zeigeZustand();

        System.out.println();
        System.out.println("Heizung ausschalten:");
        heizung.ausschalten();

        System.out.println();
        System.out.println("Endzustand:");
        klassenzimmer.zeigeZustand();
    }
}
