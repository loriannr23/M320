package flightsystem;

import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {

        Flugzeug a320 = new Flugzeug("Airbus A320", 2, 3);
        Flugzeug b777 = new Flugzeug("Boeing 777", 3, 3);

        Flug lx318 = new Flug("LX318", "London", LocalTime.of(7, 45), a320);
        Flug lx160 = new Flug("LX160", "New York", LocalTime.of(13, 5), b777);

        Passagier anna = new Passagier("Anna Meier", "CH1234567");
        Passagier bruno = new Passagier("Bruno Keller", "CH7654321");
        Passagier cem = new Passagier("Cem Yildiz", "CH1122334");

        lx318.addPassagier(anna);
        lx318.addPassagier(bruno);

        lx160.addPassagier(anna);
        lx160.addPassagier(cem);

        Zeitplan zp = new Zeitplan("ZRH");

        zp.addFlug(lx318);
        zp.addFlug(lx160);

        zp.printPlan();

        System.out.println();

        lx318.printPassagierliste();

        System.out.println();

        System.out.println("Morgenflüge 06:00-12:00: "
                + zp.findByStartZeit(LocalTime.of(6, 0), LocalTime.of(12, 0)));

        System.out.println("Passagiere total: " + zp.getTotalPassagiere());
        System.out.println("Flüge von Anna: " + anna.getFluege());

        // Entfernen
        lx318.removePassagier(bruno);

        System.out.println("Nach Storno LX318: " + lx318.getPassagiere());
    }
}
