package fr.farmeurimmo.criptmania.challenges;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import fr.farmeurimmo.criptmania.Main;
import fr.farmeurimmo.criptmania.utils.SendActionBar;

public class ChallengesCmd implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(args.length == 0) {
			ChallengesGuis.MakeDailyGui(player);
			} else if(args.length == 1){
				if(args[0].equalsIgnoreCase("reload")) {
					if(player.hasPermission("*")) {
					try {
						Main.instance1.reloadData();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
						player.sendMessage("§6§lChallenges §8» §fProblème lors du reload !");
					} catch (IOException e) {
						e.printStackTrace();
						player.sendMessage("§6§lChallenges §8» §fProblème lors du reload !");
					}
					player.sendMessage("§6§lChallenges §8» §fReload terminé !");
					} else {
						SendActionBar.SendActionBarMsg(player, "§cPermissions insuffisantes !");
					}
				}
			}
		}
		
		return false;
	}
	@Override
	 public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		 ArrayList<String> subcmd = new ArrayList<String>();
	        if (cmd.getName().equalsIgnoreCase("challenges") || cmd.getName().equalsIgnoreCase("c")) {
	        	if(sender.hasPermission("*")) {
	        		if (args.length == 1){
		            	subcmd.add("reload");
		            	Collections.sort(subcmd);
		            } else if (args.length >= 2){
		            	subcmd.add("");
		            	Collections.sort(subcmd);
		            }
	        		return subcmd;
	        	} else {
	            if (args.length >= 0){
	            	subcmd.add("");
	            	Collections.sort(subcmd);
	            }
	            }
	        }
			return subcmd;
	 }
}
