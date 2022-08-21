package main.java.fr.verymc.spigot.dungeon.events;

import main.java.fr.verymc.spigot.dungeon.DungeonManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class DungeonPlayerListener implements Listener {

    @EventHandler
    public void moveEvent(PlayerMoveEvent e) {
        if (DungeonManager.instance.blocked.contains(e.getPlayer().getUniqueId())) {
            e.setCancelled(true);
        }
    }
}
