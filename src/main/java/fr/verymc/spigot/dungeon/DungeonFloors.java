package main.java.fr.verymc.spigot.dungeon;

public enum DungeonFloors {

    FLOOR_1("Zombie"),
    FLOOR_2("Skelette"),
    FLOOR_3("Arraignée");

    private String name;

    DungeonFloors(String name) {
        this.name = name;
    }

    public static int getSizeFromFloor(DungeonFloors floor) {
        switch (floor) {
            case FLOOR_1:
                return 175;
            case FLOOR_2:
                return 200;
            case FLOOR_3:
                return 250;
            default:
                return 0;
        }
    }

    public static int getPlayersRequiredForFloor(DungeonFloors floor) {
        switch (floor) {
            case FLOOR_2:
                return 3;
            case FLOOR_3:
                return 4;
            default:
                return 1;
        }
    }

    public static int getDurationFromFloor(DungeonFloors floor) {
        switch (floor) {
            case FLOOR_2:
                return 12;
            case FLOOR_3:
                return 15;
            default:
                return 10;
        }
    }

    public String getName() {
        return name;
    }
}
