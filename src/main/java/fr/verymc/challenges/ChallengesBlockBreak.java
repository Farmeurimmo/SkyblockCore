package main.java.fr.verymc.challenges;

import main.java.fr.verymc.cmd.moderation.BuildCmd;
import main.java.fr.verymc.core.Main;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class ChallengesBlockBreak implements Listener {

    public static int cobble = 428;
    public static int coal = 192;
    public static int iron = 128;
    public static int gold = 96;
    public static int diamond = 64;
    public static int emerald = 32;
    public static int debris = 16;

    @EventHandler
    public void blockBreakEvent(BlockBreakEvent e) {
        Player player = e.getPlayer();
        Block aaa = e.getBlock();
        if (!e.isCancelled() && !BuildCmd.Build.contains(player)) {
            if (aaa.getType() == Material.COBBLESTONE && Main.instance1.getData().getBoolean("Joueurs." + player.getUniqueId() + ".Challenges.Daily.1.Active") == true) {
                int progress = Main.instance1.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.1.Progression") + 1;
                int palier = Main.instance1.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.1.Palier");
                Main.instance1.getData().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.1.Progression", progress);
                Main.instance1.saveData();
                if (progress >= cobble * palier) {
                    Main.instance1.getData().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.1.Progression", 0);
                    Main.instance1.getData().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.1.Palier", palier + 1);
                    Main.instance1.saveData();
                    ChallengesGuis.CompleteChallenge(player, 1);
                }
            }
            if (aaa.getType() == Material.COAL_ORE && Main.instance1.getData().getBoolean("Joueurs." + player.getUniqueId() + ".Challenges.Daily.2.Active") == true) {
                int progress = Main.instance1.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.2.Progression") + 1;
                int palier = Main.instance1.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.2.Palier");
                Main.instance1.getData().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.2.Progression", progress);
                Main.instance1.saveData();
                if (progress >= coal * palier) {
                    Main.instance1.getData().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.2.Progression", 0);
                    Main.instance1.getData().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.2.Palier", palier + 1);
                    Main.instance1.saveData();
                    ChallengesGuis.CompleteChallenge(player, 2);
                }
            }
            if (aaa.getType() == Material.IRON_ORE && Main.instance1.getData().getBoolean("Joueurs." + player.getUniqueId() + ".Challenges.Daily.3.Active") == true) {
                int progress = Main.instance1.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.3.Progression") + 1;
                int palier = Main.instance1.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.3.Palier");
                Main.instance1.getData().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.3.Progression", progress);
                Main.instance1.saveData();
                if (progress >= iron * palier) {
                    Main.instance1.getData().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.3.Progression", 0);
                    Main.instance1.getData().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.3.Palier", palier + 1);
                    Main.instance1.saveData();
                    ChallengesGuis.CompleteChallenge(player, 3);
                }
            }
            if (aaa.getType() == Material.GOLD_ORE && Main.instance1.getData().getBoolean("Joueurs." + player.getUniqueId() + ".Challenges.Daily.4.Active") == true) {
                int progress = Main.instance1.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.4.Progression") + 1;
                int palier = Main.instance1.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.4.Palier");
                Main.instance1.getData().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.4.Progression", progress);
                Main.instance1.saveData();
                if (progress >= gold * palier) {
                    Main.instance1.getData().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.4.Progression", 0);
                    Main.instance1.getData().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.4.Palier", palier + 1);
                    Main.instance1.saveData();
                    ChallengesGuis.CompleteChallenge(player, 4);
                }
            }
            if (aaa.getType() == Material.DIAMOND_ORE && Main.instance1.getData().getBoolean("Joueurs." + player.getUniqueId() + ".Challenges.Daily.5.Active") == true) {
                int progress = Main.instance1.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.5.Progression") + 1;
                int palier = Main.instance1.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.5.Palier");
                Main.instance1.getData().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.5.Progression", progress);
                Main.instance1.saveData();
                if (progress >= diamond * palier) {
                    Main.instance1.getData().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.5.Progression", 0);
                    Main.instance1.getData().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.5.Palier", palier + 1);
                    Main.instance1.saveData();
                    ChallengesGuis.CompleteChallenge(player, 5);
                }
            }
            if (aaa.getType() == Material.EMERALD_ORE && Main.instance1.getData().getBoolean("Joueurs." + player.getUniqueId() + ".Challenges.Daily.6.Active") == true) {
                int progress = Main.instance1.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.6.Progression") + 1;
                int palier = Main.instance1.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.6.Palier");
                Main.instance1.getData().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.6.Progression", progress);
                Main.instance1.saveData();
                if (progress >= emerald * palier) {
                    Main.instance1.getData().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.6.Progression", 0);
                    Main.instance1.getData().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.6.Palier", palier + 1);
                    Main.instance1.saveData();
                    ChallengesGuis.CompleteChallenge(player, 6);
                }
            }
            if (aaa.getType() == Material.ANCIENT_DEBRIS && Main.instance1.getData().getBoolean("Joueurs." + player.getUniqueId() + ".Challenges.Daily.7.Active") == true) {
                int progress = Main.instance1.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.7.Progression") + 1;
                int palier = Main.instance1.getData().getInt("Joueurs." + player.getUniqueId() + ".Challenges.Daily.7.Palier");
                Main.instance1.getData().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.7.Progression", progress);
                Main.instance1.saveData();
                if (progress >= debris * palier) {
                    Main.instance1.getData().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.7.Progression", 0);
                    Main.instance1.getData().set("Joueurs." + player.getUniqueId() + ".Challenges.Daily.7.Palier", palier + 1);
                    Main.instance1.saveData();
                    ChallengesGuis.CompleteChallenge(player, 7);
                }
            }
        }
    }
}
