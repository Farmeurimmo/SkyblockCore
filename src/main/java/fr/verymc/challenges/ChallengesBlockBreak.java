package main.java.fr.verymc.challenges;

import main.java.fr.verymc.Main;
import main.java.fr.verymc.cmd.moderation.BuildCmd;
import main.java.fr.verymc.config.AsyncSaver;
import main.java.fr.verymc.config.ConfigManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.HashMap;

public class ChallengesBlockBreak implements Listener {

    public static int cobble = 256;
    public static int coal = 192;
    public static int iron = 128;
    public static int gold = 96;
    public static int diamond = 96;
    public static int emerald = 64;
    public static int debris = 48;

    public static int oak_log = 96;
    public static int birch_log = 96;
    public static int spruce_log = 96;
    public static int dark_oak_log = 96;
    public static int acacia_log = 96;
    public static int jungle_log = 96;

    //test test test test test


    @EventHandler
    public void blockPlaceEvent(BlockPlaceEvent e) {
        e.getBlock().setMetadata("placed", new FixedMetadataValue(Main.instance, e.getPlayer().getName()));
    }

    @EventHandler
    public void BlockForm(BlockFormEvent e) {
        if (e.getBlock().getMetadata("placed") != null) {
            e.getBlock().removeMetadata("placed", Main.instance);
        }
    }

    @EventHandler
    public void blockBreakEvent(BlockBreakEvent e) {
        Player player = e.getPlayer();
        Material currenttype = e.getBlock().getType();
        Block block = e.getBlock();
        if (block.hasMetadata("placed")) {
            return;
        }
        if (!e.isCancelled() && !BuildCmd.Build.contains(player)) {
            if (currenttype == Material.COBBLESTONE && ConfigManager.instance.getDataAtoutsChallenges().getBoolean("Joueurs." + player.getUniqueId() + ".Challenges.Daily.1.Active") == true) {
                int progress = ConfigManager.instance.getDataAtoutsChallenges().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.1.Progression") + 1;
                int palier = ConfigManager.instance.getDataAtoutsChallenges().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.1.Palier");
                HashMap<String, Object> objectHashMap = new HashMap<>();
                objectHashMap.put("Joueurs." + player.getUniqueId() + ".Challenges.Daily.1.Progression", progress);
                if (progress >= cobble * palier) {
                    objectHashMap.put("Joueurs." + player.getUniqueId() + ".Challenges.Daily.1.Progression", progress - cobble * palier);
                    objectHashMap.put("Joueurs." + player.getUniqueId() + ".Challenges.Daily.1.Palier", palier + 1);
                    ChallengesGuis.CompleteChallenge(player, 1);
                }
                AsyncSaver.instance.setAndSaveAsync(objectHashMap, ConfigManager.instance.getDataAtoutsChallenges(),
                        ConfigManager.instance.fileChallengesAtouts);
                return;
            }
            if (currenttype == Material.COAL_ORE && ConfigManager.instance.getDataAtoutsChallenges().getBoolean("Joueurs." + player.getUniqueId() + ".Challenges.Daily.2.Active") == true) {
                int progress = ConfigManager.instance.getDataAtoutsChallenges().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.2.Progression") + 1;
                int palier = ConfigManager.instance.getDataAtoutsChallenges().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.2.Palier");
                ConfigManager.instance.getDataAtoutsChallenges().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.2.Progression", progress);
                ConfigManager.instance.saveDataAtoutsChallenges();
                if (progress >= coal * palier) {
                    ConfigManager.instance.getDataAtoutsChallenges().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.2.Progression", progress - coal * palier);
                    ConfigManager.instance.getDataAtoutsChallenges().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.2.Palier", palier + 1);
                    ConfigManager.instance.saveDataAtoutsChallenges();
                    ChallengesGuis.CompleteChallenge(player, 2);
                }
                return;
            }
            if (currenttype == Material.IRON_ORE && ConfigManager.instance.getDataAtoutsChallenges().getBoolean("Joueurs." + player.getUniqueId() + ".Challenges.Daily.3.Active") == true) {
                int progress = ConfigManager.instance.getDataAtoutsChallenges().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.3.Progression") + 1;
                int palier = ConfigManager.instance.getDataAtoutsChallenges().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.3.Palier");
                ConfigManager.instance.getDataAtoutsChallenges().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.3.Progression", progress);
                ConfigManager.instance.saveDataAtoutsChallenges();
                if (progress >= iron * palier) {
                    ConfigManager.instance.getDataAtoutsChallenges().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.3.Progression", progress - iron * palier);
                    ConfigManager.instance.getDataAtoutsChallenges().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.3.Palier", palier + 1);
                    ConfigManager.instance.saveDataAtoutsChallenges();
                    ChallengesGuis.CompleteChallenge(player, 3);
                }
                return;
            }
            if (currenttype == Material.GOLD_ORE && ConfigManager.instance.getDataAtoutsChallenges().getBoolean("Joueurs." + player.getUniqueId() + ".Challenges.Daily.4.Active") == true) {
                int progress = ConfigManager.instance.getDataAtoutsChallenges().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.4.Progression") + 1;
                int palier = ConfigManager.instance.getDataAtoutsChallenges().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.4.Palier");
                ConfigManager.instance.getDataAtoutsChallenges().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.4.Progression", progress);
                ConfigManager.instance.saveDataAtoutsChallenges();
                if (progress >= gold * palier) {
                    ConfigManager.instance.getDataAtoutsChallenges().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.4.Progression", progress - gold * palier);
                    ConfigManager.instance.getDataAtoutsChallenges().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.4.Palier", palier + 1);
                    ConfigManager.instance.saveDataAtoutsChallenges();
                    ChallengesGuis.CompleteChallenge(player, 4);
                }
                return;
            }
            if (currenttype == Material.DIAMOND_ORE && ConfigManager.instance.getDataAtoutsChallenges().getBoolean("Joueurs." + player.getUniqueId() + ".Challenges.Daily.5.Active") == true) {
                int progress = ConfigManager.instance.getDataAtoutsChallenges().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.5.Progression") + 1;
                int palier = ConfigManager.instance.getDataAtoutsChallenges().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.5.Palier");
                ConfigManager.instance.getDataAtoutsChallenges().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.5.Progression", progress);
                ConfigManager.instance.saveDataAtoutsChallenges();
                if (progress >= diamond * palier) {
                    ConfigManager.instance.getDataAtoutsChallenges().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.5.Progression", progress - diamond * palier);
                    ConfigManager.instance.getDataAtoutsChallenges().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.5.Palier", palier + 1);
                    ConfigManager.instance.saveDataAtoutsChallenges();
                    ChallengesGuis.CompleteChallenge(player, 5);
                }
                return;
            }
            if (currenttype == Material.EMERALD_ORE && ConfigManager.instance.getDataAtoutsChallenges().getBoolean("Joueurs." + player.getUniqueId() + ".Challenges.Daily.6.Active") == true) {
                int progress = ConfigManager.instance.getDataAtoutsChallenges().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.6.Progression") + 1;
                int palier = ConfigManager.instance.getDataAtoutsChallenges().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.6.Palier");
                ConfigManager.instance.getDataAtoutsChallenges().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.6.Progression", progress);
                ConfigManager.instance.saveDataAtoutsChallenges();
                if (progress >= emerald * palier) {
                    ConfigManager.instance.getDataAtoutsChallenges().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.6.Progression", progress - emerald * palier);
                    ConfigManager.instance.getDataAtoutsChallenges().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.6.Palier", palier + 1);
                    ConfigManager.instance.saveDataAtoutsChallenges();
                    ChallengesGuis.CompleteChallenge(player, 6);
                }
                return;
            }
            if (currenttype == Material.ANCIENT_DEBRIS && ConfigManager.instance.getDataAtoutsChallenges().getBoolean("Joueurs." + player.getUniqueId() + ".Challenges.Daily.7.Active") == true) {
                int progress = ConfigManager.instance.getDataAtoutsChallenges().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.7.Progression") + 1;
                int palier = ConfigManager.instance.getDataAtoutsChallenges().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.7.Palier");
                ConfigManager.instance.getDataAtoutsChallenges().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.7.Progression", progress);
                ConfigManager.instance.saveDataAtoutsChallenges();
                if (progress >= debris * palier) {
                    ConfigManager.instance.getDataAtoutsChallenges().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.7.Progression", progress - debris * palier);
                    ConfigManager.instance.getDataAtoutsChallenges().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.7.Palier", palier + 1);
                    ConfigManager.instance.saveDataAtoutsChallenges();
                    ChallengesGuis.CompleteChallenge(player, 7);
                }
                return;
            }
            if (currenttype == Material.OAK_LOG && ConfigManager.instance.getDataAtoutsChallenges().getBoolean("Joueurs." + player.getUniqueId() + ".Challenges.Daily.8.Active") == true) {
                int progress = ConfigManager.instance.getDataAtoutsChallenges().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.8.Progression") + 1;
                int palier = ConfigManager.instance.getDataAtoutsChallenges().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.8.Palier");
                ConfigManager.instance.getDataAtoutsChallenges().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.8.Progression", progress);
                ConfigManager.instance.saveDataAtoutsChallenges();
                if (progress >= oak_log * palier) {
                    ConfigManager.instance.getDataAtoutsChallenges().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.8.Progression", progress - oak_log * palier);
                    ConfigManager.instance.getDataAtoutsChallenges().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.8.Palier", palier + 1);
                    ConfigManager.instance.saveDataAtoutsChallenges();
                    ChallengesGuis.CompleteChallenge(player, 8);
                }
                return;
            }
            if (currenttype == Material.BIRCH_LOG && ConfigManager.instance.getDataAtoutsChallenges().getBoolean("Joueurs." + player.getUniqueId() + ".Challenges.Daily.9.Active") == true) {
                int progress = ConfigManager.instance.getDataAtoutsChallenges().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.9.Progression") + 1;
                int palier = ConfigManager.instance.getDataAtoutsChallenges().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.9.Palier");
                ConfigManager.instance.getDataAtoutsChallenges().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.9.Progression", progress);
                ConfigManager.instance.saveDataAtoutsChallenges();
                if (progress >= birch_log * palier) {
                    ConfigManager.instance.getDataAtoutsChallenges().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.9.Progression", progress - birch_log * palier);
                    ConfigManager.instance.getDataAtoutsChallenges().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.9.Palier", palier + 1);
                    ConfigManager.instance.saveDataAtoutsChallenges();
                    ChallengesGuis.CompleteChallenge(player, 9);
                }
                return;
            }
            if (currenttype == Material.SPRUCE_LOG && ConfigManager.instance.getDataAtoutsChallenges().getBoolean("Joueurs." + player.getUniqueId() + ".Challenges.Daily.10.Active") == true) {
                int progress = ConfigManager.instance.getDataAtoutsChallenges().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.10.Progression") + 1;
                int palier = ConfigManager.instance.getDataAtoutsChallenges().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.10.Palier");
                ConfigManager.instance.getDataAtoutsChallenges().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.10.Progression", progress);
                ConfigManager.instance.saveDataAtoutsChallenges();
                if (progress >= spruce_log * palier) {
                    ConfigManager.instance.getDataAtoutsChallenges().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.10.Progression", progress - spruce_log * palier);
                    ConfigManager.instance.getDataAtoutsChallenges().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.10.Palier", palier + 1);
                    ConfigManager.instance.saveDataAtoutsChallenges();
                    ChallengesGuis.CompleteChallenge(player, 10);
                }
                return;
            }
            if (currenttype == Material.DARK_OAK_LOG && ConfigManager.instance.getDataAtoutsChallenges().getBoolean("Joueurs." + player.getUniqueId() + ".Challenges.Daily.11.Active") == true) {
                int progress = ConfigManager.instance.getDataAtoutsChallenges().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.11.Progression") + 1;
                int palier = ConfigManager.instance.getDataAtoutsChallenges().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.11.Palier");
                ConfigManager.instance.getDataAtoutsChallenges().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.11.Progression", progress);
                ConfigManager.instance.saveDataAtoutsChallenges();
                if (progress >= dark_oak_log * palier) {
                    ConfigManager.instance.getDataAtoutsChallenges().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.11.Progression", progress - dark_oak_log * palier);
                    ConfigManager.instance.getDataAtoutsChallenges().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.11.Palier", palier + 1);
                    ConfigManager.instance.saveDataAtoutsChallenges();
                    ChallengesGuis.CompleteChallenge(player, 11);
                }
                return;
            }
            if (currenttype == Material.ACACIA_LOG && ConfigManager.instance.getDataAtoutsChallenges().getBoolean("Joueurs." + player.getUniqueId() + ".Challenges.Daily.12.Active") == true) {
                int progress = ConfigManager.instance.getDataAtoutsChallenges().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.12.Progression") + 1;
                int palier = ConfigManager.instance.getDataAtoutsChallenges().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.12.Palier");
                ConfigManager.instance.getDataAtoutsChallenges().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.12.Progression", progress);
                ConfigManager.instance.saveDataAtoutsChallenges();
                if (progress >= acacia_log * palier) {
                    ConfigManager.instance.getDataAtoutsChallenges().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.12.Progression", progress - acacia_log * palier);
                    ConfigManager.instance.getDataAtoutsChallenges().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.12.Palier", palier + 1);
                    ConfigManager.instance.saveDataAtoutsChallenges();
                    ChallengesGuis.CompleteChallenge(player, 12);
                }
                return;
            }
            if (currenttype == Material.JUNGLE_LOG && ConfigManager.instance.getDataAtoutsChallenges().getBoolean("Joueurs." + player.getUniqueId() + ".Challenges.Daily.13.Active") == true) {
                int progress = ConfigManager.instance.getDataAtoutsChallenges().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.13.Progression") + 1;
                int palier = ConfigManager.instance.getDataAtoutsChallenges().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.13.Palier");
                ConfigManager.instance.getDataAtoutsChallenges().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.13.Progression", progress);
                ConfigManager.instance.saveDataAtoutsChallenges();
                if (progress >= jungle_log * palier) {
                    ConfigManager.instance.getDataAtoutsChallenges().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.13.Progression", progress - jungle_log * palier);
                    ConfigManager.instance.getDataAtoutsChallenges().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.13.Palier", palier + 1);
                    ConfigManager.instance.saveDataAtoutsChallenges();
                    ChallengesGuis.CompleteChallenge(player, 13);
                }
                return;
            }
        }
    }
}
