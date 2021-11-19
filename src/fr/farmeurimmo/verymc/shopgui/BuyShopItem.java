package fr.farmeurimmo.verymc.shopgui;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.farmeurimmo.verymc.core.Main;
import fr.farmeurimmo.verymc.eco.EcoAccountsManager;

public class BuyShopItem {
	
	public static HashMap <ItemStack, Integer> pricesbuy = new HashMap < > ();
	public static HashMap <ItemStack, Integer> pricessell = new HashMap < > ();
	
	public static void GenPriceShopStartup() {
		for(String bb : Main.instance1.getConfig().getConfigurationSection("Shops").getKeys(false)) {
		for(String aa : Main.instance1.getConfig().getConfigurationSection("Shops."+bb).getKeys(false)) {
			int a = Main.instance1.getConfig().getInt("Shops.Blocs."+aa+".buy");
			int c = Main.instance1.getConfig().getInt("Shops.Blocs."+aa+".sell");
			ItemStack b = new ItemStack(Material.valueOf(Main.instance1.getConfig().getString("Shops.Blocs."+aa+".material")));
			pricesbuy.put(b, a);
			pricessell.put(b, c);
		}
		}
	}
	public static void BuyOSellItemNonStack(ItemStack a, Player player, boolean buy, int price) {
		if(buy == true) {
			if(EcoAccountsManager.CheckForFounds(player, pricesbuy.get(a))) {
				player.getInventory().addItem(a);
				EcoAccountsManager.RemoveFounds(player.getName(), price);
			} else {
				int loa=price - EcoAccountsManager.GetMoney(player.getName());
				player.sendMessage("§6§lShop §8» §fIl vous manque §6"+loa+"$§f.");
			}
		} else {
			if(player.getInventory().contains(a.getType(), a.getAmount())) {
				int profit = pricessell.get(a)*a.getAmount();
				player.getInventory().remove(a);
				player.sendMessage("§6§lShop §8» §fVous avez vendu §ax"+a.getAmount()+" "+a.getType().toString()+"§f pour §6"+profit+"$§f.");
				EcoAccountsManager.AddFounds(player.getName(), profit);
			} else {
				player.sendMessage("§6§lShop §8» §fVous avez besoin de plus de "+a.getType().toString()+".");
			}
		}
	}
}
