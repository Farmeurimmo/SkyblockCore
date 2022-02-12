package main.java.fr.verymc.shopgui;

import main.java.fr.verymc.gui.MenuGui;
import main.java.fr.verymc.utils.PreGenItems;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MainShopGui implements Listener {

    public static void OpenMainShopMenu(Player player) {

        Inventory inv = Bukkit.createInventory(null, 45, "§6Boutique");

        ItemStack custom1 = new ItemStack(Material.GRASS_BLOCK, 1);
        ItemMeta customa = custom1.getItemMeta();
        customa.setDisplayName("§6Blocs");
        custom1.setItemMeta(customa);
        inv.setItem(10, custom1);

        ItemStack custom3 = new ItemStack(Material.LIME_DYE, 1);
        ItemMeta customc = custom3.getItemMeta();
        customc.setDisplayName("§6Colorants");
        custom3.setItemMeta(customc);
        inv.setItem(16, custom3);

        ItemStack custom2 = new ItemStack(Material.WHEAT, 1);
        ItemMeta customb = custom2.getItemMeta();
        customb.setDisplayName("§6Agriculture");
        custom2.setItemMeta(customb);
        inv.setItem(12, custom2);

        ItemStack custom4 = new ItemStack(Material.COOKED_BEEF, 1);
        ItemMeta customd = custom4.getItemMeta();
        customd.setDisplayName("§6Nourritures");
        custom4.setItemMeta(customd);
        inv.setItem(14, custom4);

        ItemStack custom5 = new ItemStack(Material.DIAMOND_ORE, 1);
        ItemMeta custome = custom5.getItemMeta();
        custome.setDisplayName("§6Minerais");
        custom5.setItemMeta(custome);
        inv.setItem(20, custom5);

        ItemStack custom6 = new ItemStack(Material.BELL, 1);
        ItemMeta customf = custom6.getItemMeta();
        customf.setDisplayName("§6Autres");
        custom6.setItemMeta(customf);
        inv.setItem(22, custom6);

        ItemStack custom7 = new ItemStack(Material.ARROW, 1);
        ItemMeta customg = custom7.getItemMeta();
        customg.setDisplayName("§6Retour au menu principal");
        custom7.setItemMeta(customg);
        inv.setItem(44, custom7);

        ItemStack custom9 = new ItemStack(Material.GUNPOWDER, 1);
        ItemMeta customh = custom9.getItemMeta();
        customh.setDisplayName("§6Loots de mobs");
        custom9.setItemMeta(customh);
        inv.setItem(24, custom9);

        ItemStack custom11 = new ItemStack(Material.REDSTONE, 1);
        ItemMeta customj = custom11.getItemMeta();
        customj.setDisplayName("§6Redstone");
        custom11.setItemMeta(customj);
        inv.setItem(30, custom11);

        ItemStack custom12 = new ItemStack(Material.SPAWNER, 1);
        ItemMeta customk = custom12.getItemMeta();
        customk.setDisplayName("§6Spawneurs");
        custom12.setItemMeta(customk);
        inv.setItem(32, custom12);


        inv.setItem(36, PreGenItems.getOwnerHead(player));


        player.openInventory(inv);
    }

    @EventHandler
    public void OnInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        ItemStack current = e.getCurrentItem();
        if (current == null) {
            return;
        }
        if (e.getView().getTitle().equalsIgnoreCase("§6Boutique")) {
            e.setCancelled(true);
            if (current.getType() == Material.GRASS_BLOCK) {
                GenShopPage.OpenPreGenPage(player, "Blocs", 1);
            }
            if (current.getType() == Material.LIME_DYE) {
                GenShopPage.OpenPreGenPage(player, "Colorants", 1);
            }
            if (current.getType() == Material.WHEAT) {
                GenShopPage.OpenPreGenPage(player, "Agriculture", 1);
            }
            if (current.getType() == Material.COOKED_BEEF) {
                GenShopPage.OpenPreGenPage(player, "Nourritures", 1);
            }
            if (current.getType() == Material.DIAMOND_ORE) {
                GenShopPage.OpenPreGenPage(player, "Minerais", 1);
            }
            if (current.getType() == Material.BELL) {
                GenShopPage.OpenPreGenPage(player, "Autres", 1);
            }
            if (current.getType() == Material.GUNPOWDER) {
                GenShopPage.OpenPreGenPage(player, "Drops", 1);
            }
            if (current.getType() == Material.REDSTONE) {
                GenShopPage.OpenPreGenPage(player, "Redstone", 1);
            }
            if (current.getType() == Material.SPAWNER) {
                if (player.hasPermission("shop.spawneur"))
                    GenShopPage.OpenPreGenPage(player, "Spawneurs", 1);
            }
            if (current.getType() == Material.ARROW) {
                MenuGui.OpenMainMenu(player);
            }
        }
    }
}
