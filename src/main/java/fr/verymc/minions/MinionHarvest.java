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
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class MinionHarvest {

    public static MinionHarvest instance;
    public HashMap<Minion, Long> lastAction = new HashMap<>();
    public ArrayList<Block> blocksToBreak = new ArrayList<>();

    public MinionHarvest() {
        instance = this;
        autoHarvest();
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
                    if (!minion.isChestLinked()) return;
                    if (minion.getChestBloc() == null) return;
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

    public void makeAction(Minion minion) {
        Location blocLoc = minion.getBlocLocation().clone();
        if (minion.getBlockFace() == BlockFace.NORTH) {
            blocksToBreak.add(Bukkit.getWorld(blocLoc.getWorld().getName()).getBlockAt(blocLoc.add(0, 0, -1)));
        }
        if (minion.getBlockFace() == BlockFace.SOUTH) {
            blocksToBreak.add(Bukkit.getWorld(blocLoc.getWorld().getName()).getBlockAt(blocLoc.add(0, 0, 1)));
        }
        if (minion.getBlockFace() == BlockFace.WEST) {
            blocksToBreak.add(Bukkit.getWorld(blocLoc.getWorld().getName()).getBlockAt(blocLoc.add(-1, 0, 0)));
        }
        if (minion.getBlockFace() == BlockFace.EAST) {
            blocksToBreak.add(Bukkit.getWorld(blocLoc.getWorld().getName()).getBlockAt(blocLoc.add(1, 0, 0)));
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
            @Override
            public void run() {
                for (Block block : blocksToBreak) {
                    if (block.getType() != null && block.getType() != Material.AIR) {
                        if (minion.getChestBloc().getType() != Material.CHEST) return;
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
