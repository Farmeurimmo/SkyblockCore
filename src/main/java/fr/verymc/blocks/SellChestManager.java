package main.java.fr.verymc.blocks;

import main.java.fr.verymc.core.Main;
import main.java.fr.verymc.eco.EcoAccountsManager;
import main.java.fr.verymc.shopgui.BuyShopItem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

public class SellChestManager {

    public static HashMap<Location, UUID> blcsellchest = new HashMap<>();

    public static void AutoSellForVeryChest() {
        HashMap<UUID, Double> reward = new HashMap<>();
        for (Entry<Location, UUID> sellchest : blcsellchest.entrySet()) {
            BlockState e = sellchest.getKey().getBlock().getState();
            Inventory ed = ((Chest) e).getBlockInventory();
            double total = 0;
            for (ItemStack sd : ed) {
                if (sd == null) continue;
                if (sd.getType() == null) continue;
                ItemStack searched = new ItemStack(sd.getType());
                searched.setAmount(sd.getAmount());
                if (BuyShopItem.pricessell.get(new ItemStack(sd.getType())) != null && BuyShopItem.pricessell.get(new ItemStack(sd.getType())) > 0) {
                    int amount = BuyShopItem.GetAmountInInvNo(searched, ed);
                    Double price = BuyShopItem.pricessell.get(new ItemStack(sd.getType()));
                    price = amount * price;
                    BuyShopItem.removeItems(ed, searched.getType(), amount);
                    total += price;
                }
            }
            ((Chest) e).getBlockInventory().setContents(ed.getContents());
            UUID playername = sellchest.getValue();
            if (reward.containsKey(playername)) {
                reward.put(playername, reward.get(playername) + total);
            } else {
                reward.put(playername, total);
            }
        }
        for (Entry<UUID, Double> tosend : reward.entrySet()) {
            EcoAccountsManager.instance.AddFounds(tosend.getKey(), tosend.getValue(), false);
        }
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
            public void run() {
                SellChestManager.AutoSellForVeryChest();
            }
        }, 20 * 15);
    }

    public static void GiveSellChest(Player player, int i) {
        int a = 0;
        if (Main.getInstance().getDatablc().get("SellChest.num") == null) {
            a = 1;
        } else {
            a = Main.getInstance().getDatablc().getInt("SellChest.num");
            a += 1;
        }
        ItemStack aa = new ItemStack(Material.CHEST);
        ItemMeta ameta = aa.getItemMeta();
        if (i > 0) {
            a = i;
        } else {
            Main.getInstance().getDatablc().set("SellChest.num", a);
            Main.getInstance().saveData();
        }
        ameta.setDisplayName("§6SellChest §c(id#" + a + ")");
        aa.setUnbreakable(true);
        aa.setItemMeta(ameta);

        player.getInventory().addItem(aa);
    }

    public static void PlaceChest(Player player, Location block, int num) {
        Main.getInstance().getDatablc().set("SellChest." + player.getUniqueId() + "." + num, block);
        Main.getInstance().saveData();
        blcsellchest.put(block, player.getUniqueId());
    }

    public static String getOwner(Location loc) {
        return String.valueOf(blcsellchest.get(loc));
    }

    public static void ReadFromFile() {
        if (!Main.getInstance().getDatablc().isSet("SellChest")) {
            return;
        }
        for (String aa : Main.getInstance().getDatablc().getConfigurationSection("SellChest").getKeys(false)) {
            if (aa.contains("num")) {
                continue;
            }
            for (String bb : Main.getInstance().getDatablc().getConfigurationSection("SellChest." + aa).getKeys(false)) {
                blcsellchest.put(Main.getInstance().getDatablc().getLocation("SellChest." + aa + "." + bb), UUID.fromString(aa));
            }
        }
    }

}
