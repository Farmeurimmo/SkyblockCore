package fr.farmeurimmo.criptmania.cmd.moderation;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.farmeurimmo.criptmania.gui.SanctionSetGui;
import fr.farmeurimmo.criptmania.utils.SendActionBar;

public class SSCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("*")) {
				if(args.length == 0) {
					SendActionBar.SendActionBarMsg(player, "§c/ss <Joueur>");
				} else if(args.length == 1) {
				SanctionSetGui.MakeSSGui(player, Bukkit.getPlayer(args[0]));
				} else {
					SendActionBar.SendActionBarMsg(player, "§c/ss <Joueur>");
				}
			}
		}
		
		return false;
	}

}
