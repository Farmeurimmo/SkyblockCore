package main.java.fr.verymc.cmd.base;

import main.java.fr.verymc.Main;
import main.java.fr.verymc.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SpawnCmd implements CommandExecutor, TabCompleter {

    public static final Location Spawn = new Location(Bukkit.getServer().getWorld("world"), -187.5, 72.5, -63.5, -90, 0);

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            final Player player = (Player) sender;
            if (cmd.getName().equalsIgnoreCase("spawn")) {
                if (args.length == 0) {
                    final int timeLeft = Main.instance.getCooldown(player.getName());
                    if (timeLeft == 0) {
                        PlayerUtils.instance.teleportPlayerFromRequest(player, Spawn, PlayerUtils.instance.getPlayerTeleportingdelay(player));
                    }
                } else if (args.length == 1) {
                    if (Bukkit.getPlayer(args[0]) != null) {
                        if (Bukkit.getPlayer(args[0]).isOnline()) {
                            Player p = Bukkit.getPlayer(args[0]);
                            p.teleport(Spawn);
                            p.sendActionBar("§aVous avez été Téléporté au spawn par un membre du staff !");
                            player.sendActionBar("§6" + p.getName() + " §aa été envoyé au spawn avec succès !");
                        } else {
                            player.sendActionBar("§cCe joueur n'est pas en ligne !");
                        }
                    } else {
                        player.sendActionBar("§cCe joueur n'existe pas !");
                    }
                } else {
                    player.sendActionBar("§c/spawn [Joueur]");
                }
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        ArrayList<String> subcmd = new ArrayList<String>();
        if (cmd.getName().equalsIgnoreCase("spawn")) {
            if (sender.hasPermission("*")) {
                if (args.length == 1) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        subcmd.add(player.getName());
                    }
                    Collections.sort(subcmd);
                } else if (args.length >= 2) {
                    subcmd.add("");
                    Collections.sort(subcmd);
                }
            } else {
                subcmd.add("");
                Collections.sort(subcmd);
            }
        }
        return subcmd;
    }
}
