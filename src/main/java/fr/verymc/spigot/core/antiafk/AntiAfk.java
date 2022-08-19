package main.java.fr.verymc.spigot.core.antiafk;

import main.java.fr.verymc.commons.enums.ServerType;
import main.java.fr.verymc.spigot.Main;
import main.java.fr.verymc.spigot.core.cmd.base.SpawnCmd;
import main.java.fr.verymc.spigot.core.gui.AfkMineCaptchaGui;
import main.java.fr.verymc.spigot.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AntiAfk implements Listener {

    public Map<Player, Vector> playerLastDirection = new HashMap<>();
    public Map<Player, Integer> playerCountTimesAfkAsAResult = new HashMap<>();

    public HashMap<UUID, Vector> locationHashMap = new HashMap<>();
    public HashMap<UUID, Integer> countHashMap = new HashMap<>();

    public AntiAfk() {
        if (Main.instance.serverType != ServerType.SKYBLOCK_HUB) checkForAfk();
    }

    public void checkForAfk() {
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(Main.instance, new Runnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    Vector v = p.getLocation().getDirection();
                    if (locationHashMap.containsKey(p.getUniqueId())) {
                        if (locationHashMap.get(p.getUniqueId()).equals(v)) {
                            if (countHashMap.containsKey(p.getUniqueId())) {
                                countHashMap.put(p.getUniqueId(), countHashMap.get(p.getUniqueId()) + 1);
                            } else {
                                countHashMap.put(p.getUniqueId(), 1);
                            }
                        } else {
                            countHashMap.put(p.getUniqueId(), 1);
                        }
                    }
                    locationHashMap.put(p.getUniqueId(), v);
                }
                for (Map.Entry<UUID, Integer> entry : countHashMap.entrySet()) {
                    if (entry.getValue() >= 60 * 15) {
                        Player p = Bukkit.getPlayer(entry.getKey());
                        if (p != null) {
                            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
                                @Override
                                public void run() {
                                    PlayerUtils.instance.teleportPlayerFromRequest(p, SpawnCmd.Spawn, 0, ServerType.SKYBLOCK_HUB);
                                }
                            }, 0);
                            countHashMap.remove(entry.getKey());
                            break;
                        }
                    }
                    continue;
                }
            }
        }, 20, 20);
    }

    @EventHandler
    public void onPlayerInterractEvent(PlayerInteractEvent e) {

        Player p = e.getPlayer();

        Vector pDirection = p.getLocation().getDirection();

        if (!playerLastDirection.containsKey(p)) {
            playerCountTimesAfkAsAResult.put(p, 0);
            playerLastDirection.put(p, pDirection);
        }

        if (playerLastDirection.get(p).equals(pDirection)) {


            playerCountTimesAfkAsAResult.put(p, playerCountTimesAfkAsAResult.get(p) + 1);

            int result = playerCountTimesAfkAsAResult.get(p);

            if (result == 160) {

                p.sendTitle("§CAfk-Farm détecté !", "§fMerci de déplacer votre curseur.");
                p.sendMessage("§6§lAntiAFK §8» §fAttention, nous avons détecté que tu étais peut être en afk farm, si tu ne l'es pas merci de bouger ton curseur de temps en temps.");

            } else if (result == 200) {

                p.sendMessage("§6§lAntiAFK §8» §fAttention, dernier avertissement, merci de bouger ton curseur.");
                p.sendTitle("§CAfk-Farm détecté !", "§fMerci de déplacer votre curseur.");

            } else if (result == 230) {

                AfkMineCaptchaGui.MakeAfkMineCaptchaGui(p);
                playerCountTimesAfkAsAResult.put(p, 0);
            }

        } else {
            playerCountTimesAfkAsAResult.put(p, 0);

        }
        playerLastDirection.put(p, pDirection);


    }


}

