package main.java.fr.verymc.spigot.island.guis;

import main.java.fr.verymc.spigot.island.IslandManager;
import main.java.fr.verymc.spigot.island.perms.IslandRank;
import main.java.fr.verymc.spigot.island.perms.IslandRanks;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;

public class IslandMemberGui {

    public static IslandMemberGui instance;

    public IslandMemberGui() {
        instance = this;
    }

    public void openMemberIslandMenu(Player player) {

        if (!IslandManager.instance.asAnIsland(player)) {
            return;
        }
        Inventory inv = Bukkit.createInventory(null, 27, "§6Membres de l'île");

        ItemStack custom8 = new ItemStack(Material.ARROW, 1);
        ItemMeta customh = custom8.getItemMeta();
        customh.setDisplayName("§6Retour §8| §7(clic gauche)");
        custom8.setItemMeta(customh);
        inv.setItem(26, custom8);

        int current = 0;
        ArrayList<UUID> members = new ArrayList<>();
        int currentLevelRank = 0;
        HashMap<UUID, IslandRanks> levelRank = IslandManager.instance.getPlayerIsland(player).getMembers();
        HashMap<IslandRanks, Integer> rankPos = IslandRank.getIslandRankPos();
        while (members.size() != levelRank.size()) {
            if (currentLevelRank > 3) {
                break;
            }
            for (Map.Entry<UUID, IslandRanks> playerEntry : levelRank.entrySet()) {
                if (members.contains(playerEntry.getKey())) {
                    continue;
                }
                if (rankPos.get(playerEntry.getValue()) != currentLevelRank) {
                    continue;
                }
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
                customi.setLore(Arrays.asList("§7Rang: §6" + playerEntry.getValue().name(), "§7Clic droit pour rétrograder", "§7Clic gauche pour promouvoir",
                        "§7Clic molette pour exclure"));
                custom10.setItemMeta(customi);
                inv.setItem(current, custom10);
                members.add(playerEntry.getKey());
                current += 1;
            }
            currentLevelRank += 1;
        }

        player.openInventory(inv);
    }
}
