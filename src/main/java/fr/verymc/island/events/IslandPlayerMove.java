package main.java.fr.verymc.island.events;

import main.java.fr.verymc.island.Island;
import main.java.fr.verymc.island.IslandManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class IslandPlayerMove implements Listener {

    @EventHandler
    public void playerTeleport(PlayerTeleportEvent e) {
        Island island = IslandManager.instance.getIslandByLoc(e.getTo());
        if (island != null) {
            if (!island.getMembers().containsKey(e.getPlayer().getUniqueId())) {
                if (IslandManager.instance.bypasser.contains(e.getPlayer().getUniqueId())) {
                    e.setCancelled(false);
                    return;
                }
                if (!IslandManager.instance.getIslandByLoc(e.getTo()).isPublic()) {
                    e.getPlayer().sendMessage("§6§lIles §8» §cVous n'avez pas accès à cette île car elle privée.");
                    e.setCancelled(true);
                    return;
                }
                if (IslandManager.instance.getIslandByLoc(e.getTo()).isBanned(e.getPlayer().getUniqueId())) {
                    e.getPlayer().sendMessage("§6§lIles §8» §cVous n'avez pas accès à cette île car vous êtes banni.");
                    e.setCancelled(true);
                    return;
                }
            }
        }
    }

    @EventHandler
    public void playerTeleportNoCancelled(PlayerTeleportEvent e) {
        if (!e.isCancelled()) {
            IslandManager.instance.setWorldBorder(e.getPlayer(), e.getTo());
        }

    }
}
