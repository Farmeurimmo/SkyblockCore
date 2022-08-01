package main.java.fr.verymc.spigot.island.guis;

import main.java.fr.verymc.spigot.island.IslandManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Map;

public class IslandBlocsValueGui {

    public static IslandBlocsValueGui instance;

    public IslandBlocsValueGui() {
        instance = this;
    }

    public void openBlocsValueGui(Player player) {
        Inventory inv = Bukkit.createInventory(null, 36, "§6Valeur des blocs");

        ItemStack custom8 = new ItemStack(Material.ARROW, 1);
        ItemMeta customh = custom8.getItemMeta();
        customh.setDisplayName("§6Retour §8| §7(clic gauche)");
        custom8.setItemMeta(customh);
        inv.setItem(35, custom8);

        ItemStack custom9 = new ItemStack(Material.KNOWLEDGE_BOOK, 1);
        ItemMeta customi = custom9.getItemMeta();
        customi.setDisplayName("§6Informations complémentaires");
        customi.setLore(Arrays.asList("§csoon"));
        custom9.setItemMeta(customi);
        inv.setItem(27, custom9);

        int currentSlot = 10;

        for (Map.Entry<Material, Double> entry : IslandManager.instance.islandBockValues.getBlockValues().entrySet()) {
            ItemStack item = new ItemStack(entry.getKey());
            item.setLore(Arrays.asList("§7Valeur: " + entry.getValue()));
            inv.setItem(currentSlot, item);

            currentSlot++;
            if (currentSlot == 17) {
                currentSlot += 2;
            }
        }

        player.openInventory(inv);
    }
}
