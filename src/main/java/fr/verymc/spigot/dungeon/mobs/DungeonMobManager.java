package main.java.fr.verymc.spigot.dungeon.mobs;

import main.java.fr.verymc.spigot.Main;
import main.java.fr.verymc.spigot.dungeon.Dungeon;
import main.java.fr.verymc.spigot.dungeon.DungeonFloors;
import main.java.fr.verymc.spigot.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

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

    public void startDungeon(Dungeon dungeon) {
        switch (dungeon.getFloor()) {
            case ZOMBIE_1 -> floor_1(dungeon);
            case SKELETTE_2 -> floor_2(dungeon);
            case ARAIGNEE_3 -> floor_3(dungeon);
        }

    }

    public void floor_1(Dungeon dungeon) {
        ArrayList<LivingEntity> toSpawn = new ArrayList<>();

        for (DungeonFloor1 dungeonFloor1 : DungeonFloor1.values()) {
            if (dungeonFloor1.equals(DungeonFloor1.PLAYER)) {
                Location temp = dungeonFloor1.getLocations().get(0).clone();
                temp = PlayerUtils.instance.toCenterOf(dungeon.getLocDungeon(), temp).add(0, 2, 0);
                temp.setDirection(BlockFace.NORTH.getDirection());
                for (Player player : dungeon.getPlayers()) {
                    player.teleportAsync(temp);
                }
                continue;
            }
            int level = Integer.parseInt(dungeonFloor1.toString().replaceAll("[^\\d.]", ""));
            for (Location location : dungeonFloor1.getLocations()) {
                Location temp = location.clone();
                temp = PlayerUtils.instance.toCenterOf(dungeon.getLocDungeon(), temp).clone();
                temp.add(0, 2, 0);
                toSpawn.add(DungeonMobCreator.instance.spawnZombie(temp, level));
            }
        }

        for (LivingEntity livingEntity : toSpawn) {
            System.out.println("livingEntity: " + livingEntity.isDead() + " " + livingEntity.getChunk().isLoaded());
        }

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
