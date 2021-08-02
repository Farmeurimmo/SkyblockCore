package fr.farmeurimmo.criptmania.featherfly;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DailyFlyCmd implements CommandExecutor {
	
	public static ArrayList<UUID> AlreadyRedeem = new ArrayList<UUID>();

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(!AlreadyRedeem.contains(player.getUniqueId())) {
				if(player.hasPermission("dailyfly.1heur")) {
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "featherfly give " + player.getName() + " 1heur");
					player.sendMessage("§aVous avez récupéré votre plûme de fly journalier.");
					AlreadyRedeem.add(player.getUniqueId());
				}	
			}
			else {
				player.sendMessage("§cVous avez déjà récupéré votre fly journalier.");
			}
		}
		return false;
	}

}
