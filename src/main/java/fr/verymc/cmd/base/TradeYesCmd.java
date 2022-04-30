package main.java.fr.verymc.cmd.base;

import main.java.fr.verymc.Main;
import main.java.fr.verymc.utils.PlayerUtils;
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

public class TradeYesCmd implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }
        if (args.length != 0) {
            player.sendMessage("§c/tradeyes");
            return false;
        }
        if (!Main.instance.haveTradeRequest.contains(player)) {
            player.sendMessage("§6§lTrade §8» §fVous ne possédez aucune demande d'échange.");
            return false;
        }
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (Main.instance.getTradeTarget(p.getName()) != null
            && Main.instance.getTradeTarget(p.getName()).equalsIgnoreCase(player.getName())) {
                player.sendMessage("§6§lTrade §8» §fVous avez §aaccepté §fla demande d'échange de §6" + p.getName() + "§f.");
                Main.instance.pendingTrade.remove(player);
                Main.instance.haveTradeRequest.remove(player);
            }
        }
        return false;
    }


    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        ArrayList<String> subcmd = new ArrayList<>();
        if (cmd.getName().equalsIgnoreCase("tradeys")) {
            if (args.length == 1) {
                subcmd.add("");
                Collections.sort(subcmd);
            }
        }
        return subcmd;
    }
}
