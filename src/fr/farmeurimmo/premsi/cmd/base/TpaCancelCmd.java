package fr.farmeurimmo.premsi.cmd.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import fr.farmeurimmo.premsi.core.Main;

public class TpaCancelCmd implements CommandExecutor, TabCompleter {

	@SuppressWarnings("unlikely-arg-type")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(Main.pending.contains(player)) {
				Main.pending.remove(player);
				player.sendMessage("§§6§lTéléportation §8» §fVous avez §cannulé §fvotre demande de téléportation à "
				+ Main.instance1.getTarget(player.getName()) + ".");
				Main.haverequest.remove(Main.instance1.getTarget(player.getName()));
				Main.instance1.ClearPlayerAndTarget(player.getName());
			} else {
				player.sendMessage("§§6§lTéléportation §8» §fVous ne possédez aucune demande de téléportaiton de votre part.");
			}
		}
		return false;
	}
	
	@Override
	 public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		 ArrayList<String> subcmd = new ArrayList<String>();
	        if (cmd.getName().equalsIgnoreCase("tpacancel")) {
	            if (args.length >= 1) {
	            	subcmd.add("");
	            	Collections.sort(subcmd);
	            }
	        }
			return subcmd;
	 }
}