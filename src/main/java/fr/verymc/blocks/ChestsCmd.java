package main.java.fr.verymc.blocks;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ChestsCmd implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("chests")) {
            return true;
        }
        if (args.length > 2 || args.length < 2) {
            sender.sendMessage("§cErreur, utilisation /chests <joueur> <type>");
            return true;
        }
        if (args[0] == null || args[0].length() < 4) {
            sender.sendMessage("§cErreur, joueur inconnu");
            return true;
        }
        if (Bukkit.getPlayer(args[0]) == null) {
            sender.sendMessage("§cErreur, joueur inconnu");
            return true;
        }
        if (args[1].equalsIgnoreCase("chunkhoppeur")) {
            ChestManager.instance.giveChest(Bukkit.getPlayer(args[0]), System.currentTimeMillis(), 0);
        } else if (args[1].equalsIgnoreCase("sellchest")) {
            ChestManager.instance.giveChest(Bukkit.getPlayer(args[0]), System.currentTimeMillis(), 1);
        } else if (args[1].equalsIgnoreCase("playershop")) {
            ChestManager.instance.giveChest(Bukkit.getPlayer(args[0]), System.currentTimeMillis(), 2);
        } else {
            sender.sendMessage("§cErreur, coffre/hoppeur inconnu");
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        ArrayList<String> subcmd = new ArrayList<String>();
        if (cmd.getName().equalsIgnoreCase("chests")) {
            if (args.length == 1) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    subcmd.add(p.getName());
                }
            } else if (args.length == 2) {
                subcmd.addAll(Arrays.asList("ChunkHoppeur", "SellChest", "PlayerShop"));
            } else {
                subcmd.add("");
            }
        }
        Collections.sort(subcmd);
        return subcmd;
    }

}
