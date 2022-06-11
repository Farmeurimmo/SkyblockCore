package main.java.fr.verymc.core.cmd.moderation;

import main.java.fr.verymc.hub.events.RedstoneCheck;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RedstoneToggleCmd implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender.hasPermission("*"))) {
            return false;
        }
        if (args.length != 1) {
            sender.sendMessage("§c/redstone <On>/<Off>");
            return false;
        }
        if (args[0].equalsIgnoreCase("on")) {
            RedstoneCheck.redstone = true;
            sender.sendMessage("§aRedstone activée !");
        } else if (args[0].equalsIgnoreCase("off")) {
            RedstoneCheck.redstone = false;
            sender.sendMessage("§cRedstone désactivée !");
        } else {
            sender.sendMessage("§c/redstone <On>/<Off>");
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        ArrayList<String> subcmd = new ArrayList<>();
        if (cmd.getName().equalsIgnoreCase("redstone")) {
            if (args.length == 1) {
                subcmd.addAll(Arrays.asList("on", "off"));
                Collections.sort(subcmd);
            } else if (args.length >= 2) {
                subcmd.add("");
                Collections.sort(subcmd);
            }
        }
        return subcmd;
    }
}
