package main.java.fr.verymc.spigot.core.storage;

import main.java.fr.verymc.commons.enums.ServerType;
import main.java.fr.verymc.spigot.Main;
import main.java.fr.verymc.spigot.core.spawners.Spawner;
import main.java.fr.verymc.spigot.island.Island;
import main.java.fr.verymc.spigot.island.IslandManager;
import main.java.fr.verymc.spigot.island.blocks.Chest;
import main.java.fr.verymc.spigot.island.minions.Minion;
import main.java.fr.verymc.spigot.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static main.java.fr.verymc.spigot.core.storage.SkyblockUser.skyblockUserFromJSON;
import static main.java.fr.verymc.spigot.island.Island.islandFromJSON;
import static main.java.fr.verymc.spigot.island.Island.islandToJSON;

public class StorageJSONManager {

    public static StorageJSONManager instance;
    public boolean loading = true;

    public StorageJSONManager() {
        instance = this;
        getData(true);
        loading = false;
        Bukkit.getScheduler().scheduleAsyncDelayedTask(Main.instance, new Runnable() {
            @Override
            public void run() {
                sendDataToAPIAuto(false);
            }
        }, 20 * 5);
    }

    public void getData(boolean blockThread) {
        if (blockThread) {
            getDataFromAPI();
            IslandManager.instance.pasteAndLoadIslands();
        } else {
            CompletableFuture.runAsync(() -> {
                getDataFromAPI();
            });
        }
    }

    public void getDataFromAPI() {
        //tries to fetch data from a database which doesn’t block the main thread but another thread.

        //API FETCH DATA

        ArrayList<Island> islands = new ArrayList<>();
        ArrayList<SkyblockUser> skyblockUsers = new ArrayList<>();

        for (String str : ConfigManager.instance.getDataIslands().getKeys(false)) {
            if (str == null) continue;
            Island island = islandFromJSON(ConfigManager.instance.getDataIslands().getString(str));
            if (island != null) {

                island.setCenter(IslandManager.instance.getAFreeCenter());

                island.setHome(PlayerUtils.instance.toCenterOf(island.getCenter(), island.getHome()));

                for (Chest chest : island.getChests()) {
                    chest.setBlock(PlayerUtils.instance.toCenterOf(island.getCenter(), chest.getBlock()));
                }

                for (Minion minion : island.getMinions()) {
                    minion.setBlocLocation(PlayerUtils.instance.toCenterOf(island.getCenter(), minion.getBlocLocation()));
                    if (minion.getChestBloc() != null) {
                        minion.setChestBloc(PlayerUtils.instance.toCenterOf(island.getCenter(), minion.getChestBloc().getLocation()).getBlock());
                    }
                }

                for (Spawner spawner : island.getSpawners()) {
                    spawner.setLoc(PlayerUtils.instance.toCenterOf(island.getCenter(), spawner.getLoc()));
                }

                //SEND Islands to -> IslandManager.instance.islands
                if (Main.instance.serverType == ServerType.SKYBLOCK_ISLAND) {
                    islands.add(island);
                }
            }
        }
        //DATA ISLANDS

        for (String str : ConfigManager.instance.getDataSkyblockUser().getKeys(false)) {
            if (str == null) continue;
            try {
                SkyblockUser skyblockUser = skyblockUserFromJSON((JSONObject) new JSONParser().parse(ConfigManager.instance.getDataSkyblockUser().getString(str)));
                if (skyblockUser != null) {
                    skyblockUsers.add(skyblockUser);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //DATA USERS

        IslandManager.instance.islands = islands;
        //SEND SkyblockUser to -> SkyblockUserManager.instance.users
        skyblockUsers.forEach(skyblockUser -> {
                    SkyblockUserManager.instance.users.put(skyblockUser.getUserUUID(), skyblockUser);
                }
        );
    }

    public void sendDataToAPIAuto(boolean stop) {
        long start = System.currentTimeMillis();

        if (Main.instance.serverType == ServerType.SKYBLOCK_ISLAND) {
            HashMap<String, Object> toSendIslands = new HashMap<>();
            HashMap<String, Object> toRemoveIslands = new HashMap<>(); // NEED TO NULL Island id because yaml don't support override data
            ArrayList<Island> islands = IslandManager.instance.islands;
            for (Island island : islands) {
                try {
                    toSendIslands.put(island.getUUID().toString(), islandToJSON(island).toJSONString());
                } catch (Exception e) {
                    e.printStackTrace();
                    Bukkit.broadcastMessage("§cErreur lors de la sauvegarde de l'île #" + island.getUUID().toString());
                } finally {
                    toRemoveIslands.put(island.getUUID().toString(), null);
                }
            }
            AsyncConfig.instance.setAndSaveAsyncBlockCurrentThread(toRemoveIslands, ConfigManager.instance.getDataIslands(),
                    ConfigManager.instance.islandsFile);
            AsyncConfig.instance.setAndSaveAsync(toSendIslands, ConfigManager.instance.getDataIslands(),
                    ConfigManager.instance.islandsFile);
        }

        List<SkyblockUser> skyblockUsers = SkyblockUserManager.instance.getUsers();

        HashMap<String, Object> toSendSkyUsers = new HashMap<>();
        HashMap<String, Object> toRemoveSkyUsers = new HashMap<>();
        for (SkyblockUser skyblockUser : skyblockUsers) {
            try {
                toSendSkyUsers.put(skyblockUser.getUserUUID().toString(), SkyblockUser.skyblockUserToJSON(skyblockUser).toJSONString());
            } catch (Exception e) {
                e.printStackTrace();
                Bukkit.broadcastMessage("§6§lData §8§l» §cUne erreur est survenue lors de la sauvegarde des données des utilisateurs (uuid: " +
                        skyblockUser.getUserUUID().toString() + ")");
            } finally {
                toRemoveSkyUsers.put(skyblockUser.getUserUUID().toString(), null);
            }
        }
        AsyncConfig.instance.setAndSaveAsyncBlockCurrentThread(toRemoveSkyUsers, ConfigManager.instance.getDataSkyblockUser(),
                ConfigManager.instance.skyblockUserFile);
        AsyncConfig.instance.setAndSaveAsync(toSendSkyUsers, ConfigManager.instance.getDataSkyblockUser(),
                ConfigManager.instance.skyblockUserFile);


        Bukkit.broadcastMessage("§6§lData §8§l» §fMise à jour complète de la database en " + (System.currentTimeMillis()
                - start) + "ms.");

        if (stop) {
            return;
        }
        Bukkit.getScheduler().scheduleAsyncDelayedTask(Main.instance, new Runnable() {
            public void run() {
                sendDataToAPIAuto(false);
            }
        }, 20 * 60 * 5);
    }
}
