package fr.farmeurimmo.premsi.crates;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class VoteCrateManager {
	
	public static void VoteCratePreview(Player player) {
        Inventory inv = Bukkit.createInventory(null, 45, "§6Boxe vote");
        
        ItemStack custom1 = new ItemStack(Material.BARRIER, 1);
		ItemMeta meta1 = custom1.getItemMeta();
		meta1.setDisplayName("§6Boxe vote");
		meta1.setLore(Arrays.asList("§7","§eEn dev: 10%"));
		custom1.setItemMeta(meta1);
		inv.setItem(10, custom1);
		
		ItemStack custom8 = new ItemStack(Material.WHITE_STAINED_GLASS_PANE, 1);
		ItemMeta meta8 = custom8.getItemMeta();
		meta8.setDisplayName("§6");
		custom8.setItemMeta(meta8);
		
		for (int i = 0; i < inv.getSize(); i++) {
            if (inv.getItem(i) == null || inv.getItem(i).getType().equals(Material.AIR)) {
            	inv.setItem(i, custom8);
            }
        }
		
		player.openInventory(inv);
	}

}
