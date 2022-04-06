package main.java.fr.verymc.island.perms;

import main.java.fr.verymc.island.Island;

import java.util.HashMap;

public class IslandRank {

    public static IslandRank instance;

    private HashMap<IslandRanks, Integer> islandRankPos = new HashMap<>();

    public IslandRank() {
        instance = this;
        for (IslandRanks rank : IslandRanks.values()) {
            if (rank == rank.CHEF) {
                this.islandRankPos.put(rank, 0);
            }
            if (rank == IslandRanks.COCHEF) {
                this.islandRankPos.put(rank, 1);
            }
            if (rank == IslandRanks.MODERATEUR) {
                this.islandRankPos.put(rank, 2);
            }
            if (rank == IslandRanks.MEMBRE) {
                this.islandRankPos.put(rank, 3);
            }
            if (rank == IslandRanks.COOP) {
                this.islandRankPos.put(rank, 4);
            }
            if (rank == IslandRanks.VISITEUR) {
                this.islandRankPos.put(rank, 5);
            }
        }
    }

    public static boolean isUp(IslandRanks rank, IslandRanks otherRank) {
        if (rank == IslandRanks.CHEF) {
            return true;
        } else if (rank == IslandRanks.COCHEF && otherRank != IslandRanks.COCHEF && otherRank != IslandRanks.CHEF) {
            return true;
        } else if (rank == IslandRanks.MODERATEUR && otherRank != IslandRanks.MODERATEUR && otherRank != IslandRanks.COCHEF &&
                otherRank != IslandRanks.CHEF) {
            return true;
        } else if (rank == IslandRanks.MEMBRE && otherRank != IslandRanks.MEMBRE && otherRank != IslandRanks.MODERATEUR &&
                otherRank != IslandRanks.COCHEF && otherRank != IslandRanks.CHEF) {
            return true;
        } else {
            return false;
        }
    }

    public static IslandRanks getNextRank(IslandRanks rank) {
        switch (rank) {
            case MODERATEUR:
                return IslandRanks.COCHEF;
            case MEMBRE:
                return IslandRanks.MODERATEUR;
            default:
                return rank;
        }
    }

    public static IslandRanks getPreviousRank(IslandRanks rank) {
        switch (rank) {
            case COCHEF:
                return IslandRanks.MODERATEUR;
            case MODERATEUR:
                return IslandRanks.MEMBRE;
            default:
                return rank;
        }
    }

    public static HashMap<IslandRanks, Integer> getIslandRankPos() {
        return instance.islandRankPos;
    }

    public IslandRanks getNextRankForPerm(IslandPerms perms, Island playerIsland) {
        if (!playerIsland.hasPerms(IslandRanks.COCHEF, perms)) {
            return IslandRanks.COCHEF;
        } else if (!playerIsland.hasPerms(IslandRanks.MODERATEUR, perms)) {
            return IslandRanks.MODERATEUR;
        } else if (!playerIsland.hasPerms(IslandRanks.MEMBRE, perms)) {
            return IslandRanks.MEMBRE;
        } else if (!playerIsland.hasPerms(IslandRanks.COOP, perms)) {
            return IslandRanks.COOP;
        } else if (!playerIsland.hasPerms(IslandRanks.VISITEUR, perms)) {
            return IslandRanks.VISITEUR;
        } else {
            return IslandRanks.COCHEF;
        }
    }

    public IslandRanks getPreviousRankForPerm(IslandPerms perms, Island playerIsland) {
        if (playerIsland.hasPerms(IslandRanks.VISITEUR, perms)) {
            return IslandRanks.VISITEUR;
        } else if (playerIsland.hasPerms(IslandRanks.COOP, perms)) {
            return IslandRanks.COOP;
        } else if (playerIsland.hasPerms(IslandRanks.MEMBRE, perms)) {
            return IslandRanks.MEMBRE;
        } else if (playerIsland.hasPerms(IslandRanks.MODERATEUR, perms)) {
            return IslandRanks.MODERATEUR;
        } else if (playerIsland.hasPerms(IslandRanks.COCHEF, perms)) {
            return IslandRanks.COCHEF;
        } else {
            return IslandRanks.COOP;
        }
    }
}
