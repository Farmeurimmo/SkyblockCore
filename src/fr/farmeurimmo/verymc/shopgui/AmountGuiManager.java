package fr.farmeurimmo.verymc.shopgui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class AmountGuiManager implements Listener {
	
	@EventHandler
	public void OnInventoryClick(InventoryClickEvent e) {
		ItemStack current = e.getCurrentItem();
		if(e.getView().getTitle().equalsIgnoreCase("§6Choix de la quantité")) {
			e.setCancelled(true);
			if(current.getType() != Material.ARROW && current.getType() != Material.PLAYER_HEAD) {
				if(current.getType() == Material.LIME_STAINED_GLASS_PANE) {
					int amount = e.getInventory().getItem(22).getAmount();
					int toadd = 0;
					String totrait = current.getItemMeta().getDisplayName();
					totrait = totrait.replace("+", "");
					totrait = totrait.replace("§a§l", "");
					toadd = Integer.parseInt(totrait);
					int now = amount + toadd;
					if(now > 64) {
						e.getInventory().getItem(22).setAmount(64);
					} else {
						e.getInventory().getItem(22).setAmount(now);
					}
				}
				if(current.getType() == Material.RED_STAINED_GLASS_PANE) {
					int amount = e.getInventory().getItem(22).getAmount();
					int toremove = 0;
					String totrait = current.getItemMeta().getDisplayName();
					totrait = totrait.replace("+", "");
					totrait = totrait.replace("§c§l", "");
					toremove = Integer.parseInt(totrait);
					int now = amount + toremove;
					if(now < 1) {
						e.getInventory().getItem(22).setAmount(1);
					} else {
						e.getInventory().getItem(22).setAmount(now);
					}
				}
			} else if(current.getType() == Material.ARROW) {
				Player player = (Player) e.getWhoClicked();
				String lastpage = GenShopPage.lastpage.get(player);
				int pagecurrent = GenShopPage.lastnumpage.get(player);
				GenShopPage.OpenPreGenPage(player, lastpage, pagecurrent);
			}
		}
	}
}
