package main.java.fr.verymc.spigot.core.cmd.base;

import main.java.fr.verymc.spigot.Main;
import main.java.fr.verymc.spigot.core.cmd.utils.UtilsCmd;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class TradeCmd implements CommandExecutor, TabCompleter {
    public static void tradeExpiration(Player player, Player p) {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
            public void run() {
                if (Main.instance.getTradeTarget(player.getName()) != null) {
                    if (Main.instance.getTradeTarget(player.getName()).equalsIgnoreCase(p.getName())) {
                        Main.instance.haveTradeRequest.remove(p);
                        Main.instance.tradeTarget.remove(player.getName());
                        player.sendMessage("§6§lTrade §8» §fVotre demande d'échange à §a" + p.getName() + " §fa expiré.");
                        Main.instance.pendingTrade.remove(player);
                    }
                }
            }
        }, 1200);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }
        if (args.length != 1) {
            player.sendMessage("§c/trade <Joueur>");
            return false;
        }
        if (Bukkit.getPlayer(args[0]) == null) {
            player.sendMessage("§cCe joueur n'existe pas !");
            return false;
        }
        if (!Bukkit.getPlayer(args[0]).isOnline()) {
            player.sendMessage("§cCe joueur n'est pas en ligne !");
            return false;
        }
        if (Main.instance.pending.contains(player)) {
            player.sendMessage("§6§lTrade §8» §fVous avez déjà une demande en cours, §cannulez §fla avec §c/tradecancel"
                    + " §fpour pouvoir en relancer une.");
            return false;
        }
        if (Bukkit.getPlayer(args[0]).getName().equalsIgnoreCase(player.getName())) {
            player.sendMessage("§6§lTrade §8» §fVous ne pouvez pas échanger à vous même.");
            return false;
        }
        Player p = Bukkit.getPlayer(args[0]);
        Main.instance.haveTradeRequest.remove(p);
        Main.instance.haveTradeRequest.add(p);
        Main.instance.pendingTrade.add(player);
        Main.instance.setTradeTarget(player.getName(), p.getName());
        tradeExpiration(player, p);
        p.sendMessage("§6§lTrade §8» §f" + player.getName() + " souhaite faire un échange avec vous. \n \nVous avez 60 secondes"
                + " pour accepter ou refuser avec les commandes: §a/tradeyes §fou §c/tradeno \n§f");
        player.sendMessage("§6§lTrade §8» §fVous avez envoyé une demande d'échange au joueur " + p.getName() + ".\n \n§fSi vous "
                + "souhaitez §cannuler §fvotre demande d'échange, merci d'effectuer la commande §c/tradecancel \n§f");
        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String[] args) {
        ArrayList<String> subcmd = new ArrayList<>();
        if (cmd.getName().equalsIgnoreCase("trade")) {
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
