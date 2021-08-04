package fr.farmeurimmo.criptmania.gui;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.tnemc.core.TNE;
import net.tnemc.core.common.api.TNEAPI;

public class SanctionSetGui {
	
	@SuppressWarnings("deprecation")
	public static void MakeSSGui(Player player, Player aaa) {
		
		User user = LuckPermsProvider.get().getUserManager().getUser(aaa.getName());
		String Grade = user.getCachedData().getMetaData().getPrefix().replace("&", "§");
		TNEAPI ecoAPI = TNE.instance().api();
		
        Inventory inv = Bukkit.createInventory(null, 27, "§6Menu des Sanctions");
		
		ItemStack custom1 = new ItemStack(Material.WRITABLE_BOOK, 1);
		ItemMeta customa = custom1.getItemMeta();
		customa.setDisplayName("§6Mutes §8| §7(clic)");
		custom1.setItemMeta(customa);
		
		ItemStack custom4 = new ItemStack(Material.ANVIL, 1);
		ItemMeta customd = custom4.getItemMeta();
		customd.setDisplayName("§6Bans §8| §7(clic)");
		custom4.setItemMeta(customd);
		
		ItemStack custom3 = new ItemStack(Material.PLAYER_HEAD, 1);
		SkullMeta customc = (SkullMeta) custom3.getItemMeta();
		customc.setOwner(player.getName());
		customc.setDisplayName("§7" + player.getName());
		customc.setLore(Arrays.asList("§7Grade: " + Grade, "§7Argent: " + ecoAPI.getAccount(aaa.getName()).getHoldings().intValue()));
		custom3.setItemMeta(customc);
		
		ItemStack custom5 = new ItemStack(Material.NAME_TAG, 1);
		ItemMeta custome = custom5.getItemMeta();
		custome.setDisplayName("§6Bans-ip §8| §7(clic)");
		custom5.setItemMeta(custome);
		
		ItemStack custom6 = new ItemStack(Material.BEDROCK, 1);
		ItemMeta customf = custom6.getItemMeta();
		customf.setDisplayName("§6Enlever des sanctions §8| §7(clic)");
		custom6.setItemMeta(customf);
		
		ItemStack custom7 = new ItemStack(Material.IRON_DOOR, 1);
		ItemMeta customg = custom7.getItemMeta();
		customg.setDisplayName("§6Fermer §8| §7(clic)");
		custom7.setItemMeta(customg);
		
		inv.setItem(26, custom7);
		inv.setItem(13, custom3);
		inv.setItem(10, custom1);
		inv.setItem(11, custom4);
		inv.setItem(15, custom5);
		inv.setItem(16, custom6);
		
		ItemStack custom2 = new ItemStack(Material.WHITE_STAINED_GLASS_PANE, 1);
		ItemMeta customb = custom2.getItemMeta();
		customb.setDisplayName("§6");
		custom2.setItemMeta(customb);
		
		for (int i = 0; i < inv.getSize(); i++) {
            if (inv.getItem(i) == null || inv.getItem(i).getType().equals(Material.AIR)) {
            	inv.setItem(i, custom2);
            }
        }
		
		
		player.openInventory(inv);	
	}
	@SuppressWarnings("deprecation")
	public static void MakeMuteGui(Player player, Player aaa) {
		
		User user = LuckPermsProvider.get().getUserManager().getUser(aaa.getName());
		String Grade = user.getCachedData().getMetaData().getPrefix().replace("&", "§");
		TNEAPI ecoAPI = TNE.instance().api();
		
        Inventory inv = Bukkit.createInventory(null, 27, "§6Menu des Mutes");
        
        ItemStack custom3 = new ItemStack(Material.PLAYER_HEAD, 1);
		SkullMeta customc = (SkullMeta) custom3.getItemMeta();
		customc.setOwner(aaa.getName());
		customc.setDisplayName("§7" + aaa.getName());
		customc.setLore(Arrays.asList("§7Grade: " + Grade, "§7Argent: " + ecoAPI.getAccount(aaa.getName()).getHoldings().intValue()));
		custom3.setItemMeta(customc);
		
		ItemStack custom1 = new ItemStack(Material.BOW, 1);
		ItemMeta customa = custom1.getItemMeta();
		customa.setDisplayName("§6Spam §8| §7(clic)");
		custom1.setItemMeta(customa);
		
		ItemStack custom4 = new ItemStack(Material.DIAMOND_SWORD, 1);
		ItemMeta customd = custom4.getItemMeta();
		customd.setDisplayName("§6Flood §8| §7(clic)");
		custom4.setItemMeta(customd);
		
		ItemStack custom5 = new ItemStack(Material.ANVIL, 1);
		ItemMeta custome = custom5.getItemMeta();
		custome.setDisplayName("§6Insultes §8| §7(clic)");
		custom5.setItemMeta(custome);
		
		ItemStack custom6 = new ItemStack(Material.REDSTONE_BLOCK, 1);
		ItemMeta customf = custom6.getItemMeta();
		customf.setDisplayName("§6Provocation §8| §7(clic)");
		custom6.setItemMeta(customf);
		
		ItemStack custom7 = new ItemStack(Material.ACTIVATOR_RAIL, 1);
		ItemMeta customg = custom7.getItemMeta();
		customg.setDisplayName("§6Incitation §8| §7(clic)");
		custom7.setItemMeta(customg);
		
		ItemStack custom8 = new ItemStack(Material.ARMOR_STAND, 1);
		ItemMeta customh = custom8.getItemMeta();
		customh.setDisplayName("§6Contournement §8| §7(clic)");
		custom8.setItemMeta(customh);
		
		ItemStack custom9 = new ItemStack(Material.COMPASS, 1);
		ItemMeta customi = custom9.getItemMeta();
		customi.setDisplayName("§6Pub §8| §7(clic)");
		custom9.setItemMeta(customi);
		
		ItemStack custom10 = new ItemStack(Material.FLINT_AND_STEEL, 1);
		ItemMeta customj = custom10.getItemMeta();
		customj.setDisplayName("§6Menaces IRL/dox/boot/etc §8| §7(clic)");
		custom10.setItemMeta(customj);
		
		ItemStack custom11 = new ItemStack(Material.ARROW, 1);
		ItemMeta customk = custom11.getItemMeta();
		customk.setDisplayName("§6Retour au menu principal §8| §7(clic)");
		custom11.setItemMeta(customk);
		
		inv.setItem(10, custom1);
		inv.setItem(11, custom4);
		inv.setItem(12, custom5);
		inv.setItem(14, custom6);
		inv.setItem(15, custom7);
		inv.setItem(16, custom8);
		inv.setItem(13, custom9);
		inv.setItem(22, custom10);
		inv.setItem(18, custom3);
		inv.setItem(26, custom11);
		
		ItemStack custom2 = new ItemStack(Material.WHITE_STAINED_GLASS_PANE, 1);
		ItemMeta customb = custom2.getItemMeta();
		customb.setDisplayName("§6");
		custom2.setItemMeta(customb);
		
		for (int i = 0; i < inv.getSize(); i++) {
            if (inv.getItem(i) == null || inv.getItem(i).getType().equals(Material.AIR)) {
            	inv.setItem(i, custom2);
            }
        }
		
		
		player.openInventory(inv);
		
		
		
		
		
	}
}
