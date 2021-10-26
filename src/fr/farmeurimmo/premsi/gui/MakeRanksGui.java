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

public class MakeRanksGui {
	
	@SuppressWarnings("deprecation")
	public static void MakeRankGui(Player player) {
		User user = LuckPermsProvider.get().getUserManager().getUser(player.getName());
		String Grade = user.getCachedData().getMetaData().getPrefix().replace("&", "§");
		TNEAPI ecoAPI = TNE.instance().api();
		
		
		Inventory invboutiquefarm2win = Bukkit.createInventory(null, 27, "§6Boutique des grades Farm2Win");
		
		ItemStack custom2 = new ItemStack(Material.PLAYER_HEAD, 1);
		SkullMeta customb = (SkullMeta) custom2.getItemMeta();
		customb.setOwner(player.getName());
		customb.setDisplayName("§7" + player.getName());
		customb.setLore(Arrays.asList("§7Grade: " + Grade, "§7Argent: " + ecoAPI.getAccount(player.getName()).getHoldings().intValue()));
		custom2.setItemMeta(customb);
		
		ItemStack custom8 = new ItemStack(Material.ARROW, 1);
		ItemMeta customh = custom8.getItemMeta();
		customh.setDisplayName("§6Retour §8| §7(clic gauche)");
		custom8.setItemMeta(customh);
		
		if(!player.hasPermission("zeus")){
		ItemStack custom10 = new ItemStack(Material.WITHER_SKELETON_SKULL, 1);
		ItemMeta customi = custom10.getItemMeta();
		customi.setDisplayName("§bZeus");
		customi.setLore(Arrays.asList("§8» §7/fly permanant","§8» §7/sellall","§8» §7/is rename","§8» §7/enchantement","§8» §7/hat",
				"§8» §7/craft","§8» §7/feed","§7","§6Prix: " + Farm2WinGui.zeusprix + "$"));
		custom10.setItemMeta(customi);
		invboutiquefarm2win.setItem(16, custom10);
		} else {
			ItemStack custom10 = new ItemStack(Material.BEDROCK, 1);
			ItemMeta customi = custom10.getItemMeta();
			customi.setDisplayName("§bZeus §c(Déjà possédé)");
			customi.setLore(Arrays.asList("§8» §7/fly permanant","§8» §7/sellall","§8» §7/is rename","§8» §7/enchantement","§8» §7/hat",
					"§8» §7/craft","§8» §7/feed","§7","§6Prix: " + Farm2WinGui.zeusprix + "$"));
			custom10.setItemMeta(customi);
			invboutiquefarm2win.setItem(16, custom10);
		}
		
		if(!player.hasPermission("dieu")){
		ItemStack custom11 = new ItemStack(Material.BEACON, 1);
		ItemMeta customj = custom11.getItemMeta();
		customj.setDisplayName("§9Dieu");
		customj.setLore(Arrays.asList("§8» §7180 minutes de fly journalier /dailyfly","§8» §7/is rename","§8» §7/enchantement","§8» §7/hat",
				"§8» §7/craft","§8» §7/feed","§7","§6Prix: " + Farm2WinGui.dieuprix + "$"));
		custom11.setItemMeta(customj);
		invboutiquefarm2win.setItem(13, custom11);
		} else {
			ItemStack custom11 = new ItemStack(Material.BEDROCK, 1);
			ItemMeta customj = custom11.getItemMeta();
			customj.setDisplayName("§9Dieu §c(Déjà possédé)");
			customj.setLore(Arrays.asList("§8» §7180 minutes de fly journalier /dailyfly","§8» §7/is rename","§8» §7/enchantement","§8» §7/hat",
					"§8» §7/craft","§8» §7/feed","§7","§6Prix: " + Farm2WinGui.dieuprix + "$"));
			custom11.setItemMeta(customj);
			invboutiquefarm2win.setItem(13, custom11);
		}
		
		if(!player.hasPermission("legende")){
		ItemStack custom12 = new ItemStack(Material.NETHERITE_BLOCK, 1);
		ItemMeta customk = custom12.getItemMeta();
		customk.setDisplayName("§eLégende");
		customk.setLore(Arrays.asList("§8» §790 minutes de fly journalier /dailyfly","§8» §7Accès au shop spawneurs","§8» §7/craft","§8» §7/feed",
				"§7","§6Prix: " + Farm2WinGui.legendeprix + "$"));
		custom12.setItemMeta(customk);
		invboutiquefarm2win.setItem(10, custom12);
		} else {
			ItemStack custom12 = new ItemStack(Material.BEDROCK, 1);
			ItemMeta customk = custom12.getItemMeta();
			customk.setDisplayName("§eLégende §c(Déjà possédé)");
			customk.setLore(Arrays.asList("§8» §790 minutes de fly journalier /dailyfly","§8» §7Accès au shop spawneurs","§8» §7/craft","§8» §7/feed",
					"§7","§6Prix: " + Farm2WinGui.legendeprix + "$"));
			custom12.setItemMeta(customk);
			invboutiquefarm2win.setItem(10, custom12);
		}
		
		invboutiquefarm2win.setItem(26, custom8);
		invboutiquefarm2win.setItem(22, custom2);
		
		ItemStack custom9 = new ItemStack(Material.WHITE_STAINED_GLASS_PANE, 1);
		ItemMeta meta9 = custom9.getItemMeta();
		meta9.setDisplayName("§6");
		custom9.setItemMeta(meta9);
		for (int i = 0; i < invboutiquefarm2win.getSize(); i++) {
            if (invboutiquefarm2win.getItem(i) == null || invboutiquefarm2win.getItem(i).getType().equals(Material.AIR)) {
            	invboutiquefarm2win.setItem(i, custom9);
            }
        }
		
		player.openInventory(invboutiquefarm2win);
	}

}
