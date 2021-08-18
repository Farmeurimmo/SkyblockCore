package fr.farmeurimmo.criptmania.cmd.moderation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import fr.farmeurimmo.criptmania.utils.SendActionBar;

public class BuildCmd implements CommandExecutor, TabCompleter {
	
	public static ArrayList<Player> Build = new ArrayList<Player>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("build")) {
			if(sender instanceof Player) {
				final Player player = (Player) sender;
				if(player.hasPermission("build")) {
					if(Build.contains(player)) {
						Build.remove(player);
						SendActionBar.SendActionBarMsg(player, "�6Mode buildeurs d�sactiv� !");
					}
					else {
						Build.add(player);
						SendActionBar.SendActionBarMsg(player, "�6Mode buildeurs activ� !");
					}
				}
				else {
					SendActionBar.SendActionBarMsg(player, "�cPermissions insuffisantes !");
				}
			}
		}
		return false;
	}
	@Override
	 public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		 ArrayList<String> subcmd = new ArrayList<String>();
	        if (cmd.getName().equalsIgnoreCase("build")) {
	            if (args.length >= 0){
	            	subcmd.add("");
	            	Collections.sort(subcmd);
	            }
	        }
			return subcmd;
	 }
}
