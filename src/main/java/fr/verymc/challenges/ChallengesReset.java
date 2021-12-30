package main.java.fr.verymc.challenges;

import main.java.fr.verymc.core.Main;
import org.bukkit.Bukkit;

import java.util.Calendar;
import java.util.TimeZone;

public class ChallengesReset {

    public static void CheckForReset() {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Paris"));
        Calendar calendar = Calendar.getInstance();
        if (calendar.getTime().getHours() == 0 && calendar.getTime().getMinutes() == 0 &&
                calendar.getTime().getSeconds() == 0) {
            ResetAllChallenges();
        }
        Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
            public void run() {
                CheckForReset();
            }
        }, 20);
    }

    public static void CreateChallengesForPlayer(String aa) {
        if (Main.instance1.getData().getString("Joueurs." + aa + ".Challenges.Daily.1.Active") == null) {
            Main.instance1.getData().set("Joueurs." + aa + ".Challenges.Daily.1.Active", true);
            Main.instance1.getData().set("Joueurs." + aa + ".Challenges.Daily.1.Progression", 0);
            Main.instance1.getData().set("Joueurs." + aa + ".Challenges.Daily.1.Palier", 1);
            Main.instance1.saveData();
        }
        if (Main.instance1.getData().getString("Joueurs." + aa + ".Challenges.Daily.2.Active") == null) {
            Main.instance1.getData().set("Joueurs." + aa + ".Challenges.Daily.2.Active", true);
            Main.instance1.getData().set("Joueurs." + aa + ".Challenges.Daily.2.Progression", 0);
            Main.instance1.getData().set("Joueurs." + aa + ".Challenges.Daily.2.Palier", 1);
            Main.instance1.saveData();
        }
        if (Main.instance1.getData().getString("Joueurs." + aa + ".Challenges.Daily.3.Active") == null) {
            Main.instance1.getData().set("Joueurs." + aa + ".Challenges.Daily.3.Active", true);
            Main.instance1.getData().set("Joueurs." + aa + ".Challenges.Daily.3.Progression", 0);
            Main.instance1.getData().set("Joueurs." + aa + ".Challenges.Daily.3.Palier", 1);
            Main.instance1.saveData();
        }
        if (Main.instance1.getData().getString("Joueurs." + aa + ".Challenges.Daily.4.Active") == null) {
            Main.instance1.getData().set("Joueurs." + aa + ".Challenges.Daily.4.Active", true);
            Main.instance1.getData().set("Joueurs." + aa + ".Challenges.Daily.4.Progression", 0);
            Main.instance1.getData().set("Joueurs." + aa + ".Challenges.Daily.4.Palier", 1);
            Main.instance1.saveData();
        }
        if (Main.instance1.getData().getString("Joueurs." + aa + ".Challenges.Daily.5.Active") == null) {
            Main.instance1.getData().set("Joueurs." + aa + ".Challenges.Daily.5.Active", true);
            Main.instance1.getData().set("Joueurs." + aa + ".Challenges.Daily.5.Progression", 0);
            Main.instance1.getData().set("Joueurs." + aa + ".Challenges.Daily.5.Palier", 1);
            Main.instance1.saveData();
        }
        if (Main.instance1.getData().getString("Joueurs." + aa + ".Challenges.Daily.6.Active") == null) {
            Main.instance1.getData().set("Joueurs." + aa + ".Challenges.Daily.6.Active", true);
            Main.instance1.getData().set("Joueurs." + aa + ".Challenges.Daily.6.Progression", 0);
            Main.instance1.getData().set("Joueurs." + aa + ".Challenges.Daily.6.Palier", 1);
            Main.instance1.saveData();
        }
        if (Main.instance1.getData().getString("Joueurs." + aa + ".Challenges.Daily.7.Active") == null) {
            Main.instance1.getData().set("Joueurs." + aa + ".Challenges.Daily.7.Active", true);
            Main.instance1.getData().set("Joueurs." + aa + ".Challenges.Daily.7.Progression", 0);
            Main.instance1.getData().set("Joueurs." + aa + ".Challenges.Daily.7.Palier", 1);
            Main.instance1.saveData();
        }
    }

    public static void ResetAllChallenges() {
        for (String aa : Main.instance1.getData().getConfigurationSection("Joueurs").getKeys(false)) {
            Main.instance1.getData().set("Joueurs." + aa + ".Challenges.Daily.1.Active", true);
            Main.instance1.getData().set("Joueurs." + aa + ".Challenges.Daily.1.Progression", 0);
            Main.instance1.getData().set("Joueurs." + aa + ".Challenges.Daily.1.Palier", 1);

            Main.instance1.getData().set("Joueurs." + aa + ".Challenges.Daily.2.Active", true);
            Main.instance1.getData().set("Joueurs." + aa + ".Challenges.Daily.2.Progression", 0);
            Main.instance1.getData().set("Joueurs." + aa + ".Challenges.Daily.2.Palier", 1);

            Main.instance1.getData().set("Joueurs." + aa + ".Challenges.Daily.3.Active", true);
            Main.instance1.getData().set("Joueurs." + aa + ".Challenges.Daily.3.Progression", 0);
            Main.instance1.getData().set("Joueurs." + aa + ".Challenges.Daily.3.Palier", 1);

            Main.instance1.getData().set("Joueurs." + aa + ".Challenges.Daily.4.Active", true);
            Main.instance1.getData().set("Joueurs." + aa + ".Challenges.Daily.4.Progression", 0);
            Main.instance1.getData().set("Joueurs." + aa + ".Challenges.Daily.4.Palier", 1);

            Main.instance1.getData().set("Joueurs." + aa + ".Challenges.Daily.5.Active", true);
            Main.instance1.getData().set("Joueurs." + aa + ".Challenges.Daily.5.Progression", 0);
            Main.instance1.getData().set("Joueurs." + aa + ".Challenges.Daily.5.Palier", 1);

            Main.instance1.getData().set("Joueurs." + aa + ".Challenges.Daily.6.Active", true);
            Main.instance1.getData().set("Joueurs." + aa + ".Challenges.Daily.6.Progression", 0);
            Main.instance1.getData().set("Joueurs." + aa + ".Challenges.Daily.6.Palier", 1);

            Main.instance1.getData().set("Joueurs." + aa + ".Challenges.Daily.7.Active", true);
            Main.instance1.getData().set("Joueurs." + aa + ".Challenges.Daily.7.Progression", 0);
            Main.instance1.getData().set("Joueurs." + aa + ".Challenges.Daily.7.Palier", 1);
        }
        Main.instance1.saveData();
        Bukkit.broadcastMessage("§6§lChallenges §8» §fTous les challenges journaliers ont été r§initialis§ !");
    }
}
