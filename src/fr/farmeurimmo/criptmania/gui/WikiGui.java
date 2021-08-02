package fr.farmeurimmo.criptmania.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class WikiGui implements Listener {
	
	public static void OpenWikiMainGui(Player player) {
		Inventory inv = Bukkit.createInventory(null, 27, "§6Wiki");
		
		ItemStack custom1 = new ItemStack(Material.GRASS_BLOCK, 1);
		ItemMeta customa = custom1.getItemMeta();
		customa.setDisplayName("§6Wiki sur les iles");
		custom1.setItemMeta(customa);
		inv.setItem(10, custom1);
		
		ItemStack custom2 = new ItemStack(Material.COMMAND_BLOCK, 1);
		ItemMeta customb = custom2.getItemMeta();
		customb.setDisplayName("§6Wiki sur les commandes de base");
		custom2.setItemMeta(customb);
		inv.setItem(12, custom2);
		
		ItemStack custom3 = new ItemStack(Material.CLOCK, 1);
		ItemMeta customc = custom3.getItemMeta();
		customc.setDisplayName("§6Wiki sur les events");
		custom3.setItemMeta(customc);
		inv.setItem(14, custom3);
		
		ItemStack custom4 = new ItemStack(Material.BOOKSHELF, 1);
		ItemMeta customd = custom4.getItemMeta();
		customd.setDisplayName("§6Informations sur les grades");
		custom4.setItemMeta(customd);
		inv.setItem(16, custom4);
		
		player.openInventory(inv);
	}
	
	@EventHandler
	public void OnInventoryClic(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack current = event.getCurrentItem();
		
		if(event.getView().getTitle().equalsIgnoreCase("§6Wiki")) {
			event.setCancelled(true);
			if(current.getType() == Material.GRASS_BLOCK) {
				org.bukkit.inventory.Inventory invwikiile = Bukkit.createInventory(null, 27, "§6Sous-Wikis");
				
				ItemStack custom1 = new ItemStack(Material.BEDROCK, 1);
				ItemMeta customa = custom1.getItemMeta();
				customa.setDisplayName("§cIndispo");
				custom1.setItemMeta(customa);
				invwikiile.setItem(10, custom1);
				player.openInventory(invwikiile);
			}
			if(current.getType() == Material.BEDROCK) {
			}
			if(current.getType() == Material.BOOKSHELF) {
			}
		}
		if(event.getView().getTitle().equalsIgnoreCase("§6Sous-Wikis")) {
			event.setCancelled(true);
		}
	}

}
