package main.java.fr.verymc.cmd.base;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EnchantementCmd implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }
        if (player.hasPermission("enchantement")) {
            player.openEnchanting(new Location(Bukkit.getServer().getWorld("world"), -186, 64, -55), true);
        } else {
            player.sendActionBar("§cPermissions insuffisantes !");
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        ArrayList<String> subcmd = new ArrayList<>();
        if (cmd.getName().equalsIgnoreCase("enchantement")) {
            subcmd.add("");
            Collections.sort(subcmd);
        }
        return subcmd;
    }
}
