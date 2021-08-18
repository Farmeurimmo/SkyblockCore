package fr.farmeurimmo.criptmania.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PermanantItem implements Listener {
	
	public static String NameMenuItem = "§6Menu §8| §7(clic droit)";
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		if(e.getItemDrop().getType() != null) {
		if(e.getItemDrop().getItemStack().getItemMeta().getDisplayName().equalsIgnoreCase(NameMenuItem)) {
			e.setCancelled(true);
		}
	}
	}
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		ItemStack custom1 = new ItemStack(Material.NETHER_STAR, 1);
		ItemMeta customS = custom1.getItemMeta();
		customS.setUnbreakable(true);
		customS.setDisplayName(PermanantItem.NameMenuItem);
		custom1.setItemMeta(customS);
		for(ItemStack aa : e.getEntity().getInventory().getContents()) {
			if(aa != null) {
				if(aa.getType() == custom1.getType() && aa.getItemMeta().getDisplayName().equalsIgnoreCase(custom1.getItemMeta().getDisplayName())) {
					aa.setAmount(0);
				}
			}
		}
	}
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) {
		ItemStack custom1 = new ItemStack(Material.NETHER_STAR, 1);
		ItemMeta customS = custom1.getItemMeta();
		customS.setUnbreakable(true);
		customS.setDisplayName(PermanantItem.NameMenuItem);
		custom1.setItemMeta(customS);
		e.getPlayer().getInventory().setItem(8, custom1);
	}
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
	Player player = (Player) e.getWhoClicked();
	ItemStack current = e.getCurrentItem();
	
	ItemStack custom1 = new ItemStack(Material.NETHER_STAR, 1);
	ItemMeta customS = custom1.getItemMeta();
	customS.setUnbreakable(true);
	customS.setDisplayName(PermanantItem.NameMenuItem);
	custom1.setItemMeta(customS);
	
	if(e.getCurrentItem() == null) {
		return; 
	}
	if(e.getCurrentItem().getItemMeta() == null) {
		return; 
	}
	if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(NameMenuItem)) {
		e.setResult(Result.DEFAULT);
		player.closeInventory();
		player.chat("/menu");
	}
	if(e.getView().getTitle() == "§6Menu du Skyblock") {
		e.setCancelled(true);
		if(current.getType() == Material.GRASS_BLOCK) {
			player.closeInventory();
			player.chat("/is");
		}
		if(current.getType() == Material.DIAMOND_SWORD) {
			player.closeInventory();
			player.chat("/farm2win");
		}
		if(current.getType() == Material.BOOK) {
			player.closeInventory();
			player.chat("/wiki");
		}
		if(current.getType() == Material.ENDER_EYE) {
			player.closeInventory();
			player.chat("/warps");
		}
		if(current.getType() == Material.SADDLE) {
			player.closeInventory();
			player.chat("/hdv");
		}
		if(current.getType() == Material.VILLAGER_SPAWN_EGG) {
			player.closeInventory();
			player.chat("/shop");
		}
		if(current.getType() == Material.IRON_SWORD) {
			player.closeInventory();
			player.chat("/c");
		}
		if(current.getType() == Material.POTION) {
			player.closeInventory();
			player.chat("/atout");
		}
	}
	}
}
