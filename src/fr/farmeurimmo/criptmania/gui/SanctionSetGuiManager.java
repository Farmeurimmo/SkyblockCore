package fr.farmeurimmo.criptmania.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class SanctionSetGuiManager implements Listener {
	
	@EventHandler
	public void OnInventoryClick(InventoryClickEvent e) {
		Player player = (Player) e.getWhoClicked();
		ItemStack current = e.getCurrentItem();
		
		if(current == null) {
			return;
		}
		if(current.getType() == null) {
			return;
		}
		
		if(e.getView().getTitle().equalsIgnoreCase("§6Menu des Sanctions")) {
			e.setCancelled(true);
			if(current.getType() == Material.WRITABLE_BOOK) {
				player.closeInventory();
				SanctionSetGui.MakeMuteGui(player, Bukkit.getPlayer(e.getInventory().getItem(13).getItemMeta().getDisplayName().replace("§7", "")));
			}
			if(current.getType() == Material.ANVIL) {
				player.closeInventory();
				SanctionSetGui.MakeBanGui(player, Bukkit.getPlayer(e.getInventory().getItem(13).getItemMeta().getDisplayName().replace("§7", "")));
			}
			if(current.getType() == Material.NAME_TAG) {
				player.closeInventory();
				SanctionSetGui.MakeBanIpGui(player, Bukkit.getPlayer(e.getInventory().getItem(13).getItemMeta().getDisplayName().replace("§7", "")));
			}
			if(current.getType() == Material.BEDROCK) {
				player.closeInventory();
				SanctionSetGui.MakePurgeGui(player, Bukkit.getPlayer(e.getInventory().getItem(13).getItemMeta().getDisplayName().replace("§7", "")));
			}
			if(current.getType() == Material.IRON_DOOR) {
				player.closeInventory();
			}
		}
		if(e.getView().getTitle().equalsIgnoreCase("§6Menu des Mutes")) {
			e.setCancelled(true);
			if(current.getType() == Material.ARROW) {
				player.closeInventory();
				SanctionSetGui.MakeSSGui(player, Bukkit.getPlayer(e.getInventory().getItem(18).getItemMeta().getDisplayName().replace("§7", "")));
			}
		}
		if(e.getView().getTitle().equalsIgnoreCase("§6Menu des Bans")) {
			e.setCancelled(true);
			if(current.getType() == Material.ARROW) {
				player.closeInventory();
				SanctionSetGui.MakeSSGui(player, Bukkit.getPlayer(e.getInventory().getItem(18).getItemMeta().getDisplayName().replace("§7", "")));
			}
		}
		if(e.getView().getTitle().equalsIgnoreCase("§6Menu des Bans-Ip")) {
			e.setCancelled(true);
			if(current.getType() == Material.ARROW) {
				player.closeInventory();
				SanctionSetGui.MakeSSGui(player, Bukkit.getPlayer(e.getInventory().getItem(18).getItemMeta().getDisplayName().replace("§7", "")));
			}
		}
		if(e.getView().getTitle().equalsIgnoreCase("§6Menu des Purges")) {
			e.setCancelled(true);
			if(current.getType() == Material.ARROW) {
				player.closeInventory();
				SanctionSetGui.MakeSSGui(player, Bukkit.getPlayer(e.getInventory().getItem(18).getItemMeta().getDisplayName().replace("§7", "")));
			}
		}
	}

}
