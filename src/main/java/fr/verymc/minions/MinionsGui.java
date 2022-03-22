package main.java.fr.verymc.minions;

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
import java.util.List;

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
        meta2.setDisplayName("§6Coffre du minion");
        String linked = minion.isChestLinked() + "";
        meta2.setLore(Arrays.asList("§6Coffre lié: " + linked.replace("true", "§aoui")
                .replace("false", "§cnon")));
        if (minion.isChestLinked() && minion.getChestBloc() != null) {
            List<String> lore = meta2.getLore();
            lore.add("§6Coordonnées §ex:" + minion.getChestBloc().getX() + " y:" + minion.getChestBloc().getY() + " z:" + minion.getChestBloc().getZ());
            meta2.setLore(lore);
        }
        custom2.setItemMeta(meta2);

        ItemStack custom3 = new ItemStack(Material.BLAZE_POWDER, 1);
        ItemMeta meta3 = custom3.getItemMeta();
        meta3.setDisplayName("§6Améliorations");
        meta3.setLore(Arrays.asList("§6Niveau actuel: §e" + minion.getLevelInt(), "§6Délai entre les actions: §e" +
                MinionManager.instance.getMinerDelay(minion.getLevelInt()) + "s"));
        custom3.setItemMeta(meta3);

        ItemStack custom4 = new ItemStack(Material.PAPER, 1);
        ItemMeta meta4 = custom4.getItemMeta();
        meta4.setDisplayName("§6Paramètres");
        meta4.setLore(Arrays.asList("§6AutoSmelt: §e" + minion.isAutoSmelt().toString().replace("true", "§aoui")
                .replace("false", "§cnon")));
        custom4.setItemMeta(meta4);


        inv.setItem(10, custom2);
        inv.setItem(12, custom3);
        inv.setItem(14, custom4);
        inv.setItem(16, custom1);

        minionOpened.put(player.getName(), minion);
        player.openInventory(inv);

    }

    public void openUpgradeShop(Player player, Minion minion) {
        Inventory inv = Bukkit.createInventory(null, 27, "§6Améliorations du minion");

        for (int i = 0; i <= 6; i++) {
            ItemStack custom10 = new ItemStack(Material.PLAYER_HEAD, i + 1);
            SkullMeta customi = (SkullMeta) custom10.getItemMeta();
            if (i == 0) {
                customi.setDisplayName("§6Niveau §e" + i + " (Gratuit)");
            } else {
                customi.setDisplayName("§6Niveau §e" + i);
            }
            customi.setLore(Arrays.asList("§6Délai de minage: §e" + MinionManager.instance.getMinerDelay(i) + "s", "§6Possédé: " +
                    MinionManager.instance.getBeforeBooleanUpgrade(i, minion).toString().replace("true", "§aoui")
                            .replace("false", "§cnon"), "§6Prix: §e" + MinionManager.instance.getNextUpgradeCost(i, i)));
            customi.setOwner("Farmeurimmo");
            custom10.setItemMeta(customi);

            inv.setItem(10 + i, custom10);
        }


        ItemStack custom3 = new ItemStack(Material.ARROW, 1);
        ItemMeta meta3 = custom3.getItemMeta();
        meta3.setDisplayName("§6Retour §8| §7(clic gauche)");
        custom3.setItemMeta(meta3);

        inv.setItem(26, custom3);

        minionOpened.put(player.getName(), minion);
        player.openInventory(inv);
    }

    public void minionParamGui(Player player, Minion minion) {

        Inventory inv = Bukkit.createInventory(null, 27, "§6Paramètres du minion " + minion.getMinionType().getName(minion.getMinionType()));

        ItemStack custom1 = new ItemStack(Material.FURNACE, 1);
        ItemMeta meta1 = custom1.getItemMeta();
        meta1.setDisplayName("§6AutoSmelt");
        meta1.setLore(Arrays.asList("§6Actif: " + minion.isAutoSmelt().toString().replace("true", "§aoui")
                .replace("false", "§cnon")));
        custom1.setItemMeta(meta1);

        ItemStack custom3 = new ItemStack(Material.ARROW, 1);
        ItemMeta meta3 = custom3.getItemMeta();
        meta3.setDisplayName("§6Retour §8| §7(clic gauche)");
        custom3.setItemMeta(meta3);

        inv.setItem(26, custom3);

        inv.setItem(10, custom1);

        minionOpened.put(player.getName(), minion);
        player.openInventory(inv);

    }


}