package main.java.fr.verymc.island.protections;

import main.java.fr.verymc.island.Island;
import main.java.fr.verymc.island.IslandManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;

public class EntityListener implements Listener {

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent e) {
        if (e.getEntityType() == EntityType.PHANTOM) {
            e.setCancelled(true);
            return;
        }
    }

    @EventHandler
    public void creatureSpawn(CreatureSpawnEvent e) {
        if (e.getEntityType() == EntityType.ARMOR_STAND) {
            return;
        }
        Island island = IslandManager.instance.getIslandByLoc(e.getLocation());
        if (island != null) {
            if (!island.hasSettingActivated(IslandSettings.MOB_SPAWNING)) {
                e.getEntity().remove();
                e.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void onSteal(EntityChangeBlockEvent e) {
        if (e.getEntity() instanceof Player) {
            return;
        }
        Island island = IslandManager.instance.getIslandByLoc(e.getBlock().getLocation());
        if (island != null) {
            if (!island.hasSettingActivated(IslandSettings.MOB_GRIEFING)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void primeExplo(ExplosionPrimeEvent e) {
        Island island = IslandManager.instance.getIslandByLoc(e.getEntity().getLocation());
        if (e.getEntity() instanceof TNTPrimed) {
            if (island != null) {
                if (!island.hasSettingActivated(IslandSettings.BLOCK_EXPLOSION)) {
                    e.setCancelled(true);
                }
            }
            return;
        }
        if (e.getEntity() instanceof Entity) {
            if (island != null) {
                if (!island.hasSettingActivated(IslandSettings.MOB_GRIEFING)) {
                    e.setCancelled(true);
                }
            }
            return;
        }
    }
}
