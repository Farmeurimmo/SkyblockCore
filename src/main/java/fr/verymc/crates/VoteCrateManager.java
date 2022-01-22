package main.java.fr.verymc.crates;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class VoteCrateManager {

    public static void VoteCratePreview(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, "§6Boxe vote");

        ItemStack custom1 = new ItemStack(Material.BARRIER, 1);
        ItemMeta meta1 = custom1.getItemMeta();
        meta1.setDisplayName("§6Boxe vote");
        meta1.setLore(Arrays.asList("§7", "§eEn dev: 10%"));
        custom1.setItemMeta(meta1);
        inv.setItem(10, custom1);

        player.openInventory(inv);
    }

}
