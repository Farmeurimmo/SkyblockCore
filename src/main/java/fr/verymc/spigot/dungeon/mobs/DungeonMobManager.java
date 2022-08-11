package main.java.fr.verymc.spigot.dungeon.mobs;

import main.java.fr.verymc.spigot.Main;
import main.java.fr.verymc.spigot.dungeon.Dungeon;
import main.java.fr.verymc.spigot.dungeon.DungeonFloors;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DungeonMobManager {

    public static DungeonMobManager instance;

    public HashMap<Dungeon, ArrayList<LivingEntity>> mobs = new HashMap<>();

    public DungeonMobManager() {
        instance = this;

        new DungeonMobCreator();
    }

    public void removeAllMobs() {
        for (Map.Entry<Dungeon, ArrayList<LivingEntity>> entry : mobs.entrySet()) {
            for (LivingEntity entity : entry.getValue()) {
                entity.remove();
            }
        }
    }

    public void spawnMobs(Dungeon dungeon) {
        switch (dungeon.getFloor()) {
            case FLOOR_1 -> floor_1(dungeon);
            case FLOOR_2 -> floor_2(dungeon);
            case FLOOR_3 -> floor_3(dungeon);
        }

    }

    public void floor_1(Dungeon dungeon) {
        ArrayList<LivingEntity> toSpawn = new ArrayList<>();

        LivingEntity lE = DungeonMobCreator.instance.createAndSpawnZombie(dungeon.getLocDungeon().clone().add(0, 2, 0), 10);

        toSpawn.add(lE);

        mobs.put(dungeon, toSpawn);
        makeExpireMobsForDungeon(dungeon, DungeonFloors.getDurationFromFloor(dungeon.getFloor()));
    }

    public void floor_2(Dungeon dungeon) {
        ArrayList<LivingEntity> toSpawn = new ArrayList<>();

        mobs.put(dungeon, toSpawn);
        makeExpireMobsForDungeon(dungeon, DungeonFloors.getDurationFromFloor(dungeon.getFloor()));
    }

    public void floor_3(Dungeon dungeon) {
        ArrayList<LivingEntity> toSpawn = new ArrayList<>();

        mobs.put(dungeon, toSpawn);
        makeExpireMobsForDungeon(dungeon, DungeonFloors.getDurationFromFloor(dungeon.getFloor()));
    }

    public void makeExpireMobsForDungeon(Dungeon dungeon, int minutes) {
        Bukkit.getScheduler().scheduleAsyncDelayedTask(Main.instance, () -> Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, () -> {
            if (!mobs.containsKey(dungeon)) {
                return;
            }
            ArrayList<LivingEntity> livingEntities = mobs.get(dungeon);
            livingEntities.forEach(livingEntity -> livingEntity.remove());
            mobs.remove(dungeon);
        }, 0), 20L * 60L * (long) minutes);
    }
}
