package fr.farmeurimmo.premsi.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class WarpGui implements Listener {
	
	public static void OpenGUi(Player player) {
		Inventory inv = Bukkit.createInventory(null, 45, "§6Warps");
		
		ItemStack custom1 = new ItemStack(Material.ENDER_CHEST, 1);
		ItemMeta meta1 = custom1.getItemMeta();
		meta1.setDisplayName("§6Boxes");
		custom1.setItemMeta(meta1);
		
		ItemStack custom2 = new ItemStack(Material.ENCHANTING_TABLE, 1);
		ItemMeta meta2 = custom2.getItemMeta();
		meta2.setDisplayName("§6Zone enchantements");
		custom2.setItemMeta(meta2);
		
		ItemStack custom3 = new ItemStack(Material.POTION, 1);
		ItemMeta meta3 = custom3.getItemMeta();
		meta3.setDisplayName("§6Bar §c(en développement)");
		custom3.setItemMeta(meta3);
		
		inv.setItem(10, custom1);
		inv.setItem(12, custom2);
		inv.setItem(14, custom3);
		
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
		
		ItemStack custom9 = new ItemStack(Material.ARROW, 1);
		ItemMeta customh = custom9.getItemMeta();
		customh.setDisplayName("§6Retour §8| §7(clic gauche)");
		custom9.setItemMeta(customh);
		inv.setItem(44, custom9);
		
		player.openInventory(inv);
	}
	@EventHandler
	public void onClickWarps(InventoryClickEvent e) {
		Player player = (Player) e.getWhoClicked();
		ItemStack current = e.getCurrentItem();
		if(e.getView().getTitle().equalsIgnoreCase("§6Warps")) {
			e.setCancelled(true);
			if(current.getType() == Material.ARROW) {
				player.closeInventory();
				MenuGui.OpenMainMenu(player);
			}
			if(current.getType() == Material.ENDER_CHEST) {
				player.closeInventory();
				player.chat("/warp boxes");
			}
			if(current.getType() == Material.ENCHANTING_TABLE) {
				player.closeInventory();
				player.chat("/warp enchantement");
			}
			if(current.getType() == Material.POTION) {
				player.closeInventory();
				player.chat("/warp bar");
			}
		}
		
	}

}
