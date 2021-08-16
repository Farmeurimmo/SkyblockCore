package fr.farmeurimmo.criptmania.cmd.moderation;

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

public class CheckFlyCmd implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("*")) {
					if(args.length == 0) {
						SendActionBar.SendActionBarMsg(player, "§c/checkfly <Joueur>");
					} else if (args.length == 1){
						if(Bukkit.getPlayer(args[0]) != null) {
							if(Bukkit.getPlayer(args[0]).isOnline()) {
							Player p = Bukkit.getPlayer(args[0]);
							String color1 = null;
							String color2 = null;
							if(p.getAllowFlight() == false) {
								color1 = "§c§l";
							} else {
								color1 = "§a§l";
							}
							if(p.isFlying() == false) {
								color2 = "§c§l";
							} else {
								color2 = "§a§l";
							}
							player.sendMessage("§6Gestion du fly de " + p.getName() + ": \n§6Permission de voler " + color1 + p.getAllowFlight() + " \n§6En vol: " + color2 + p.isFlying());
							return true;
							} else {
								SendActionBar.SendActionBarMsg(player, "§cCe joueur n'est pas en ligne !");
							}
						} else {
							SendActionBar.SendActionBarMsg(player, "§cCe joueur n'existe pas !");
						}
					} else {
						SendActionBar.SendActionBarMsg(player, "§c/checkfly <Joueur>");
					}
			} else {
				SendActionBar.SendActionBarMsg(player, "§cVous n'avez pas la permission !");
			}
		}
		return false;
	}
	@Override
	 public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		 ArrayList<String> subcmd = new ArrayList<String>();
	        if (cmd.getName().equalsIgnoreCase("checkfly")) {
	            if (args.length == 1) {
	            	for(Player player : Bukkit.getOnlinePlayers()) {
	            		subcmd.add(player.getName());
	            	}
	            	Collections.sort(subcmd);
	            } else if (args.length >= 2){
	            	subcmd.add("");
	            	Collections.sort(subcmd);
	            }
	        }
			return subcmd;
	 }
}
