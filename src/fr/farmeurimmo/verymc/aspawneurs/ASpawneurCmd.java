package fr.farmeurimmo.verymc.aspawneurs;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ASpawneurCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(args.length == 3) {
		if(Bukkit.getPlayer(args[0]) != null) {
			if(Bukkit.getPlayer(args[0]).isOnline()) {
				if(args[1].equalsIgnoreCase("give")) {
					if(args[2].equalsIgnoreCase("IG")) {
						SpawneurManager.GiveSpawneur(Bukkit.getPlayer(args[0]), args[2]);
					}
				}
			}
		}
		} else {
			sender.sendMessage("Utilisation: /aspawneur <pseudo> <give> <type>");
		}
		
		return false;
	}

}
