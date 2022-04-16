package main.java.fr.verymc.blocks;

import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Hopper;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ChestListener implements Listener {

    public static int GetAmountToFillInInv(ItemStack aa, Inventory player) {
        int total = 0;

        int size = player.getSize();
        for (int slot = 0; slot < size; slot++) {
            ItemStack is = player.getItem(slot);
            if (is == null) {
                total += 64;
                continue;
            } else if (is.getType() == aa.getType()) {
                total += 64 - is.getAmount();
                continue;
            }
        }

        return total;
    }

    @EventHandler
    public void CheckForItem(ItemSpawnEvent e) {
        if (!(e.getEntity() instanceof Item)) {
            return;
        }
        if (e.getEntity().getLocation().getWorld().getName().equalsIgnoreCase("world")) {
            return;
        }
        for (Chest c : ChestManager.instance.chests) {
            if (c.getType() == 0) {
                if (c.getChunkKey().equals(e.getLocation().getChunk().getChunkKey())) {
                    if (c.getBlock().getBlock().getType() == Material.HOPPER) {
                        Hopper blhopper = (Hopper) c.getBlock().getBlock().getState();
                        if (!blhopper.getCustomName().contains("§6Chunk Hoppeur ")) {
                            continue;
                        }
                        ItemStack a = e.getEntity().getItemStack();
                        if (GetAmountToFillInInv(a, blhopper.getInventory()) > 0) {
                            e.getEntity().remove();
                            blhopper.getInventory().addItem(a);
                            break;
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void BreakEvent(BlockBreakEvent e) {
        if (e.getBlock() == null) {
            return;
        }
        if (e.getBlock().getType() == null) {
            return;
        }
        if (e.getBlock().getState() == null) {
            return;
        }
        BlockState bs = e.getBlock().getState();
        String a = "";
        int type = -1;
        if (e.getBlock().getType() == Material.HOPPER) {
            Hopper blhopper = (Hopper) e.getBlock().getState();
            if (blhopper.getCustomName().contains("§6Chunk Hoppeur ")) {
                type = 0;
            } else {
                return;
            }
            a = blhopper.getCustomName().replace("§6", "");
        } else if (e.getBlock().getType() == Material.CHEST) {
            org.bukkit.block.Chest blchest = (org.bukkit.block.Chest) e.getBlock().getState();
            if (blchest.getCustomName().contains("§6SellChest ")) {
                type = 1;
            } else {
                return;
            }
            a = blchest.getCustomName().replace("§6", "");
        } else {
            return;
        }
        String numberOnly = a.replaceAll("[^0-9]", "");
        ChestManager.instance.removeChestFromLoc(e.getBlock().getLocation());
        ChestManager.instance.giveChest(e.getPlayer(), Integer.parseInt(numberOnly), type);
        e.setDropItems(false);
    }

    @EventHandler
    public void PlaceEvent(BlockPlaceEvent e) {
        if (e.getItemInHand() == null) {
            return;
        }
        if (e.getItemInHand().getType() == null) {
            return;
        }
        String str = e.getItemInHand().getDisplayName().replace("§6", "");
        String numberOnly = str.replaceAll("[^0-9]", "");
        int type = -1;
        if (e.getItemInHand().getDisplayName().contains("§6Chunk Hoppeur")) {
            type = 0;
        } else if (e.getItemInHand().getDisplayName().contains("§6SellChest ")) {
            type = 1;
        } else {
            return;
        }
        ChestManager.instance.placeChest(e.getPlayer(), e.getBlock().getLocation(), Integer.parseInt(numberOnly), type);
    }

}
