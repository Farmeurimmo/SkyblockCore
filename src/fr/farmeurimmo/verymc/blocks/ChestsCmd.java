package fr.farmeurimmo.verymc.blocks;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ChestsCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(!sender.hasPermission("chests")) {
			return true;
		}
		if(args.length == 0 || args.length > 1){
            return true;
        }
        if(args[0]==null || args[0].length() < 4){
            return true;
        }
        if(Bukkit.getPlayer(args[0])==null) {
        	return true;
        }
        ChunkCollectorManager.GiveChest(Bukkit.getPlayer(args[0]),0);
		
		return false;
	}

}
