package main.java.fr.verymc.challenges;

import main.java.fr.verymc.Main;
import org.bukkit.Bukkit;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.UUID;

public class ChallengesReset {

    public static void CheckForReset() {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Paris"));
        Calendar calendar = Calendar.getInstance();
        if (calendar.getTime().getHours() == 0 && calendar.getTime().getMinutes() == 0 &&
                calendar.getTime().getSeconds() == 0) {
            ResetAllChallenges();
        }
        Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(Main.instance, new Runnable() {
            public void run() {
                CheckForReset();
            }
        }, 20);
    }

    public static void CreateChallengesForPlayer(UUID aa) {
        if (Main.instance.getData().getString("Joueurs." + aa + ".Challenges.Daily.1.Active") == null) {
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.1.Active", true);
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.1.Progression", 0);
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.1.Palier", 1);
            Main.instance.saveDataChallenges();
        }
        if (Main.instance.getData().getString("Joueurs." + aa + ".Challenges.Daily.2.Active") == null) {
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.2.Active", true);
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.2.Progression", 0);
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.2.Palier", 1);
            Main.instance.saveDataChallenges();
        }
        if (Main.instance.getData().getString("Joueurs." + aa + ".Challenges.Daily.3.Active") == null) {
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.3.Active", true);
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.3.Progression", 0);
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.3.Palier", 1);
            Main.instance.saveDataChallenges();
        }
        if (Main.instance.getData().getString("Joueurs." + aa + ".Challenges.Daily.4.Active") == null) {
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.4.Active", true);
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.4.Progression", 0);
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.4.Palier", 1);
            Main.instance.saveDataChallenges();
        }
        if (Main.instance.getData().getString("Joueurs." + aa + ".Challenges.Daily.5.Active") == null) {
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.5.Active", true);
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.5.Progression", 0);
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.5.Palier", 1);
            Main.instance.saveDataChallenges();
        }
        if (Main.instance.getData().getString("Joueurs." + aa + ".Challenges.Daily.6.Active") == null) {
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.6.Active", true);
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.6.Progression", 0);
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.6.Palier", 1);
            Main.instance.saveDataChallenges();
        }
        if (Main.instance.getData().getString("Joueurs." + aa + ".Challenges.Daily.7.Active") == null) {
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.7.Active", true);
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.7.Progression", 0);
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.7.Palier", 1);
            Main.instance.saveDataChallenges();
        }
        if (Main.instance.getData().getString("Joueurs." + aa + ".Challenges.Daily.8.Active") == null) {
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.8.Active", true);
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.8.Progression", 0);
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.8.Palier", 1);
            Main.instance.saveDataChallenges();
        }
        if (Main.instance.getData().getString("Joueurs." + aa + ".Challenges.Daily.9.Active") == null) {
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.9.Active", true);
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.9.Progression", 0);
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.9.Palier", 1);
            Main.instance.saveDataChallenges();
        }
        if (Main.instance.getData().getString("Joueurs." + aa + ".Challenges.Daily.10.Active") == null) {
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.10.Active", true);
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.10.Progression", 0);
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.10.Palier", 1);
            Main.instance.saveDataChallenges();
        }
        if (Main.instance.getData().getString("Joueurs." + aa + ".Challenges.Daily.11.Active") == null) {
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.11.Active", true);
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.11.Progression", 0);
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.11.Palier", 1);
            Main.instance.saveDataChallenges();
        }
        if (Main.instance.getData().getString("Joueurs." + aa + ".Challenges.Daily.12.Active") == null) {
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.12.Active", true);
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.12.Progression", 0);
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.12.Palier", 1);
            Main.instance.saveDataChallenges();
        }
        if (Main.instance.getData().getString("Joueurs." + aa + ".Challenges.Daily.13.Active") == null) {
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.13.Active", true);
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.13.Progression", 0);
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.13.Palier", 1);
            Main.instance.saveDataChallenges();
        }
    }

    public static void ResetAllChallenges() {
        for (String aa : Main.instance.getData().getConfigurationSection("Joueurs").getKeys(false)) {
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.1.Active", true);
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.1.Progression", 0);
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.1.Palier", 1);

            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.2.Active", true);
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.2.Progression", 0);
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.2.Palier", 1);

            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.3.Active", true);
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.3.Progression", 0);
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.3.Palier", 1);

            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.4.Active", true);
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.4.Progression", 0);
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.4.Palier", 1);

            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.5.Active", true);
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.5.Progression", 0);
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.5.Palier", 1);

            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.6.Active", true);
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.6.Progression", 0);
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.6.Palier", 1);

            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.7.Active", true);
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.7.Progression", 0);
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.7.Palier", 1);

            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.8.Active", true);
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.8.Progression", 0);
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.8.Palier", 1);

            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.9.Active", true);
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.9.Progression", 0);
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.9.Palier", 1);

            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.10.Active", true);
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.10.Progression", 0);
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.10.Palier", 1);

            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.11.Active", true);
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.11.Progression", 0);
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.11.Palier", 1);

            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.12.Active", true);
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.12.Progression", 0);
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.12.Palier", 1);

            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.13.Active", true);
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.13.Progression", 0);
            Main.instance.getData().set("Joueurs." + aa + ".Challenges.Daily.13.Palier", 1);

        }
        Main.instance.saveDataChallenges();
        Bukkit.broadcastMessage("§6§lChallenges §8» §fTous les challenges journaliers ont été réinitialisé !");
    }
}
