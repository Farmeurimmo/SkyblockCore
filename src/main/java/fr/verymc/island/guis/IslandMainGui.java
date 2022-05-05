package main.java.fr.verymc.island.guis;

import main.java.fr.verymc.island.IslandManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;

public class IslandMainGui {

    public static IslandMainGui instance;

    public IslandMainGui() {
        instance = this;
    }

    public void openMainIslandMenu(Player player) {

        if (!IslandManager.instance.asAnIsland(player)) {
            return;
        }

        Inventory inv = Bukkit.createInventory(null, 36, "§6Menu d'île");

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
        inv.setItem(21, custom3);

        ItemStack custom4 = new ItemStack(Material.BARRIER, 1);
        ItemMeta meta4 = custom4.getItemMeta();
        meta4.setDisplayName("§6Couleur de la bordure §8| §7(clic gauche)");
        custom4.setItemMeta(meta4);
        inv.setItem(22, custom4);

        ItemStack custom5 = new ItemStack(Material.BEACON, 1);
        ItemMeta meta5 = custom5.getItemMeta();
        meta5.setDisplayName("§6Classement §8| §7(clic gauche)");
        custom5.setItemMeta(meta5);
        inv.setItem(16, custom5);

        ItemStack custom6 = new ItemStack(Material.BOOKSHELF, 1);
        ItemMeta meta6 = custom6.getItemMeta();
        meta6.setDisplayName("§6Permissions §8| §7(clic gauche)");
        custom6.setItemMeta(meta6);
        inv.setItem(13, custom6);

        ItemStack custom7 = new ItemStack(Material.WOODEN_HOE, 1);
        ItemMeta meta7 = custom7.getItemMeta();
        meta7.setDisplayName("§6Coops §8| §7(clic gauche)");
        custom7.setItemMeta(meta7);
        inv.setItem(19, custom7);

        ItemStack custom9 = new ItemStack(Material.PAPER, 1);
        ItemMeta meta9 = custom9.getItemMeta();
        meta9.setDisplayName("§6Challenges §8| §7(clic gauche)");
        custom9.setItemMeta(meta9);
        inv.setItem(20, custom9);

        ItemStack custom12 = new ItemStack(Material.EMERALD_BLOCK, 1);
        ItemMeta meta12 = custom12.getItemMeta();
        meta12.setDisplayName("§6Valeur des blocs §8| §7(clic gauche)");
        custom12.setItemMeta(meta12);
        inv.setItem(14, custom12);

        ItemStack custom13 = new ItemStack(Material.COMPARATOR, 1);
        ItemMeta meta13 = custom13.getItemMeta();
        meta13.setDisplayName("§6Paramètres d'îles §8| §7(clic gauche)");
        custom13.setItemMeta(meta13);
        inv.setItem(23, custom13);

        ItemStack custom11 = new ItemStack(Material.SPRUCE_SAPLING, 1);
        ItemMeta meta11 = custom11.getItemMeta();
        meta11.setDisplayName("§6Biome §8| §7(clic gauche)");
        meta11.setLore(Arrays.asList("", "§c§lINDISPONIBLE POUR LE MOMENT"));
        custom11.setItemMeta(meta11);

        ItemStack custom8 = new ItemStack(Material.ARROW, 1);
        ItemMeta customh = custom8.getItemMeta();
        customh.setDisplayName("§6Retour §8| §7(clic gauche)");
        custom8.setItemMeta(customh);
        inv.setItem(35, custom8);

        player.openInventory(inv);
    }
}
