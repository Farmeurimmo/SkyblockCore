package main.java.fr.verymc.spigot.dungeon.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class DungeonEntityListener implements Listener {

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent e) {
        if (!e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)) {
            e.setCancelled(true);
            return;
        }
    }
}
