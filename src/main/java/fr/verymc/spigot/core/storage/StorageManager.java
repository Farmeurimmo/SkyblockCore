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

    public StorageManager() {
        instance = this;

        skyblockUserCheckForUpdate();
    }

    public void createIsland(Island island) {
    }

    public Island getIsland(UUID uuid) {
        return null;
    }

    public void updateIsland(Island island, StoragePriorities priority) {
    }

    public void forceUpdateIsland(Island island) {
    }

    public void deleteIsland(UUID uuid) {
    }


    public void createUser(SkyblockUser skyblockUser) {
    }

    public SkyblockUser getUser(UUID uuid) {
        return null;
    }

    public void updateUser(SkyblockUser skyblockUser) {
    }

    public void deleteUser(SkyblockUser skyblockUser) {
    }


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
        } else {
            usersQueued.put(skyblockUser, priority.getTicks());
        }
    }

    public void skyblockUserCheckForUpdate() {
        HashMap<SkyblockUser, Integer> usersToUpdate = new HashMap<>(usersQueued);
        for (Map.Entry<SkyblockUser, Integer> entry : usersQueued.entrySet()) {
            if (entry.getValue() <= 0) {
                updateUser(entry.getKey());
                usersToUpdate.remove(entry.getKey());
            } else {
                usersQueued.replace(entry.getKey(), entry.getValue() - INTERVAL_TICKS);
            }
        }

        usersQueued = usersToUpdate;

        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, this::skyblockUserCheckForUpdate, INTERVAL_TICKS);
    }

}
