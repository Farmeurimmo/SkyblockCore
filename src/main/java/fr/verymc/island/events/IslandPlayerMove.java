package main.java.fr.verymc.island.events;

import main.java.fr.verymc.cmd.base.SpawnCmd;
import main.java.fr.verymc.island.IslandManager;
import main.java.fr.verymc.utils.PlayerUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class IslandPlayerMove implements Listener {

    @EventHandler
    public void playerTeleport(PlayerTeleportEvent e) {
        if (IslandManager.instance.isAnIslandByLoc(e.getTo())) {
            if (!IslandManager.instance.getIslandByLoc(e.getTo()).getMembers().containsKey(e.getPlayer().getUniqueId())) {
                if (!IslandManager.instance.getIslandByLoc(e.getTo()).isPublic()) {
                    e.getPlayer().sendMessage("§6§lIles §8» §cVous n'avez pas accès à cette île car elle privée.");
                    e.setCancelled(true);
                    return;
                }
            }
        }
    }

    @EventHandler
    public void playerMove(PlayerMoveEvent e) {
        if (e.getTo().getY() < -1) {
            PlayerUtils.TeleportPlayerFromRequest(e.getPlayer(), SpawnCmd.Spawn, 0);
            return;
        }
    }
}
