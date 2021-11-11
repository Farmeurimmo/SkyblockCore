package fr.farmeurimmo.verymc.cmd.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import fr.farmeurimmo.verymc.utils.SendActionBar;

public class EnchantementCmd implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(sender instanceof Player){
			Player player = (Player) sender;
			if(player.hasPermission("enchantement")) {
				player.openEnchanting(new Location(Bukkit.getServer().getWorld("world"), -181, 108, -69), true);
			}
			else {
				SendActionBar.SendActionBarMsg(player, "§cPermissions insuffisantes !");
			}
		}
		
		return false;
	}
	@Override
	 public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		 ArrayList<String> subcmd = new ArrayList<String>();
	        if (cmd.getName().equalsIgnoreCase("enchantement")) {
	            if (args.length >= 0){
	            	subcmd.add("");
	            	Collections.sort(subcmd);
	            }
	        }
			return subcmd;
	 }
}
