public class Monitor extends Produkt {
    private double bildschirmdiagonale;
    private String auflösung;

    public Monitor(String produktname, String hersteller, double preis, String artikelnummer,
                   boolean anLager, double bildschirmdiagonale, String auflösung) {
        super(produktname, hersteller, preis, artikelnummer, anLager);
        this.bildschirmdiagonale = bildschirmdiagonale;
        this.auflösung = auflösung;
    }

    public double getBildschirmdiagonale() {
        return bildschirmdiagonale;
    }

    public String getAuflösung() {
        return auflösung;
    }

    @Override
    public String toString() {
        return super.toString() + ", Bildschirmdiagonale: " + bildschirmdiagonale
                + " Zoll, Auflösung: " + auflösung;
    }
}
