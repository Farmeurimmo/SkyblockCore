package main.java.fr.verymc.cmd.base;

import main.java.fr.verymc.Main;
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

public class TradeNoCmd implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }
        if (args.length != 0) {
            player.sendMessage("§c/tradeno");
            return false;
        }
        if (!Main.instance.haveTradeRequest.contains(player)) {
            player.sendMessage("§6§lTrade §8» §fVous ne possèdez aucune demande d'échange.");
            return false;
        }
        Main.instance.haveTradeRequest.remove(player);
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (Main.instance.getTradeTarget(p.getName()) != null
                    && Main.instance.getTradeTarget(p.getName()).equalsIgnoreCase(player.getName())) {
                Main.instance.pendingTrade.remove(player.getName());
                Main.instance.tradeTarget.remove(player.getName());
                p.sendMessage("§6§lTrade §8» §a" + player.getName() + " §fa refusé votre demande d'échange.");
                player.sendMessage("§6§lTrade §8» §fLa demande d'échange de " + p.getName() + " §fa été refusé avec succès.");
            }
        }
        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        ArrayList<String> subcmd = new ArrayList<>();
        if (cmd.getName().equalsIgnoreCase("tradeno")) {
            if (args.length == 1) {
                subcmd.add("");
                Collections.sort(subcmd);
            }
        }
        return subcmd;
    }
}
