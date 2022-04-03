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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class IslandTopGui {

    public static IslandTopGui instance;

    public IslandTopGui() {
        instance = this;
    }

    public void openTopIslandMenu(Player player) {
        Inventory inv = Bukkit.createInventory(null, 45, "§6Classement des îles");

        ItemStack custom8 = new ItemStack(Material.ARROW, 1);
        ItemMeta customh = custom8.getItemMeta();
        customh.setDisplayName("§6Retour §8| §7(clic gauche)");
        custom8.setItemMeta(customh);
        inv.setItem(44, custom8);

        int currentNum = 0;
        ArrayList<Island> islands = IslandManager.instance.islands;
        ArrayList<Island> islandsAlreadySorted = new ArrayList<>();
        HashMap<Island, Integer> islandClassementPos = new HashMap<>();
        while (true) {
            double bestValue = -1;
            Island tmpIsland = null;
            for (Island island : islands) {
                if (!islandsAlreadySorted.contains(island)) {
                    if (island.getValue() > bestValue) {
                        if (tmpIsland != null) {
                            islandsAlreadySorted.remove(tmpIsland);
                        }
                        bestValue = island.getValue();
                        islandsAlreadySorted.add(island);
                        tmpIsland = island;
                    }
                }
            }
            if (tmpIsland == null) {
                break;
            }
            islandClassementPos.put(tmpIsland, currentNum);
            currentNum += 1;
            if (islandsAlreadySorted.size() >= 21) {
                break;
            }
        }
        for (Map.Entry<Island, Integer> entry : islandClassementPos.entrySet()) {
            int slot = entry.getValue().intValue() + 10;
            ItemStack custom10 = new ItemStack(Material.PLAYER_HEAD, 1);
            SkullMeta customi = (SkullMeta) custom10.getItemMeta();
            customi.setOwner(entry.getKey().getOwner());
            customi.setDisplayName("§6#" + (entry.getValue() + 1) + ": " + entry.getKey().getName());
            customi.setLore(Arrays.asList("§7Points: " + entry.getKey().getValue()));
            custom10.setItemMeta(customi);
            inv.setItem(slot, custom10);
        }

        player.openInventory(inv);
    }
}
