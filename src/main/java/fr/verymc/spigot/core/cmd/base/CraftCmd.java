package main.java.fr.verymc.spigot.core.cmd.base;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CraftCmd implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player player)) {
            return false;
        }
        if (player.hasPermission("craft")) {
            player.openWorkbench(null, true);
        } else {
            player.sendActionBar("§cPermissions insuffisantes !");
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        ArrayList<String> subcmd = new ArrayList<>();
        if (cmd.getName().equalsIgnoreCase("craft")) {
            subcmd.add("");
            Collections.sort(subcmd);
        }
        return subcmd;
    }
}
