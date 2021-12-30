package main.java.fr.verymc.cmd.base;

import main.java.fr.verymc.utils.SendActionBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FlyCmd implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("fly")) {
                if (!player.getWorld().getName().equalsIgnoreCase("world")) {
                    if (player.getAllowFlight() == false) {
                        player.setAllowFlight(true);
                        player.setFlying(true);
                        SendActionBar.SendActionBarMsg(player, "§aFly activé !");
                    } else {
                        player.setAllowFlight(false);
                        player.setFlying(false);
                        SendActionBar.SendActionBarMsg(player, "§aFly désactivé !");
                    }
                } else {
                    SendActionBar.SendActionBarMsg(player, "§cImpossible de fly dans ce monde !");
                }
            } else {
                SendActionBar.SendActionBarMsg(player, "§cPermissions insuffisantes.");
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        ArrayList<String> subcmd = new ArrayList<String>();
        if (cmd.getName().equalsIgnoreCase("fly")) {
            if (args.length >= 0) {
                subcmd.add("");
                Collections.sort(subcmd);
            }
        }
        return subcmd;
    }
}
