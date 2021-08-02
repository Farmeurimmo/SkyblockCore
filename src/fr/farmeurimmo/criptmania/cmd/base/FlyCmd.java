package fr.farmeurimmo.criptmania.cmd.base;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.farmeurimmo.criptmania.utils.SendActionBar;

public class FlyCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(sender instanceof Player) {
		Player player = (Player) sender;
		if(player.hasPermission("fly")) {
			if(!player.getWorld().getName().equalsIgnoreCase("world")) {
			if(player.getAllowFlight() == false) {
				player.setAllowFlight(true);
				player.setFlying(true);
				SendActionBar.SendActionBarMsg(player, "§aFly activé !");
			}
			else {
				player.setAllowFlight(false);
				player.setFlying(false);
				SendActionBar.SendActionBarMsg(player, "§aFly désactivé !");
			}
		}
			else {
				SendActionBar.SendActionBarMsg(player, "§cImpossible de fly dans ce monde !");
			}
		}
		else {
			SendActionBar.SendActionBarMsg(player, "§cPermissions insuffisantes.");
		}
		}
		return false;
	}

}
