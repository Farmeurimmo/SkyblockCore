package fr.farmeurimmo.criptmania.cmd.base;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.farmeurimmo.criptmania.utils.SendActionBar;

public class EnchantementCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(sender instanceof Player){
			Player player = (Player) sender;
			if(player.hasPermission("enchantement")) {
				player.openEnchanting(new Location(Bukkit.getServer().getWorld("IlesSkyblock"), -3, 95, -1), true);
			}
			else {
				SendActionBar.SendActionBarMsg(player, "§cPermissions insuffisantes !");
			}
		}
		
		return false;
	}

}
