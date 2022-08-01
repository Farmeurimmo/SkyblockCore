package main.java.fr.verymc.spigot.core.events;

import main.java.fr.verymc.commons.enums.ServerType;
import main.java.fr.verymc.spigot.core.cmd.base.SpawnCmd;
import main.java.fr.verymc.spigot.utils.PlayerUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPortalEvent;

public class ServerCoreMicellanous implements Listener {

    @EventHandler
    public void portal(PlayerPortalEvent e) {
        e.setCancelled(true);
        e.getPlayer().sendMessage("§6§lIles §8» §cLe nether est désactivé.");
    }

    @EventHandler
    public void playerMove(PlayerMoveEvent e) {
        if (e.getTo().getY() < -1) {
            PlayerUtils.instance.teleportPlayerFromRequest(e.getPlayer(), SpawnCmd.Spawn, 0, ServerType.SKYBLOCK_HUB);
            e.setCancelled(true);
            return;
        }
    }

}
