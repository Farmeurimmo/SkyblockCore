package fr.farmeurimmo.verymc.utils;

import fr.farmeurimmo.verymc.core.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TeleportPlayer {

    public static void TeleportPlayerFromRequest(Player player, Location loc, int temp) {

        final int timeLeft = Main.instance1.getCooldown(player.getName());
        if (GetTeleportDelay.GetPlayerTeleportingdelay(player) == 0) {
            player.teleport(loc);
            SendActionBar.SendActionBarMsg(player, "§6Téléportation effectu§e !");
            return;
        } else {
            if (timeLeft == 0) {
                if (temp == 1) {
                    SendActionBar.SendActionBarMsg(player, "§6Téléportation dans 1 seconde...");
                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
                        public void run() {
                            player.teleport(loc);
                            SendActionBar.SendActionBarMsg(player, "§6Téléportation effectu§e !");
                        }
                    }, 20);
                    return;
                } else {
                    SendActionBar.SendActionBarMsg(player, "§6Téléportation dans " + temp + " secondes...");
                }
                Main.instance1.setCooldown(player.getName(), temp - 1);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        int timeLeft = Main.instance1.getCooldown(player.getName());
                        if (timeLeft == 0) {
                            Main.instance1.setCooldown(player.getName(), 0);
                            player.teleport(loc);
                            SendActionBar.SendActionBarMsg(player, "§6Téléportation effectu§e !");
                            this.cancel();
                            return;
                        }
                        Main.instance1.setCooldown(player.getName(), timeLeft - 1);
                        if (timeLeft != 0 && timeLeft != 1) {
                            SendActionBar.SendActionBarMsg(player, "§6Téléportation dans " + timeLeft + " secondes...");
                        } else if (timeLeft == 1) {
                            SendActionBar.SendActionBarMsg(player, "§6Téléportation dans " + timeLeft + " seconde...");
                        } else if (timeLeft <= 0) {
                            return;
                        }
                    }
                }.runTaskTimer(Bukkit.getPluginManager().getPlugin("SkyblockCore"), 20, 20);
            }

        }

    }

    public static void TeleportPlayerFromRequestToAnotherPlayer(Player player, Player p, int temp) {

        Location totp = p.getLocation();

        player.sendMessage("§6§lTéléportation §8» §a" + p.getName() + " §fvient d'accepter votre demande de Téléportation.");
        final int timeLeft = Main.instance1.getCooldown(player.getName());
        if (GetTeleportDelay.GetPlayerTeleportingdelay(player) == 0) {
            player.teleport(totp);
            SendActionBar.SendActionBarMsg(player, "fTéléportation sur §a" + p.getName() + "§f effectu§e !");
            return;
        } else {
            if (timeLeft == 0) {
                if (temp == 1) {
                    SendActionBar.SendActionBarMsg(player, "§fTéléportation sur §a" + p.getName() + "§f dans §c1 §fseconde...");
                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
                        public void run() {
                            player.teleport(totp);
                            SendActionBar.SendActionBarMsg(player, "§fTéléportation sur §a" + p.getName() + "§f effectu§e !");
                        }
                    }, 20);
                    return;
                } else {
                    SendActionBar.SendActionBarMsg(player, "§fTéléportation sur §a" + p.getName() + "§f dans §c" + temp + " §fsecondes...");
                }
                Main.instance1.setCooldown(player.getName(), temp - 1);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        int timeLeft = Main.instance1.getCooldown(player.getName());
                        if (timeLeft == 0) {
                            Main.instance1.setCooldown(player.getName(), 0);
                            player.teleport(totp);
                            SendActionBar.SendActionBarMsg(player, "§fTéléportation sur §a" + p.getName() + "§f effectu§e !");
                            this.cancel();
                            return;
                        }
                        Main.instance1.setCooldown(player.getName(), timeLeft - 1);
                        if (timeLeft != 0 && timeLeft != 1) {
                            SendActionBar.SendActionBarMsg(player, "§fTéléportation sur §a" + p.getName() + "§f dans §c" + timeLeft + " §fsecondes...");
                        } else if (timeLeft == 1) {
                            SendActionBar.SendActionBarMsg(player, "§fTéléportation sur §a" + p.getName() + "§f dans §c" + timeLeft + " §fseconde...");
                        }
                    }
                }.runTaskTimer(Bukkit.getPluginManager().getPlugin("SkyblockCore"), 20, 20);
            }

        }

    }
}
