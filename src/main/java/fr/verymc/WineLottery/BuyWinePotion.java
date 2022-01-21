package main.java.fr.verymc.WineLottery;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class BuyWinePotion {

    public static int cidreprice = 5000;
    public static int biereprice = 10000;
    public static int rhumprice = 25000;
    public static int vodkaprice = 50000;

    public static void MakeWinePotionGui(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, "§6Boutique des vins");

        ItemStack custom1 = new ItemStack(Material.POTION, 1);
        ItemMeta meta1 = custom1.getItemMeta();
        meta1.setDisplayName("§6Cidre");
        meta1.setLore(Arrays.asList("§7Ce vin est parfait pour les pauvres", "§7", "§6Prix: 5000$",
                "§7", "§6Gain possible 10000$", "§cEn cas de défaite, vous perdez l'alcool"));
        meta1.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        custom1.setItemMeta(meta1);
        inv.setItem(10, custom1);

        ItemStack custom2 = new ItemStack(Material.POTION, 1);
        ItemMeta meta2 = custom2.getItemMeta();
        meta2.setDisplayName("§6Bière");
        meta2.setLore(Arrays.asList("§7Cette Bière est parfait pour l'apéro entre pote", "§7", "§6Prix: 10000$",
                "§7", "§6Gain possible 20000$", "§cEn cas de défaite, vous perdez l'alcool"));
        meta2.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        custom2.setItemMeta(meta2);
        inv.setItem(11, custom2);

        ItemStack custom3 = new ItemStack(Material.POTION, 1);
        ItemMeta meta3 = custom3.getItemMeta();
        meta3.setDisplayName("§6Rhum");
        meta3.setLore(Arrays.asList("§7Ce rhum est parfait dans une glace", "§7", "§6Prix: 25000$",
                "§7", "§6Gain possible 50000$", "§cEn cas de défaite, vous perdez l'alcool"));
        meta3.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        custom3.setItemMeta(meta3);
        inv.setItem(15, custom3);

        ItemStack custom4 = new ItemStack(Material.POTION, 1);
        ItemMeta meta4 = custom4.getItemMeta();
        meta4.setDisplayName("§6Vodka");
        meta4.setLore(Arrays.asList("§7Cette vodka est parfait pour vous réchauffer le ventre", "§7", "§6Prix: 50000$",
                "§7", "§6Gain possible 100000$", "§cEn cas de défaite, vous perdez l'alcool"));
        meta4.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        custom4.setItemMeta(meta4);
        inv.setItem(16, custom4);

        ItemStack custom5 = new ItemStack(Material.BOOK, 1);
        ItemMeta meta5 = custom5.getItemMeta();
        meta5.setDisplayName("§6Informations");
        meta5.setLore(Arrays.asList("§7Qui est le barman et que fait-il?", "§7Eh bien il vend des fioles d'alcool fictives.",
                "§7Si vous buvez ces fioles, vous pouvez obtenir", "§7un §abonus §7comme un §cmalus§7.", "§7Les §abonus §7et §cmalus §7sont aléatoires",
                "§7Vous pouvez perdre comme gagner", "§7plusieurs fois à la suite.", "§7De plus les probabilitées de §agagner §7descendent",
                "§7en fonction de la taille de la récompense.", "§7", "§7(L'abus d'alcool est dangereux pour la santé)"));
        meta5.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        custom5.setItemMeta(meta5);
        inv.setItem(13, custom5);


        ItemStack custom8 = new ItemStack(Material.WHITE_STAINED_GLASS_PANE, 1);
        ItemMeta meta8 = custom8.getItemMeta();
        meta8.setDisplayName("§6");
        custom8.setItemMeta(meta8);
        for (int i = 0; i < inv.getSize(); i++) {
            if (inv.getItem(i) == null || inv.getItem(i).getType().equals(Material.AIR)) {
                inv.setItem(i, custom8);
            }
        }

        player.openInventory(inv);
    }
}
