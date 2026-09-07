package heizung;

public class Raum {
    private String name;
    private Heizung heizung;

    public Raum(String name, Heizung heizung) {
        this.name = name;
        this.heizung = heizung;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Heizung getHeizung() {
        return heizung;
    }

    public void setHeizung(Heizung heizung) {
        this.heizung = heizung;
    }

    public void temperaturAendern(double temperatur) {
        System.out.println("Im Raum " + name + " soll die Temperatur geändert werden.");
        heizung.setTemperatur(temperatur);
    }

    public void zeigeZustand() {
        System.out.println("Raum: " + name);
        System.out.println("Temperatur: " + heizung.getTemperatur() + " Grad");
        System.out.println("Heizung eingeschaltet: " + heizung.isEingeschaltet());
    }
}
