package main.java.fr.verymc.playerwarps;

import main.java.fr.verymc.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class PlayerWarpManager {

    public static PlayerWarpManager instance;
    public ArrayList<PlayerWarp> playerWarps = new ArrayList<PlayerWarp>();

    public HashMap<Integer, PlayerWarp> posBrowser = new HashMap<Integer, PlayerWarp>();

    public PlayerWarpManager() {
        instance = this;

        new PlayerWarpBrowserGui();
        new PlayerWarpManagingGui();
        new PlayerWarpNotationGui();

        regenPosBrowser();
    }

    public void regenPosBrowser() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.instance, new Runnable() {
            @Override
            public void run() {
                posBrowser.clear();
                for (int i = 0; i < playerWarps.size(); i++) {
                    posBrowser.put(i + 1, playerWarps.get(i));
                }
            }
        }, 0, 40);
    }

    public PlayerWarp getPlayerWarpFromUUID(UUID uuid) {
        for (PlayerWarp playerWarp : playerWarps) {
            if (playerWarp.getOwnerUUID().equals(uuid)) {
                return playerWarp;
            }
        }
        return null;
    }

    public PlayerWarp getPlayerWarpFromPlayerName(String name) {
        for (PlayerWarp playerWarp : playerWarps) {
            if (playerWarp.getOwner().equals(name)) {
                return playerWarp;
            }
        }
        return null;
    }

    public void addWarp(PlayerWarp playerWarp) {
        for (PlayerWarp playerWarp1 : playerWarps) {
            if (playerWarp1.getOwnerUUID().equals(playerWarp.getOwnerUUID())) {
                playerWarps.remove(playerWarp1);
                break;
            }
        }
        playerWarps.add(playerWarp);
    }

    public void deleteWarp(Player player) {
        playerWarps.remove(getPlayerWarpFromUUID(player.getUniqueId()));
    }
}
