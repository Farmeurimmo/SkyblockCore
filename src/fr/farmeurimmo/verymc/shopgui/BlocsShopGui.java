package fr.farmeurimmo.verymc.shopgui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class BlocsShopGui implements Listener {
	
	@EventHandler
	public void OnInventoryClick(InventoryClickEvent e) {
		Player player = (Player) e.getWhoClicked();
		ItemStack current = e.getCurrentItem();
		if(e.getView().getTitle().contains("§6Blocs ") && e.getView().getTitle().contains("/")) {
			e.setCancelled(true);
			if(e.getClick() == ClickType.LEFT) {
			if(current.getType() == Material.ARROW) {
				if(current.getItemMeta().getDisplayName().contains("suivante")) {
					nextpage(player, "Blocs");
				}
				if(current.getItemMeta().getDisplayName().contains("précédente")) {
					backpage(player, "Blocs");
				}
			} else if (current.getType() != Material.PLAYER_HEAD && current.getType() != Material.ARROW) {
				GenAmoutShopGui.OpenPregenAmoutShop(player, current, true);
			}
			}
			if(e.getClick() == ClickType.RIGHT && current.getType() != Material.PLAYER_HEAD && current.getType() != Material.ARROW) {
				GenAmoutShopGui.OpenPregenAmoutShop(player, current, false);
			}
		}
	}
	public void nextpage(Player player, String page) {
		if(GenShopPage.numpages.containsKey(player.getName())) {
	        int numberofpage = GenShopPage.maxpage.get(page);
			if(numberofpage == GenShopPage.numpages.get(player.getName())) {
				player.sendMessage("§6§lShop §8» §fVous ne pouvez pas passer à la page suivante car elle n'existe pas !");
			} else {
				GenShopPage.OpenPreGenPage(player, "Blocs", 2);
			}
		}
	}
	public void backpage(Player player, String page) {
		if(GenShopPage.numpages.containsKey(player.getName())) {
			int numberofpage = GenShopPage.maxpage.get(page);
			if(GenShopPage.numpages.get(player.getName()) <= numberofpage && GenShopPage.numpages.get(player.getName()) == 1) {
				player.sendMessage("§6§lShop §8» §fVous ne pouvez pas passer à la page précédente car elle n'existe pas !");
			} else {
				GenShopPage.OpenPreGenPage(player, "Blocs", 1);
			}
		}
	}

}
