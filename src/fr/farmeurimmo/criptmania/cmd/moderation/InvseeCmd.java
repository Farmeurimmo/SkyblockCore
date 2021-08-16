package fr.farmeurimmo.criptmania.cmd.moderation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import fr.farmeurimmo.criptmania.utils.SendActionBar;

public class InvseeCmd implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("*")) {
				if(args.length == 1) {
					if(Bukkit.getPlayer(args[0]) != null) {
						if(Bukkit.getPlayer(args[0]).isOnline()) {
							Player p = Bukkit.getPlayer(args[0]);
							Inventory inv = Bukkit.createInventory(null, 45, p.getName());
							inv.setContents(p.getInventory().getContents());
							player.openInventory(inv);
						} else {
							SendActionBar.SendActionBarMsg(player, "§cCe joueur n'est pas en ligne !");
						}
					} else {
						SendActionBar.SendActionBarMsg(player, "§cCe joueur n'existe pas !");
					}
				} else {
					SendActionBar.SendActionBarMsg(player, "§c/invsee <Joueur>");
				}
			}
			
		}
		return false;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args){
		ArrayList<String> subcmd = new ArrayList<String>();
		if(cmd.getName().equalsIgnoreCase("invsee")) {
			if(args.length == 1) {
				for(Player player : Bukkit.getOnlinePlayers()) {
					subcmd.add(player.getName());
				}
				Collections.sort(subcmd);
			} else if(args.length >= 2) {
				subcmd.add("");
				Collections.sort(subcmd);
			}
		}
		return subcmd;
	}

}
