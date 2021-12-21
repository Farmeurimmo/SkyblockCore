package fr.farmeurimmo.verymc.blocks;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.farmeurimmo.verymc.core.Main;

public class ChunkCollectorManager {
	
	public static HashMap <Location, Long> blcchunk = new HashMap < > ();
	
	public static void GiveChest(Player player) {
		ItemStack aa = new ItemStack(Material.HOPPER);
		ItemMeta ameta = aa.getItemMeta();
		ameta.setDisplayName("§6Chunk Hoppeur");
		aa.setUnbreakable(true);
		aa.setItemMeta(ameta);
		
		player.getInventory().addItem(aa);
	}
	
	public static void PlaceChest(Player player, Long chunkkey, Location block) {
		Main.getInstance().getDatablc().set("ChunkHoppeur."+chunkkey, block);
		Main.getInstance().saveData();
		blcchunk.put(block, chunkkey);
	}
}
