package heizung;

public class Heizung {
    private double temperatur;
    private boolean eingeschaltet;

    public Heizung(double temperatur) {
        this.temperatur = temperatur;
        this.eingeschaltet = false;
    }

    public void einschalten() {
        eingeschaltet = true;
        System.out.println("Die Heizung wurde eingeschaltet.");
    }

    public void ausschalten() {
        eingeschaltet = false;
        System.out.println("Die Heizung wurde ausgeschaltet.");
    }

    public void setTemperatur(double temperatur) {
        this.temperatur = temperatur;
        System.out.println("Die Temperatur wurde auf " + temperatur + " Grad gesetzt.");
    }

    public double getTemperatur() {
        return temperatur;
    }

    public boolean isEingeschaltet() {
        return eingeschaltet;
    }
}
