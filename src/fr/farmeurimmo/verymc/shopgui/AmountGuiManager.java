package fr.farmeurimmo.verymc.shopgui;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AmountGuiManager implements Listener {
	
	@EventHandler
	public void OnInventoryClick(InventoryClickEvent e) {
		ItemStack current = e.getCurrentItem();
		if(e.getView().getTitle().equalsIgnoreCase("§6Choix de la quantité d'achat")) {
			e.setCancelled(true);
			if(current.getType() != Material.ARROW && current.getType() != Material.PLAYER_HEAD && current.getType() != Material.LIME_WOOL) {
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
					ItemMeta temp = e.getInventory().getItem(22).getItemMeta();
					int price = BuyShopItem.pricesbuy.get(e.getInventory().getItem(22).getType().toString());
					temp.setLore(Arrays.asList("§6Prix d'achat: §c"+price+"$/u","§6Coût total: §c"+price*e.getInventory().getItem(22).getAmount()+"$"));
					e.getInventory().getItem(22).setItemMeta(temp);
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
					ItemMeta temp = e.getInventory().getItem(22).getItemMeta();
					int price = BuyShopItem.pricesbuy.get(e.getInventory().getItem(22).getType().toString());
					temp.setLore(Arrays.asList("§6Prix d'achat: §c"+price+"$/u","§6Coût total: §c"+price*e.getInventory().getItem(22).getAmount()+"$"));
					e.getInventory().getItem(22).setItemMeta(temp);
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
