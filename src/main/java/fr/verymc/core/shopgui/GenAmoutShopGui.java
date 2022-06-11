package main.java.fr.verymc.core.shopgui;

import main.java.fr.verymc.utils.PreGenItems;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

public class GenAmoutShopGui {

    public static HashMap<ItemStack, Integer> amountchoice = new HashMap<>();

    public static void OpenPregenAmoutShop(Player player, ItemStack aa, boolean a) {
        Inventory inv;
        if (a == true) {
            inv = Bukkit.createInventory(null, 54, "§6Choix de la quantité à acheter");
        } else {
            inv = Bukkit.createInventory(null, 54, "§6Choix de la quantité à vendre");
        }


        for (Entry<ItemStack, Integer> cc : amountchoice.entrySet()) {
            inv.setItem(cc.getValue(), cc.getKey());
        }
        inv.setItem(49, PreGenItems.instance.getOwnerHead(player));

        ItemStack custom7 = new ItemStack(Material.ARROW, 1);
        ItemMeta customg = custom7.getItemMeta();
        customg.setDisplayName("§6Retour en arrière");
        custom7.setItemMeta(customg);
        inv.setItem(53, custom7);

        ItemStack custom11 = new ItemStack(Material.LIME_WOOL, 1);
        ItemMeta customk = custom11.getItemMeta();
        if (a == true) {
            customk.setDisplayName("§aComfirmer l'achat");
        } else {
            customk.setDisplayName("§aComfirmer la vente");
        }
        custom11.setItemMeta(customk);

        ItemStack custom4 = new ItemStack(Material.GREEN_STAINED_GLASS, 64);
        ItemMeta customd = custom4.getItemMeta();
        if (a == true) {
            customd.setDisplayName("§aAchat par stacks");
        } else {
            customd.setDisplayName("§aTout vendre");
        }
        custom4.setItemMeta(customd);

        double price = 0.0;
        ItemMeta tempameta = aa.getItemMeta();
        tempameta.setLore(null);
        if (a == true) {
            if (aa.getType() != Material.SPAWNER) {
                price = BuyShopItem.pricesbuy.get(new ItemStack(Material.valueOf(aa.getType().toString())));
            } else {
                price = BuyShopItem.pricesbuy.get(aa);
            }
            tempameta.setLore(Arrays.asList("§6Prix d'achat: §c" + DecimalFormat.getNumberInstance().format(price) +
                    " $/u", "§6Total: §c" + DecimalFormat.getNumberInstance().format(price * aa.getAmount()) + " $"));
            ItemMeta temp = custom11.getItemMeta();
            temp.setLore(Arrays.asList("§aTotal: §c" + DecimalFormat.getNumberInstance().format(price * aa.getAmount()) + " $"));
            custom11.setItemMeta(temp);
        } else {
            price = BuyShopItem.pricessell.get(new ItemStack(Material.valueOf(aa.getType().toString())));
            tempameta.setLore(Arrays.asList("§6Prix de vente: §a" + DecimalFormat.getNumberInstance().format(price) +
                    " $/u", "§6Total: §a" + DecimalFormat.getNumberInstance().format(price * aa.getAmount()) + " $"));
            ItemMeta temp = custom11.getItemMeta();
            temp.setLore(Arrays.asList("§aTotal: §a" + DecimalFormat.getNumberInstance().format(price * aa.getAmount()) + " $"));
            custom11.setItemMeta(temp);
        }
        aa.setItemMeta(tempameta);
        inv.setItem(22, aa);

        inv.setItem(40, custom11);
        inv.setItem(45, custom4);

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
    }
}
