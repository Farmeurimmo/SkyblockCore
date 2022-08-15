package main.java.fr.verymc.spigot.island.blocks;

import main.java.fr.verymc.commons.enums.ServerType;
import main.java.fr.verymc.spigot.Main;
import main.java.fr.verymc.spigot.core.shopgui.BuyShopItem;
import main.java.fr.verymc.spigot.core.storage.AsyncConfig;
import main.java.fr.verymc.spigot.core.storage.ConfigManager;
import main.java.fr.verymc.spigot.island.Island;
import main.java.fr.verymc.spigot.island.IslandManager;
import main.java.fr.verymc.spigot.island.guis.IslandValueStorageGui;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.Hopper;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

public class ChestManager {

    public static ChestManager instance;

    public HashMap<Integer, Material> materialHashMap = new HashMap<>();

    public ChestManager() {
        instance = this;

        materialHashMap.put(0, Material.HOPPER);
        materialHashMap.put(1, Material.CHEST);
        materialHashMap.put(2, Material.CHEST);

        if (Main.instance.serverType == ServerType.SKYBLOCK_ISLAND) {
            new PlayerShopGuis();
            new IslandValueStorageGui();

            autoSellForVeryChest();
        }
    }

    public void makeChestRepop() {
        for (Island island : IslandManager.instance.islands) {
            for (main.java.fr.verymc.spigot.island.blocks.Chest chest : island.getChests()) {
                Block block = chest.getBlock().getBlock();
                block.getLocation().getChunk().load();
                BlockState bs = block.getState();
                if (bs instanceof Chest) {
                    Chest c = (Chest) bs;
                    if (chest.getType() == 1) {
                        c.setCustomName("§6SellChest");
                    } else if (chest.getType() == 2) {
                        c.setCustomName("§6Player shop");
                    }
                }
                if (bs instanceof Hopper) {
                    Hopper h = (Hopper) bs;
                    if (chest.getType() == 0) {
                        h.setCustomName("§6Chunk Hoppeur");
                    }
                }
                bs.update();
                block.setBlockData(bs.getBlockData());
                block.getLocation().getChunk().unload();
            }
        }
    }

    public void autoSellForVeryChest() {
        HashMap<main.java.fr.verymc.spigot.island.blocks.Chest, Double> reward = new HashMap<>();
        for (Island island : IslandManager.instance.islands) {
            for (main.java.fr.verymc.spigot.island.blocks.Chest chest : island.getChests()) {
                try {
                    BlockState e = chest.getBlock().getBlock().getState();
                    if (e.getType() != Material.CHEST) {
                        continue;
                    }
                    Inventory ed = ((Chest) e).getBlockInventory();
                    if (((Chest) e).getCustomName() == null) {
                        continue;
                    }
                    if (!((Chest) e).getCustomName().contains("§6SellChest")) {
                        continue;
                    }
                    double total = 0;
                    for (ItemStack sd : ed) {
                        if (sd == null) continue;
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
                    if (reward.containsKey(chest)) {
                        reward.put(chest, reward.get(chest) + total);
                    } else {
                        reward.put(chest, total);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        for (Entry<main.java.fr.verymc.spigot.island.blocks.Chest, Double> tosend : reward.entrySet()) {
            Island island = IslandManager.instance.getIslandByLoc(tosend.getKey().getBlock());
            if (island == null) continue;
            island.getBank().addMoney(tosend.getValue());
        }
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
            public void run() {
                autoSellForVeryChest();
            }
        }, 20 * 20);
    }

    public void giveChest(Player player, int type) {
        ItemStack aa = new ItemStack(Material.AIR);
        if (type == 1) {
            aa = new ItemStack(Material.CHEST);
            ItemMeta ameta = aa.getItemMeta();

            ameta.setDisplayName("§6SellChest");
            aa.setUnbreakable(true);
            aa.setItemMeta(ameta);
        } else if (type == 0) {
            aa = new ItemStack(Material.HOPPER);
            ItemMeta ameta = aa.getItemMeta();

            ameta.setDisplayName("§6Chunk Hoppeur");
            aa.setUnbreakable(true);
            aa.setItemMeta(ameta);
        } else if (type == 2) {
            aa = new ItemStack(Material.CHEST);
            ItemMeta ameta = aa.getItemMeta();

            ameta.setDisplayName("§6Player shop");
            aa.setUnbreakable(true);
            aa.setItemMeta(ameta);
        }

        if (aa.getType() == Material.AIR) {
            return;
        }

        player.getInventory().addItem(aa);
    }

    public void placeChest(Player player, Location block, int type, ItemStack item, double price) {
        IslandManager.instance.getPlayerIsland(player).addChest(new main.java.fr.verymc.spigot.island.blocks.Chest(type, block, player.getUniqueId(), block.getChunk().getChunkKey(),
                item, price, false, false));
    }

    public void removeChestFromLoc(Location block) {
        for (Island island : IslandManager.instance.islands) {
            for (main.java.fr.verymc.spigot.island.blocks.Chest chest : island.getChests()) {
                if (chest.getBlock().getBlock().getLocation().equals(block)) {
                    island.removeChest(chest);
                    HashMap<String, Object> toEdit = new HashMap<>();
                    toEdit.put(island.getUUID().toString(), null);
                    AsyncConfig.instance.setAndSaveAsync(toEdit, ConfigManager.instance.getDataIslands(), ConfigManager.instance.islandsFile);
                    return;
                }
            }
        }
    }

    public main.java.fr.verymc.spigot.island.blocks.Chest getChestFromLoc(Location block) {
        for (Island island : IslandManager.instance.islands) {
            for (main.java.fr.verymc.spigot.island.blocks.Chest chest : island.getChests()) {
                if (chest.getBlock().getBlock().getLocation().equals(block)) {
                    return chest;
                }
            }
        }
        return null;
    }

    public UUID getOwner(Location loc) {
        for (Island island : IslandManager.instance.islands) {
            for (main.java.fr.verymc.spigot.island.blocks.Chest chest : island.getChests()) {
                if (chest.getBlock().getBlock().getLocation().equals(loc)) {
                    return chest.getOwner();
                }
            }
        }
        return null;
    }
}
