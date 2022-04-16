package main.java.fr.verymc.cmd.moderation;

import main.java.fr.verymc.storage.SkyblockUserManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CheckFlyCmd implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                player.sendActionBar("§c/checkfly <Joueur>");
            } else if (args.length == 1) {
                if (Bukkit.getPlayer(args[0]) != null) {
                    if (Bukkit.getPlayer(args[0]).isOnline()) {
                        Player p = Bukkit.getPlayer(args[0]);
                        String color1 = null;
                        String color2 = null;
                        if (p.getAllowFlight() == false) {
                            color1 = "§c§l";
                        } else {
                            color1 = "§a§l";
                        }
                        if (p.isFlying() == false) {
                            color2 = "§c§l";
                        } else {
                            color2 = "§a§l";
                        }
                        player.sendMessage("§6Gestion du fly de " + p.getName() + ": \n§6Permission de voler " + color1 + p.getAllowFlight() +
                                " \n§6En vol: " + color2 + p.isFlying() + "\nTemp fly: " + SkyblockUserManager.instance.getUser(p.getUniqueId()).getFlyLeft());
                        return true;
                    } else {
                        player.sendActionBar("§cCe joueur n'est pas en ligne !");
                    }
                } else {
                    player.sendActionBar("§cCe joueur n'existe pas !");
                }
            } else {
                player.sendActionBar("§c/checkfly <Joueur>");
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        ArrayList<String> subcmd = new ArrayList<String>();
        if (cmd.getName().equalsIgnoreCase("checkfly")) {
            if (args.length == 1) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    subcmd.add(player.getName());
                }
                Collections.sort(subcmd);
            } else if (args.length >= 2) {
                subcmd.add("");
                Collections.sort(subcmd);
            }
        }
        return subcmd;
    }
}
