package main.java.fr.verymc.island.guis;

import main.java.fr.verymc.island.IslandManager;
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

        if (!IslandManager.instance.asAnIsland(player)) {
            return;
        }

        Inventory inv = Bukkit.createInventory(null, 45, "§6Menu d'île");

        ItemStack custom1 = new ItemStack(Material.ENDER_EYE, 1);
        ItemMeta meta1 = custom1.getItemMeta();
        meta1.setDisplayName("§6Téléportation §8| §7(clic gauche)");
        custom1.setItemMeta(meta1);
        inv.setItem(10, custom1);

        ItemStack custom10 = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta customi = (SkullMeta) custom10.getItemMeta();
        customi.setOwner("Farmeurimmo");
        customi.setDisplayName("§6Membres §8| §7(clic gauche)");
        custom10.setItemMeta(customi);
        inv.setItem(11, custom10);

        ItemStack custom2 = new ItemStack(Material.BLAST_FURNACE, 1);
        ItemMeta meta2 = custom2.getItemMeta();
        meta2.setDisplayName("§6Améliorations §8| §7(clic gauche)");
        custom2.setItemMeta(meta2);
        inv.setItem(12, custom2);

        ItemStack custom3 = new ItemStack(Material.CHEST, 1);
        ItemMeta meta3 = custom3.getItemMeta();
        meta3.setDisplayName("§6Banque §8| §7(clic gauche)");
        custom3.setItemMeta(meta3);
        inv.setItem(13, custom3);

        ItemStack custom4 = new ItemStack(Material.BARRIER, 1);
        ItemMeta meta4 = custom4.getItemMeta();
        meta4.setDisplayName("§6Couleur de la bordure §8| §7(clic gauche)");
        custom4.setItemMeta(meta4);
        inv.setItem(14, custom4);


        ItemStack custom8 = new ItemStack(Material.ARROW, 1);
        ItemMeta customh = custom8.getItemMeta();
        customh.setDisplayName("§6Retour §8| §7(clic gauche)");
        custom8.setItemMeta(customh);
        inv.setItem(44, custom8);

        player.openInventory(inv);
    }
}
