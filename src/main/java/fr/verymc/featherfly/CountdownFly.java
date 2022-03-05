package main.java.fr.verymc.featherfly;

import main.java.fr.verymc.core.Main;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CountdownFly implements Listener {

    public static HashMap<UUID, Integer> fly = new HashMap<>();

    public static CountdownFly instance;

    public CountdownFly() {
        instance = this;
        ReadForTempFly();
        CountDown();
    }

    public void setCooldown(UUID uuid, Integer time) {
        if (time == null)
            fly.remove(uuid);
        else
            fly.put(uuid, time);
    }

    public int getCooldown(Player player) {
        return (fly.get(player.getUniqueId()) == null ? 0 : fly.get(player.getUniqueId()));
    }

    public void WriteFlyLeft() {
        for (Map.Entry<UUID, Integer> aaa : CountdownFly.fly.entrySet()) {
            if (aaa.getValue() >= 1) {
                Main.instance.getDatac().set("Joueurs." + aaa.getKey() + ".Fly.timeleft", aaa.getValue());
                Main.instance.saveData();
            }
        }
    }

    public void ReadForTempFly() {
        if (Main.instance.getDatac().getConfigurationSection("Joueurs") == null) {
            Main.instance.getDatac().set("Joueurs.ini", true);
            Main.instance.saveData();
        }
        for (String aa : Main.instance.getDatac().getConfigurationSection("Joueurs").getKeys(false)) {
            if (Main.instance.getDatac().getBoolean(aa) == true) {
                continue;
            }
            int a = 0;
            a = Main.instance.getDatac().getInt("Joueurs." + aa + ".Fly.timeleft");
            if (a >= 1) {
                CountdownFly.instance.setCooldown(UUID.fromString(aa), a);
            }
        }
    }


    public void EnableFlyForPlayer(Player player, String dure, String sb) {
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
            setCooldown(player.getUniqueId(), newtime);
        } else {
            setCooldown(player.getUniqueId(), DurationInSec);
            if (!player.getWorld().getName().equalsIgnoreCase("world")) {
                player.setFlying(true);
            }
        }
    }

    public void CountDown() {
        for (Map.Entry<UUID, Integer> flymap : fly.entrySet()) {
            Player player = Bukkit.getPlayer(flymap.getKey());
            if (player != null) {
                if (player.isOnline()) {
                    int TimeLeft = getCooldown(player);

                    if (getCooldown(player) - 1 != -1 && getCooldown(player) >= 0) {

                        if (!player.getWorld().getName().equalsIgnoreCase("world")) {
                            TimeLeft = TimeLeft - 1;
                        }

                        if (player.getAllowFlight() == false && !player.getWorld().getName().equalsIgnoreCase("world")) {
                            player.setAllowFlight(true);
                        }
                        String aaaaaa = "" + TimeLeft;
                        if (aaaaaa.contains("0")) {
                            Main.instance.getDatac().set("Joueurs." + player.getUniqueId() + ".Fly.timeleft", TimeLeft);
                            Main.instance.saveData();
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
                        CountdownFly.instance.setCooldown(player.getUniqueId(), TimeLeft);
                        player.sendActionBar(messagetimeleft);

                    } else {
                        player.setAllowFlight(false);
                        player.setFlying(false);
                        player.chat("/is home");
                        player.sendMessage("§6Fin du fly, Téléportation sur votre île..");
                        player.sendActionBar("§6Fin du fly.");
                        fly.remove(player.getUniqueId());
                        Main.instance.getDatac().set("Joueurs." + player.getUniqueId() + ".Fly.timeleft", 0);
                        Main.instance.saveData();
                    }
                }
            }
        }
        Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(Main.instance, new Runnable() {
            public void run() {
                CountDown();
            }
        }, 20);
    }
}
