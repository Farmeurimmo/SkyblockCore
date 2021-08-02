package fr.farmeurimmo.criptmania.featherfly;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class FeatherFlyCmd implements CommandExecutor {

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
		}
		if(args[0].equalsIgnoreCase("help")) {
			sender.sendMessage("§7/featherfly give <pseudo> <temp>+sec/min/heur");
		}
		}
		return false;
	}

}
