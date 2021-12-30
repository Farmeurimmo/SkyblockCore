package fr.farmeurimmo.verymc.gui;

import fr.farmeurimmo.verymc.eco.EcoAccountsManager;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;

public class MakeRanksGui {


    public static void MakeRankGui(Player player) {
        User user = LuckPermsProvider.get().getUserManager().getUser(player.getName());
        String Grade = user.getCachedData().getMetaData().getPrefix().replace("&", "§");


        Inventory invboutiquefarm2win = Bukkit.createInventory(null, 27, "§6Boutique des grades Farm2Win");

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

        ItemStack custom10 = new ItemStack(Material.NETHERITE_BLOCK, 1);
        ItemMeta customi = custom10.getItemMeta();
        if (!player.hasPermission("group.dieu")) {
            customi.setDisplayName("§bDieu");
        } else {
            customi.setDisplayName("§bDieu §c(déjà poss§d§)");
            custom10.setType(Material.BEDROCK);
        }
        customi.setLore(Arrays.asList("§8» Avantages affich§s au spawn."));
        custom10.setItemMeta(customi);
        invboutiquefarm2win.setItem(15, custom10);

        ItemStack custom12 = new ItemStack(Material.GOLD_BLOCK, 1);
        ItemMeta customk = custom12.getItemMeta();
        if (!player.hasPermission("group.legende")) {
            customk.setDisplayName("§aL§gende");
        } else {
            customk.setDisplayName("§aL§gende §c(déjà poss§d§)");
            custom12.setType(Material.BEDROCK);
        }
        customk.setLore(Arrays.asList("§8» Avantages affich§s au spawn."));
        custom12.setItemMeta(customk);
        invboutiquefarm2win.setItem(11, custom12);


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
