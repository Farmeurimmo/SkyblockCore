package main.java.fr.verymc.island.guis;

import main.java.fr.verymc.island.Island;
import main.java.fr.verymc.island.IslandManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class IslandBankGui {

    public static IslandBankGui instance;

    public IslandBankGui() {
        instance = this;
    }

    public void openBankIslandMenu(Player player) {

        if (!IslandManager.instance.asAnIsland(player)) {
            return;
        }

        Inventory inv = Bukkit.createInventory(null, 27, "§6Banque de l'île");

        Island playerIsland = IslandManager.instance.getPlayerIsland(player);

        ItemStack custom1 = new ItemStack(Material.SUNFLOWER, 1);
        ItemMeta meta1 = custom1.getItemMeta();
        meta1.setDisplayName("§6Argent §8| §7(clic gauche)");
        meta1.setLore(Arrays.asList("§7Argent en banque: §6" + playerIsland.getBank().getMoney() + "$"));
        custom1.setItemMeta(meta1);
        inv.setItem(10, custom1);

        ItemStack custom2 = new ItemStack(Material.NETHER_STAR, 1);
        ItemMeta meta2 = custom2.getItemMeta();
        meta2.setDisplayName("§6Crystaux §8| §7(clic gauche)");
        meta2.setLore(Arrays.asList("§7Crystaux en banque: §6" + playerIsland.getBank().getCrystaux()));
        custom2.setItemMeta(meta2);
        inv.setItem(13, custom2);

        ItemStack custom3 = new ItemStack(Material.EXPERIENCE_BOTTLE, 1);
        ItemMeta meta3 = custom3.getItemMeta();
        meta3.setDisplayName("§6Expérience §8| §7(clic gauche)");
        meta3.setLore(Arrays.asList("§7Expérience en banque: §6" + playerIsland.getBank().getXp()));
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
