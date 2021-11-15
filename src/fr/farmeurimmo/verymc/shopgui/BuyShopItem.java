package fr.farmeurimmo.verymc.shopgui;

import java.util.HashMap;

import fr.farmeurimmo.verymc.core.Main;

public class BuyShopItem {
	
	public static HashMap <String, Integer> pricesbuy = new HashMap < > ();
	public static HashMap <String, Integer> pricessell = new HashMap < > ();
	
	public static void GenPriceShopStartup() {
		for(String bb : Main.instance1.getConfig().getConfigurationSection("Shops").getKeys(false)) {
		for(String aa : Main.instance1.getConfig().getConfigurationSection("Shops."+bb).getKeys(false)) {
			int a = Main.instance1.getConfig().getInt("Shops.Blocs."+aa+".buy");
			int c = Main.instance1.getConfig().getInt("Shops.Blocs."+aa+".sell");
			String b = Main.instance1.getConfig().getString("Shops.Blocs."+aa+".material");
			pricesbuy.put(b, a);
			pricessell.put(b, c);
		}
		}
	}
}
