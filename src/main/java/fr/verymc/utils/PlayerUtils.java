package main.java.fr.verymc.utils;

import main.java.fr.verymc.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerUtils {

    public static void TeleportPlayerFromRequest(Player player, Location loc, int temp) {

        final int timeLeft = Main.instance.getCooldown(player.getName());
        if (GetTeleportDelay.GetPlayerTeleportingdelay(player) == 0) {
            player.teleport(loc);
            player.sendActionBar("§6Téléportation effectuée !");
            return;
        } else {
            if (timeLeft == 0) {
                if (temp == 1) {
                    player.sendActionBar("§6Téléportation dans 1 seconde...");
                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
                        public void run() {
                            player.teleport(loc);
                            player.sendActionBar("§6Téléportation effectuée !");
                        }
                    }, 20);
                    return;
                } else {
                    player.sendActionBar("§6Téléportation dans " + temp + " secondes...");
                }
                Main.instance.setCooldown(player.getName(), temp - 1);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        int timeLeft = Main.instance.getCooldown(player.getName());
                        if (timeLeft == 0) {
                            Main.instance.setCooldown(player.getName(), 0);
                            player.teleport(loc);
                            player.sendActionBar("§6Téléportation effectuée !");
                            this.cancel();
                            return;
                        }
                        Main.instance.setCooldown(player.getName(), timeLeft - 1);
                        if (timeLeft != 0 && timeLeft != 1) {
                            player.sendActionBar("§6Téléportation dans " + timeLeft + " secondes...");
                        } else if (timeLeft == 1) {
                            player.sendActionBar("§6Téléportation dans " + timeLeft + " seconde...");
                        } else if (timeLeft <= 0) {
                            return;
                        }
                    }
                }.runTaskTimer(Main.instance, 20, 20);
            }

        }

    }

    public static void TeleportPlayerFromRequestToAnotherPlayer(Player player, Player p, int temp) {

        Location totp = p.getLocation();

        player.sendMessage("§6§lTéléportation §8» §a" + p.getName() + " §fvient d'accepter votre demande de Téléportation.");
        final int timeLeft = Main.instance.getCooldown(player.getName());
        if (GetTeleportDelay.GetPlayerTeleportingdelay(player) == 0) {
            player.teleport(totp);
            player.sendActionBar("fTéléportation sur §a" + p.getName() + "§f effectuée !");
            return;
        } else {
            if (timeLeft == 0) {
                if (temp == 1) {
                    player.sendActionBar("§fTéléportation sur §a" + p.getName() + "§f dans §c1 §fseconde...");
                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
                        public void run() {
                            player.teleport(totp);
                            player.sendActionBar("§fTéléportation sur §a" + p.getName() + "§f effectuée !");
                        }
                    }, 20);
                    return;
                } else {
                    player.sendActionBar("§fTéléportation sur §a" + p.getName() + "§f dans §c" + temp + " §fsecondes...");
                }
                Main.instance.setCooldown(player.getName(), temp - 1);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        int timeLeft = Main.instance.getCooldown(player.getName());
                        if (timeLeft == 0) {
                            Main.instance.setCooldown(player.getName(), 0);
                            player.teleport(totp);
                            player.sendActionBar("§fTéléportation sur §a" + p.getName() + "§f effectuée !");
                            this.cancel();
                            return;
                        }
                        Main.instance.setCooldown(player.getName(), timeLeft - 1);
                        if (timeLeft != 0 && timeLeft != 1) {
                            player.sendActionBar("§fTéléportation sur §a" + p.getName() + "§f dans §c" + timeLeft + " §fsecondes...");
                        } else if (timeLeft == 1) {
                            player.sendActionBar("§fTéléportation sur §a" + p.getName() + "§f dans §c" + timeLeft + " §fseconde...");
                        }
                    }
                }.runTaskTimer(Main.instance, 20, 20);
            }

        }

    }
}
