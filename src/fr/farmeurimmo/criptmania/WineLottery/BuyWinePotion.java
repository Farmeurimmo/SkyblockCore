package fr.farmeurimmo.criptmania.WineLottery;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BuyWinePotion {
	
	public static void MakeWinePotionGui(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, "§6Boutique des vins");
		
		ItemStack custom1 = new ItemStack(Material.POTION, 1);
		ItemMeta customa = custom1.getItemMeta();
		customa.setDisplayName("§6Cidre");
		customa.setLore(Arrays.asList("§7test"));
		custom1.setItemMeta(customa);
		inv.setItem(10, custom1);
		
		player.openInventory(inv);
	}

}
