package fr.farmeurimmo.criptmania.cmd.base;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.farmeurimmo.criptmania.utils.SendActionBar;

public class BuildCmd implements CommandExecutor {
	
	public static ArrayList<Player> Build = new ArrayList<Player>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("build")) {
			if(sender instanceof Player) {
				final Player player = (Player) sender;
				if(player.hasPermission("build")) {
					if(Build.contains(player)) {
						Build.remove(player);
						SendActionBar.SendActionBarMsg(player, "§6Mode buildeurs désactivé !");
					}
					else {
						Build.add(player);
						SendActionBar.SendActionBarMsg(player, "§6Mode buildeurs activé !");
					}
				}
				else {
					SendActionBar.SendActionBarMsg(player, "§cPermissions insuffisantes !");
				}
			}
		}
		return false;
	}

}
