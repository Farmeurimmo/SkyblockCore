package main.java.fr.verymc.island.cmds;

import main.java.fr.verymc.island.IslandManager;
import main.java.fr.verymc.island.guis.IslandMainGui;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class IslandCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (!IslandManager.instance.asAnIsland(p)) {
                IslandManager.instance.genIsland(p);
                return true;
            }
            if (IslandManager.instance.mainWorld != null) {
                if (args.length == 0) {
                    IslandMainGui.instance.openMainIslandMenu(p);
                    return true;
                } else if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("go")) {
                        IslandManager.instance.teleportPlayerToIslandSafe(p);
                        return true;
                    }
                }
            }
        }
        return true;
    }
}
