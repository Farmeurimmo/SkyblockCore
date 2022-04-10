package main.java.fr.verymc.island.guis;

import main.java.fr.verymc.island.Island;
import main.java.fr.verymc.island.IslandManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.UUID;

public class IslandCoopGui {

    public static IslandCoopGui instance;

    public IslandCoopGui() {
        instance = this;
    }

    public void openCoopIslandMenu(Player player) {

        if (!IslandManager.instance.asAnIsland(player)) {
            return;
        }

        Inventory inv = Bukkit.createInventory(null, 27, "§6Membres temporaires (coops)");

        Island playerIsland = IslandManager.instance.getPlayerIsland(player);

        for (UUID uuid : playerIsland.getCoops()) {
            Player p = Bukkit.getPlayer(uuid);
            if (p == null) {
                p = Bukkit.getOfflinePlayer(uuid).getPlayer();
            }
            if (p == null) {
                continue;
            }
            ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
            SkullMeta itemMeta = (SkullMeta) item.getItemMeta();
            itemMeta.setDisplayName("§6" + p.getName());
            itemMeta.setOwner(p.getName());
            itemMeta.setLore(Arrays.asList("§7Clic pour supprimer le statut coop"));
            item.setItemMeta(itemMeta);
            inv.addItem(item);
        }


        ItemStack custom8 = new ItemStack(Material.ARROW, 1);
        ItemMeta customh = custom8.getItemMeta();
        customh.setDisplayName("§6Retour §8| §7(clic gauche)");
        custom8.setItemMeta(customh);
        inv.setItem(26, custom8);

        player.openInventory(inv);
    }
}
