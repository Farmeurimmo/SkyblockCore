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

                for (Island island : IslandManager.instance.islands) {

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

                            island.sendMessageToEveryMember("§6§lIles §8» §fRecalcul de votre île terminé. (en " + elasped + " ms)");
                        }
                    }, 0);
                }
                Bukkit.broadcastMessage("§6§lIles §8» §fRecalcul de toutes les îles lancé. Cette opération peut prendre plusieurs minutes" +
                        " mais n'affectera pas votre expérience de jeu.");
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
