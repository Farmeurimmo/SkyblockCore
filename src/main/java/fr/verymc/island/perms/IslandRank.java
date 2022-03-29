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

    public static boolean isUp(IslandRank rank, IslandRank otherRank) {
        if (rank == COCHEF && otherRank == MODERATEUR || otherRank == MEMBRE) {
            return true;
        } else if (rank == MODERATEUR && otherRank == MEMBRE) {
            return true;
        } else {
            return false;
        }
    }

    public static IslandRank getNextRank(IslandRank rank) {
        switch (rank) {
            case MODERATEUR:
                return COCHEF;
            case MEMBRE:
                return MODERATEUR;
            default:
                return rank;
        }
    }

    public static IslandRank getPreviousRank(IslandRank rank) {
        switch (rank) {
            case CHEF:
                return COCHEF;
            case COCHEF:
                return MODERATEUR;
            case MODERATEUR:
                return MEMBRE;
            default:
                return rank;
        }
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
