package fr.farmeurimmo.criptmania.cmd.base;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.farmeurimmo.criptmania.gui.WikiGui;

public class WikiCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("wiki")) {
			if(sender instanceof Player) {
				Player player = (Player) sender;
				WikiGui.OpenWikiMainGui(player);
			}
		}
		return false;
	}

}
