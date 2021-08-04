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
			if(current.getType() == Material.BOW) {
				player.closeInventory();
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mute 20m Spam");
				player.sendMessage("§6La sanction a été appliquée !");
			}
			if(current.getType() == Material.DIAMOND_SWORD) {
				player.closeInventory();
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mute 20m Flood");
				player.sendMessage("§6La sanction a été appliquée !");
			}
			if(current.getType() == Material.ANVIL) {
				player.closeInventory();
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mute 30m Insultes");
				player.sendMessage("§6La sanction a été appliquée !");
			}
			if(current.getType() == Material.REDSTONE_BLOCK) {
				player.closeInventory();
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mute 30m Provocation");
				player.sendMessage("§6La sanction a été appliquée !");
			}
			if(current.getType() == Material.ACTIVATOR_RAIL) {
				player.closeInventory();
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mute 30m Incitation");
				player.sendMessage("§6La sanction a été appliquée !");
			}
			if(current.getType() == Material.ARMOR_STAND) {
				player.closeInventory();
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mute 1h Contournement");
				player.sendMessage("§6La sanction a été appliquée !");
			}
			if(current.getType() == Material.COMPASS) {
				player.closeInventory();
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mute 2h Pub");
				player.sendMessage("§6La sanction a été appliquée !");
			}
			if(current.getType() == Material.FLINT_AND_STEEL) {
				player.closeInventory();
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mute 1h Menaces");
				player.sendMessage("§6La sanction a été appliquée !");
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
