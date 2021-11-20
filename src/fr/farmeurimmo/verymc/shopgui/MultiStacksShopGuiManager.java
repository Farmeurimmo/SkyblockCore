package fr.farmeurimmo.verymc.shopgui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class MultiStacksShopGuiManager implements Listener {
	
	@EventHandler
	public void OnInventoryClick(InventoryClickEvent e) {
		Player player = (Player) e.getWhoClicked();
		ItemStack current = e.getCurrentItem();
		if(current == null) {
			return;
		}
		if(e.getView().getTitle().contains("Choix des stacks")) {
			e.setCancelled(true);
			ItemStack od = e.getInventory().getItem(45);
			if(current.getType() == Material.ARROW) {
				GenAmoutShopGui.OpenPregenAmoutShop(player, od, true);
			}
			if(current.getType() == Material.GREEN_STAINED_GLASS) {
				if(current.getAmount() < 64) {
					int numstacks = current.getAmount();
					float price = (float) BuyShopItem.pricesbuy.get(new ItemStack(od.getType()));
					BuyShopItem.BuyOSellItemNonStack(od, (Player) e.getWhoClicked(), true, price, numstacks*64);
				} else {
					int amountinvinv = BuyShopItem.GetAmountToFillInInv(od, (Player) e.getWhoClicked());
					float price = (float) BuyShopItem.pricesbuy.get(new ItemStack(od.getType()));
					BuyShopItem.BuyOSellItemNonStack(od, (Player) e.getWhoClicked(), true, price, amountinvinv);
				}
			}
		}
	}
}
