package main.java.fr.verymc.spigot.core.spawners;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import main.java.fr.verymc.spigot.Main;
import main.java.fr.verymc.spigot.core.holos.HoloManager;
import main.java.fr.verymc.spigot.island.Island;
import main.java.fr.verymc.spigot.island.IslandManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.text.NumberFormat;

public class SpawnersManager {

    public static SpawnersManager instance;

    public SpawnersManager() {
        instance = this;

        checkForSpawnersHolo();
    }

    public void respawnAllSpawners() {
        for (Island island : IslandManager.instance.islands) {
            for (Spawner spawner : island.getSpawners()) {
                placeSpawner(spawner, island, true);
            }
        }
    }

    public void giveSpawner(Player player, EntityType entityType, int amount) {
        ItemStack item = new ItemStack(Material.SPAWNER, amount);
        item.setDisplayName("§fGénérateur de §e" + entityType.toString());
        item.setUnbreakable(true);
        player.getInventory().addItem(item);
    }

    public Spawner getSpawner(Location loc) {
        Island island = IslandManager.instance.getIslandByLoc(loc);
        if (island == null) return null;
        for (Spawner spawner : island.getSpawners()) {
            if (spawner.getLoc().equals(loc.getBlock().getLocation())) {
                return spawner;
            }
        }
        return null;
    }

    public void placeSpawner(Spawner spawner, Island island, boolean startup) {
        updateSpawner(spawner);
        if (!startup)
            island.addSpawner(spawner);
    }

    public void updateSpawner(Spawner spawner) {
        Block block = spawner.getLoc().getBlock();
        block.getChunk().load(false);
        if (block.getType() != Material.SPAWNER) {
            block.setType(Material.SPAWNER);
        }
        CreatureSpawner creatureSpawner = (CreatureSpawner) block.getState();
        creatureSpawner.setSpawnedType(spawner.getEntityType());
        creatureSpawner.update();

        Location loc = spawner.getLoc().clone().add(0.5, 1.5, 0.5);
        Hologram holo = HoloManager.instance.getHoloAtLoc(loc);
        if (holo == null) {
            holo = HologramsAPI.createHologram(Main.instance, loc);
            HoloManager.instance.addHolo(holo);
        }
        holo.clearLines();
        holo.appendTextLine("§6x" + NumberFormat.getInstance().format(spawner.getAmount()) + " §f" + spawner.getEntityType().toString());
    }

    public void destroySpawner(Spawner spawner, Island island, boolean del) {
        Block block = spawner.getLoc().getBlock();
        if (!del)
            island.removeSpawner(spawner);
        block.setType(Material.AIR);
        removeHolo(spawner);
    }

    public void removeHolo(Spawner spawner) {
        Hologram hologram = HoloManager.instance.getHoloAtLoc(spawner.getLoc().clone().add(0.5, 1.4, 0.5));
        if (hologram != null) {
            HoloManager.instance.removeHolo(hologram);
        }
    }

    public void checkForSpawnersHolo() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.instance, () -> {
            for (Island island : IslandManager.instance.islands) {
                for (Spawner spawner : island.getSpawners()) {
                    updateSpawner(spawner);
                }
            }
        }, 0, 3 * 20L);
    }
}
