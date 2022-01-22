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

public class GmLCmd implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("*")) {
                if (cmd.getName().equalsIgnoreCase("gma")) {
                    if (args.length == 0) {
                        player.setGameMode(GameMode.ADVENTURE);
                        player.sendActionBar("§aVous venez de passer en adventure");
                    } else if (args.length == 1) {
                        if (Bukkit.getPlayer(args[0]) != null) {
                            if (Bukkit.getPlayer(args[0]).isOnline()) {
                                Player p = Bukkit.getPlayer(args[0]);
                                p.setGameMode(GameMode.ADVENTURE);
                                p.sendActionBar("§aVous venez de passer en adventure");
                                return true;
                            } else {
                                player.sendActionBar("§cCe joueur n'est pas en ligne !");
                            }
                        } else {
                            player.sendActionBar("§cCe joueur n'existe pas !");
                        }
                    } else {
                        player.sendActionBar("§c/gma [Joueur]");
                    }
                } else if (cmd.getName().equalsIgnoreCase("gmc")) {
                    if (args.length == 0) {
                        player.setGameMode(GameMode.CREATIVE);
                        player.sendActionBar("§aVous venez de passer en créatif");
                    } else if (args.length == 1) {
                        if (Bukkit.getPlayer(args[0]) != null) {
                            if (Bukkit.getPlayer(args[0]).isOnline()) {
                                Player p = Bukkit.getPlayer(args[0]);
                                p.setGameMode(GameMode.CREATIVE);
                                p.sendActionBar("§aVous venez de passer en créatif");
                                return true;
                            } else {
                                player.sendActionBar("§cCe joueur n'est pas en ligne !");
                            }
                        } else {
                            player.sendActionBar("§cCe joueur n'existe pas !");
                        }
                    } else {
                        player.sendActionBar("§c/gmc [Joueur]");
                    }
                } else if (cmd.getName().equalsIgnoreCase("gms")) {
                    if (args.length == 0) {
                        player.setGameMode(GameMode.SURVIVAL);
                        player.sendActionBar("§aVous venez de passer en survie");
                    } else if (args.length == 1) {
                        if (Bukkit.getPlayer(args[0]) != null) {
                            if (Bukkit.getPlayer(args[0]).isOnline()) {
                                Player p = Bukkit.getPlayer(args[0]);
                                p.setGameMode(GameMode.SURVIVAL);
                                p.sendActionBar("§aVous venez de passer en survie");
                                return true;
                            } else {
                                player.sendActionBar("§cCe joueur n'est pas en ligne !");
                            }
                        } else {
                            player.sendActionBar("§cCe joueur n'existe pas !");
                        }
                    } else {
                        player.sendActionBar("§c/gms [Joueur]");
                    }
                } else if (cmd.getName().equalsIgnoreCase("gmsp")) {
                    if (args.length == 0) {
                        player.setGameMode(GameMode.SPECTATOR);
                        player.sendActionBar("§aVous venez de passer en spectateur");
                    } else if (args.length == 1) {
                        if (Bukkit.getPlayer(args[0]) != null) {
                            if (Bukkit.getPlayer(args[0]).isOnline()) {
                                Player p = Bukkit.getPlayer(args[0]);
                                p.setGameMode(GameMode.SPECTATOR);
                                p.sendActionBar("§aVous venez de passer en spectateur");
                                return true;
                            } else {
                                player.sendActionBar("§cCe joueur n'est pas en ligne !");
                            }
                        } else {
                            player.sendActionBar("§cCe joueur n'existe pas !");
                        }
                    } else {
                        player.sendActionBar("§c/gmsp [Joueur]");
                    }
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
        if (cmd.getName().equalsIgnoreCase("gma") || cmd.getName().equalsIgnoreCase("gms")
                || cmd.getName().equalsIgnoreCase("gmsp") || cmd.getName().equalsIgnoreCase("gmc")) {
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
