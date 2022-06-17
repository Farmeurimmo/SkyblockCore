package main.java.fr.verymc.core.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;

public class ServerCoreMicellanous implements Listener {

    @EventHandler
    public void portal(PlayerPortalEvent e) {
        e.setCancelled(true);
        e.getPlayer().sendMessage("§6§lIles §8» §cLe nether est désactivé.");
    }

}
