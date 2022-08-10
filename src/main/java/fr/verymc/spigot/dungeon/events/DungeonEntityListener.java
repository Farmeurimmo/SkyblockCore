package main.java.fr.verymc.spigot.dungeon.events;

import main.java.fr.verymc.spigot.dungeon.Dungeon;
import main.java.fr.verymc.spigot.dungeon.DungeonManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class DungeonEntityListener implements Listener {

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent e) {
        if (!e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)) {
            e.setCancelled(true);
            return;
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        if (e.getEntity().hasMetadata("boss")) {
            Dungeon dungeon = DungeonManager.instance.getDungeonByLoc(e.getEntity().getLocation());
            if (dungeon == null) return;
            DungeonManager.instance.makeDungeonEnd(dungeon, false);
        }
    }
}
