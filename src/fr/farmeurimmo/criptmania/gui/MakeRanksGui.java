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

public class MakeRanksGui {
	
	@SuppressWarnings("deprecation")
	public static void MakeRankGui(Player player) {
		User user = LuckPermsProvider.get().getUserManager().getUser(player.getName());
		String Grade = user.getCachedData().getMetaData().getPrefix().replace("&", "§");
		TNEAPI ecoAPI = TNE.instance().api();
		
		
		Inventory invboutiquefarm2win = Bukkit.createInventory(null, 45, "§6Boutique des grades Farm2Win");
		
		ItemStack custom2 = new ItemStack(Material.PLAYER_HEAD, 1);
		SkullMeta customb = (SkullMeta) custom2.getItemMeta();
		customb.setOwner(player.getName());
		customb.setDisplayName("§7" + player.getName());
		customb.setLore(Arrays.asList("§7Grade: " + Grade, "§7Argent: " + ecoAPI.getAccount(player.getName()).getHoldings().intValue()));
		custom2.setItemMeta(customb);
		
		if(!player.hasPermission("vip")) {
		ItemStack custom3 = new ItemStack(Material.GOLD_BLOCK, 1);
		ItemMeta customc = custom3.getItemMeta();
		customc.setDisplayName("§eV.I.P");
		customc.setLore(Arrays.asList("§7Uniquement disponible à ", "§7l'achat sur le site du serveur"));
		custom3.setItemMeta(customc);
		invboutiquefarm2win.setItem(34, custom3);
		} else {
			ItemStack custom3 = new ItemStack(Material.BEDROCK, 1);
			ItemMeta customc = custom3.getItemMeta();
			customc.setDisplayName("§eV.I.P");
			customc.setLore(Arrays.asList("§7Uniquement disponible à ", "§7l'achat sur le site du serveur"));
			custom3.setItemMeta(customc);
			invboutiquefarm2win.setItem(34, custom3);
		}
		
		if(!player.hasPermission("premium")) {
		ItemStack custom4 = new ItemStack(Material.DIAMOND_BLOCK, 1);
		ItemMeta customd = custom4.getItemMeta();
		customd.setDisplayName("§aPremium");
		customd.setLore(Arrays.asList("§7Uniquement disponible à ", "§7l'achat sur le site du serveur"));
		custom4.setItemMeta(customd);
		invboutiquefarm2win.setItem(25, custom4);
		} else {
			ItemStack custom4 = new ItemStack(Material.BEDROCK, 1);
			ItemMeta customd = custom4.getItemMeta();
			customd.setDisplayName("§aPremium");
			customd.setLore(Arrays.asList("§7Uniquement disponible à ", "§7l'achat sur le site du serveur"));
			custom4.setItemMeta(customd);
			invboutiquefarm2win.setItem(25, custom4);
		}
		
		if(!player.hasPermission("elite")){
		ItemStack custom5 = new ItemStack(Material.EMERALD_BLOCK, 1);
		ItemMeta custome = custom5.getItemMeta();
		custome.setDisplayName("§cElite");
		custome.setLore(Arrays.asList("§7Uniquement disponible à ", "§7l'achat sur le site du serveur"));
		custom5.setItemMeta(custome);
		invboutiquefarm2win.setItem(16, custom5);
		} else {
			ItemStack custom5 = new ItemStack(Material.BEDROCK, 1);
			ItemMeta custome = custom5.getItemMeta();
			custome.setDisplayName("§cElite");
			custome.setLore(Arrays.asList("§7Uniquement disponible à ", "§7l'achat sur le site du serveur"));
			custom5.setItemMeta(custome);
			invboutiquefarm2win.setItem(16, custom5);
		}
		
		if(!player.hasPermission("mania")){
		ItemStack custom6 = new ItemStack(Material.DIAMOND_SWORD, 1);
		ItemMeta customf = custom6.getItemMeta();
		customf.setDisplayName("§dMania");
		customf.setLore(Arrays.asList("§7Uniquement disponible à ", "§7l'achat sur le lobby"));
		custom6.setItemMeta(customf);
		invboutiquefarm2win.setItem(13, custom6);
		} else {
			ItemStack custom6 = new ItemStack(Material.BEDROCK, 1);
			ItemMeta customf = custom6.getItemMeta();
			customf.setDisplayName("§dMania");
			customf.setLore(Arrays.asList("§7Uniquement disponible à ", "§7l'achat sur le lobby"));
			custom6.setItemMeta(customf);
			invboutiquefarm2win.setItem(13, custom6);
		}
		
		if(!player.hasPermission("rusher")){
		ItemStack custom7 = new ItemStack(Material.IRON_SWORD, 1);
		ItemMeta customg = custom7.getItemMeta();
		customg.setDisplayName("§bRusher");
		customg.setLore(Arrays.asList("§7Uniquement disponible à ", "§7l'achat sur le lobby"));
		custom7.setItemMeta(customg);
		invboutiquefarm2win.setItem(22, custom7);
		} else {
			ItemStack custom7 = new ItemStack(Material.BEDROCK, 1);
			ItemMeta customg = custom7.getItemMeta();
			customg.setDisplayName("§bRusher");
			customg.setLore(Arrays.asList("§7Uniquement disponible à ", "§7l'achat sur le lobby"));
			custom7.setItemMeta(customg);
			invboutiquefarm2win.setItem(22, custom7);
		}
		
		ItemStack custom8 = new ItemStack(Material.ARROW, 1);
		ItemMeta customh = custom8.getItemMeta();
		customh.setDisplayName("§6Retour §8| §7(clic gauche)");
		custom8.setItemMeta(customh);
		
		if(!player.hasPermission("zeus")){
		ItemStack custom10 = new ItemStack(Material.WITHER_SKELETON_SKULL, 1);
		ItemMeta customi = custom10.getItemMeta();
		customi.setDisplayName("§bZeus");
		customi.setLore(Arrays.asList("§7","§8» §7Grade Joueur §75 slots amis et 5 slots de groupe","§8» §bGrade Rusher §76 slots amis et 5 slots de groupe"));
		custom10.setItemMeta(customi);
		invboutiquefarm2win.setItem(10, custom10);
		} else {
			ItemStack custom10 = new ItemStack(Material.BEDROCK, 1);
			ItemMeta customi = custom10.getItemMeta();
			customi.setDisplayName("§bZeus");
			customi.setLore(Arrays.asList("§7soon", "§7"));
			custom10.setItemMeta(customi);
			invboutiquefarm2win.setItem(10, custom10);
		}
		
		if(!player.hasPermission("dieu")){
		ItemStack custom11 = new ItemStack(Material.BEACON, 1);
		ItemMeta customj = custom11.getItemMeta();
		customj.setDisplayName("§9Dieu");
		customj.setLore(Arrays.asList("§7soon", "§7"));
		custom11.setItemMeta(customj);
		invboutiquefarm2win.setItem(19, custom11);
		} else {
			ItemStack custom11 = new ItemStack(Material.BEDROCK, 1);
			ItemMeta customj = custom11.getItemMeta();
			customj.setDisplayName("§9Dieu");
			customj.setLore(Arrays.asList("§7soon", "§7"));
			custom11.setItemMeta(customj);
			invboutiquefarm2win.setItem(19, custom11);
		}
		
		if(!player.hasPermission("legende")){
		ItemStack custom12 = new ItemStack(Material.NETHERITE_BLOCK, 1);
		ItemMeta customk = custom12.getItemMeta();
		customk.setDisplayName("§eLégende");
		customk.setLore(Arrays.asList("§7soon", "§7"));
		custom12.setItemMeta(customk);
		invboutiquefarm2win.setItem(28, custom12);
		} else {
			ItemStack custom12 = new ItemStack(Material.BEDROCK, 1);
			ItemMeta customk = custom12.getItemMeta();
			customk.setDisplayName("§eLégende");
			customk.setLore(Arrays.asList("§7soon", "§7"));
			custom12.setItemMeta(customk);
			invboutiquefarm2win.setItem(28, custom12);
		}
		
		invboutiquefarm2win.setItem(44, custom8);
		invboutiquefarm2win.setItem(40, custom2);
		
		ItemStack custom9 = new ItemStack(Material.WHITE_STAINED_GLASS_PANE, 1);
		ItemMeta meta9 = custom9.getItemMeta();
		meta9.setDisplayName("§6");
		custom9.setItemMeta(meta9);
		invboutiquefarm2win.setItem(0, custom9);
		invboutiquefarm2win.setItem(1, custom9);
		invboutiquefarm2win.setItem(2, custom9);
		invboutiquefarm2win.setItem(3, custom9);
		invboutiquefarm2win.setItem(4, custom9);
		invboutiquefarm2win.setItem(5, custom9);
		invboutiquefarm2win.setItem(6, custom9);
		invboutiquefarm2win.setItem(7, custom9);
		invboutiquefarm2win.setItem(8, custom9);
		invboutiquefarm2win.setItem(9, custom9);
		invboutiquefarm2win.setItem(11, custom9);
		invboutiquefarm2win.setItem(12, custom9);
		invboutiquefarm2win.setItem(14, custom9);
		invboutiquefarm2win.setItem(15, custom9);
		invboutiquefarm2win.setItem(17, custom9);
		invboutiquefarm2win.setItem(18, custom9);
		invboutiquefarm2win.setItem(20, custom9);
		invboutiquefarm2win.setItem(21, custom9);
		invboutiquefarm2win.setItem(23, custom9);
		invboutiquefarm2win.setItem(24, custom9);
		invboutiquefarm2win.setItem(26, custom9);
		invboutiquefarm2win.setItem(27, custom9);
		invboutiquefarm2win.setItem(29, custom9);
		invboutiquefarm2win.setItem(30, custom9);
		invboutiquefarm2win.setItem(31, custom9);
		invboutiquefarm2win.setItem(32, custom9);
		invboutiquefarm2win.setItem(33, custom9);
		invboutiquefarm2win.setItem(35, custom9);
		invboutiquefarm2win.setItem(36, custom9);
		invboutiquefarm2win.setItem(37, custom9);
		invboutiquefarm2win.setItem(38, custom9);
		invboutiquefarm2win.setItem(39, custom9);
		invboutiquefarm2win.setItem(41, custom9);
		invboutiquefarm2win.setItem(42, custom9);
		invboutiquefarm2win.setItem(43, custom9);
		
		player.openInventory(invboutiquefarm2win);
	}

}
