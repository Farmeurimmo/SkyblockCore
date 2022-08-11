package main.java.fr.verymc.spigot.dungeon;

import main.java.fr.verymc.JedisManager;
import main.java.fr.verymc.commons.enums.ServerType;
import main.java.fr.verymc.spigot.Main;
import main.java.fr.verymc.spigot.core.cmd.base.SpawnCmd;
import main.java.fr.verymc.spigot.dungeon.mobs.DungeonMobManager;
import main.java.fr.verymc.spigot.utils.FAWEUtils;
import main.java.fr.verymc.spigot.utils.ObjectConverter;
import main.java.fr.verymc.spigot.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class DungeonManager {

    public static final int distanceBetweenDungeons = 400;
    public static DungeonManager instance;
    public ArrayList<Dungeon> dungeons = new ArrayList<>();

    public DungeonManager() {
        instance = this;
        ;
        new DungeonBossBarManager();
        new DungeonMobManager();

        JedisManager.instance.sendToRedis("dungeonsReady", Main.instance.serverName); //use json after
        //this is temporary, we will use redis with list later


        //START WHEN AN SPECIFIC DUNGEON IS REQUESTED AND THERE IS ENOUGH PLAYERS
        //loadDungeons();
    }

    public void makeAllDungeonsEnd() {
        ArrayList<Dungeon> list = dungeons;
        list.clone();
        for (Dungeon dungeon : list) {
            makeDungeonEnd(dungeon, true);
        }
    }

    public void checkIfAllPlayersAreDead(Dungeon dungeon) {
        if (dungeon.getDeadPlayers().size() >= dungeon.getPlayers().size()) {
            makeDungeonEnd(dungeon, true);
        }
    }

    public void loadDungeon(List<String> players, DungeonFloors floor) {
        Location loc = getEmptyLocation();

        ArrayList<Player> players1 = new ArrayList<>();
        players.forEach(player -> players1.add(Bukkit.getPlayer(player.substring(1, player.length() - 1))));

        for (Player player : players1) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 999, 10));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 999, 10));
            player.sendTitle("§fPréparation du dungeon", "§fVeuillez patienter...", 0, 999, 0);
            player.teleport(new Location(Main.instance.mainWorld, 0, 256, 0));
            player.setAllowFlight(true);
            player.setFlying(true);
        }

        Dungeon dungeon = new Dungeon(floor.getName(), floor, players1, loc, DungeonFloors.getDurationFromFloor(floor));
        dungeons.add(dungeon);

        for (File file : Main.instance.getDataFolder().listFiles()) {
            if (file.getName().contains(floor.toString())) {
                FAWEUtils.instance.pasteSchem(file, dungeon.getLocDungeon());
            }
        }

        FAWEUtils.instance.awaiting.add(dungeon);
    }

    public void secondPartLoad(Dungeon dungeon) {
        DungeonMobManager.instance.spawnMobs(dungeon);
        Location teleportLoc = dungeon.getLocDungeon().clone();
        teleportLoc.add(0, 3, 0);
        for (Player player : dungeon.getPlayers()) {
            if (player.hasPotionEffect(PotionEffectType.BLINDNESS)) {
                player.removePotionEffect(PotionEffectType.BLINDNESS);
                player.removePotionEffect(PotionEffectType.SLOW);
                player.clearTitle();
                player.teleport(teleportLoc);
                player.setFlying(false);
                player.setAllowFlight(false);
            }
        }

        dungeon.setTime_of_start(System.currentTimeMillis());


        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, () -> makeDungeonEnd(dungeon, true), 20 * 60 * 10);
    }

    public void makeDungeonEnd(Dungeon dungeon, boolean force) {
        if (!dungeons.contains(dungeon)) {
            return;
        }
        dungeons.remove(dungeon);
        for (LivingEntity livingEntity : DungeonMobManager.instance.mobs.get(dungeon)) {
            livingEntity.remove();
        }
        long duration = System.currentTimeMillis() - dungeon.getTime_of_start();
        for (Player player : dungeon.getPlayers()) {
            if (force) {
                player.sendMessage("§6§lDungeon §l» §cVous n'avez pas terminé le dungeon !");
            } else {
                player.sendTitle("§aLe boss est mort", "§aEn " + TimeUnit.MILLISECONDS.toSeconds(duration) + " secondes");
                player.sendMessage("§6§lDungeon §l» §aVous avez terminé le dungeon en " + TimeUnit.MILLISECONDS.toSeconds(duration) + " secondes");
            }
            PlayerUtils.instance.teleportPlayerFromRequest(player, SpawnCmd.Spawn, 0, ServerType.SKYBLOCK_HUB);
        }

    }

    public void checkForFullTeam(List<String> players, DungeonFloors floor, int tries) {
        AtomicInteger count = new AtomicInteger(tries);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, () -> {
            int size = 0;
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (players.contains(player.getName())) {
                    size++;
                }
            }
            if (size >= players.size()) {
                loadDungeon(players, floor);
            }
            count.getAndIncrement();
            if (count.get() > 10) {
                loadDungeon(players, floor);
                return;
            }
            checkForFullTeam(players, floor, count.get());
        }, 2);
    }

    public void playerLogged(Player player) {
        String redis = JedisManager.instance.getFromRedis("tmpDungeonTeam");
        if (redis == null) return;
        if (redis.contains(player.getName())) {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = null;
            try {
                jsonObject = (JSONObject) jsonParser.parse(redis);
            } catch (Exception ex) {
                ex.printStackTrace();
                return;
            }
            List<String> list = ObjectConverter.instance.stringToArrayList(String.valueOf(jsonObject.get("players")));
            DungeonFloors floor = DungeonFloors.valueOf(String.valueOf(jsonObject.get("floor")));
            JedisManager.instance.removeFromRedis("tmpDungeonTeam");
            checkForFullTeam(list, floor, 0);
        }
    }

    public Location getEmptyLocation() {
        Location toReturn = null;

        if (dungeons.size() == 0) {
            toReturn = new Location(Main.instance.mainWorld, 0, 80, 0);
        } else {
            int minx = 0;
            int minz = 0;
            int maxx = 0;
            int maxz = 0;
            for (Dungeon i : dungeons) {
                if (minx > i.getLocDungeon().getBlockX()) {
                    minx = i.getLocDungeon().getBlockX();
                }
                if (minz > i.getLocDungeon().getBlockZ()) {
                    minz = i.getLocDungeon().getBlockZ();
                }
                if (maxx < i.getLocDungeon().getBlockX()) {
                    maxx = i.getLocDungeon().getBlockX();
                }
                if (maxz < i.getLocDungeon().getBlockZ()) {
                    maxz = i.getLocDungeon().getBlockZ();
                }
            }
            Random rand = new Random();

            while (toReturn == null) {
                for (int i = minz; i <= maxz; i += distanceBetweenDungeons) {
                    if (toReturn != null) break;
                    if (!isADungeonByLoc(new Location(Main.instance.mainWorld, minx, 0, i))) {
                        toReturn = new Location(Main.instance.mainWorld, minx, 80, i);
                    }
                    for (int j = minx; j <= maxx; j += distanceBetweenDungeons) {
                        if (!isADungeonByLoc(new Location(Main.instance.mainWorld, j, 0, i))) {
                            toReturn = new Location(Main.instance.mainWorld, j, 80, i);
                        }
                    }
                }
                if (toReturn != null) {
                    break;
                }
                int toSumx = 0;
                int toSumz = 0;
                int randint = rand.nextInt(4);
                if (randint == 0) {
                    toSumx = distanceBetweenDungeons;
                } else if (randint == 1) {
                    toSumx = -distanceBetweenDungeons;
                } else if (randint == 2) {
                    toSumz = distanceBetweenDungeons;
                } else if (randint == 3) {
                    toSumz = -distanceBetweenDungeons;
                }
                Location tmp = new Location(Main.instance.mainWorld, maxx + toSumx, 80, minz + toSumz);
                if (!isADungeonByLoc(tmp)) {
                    toReturn = tmp;
                    break;
                }

            }
        }
        return toReturn;
    }

    public Dungeon getDungeonByLoc(Location loc) {
        for (Dungeon dungeon : dungeons) {
            if (dungeon.getLocDungeon().getX() - 300 < loc.getX() && dungeon.getLocDungeon().getX() + 300 > loc.getX() &&
                    dungeon.getLocDungeon().getZ() - 300 < loc.getZ() && dungeon.getLocDungeon().getZ() + 300 > loc.getZ()) {
                return dungeon;
            }
        }
        return null;
    }

    public boolean isADungeonByLoc(Location loc) {
        for (Dungeon i : dungeons) {
            if (i.getLocDungeon().getBlockX() == loc.getBlockX() && i.getLocDungeon().getBlockZ() == loc.getBlockZ()) {
                return true;
            }
        }
        return false;
    }

    public Dungeon getDungeonByPlayer(Player player) {
        for (Dungeon dungeon : dungeons) {
            if (dungeon.getPlayers().contains(player)) {
                return dungeon;
            }
        }
        return null;
    }
}
