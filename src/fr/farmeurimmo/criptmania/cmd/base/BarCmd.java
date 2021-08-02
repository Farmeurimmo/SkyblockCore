package fr.farmeurimmo.criptmania.cmd.base;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.farmeurimmo.criptmania.Main;

public class BarCmd implements CommandExecutor {

	public static ArrayList<Player> Disable = new ArrayList<Player>();

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(args.length == 1) {
			if(args[0].equalsIgnoreCase("on")) {
				if(Disable.contains(player)) {
					Disable.remove(player);
				}
				player.sendMessage(Main.getInstance().getConfig().getString("bossbar.activated"));
				}
			else if(args[0].equalsIgnoreCase("off")) {
				Disable.add(player);
				player.sendMessage(Main.getInstance().getConfig().getString("bossbar.disabled"));
			}
			else {
				player.sendMessage(Main.getInstance().getConfig().getString("bossbar.invalidargs"));
				return true;
			}
			}
			else {
				player.sendMessage("§cUsage, /bar <on|off>");
				return true;
			}
		}
		return false;
	}

}
