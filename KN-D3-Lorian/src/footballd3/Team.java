package footballd3;

public final class Team {
    private final String name;

    public Team(String name) {
        this.name = name == null || name.trim().isEmpty() ? "Unbekannt" : name;
    }

    public String getName() {
        return name;
    }
}
