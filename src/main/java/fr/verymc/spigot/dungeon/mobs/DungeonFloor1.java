package main.java.fr.verymc.spigot.dungeon.mobs;

import main.java.fr.verymc.spigot.Main;
import org.bukkit.Location;

import java.util.Arrays;
import java.util.List;

public enum DungeonFloor1 {

    PLAYER(Arrays.asList(new Location(Main.instance.mainWorld, 0.5, 86, 59.5))),

    ZOMBIE_T1(Arrays.asList(new Location(Main.instance.mainWorld, -26.5, 81, 18.5),
            new Location(Main.instance.mainWorld, -9.5, 81, 12.5), new Location(Main.instance.mainWorld, 11.5, 81, 5.5),
            new Location(Main.instance.mainWorld, 34.5, 81, 10.5))),

    ZOMBIE_T3(Arrays.asList(new Location(Main.instance.mainWorld, 42.5, 81, -19.5), new Location(Main.instance.mainWorld, 19.5, 81, -30.5),
            new Location(Main.instance.mainWorld, -5.5, 82, -36.5), new Location(Main.instance.mainWorld, 1.5, 82, -46.5))),

    ZOMBIE_T5(Arrays.asList(new Location(Main.instance.mainWorld, -5.5, 82, -75), new Location(Main.instance.mainWorld, 16.5, 82, -80.5),
            new Location(Main.instance.mainWorld, 4.5, 82, -83.5))),

    ZOMBIE_T10(Arrays.asList(new Location(Main.instance.mainWorld, 4.5, 82, -116.5)));

    private List<Location> locations;

    DungeonFloor1(List<Location> locations) {
        this.locations = locations;
    }

    public List<Location> getLocations() {
        return locations;
    }
}
