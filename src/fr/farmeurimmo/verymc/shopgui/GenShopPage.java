package fr.farmeurimmo.verymc.shopgui;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.farmeurimmo.verymc.core.Main;

public class GenShopPage {
	
	public static HashMap <Integer, ItemStack > items = new HashMap < > ();
	
	public static void GenenerateShopPage(Player player, String page) {
		int numberofitem = 0;
		for(String aa : Main.instance1.getConfig().getConfigurationSection("Shops."+page).getKeys(false)) {
			ItemStack custom1 = new ItemStack(Material.valueOf(Main.instance1.getConfig().getString("Shops."+page+"."+aa+".material")), 1);
			numberofitem += 1;
			player.getInventory().addItem(custom1);
		}
	}
}
