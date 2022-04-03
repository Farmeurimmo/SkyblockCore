package main.java.fr.verymc.island;

import main.java.fr.verymc.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.HashMap;

public class IslandValueCalcManager {

    public static IslandValueCalcManager instance;

    public IslandValueCalcManager() {
        instance = this;
        makeCountForAllIsland();
        checkForUpdate();
    }

    public void makeCountForAllIsland() {
        Bukkit.getScheduler().scheduleAsyncDelayedTask(Main.instance, new Runnable() {
            @Override
            public void run() {
                final ArrayList<Material> keys = new ArrayList<>(IslandBlockValues.instance.getMaterials());
                Long startmills = System.currentTimeMillis();

                Double total = 0.0;

                for (Island island : IslandManager.instance.islands) {
                    Double blocs = Double.valueOf(island.getSizeUpgrade().getSize() * island.getSizeUpgrade().getSize());
                    blocs = blocs * 256;

                    final Double blocs2 = blocs;

                    total += blocs;

                    Bukkit.getScheduler().scheduleAsyncDelayedTask(Main.instance, new Runnable() {
                        @Override
                        public void run() {

                            final World world = IslandManager.instance.getMainWorld();
                            int minx = island.getCenter().getBlockX() - island.getSizeUpgrade().getSize();
                            int minz = island.getCenter().getBlockZ() - island.getSizeUpgrade().getSize();
                            int maxx = island.getCenter().getBlockX() + island.getSizeUpgrade().getSize();
                            int maxz = island.getCenter().getBlockZ() + island.getSizeUpgrade().getSize();

                            double value = 0;

                            HashMap<Material, Integer> blocks = new HashMap<>();
                            for (int x = minx; x <= maxx; x++) {
                                for (int z = minz; z < maxz; z++) {
                                    if (world.getBlockAt(x, 0, z).getLightFromSky() == 15) {
                                        continue;
                                    }
                                    for (int y = 0; y < world.getHighestBlockYAt(x, z); y++) {
                                        final Block block = world.getBlockAt(x, y, z);
                                        if (blocks.containsKey(block.getType())) {
                                            blocks.put(block.getType(), blocks.get(block.getType()) + 1);
                                            continue;
                                        }
                                        blocks.put(block.getType(), 1);
                                    }
                                }
                            }
                            for (Material material : keys) {
                                if (blocks.containsKey(material)) {
                                    value += blocks.get(material) * IslandBlockValues.instance.getBlockValue(material);
                                }
                            }

                            island.setValue(value);
                            Long elasped = (System.currentTimeMillis() - startmills);
                            Long elapsedInSec = elasped / 1000 + elasped % 1000;
                            if (elapsedInSec == 0) {
                                elapsedInSec = 1L;
                            }
                            island.sendMessageToEveryMember("§6§lIles §8» §fRecalcul des îles terminé. (en " + elasped + " ms)" +
                                    " (vitesse ~" + ((blocs2 / elapsedInSec) + (blocs2 % elapsedInSec)) + " blocs/s)");
                        }
                    }, 0);
                }
                Long elasped = (System.currentTimeMillis() - startmills);

                Bukkit.broadcastMessage("§6§lIles §8» §fRecalcul des îles lancé. Vitesse moyenne " +
                        IslandManager.instance.islands.size() * 95000 + " blocs/s pour une durée de moyenne de "
                        + total / (IslandManager.instance.islands.size() * 95000) + " seconde(s) par île (non cumulé).");
            }
        }, 0);
    }

    public void checkForUpdate() {
        Bukkit.getScheduler().scheduleAsyncDelayedTask(Main.instance, new Runnable() {
            @Override
            public void run() {
                makeCountForAllIsland();
            }
        }, 20 * 60 * 60);
    }
}
