package main.java.fr.verymc.island.guis;

import main.java.fr.verymc.island.IslandManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class IslandUpgradeGui {

    public static IslandUpgradeGui instance;

    public IslandUpgradeGui() {
        instance = this;
    }

    public void openUpgradeIslandMenu(Player player) {

        if (!IslandManager.instance.asAnIsland(player)) {
            return;
        }
        Inventory inv = Bukkit.createInventory(null, 27, "§6Améliorations de l'île");

        int sizeIsland = IslandManager.instance.getPlayerIsland(player).getSizeUpgrade().getSize();

        ItemStack custom1 = new ItemStack(Material.GRASS_BLOCK);
        ItemMeta custom1Meta = custom1.getItemMeta();
        custom1Meta.setDisplayName("§6Taille de l'île");
        custom1Meta.setLore(Arrays.asList("§7Taille actuelle : §6" + sizeIsland + "§7x§6" + sizeIsland));
        custom1.setItemMeta(custom1Meta);
        inv.setItem(10, custom1);

        ItemStack custom2 = new ItemStack(Material.COBBLESTONE);
        ItemMeta custom2Meta = custom2.getItemMeta();
        custom2Meta.setDisplayName("§6Générateur de l'île");
        custom2.setItemMeta(custom2Meta);
        inv.setItem(12, custom2);

        ItemStack custom3 = new ItemStack(Material.PAPER);
        ItemMeta custom3Meta = custom3.getItemMeta();
        custom3Meta.setDisplayName("§6Nombre de membres");
        custom3.setItemMeta(custom3Meta);
        inv.setItem(14, custom3);

        ItemStack custom4 = new ItemStack(Material.SPAWNER);
        ItemMeta custom4Meta = custom4.getItemMeta();
        custom4Meta.setDisplayName("§6Limite de blocs");
        custom4.setItemMeta(custom4Meta);
        inv.setItem(16, custom4);


        ItemStack custom8 = new ItemStack(Material.ARROW, 1);
        ItemMeta customh = custom8.getItemMeta();
        customh.setDisplayName("§6Retour §8| §7(clic gauche)");
        custom8.setItemMeta(customh);
        inv.setItem(26, custom8);


        player.openInventory(inv);
    }
}
