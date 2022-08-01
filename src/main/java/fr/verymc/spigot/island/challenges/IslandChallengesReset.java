package main.java.fr.verymc.spigot.island.challenges;

import main.java.fr.verymc.commons.enums.ServerType;
import main.java.fr.verymc.spigot.Main;
import main.java.fr.verymc.spigot.island.Island;
import main.java.fr.verymc.spigot.island.IslandManager;
import org.bukkit.Bukkit;

import java.util.Calendar;
import java.util.TimeZone;

public class IslandChallengesReset {

    public static final int maxPalier = 2; // 2 = 3 vu qu'on part de 0
    public static IslandChallengesReset instance;

    public IslandChallengesReset() {
        instance = this;
        checkForReset();
    }

    public void checkForReset() {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Paris"));
        Calendar calendar = Calendar.getInstance();
        if (calendar.getTime().getHours() == 19 && calendar.getTime().getMinutes() == 0 &&
                calendar.getTime().getSeconds() == 0) {
            resetAllChallenges();
        }
        Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(Main.instance, new Runnable() {
            public void run() {
                checkForReset();
            }
        }, 20);
    }

    public long getTimeBeforeReset() {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Paris"));
        Calendar calendar = Calendar.getInstance();
        if (calendar.getTime().getHours() < 19) {
            calendar.set(Calendar.HOUR_OF_DAY, 19);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
        } else {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.HOUR_OF_DAY, 19);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
        }
        long time = calendar.getTimeInMillis();
        final long total = time - System.currentTimeMillis();
        calendar.clear();
        return total;
    }

    public void resetAllChallenges() {
        if (Main.instance.serverType == ServerType.SKYBLOCK_ISLAND) {
            for (Island island : IslandManager.instance.islands) {
                island.getChallenges().clear();
                island.addDefaultChallenges();
            }
        }
        Bukkit.broadcastMessage("§6§lChallenges §8» §fTous les challenges journaliers ont été réinitialisé !");
    }
}
