package main.java.fr.verymc.featherfly;

import main.java.fr.verymc.core.Main;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.HashMap;

public class CountdownFly implements Listener {

    public static HashMap<String, Integer> fly = new HashMap<>();

    public static void setCooldown(String uuid, Integer time) {
        if (time == null)
            fly.remove(uuid);
        else
            fly.put(uuid, time);
    }

    public static int getCooldown(Player player) {
        return (fly.get(player.getName()) == null ? 0 : fly.get(player.getName()));
    }

    public static void EnableFlyForPlayer(Player player, String dure, String sb) {
        int DurationInSec = 0;
        int DurationGlobal = Integer.parseInt(sb);
        if (dure.equalsIgnoreCase("heures")) {
            DurationInSec = DurationGlobal * 60 * 60;
        }
        if (dure.equalsIgnoreCase("minutes")) {
            DurationInSec = DurationGlobal * 60;
        }
        if (dure.equalsIgnoreCase("secondes")) {
            DurationInSec = DurationGlobal;
        }
        if (getCooldown(player) >= 1) {
            int newtime = DurationInSec + getCooldown(player);
            setCooldown(player.getName(), newtime);
        } else {
            setCooldown(player.getName(), DurationInSec);
            player.setAllowFlight(true);
            if (!player.getWorld().getName().equalsIgnoreCase("world")) {
                player.setFlying(true);
            }
            CountDown(player.getName());
        }
    }

    public static void CountDown(String player) {
        if (!fly.containsKey(player)) {
            return;
        }
        if (Bukkit.getPlayer(player) != null) {
            if (Bukkit.getPlayer(player).isOnline()) {
                Player p = Bukkit.getPlayer(player);
                int TimeLeft = getCooldown(p);

                if (getCooldown(p) - 1 != -1 && getCooldown(p) >= 0) {

                    if (!p.getWorld().getName().equalsIgnoreCase("world")) {
                        TimeLeft = TimeLeft - 1;
                    }

                    if (p.getAllowFlight() == false && !p.getWorld().getName().equalsIgnoreCase("world")) {
                        p.setAllowFlight(true);
                    }
                    String aaaaaa = "" + TimeLeft;
                    if (aaaaaa.contains("5") || aaaaaa.contains("0") || aaaaaa.contains("0") && aaaaaa.contains("5")) {
                        Main.instance1.getDatac().set("Joueurs." + p.getName() + ".Fly.timeleft", TimeLeft);
                        Main.instance1.saveData();
                    }

                    int timeforconv = TimeLeft;
                    int nHours = (timeforconv % 86400) / 3600;
                    int nMin = ((timeforconv % 86400) % 3600) / 60;
                    int nSec = (((timeforconv % 86400) % 3600) % 60);

                    String nhoursnew;
                    String nminnew;
                    String nsecnew;
                    if (nHours <= 9) {
                        nhoursnew = "0" + nHours;
                    } else {
                        nhoursnew = "" + nHours;
                    }
                    if (nMin <= 9) {
                        nminnew = "0" + nMin;
                    } else {
                        nminnew = "" + nMin;
                    }
                    if (nSec <= 9) {
                        nsecnew = "0" + nSec;
                    } else {
                        nsecnew = "" + nSec;
                    }

                    String messagetimeleft = "§aTemps restant: " + nhoursnew + ":" + nminnew + ":" + nsecnew;
                    CountdownFly.setCooldown(p.getName(), TimeLeft);
                    p.sendActionBar(messagetimeleft);

                } else {
                    if (p.getGameMode() != GameMode.CREATIVE && p.getGameMode() != GameMode.SPECTATOR) {
                        p.setAllowFlight(false);
                        p.setFlying(false);
                        p.chat("/is home");
                        p.sendMessage("§6Fin du fly, Téléportation sur votre île..");
                    }
                    p.sendActionBar("§6Fin du fly.");
                    fly.remove(p.getName());
                    Main.instance1.getDatac().set("Joueurs." + p.getName() + ".Fly.timeleft", 0);
                    Main.instance1.saveData();
                    return;
                }
            }
        }
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
            public void run() {
                CountDown(player);
            }
        }, 20);
    }
}
