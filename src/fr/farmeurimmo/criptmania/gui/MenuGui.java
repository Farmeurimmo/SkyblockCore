package fr.farmeurimmo.criptmania.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MenuGui {

	public static void OpenMainMenu(Player player) {
		
        Inventory inv = Bukkit.createInventory(null, 45, "§6Menu du Skyblock");
        
    	ItemStack custom1 = new ItemStack(Material.GRASS_BLOCK, 1);
		ItemMeta meta1 = custom1.getItemMeta();
		meta1.setDisplayName("§6Votre Ile §8| §7(clic gauche)");
		custom1.setItemMeta(meta1);
		inv.setItem(10, custom1);
		
		ItemStack custom2 = new ItemStack(Material.DIAMOND_SWORD, 1);
		ItemMeta meta2 = custom2.getItemMeta();
		meta2.setDisplayName("§6Boutique Farm2Win §8| §7(clic gauche)");
		custom2.setItemMeta(meta2);
		inv.setItem(12, custom2);
		
		ItemStack custom3 = new ItemStack(Material.BOOK, 1);
		ItemMeta meta3 = custom3.getItemMeta();
		meta3.setDisplayName("§6Wiki §8| §7(clic gauche)");
		custom3.setItemMeta(meta3);
		inv.setItem(30, custom3);
		
		ItemStack custom7 = new ItemStack(Material.ENDER_EYE, 1);
		ItemMeta meta7 = custom7.getItemMeta();
		meta7.setDisplayName("§6Warps §8| §7(clic gauche)");
		custom7.setItemMeta(meta7);
		inv.setItem(28, custom7);
		
		ItemStack custom4 = new ItemStack(Material.BOOKSHELF, 1);
		ItemMeta meta4 = custom4.getItemMeta();
		meta4.setDisplayName("§6Règles §8| §7(clic gauche)");
		custom4.setItemMeta(meta4);
		inv.setItem(32, custom4);
		
		ItemStack custom5 = new ItemStack(Material.SADDLE, 1);
		ItemMeta meta5 = custom5.getItemMeta();
		meta5.setDisplayName("§6Hôtel des ventes §8| §7(clic gauche)");
		custom5.setItemMeta(meta5);
		inv.setItem(14, custom5);
		
		ItemStack custom6 = new ItemStack(Material.VILLAGER_SPAWN_EGG, 1);
		ItemMeta meta6 = custom6.getItemMeta();
		meta6.setDisplayName("§6Shop §8| §7(clic gauche)");
		custom6.setItemMeta(meta6);
		inv.setItem(16, custom6);
		
		ItemStack custom10 = new ItemStack(Material.BEDROCK, 1);
		ItemMeta meta10 = custom10.getItemMeta();
		meta10.setDisplayName("§cSoon");
		custom10.setItemMeta(meta10);
		inv.setItem(34, custom10);
		
		ItemStack custom8 = new ItemStack(Material.WHITE_STAINED_GLASS_PANE, 1);
		ItemMeta meta8 = custom8.getItemMeta();
		meta8.setDisplayName("§6");
		custom8.setItemMeta(meta8);
		inv.setItem(0, custom8);
		inv.setItem(1, custom8);
		inv.setItem(2, custom8);
		inv.setItem(3, custom8);
		inv.setItem(4, custom8);
		inv.setItem(5, custom8);
		inv.setItem(6, custom8);
		inv.setItem(7, custom8);
		inv.setItem(8, custom8);
		inv.setItem(9, custom8);
		inv.setItem(11, custom8);
		inv.setItem(13, custom8);
		inv.setItem(15, custom8);
		inv.setItem(17, custom8);
		inv.setItem(18, custom8);
		inv.setItem(19, custom8);
		inv.setItem(20, custom8);
		inv.setItem(21, custom8);
		inv.setItem(22, custom8);
		inv.setItem(23, custom8);
		inv.setItem(24, custom8);
		inv.setItem(25, custom8);
		inv.setItem(26, custom8);
		inv.setItem(27, custom8);
		inv.setItem(29, custom8);
		inv.setItem(31, custom8);
		inv.setItem(33, custom8);
		inv.setItem(35, custom8);
		inv.setItem(36, custom8);
		inv.setItem(37, custom8);
		inv.setItem(38, custom8);
		inv.setItem(39, custom8);
		inv.setItem(40, custom8);
		inv.setItem(41, custom8);
		inv.setItem(42, custom8);
		inv.setItem(43, custom8);
		inv.setItem(44, custom8);
		
		player.openInventory(inv);
	}
	
}
