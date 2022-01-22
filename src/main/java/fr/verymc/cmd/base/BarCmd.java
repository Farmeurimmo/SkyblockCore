package main.java.fr.verymc.cmd.base;

import main.java.fr.verymc.core.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BarCmd implements CommandExecutor, TabCompleter {

    public static ArrayList<Player> Disable = new ArrayList<Player>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("on")) {
                    Disable.remove(player);
                    player.sendMessage(Main.getInstance().getConfig().getString("bossbar.activated"));
                } else if (args[0].equalsIgnoreCase("off")) {
                    Disable.add(player);
                    player.sendMessage(Main.getInstance().getConfig().getString("bossbar.disabled"));
                } else {
                    player.sendMessage(Main.getInstance().getConfig().getString("bossbar.invalidargs"));
                    return true;
                }
            } else {
                player.sendMessage("§cUsage, /bar <on|off>");
                return true;
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        ArrayList<String> subcmd = new ArrayList<String>();
        if (cmd.getName().equalsIgnoreCase("bar")) {
            if (args.length == 1) {
                subcmd.add("on");
                subcmd.add("off");
                Collections.sort(subcmd);
            } else if (args.length >= 2) {
                subcmd.add("");
                Collections.sort(subcmd);
            }
        }
        return subcmd;
    }
}
