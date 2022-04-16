package main.java.fr.verymc.minions;

import main.java.fr.verymc.Main;
import main.java.fr.verymc.blocks.ChestListener;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

public class MinionHarvest {

    public static MinionHarvest instance;
    public HashMap<Minion, Long> lastAction = new HashMap<>();
    public HashMap<Minion, Integer> lastWarn = new HashMap<>();

    public ArrayList<Material> blockBreakable = new ArrayList<>();

    public MinionHarvest() {
        instance = this;
        autoHarvest();
        blockBreakable.addAll(Arrays.asList(Material.ANCIENT_DEBRIS, Material.COBBLESTONE, Material.IRON_ORE, Material.COAL_ORE, Material.GOLD_ORE
                , Material.DIAMOND_ORE, Material.EMERALD_ORE, Material.STONE));
    }

    public void autoHarvest() {

        Bukkit.getScheduler().scheduleAsyncRepeatingTask(Main.instance, new Runnable() {
            public void run() {
                ArrayList<Minion> minions = new ArrayList<>();
                minions.addAll(MinionManager.instance.minions);
                Long millis = System.currentTimeMillis();
                for (Minion minion : minions) {
                    if (minion == null) continue;
                    Location blocLoc = minion.getBlocLocation();
                    if (!Bukkit.getWorld(blocLoc.getWorld().getName()).
                            getChunkAt(blocLoc.getBlock()).isLoaded()) continue;
                    if (!minion.isChestLinked()) {
                        Player player = Bukkit.getPlayer(minion.getOwnerUUID());
                        if (player != null && player.isOnline()) invalidChestProcess(player, minion);
                        continue;
                    }
                    if (minion.getChestBloc() == null) {
                        Player player = Bukkit.getPlayer(minion.getOwnerUUID());
                        if (player != null && player.isOnline()) invalidChestProcess(player, minion);
                        continue;
                    }
                    Integer delay = MinionManager.instance.getMinerDelay(minion.getLevelInt());
                    if (!lastAction.containsKey(minion)) {
                        makeAction(minion);
                        lastAction.put(minion, millis + delay * 1000);
                        continue;
                    }
                    if (lastAction.get(minion) <= millis) {
                        lastAction.put(minion, millis + delay * 1000);
                        makeAction(minion);
                        continue;
                    }
                    continue;
                }
            }
        }, 20, 20);
    }

    public void invalidChestProcess(Player player, Minion minion) {
        if (player != null && player.isOnline()) {
            if (!lastWarn.containsKey(minion)) {
                lastWarn.put(minion, 1);
            } else {
                lastWarn.put(minion, lastWarn.get(minion) + 1);
            }
            if (lastWarn.get(minion) >= 30) {
                player.sendMessage("§6§lMinions §8» §cLe minion en x: " + minion.getBlocLocation().getX() +
                        " y: " + minion.getBlocLocation().getY() + " z: " + minion.getBlocLocation().getZ() +
                        " possède un §lcoffre invalide/non définit§c.");
                player.playEffect(player.getLocation(), Effect.ANVIL_BREAK, 0);
                lastWarn.remove(minion);
            }
        }
    }

    public void makeAction(Minion minion) {
        Location blocLoc = minion.getBlocLocation().clone();
        Block blockToBreak = null;
        if (minion.getBlockFace() == BlockFace.NORTH) {
            Bukkit.getWorld(blocLoc.getWorld().getName()).getBlockAt(blocLoc.add(0, 0, -1));
            if (blockBreakable.contains(blocLoc.getBlock().getType())) {
                blockToBreak = blocLoc.getBlock();
            }
        }
        if (minion.getBlockFace() == BlockFace.SOUTH) {
            Bukkit.getWorld(blocLoc.getWorld().getName()).getBlockAt(blocLoc.add(0, 0, 1));
            if (blockBreakable.contains(blocLoc.getBlock().getType())) {
                blockToBreak = blocLoc.getBlock();
            }
        }
        if (minion.getBlockFace() == BlockFace.WEST) {
            Bukkit.getWorld(blocLoc.getWorld().getName()).getBlockAt(blocLoc.add(-1, 0, 0));
            if (blockBreakable.contains(blocLoc.getBlock().getType())) {
                blockToBreak = blocLoc.getBlock();
            }
        }
        if (minion.getBlockFace() == BlockFace.EAST) {
            Bukkit.getWorld(blocLoc.getWorld().getName()).getBlockAt(blocLoc.add(1, 0, 0));
            if (blockBreakable.contains(blocLoc.getBlock().getType())) {
                blockToBreak = blocLoc.getBlock();
            }
        }
        if (blockToBreak == null) return;
        Block finalBlockToBreak = blockToBreak;
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
            @Override
            public void run() {
                if (finalBlockToBreak.getType() != null && finalBlockToBreak.getType() != Material.AIR) {
                    if (minion.getChestBloc().getType() != Material.CHEST) {
                        Player player = Bukkit.getPlayer(minion.getOwnerUUID());
                        if (player != null && player.isOnline()) invalidChestProcess(player, minion);
                        return;
                    }
                    Chest blhopper = (Chest) minion.getChestBloc().getState();
                    Collection<ItemStack> a = finalBlockToBreak.getDrops();
                    for (ItemStack fda : a) {
                        if (ChestListener.GetAmountToFillInInv(fda, blhopper.getInventory()) > 0) {
                            if (minion.isAutoSmelt()) {
                                blhopper.getInventory().addItem(returnCookedItem(fda));
                                continue;
                            }
                            blhopper.getInventory().addItem(fda);
                            continue;
                        }
                    }
                    finalBlockToBreak.setType(Material.AIR);
                }
            }
        }, 0);
    }

    public ItemStack returnCookedItem(ItemStack itemStack) {
        ItemStack toReturnItemStack = null;
        if (itemStack.getType() == Material.COBBLESTONE) {
            toReturnItemStack = new ItemStack(Material.STONE, itemStack.getAmount());
        } else if (itemStack.getType() == Material.COAL_ORE) {
            toReturnItemStack = new ItemStack(Material.COAL, itemStack.getAmount());
        } else if (itemStack.getType() == Material.IRON_ORE) {
            toReturnItemStack = new ItemStack(Material.IRON_INGOT, itemStack.getAmount());
        } else if (itemStack.getType() == Material.GOLD_ORE) {
            toReturnItemStack = new ItemStack(Material.GOLD_INGOT, itemStack.getAmount());
        } else if (itemStack.getType() == Material.DIAMOND_ORE) {
            toReturnItemStack = new ItemStack(Material.DIAMOND, itemStack.getAmount());
        } else if (itemStack.getType() == Material.EMERALD_ORE) {
            toReturnItemStack = new ItemStack(Material.EMERALD, itemStack.getAmount());
        } else {
            toReturnItemStack = itemStack;
        }

        return toReturnItemStack;
    }
}
