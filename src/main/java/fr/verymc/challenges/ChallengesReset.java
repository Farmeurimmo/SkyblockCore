package main.java.fr.verymc.challenges;

import main.java.fr.verymc.Main;
import main.java.fr.verymc.config.AsyncSaver;
import main.java.fr.verymc.config.ConfigManager;
import org.bukkit.Bukkit;

import java.util.Calendar;
import java.util.HashMap;
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
        HashMap<String, Object> objectHashMap = new HashMap<>();
        if (ConfigManager.instance.getDataAtoutsChallenges().getString("Joueurs." + aa + ".Challenges.Daily.1.Active") == null) {
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.1.Active", true);
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.1.Progression", 0);
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.1.Palier", 1);
        }
        if (ConfigManager.instance.getDataAtoutsChallenges().getString("Joueurs." + aa + ".Challenges.Daily.2.Active") == null) {
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.2.Active", true);
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.2.Progression", 0);
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.2.Palier", 1);
        }
        if (ConfigManager.instance.getDataAtoutsChallenges().getString("Joueurs." + aa + ".Challenges.Daily.3.Active") == null) {
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.3.Active", true);
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.3.Progression", 0);
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.3.Palier", 1);
        }
        if (ConfigManager.instance.getDataAtoutsChallenges().getString("Joueurs." + aa + ".Challenges.Daily.4.Active") == null) {
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.4.Active", true);
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.4.Progression", 0);
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.4.Palier", 1);
        }
        if (ConfigManager.instance.getDataAtoutsChallenges().getString("Joueurs." + aa + ".Challenges.Daily.5.Active") == null) {
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.5.Active", true);
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.5.Progression", 0);
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.5.Palier", 1);
        }
        if (ConfigManager.instance.getDataAtoutsChallenges().getString("Joueurs." + aa + ".Challenges.Daily.6.Active") == null) {
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.6.Active", true);
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.6.Progression", 0);
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.6.Palier", 1);
        }
        if (ConfigManager.instance.getDataAtoutsChallenges().getString("Joueurs." + aa + ".Challenges.Daily.7.Active") == null) {
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.7.Active", true);
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.7.Progression", 0);
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.7.Palier", 1);
        }
        if (ConfigManager.instance.getDataAtoutsChallenges().getString("Joueurs." + aa + ".Challenges.Daily.8.Active") == null) {
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.8.Active", true);
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.8.Progression", 0);
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.8.Palier", 1);
        }
        if (ConfigManager.instance.getDataAtoutsChallenges().getString("Joueurs." + aa + ".Challenges.Daily.9.Active") == null) {
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.9.Active", true);
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.9.Progression", 0);
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.9.Palier", 1);
        }
        if (ConfigManager.instance.getDataAtoutsChallenges().getString("Joueurs." + aa + ".Challenges.Daily.10.Active") == null) {
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.10.Active", true);
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.10.Progression", 0);
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.10.Palier", 1);
        }
        if (ConfigManager.instance.getDataAtoutsChallenges().getString("Joueurs." + aa + ".Challenges.Daily.11.Active") == null) {
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.11.Active", true);
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.11.Progression", 0);
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.11.Palier", 1);
        }
        if (ConfigManager.instance.getDataAtoutsChallenges().getString("Joueurs." + aa + ".Challenges.Daily.12.Active") == null) {
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.12.Active", true);
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.12.Progression", 0);
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.12.Palier", 1);
        }
        if (ConfigManager.instance.getDataAtoutsChallenges().getString("Joueurs." + aa + ".Challenges.Daily.13.Active") == null) {
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.13.Active", true);
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.13.Progression", 0);
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.13.Palier", 1);
        }
        if (objectHashMap.size() > 0)
            AsyncSaver.instance.setAndSaveAsync(objectHashMap, ConfigManager.instance.getDataAtoutsChallenges(),
                    ConfigManager.instance.fileChallengesAtouts);
    }

    public static void ResetAllChallenges() {
        for (String aa : ConfigManager.instance.getDataAtoutsChallenges().getConfigurationSection("Joueurs").getKeys(false)) {
            HashMap<String, Object> objectHashMap = new HashMap<>();
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.1.Active", true);
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.1.Progression", 0);
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.1.Palier", 1);

            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.2.Active", true);
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.2.Progression", 0);
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.2.Palier", 1);

            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.3.Active", true);
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.3.Progression", 0);
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.3.Palier", 1);

            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.4.Active", true);
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.4.Progression", 0);
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.4.Palier", 1);

            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.5.Active", true);
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.5.Progression", 0);
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.5.Palier", 1);

            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.6.Active", true);
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.6.Progression", 0);
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.6.Palier", 1);

            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.7.Active", true);
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.7.Progression", 0);
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.7.Palier", 1);

            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.8.Active", true);
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.8.Progression", 0);
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.8.Palier", 1);

            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.9.Active", true);
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.9.Progression", 0);
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.9.Palier", 1);

            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.10.Active", true);
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.10.Progression", 0);
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.10.Palier", 1);

            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.11.Active", true);
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.11.Progression", 0);
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.11.Palier", 1);

            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.12.Active", true);
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.12.Progression", 0);
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.12.Palier", 1);

            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.13.Active", true);
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.13.Progression", 0);
            objectHashMap.put("Joueurs." + aa + ".Challenges.Daily.13.Palier", 1);

            AsyncSaver.instance.setAndSaveAsync(objectHashMap, ConfigManager.instance.getDataAtoutsChallenges(),
                    ConfigManager.instance.fileChallengesAtouts);
        }
        Bukkit.broadcastMessage("§6§lChallenges §8» §fTous les challenges journaliers ont été réinitialisé !");
    }
}
