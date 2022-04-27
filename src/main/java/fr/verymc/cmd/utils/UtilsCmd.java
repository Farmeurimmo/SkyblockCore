package main.java.fr.verymc.cmd.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;

public class UtilsCmd {
    public static void set_all_player_in_subcmd(ArrayList<String> subcmd) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            subcmd.add(player.getName());
        }
    }
    public static void set_all_player_in_subcmd_without_me(ArrayList<String> subcmd, CommandSender sender) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (!p.getName().equals(sender.getName())) {
                    subcmd.add(p.getName());
                }
            }
    }
    public static void generate_auto_complete(String[] args, ArrayList<String> subcmd) {
        if (args.length == 1) {
            set_all_player_in_subcmd(subcmd);
            Collections.sort(subcmd);
        } else if (args.length >= 2) {
            subcmd.add("");
            Collections.sort(subcmd);
        }
    }
}
