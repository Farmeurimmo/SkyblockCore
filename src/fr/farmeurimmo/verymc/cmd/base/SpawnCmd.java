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

import fr.farmeurimmo.verymc.core.Main;
import fr.farmeurimmo.verymc.utils.GetTeleportDelay;
import fr.farmeurimmo.verymc.utils.SendActionBar;
import fr.farmeurimmo.verymc.utils.TeleportPlayer;

public class SpawnCmd implements CommandExecutor, TabCompleter {

	public static final Location Spawn = new Location(Bukkit.getServer().getWorld("world"), -187.5, 72.5, -64.5, -90, 0);
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(sender instanceof Player) {
			final Player player = (Player) sender;
		if(cmd.getName().equalsIgnoreCase("spawn")) {
			if(args.length == 0) {
				final int timeLeft = Main.instance1.getCooldown(player.getName());
				if(timeLeft == 0) {
					TeleportPlayer.TeleportPlayerFromRequest(player, Spawn, GetTeleportDelay.GetPlayerTeleportingdelay(player));
				}
			} else if (args.length == 1) {
				if(Bukkit.getPlayer(args[0]) != null) {
					if(Bukkit.getPlayer(args[0]).isOnline()) {
					Player p = Bukkit.getPlayer(args[0]);
					p.teleport(Spawn);
					SendActionBar.SendActionBarMsg(player, "§aVous avez été téléporté au spawn par un membre du staff !");
					} else {
						SendActionBar.SendActionBarMsg(player, "§cCe joueur n'est pas en ligne !");
					}
				} else {
					SendActionBar.SendActionBarMsg(player, "§cCe joueur n'existe pas !");
				}
			} else {
				SendActionBar.SendActionBarMsg(player, "§c/spawn [Joueur]");
			}
		}
		}
		return false;
	}
	@Override
	 public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		 ArrayList<String> subcmd = new ArrayList<String>();
	        if (cmd.getName().equalsIgnoreCase("spawn")) {
	        	if(sender.hasPermission("*")) {
	            if (args.length == 1){
	            	for(Player player : Bukkit.getOnlinePlayers()) {
	            		subcmd.add(player.getName());
	            	}
	            	Collections.sort(subcmd);
	            } else if(args.length >= 2) {
	            	subcmd.add("");
	            	Collections.sort(subcmd);
	            }
	        	} else {
	        		subcmd.add("");
	            	Collections.sort(subcmd);
	        	}
	        }
			return subcmd;
	 }
}
