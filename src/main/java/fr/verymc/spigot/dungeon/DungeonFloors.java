package main.java.fr.verymc.spigot.dungeon;

public enum DungeonFloors {

    FLOOR_1("Zombie"),
    FLOOR_2("Skelette"),
    FLOOR_3("Arraignée");

    private String name;

    DungeonFloors(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
