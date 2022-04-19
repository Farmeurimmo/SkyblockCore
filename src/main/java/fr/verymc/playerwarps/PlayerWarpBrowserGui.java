package main.java.fr.verymc.playerwarps;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class PlayerWarpBrowserGui {

    public static PlayerWarpBrowserGui instance;

    public PlayerWarpBrowserGui() {
        instance = this;
    }


    public void openBrowserMenu(Player player, int page) {
        Inventory inv = Bukkit.createInventory(null, 54, "§6Warp browser #" + page);

        ArrayList<Integer> pos = new ArrayList<>();
        pos.addAll(Arrays.asList(10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39
                , 40, 41, 42, 43));

        int goal = 4 * 7 * page;
        int reversed = 1;

        for (Map.Entry<Integer, PlayerWarp> entry : PlayerWarpManager.instance.posBrowser.entrySet()) {
            if (entry.getKey() == (goal - 28 + reversed)) {
                ItemStack item = new ItemStack(Material.PAPER);
                item.setDisplayName("§7" + entry.getValue().getName());
                item.setLore(Arrays.asList("§7Vues: " + NumberFormat.getInstance().format(entry.getValue().getVues()),
                        "§7Note : " + (entry.getValue().getNote() < 0 ? "§cAucune note" : NumberFormat.getInstance().format(entry.getValue().getNote())),
                        "§7Propriétaire: " + entry.getValue().getOwner(),
                        "§7Promu: " + (entry.getValue().isPromoted() ? "§aOui" : "§cNon")));
                int lowest = getLowest(pos);
                if (!pos.contains(lowest)) {
                    break;
                }
                pos.remove(lowest);

                inv.setItem(lowest, item);

                reversed++;
            }
        }

        if (page > 1) {
            ItemStack item = new ItemStack(Material.ARROW);
            item.setDisplayName("§6Page précédente");
            inv.setItem(45, item);
        }

        if (PlayerWarpManager.instance.posBrowser.size() >= (4 * 7 * (page + 1))) {
            ItemStack item = new ItemStack(Material.ARROW);
            item.setDisplayName("§6Page suivante");
            inv.setItem(53, item);
        }


        player.openInventory(inv);

    }

    public Integer getLowest(ArrayList<Integer> pos) {
        int slottoreturn = 0;
        if (pos.size() == 0) {
            return slottoreturn;
        }
        while (slottoreturn != pos.get(0)) {
            slottoreturn += 1;
        }
        return slottoreturn;
    }

}
