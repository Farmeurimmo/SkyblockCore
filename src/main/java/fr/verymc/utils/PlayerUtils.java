package main.java.fr.verymc.utils;

import main.java.fr.verymc.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerUtils {

    public static PlayerUtils instance;

    public PlayerUtils() {
        instance = this;
    }

    public void teleportPlayerFromRequest(Player player, Location loc, int temp) {

        final int timeLeft = Main.instance.getCooldown(player.getName());
        if (getPlayerTeleportingdelay(player) == 0) {
            player.teleport(loc);
            player.sendActionBar("§6Téléportation effectuée !");
            return;
        } else {
            if (timeLeft == 0) {
                if (temp == 0) {
                    player.teleport(loc);
                    player.sendActionBar("§6Téléportation effectuée !");
                    return;
                }
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
                        if (timeLeft <= 0) {
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
                            this.cancel();
                            return;
                        }
                    }
                }.runTaskTimer(Main.instance, 20, 20);
            }

        }

    }

    public void teleportPlayerFromRequestToAnotherPlayer(Player player, Player p, int temp) {

        Location totp = p.getLocation();

        player.sendMessage("§6§lTéléportation §8» §a" + p.getName() + " §fvient d'accepter votre demande de Téléportation.");
        final int timeLeft = Main.instance.getCooldown(player.getName());
        if (getPlayerTeleportingdelay(player) == 0) {
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
                        if (timeLeft <= 0) {
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
                        } else if (timeLeft <= 0) {
                            this.cancel();
                            return;
                        }
                    }
                }.runTaskTimer(Main.instance, 20, 20);
            }

        }

    }

    private int getExpAtLevel(final int level) {
        if (level <= 15) {
            return (2 * level) + 7;
        } else if (level <= 30) {
            return (5 * level) - 38;
        }
        return (9 * level) - 158;
    }

    public int getTotalExperience(final Player player) {
        int exp = Math.round(getExpAtLevel(player.getLevel()) * player.getExp());
        int currentLevel = player.getLevel();

        while (currentLevel > 0) {
            currentLevel--;
            exp += getExpAtLevel(currentLevel);
        }

        if (exp < 0) {
            exp = Integer.MAX_VALUE;
        }

        return exp;
    }

    public void setTotalExperience(final Player player, final int exp) {
        if (exp < 0) {
            return;
        }

        player.setExp(0);
        player.setLevel(0);
        player.setTotalExperience(0);

        int amount = exp;
        while (amount > 0) {
            final int expToLevel = getExpAtLevel(player.getLevel());
            amount -= expToLevel;
            if (amount >= 0) {
                // give until next level
                player.giveExp(expToLevel);
            } else {
                // give the rest
                amount += expToLevel;
                player.giveExp(amount);
                amount = 0;
            }
        }
    }

    public int getPlayerTeleportingdelay(Player player) {
        int time = 0;
        if (player.hasPermission("*")) {
            return time;
        } else if (player.hasPermission("group.zeus")) {
            return time;
        } else if (player.hasPermission("group.dieu")) {
            return time + 1;
        } else if (player.hasPermission("group.legende")) {
            return time + 3;
        } else {
            return time + 5;
        }
    }
}
