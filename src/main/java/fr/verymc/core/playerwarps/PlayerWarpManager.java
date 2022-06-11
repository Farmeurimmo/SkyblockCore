package main.java.fr.verymc.core.playerwarps;

import main.java.fr.verymc.Main;
import main.java.fr.verymc.core.storage.SkyblockUser;
import main.java.fr.verymc.core.storage.SkyblockUserManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(Main.instance, new Runnable() {
            @Override
            public void run() {
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
                    @Override
                    public void run() {
                        playerWarps.clear();
                        for (SkyblockUser user : SkyblockUserManager.instance.users) {
                            if (user.getPlayerWarp() != null) {
                                playerWarps.add(user.getPlayerWarp());
                            }
                        }
                        posBrowser.clear();
                        for (int i = 0; i < playerWarps.size(); i++) {
                            posBrowser.put(i + 1, playerWarps.get(i));
                        }
                        ArrayList<Player> toRemove = new ArrayList<Player>();
                        for (Map.Entry<Player, Integer> entry : PlayerWarpBrowserGui.instance.toAct.entrySet()) {
                            if (entry.getKey().isOnline()) {
                                if (entry.getKey().getOpenInventory() != null) {
                                    if (entry.getKey().getOpenInventory().getTitle().contains("§6Warp browser #")) {
                                        PlayerWarpBrowserGui.instance.openBrowserMenu(entry.getKey(), entry.getValue());
                                        continue;
                                    }
                                }
                            }
                            toRemove.add(entry.getKey());
                        }
                        for (Player p : toRemove) {
                            PlayerWarpBrowserGui.instance.toAct.remove(p);
                        }
                    }
                }, 0);
            }
        }, 0, 40);
    }

    public PlayerWarp getPlayerWarpFromUUID(UUID uuid) {
        for (SkyblockUser skyblockUser : SkyblockUserManager.instance.users) {
            if (skyblockUser.getUserUUID().equals(uuid)) {
                return skyblockUser.getPlayerWarp();
            }
        }
        return null;
    }

    public PlayerWarp getPlayerWarpFromPlayerName(String name) {
        for (SkyblockUser skyblockUser : SkyblockUserManager.instance.users) {
            if (skyblockUser.getUsername().equals(name)) {
                return skyblockUser.getPlayerWarp();
            }
        }
        return null;
    }

    public void addWarp(SkyblockUser user, PlayerWarp playerWarp) {
        user.setPlayerWarp(playerWarp);
    }

    public String getOwnerFromPlayerWarp(PlayerWarp playerWarp) {
        for (SkyblockUser skyblockUser : SkyblockUserManager.instance.users) {
            if (skyblockUser.getPlayerWarp() != null) {
                if (skyblockUser.getPlayerWarp().equals(playerWarp)) {
                    return skyblockUser.getUsername();
                }
            }
        }
        return null;
    }

    public UUID getOwnerUUIDFromPlayerWarp(PlayerWarp playerWarp) {
        for (SkyblockUser skyblockUser : SkyblockUserManager.instance.users) {
            if (skyblockUser.getPlayerWarp() != null) {
                if (skyblockUser.getPlayerWarp().equals(playerWarp)) {
                    return skyblockUser.getUserUUID();
                }
            }
        }
        return null;
    }

    public void deleteWarp(Player player) {
        SkyblockUserManager.instance.getUser(player.getUniqueId()).setPlayerWarp(null);
    }
}
