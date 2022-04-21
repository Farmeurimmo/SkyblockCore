package main.java.fr.verymc.playerwarps;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class PlayerWarpNotationGui {

    public static PlayerWarpNotationGui instance;

    public PlayerWarpNotationGui() {
        instance = this;
    }

    public void openNotationMenu(Player player, PlayerWarp playerWarp) {
        String owner = PlayerWarpManager.instance.getOwnerFromPlayerWarp(playerWarp);
        Inventory inv = Bukkit.createInventory(null, 27, "§6Notation du warp de " + owner);

        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§6Informations");
        meta.setLore(Arrays.asList("§7Nom: " + playerWarp.getName(), "§7Vues totales: " + playerWarp.getVues(), "§7Note moyenne: " +
                (playerWarp.getNote() < 0 ? "§cAucune note" : playerWarp.getNote()), "§7Propriétaire: " + owner));
        item.setItemMeta(meta);
        inv.setItem(10, item);

        for (int i = 1; i <= 5; i++) {
            ItemStack item2 = new ItemStack(Material.GREEN_STAINED_GLASS_PANE, i);
            item2.setDisplayName("§a§l" + i + "/5");
            item2.setLore(Arrays.asList("§7Clic pour voter pour cette notation"));
            inv.setItem(11 + i, item2);
        }

        player.openInventory(inv);
    }
}
