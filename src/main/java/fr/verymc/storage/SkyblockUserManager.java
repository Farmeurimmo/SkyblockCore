package main.java.fr.verymc.storage;

import main.java.fr.verymc.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class SkyblockUserManager {

    public static SkyblockUserManager instance;
    public ArrayList<SkyblockUser> users = new ArrayList<>();

    public SkyblockUserManager() {
        instance = this;
    }

    public void addUser(SkyblockUser user) {
        users.add(user);
    }

    public void removeUser(SkyblockUser user) {
        users.remove(user);
    }

    public SkyblockUser getUser(UUID uuid) {
        for (SkyblockUser user : users) {
            if (user.getUserUUID().equals(uuid)) {
                return user;
            }
        }
        return null;
    }

    public void checkForAccount(Player player) {
        SkyblockUser skyblockUser = SkyblockUserManager.instance.getUser(player.getUniqueId());
        if (skyblockUser == null) {
            skyblockUser = new SkyblockUser(player.getName(), player.getUniqueId(), 200.0, false, false, false,
                    false, false, false, 0, false);
            SkyblockUserManager.instance.addUser(skyblockUser);
        }
    }

}
