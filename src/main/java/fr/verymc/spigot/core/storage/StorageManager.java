package main.java.fr.verymc.spigot.core.storage;

import fr.verymc.api.wrapper.ApiKey;
import fr.verymc.api.wrapper.WrapperConfig;
import main.java.fr.verymc.spigot.Main;
import main.java.fr.verymc.spigot.island.Island;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StorageManager {

    public static final int INTERVAL_TICKS = 3;
    public static final int MAX_EDIT_TIMES = 10;
    private static final WrapperConfig WRAPPER_CONFIG_SKYBLOCK_USER = new WrapperConfig("games.verymc.fr/skyblock/users",
            new ApiKey("games-skyblock", ""));
    private static final WrapperConfig WRAPPER_CONFIG_SKYBLOCK_ISLANDS = new WrapperConfig("games.verymc.fr/skyblock/islands",
            new ApiKey("games-skyblock", ""));
    private static final WrapperConfig WRAPPER_CONFIG_SKYBLOCK_AUCTIONS = new WrapperConfig("games.verymc.fr/skyblock/auctions",
            new ApiKey("games-skyblock", ""));
    public static StorageManager instance;
    //private final static SkyblockUserManager MANAGER_USER = new SkyblockUserManager(WRAPPER_CONFIG);
    //private final static SKyblockIslandManager MANAGER_ISLAND = new SKyblockIslandManager(WRAPPER_CONFIG);
    //private final static SkyblockAuctionManager MANAGER_AUCTION = new SkyblockAuctionManager(WRAPPER_CONFIG);
    public HashMap<SkyblockUser, Integer> usersQueued = new HashMap<>();
    public HashMap<SkyblockUser, Integer> usersQueuedTimesEdited = new HashMap<>();
    public HashMap<Island, Integer> islandsQueued = new HashMap<>();
    public HashMap<Island, Integer> islandsQueuedTimesEdited = new HashMap<>();

    public StorageManager() {
        instance = this;

        skyblockUserCheckForUpdate();
        islandCheckForUpdate();
    }

    //ISLAND

    public void createIsland(Island island) {
    }

    public Island getIslandByUUID(UUID uuid) {
        return null;
    }

    public Island getIslandByMember(UUID uuid) {
        //if members map contains the uuid, return the island
        //(c'est pour récupérer l'île d'un joueur qu'il soit membre ou chef de l'île)
        return null;
    }

    public void updateIsland(Island island) {
    }

    public void deleteIsland(UUID uuid) {
    }


    //USER

    public void createUser(SkyblockUser skyblockUser) {
    }

    public SkyblockUser getUser(UUID uuid) {
        return null;
    }

    public void updateUser(SkyblockUser skyblockUser) {
    }

    public void deleteUser(SkyblockUser skyblockUser) {
        //PAS IMPLÉMENTÉ DANS LE PLUGIN DONC PAS NECESSAIRE
    }


    //QUEUE PART

    //ISLAND
    public void forceUpdateAllQueuedIslands() {
        for (Map.Entry<Island, Integer> entry : islandsQueued.entrySet()) {
            updateIsland(entry.getKey());
        }
        new ArrayList<>(islandsQueued.keySet()).forEach(island -> islandsQueued.remove(island));
    }

    public void startUpdateIsland(Island island, StoragePriorities priority) {
        if (priority.getTicks() == 0) {
            updateIsland(island);
            return;
        }
        if (islandsQueued.containsKey(island)) {
            islandsQueued.replace(island, priority.getTicks());
            if (islandsQueuedTimesEdited.containsKey(island)) {
                if (islandsQueuedTimesEdited.get(island) >= MAX_EDIT_TIMES) {
                    islandsQueuedTimesEdited.remove(island);
                    updateIsland(island);
                    return;
                }
                islandsQueuedTimesEdited.replace(island, islandsQueuedTimesEdited.get(island) + 1);
                return;
            }
            islandsQueuedTimesEdited.put(island, 1);
            return;
        }
        islandsQueued.put(island, priority.getTicks());
    }

    public void islandCheckForUpdate() {
        for (Map.Entry<Island, Integer> entry : islandsQueued.entrySet()) {
            if (entry.getValue() <= 0) {
                updateIsland(entry.getKey());
                islandsQueued.remove(entry.getKey());
            } else {
                islandsQueued.replace(entry.getKey(), entry.getValue() - INTERVAL_TICKS);
            }
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, this::islandCheckForUpdate, INTERVAL_TICKS);
    }


    //SKYBLOCK_USER
    public void forceUpdateAllQueuedUsers() {
        for (Map.Entry<SkyblockUser, Integer> entry : usersQueued.entrySet()) {
            updateUser(entry.getKey());
        }
        new ArrayList<>(usersQueued.keySet()).forEach(usersQueued::remove);
    }

    public void startUpdateUser(SkyblockUser skyblockUser, StoragePriorities priority) {
        if (priority.getTicks() == 0) {
            updateUser(skyblockUser);
            return;
        }
        if (usersQueued.containsKey(skyblockUser.getUserUUID())) {
            usersQueued.replace(skyblockUser, priority.getTicks());
            if (usersQueuedTimesEdited.containsKey(skyblockUser)) {
                if (usersQueuedTimesEdited.get(skyblockUser) >= MAX_EDIT_TIMES) {
                    usersQueuedTimesEdited.remove(skyblockUser);
                    updateUser(skyblockUser);
                    return;
                }
                usersQueuedTimesEdited.replace(skyblockUser, usersQueuedTimesEdited.get(skyblockUser) + 1);
                return;
            }
            usersQueuedTimesEdited.put(skyblockUser, 1);
            return;
        }
        usersQueued.put(skyblockUser, priority.getTicks());
    }

    public void skyblockUserCheckForUpdate() {
        for (Map.Entry<SkyblockUser, Integer> entry : usersQueued.entrySet()) {
            if (entry.getValue() <= 0) {
                updateUser(entry.getKey());
                usersQueued.remove(entry.getKey());
            } else {
                usersQueued.replace(entry.getKey(), entry.getValue() - INTERVAL_TICKS);
            }
        }

        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, this::skyblockUserCheckForUpdate, INTERVAL_TICKS);
    }

}
