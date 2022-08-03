package main.java.fr.verymc.spigot.core.events;

import main.java.fr.verymc.commons.enums.ServerType;
import main.java.fr.verymc.spigot.core.InventorySyncManager;
import main.java.fr.verymc.spigot.core.cmd.base.SpawnCmd;
import main.java.fr.verymc.spigot.utils.PlayerUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
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
        if (InventorySyncManager.instance.isSync(e.getPlayer())) {
            e.setCancelled(true);
            InventorySyncManager.instance.sendReasonForEventCancelled(e.getPlayer());
            return;
        }
        if (e.getTo().getY() < -1) {
            PlayerUtils.instance.teleportPlayerFromRequest(e.getPlayer(), SpawnCmd.Spawn, 0, ServerType.SKYBLOCK_HUB);
            e.setCancelled(true);
            return;
        }
    }

    @EventHandler
    public void interact(PlayerInteractEvent e) {
        if (InventorySyncManager.instance.isSync(e.getPlayer())) {
            e.setCancelled(true);
            InventorySyncManager.instance.sendReasonForEventCancelled(e.getPlayer());
        }
    }

    @EventHandler
    public void clickInv(InventoryClickEvent e) {
        if (InventorySyncManager.instance.isSync((Player) e.getWhoClicked())) {
            e.setCancelled(true);
            InventorySyncManager.instance.sendReasonForEventCancelled((Player) e.getWhoClicked());
        }
    }

    @EventHandler
    public void drop(PlayerDropItemEvent e) {
        if (InventorySyncManager.instance.isSync(e.getPlayer())) {
            e.setCancelled(true);
            InventorySyncManager.instance.sendReasonForEventCancelled(e.getPlayer());
        }
    }

}
