package main.java.fr.verymc.spigot.dungeon;

import main.java.fr.verymc.spigot.dungeon.mobs.DungeonMobCreator;

public class DungeonManager {

    public static DungeonManager instance;

    public DungeonManager() {
        instance = this;

        new DungeonMobCreator();
        new DungeonBossBarManager();


        //START WHEN AN SPECIFIC DUNGEON IS REQUESTED AND THERE IS ENOUGH PLAYERS
        //loadDungeons();
    }

    public void loadDungeons() {
        //choose difficulty
        //paste schem file here
        //init dungeon
    }
}
