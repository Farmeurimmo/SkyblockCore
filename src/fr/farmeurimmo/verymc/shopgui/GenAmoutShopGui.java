package fr.farmeurimmo.verymc.shopgui;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import fr.farmeurimmo.verymc.eco.EcoAccountsManager;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;

public class GenAmoutShopGui {
	
	public static HashMap <ItemStack, Integer> amountchoice = new HashMap < > ();
	
	@SuppressWarnings({ "deprecation", "unlikely-arg-type" })
	public static void OpenPregenAmoutShop(Player player, ItemStack aa, boolean a, String shop) {
		Inventory inv = Bukkit.createInventory(null, 54, "§6Choix de la quantité d'achat");
		
		User user = LuckPermsProvider.get().getUserManager().getUser(player.getName());
		String Grade = user.getCachedData().getMetaData().getPrefix().replace("&", "§");
		
		for(Entry<ItemStack, Integer> cc : amountchoice.entrySet()) {
        	inv.setItem(cc.getValue(), cc.getKey());
        }
		ItemStack custom2 = new ItemStack(Material.PLAYER_HEAD, 1);
		SkullMeta customb = (SkullMeta) custom2.getItemMeta();
		customb.setOwner(player.getName());
		customb.setDisplayName("§7" + player.getName());
		customb.setLore(Arrays.asList("§7Grade: " + Grade, "§7Argent: " + EcoAccountsManager.Moneys.get(player.getName())));
		custom2.setItemMeta(customb);
		inv.setItem(49, custom2);
		
		ItemStack custom7 = new ItemStack(Material.ARROW, 1);
		ItemMeta customg = custom7.getItemMeta();
		customg.setDisplayName("§6Retour en arrière");
		custom7.setItemMeta(customg);
		inv.setItem(53, custom7);
		
		int price = 0;
		ItemMeta tempameta = aa.getItemMeta();
		tempameta.setLore(null);
		if(a == true) {
			price = BuyShopItem.pricesbuy.get(aa.getType().toString());
			tempameta.setLore(Arrays.asList("§6Prix d'achat: §c"+price+"$/u","§6Coût total: §c"+price*aa.getAmount()+"$"));
		} else {
			
		}
		aa.setItemMeta(tempameta);
		inv.setItem(22, aa);
        
		player.openInventory(inv);
		
	}
	public static void GenAmoutShopGuiStartup() {  
        ItemStack custom1 = new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1);
		ItemMeta customa = custom1.getItemMeta();
		customa.setDisplayName("§a§l+1");
		custom1.setItemMeta(customa);
		
		ItemStack custom3 = new ItemStack(Material.LIME_STAINED_GLASS_PANE, 8);
		ItemMeta customc = custom3.getItemMeta();
		customc.setDisplayName("§a§l+8");
		custom3.setItemMeta(customc);
		
		ItemStack custom2 = new ItemStack(Material.LIME_STAINED_GLASS_PANE, 16);
		ItemMeta customb = custom2.getItemMeta();
		customb.setDisplayName("§a§l+16");
		custom2.setItemMeta(customb);
		
		ItemStack custom4 = new ItemStack(Material.LIME_STAINED_GLASS_PANE, 32);
		ItemMeta customd = custom4.getItemMeta();
		customd.setDisplayName("§a§l+32");
		custom4.setItemMeta(customd);
		
		ItemStack custom5 = new ItemStack(Material.LIME_STAINED_GLASS_PANE, 64);
		ItemMeta custome = custom5.getItemMeta();
		custome.setDisplayName("§a§l+64");
		custom5.setItemMeta(custome);
		
		
		ItemStack custom6 = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);
		ItemMeta customf = custom6.getItemMeta();
		customf.setDisplayName("§c§l-1");
		custom6.setItemMeta(customf);
		
		ItemStack custom7 = new ItemStack(Material.RED_STAINED_GLASS_PANE, 8);
		ItemMeta customg = custom7.getItemMeta();
		customg.setDisplayName("§c§l-8");
		custom7.setItemMeta(customg);
		
		ItemStack custom8 = new ItemStack(Material.RED_STAINED_GLASS_PANE, 16);
		ItemMeta customh = custom8.getItemMeta();
		customh.setDisplayName("§c§l-16");
		custom8.setItemMeta(customh);
		
		ItemStack custom9 = new ItemStack(Material.RED_STAINED_GLASS_PANE, 32);
		ItemMeta customi = custom9.getItemMeta();
		customi.setDisplayName("§c§l-32");
		custom9.setItemMeta(customi);
		
		ItemStack custom10 = new ItemStack(Material.RED_STAINED_GLASS_PANE, 64);
		ItemMeta customj = custom10.getItemMeta();
		customj.setDisplayName("§c§l-64");
		custom10.setItemMeta(customj);
		
		ItemStack custom11 = new ItemStack(Material.LIME_WOOL, 1);
		ItemMeta customk = custom11.getItemMeta();
		customk.setDisplayName("§aComfirmer l'achat");
		custom11.setItemMeta(customk);
		
		ItemStack custom12 = new ItemStack(Material.LIME_WOOL, 64);
		ItemMeta customl = custom12.getItemMeta();
		customl.setDisplayName("§aAcheter des stacks");
		custom12.setItemMeta(customl);
		
		amountchoice.put(custom1, 23);
		amountchoice.put(custom3, 24);
		amountchoice.put(custom2, 25);
		amountchoice.put(custom4, 15);
		amountchoice.put(custom5, 33);
		
		amountchoice.put(custom6, 21);
		amountchoice.put(custom7, 20);
		amountchoice.put(custom8, 19);
		amountchoice.put(custom9, 11);
		amountchoice.put(custom10, 29);
		
		amountchoice.put(custom11, 40);
		amountchoice.put(custom12, 4);
	}
}
