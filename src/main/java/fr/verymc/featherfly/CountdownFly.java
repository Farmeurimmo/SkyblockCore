package main.java.fr.verymc.featherfly;

import main.java.fr.verymc.Main;
import main.java.fr.verymc.cmd.base.SpawnCmd;
import main.java.fr.verymc.storage.SkyblockUser;
import main.java.fr.verymc.storage.SkyblockUserManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class CountdownFly implements Listener {

    public static CountdownFly instance;

    public CountdownFly() {
        instance = this;
        CountDown();
    }

    public void EnableFlyForPlayer(Player player, String dure, String sb) {
        SkyblockUserManager.instance.checkForAccount(player);
        SkyblockUser skyblockUser = SkyblockUserManager.instance.getUser(player.getUniqueId());
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
        if (skyblockUser.getFlyLeft() >= 1) {
            int newtime = DurationInSec + skyblockUser.getFlyLeft();
            skyblockUser.setFlyLeft(newtime);
        } else {
            skyblockUser.setFlyLeft(DurationInSec);
            if (!player.getWorld().getName().equalsIgnoreCase("world")) {
                player.setAllowFlight(true);
                player.setFlying(true);
            }
        }
    }

    public void CountDown() {
        for (SkyblockUser skyblockUser : SkyblockUserManager.instance.users) {
            Player player = Bukkit.getPlayer(skyblockUser.getUserUUID());
            if (player != null) {
                if (player.isOnline()) {
                    int timeLeft = skyblockUser.getFlyLeft();

                    if (timeLeft > 0) {

                        if (!player.getWorld().getName().equalsIgnoreCase("world")) {
                            timeLeft -= 1;

                            if (player.getAllowFlight() == false && !player.getWorld().getName().equalsIgnoreCase("world")) {
                                player.setAllowFlight(true);
                            }

                            int timeforconv = timeLeft;
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

                            String messagetimeleft = "§aFly restant: " + nhoursnew + ":" + nminnew + ":" + nsecnew;
                            skyblockUser.setFlyLeft(timeLeft);
                            skyblockUser.setActive(true);
                            player.sendActionBar(messagetimeleft);
                        } else {
                            int timeforconv = timeLeft;
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

                            String messagetimeleft = "§aFly restant: " + nhoursnew + ":" + nminnew + ":" + nsecnew;
                            skyblockUser.setFlyLeft(timeLeft);
                            player.sendActionBar(messagetimeleft);
                            skyblockUser.setActive(false);
                            continue;
                        }
                    }

                    if (skyblockUser.isActive() && skyblockUser.getFlyLeft() <= 0) {
                        player.teleport(SpawnCmd.Spawn);
                        skyblockUser.setActive(false);
                        player.setAllowFlight(false);
                        player.setFlying(false);
                        player.sendActionBar("§6Fin du fly.");
                        skyblockUser.setFlyLeft(0);
                    }
                }
            }
        }
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
            public void run() {
                CountDown();
            }
        }, 20);
    }
}
