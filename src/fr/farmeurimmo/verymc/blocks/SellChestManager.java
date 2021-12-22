package fr.farmeurimmo.verymc.blocks;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.farmeurimmo.verymc.core.Main;

public class SellChestManager {
	
	public static HashMap <String, Location> blcsellchest = new HashMap < > ();
	
	public static void GiveSellChest(Player player, int i) {
		int a = 0;
		if(Main.getInstance().getDatablc().get("SellChest.num")==null) {
			a = 1;
		} else {
			a = Main.getInstance().getDatablc().getInt("SellChest.num");
			a+=1;
		}
		ItemStack aa = new ItemStack(Material.CHEST);
		ItemMeta ameta = aa.getItemMeta();
		if(i>0) {
			a=i;
		} else {
			Main.getInstance().getDatablc().set("SellChest.num", a);
			Main.getInstance().saveData();
		}
		ameta.setDisplayName("§6SellChest §c(id#"+a+")");
		aa.setUnbreakable(true);
		aa.setItemMeta(ameta);
		
		player.getInventory().addItem(aa);
	}
	public static void PlaceChest(Player player, Location block, int num) {
		Main.getInstance().getDatablc().set("SellChest."+player.getName()+"."+num, block);
		Main.getInstance().saveData();
		blcsellchest.put(player.getName(), block);
	}
	public static String getOwner(Location loc) {
		if(!Main.getInstance().getDatablc().isSet("SellChest")) {
			return null;
		}
		for(String aa : Main.getInstance().getDatablc().getConfigurationSection("SellChest").getKeys(false)) {
			for(String bb : Main.getInstance().getDatablc().getConfigurationSection("SellChest."+aa).getKeys(false)) {
				if(Main.getInstance().getDatablc().getLocation("SellChest."+aa+"."+bb) == loc) {
					return aa;
				}
			}
		}
		return null;
		
	}
	public static void ReadFromFile() {
		if(!Main.getInstance().getDatablc().isSet("SellChest")) {
			return;
		}
		for(String aa : Main.getInstance().getDatablc().getConfigurationSection("SellChest").getKeys(false)) {
			if(aa.contains("num")) {
				continue;
			}
			for(String bb : Main.getInstance().getDatablc().getConfigurationSection("SellChest."+aa).getKeys(false)) {
				blcsellchest.put(aa,Main.getInstance().getDatablc().getLocation("SellChest."+aa+"."+bb));
			}
		}
	}
	
}
