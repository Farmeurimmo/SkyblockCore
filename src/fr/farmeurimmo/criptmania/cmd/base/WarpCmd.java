package fr.farmeurimmo.criptmania.cmd.base;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.farmeurimmo.criptmania.utils.SendActionBar;

public class WarpCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		final Location Crates = new Location(Bukkit.getServer().getWorld("world"), -185.5, 109, -53.5, -42, 0);
		final Location Enchantement = new Location(Bukkit.getServer().getWorld("world"), -181.5, 109, -64.5, -170, 13);
		final Location Bar = new Location(Bukkit.getServer().getWorld("world"), -191.5, 110, -49.5, 90, 0);
		
		if(sender instanceof Player) {
			Player player = (Player) sender;
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase("boxes")) {
			player.teleport(Crates);
			SendActionBar.SendActionBarMsg(player, "§6Vous avez été téléporté au warp des boxes");	
			}
			if(args[0].equalsIgnoreCase("enchantement")) {
				player.teleport(Enchantement);
				SendActionBar.SendActionBarMsg(player, "§6Vous avez été téléporté au warp enchantement");	
				}
			if(args[0].equalsIgnoreCase("bar")) {
				player.teleport(Bar);
				SendActionBar.SendActionBarMsg(player, "§6Vous avez été téléporté au warp bar");	
				}
			}
			else {
				player.sendMessage("§6Warps disponibles: Boxes, Enchantement, Bar");
			}
		}
		return false;
	}

}
