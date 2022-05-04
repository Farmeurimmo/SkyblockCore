package main.java.fr.verymc.island.events;

import main.java.fr.verymc.island.Island;
import main.java.fr.verymc.island.IslandBlocsValues;
import main.java.fr.verymc.island.IslandManager;
import main.java.fr.verymc.island.perms.IslandPerms;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class IslandInteractManager implements Listener {

    @EventHandler
    public void playerInteractEvent(PlayerInteractEvent e) {
        Player player = e.getPlayer();

        if (e.getItem() == null) return;
        if (e.getItem().getItemMeta() == null) return;
        if (e.getItem().getLore() == null) return;

        ItemStack currentItem = e.getItem();
        if (currentItem.getType() != Material.NETHER_STAR) return;
        if (!currentItem.isUnbreakable()) return;

        Island island = IslandManager.instance.getPlayerIsland(player);
        if (island != null) {
            if (!island.hasPerms(island.getIslandRankFromUUID(player.getUniqueId()), IslandPerms.BANK_ADD, player)) {
                return;
            }
            if (player.isSneaking()) {
                island.getBank().addCrystaux(currentItem.getAmount() * 5);
                currentItem.setAmount(0);
            } else {
                island.getBank().addCrystaux(1 * 5);
                currentItem.setAmount(currentItem.getAmount() - 1);
            }
        }
    }

    @EventHandler
    public void blockBreakEvent(BlockBreakEvent e) {
        Player player = e.getPlayer();
        Island island = IslandManager.instance.getIslandByLoc(e.getBlock().getLocation());
        if (island != null) {
            if (island.hasPerms(island.getIslandRankFromUUID(player.getUniqueId()), IslandPerms.BREAK, player)) {
                if (IslandBlocsValues.instance.hasBlockValue(e.getBlock().getType())) {
                    island.removeValue(IslandBlocsValues.instance.getBlockValue(e.getBlock().getType()));
                }
                return;
            }
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void blockPlaceEvent(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        Island island = IslandManager.instance.getIslandByLoc(e.getBlock().getLocation());
        if (island != null) {
            if (island.hasPerms(island.getIslandRankFromUUID(player.getUniqueId()), IslandPerms.BUILD, player)) {
                if (IslandBlocsValues.instance.hasBlockValue(e.getBlock().getType())) {
                    island.addValue(IslandBlocsValues.instance.getBlockValue(e.getBlock().getType()));
                }
                return;
            }
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void interactEvent(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (e.getClickedBlock() == null) return;
        if (e.getClickedBlock().getLocation() == null) return;
        Island island = IslandManager.instance.getIslandByLoc(e.getClickedBlock().getLocation());
        if (island != null) {
            if (e.getClickedBlock().getState() instanceof InventoryHolder) {
                if (island.hasPerms(island.getIslandRankFromUUID(player.getUniqueId()), IslandPerms.CONTAINER, player)) {
                    return;
                }
                e.setCancelled(true);
                return;
            }
            if (island.hasPerms(island.getIslandRankFromUUID(player.getUniqueId()), IslandPerms.INTERACT, player)) {
                return;
            }
            e.setCancelled(true);
            return;
        }
    }
}
