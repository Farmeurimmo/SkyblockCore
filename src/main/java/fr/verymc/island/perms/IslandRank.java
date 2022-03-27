package main.java.fr.verymc.island.perms;

public enum IslandRank {

    CHEF("Chef"),
    COCHEF("Co-Chef"),
    MODERATEUR("Modérateur"),
    MEMBRE("Membre");

    private String name;

    IslandRank(String name) {
        this.name = name;
    }

    public String getName(IslandRank rank) {
        return rank.name();
    }

    public IslandRank fromString(String rank) {
        return valueOf(rank);
    }

    public String getName() {
        return name;
    }
}
