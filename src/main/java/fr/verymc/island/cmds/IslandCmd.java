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
                    p.teleport(IslandManager.instance.mainWorld.getSpawnLocation());
                    p.sendMessage("§aVous avez été téléporté à l'île.");
                    IslandManager.instance.genIsland(p);
                }
            }
        }
        return true;
    }
}
