package main.java.fr.verymc.island.guis;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class IslandMainGui {

    public static IslandMainGui instance;

    public IslandMainGui() {
        instance = this;
    }

    public void openMainIslandMenu(Player player) {

        Inventory inv = Bukkit.createInventory(null, 45, "§6Menu d'île");

        ItemStack custom1 = new ItemStack(Material.GRASS_BLOCK, 1);
        ItemMeta meta1 = custom1.getItemMeta();
        meta1.setDisplayName("§6Téléportation §8| §7(clic gauche)");
        custom1.setItemMeta(meta1);
        inv.setItem(10, custom1);

        ItemStack custom8 = new ItemStack(Material.ARROW, 1);
        ItemMeta customh = custom8.getItemMeta();
        customh.setDisplayName("§6Retour §8| §7(clic gauche)");
        custom8.setItemMeta(customh);
        inv.setItem(44, custom8);

        ItemStack custom10 = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta customi = (SkullMeta) custom10.getItemMeta();
        customi.setOwner("Farmeurimmo");
        customi.setDisplayName("§6Membres §8| §7(clic gauche)");
        custom10.setItemMeta(customi);
        inv.setItem(11, custom10);

        player.openInventory(inv);
    }
}
