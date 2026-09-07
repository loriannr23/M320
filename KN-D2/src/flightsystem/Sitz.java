package flightsystem;

public class Sitz {
    private final int reihe;
    private final char platz;

    public Sitz(int reihe, char platz) {
        this.reihe = reihe;
        this.platz = platz;
    }

    public int getReihe() {
        return reihe;
    }

    public char getPlatz() {
        return platz;
    }

    @Override
    public String toString() {
        return Integer.toString(reihe) + platz;
    }
}
