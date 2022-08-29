package main.java.fr.verymc.spigot.island.playerwarps;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayerWarpCmd implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            player.sendMessage("§6§lPlayerWarp §8» §cCommande désactivée temporairement.");
            if (args.length == 0) {
                PlayerWarpManagingGui.instance.openManagingMenu(player);
                return true;
            } else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("note")) {
                    PlayerWarp pw = PlayerWarpManager.instance.getPlayerWarpFromPlayerName(args[1]);
                    if (pw != null) {
                        if (pw.alreadyVoted(player.getUniqueId())) {
                            player.sendMessage("§6§lPlayerWarp §8» §cVous avez déjà voté pour ce warp.");
                            return true;
                        }
                        PlayerWarpNotationGui.instance.openNotationMenu(player, pw);
                    } else {
                        player.sendMessage("§6§lPlayerWarp §8» §cCette personne n'a pas de warp.");
                    }
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        ArrayList<String> subcmd = new ArrayList<String>();
        if (cmd.getName().equalsIgnoreCase("pw") || cmd.getName().equalsIgnoreCase("playerwarp")) {
            if (args.length == 1) {
                subcmd.add("note");
            } else if (args.length >= 2) {
                if (args[0].equalsIgnoreCase("note")) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        subcmd.add(p.getName());
                    }
                }
            }
            Collections.sort(subcmd);
        }
        return subcmd;
    }
}
