package main.java.fr.verymc.spigot.island.guis;

import main.java.fr.verymc.spigot.island.Island;
import main.java.fr.verymc.spigot.island.IslandManager;
import main.java.fr.verymc.spigot.utils.WorldBorderUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class IslandBorderGui {

    public static IslandBorderGui instance;

    public IslandBorderGui() {
        instance = this;
    }

    public void openBorderIslandMenu(Player player) {

        if (!IslandManager.instance.asAnIsland(player)) {
            return;
        }

        Inventory inv = Bukkit.createInventory(null, 27, "§6Bordure de l'île");

        Island playerIsland = IslandManager.instance.getPlayerIsland(player);

        ItemStack custom1 = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);
        ItemMeta meta1 = custom1.getItemMeta();
        if (playerIsland.getBorderColor() == WorldBorderUtils.Color.RED) {
            meta1.setDisplayName("§6Rouge §a(actif)");
        } else {
            meta1.setDisplayName("§6Rouge");
        }
        meta1.setLore(Arrays.asList("§7Clic pour passer la bordure en rouge"));
        custom1.setItemMeta(meta1);
        inv.setItem(10, custom1);

        ItemStack custom2 = new ItemStack(Material.GREEN_STAINED_GLASS_PANE, 1);
        ItemMeta meta2 = custom2.getItemMeta();
        if (playerIsland.getBorderColor() == WorldBorderUtils.Color.GREEN) {
            meta2.setDisplayName("§6Vert §a(actif)");
        } else {
            meta2.setDisplayName("§6Vert");
        }
        meta2.setLore(Arrays.asList("§7Clic pour passer la bordure en vert"));
        custom2.setItemMeta(meta2);
        inv.setItem(13, custom2);

        ItemStack custom3 = new ItemStack(Material.BLUE_STAINED_GLASS_PANE, 1);
        ItemMeta meta3 = custom3.getItemMeta();
        if (playerIsland.getBorderColor() == WorldBorderUtils.Color.BLUE) {
            meta3.setDisplayName("§6Bleu §a(actif)");
        } else {
            meta3.setDisplayName("§6Bleu");
        }
        meta3.setLore(Arrays.asList("§7Clic pour passer la bordure en bleu"));
        custom3.setItemMeta(meta3);
        inv.setItem(16, custom3);


        ItemStack custom8 = new ItemStack(Material.ARROW, 1);
        ItemMeta customh = custom8.getItemMeta();
        customh.setDisplayName("§6Retour §8| §7(clic gauche)");
        custom8.setItemMeta(customh);
        inv.setItem(26, custom8);

        player.openInventory(inv);
    }
}
