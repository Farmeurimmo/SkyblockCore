package main.java.fr.verymc.island.upgrade;

public enum IslandUpgradesType {

    SIZE(0),
    MEMBER(1),
    GENERATOR(2),
    BLOC_LIMIT(3);

    private Integer type;

    IslandUpgradesType(int size) {
        this.type = size;
    }

    public Integer getType() {
        return type;
    }

}
