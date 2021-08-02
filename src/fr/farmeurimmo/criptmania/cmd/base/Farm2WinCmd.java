package fr.farmeurimmo.criptmania.cmd.base;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.farmeurimmo.criptmania.gui.Farm2WinGui;

public class Farm2WinCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
            Player player = (Player) sender;
            Farm2WinGui.MainBoutiqueGUI(player);
        }
		return false;
	}

}
