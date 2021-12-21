package fr.farmeurimmo.verymc.blocks;

import java.util.Map.Entry;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Hopper;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ChunkCollector implements Listener {
	
	public static int GetAmountToFillInInv(ItemStack aa, Inventory player) {
		int total = 0;
		
		int size = player.getSize();
        for (int slot = 0; slot < size; slot++) {
        	ItemStack is = player.getItem(slot);
        	if(is == null) {
        		total+=64;
        		continue;
        	} else if(is.getType() == aa.getType()) {
        		total+=64-is.getAmount();
        		continue;
        	}
        }
		
		return total;
	}
	
	@EventHandler
	public void CheckForItem(ItemSpawnEvent e) {
		if(!(e.getEntity() instanceof Item)) {
			return;
		}
		if(ChunkCollectorManager.blcchunk.containsValue(e.getLocation().getChunk().getChunkKey())) {
			for(Entry<Location, Long> tttt : ChunkCollectorManager.blcchunk.entrySet()) {
			Location hopper = tttt.getKey();
			if(hopper.getBlock().getType()==Material.HOPPER) {
				Hopper blhopper = (Hopper) hopper.getBlock().getState();
				ItemStack a = e.getEntity().getItemStack();
				if(GetAmountToFillInInv(a, blhopper.getInventory())>0) {
					e.setCancelled(true);
					blhopper.getInventory().addItem(a);
					break;
				}
				}
			}
		}
	}
	@EventHandler
	public void PlaceEvent(BlockPlaceEvent e) {
		if(e.getBlockPlaced().getType()==Material.HOPPER && e.getItemInHand().getItemMeta().getDisplayName().contains("§6Chunk Hoppeur")) {
			ChunkCollectorManager.PlaceChest(e.getPlayer(), e.getBlock().getLocation().getChunk().getChunkKey(), e.getBlock().getLocation());
		}
	}

}
