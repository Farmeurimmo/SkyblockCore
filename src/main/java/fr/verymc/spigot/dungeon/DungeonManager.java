package main.java.fr.verymc.spigot.dungeon;

public class DungeonManager {

    public static DungeonManager instance;

    public DungeonManager() {
        instance = this;

        new DungeonMobCreator();
        new DungeonBossBarManager();
    }

    public void loadDungeons() {
        //choose difficulty
        //paste schem file here
        //init dungeon
    }
}
