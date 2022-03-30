package main.java.fr.verymc.island.perms;

import java.util.HashMap;

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
        if (rank == CHEF) {
            return true;
        }
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
            case COCHEF:
                return MODERATEUR;
            case MODERATEUR:
                return MEMBRE;
            default:
                return rank;
        }
    }

    public static HashMap<IslandRank, Integer> getIslandRankPos() {
        HashMap<IslandRank, Integer> pos = new HashMap<>();
        for (IslandRank rank : IslandRank.values()) {
            if (rank == CHEF) {
                pos.put(rank, 0);
            }
            if (rank == COCHEF) {
                pos.put(rank, 1);
            }
            if (rank == MODERATEUR) {
                pos.put(rank, 2);
            }
            if (rank == MEMBRE) {
                pos.put(rank, 3);
            }
        }
        return pos;
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
