package fr.farmeurimmo.criptmania.crates;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import fr.farmeurimmo.criptmania.utils.SendActionBar;

public class KeyCmd implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("*")) {
				if(args.length == 3) {
					if(Bukkit.getPlayer(args[0]) != null) {
						if(Bukkit.getPlayer(args[0]).isOnline()) {
						Player p = Bukkit.getPlayer(args[0]);
							String sample = args[2];
							char[] chars = sample.toCharArray();
						      StringBuilder sb = new StringBuilder();
						      for(char c : chars){
						         if(Character.isDigit(c)){
						            sb.append(c);
						         }
						      }
						      if(!sb.isEmpty()) {
						    	  int nombre = Integer.parseInt(sb.toString());
						    	  if(args[1].equalsIgnoreCase("L�gendaire")) {
						    		  CratesKeyManager.GiveCrateKey(p, nombre, "L�gendaire");
						      }
						    	  if(args[1].equalsIgnoreCase("Challenge")) {
						    		  CratesKeyManager.GiveCrateKey(p, nombre, "Challenge");
						      }
						}
						}
					}
				}
			} else {
				SendActionBar.SendActionBarMsg(player, "�cPermissions insuffisantes !");
			}
		}
		
		return false;
	}
	@Override
	 public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		 ArrayList<String> subcmd = new ArrayList<String>();
	        if (cmd.getName().equalsIgnoreCase("key")) {
	        	if(sender.hasPermission("*")) {
	        		if (args.length == 1){
		            	for(Player player : Bukkit.getOnlinePlayers()) {
		            		subcmd.add(player.getName());
		            	}
		            } else if (args.length == 2){
		            	subcmd.add("L�gendaire");
		            	subcmd.add("Challenge");
		            } else if (args.length == 3){
		            	subcmd.add("1");
		            } else if (args.length >= 4){
		            	subcmd.add("");
		            }
	        		Collections.sort(subcmd);
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
