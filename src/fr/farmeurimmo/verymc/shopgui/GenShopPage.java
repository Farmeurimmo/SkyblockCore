package fr.farmeurimmo.verymc.shopgui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.farmeurimmo.verymc.core.Main;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;

public class GenShopPage {
	
	public static HashMap <Integer, ItemStack > items = new HashMap < > ();
	public static ArrayList<Integer> slotstofill = new ArrayList<Integer>();
	public static ArrayList<Integer> slotsfilled = new ArrayList<Integer>();
	
	public static void GenenerateShopPage(Player player, String page) {
		int numberofitem = 0;
		int numberofpage = 1;
		
		User user = LuckPermsProvider.get().getUserManager().getUser(player.getName());
		String Grade = user.getCachedData().getMetaData().getPrefix().replace("&", "§");
		
        Inventory inv = Bukkit.createInventory(null, 54, page + " #"+numberofpage);
        
        slotstofill.addAll(Arrays.asList(10,11,12,13,14,15,16,19,20,21,22,23,24,25,28,29,30,31,32,33,36,37,38,39,40,41,42,43));
        
		for(String aa : Main.instance1.getConfig().getConfigurationSection("Shops."+page).getKeys(false)) {
			ItemStack custom1 = new ItemStack(Material.valueOf(Main.instance1.getConfig().getString("Shops."+page+"."+aa+".material")), 1);
			numberofitem += 1;
			int slot = GetNextSlot();
			if(slot == 0) {
			slotsfilled.add(slot);
			break;
			}
			player.getInventory().addItem(custom1);
		}
		player.sendMessage(slotsfilled.toString());
	}
	public static int GetNextSlot() {
		int slottoreturn = 0;
		if(slotstofill.isEmpty()) {
			return slottoreturn;
		}
		while (slottoreturn+1 != slotstofill.get(slottoreturn)){
			slottoreturn+=1;
		}
		slotstofill.remove(slottoreturn);
		return slottoreturn;
	}
}
