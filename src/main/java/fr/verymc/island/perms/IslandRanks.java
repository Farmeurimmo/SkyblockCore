package main.java.fr.verymc.island.perms;

public enum IslandRanks {

    CHEF("Chef"),
    COCHEF("Cochef"),
    MODERATEUR("Modérateur"),
    MEMBRE("Membre"),
    COOP("Membre temporaire");

    private String name;

    IslandRanks(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

