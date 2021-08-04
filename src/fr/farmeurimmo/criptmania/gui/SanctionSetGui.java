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
		
        Inventory inv = Bukkit.createInventory(null, 27, "§6Boutique Farm2Win");
		
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
}
