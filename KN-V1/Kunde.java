public class Kunde {
    private String vorname;
    private String nachname;
    private String kundennummer;

    public Kunde(String vorname, String nachname, String kundennummer) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.kundennummer = kundennummer;
    }

    public String getVorname() {
        return vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public String getKundennummer() {
        return kundennummer;
    }

    @Override
    public String toString() {
        return vorname + " " + nachname + " (" + kundennummer + ")";
    }
}
