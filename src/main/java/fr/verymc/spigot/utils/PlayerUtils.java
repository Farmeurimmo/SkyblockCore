package main.java.fr.verymc.spigot.utils;

import main.java.fr.verymc.commons.enums.ServerType;
import main.java.fr.verymc.spigot.Main;
import main.java.fr.verymc.spigot.core.ServersManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerUtils {

    public static PlayerUtils instance;

    public PlayerUtils() {
        instance = this;
    }

    public Location toCenterOf(Location centerOfIsland, Location realLocation) {
        return new Location(centerOfIsland.getWorld(), centerOfIsland.getX() + realLocation.getX(), realLocation.getY(),
                centerOfIsland.getZ() + realLocation.getZ());
    }

    public Location addCenterTo(Location centerOfIsland, Location realLocation) {
        return new Location(centerOfIsland.getWorld(), realLocation.getX() - centerOfIsland.getX(), realLocation.getY(),
                realLocation.getZ() - centerOfIsland.getZ());
    }

    public void teleportPlayer(Player player, Location location) {
        boolean hasFly = player.getAllowFlight();
        boolean wasActive = player.isFlying();
        new BukkitRunnable() {
            @Override
            public void run() {
                player.teleportAsync(location);
                for (int i = -2; i > -5; i--) {
                    if (location.clone().add(0, i, 0).getBlock().getType() != Material.AIR) {
                        if (player.isFlying() && !wasActive) player.setFlying(false);
                        if (player.getAllowFlight() && !hasFly) player.setAllowFlight(false);
                        this.cancel();
                    }
                }
                player.setAllowFlight(true);
                player.setFlying(true);
            }
        }.runTaskTimer(Main.instance, 0, 10L);
    }

    public void teleportPlayerFromRequest(Player player, Location loc, int temp, ServerType serverType) {

        final int timeLeft = Main.instance.getCooldown(player.getName());
        if (getPlayerTeleportingdelay(player) == 0) {
            player.sendActionBar("§6Téléportation effectuée !");
            if (Main.instance.serverType != serverType) {
                ServersManager.instance.sendToServer(ServersManager.instance.getServerOfType(serverType), player, loc);
                return;
            }
            teleportPlayer(player, loc);
            return;
        } else {
            if (timeLeft == 0) {
                if (temp == 0) {
                    player.sendActionBar("§6Téléportation effectuée !");
                    if (Main.instance.serverType != ServerType.SKYBLOCK_HUB) {
                        ServersManager.instance.sendToServer(ServersManager.instance.getServerOfType(serverType), player, loc);
                        return;
                    }
                    teleportPlayer(player, loc);
                    return;
                }
                if (temp == 1) {
                    player.sendActionBar("§6Téléportation dans 1 seconde...");
                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, () -> {
                        player.sendActionBar("§6Téléportation effectuée !");
                        if (Main.instance.serverType != ServerType.SKYBLOCK_HUB) {
                            ServersManager.instance.sendToServer(ServersManager.instance.getServerOfType(serverType), player, loc);
                            return;
                        }
                        teleportPlayer(player, loc);
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
                            player.sendActionBar("§6Téléportation effectuée !");
                            if (Main.instance.serverType != ServerType.SKYBLOCK_HUB) {
                                ServersManager.instance.sendToServer(ServersManager.instance.getServerOfType(serverType), player, loc);
                                return;
                            }
                            teleportPlayer(player, loc);
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

    public int levelsToXp(int levels) {
        if (levels <= 16) {
            return (int) (Math.pow(levels, 2) + (6 * levels));
        } else if (levels >= 17 && levels <= 31) {
            return (int) ((2.5 * Math.pow(levels, 2)) + (-40.5 * levels) + 360);
        } else if (levels >= 32) {
            return (int) ((4.5 * Math.pow(levels, 2)) + (-162.5 * levels) + 2220);
        }
        return -1;
    }

    public int XpToLevels(int xp) {
        if (xp <= 352) {
            return (int) (Math.sqrt(xp + 9) - 3);
        } else if (xp >= 394 && xp <= 1507) {
            return (int) ((Math.sqrt(40 * xp - 7839) + 81) * 0.1);
        } else if (xp >= 1628) {
            return (int) ((Math.sqrt(72 * xp - 54215) + 325) / 18);
        }
        return -1;
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
