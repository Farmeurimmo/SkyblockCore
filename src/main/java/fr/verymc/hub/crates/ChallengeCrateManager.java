package main.java.fr.verymc.hub.crates;

import main.java.fr.verymc.core.eco.EcoAccountsManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Random;

public class ChallengeCrateManager {

    public static String ChallengeCrateLoot(Player player) {
        String loot = null;
        Random rand = new Random();
        loot = "error";
        int n = rand.nextInt(100);
        if (n >= 0 && n <= 11) {
            loot = "5 000$";
            EcoAccountsManager.instance.addFounds(player, (double) 5000, false);
            return loot;
        }
        if (n >= 12 && n <= 17) {
            loot = "10 000$";
            EcoAccountsManager.instance.addFounds(player, (double) 10000, false);
            return loot;
        }
        if (n >= 18 && n <= 20) {
            loot = "15 000$";
            EcoAccountsManager.instance.addFounds(player, (double) 15000, false);
            return loot;
        }
        if (n >= 21 && n <= 27) {
            loot = "x2 Clée challenge";
            CratesKeyManager.GiveCrateKey(player, 2, "Challenge");
            return loot;
        }
        if (n == 28) {
            loot = "x1 Clée légendaire";
            CratesKeyManager.GiveCrateKey(player, 1, "légendaire");
            return loot;
        }
        if (n >= 29 && n <= 33) {
            loot = "x6 Bloc de netherite";
            player.getInventory().addItem(new ItemStack(Material.NETHERITE_BLOCK, 6));
            return loot;
        }
        if (n >= 34 && n <= 38) {
            loot = "x12 Bloc d'émeraude";
            player.getInventory().addItem(new ItemStack(Material.EMERALD_BLOCK, 12));
            return loot;
        }
        if (n >= 39 && n <= 46) {
            loot = "x18 Bloc de diamant";
            player.getInventory().addItem(new ItemStack(Material.DIAMOND_BLOCK, 18));
            return loot;
        }
        if (n >= 47 && n <= 56) {
            loot = "x24 Bloc d'or";
            player.getInventory().addItem(new ItemStack(Material.GOLD_BLOCK, 24));
            return loot;
        }
        if (n >= 57 && n <= 68) {
            loot = "x32 Bloc de fer";
            player.getInventory().addItem(new ItemStack(Material.IRON_BLOCK, 32));
            return loot;
        }
        if (n >= 69 && n <= 84) {
            loot = "x48 Bloc de charbon";
            player.getInventory().addItem(new ItemStack(Material.COAL_BLOCK, 48));
            return loot;
        }
        if (n >= 85 && n <= 91) {
            loot = "x1 Fly de 5 minutes";
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "featherfly give " + player.getName() + " 5min");
            return loot;
        }
        if (n >= 92 && n <= 96) {
            loot = "x1 Fly de 10 minutes";
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "featherfly give " + player.getName() + " 10min");
            return loot;
        }
        if (n >= 97 && n <= 99) {
            loot = "x1 Fly de 20 minutes";
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "featherfly give " + player.getName() + " 20min");
            return loot;
        }
        return loot;
    }

    public static void ChallengeCratePreview(Player player) {
        Inventory inv = Bukkit.createInventory(null, 36, "§6Boxe challenge");

        ItemStack custom1 = new ItemStack(Material.SUNFLOWER, 1);
        ItemMeta meta1 = custom1.getItemMeta();
        meta1.setDisplayName("§65 000$");
        meta1.setLore(Arrays.asList("§7", "§e12%"));
        custom1.setItemMeta(meta1);
        inv.setItem(10, custom1);

        ItemStack custom2 = new ItemStack(Material.SUNFLOWER, 2);
        ItemMeta meta2 = custom2.getItemMeta();
        meta2.setDisplayName("§610 000$");
        meta2.setLore(Arrays.asList("§7", "§e6%"));
        custom2.setItemMeta(meta2);
        inv.setItem(11, custom2);

        ItemStack custom3 = new ItemStack(Material.SUNFLOWER, 3);
        ItemMeta meta3 = custom3.getItemMeta();
        meta3.setDisplayName("§615 000$");
        meta3.setLore(Arrays.asList("§7", "§e4%"));
        custom3.setItemMeta(meta3);
        inv.setItem(12, custom3);

        ItemStack custom4 = new ItemStack(Material.TRIPWIRE_HOOK, 2);
        ItemMeta meta4 = custom4.getItemMeta();
        meta4.addEnchant(Enchantment.DURABILITY, 10, true);
        meta4.setUnbreakable(true);
        meta4.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta4.setDisplayName("§6§lx2 Clée challenge");
        meta4.setLore(Arrays.asList("§7", "§e7%"));
        custom4.setItemMeta(meta4);
        inv.setItem(13, custom4);

        ItemStack custom5 = new ItemStack(Material.TRIPWIRE_HOOK, 1);
        ItemMeta meta5 = custom5.getItemMeta();
        meta5.addEnchant(Enchantment.DURABILITY, 10, true);
        meta5.setUnbreakable(true);
        meta5.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta5.setDisplayName("§6§lx1 Clée légendaire");
        meta5.setLore(Arrays.asList("§7", "§e1%"));
        custom5.setItemMeta(meta5);
        inv.setItem(14, custom5);

        ItemStack custom6 = new ItemStack(Material.NETHERITE_BLOCK, 6);
        ItemMeta meta6 = custom6.getItemMeta();
        meta6.setDisplayName("§66 Blocs de netherite");
        meta6.setLore(Arrays.asList("§7", "§e5%"));
        custom6.setItemMeta(meta6);
        inv.setItem(15, custom6);

        ItemStack custom7 = new ItemStack(Material.EMERALD_BLOCK, 12);
        ItemMeta meta7 = custom7.getItemMeta();
        meta7.setDisplayName("§612 Blocs d'émeraude");
        meta7.setLore(Arrays.asList("§7", "§e5%"));
        custom7.setItemMeta(meta7);
        inv.setItem(16, custom7);

        ItemStack custom8 = new ItemStack(Material.DIAMOND_BLOCK, 18);
        ItemMeta meta8 = custom8.getItemMeta();
        meta8.setDisplayName("§618 Blocs de diamant");
        meta8.setLore(Arrays.asList("§8", "§e8%"));
        custom8.setItemMeta(meta8);
        inv.setItem(19, custom8);

        ItemStack custom9 = new ItemStack(Material.GOLD_BLOCK, 24);
        ItemMeta meta9 = custom9.getItemMeta();
        meta9.setDisplayName("§624 Blocs d'or");
        meta9.setLore(Arrays.asList("§9", "§e10%"));
        custom9.setItemMeta(meta9);
        inv.setItem(20, custom9);

        ItemStack custom10 = new ItemStack(Material.IRON_BLOCK, 32);
        ItemMeta meta10 = custom10.getItemMeta();
        meta10.setDisplayName("§632 Blocs de fer");
        meta10.setLore(Arrays.asList("§1", "§e12%"));
        custom10.setItemMeta(meta10);
        inv.setItem(21, custom10);

        ItemStack custom11 = new ItemStack(Material.COAL_BLOCK, 48);
        ItemMeta meta11 = custom11.getItemMeta();
        meta11.setDisplayName("§648 Blocs de charbon");
        meta11.setLore(Arrays.asList("§1", "§e16%"));
        custom11.setItemMeta(meta11);
        inv.setItem(22, custom11);

        ItemStack custom12 = new ItemStack(Material.FEATHER, 1);
        ItemMeta meta12 = custom12.getItemMeta();
        meta12.setDisplayName("§6Fly de 5 minutes");
        meta12.setLore(Arrays.asList("§1", "§e7%"));
        custom12.setItemMeta(meta12);
        inv.setItem(23, custom12);

        ItemStack custom13 = new ItemStack(Material.FEATHER, 1);
        ItemMeta meta13 = custom13.getItemMeta();
        meta13.setDisplayName("§6Fly de 10 minutes");
        meta13.setLore(Arrays.asList("§1", "§e5%"));
        custom13.setItemMeta(meta13);
        inv.setItem(24, custom13);

        ItemStack custom14 = new ItemStack(Material.FEATHER, 1);
        ItemMeta meta14 = custom14.getItemMeta();
        meta14.setDisplayName("§6Fly de 20 minutes");
        meta14.setLore(Arrays.asList("§1", "§e3%"));
        custom14.setItemMeta(meta14);
        inv.setItem(25, custom14);


        player.openInventory(inv);
    }

}
