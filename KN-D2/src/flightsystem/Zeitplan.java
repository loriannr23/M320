package flightsystem;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Zeitplan {
    private final String flughafen;
    private final List<Flug> fluege = new ArrayList<>();

    public Zeitplan(String flughafen) {
        this.flughafen = Objects.requireNonNull(flughafen);
    }

    public void addFlug(Flug flug) {
        fluege.add(Objects.requireNonNull(flug));
    }

    public List<Flug> findByStartZeit(LocalTime von, LocalTime bis) {
        List<Flug> treffer = new ArrayList<>();
        for (Flug flug : fluege) {
            LocalTime start = flug.getStartZeit();
            if (!start.isBefore(von) && !start.isAfter(bis)) {
                treffer.add(flug);
            }
        }
        return treffer;
    }

    public int getTotalPassagiere() {
        int total = 0;
        for (Flug flug : fluege) {
            // Delegation: Der Zeitplan fragt jeden Flug nach seiner Anzahl.
            total += flug.getAnzahlPassagiere();
        }
        return total;
    }

    public void printPlan() {
        System.out.println("Zeitplan " + flughafen + ":");
        for (Flug flug : fluege) {
            System.out.println("- " + flug);
        }
    }
}
