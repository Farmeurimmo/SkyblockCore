package main.java.fr.verymc.minions;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;

public class MinionsGui {

    public static MinionsGui instance;

    public ArrayList<Player> linking = new ArrayList<>();
    public HashMap<String, Minion> minionOpened = new HashMap<>();


    public MinionsGui() {
        instance = this;
    }

    public void minionMainGui(Player player, Minion minion) {

        Inventory inv = Bukkit.createInventory(null, 27, "§6Menu du minion " + minion.getMinionType().getName(minion.getMinionType()));

        ItemStack custom1 = new ItemStack(Material.DRAGON_BREATH, 1);
        ItemMeta meta1 = custom1.getItemMeta();
        meta1.setDisplayName("§6Récupérer le minion");
        custom1.setItemMeta(meta1);

        ItemStack custom2 = new ItemStack(Material.CHEST, 1);
        ItemMeta meta2 = custom2.getItemMeta();
        meta2.setDisplayName("§6Coffre");
        custom2.setItemMeta(meta2);

        ItemStack custom3 = new ItemStack(Material.BLAZE_POWDER, 1);
        ItemMeta meta3 = custom3.getItemMeta();
        meta3.setDisplayName("§6Améliorations");
        custom3.setItemMeta(meta3);


        inv.setItem(10, custom2);
        inv.setItem(13, custom3);
        inv.setItem(16, custom1);

        minionOpened.put(player.getName(), minion);
        player.openInventory(inv);

    }
}
