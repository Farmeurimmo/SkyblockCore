package main.java.fr.verymc.hub.events;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class SwitchWorld implements Listener {

    @EventHandler
    public void SwitchWorldInServer(PlayerChangedWorldEvent e) {
        Player player = e.getPlayer();
        if (player.getWorld().getName().equalsIgnoreCase("world")) {
            if (player.getAllowFlight() == true) {
                if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) return;
                player.setAllowFlight(false);
                player.setFlying(false);
                player.sendActionBar("§aFly désactivé");
            }
        }
        if (player.getWorld().getName().equalsIgnoreCase("IlesSkyblock")) {
            if (player.hasPermission("fly")) {
                if (player.getAllowFlight() == false) {
                    player.setAllowFlight(true);
                    player.setFlying(true);
                    player.sendActionBar("§aFly activé");
                }
            }
        }
    }

}
