package fr.farmeurimmo.verymc.gui;

import java.util.Arrays;

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

public class MakeItemGui {
	
	public static void MakeRankGui(Player player) {
		User user = LuckPermsProvider.get().getUserManager().getUser(player.getName());
		String Grade = user.getCachedData().getMetaData().getPrefix().replace("&", "§");
		
		
		Inventory invboutiquefarm2win = Bukkit.createInventory(null, 27, "§6Boutique des items Farm2Win");
		
		ItemStack custom2 = new ItemStack(Material.PLAYER_HEAD, 1);
		SkullMeta customb = (SkullMeta) custom2.getItemMeta();
		customb.setOwner(player.getName());
		customb.setDisplayName("§7" + player.getName());
		customb.setLore(Arrays.asList("§7Grade: " + Grade, "§7Argent: " + EcoAccountsManager.Moneys.get(player.getName())));
		custom2.setItemMeta(customb);
		
		ItemStack custom8 = new ItemStack(Material.ARROW, 1);
		ItemMeta customh = custom8.getItemMeta();
		customh.setDisplayName("§6Retour §8| §7(clic gauche)");
		custom8.setItemMeta(customh);
		
		ItemStack custom10 = new ItemStack(Material.DRAGON_BREATH, 1);
		ItemMeta customi = custom10.getItemMeta();
		customi.setDisplayName("§eMinion piocheur");
		customi.setLore(Arrays.asList("§6Prix: 25000$"));
		custom10.setItemMeta(customi);
		invboutiquefarm2win.setItem(10, custom10);
		
		ItemStack custom11 = new ItemStack(Material.TRAPPED_CHEST, 1);
		ItemMeta customj = custom11.getItemMeta();
		customj.setDisplayName("§dSellChest");
		customj.setLore(Arrays.asList("§6Prix: 1000000$"));
		custom11.setItemMeta(customj);
		invboutiquefarm2win.setItem(16, custom11);
		
		ItemStack custom12 = new ItemStack(Material.CHEST, 1);
		ItemMeta customk = custom12.getItemMeta();
		customk.setDisplayName("§eCollecteur de chunk (chunkhopper)");
		customk.setLore(Arrays.asList("§6Prix: 500000$"));
		custom12.setItemMeta(customk);
		invboutiquefarm2win.setItem(13, custom12);
		
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
