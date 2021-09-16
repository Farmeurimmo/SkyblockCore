package fr.farmeurimmo.premsi.gui;

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

public class MakeCommandsGui {
	
	@SuppressWarnings("deprecation")
	public static void MakeCommandGui(Player player) {
		User user = LuckPermsProvider.get().getUserManager().getUser(player.getName());
		String Grade = user.getCachedData().getMetaData().getPrefix().replace("&", "§");
		TNEAPI ecoAPI = TNE.instance().api();
		
		
		Inventory inv = Bukkit.createInventory(null, 36, "§6Boutique des commandes Farm2Win");
		
		ItemStack custom2 = new ItemStack(Material.PLAYER_HEAD, 1);
		SkullMeta customb = (SkullMeta) custom2.getItemMeta();
		customb.setOwner(player.getName());
		customb.setDisplayName("§7" + player.getName());
		customb.setLore(Arrays.asList("§7Grade: " + Grade, "§7Argent: " + ecoAPI.getAccount(player.getName()).getHoldings().intValue()));
		custom2.setItemMeta(customb);
		inv.setItem(31, custom2);
		
		ItemStack custom10 = new ItemStack(Material.BEDROCK, 1);
		ItemMeta customi = custom10.getItemMeta();
		customi.setDisplayName("§cCommande déjà possédée");
		custom10.setItemMeta(customi);
		
		if(!player.hasPermission("hat")) {
		ItemStack custom3 = new ItemStack(Material.LEATHER_HELMET);
		ItemMeta customc = custom3.getItemMeta();
		customc.setDisplayName("§e/hat");
		customc.setLore(Arrays.asList("§7Cette commande permet d'équiper le","§7bloc présent dans votre main sur","§7votre tête","§7","§6Prix: " + Farm2WinGui.hatprix + "$"));
		custom3.setItemMeta(customc);
		inv.setItem(10, custom3);
		} else {
			ItemStack custom3 = new ItemStack(Material.BEDROCK);
			ItemMeta customc = custom3.getItemMeta();
			customc.setDisplayName("§e/hat §c(Déjà possédée)");
			customc.setLore(Arrays.asList("§7Cette commande permet d'équiper le","§7bloc présent dans votre main sur","§7votre tête","§7","§6Prix: " + Farm2WinGui.hatprix + "$"));
			custom3.setItemMeta(customc);
			inv.setItem(10, custom3);
		}
		
		if(!player.hasPermission("fly")) {
		ItemStack custom4 = new ItemStack(Material.FEATHER, 1);
		ItemMeta customd = custom4.getItemMeta();
		customd.setDisplayName("§e/fly");
		customd.setLore(Arrays.asList("§7Cette commande permet de voler","§7sur votre île indéfiniment","§7","§6Prix: " + Farm2WinGui.flyprix + "$"));
		custom4.setItemMeta(customd);
		inv.setItem(12, custom4);
		} else {
			ItemStack custom4 = new ItemStack(Material.BEDROCK, 1);
			ItemMeta customd = custom4.getItemMeta();
			customd.setDisplayName("§e/fly §c(Déjà possédée)");
			customd.setLore(Arrays.asList("§7Cette commande permet de voler","§7sur votre île indéfiniment","§7","§6Prix: " + Farm2WinGui.flyprix + "$"));
			custom4.setItemMeta(customd);
			inv.setItem(12, custom4);
		}
		
		if(!player.hasPermission("feed")) {
		ItemStack custom5 = new ItemStack(Material.COOKED_BEEF, 1);
		ItemMeta custome = custom5.getItemMeta();
		custome.setDisplayName("§e/feed");
		custome.setLore(Arrays.asList("§7Cette commande permet de","§7vous redonnez toute votre nourriture","§7","§6Prix: " + Farm2WinGui.feedprix + "$"));
		custom5.setItemMeta(custome);
		inv.setItem(14, custom5);
		} else {
			ItemStack custom5 = new ItemStack(Material.BEDROCK, 1);
			ItemMeta custome = custom5.getItemMeta();
			custome.setDisplayName("§e/feed §c(Déjà possédée)");
			custome.setLore(Arrays.asList("§7Cette commande permet de","§7vous redonnez toute votre nourriture","§7","§6Prix: " + Farm2WinGui.feedprix + "$"));
			custom5.setItemMeta(custome);
			inv.setItem(14, custom5);
		}
		
		if(!player.hasPermission("craft")) {
		ItemStack custom6 = new ItemStack(Material.CRAFTING_TABLE, 1);
		ItemMeta customf = custom6.getItemMeta();
		customf.setDisplayName("§e/craft");
		customf.setLore(Arrays.asList("§7Cette commande permet de","§7d'ouvrir une table de craft n'importe oû","§7","§6Prix: " + Farm2WinGui.craftprix + "$"));
		custom6.setItemMeta(customf);
		inv.setItem(16, custom6);
		} else {
			ItemStack custom6 = new ItemStack(Material.BEDROCK, 1);
			ItemMeta customf = custom6.getItemMeta();
			customf.setDisplayName("§e/craft §c(Déjà possédée)");
			customf.setLore(Arrays.asList("§7Cette commande permet de","§7d'ouvrir une table de craft n'importe oû","§7","§6Prix: " + Farm2WinGui.craftprix + "$"));
			custom6.setItemMeta(customf);
			inv.setItem(16, custom6);
		}
		
		if(!player.hasPermission("iridiumskyblock.rename")) {
		ItemStack custom7 = new ItemStack(Material.NAME_TAG, 1);
		ItemMeta customg = custom7.getItemMeta();
		customg.setDisplayName("§e/is rename");
		customg.setLore(Arrays.asList("§7Cette commande permet de","§7de renomer votre île","§7","§6Prix: " + Farm2WinGui.isrenameprix + "$"));
		custom7.setItemMeta(customg);
		inv.setItem(20, custom7);
		} else {
			ItemStack custom7 = new ItemStack(Material.BEDROCK, 1);
			ItemMeta customg = custom7.getItemMeta();
			customg.setDisplayName("§e/is rename §c(Déjà possédée)");
			customg.setLore(Arrays.asList("§7Cette commande permet de","§7de renomer votre île","§7","§6Prix: " + Farm2WinGui.isrenameprix + "$"));
			custom7.setItemMeta(customg);
			inv.setItem(20, custom7);
		}
		
		if(!player.hasPermission("economyshopgui.sellall")) {
			ItemStack custom7 = new ItemStack(Material.OAK_SIGN, 1);
			ItemMeta customg = custom7.getItemMeta();
			customg.setDisplayName("§e/sellall");
			customg.setLore(Arrays.asList("§7Cette commande permet de","§7de vendre tous les items","§7vendables dans votre inventaire","§7","§6Prix: " + Farm2WinGui.sellallprix + "$"));
			custom7.setItemMeta(customg);
			inv.setItem(22, custom7);
			} else {
				ItemStack custom7 = new ItemStack(Material.BEDROCK, 1);
				ItemMeta customg = custom7.getItemMeta();
				customg.setDisplayName("§e/sellall §c(Déjà possédée)");
				customg.setLore(Arrays.asList("§7Cette commande permet de","§7de vendre tous les items","§7vendables dans votre inventaire","§7","§6Prix: " + Farm2WinGui.sellallprix + "$"));
				custom7.setItemMeta(customg);
				inv.setItem(22, custom7);
			}
		
		if(!player.hasPermission("enchantement")) {
		ItemStack custom1 = new ItemStack(Material.ENCHANTING_TABLE, 1);
		ItemMeta customa = custom1.getItemMeta();
		customa.setDisplayName("§e/enchantement");
		customa.setLore(Arrays.asList("§7Cette commande permet de","§7d'ouvrir une table d'enchantement level 30 n'importe oû","§7","§6Prix: " + Farm2WinGui.enchantementprix + "$"));
		custom1.setItemMeta(customa);
		inv.setItem(24, custom1);
		} else {
			ItemStack custom1 = new ItemStack(Material.BEDROCK, 1);
			ItemMeta customa = custom1.getItemMeta();
			customa.setDisplayName("§e/enchantement §c(Déjà possédée)");
			customa.setLore(Arrays.asList("§7Cette commande permet de","§7d'ouvrir une table d'enchantement n'importe oû","§7","§6Prix: " + Farm2WinGui.enchantementprix + "$"));
			custom1.setItemMeta(customa);
			inv.setItem(24, custom1);
		}
		
		ItemStack custom8 = new ItemStack(Material.ARROW, 1);
		ItemMeta customh = custom8.getItemMeta();
		customh.setDisplayName("§6Retour §8| §7(clic gauche)");
		custom8.setItemMeta(customh);
		inv.setItem(35, custom8);
		
		ItemStack custom9 = new ItemStack(Material.WHITE_STAINED_GLASS_PANE, 1);
		ItemMeta meta9 = custom9.getItemMeta();
		meta9.setDisplayName("§6");
		custom9.setItemMeta(meta9);
		
		for (int i = 0; i < inv.getSize(); i++) {
            if (inv.getItem(i) == null || inv.getItem(i).getType().equals(Material.AIR)) {
            	inv.setItem(i, custom9);
            }
        }
		
		player.openInventory(inv);
	}

}
