package fr.farmeurimmo.criptmania.cmd.base;

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

import fr.farmeurimmo.criptmania.utils.GetTeleportDelay;
import fr.farmeurimmo.criptmania.utils.SendActionBar;
import fr.farmeurimmo.criptmania.utils.TeleportPlayer;

public class WarpCmd implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		final Location Crates = new Location(Bukkit.getServer().getWorld("world"), -185.5, 109, -53.5, -42, 0);
		final Location Enchantement = new Location(Bukkit.getServer().getWorld("world"), -181.5, 109, -64.5, -170, 13);
		final Location Bar = new Location(Bukkit.getServer().getWorld("world"), -191.5, 110, -49.5, 90, 0);
		
		if(sender instanceof Player) {
			Player player = (Player) sender;
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase("boxes")) {
				TeleportPlayer.TeleportPlayerFromRequest(player, Crates, GetTeleportDelay.GetPlayerTeleportingdelay(player));
			}
			if(args[0].equalsIgnoreCase("enchantement")) {
				TeleportPlayer.TeleportPlayerFromRequest(player, Enchantement, GetTeleportDelay.GetPlayerTeleportingdelay(player));
				}
			if(args[0].equalsIgnoreCase("bar")) {
				TeleportPlayer.TeleportPlayerFromRequest(player, Bar, GetTeleportDelay.GetPlayerTeleportingdelay(player));
				}
			} else if(args.length == 2) {
				if(Bukkit.getPlayer(args[1]) != null) {
					if(Bukkit.getPlayer(args[1]).isOnline()) {
					Player p = Bukkit.getPlayer(args[1]);
					if(args[0].equalsIgnoreCase("boxes")) {
						p.teleport(Crates);
						SendActionBar.SendActionBarMsg(p, "§aVous avez été téléporté au warp boxes par un membre du staff !");
					}
					if(args[0].equalsIgnoreCase("enchantement")) {
						p.teleport(Enchantement);
						SendActionBar.SendActionBarMsg(p, "§aVous avez été téléporté au warp enchantement par un membre du staff !");
						}
					if(args[0].equalsIgnoreCase("bar")) {
						p.teleport(Bar);
						SendActionBar.SendActionBarMsg(p, "§aVous avez été téléporté au warp bar par un membre du staff !");
						}
					}
				}
			} else {
				player.sendMessage("§c/warp <warp> [Joueur]");
			}
		}
		return false;
	}
	@Override
	 public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		 ArrayList<String> subcmd = new ArrayList<String>();
	        if (cmd.getName().equalsIgnoreCase("warp")) {
	            if (args.length == 1){
	            	subcmd.add("boxes");
	            	subcmd.add("enchantement");
	            	subcmd.add("bar");
	            	Collections.sort(subcmd);
	            } else if (args.length == 2) {
	            	for(Player player : Bukkit.getOnlinePlayers()) {
	            		subcmd.add(player.getName());
	            	}
	            	Collections.sort(subcmd);
	            } else if(args.length >= 3) {
	            	subcmd.add("");
	            	Collections.sort(subcmd);
	            }
	        }
			return subcmd;
	 }
}
