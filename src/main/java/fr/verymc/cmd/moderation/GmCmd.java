package main.java.fr.verymc.cmd.moderation;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GmCmd implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("gm")) {
                if (args.length == 0) {
                    player.sendActionBar("§c/gm <0,1,2,3> [Joueur]");
                    return true;
                } else if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("0")) {
                        player.setGameMode(GameMode.SURVIVAL);
                        player.sendActionBar("§aVous venez de passer en survie");
                        return true;
                    } else if (args[0].equalsIgnoreCase("1")) {
                        player.setGameMode(GameMode.CREATIVE);
                        player.sendActionBar("§aVous venez de passer en créatif");
                        return true;
                    } else if (args[0].equalsIgnoreCase("2")) {
                        player.setGameMode(GameMode.ADVENTURE);
                        player.sendActionBar("§aVous venez de passer en adventure");
                        return true;
                    } else if (args[0].equalsIgnoreCase("3")) {
                        player.setGameMode(GameMode.SPECTATOR);
                        player.sendActionBar("§aVous venez de passer en spectateur");
                        return true;
                    } else {
                        player.sendActionBar("§cGamemodes disponibles: 0,1,2,3");
                        return true;
                    }
                } else if (args.length == 2) {
                    if (Bukkit.getPlayer(args[1]) != null) {
                        if (Bukkit.getPlayer(args[1]).isOnline()) {
                            Player p = Bukkit.getPlayer(args[1]);
                            if (args[0].equalsIgnoreCase("0")) {
                                p.setGameMode(GameMode.SURVIVAL);
                                p.sendActionBar("§aVous venez de passer en survie");
                                return true;
                            } else if (args[0].equalsIgnoreCase("1")) {
                                p.setGameMode(GameMode.CREATIVE);
                                p.sendActionBar("§aVous venez de passer en créatif");
                                return true;
                            } else if (args[0].equalsIgnoreCase("2")) {
                                p.setGameMode(GameMode.ADVENTURE);
                                p.sendActionBar("§aVous venez de passer en adventure");
                                return true;
                            } else if (args[0].equalsIgnoreCase("3")) {
                                p.setGameMode(GameMode.SPECTATOR);
                                p.sendActionBar("§aVous venez de passer en spectateur");
                                return true;
                            } else {
                                player.sendActionBar("§cGamemodes disponibles: 0,1,2,3");
                                return true;
                            }
                        } else {
                            player.sendActionBar("§cCe joueur n'est pas en ligne !");
                        }
                    } else {
                        player.sendActionBar("§cCe joueur n'existe pas !");
                    }
                } else {
                    player.sendActionBar("§c/gm <0,1,2,3> [Joueur]");
                }
            } else {
                player.sendActionBar("§cVous n'avez pas la permission !");
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        ArrayList<String> subcmd = new ArrayList<String>();
        if (cmd.getName().equalsIgnoreCase("gm")) {
            if (args.length == 1) {
                subcmd.add("0");
                subcmd.add("1");
                subcmd.add("2");
                subcmd.add("3");
                Collections.sort(subcmd);
            } else if (args.length == 2) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    subcmd.add(player.getName());
                }
                Collections.sort(subcmd);
            } else if (args.length >= 3) {
                subcmd.add("");
                Collections.sort(subcmd);
            }
        }
        return subcmd;
    }

}
