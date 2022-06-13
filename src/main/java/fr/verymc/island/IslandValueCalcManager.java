package main.java.fr.verymc.island;

import main.java.fr.verymc.Main;
import main.java.fr.verymc.island.blocks.Chest;
import main.java.fr.verymc.island.guis.IslandTopGui;
import main.java.fr.verymc.utils.DiscordUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class IslandValueCalcManager {

    public static IslandValueCalcManager instance;

    public IslandValueCalcManager() {
        instance = this;
        makeCountForAllIsland();
        checkForUpdate();
        checkForUpdateWebHook();
    }

    public void makeCountForAllIsland() {
        /*CompletableFuture.runAsync(() -> {
            Long startMillis = System.currentTimeMillis();
            HashMap<Material, Double> materialDoubleHashMap = IslandBlocsValues.instance.getBlockValues();

            Bukkit.broadcastMessage("§6§lIles §8» §fRecalcul de toutes les îles lancé. Cette opération peut prendre plusieurs minutes" +
                    " mais n'affectera pas votre expérience de jeu.");

            for (Island island : IslandManager.instance.islands) {

                CompletableFuture.runAsync(() -> {

                    final World world = IslandManager.instance.getMainWorld();
                    final int minx = island.getCenter().getBlockX() - island.getSizeUpgrade().getSize();
                    final int minz = island.getCenter().getBlockZ() - island.getSizeUpgrade().getSize();
                    final int maxx = island.getCenter().getBlockX() + island.getSizeUpgrade().getSize();
                    final int maxz = island.getCenter().getBlockZ() + island.getSizeUpgrade().getSize();

                    AtomicDouble total = new AtomicDouble(0);
                    AtomicInteger totalThreads = new AtomicInteger();
                    for (int x = minx; x <= maxx; x++) {
                        int finalX = x;
                        Bukkit.broadcastMessage("x : " + x);
                            for (int z = minz; z < maxz; z++) {
                                for (int y = 0; y <= world.getHighestBlockAt(finalX, z).getY(); y++) {
                                    final Block block = world.getBlockAt(finalX, y, z);
                                    if (block.getType() == Material.AIR) {
                                        continue;
                                    }
                                    if (materialDoubleHashMap.containsKey(block.getType())) {
                                        total.getAndAdd(materialDoubleHashMap.get(block.getType()));
                                        continue;
                                    }
                                }
                            }
                    }
                    CompletableFuture.runAsync(() -> {
                        while (totalThreads.get() < maxx - minx) {
                            try {
                                Bukkit.broadcastMessage("Waiting for the island " + island.getName() + " to be calculated | current:" + totalThreads.get() + " | max:" + (maxx - minx));
                                Thread.sleep(5000);
                            } catch (InterruptedException e) {}
                        }
                            }).join();

                    IslandManager.instance.getIslandByLoc(island.getCenter()).setValue(total.doubleValue());
                    Long elapsed = (System.currentTimeMillis() - startMillis);

                    IslandManager.instance.getIslandByLoc(island.getCenter()).sendMessageToEveryMember("§6§lIles §8» §fRecalcul de votre île terminé. (en " + elapsed + " ms)");
                }).join();
            }
        });
    }*/
        CompletableFuture.runAsync(() -> {
            final ArrayList<Material> keys = new ArrayList<>(IslandBlocsValues.instance.getMaterials());
            Long startmills = System.currentTimeMillis();

            for (Island island : IslandManager.instance.islands) {

                CompletableFuture.runAsync(() -> {

                    int minx = island.getCenter().getBlockX() - island.getSizeUpgrade().getSize();
                    int minz = island.getCenter().getBlockZ() - island.getSizeUpgrade().getSize();
                    int maxx = island.getCenter().getBlockX() + island.getSizeUpgrade().getSize();
                    int maxz = island.getCenter().getBlockZ() + island.getSizeUpgrade().getSize();

                    double value = 0;

                    for (Chest chest : island.getChests()) {
                        if (chest.getType() != 3) continue;
                        if (chest.getBlock().getX() >= minx && chest.getBlock().getX() <= maxx && chest.getBlock().getZ() >= minz &&
                                chest.getBlock().getZ() <= maxz) {
                            value += IslandBlocsValues.instance.getBlockValue(chest.getStacked()) * chest.getAmount();
                        }
                    }

                    IslandManager.instance.getIslandByLoc(island.getCenter()).setValue(value);
                    Long elasped = (System.currentTimeMillis() - startmills);

                    //IslandManager.instance.getIslandByLoc(island.getCenter()).sendMessageToEveryMember("§6§lIles §8» §fRecalcul de votre île terminé. (en " + elasped + " ms)");
                });
            }
        });
    }

    public void checkForUpdate() {
        Bukkit.getScheduler().scheduleAsyncDelayedTask(Main.instance, new Runnable() {
            @Override
            public void run() {
                makeCountForAllIsland();
                checkForUpdate();
            }
        }, 20 * 60 * 30);
    }

    public void checkForUpdateWebHook() {
        Bukkit.getScheduler().scheduleAsyncDelayedTask(Main.instance, new Runnable() {
            @Override
            public void run() {
                Calendar cal = Calendar.getInstance();
                if (cal.getTime().getHours() == 21) {
                    if (cal.getTime().getMinutes() == 0) {
                        if (cal.getTime().getSeconds() == 0) {
                            sendWebHookTop();
                        }
                    }
                }
                checkForUpdateWebHook();
            }
        }, 20);
    }


    public void sendWebHookTop() {
        CompletableFuture.runAsync(() -> {
            Calendar cal = Calendar.getInstance();
            long start = System.currentTimeMillis();
            HashMap<Island, Integer> pos = IslandTopGui.instance.getTopIsland();
            final DiscordUtils webhook = new DiscordUtils("https://discord.com/api/webhooks/977574291103158304/sIkg_XdYxEPb3b3BRPRhdzE4Fe0B-G7IvlufmvII1sbyKg4FMa5j-iXFBCN_IzO5Z5Xu");
            webhook.setAvatarUrl("https://cdn.discordapp.com/attachments/567693189142675467/977562122357731358/logo_64x64.png");
            webhook.setUsername("Skyblock");
            String toSend = "";
            for (int i = 0; i < pos.size(); i++) {
                if (i > 9) break;
                for (Map.Entry<Island, Integer> entry : pos.entrySet()) {
                    if ((i + 1) == entry.getValue()) {
                        toSend += "**" + (i + 1) + ".**  " + entry.getKey().getName().replace("\n", "") + "  **" + entry.getKey().getValue() + " points**\\n";
                        break;
                    }
                }
            }
            DiscordUtils.EmbedObject embed = new DiscordUtils.EmbedObject();
            embed.setTitle("Classement des îles");
            embed.setDescription(toSend);
            webhook.addEmbed(embed);
            try {
                webhook.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Island top sended in " + (System.currentTimeMillis() - start) + "ms");
        });
    }
}
