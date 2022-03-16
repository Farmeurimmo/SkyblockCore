package main.java.fr.verymc.minions;

import main.java.fr.verymc.core.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.util.ArrayList;
import java.util.HashMap;

public class MinionHarvest {

    public static MinionHarvest instance;
    public HashMap<Minion, Long> lastAction = new HashMap<>();
    public ArrayList<Block> blocksToBreak = new ArrayList<>();

    public boolean running = false;

    public MinionHarvest() {
        instance = this;
        autoHarvest();
    }

    public void autoHarvest() {

        blocksToBreak.clear();
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(Main.instance, new Runnable() {
            public void run() {
                running=true;
                ArrayList<Minion> minions = new ArrayList<>();
                minions.addAll(MinionManager.instance.minions);
                Long millis = System.currentTimeMillis();
                for (Minion minion : minions) {
                    Integer delay = minion.getDelay();
                    if (!lastAction.containsKey(minion)) {
                        makeAction(minion);
                        lastAction.put(minion, millis+delay*1000);
                        continue;
                    }
                    if (lastAction.get(minion) <= millis) {
                        lastAction.put(minion, millis+delay*1000);
                        makeAction(minion);
                        continue;
                    }
                    continue;
                }
                running=false;
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
                    public void run() {
                        if(running==false) {
                            for (Block block : blocksToBreak) {
                                if(block.getType()!=null&&block.getType()!=Material.AIR){
                                    block.setType(Material.AIR);
                                }
                            }
                            blocksToBreak.clear();
                        }
                    }
                }, 0);
            }
        }, 20, 20);
    }

    public void makeAction(Minion minion) {
        if(minion.getBlockFace() == BlockFace.NORTH){
            blocksToBreak.add(Bukkit.getWorld(minion.getBlocLocation().getWorld().getName()).getBlockAt(minion.getBlocLocation().clone().add(0,1,-1)));
        }
    }
}
