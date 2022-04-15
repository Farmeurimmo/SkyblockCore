package main.java.fr.verymc.island.challenges;

import main.java.fr.verymc.Main;
import main.java.fr.verymc.island.Island;
import main.java.fr.verymc.island.IslandManager;
import org.bukkit.Bukkit;

import java.util.Calendar;
import java.util.TimeZone;

public class IslandChallengesReset {

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

    public static void ResetAllChallenges() {
        for (Island island : IslandManager.instance.islands) {
            island.getChallenges().clear();
            island.addDefaultChallenges();
        }
        Bukkit.broadcastMessage("§6§lChallenges §8» §fTous les challenges journaliers ont été réinitialisé !");
    }
}
