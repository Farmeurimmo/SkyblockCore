package main.java.fr.verymc.spigot.core.storage;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashMap;
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

    public Collection<SkyblockUser> getUsers() {
        return users.values();
    }

    public void addUser(SkyblockUser user) {
        users.put(user.getUserUUID(), user);
    }

    public void removeUser(SkyblockUser user) {
        users.remove(user.getUserUUID());
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
        SkyblockUser skyblockUser = (users.get(player.getUniqueId()) != null) ? users.get(player.getUniqueId()) : StorageManager.instance.getUser(player.getUniqueId());
        if (skyblockUser == null) {
            skyblockUser = new SkyblockUser(player.getName(), player.getUniqueId(), 200.0, false, false, false,
                    false, false, false, 0, false, false, 0, null, 0.0, 0.0);
            SkyblockUserManager.instance.addUser(skyblockUser);
            StorageManager.instance.createUser(skyblockUser);
            return;
        }
        users.replace(skyblockUser.getUserUUID(), skyblockUser);
    }

}
