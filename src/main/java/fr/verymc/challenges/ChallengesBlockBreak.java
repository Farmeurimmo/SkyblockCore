package main.java.fr.verymc.challenges;

import main.java.fr.verymc.Main;
import main.java.fr.verymc.cmd.moderation.BuildCmd;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;

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
            if (currenttype == Material.COBBLESTONE && Main.instance.getData().getBoolean("Joueurs." + player.getUniqueId() + ".Challenges.Daily.1.Active") == true) {
                int progress = Main.instance.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.1.Progression") + 1;
                int palier = Main.instance.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.1.Palier");
                Main.instance.getData().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.1.Progression", progress);
                Main.instance.saveData();
                if (progress >= cobble * palier) {
                    Main.instance.getData().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.1.Progression", progress - cobble * palier);
                    Main.instance.getData().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.1.Palier", palier + 1);
                    Main.instance.saveData();
                    ChallengesGuis.CompleteChallenge(player, 1);
                }
                return;
            }
            if (currenttype == Material.COAL_ORE && Main.instance.getData().getBoolean("Joueurs." + player.getUniqueId() + ".Challenges.Daily.2.Active") == true) {
                int progress = Main.instance.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.2.Progression") + 1;
                int palier = Main.instance.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.2.Palier");
                Main.instance.getData().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.2.Progression", progress);
                Main.instance.saveData();
                if (progress >= coal * palier) {
                    Main.instance.getData().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.2.Progression", progress - coal * palier);
                    Main.instance.getData().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.2.Palier", palier + 1);
                    Main.instance.saveData();
                    ChallengesGuis.CompleteChallenge(player, 2);
                }
                return;
            }
            if (currenttype == Material.IRON_ORE && Main.instance.getData().getBoolean("Joueurs." + player.getUniqueId() + ".Challenges.Daily.3.Active") == true) {
                int progress = Main.instance.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.3.Progression") + 1;
                int palier = Main.instance.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.3.Palier");
                Main.instance.getData().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.3.Progression", progress);
                Main.instance.saveData();
                if (progress >= iron * palier) {
                    Main.instance.getData().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.3.Progression", progress - iron * palier);
                    Main.instance.getData().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.3.Palier", palier + 1);
                    Main.instance.saveData();
                    ChallengesGuis.CompleteChallenge(player, 3);
                }
                return;
            }
            if (currenttype == Material.GOLD_ORE && Main.instance.getData().getBoolean("Joueurs." + player.getUniqueId() + ".Challenges.Daily.4.Active") == true) {
                int progress = Main.instance.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.4.Progression") + 1;
                int palier = Main.instance.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.4.Palier");
                Main.instance.getData().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.4.Progression", progress);
                Main.instance.saveData();
                if (progress >= gold * palier) {
                    Main.instance.getData().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.4.Progression", progress - gold * palier);
                    Main.instance.getData().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.4.Palier", palier + 1);
                    Main.instance.saveData();
                    ChallengesGuis.CompleteChallenge(player, 4);
                }
                return;
            }
            if (currenttype == Material.DIAMOND_ORE && Main.instance.getData().getBoolean("Joueurs." + player.getUniqueId() + ".Challenges.Daily.5.Active") == true) {
                int progress = Main.instance.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.5.Progression") + 1;
                int palier = Main.instance.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.5.Palier");
                Main.instance.getData().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.5.Progression", progress);
                Main.instance.saveData();
                if (progress >= diamond * palier) {
                    Main.instance.getData().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.5.Progression", progress - diamond * palier);
                    Main.instance.getData().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.5.Palier", palier + 1);
                    Main.instance.saveData();
                    ChallengesGuis.CompleteChallenge(player, 5);
                }
                return;
            }
            if (currenttype == Material.EMERALD_ORE && Main.instance.getData().getBoolean("Joueurs." + player.getUniqueId() + ".Challenges.Daily.6.Active") == true) {
                int progress = Main.instance.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.6.Progression") + 1;
                int palier = Main.instance.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.6.Palier");
                Main.instance.getData().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.6.Progression", progress);
                Main.instance.saveData();
                if (progress >= emerald * palier) {
                    Main.instance.getData().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.6.Progression", progress - emerald * palier);
                    Main.instance.getData().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.6.Palier", palier + 1);
                    Main.instance.saveData();
                    ChallengesGuis.CompleteChallenge(player, 6);
                }
                return;
            }
            if (currenttype == Material.ANCIENT_DEBRIS && Main.instance.getData().getBoolean("Joueurs." + player.getUniqueId() + ".Challenges.Daily.7.Active") == true) {
                int progress = Main.instance.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.7.Progression") + 1;
                int palier = Main.instance.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.7.Palier");
                Main.instance.getData().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.7.Progression", progress);
                Main.instance.saveData();
                if (progress >= debris * palier) {
                    Main.instance.getData().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.7.Progression", progress - debris * palier);
                    Main.instance.getData().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.7.Palier", palier + 1);
                    Main.instance.saveData();
                    ChallengesGuis.CompleteChallenge(player, 7);
                }
                return;
            }
            if (currenttype == Material.OAK_LOG && Main.instance.getData().getBoolean("Joueurs." + player.getUniqueId() + ".Challenges.Daily.8.Active") == true) {
                int progress = Main.instance.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.8.Progression") + 1;
                int palier = Main.instance.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.8.Palier");
                Main.instance.getData().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.8.Progression", progress);
                Main.instance.saveData();
                if (progress >= oak_log * palier) {
                    Main.instance.getData().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.8.Progression", progress - oak_log * palier);
                    Main.instance.getData().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.8.Palier", palier + 1);
                    Main.instance.saveData();
                    ChallengesGuis.CompleteChallenge(player, 8);
                }
                return;
            }
            if (currenttype == Material.BIRCH_LOG && Main.instance.getData().getBoolean("Joueurs." + player.getUniqueId() + ".Challenges.Daily.9.Active") == true) {
                int progress = Main.instance.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.9.Progression") + 1;
                int palier = Main.instance.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.9.Palier");
                Main.instance.getData().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.9.Progression", progress);
                Main.instance.saveData();
                if (progress >= birch_log * palier) {
                    Main.instance.getData().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.9.Progression", progress - birch_log * palier);
                    Main.instance.getData().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.9.Palier", palier + 1);
                    Main.instance.saveData();
                    ChallengesGuis.CompleteChallenge(player, 9);
                }
                return;
            }
            if (currenttype == Material.SPRUCE_LOG && Main.instance.getData().getBoolean("Joueurs." + player.getUniqueId() + ".Challenges.Daily.10.Active") == true) {
                int progress = Main.instance.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.10.Progression") + 1;
                int palier = Main.instance.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.10.Palier");
                Main.instance.getData().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.10.Progression", progress);
                Main.instance.saveData();
                if (progress >= spruce_log * palier) {
                    Main.instance.getData().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.10.Progression", progress - spruce_log * palier);
                    Main.instance.getData().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.10.Palier", palier + 1);
                    Main.instance.saveData();
                    ChallengesGuis.CompleteChallenge(player, 10);
                }
                return;
            }
            if (currenttype == Material.DARK_OAK_LOG && Main.instance.getData().getBoolean("Joueurs." + player.getUniqueId() + ".Challenges.Daily.11.Active") == true) {
                int progress = Main.instance.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.11.Progression") + 1;
                int palier = Main.instance.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.11.Palier");
                Main.instance.getData().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.11.Progression", progress);
                Main.instance.saveData();
                if (progress >= dark_oak_log * palier) {
                    Main.instance.getData().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.11.Progression", progress - dark_oak_log * palier);
                    Main.instance.getData().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.11.Palier", palier + 1);
                    Main.instance.saveData();
                    ChallengesGuis.CompleteChallenge(player, 11);
                }
                return;
            }
            if (currenttype == Material.ACACIA_LOG && Main.instance.getData().getBoolean("Joueurs." + player.getUniqueId() + ".Challenges.Daily.12.Active") == true) {
                int progress = Main.instance.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.12.Progression") + 1;
                int palier = Main.instance.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.12.Palier");
                Main.instance.getData().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.12.Progression", progress);
                Main.instance.saveData();
                if (progress >= acacia_log * palier) {
                    Main.instance.getData().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.12.Progression", progress - acacia_log * palier);
                    Main.instance.getData().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.12.Palier", palier + 1);
                    Main.instance.saveData();
                    ChallengesGuis.CompleteChallenge(player, 12);
                }
                return;
            }
            if (currenttype == Material.JUNGLE_LOG && Main.instance.getData().getBoolean("Joueurs." + player.getUniqueId() + ".Challenges.Daily.13.Active") == true) {
                int progress = Main.instance.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.13.Progression") + 1;
                int palier = Main.instance.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.13.Palier");
                Main.instance.getData().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.13.Progression", progress);
                Main.instance.saveData();
                if (progress >= jungle_log * palier) {
                    Main.instance.getData().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.13.Progression", progress - jungle_log * palier);
                    Main.instance.getData().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.13.Palier", palier + 1);
                    Main.instance.saveData();
                    ChallengesGuis.CompleteChallenge(player, 13);
                }
                return;
            }
        }
    }
}
