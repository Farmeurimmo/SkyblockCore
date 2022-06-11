package main.java.fr.verymc.island.minions;

public enum MinionType {

    PIOCHEUR("Piocheur"),
    AUTRE("Autre");

    private String name;

    MinionType(String name) {
        this.name = name;
    }

    public String getName(MinionType minionType) {
        return minionType.name();
    }

    public MinionType fromString(String minionType) {
        return valueOf(minionType);
    }
}
