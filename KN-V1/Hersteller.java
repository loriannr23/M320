public class Hersteller {
    private String name;
    private String land;
    private String webseite;

    public Hersteller(String name, String land, String webseite) {
        this.name = name;
        this.land = land;
        this.webseite = webseite;
    }

    public String getName() {
        return name;
    }

    public String getLand() {
        return land;
    }

    public String getWebseite() {
        return webseite;
    }

    @Override
    public String toString() {
        return name + ", " + land + ", " + webseite;
    }
}
