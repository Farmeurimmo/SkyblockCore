package main.java.fr.verymc.cmd.base;

import main.java.fr.verymc.Main;
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

public class TradeCancelCmd implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }
        if (args.length != 0) {
            player.sendMessage("§c/tradecancel");
            return false;
        }
        if (!Main.instance.pendingTrade.contains(player)) {
            player.sendMessage("§§6§lTrade §8» §fVous n'avez aucune demande d'échange en cours.");
            return false;
        }
        Main.instance.pendingTrade.remove(player);
        player.sendMessage("§§6§lTrade §8» §fVous avez §cannulé §fvotre demande d'échange à "
                + Main.instance.getTradeTarget(player.getName()) + ".");
        Main.instance.haveTradeRequest.remove(Main.instance.getTradeTarget(player.getName()));
        Main.instance.tradeTarget.remove(player);
        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        ArrayList<String> subcmd = new ArrayList<>();
        if (cmd.getName().equalsIgnoreCase("tpacancel")) {
            if (args.length >= 1) {
                subcmd.add("");
                Collections.sort(subcmd);
            }
        }
        return subcmd;
    }
}
