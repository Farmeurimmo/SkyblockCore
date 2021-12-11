package fr.farmeurimmo.verymc.featherfly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class FeatherFlyCmd implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		
		if(sender.hasPermission("featherfly.give")) {
		if(args.length == 3) {
			if(args[0].equalsIgnoreCase("give")) {
			if(Bukkit.getPlayer(args[1]).isOnline() == true) {
				String sample = args[2];
				char[] chars = sample.toCharArray();
			      StringBuilder sb = new StringBuilder();
			      for(char c : chars){
			         if(Character.isDigit(c)){
			            sb.append(c);
			         }
			      }
			      if(!sb.isEmpty() && sb.length() < 9) {
						if(args[2].contains("sec") || args[2].contains("min") || args[2].contains("heur")) {
				ItemStack custom6 = new ItemStack(Material.FEATHER, 1);
				ItemMeta customf = custom6.getItemMeta();
				customf.setUnbreakable(true);
				customf.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
				customf.setDisplayName("§eFly de " + sb + " " + args[2].replace(sb, "").replace("min", "minutes").replace("sec", "secondes").replace("heur", "heures"));
				customf.setLore(Arrays.asList("§7Plûme qui vous donne la possibilité de", "§7voler pendant un temps définit."));
				custom6.setItemMeta(customf);
				Player player = Bukkit.getPlayer(args[1]);
				player.getInventory().addItem(custom6);
				player.sendMessage("§eVous avez reçu une plûme de fly de " + sb + " " + args[2].replace(sb, "").replace("min", "minutes").replace("sec", "secondes").replace("heur", "heures"));
						}
					}
			}
			}
		}
		else {
			sender.sendMessage("§cErreur, faites /featherfly help pour voir les commandes !");
			if(args[0].equalsIgnoreCase("help")) {
				sender.sendMessage("§7/featherfly give <pseudo> <temp>+sec/min/heur");
			}
		}
		}
		return false;
	}
	@Override
	 public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		 ArrayList<String> subcmd = new ArrayList<String>();
	        if (cmd.getName().equalsIgnoreCase("featherfly")) {
	            if (args.length == 1) {
	            	subcmd.add("give");
	            } else if (args.length == 2){
	            	for(Player player : Bukkit.getOnlinePlayers()) {
	            		subcmd.add(player.getName());
	            	}
	            } else if(args.length == 3) {
	            	subcmd.add("10sec");
	            	subcmd.add("10min");
	            	subcmd.add("1heur");
	            	subcmd.add("10heur");
	            	subcmd.add("24heur");
	            }
	            Collections.sort(subcmd);
	        }
			return subcmd;
	 }

}
