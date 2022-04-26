package main.java.fr.verymc.evenement;

import main.java.fr.verymc.Main;
import main.java.fr.verymc.island.challenges.IslandChallengesGuis;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.TimeZone;

public class DailyBonus {

    public static DailyBonus instance;
    public ArrayList<Integer> hours = new ArrayList<>(Arrays.asList(16, 19, 22));
    public boolean active = false;
    long duration = 20 * 60 * 10;
    long lastAct = 0;

    public DailyBonus() {
        instance = this;
        checkForIt();
    }

    public void checkForIt() {
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(Main.instance, new Runnable() {
            @Override
            public void run() {
                TimeZone.setDefault(TimeZone.getTimeZone("Europe/Paris"));
                Calendar calendar = Calendar.getInstance();
                if (hours.contains(calendar.getTime().getHours()) && calendar.getTime().getMinutes() == 0
                        && calendar.getTime().getSeconds() == 0) {
                    startBonus();
                }
            }
        }, 0L, 20L);
    }

    public void startBonus() {
        lastAct = System.currentTimeMillis();
        active = true;
        IslandChallengesGuis.boost = 2;
        Bukkit.broadcastMessage("§6§lBonus §8» §fUn bonus x2 a été activé pour les challenges. Il expirera dans §e" + duration / 20 + "§f secondes.");
        Bukkit.getScheduler().scheduleAsyncDelayedTask(Main.instance, new Runnable() {
            @Override
            public void run() {
                IslandChallengesGuis.boost = 1;
                active = false;
                Bukkit.broadcastMessage("§6§lBonus §8» §fLe bonus a été désactivé.");
            }
        }, duration);
    }
}
