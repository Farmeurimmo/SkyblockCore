package main.java.fr.verymc.island.guis;

import main.java.fr.verymc.island.IslandManager;
import main.java.fr.verymc.island.perms.IslandRank;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

public class IslandMemberGui {

    public static IslandMemberGui instance;

    public IslandMemberGui() {
        instance = this;
    }

    public void openMemberIslandMenu(Player player) {

        Inventory inv = Bukkit.createInventory(null, 27, "§6Membres de l'île");

        ItemStack custom8 = new ItemStack(Material.ARROW, 1);
        ItemMeta customh = custom8.getItemMeta();
        customh.setDisplayName("§6Retour §8| §7(clic gauche)");
        custom8.setItemMeta(customh);
        inv.setItem(26, custom8);

        int current = 0;
        for (Map.Entry<UUID, IslandRank> playerEntry : IslandManager.instance.getPlayerIsland(player).getMembers().entrySet()) {
            ItemStack custom10 = new ItemStack(Material.PLAYER_HEAD, 1);
            SkullMeta customi = (SkullMeta) custom10.getItemMeta();
            if (Bukkit.getPlayer(playerEntry.getKey()) != null) {
                customi.setOwner(Bukkit.getPlayer(playerEntry.getKey()).getName());
            } else if (Bukkit.getOfflinePlayer(playerEntry.getKey()) != null) {
                customi.setOwner(Bukkit.getOfflinePlayer(playerEntry.getKey()).getName());
            } else {
                customi.setOwner("");
            }
            customi.setDisplayName("§6" + customi.getOwner());
            customi.setLore(Arrays.asList("§7Rang: §6" + playerEntry.getValue().getName(), "§6Clic droit pour rétrograder", "Clic gauche pour promouvoir",
                    "§6Clic molette pour exclure"));
            custom10.setItemMeta(customi);
            inv.setItem(current, custom10);
            current += 1;
        }


        player.openInventory(inv);
    }
}
