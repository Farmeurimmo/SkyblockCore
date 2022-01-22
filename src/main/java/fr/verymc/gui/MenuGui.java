package main.java.fr.verymc.gui;

import main.java.fr.verymc.challenges.ChallengesGuis;
import main.java.fr.verymc.shopgui.MainShopGui;
import main.java.fr.verymc.atout.AtoutGui;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MenuGui implements Listener {

    public static void OpenMainMenu(Player player) {

        Inventory inv = Bukkit.createInventory(null, 45, "§6Menu du Skyblock");

        ItemStack custom1 = new ItemStack(Material.GRASS_BLOCK, 1);
        ItemMeta meta1 = custom1.getItemMeta();
        meta1.setDisplayName("§6Votre Ile §8| §7(clic gauche)");
        custom1.setItemMeta(meta1);
        inv.setItem(10, custom1);

        ItemStack custom2 = new ItemStack(Material.DIAMOND_SWORD, 1);
        ItemMeta meta2 = custom2.getItemMeta();
        meta2.setDisplayName("§6Boutique Farm2Win §8| §7(clic gauche)");
        custom2.setItemMeta(meta2);
        inv.setItem(12, custom2);

        ItemStack custom3 = new ItemStack(Material.BEDROCK, 1);
        ItemMeta meta3 = custom3.getItemMeta();
        meta3.setDisplayName("§c????");
        custom3.setItemMeta(meta3);
        inv.setItem(34, custom3);

        ItemStack custom7 = new ItemStack(Material.ENDER_EYE, 1);
        ItemMeta meta7 = custom7.getItemMeta();
        meta7.setDisplayName("§6Warps §8| §7(clic gauche)");
        custom7.setItemMeta(meta7);
        inv.setItem(28, custom7);

        ItemStack custom4 = new ItemStack(Material.POTION, 1);
        ItemMeta meta4 = custom4.getItemMeta();
        meta4.setDisplayName("§6Atouts §8| §7(clic gauche)");
        custom4.setItemMeta(meta4);
        inv.setItem(32, custom4);

        ItemStack custom5 = new ItemStack(Material.SADDLE, 1);
        ItemMeta meta5 = custom5.getItemMeta();
        meta5.setDisplayName("§6Hôtel des ventes §8| §7(clic gauche)");
        custom5.setItemMeta(meta5);
        inv.setItem(14, custom5);

        ItemStack custom6 = new ItemStack(Material.VILLAGER_SPAWN_EGG, 1);
        ItemMeta meta6 = custom6.getItemMeta();
        meta6.setDisplayName("§6Shop §8| §7(clic gauche)");
        custom6.setItemMeta(meta6);
        inv.setItem(16, custom6);

        ItemStack custom10 = new ItemStack(Material.IRON_SWORD, 1);
        ItemMeta meta10 = custom10.getItemMeta();
        meta10.setDisplayName("§6Challenges journaliers §8| §7(clic gauche)");
        custom10.setItemMeta(meta10);
        inv.setItem(30, custom10);

        player.openInventory(inv);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        ItemStack current = e.getCurrentItem();

        if (e.getCurrentItem() == null) {
            return;
        }
        if (e.getCurrentItem().getItemMeta() == null) {
            return;
        }
        if (e.getView().getTitle() == "§6Menu du Skyblock") {
            e.setCancelled(true);
            if (current.getType() == Material.GRASS_BLOCK) {
                player.chat("/is");
            }
            if (current.getType() == Material.DIAMOND_SWORD) {
                Farm2WinGui.MainBoutiqueGUI(player);
            }
            if (current.getType() == Material.ENDER_EYE) {
                WarpGui.OpenGUi(player);
            }
            if (current.getType() == Material.SADDLE) {
                player.chat("/hdv");
            }
            if (current.getType() == Material.VILLAGER_SPAWN_EGG) {
                MainShopGui.OpenMainShopMenu(player);
            }
            if (current.getType() == Material.IRON_SWORD) {
                ChallengesGuis.MakeMainGui(player);
            }
            if (current.getType() == Material.POTION) {
                AtoutGui.MakeAtoutGui(player);
            }
        }
    }

}
