package main.java.fr.verymc.island.protections;

import main.java.fr.verymc.island.Island;
import main.java.fr.verymc.island.IslandManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.weather.LightningStrikeEvent;

public class BlockListener implements Listener {

    @EventHandler
    public void fireEvent(BlockBurnEvent e) {
        Island island = IslandManager.instance.getIslandByLoc(e.getBlock().getLocation());
        if (island != null) {
            if (!island.hasSettingActivated(IslandSettings.BLOCK_BURNING)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void lightning(LightningStrikeEvent e) {
        Island island = IslandManager.instance.getIslandByLoc(e.getLightning().getLocation());
        if (island != null) {
            if (!island.hasSettingActivated(IslandSettings.LIGHTNING_STRIKE)) {
                e.setCancelled(true);
            }
        }
    }
}
