package main.java.fr.verymc.cmd.moderation;

import main.java.fr.verymc.core.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataCmd implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("*")) {
            if (args.length == 0 || args.length >= 2) {
                sender.sendMessage("§c/data <reload>");
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    try {
                        Main.instance.reloadData();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        sender.sendMessage("§6§lData §8» §fProblème lors du reload !");
                    } catch (IOException e) {
                        e.printStackTrace();
                        sender.sendMessage("§6§lData §8» §fProblème lors du reload !");
                    }
                    sender.sendMessage("§6§lData §8» §fReload Terminé !");
                }
            }
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        ArrayList<String> subcmd = new ArrayList<String>();
        if (cmd.getName().equalsIgnoreCase("datafile")) {
            if (sender.hasPermission("*")) {
                if (args.length == 1) {
                    subcmd.add("reload");
                    Collections.sort(subcmd);
                } else if (args.length >= 2) {
                    subcmd.add("");
                    Collections.sort(subcmd);
                }
                return subcmd;
            } else {
                if (args.length >= 0) {
                    subcmd.add("");
                    Collections.sort(subcmd);
                }
            }
        }
        return subcmd;
    }
}