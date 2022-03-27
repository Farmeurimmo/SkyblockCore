package main.java.fr.verymc.island.cmds;

import main.java.fr.verymc.island.IslandManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class IslandCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (IslandManager.instance.mainWorld != null) {
                if (args.length == 0) {
                    if (IslandManager.instance.asAnIsland(p)) {
                        IslandManager.instance.teleportPlayerToIslandSafe(p);
                    } else {
                        IslandManager.instance.genIsland(p);
                    }
                }
            }
        }
        return true;
    }
}
