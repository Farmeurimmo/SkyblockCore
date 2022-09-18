package main.java.fr.verymc.spigot.core.storage;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SkyblockUserManager {

    public static SkyblockUserManager instance;
    public HashMap<UUID, SkyblockUser> users = new HashMap<>();

    public SkyblockUserManager() {
        instance = this;
        for (Player player : Bukkit.getOnlinePlayers()) {
            checkForAccount(player);
        }
    }

    public List<SkyblockUser> getUsers() {
        return users.values().stream().toList();
    }

    public void addUser(SkyblockUser user) {
        users.put(user.getUserUUID(), user);
    }

    public void removeUser(SkyblockUser user) {
        users.remove(user.getUserUUID());
    }

    public void fetchUser(Player player) {
        SkyblockUser skyblockUser = getUser(player.getUniqueId());
        if (users.containsKey(skyblockUser)) {
            users.replace(player.getUniqueId(), StorageManager.instance.getUser(player.getUniqueId()));
        }
    }

    public SkyblockUser getUser(UUID uuid) {
        for (Map.Entry<UUID, SkyblockUser> entry : users.entrySet()) {
            if (entry.getKey().equals(uuid)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public SkyblockUser getUser(Player player) {
        checkForAccount(player);
        return getUser(player.getUniqueId());
    }

    public void checkForAccount(Player player) {
        SkyblockUser skyblockUser = StorageManager.instance.getUser(player.getUniqueId());
        if (skyblockUser == null) {
            if (users.containsKey(player.getUniqueId())) {
                return;
            }
            System.out.println("user null");
            skyblockUser = new SkyblockUser(player.getName(), player.getUniqueId(), 200.0, false, false, false,
                    false, false, false, 0, false, false, 0, null, 0.0, 0.0);
            SkyblockUserManager.instance.addUser(skyblockUser);
            StorageManager.instance.createUser(skyblockUser);
            return;
        }
        users.replace(skyblockUser.getUserUUID(), skyblockUser);
    }

}
