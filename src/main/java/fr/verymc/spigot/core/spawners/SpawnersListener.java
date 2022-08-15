package main.java.fr.verymc.spigot.core.spawners;

import com.destroystokyo.paper.event.block.BlockDestroyEvent;
import main.java.fr.verymc.spigot.island.Island;
import main.java.fr.verymc.spigot.island.IslandManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class SpawnersListener implements Listener {

    @EventHandler
    public void blockPlaceEvent(BlockPlaceEvent e) {
        ItemStack item = e.getItemInHand();

        if (e.isCancelled()) return;

        if (item.getType() != Material.SPAWNER) return;

        Island island = IslandManager.instance.getIslandByLoc(e.getBlockPlaced().getLocation());
        if (island == null) {
            e.setCancelled(true);
            return;
        }
        //lpepekozkp

        Spawner spawner = new Spawner(e.getBlock().getLocation().getBlock().getLocation(), 1,
                EntityType.valueOf(item.getItemMeta().getDisplayName().replace("§fGénérateur de §e", "")));
        SpawnersManager.instance.placeSpawner(spawner, island);
        SpawnersManager.instance.addSpawner(spawner, island);
    }

    @EventHandler
    public void blockDestroyEvent(BlockDestroyEvent e) {
        if (e.getBlock().getType() != Material.SPAWNER) return;
        e.setCancelled(true);
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
            player.sendMessage("§cVous ne pouvez pas supprimer un générateur qui contient plusieurs générateur, retirez les puis réessayez.");
            return;
        }
        SpawnersManager.instance.removeHolo(spawner);
        SpawnersManager.instance.removeSpawner(spawner, island);
        SpawnersManager.instance.destroySpawner(spawner, island);
        SpawnersManager.instance.giveSpawner(player, spawner.getEntityType(), 1);
    }
}
