package main.java.fr.verymc.island.upgrade;

public enum IslandUpgrades {

    SIZE_0(50),
    SIZE_1(75),
    SIZE_2(100),
    SIZE_3(150),
    SIZE_4(200),
    SIZE_5(250);

    private Integer size;

    IslandUpgrades(int size) {
        this.size = size;
    }

    public Integer getSize() {
        return size;
    }
}
