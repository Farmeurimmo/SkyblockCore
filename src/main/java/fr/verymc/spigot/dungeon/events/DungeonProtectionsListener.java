package main.java.fr.verymc.spigot.dungeon.events;

import com.destroystokyo.paper.event.block.BlockDestroyEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.weather.LightningStrikeEvent;

public class DungeonProtectionsListener implements Listener {

    @EventHandler
    public void interact(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.PHYSICAL) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void placeEvent(BlockPlaceEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void breakEvent(BlockBreakEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void destroyEvent(BlockDestroyEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void fireBlock(BlockBurnEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void lightning(LightningStrikeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void burn(BlockBurnEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void shear(BlockShearEntityEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void blockDamage(BlockDamageEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void explosionEntity(EntityExplodeEvent e) {
        e.blockList().clear();
    }

    @EventHandler
    public void explosionBlock(BlockExplodeEvent e) {
        e.blockList().clear();
    }
}
