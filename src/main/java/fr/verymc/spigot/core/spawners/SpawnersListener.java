package main.java.fr.verymc.spigot.core.spawners;

import com.destroystokyo.paper.event.block.BlockDestroyEvent;
import main.java.fr.verymc.spigot.core.mobstacker.MobStackerManager;
import main.java.fr.verymc.spigot.island.Island;
import main.java.fr.verymc.spigot.island.IslandManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.SpawnerSpawnEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class SpawnersListener implements Listener {

    @EventHandler
    public void blockPlaceEvent(BlockPlaceEvent e) {
        ItemStack item = e.getItemInHand();

        if (e.isCancelled()) return;

        if (item.getType() != Material.SPAWNER) return;
        e.setCancelled(true);

        Island island = IslandManager.instance.getIslandByLoc(e.getBlockPlaced().getLocation());
        if (island == null)
            return;

        EntityType entityType = EntityType.valueOf(item.getItemMeta().getDisplayName().replace("§fGénérateur de §e", ""));

        if (e.getBlockAgainst().getType() == Material.SPAWNER) {
            Spawner spawner = SpawnersManager.instance.getSpawner(e.getBlockAgainst().getLocation());
            if (spawner == null) return;
            if (spawner.getEntityType() == entityType) {
                ItemStack temp = item.clone();
                temp.setAmount(item.getAmount() - 1);
                Objects.requireNonNull(e.getPlayer().getEquipment()).setItem(e.getHand(), temp);
                spawner.incrementAmount();
                SpawnersManager.instance.updateSpawner(spawner);
                return;
            }
        }

        Spawner spaw = SpawnersManager.instance.getSpawner(e.getBlockPlaced().getLocation());
        if (spaw != null) return;

        ItemStack temp = item.clone();
        temp.setAmount(item.getAmount() - 1);
        e.getPlayer().getEquipment().setItem(e.getHand(), temp);

        Spawner spawner = new Spawner(e.getBlock().getLocation().getBlock().getLocation(), 1, entityType);
        SpawnersManager.instance.placeSpawner(spawner, island, false);
    }

    @EventHandler
    public void spawnEvent(SpawnerSpawnEvent e) {
        if (e.isCancelled()) return;
        Spawner spawner = SpawnersManager.instance.getSpawner(e.getSpawner().getLocation());
        if (spawner == null) return;
        int spawnCount = spawner.getAmount() / 2;
        MobStackerManager.instance.spawnMobStacked(spawner.getEntityType(), e.getLocation(), (spawnCount <= 0 ? 1 : spawnCount));
        e.setCancelled(true);
    }

    @EventHandler
    public void blockDestroyEvent(BlockDestroyEvent e) {
        if (e.getBlock().getType() != Material.SPAWNER) return;
        e.setCancelled(true);
    }

    @EventHandler
    public void blockExplodeEvent(BlockExplodeEvent e) {
        e.blockList().removeIf(b -> b.getType() == Material.SPAWNER);
    }

    @EventHandler
    public void entityExplodeEvent(EntityExplodeEvent e) {
        e.blockList().removeIf(b -> b.getType() == Material.SPAWNER);
    }

    @EventHandler
    public void interactAtSpawner(PlayerInteractEvent e) {
        if (e.isCancelled()) return;
        Block block = e.getClickedBlock();
        if (block == null) return;
        if (e.getItem() == null) return;
        if (e.getItem().getType() == Material.SPAWNER) return;
        if (block.getType() == Material.SPAWNER) e.setCancelled(true);
    }

    @EventHandler
    public void blockBreakEvent(BlockBreakEvent e) {
        if (e.isCancelled()) return;
        if (e.getBlock().getType() != Material.SPAWNER) return;
        Block block = e.getBlock();
        Player player = e.getPlayer();
        Spawner spawner = SpawnersManager.instance.getSpawner(block.getLocation());
        if (spawner == null) return;
        e.setCancelled(true);
        Island island = IslandManager.instance.getIslandByLoc(block.getLocation());
        if (island == null) {
            return;
        }
        if (spawner.getAmount() > 1) {
            SpawnersManager.instance.giveSpawner(player, spawner.getEntityType(), 1);
            spawner.setAmount(spawner.getAmount() - 1);
            SpawnersManager.instance.updateSpawner(spawner);
            return;
        }
        SpawnersManager.instance.destroySpawner(spawner, island, false);
        SpawnersManager.instance.giveSpawner(player, spawner.getEntityType(), 1);
    }
}
