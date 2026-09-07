public class Smartphone extends Produkt {
    private double displaygrösse;
    private boolean dualSim;

    public Smartphone(String produktname, String hersteller, double preis, String artikelnummer,
                      boolean anLager, double displaygrösse, boolean dualSim) {
        super(produktname, hersteller, preis, artikelnummer, anLager);
        this.displaygrösse = displaygrösse;
        this.dualSim = dualSim;
    }

    public double getDisplaygrösse() {
        return displaygrösse;
    }

    public boolean isDualSim() {
        return dualSim;
    }

    @Override
    public String toString() {
        return super.toString() + ", Displaygrösse: " + displaygrösse + " Zoll, Dual-SIM: " + dualSim;
    }
}
