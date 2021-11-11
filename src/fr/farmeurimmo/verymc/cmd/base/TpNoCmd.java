package fr.farmeurimmo.verymc.cmd.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import fr.farmeurimmo.verymc.core.Main;

public class TpNoCmd implements CommandExecutor, TabCompleter{

	@SuppressWarnings("unlikely-arg-type")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(Main.haverequest.contains(player)) {
				Main.haverequest.remove(player);
				for(Player p : Bukkit.getOnlinePlayers()) {
					if(Main.instance1.getTarget(p.getName()) != null) {
					if(Main.instance1.getTarget(p.getName()).equalsIgnoreCase(player.getName())) {
						Main.pending.remove(Main.instance1.getTarget(p.getName()));
						Main.instance1.ClearPlayerAndTarget(p.getName());
						p.sendMessage("§6§lTéléportation §8» §a" + player.getName() + " §fa refusé votre demande de téléportation.");
						player.sendMessage("§6§lTéléportation §8» §fLa demande de téléportation de " + p.getName() + " §fa été refusé avec succès.");
					}
					} else {
						p.sendMessage("§6§lTéléportation §8» §fVous ne possédez aucune demande de téléportation.");
					}
				}
			} else {
				player.sendMessage("§6§lTéléportation §8» §fVous ne possédez aucune demande de téléportation.");
			}
		}
		return false;
	}
	@Override
	 public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		 ArrayList<String> subcmd = new ArrayList<String>();
	        if (cmd.getName().equalsIgnoreCase("tpno")) {
	            if (args.length == 1) {
	            	subcmd.add("");
	            	Collections.sort(subcmd);
	            }
	        }
			return subcmd;
	 }
}