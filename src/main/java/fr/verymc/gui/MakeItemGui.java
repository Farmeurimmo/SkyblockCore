package main.java.fr.verymc.gui;

import main.java.fr.verymc.utils.PreGenItems;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class MakeItemGui {

    public static void MakeRankGui(Player player) {


        Inventory invboutiquefarm2win = Bukkit.createInventory(null, 27, "§6Boutique des items Farm2Win");

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
        invboutiquefarm2win.setItem(12, custom11);

        ItemStack custom12 = new ItemStack(Material.HOPPER, 1);
        ItemMeta customk = custom12.getItemMeta();
        customk.setDisplayName("§eCollecteur de chunk (chunkhopper)");
        customk.setLore(Arrays.asList("§6Prix: 500000$"));
        custom12.setItemMeta(customk);
        invboutiquefarm2win.setItem(14, custom12);

        ItemStack custom13 = new ItemStack(Material.NETHERITE_HOE, 1);
        ItemMeta customl = custom13.getItemMeta();
        customl.setDisplayName("§eFarmHoe §cTier I (1x1)");
        customl.setLore(Arrays.asList("§6Prix: 500000$"));
        custom13.setItemMeta(customl);
        invboutiquefarm2win.setItem(16, custom13);

        invboutiquefarm2win.setItem(26, custom8);
        invboutiquefarm2win.setItem(22, PreGenItems.getOwnerHead(player));

        player.openInventory(invboutiquefarm2win);
    }
}
