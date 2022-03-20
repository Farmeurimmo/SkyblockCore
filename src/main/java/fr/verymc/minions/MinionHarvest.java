package main.java.fr.verymc.minions;

import main.java.fr.verymc.blocks.ChunkCollector;
import main.java.fr.verymc.core.Main;
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
    public HashMap<String, Integer> lastWarn = new HashMap<>();
    public ArrayList<Block> blocksToBreak = new ArrayList<>();

    public ArrayList<Material> blockBreakable = new ArrayList<>();

    public MinionHarvest() {
        instance = this;
        autoHarvest();
        blockBreakable.addAll(Arrays.asList(Material.ANCIENT_DEBRIS, Material.COBBLESTONE, Material.IRON_ORE, Material.COAL_ORE, Material.GOLD_ORE
                , Material.DIAMOND_ORE, Material.EMERALD_ORE, Material.STONE));
    }

    public void autoHarvest() {

        blocksToBreak.clear();
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(Main.instance, new Runnable() {
            public void run() {
                ArrayList<Minion> minions = new ArrayList<>();
                minions.addAll(MinionManager.instance.minions);
                Long millis = System.currentTimeMillis();
                for (Minion minion : minions) {
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
            if (!lastWarn.containsKey(player.getName())) {
                lastWarn.put(player.getName(), 1);
            } else {
                lastWarn.put(player.getName(), lastWarn.get(player.getName()) + 1);
            }
            if (lastWarn.get(player.getName()) >= 10) {
                player.sendMessage("§6§lMinions §8» §cLe minion en x: " + minion.getBlocLocation().getX() +
                        " y: " + minion.getBlocLocation().getY() + " z: " + minion.getBlocLocation().getZ() +
                        " possède un §lcoffre invalide/non définit§c.");
                player.playEffect(player.getLocation(), Effect.ANVIL_BREAK, 0);
                lastWarn.remove(player.getName());
            }
        }
    }

    public void makeAction(Minion minion) {
        Location blocLoc = minion.getBlocLocation().clone();
        if (minion.getBlockFace() == BlockFace.NORTH) {
            Bukkit.getWorld(blocLoc.getWorld().getName()).getBlockAt(blocLoc.add(0, 0, -1));
            if (blockBreakable.contains(blocLoc.getBlock().getType())) {
                blocksToBreak.add(blocLoc.getBlock());
            }
        }
        if (minion.getBlockFace() == BlockFace.SOUTH) {
            Bukkit.getWorld(blocLoc.getWorld().getName()).getBlockAt(blocLoc.add(0, 0, 1));
            if (blockBreakable.contains(blocLoc.getBlock().getType())) {
                blocksToBreak.add(blocLoc.getBlock());
            }
        }
        if (minion.getBlockFace() == BlockFace.WEST) {
            Bukkit.getWorld(blocLoc.getWorld().getName()).getBlockAt(blocLoc.add(-1, 0, 0));
            if (blockBreakable.contains(blocLoc.getBlock().getType())) {
                blocksToBreak.add(blocLoc.getBlock());
            }
        }
        if (minion.getBlockFace() == BlockFace.EAST) {
            Bukkit.getWorld(blocLoc.getWorld().getName()).getBlockAt(blocLoc.add(1, 0, 0));
            if (blockBreakable.contains(blocLoc.getBlock().getType())) {
                blocksToBreak.add(blocLoc.getBlock());
            }
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
            @Override
            public void run() {
                for (Block block : blocksToBreak) {
                    if (block.getType() != null && block.getType() != Material.AIR) {
                        if (minion.getChestBloc().getType() != Material.CHEST) {
                            Player player = Bukkit.getPlayer(minion.getOwnerUUID());
                            if (player != null && player.isOnline()) invalidChestProcess(player, minion);
                            continue;
                        }
                        Chest blhopper = (Chest) minion.getChestBloc().getState();
                        Collection<ItemStack> a = block.getDrops();
                        for (ItemStack fda : a) {
                            if (ChunkCollector.GetAmountToFillInInv(fda, blhopper.getInventory()) > 0) {
                                blhopper.getInventory().addItem(fda);
                                continue;
                            }
                        }
                        block.setType(Material.AIR);
                    }
                }
                blocksToBreak.clear();
            }
        }, 0);
    }

}
