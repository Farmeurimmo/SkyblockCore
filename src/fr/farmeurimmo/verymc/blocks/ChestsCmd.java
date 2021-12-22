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
		if(args.length == 0 || args.length > 2){
			sender.sendMessage("§cErreur, utilisation /chests <joueur> <type>");
            return true;
        }
        if(args[0]==null || args[0].length() < 4){
        	sender.sendMessage("§cErreur, joueur inconnu");
        	return true;
        }
        if(Bukkit.getPlayer(args[0])==null) {
        	sender.sendMessage("§cErreur, joueur inconnu");
        	return true;
        }
        if(args[1].equalsIgnoreCase("chunkhoppeur")) {
        	ChunkCollectorManager.GiveChest(Bukkit.getPlayer(args[0]),0);
        } else if(args[1].equalsIgnoreCase("sellchest")) {
        	SellChestManager.GiveSellChest(Bukkit.getPlayer(args[0]), 0);
        } else {
        	sender.sendMessage("§cErreur, coffre/hoppeur inconnu");
        }
		
		return false;
	}

}
