package fr.farmeurimmo.criptmania.items;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemLegCmd implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		
		if(sender.hasPermission("itemleg.give")) {
		if(args.length == 3) {
			if(args[0].equalsIgnoreCase("give")) {
			if(Bukkit.getPlayer(args[1]).isOnline() == true) {
				if(args[2].equalsIgnoreCase("pioche")) {
				ItemStack custom6 = new ItemStack(Material.NETHERITE_PICKAXE, 1);
				ItemMeta customf = custom6.getItemMeta();
				customf.setDisplayName("�6Pioche l�gendaire");
				customf.addEnchant(Enchantment.DIG_SPEED, 7, true);
				customf.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 5, true);
				customf.addEnchant(Enchantment.DURABILITY, 5, true);
				customf.addEnchant(Enchantment.MENDING, 1, true);
				custom6.setItemMeta(customf);
				Player player = Bukkit.getPlayer(args[1]);
				player.getInventory().addItem(custom6);
				player.sendMessage("�eVous avez re�u une pioche l�gendaire");
				
				} else if(args[2].equalsIgnoreCase("hache")) {
					ItemStack custom6 = new ItemStack(Material.NETHERITE_AXE, 1);
					ItemMeta customf = custom6.getItemMeta();
					customf.setDisplayName("�6Hache l�gendaire");
					customf.addEnchant(Enchantment.DIG_SPEED, 7, true);
					customf.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 5, true);
					customf.addEnchant(Enchantment.DURABILITY, 5, true);
					customf.addEnchant(Enchantment.MENDING, 1, true);
					custom6.setItemMeta(customf);
					Player player = Bukkit.getPlayer(args[1]);
					player.getInventory().addItem(custom6);
					player.sendMessage("�eVous avez re�u une hache l�gendaire");
					
					} else if(args[2].equalsIgnoreCase("pelle")) {
						ItemStack custom6 = new ItemStack(Material.NETHERITE_SHOVEL, 1);
						ItemMeta customf = custom6.getItemMeta();
						customf.setDisplayName("�6Pelle l�gendaire");
						customf.addEnchant(Enchantment.DIG_SPEED, 7, true);
						customf.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 5, true);
						customf.addEnchant(Enchantment.DURABILITY, 5, true);
						customf.addEnchant(Enchantment.MENDING, 1, true);
						custom6.setItemMeta(customf);
						Player player = Bukkit.getPlayer(args[1]);
						player.getInventory().addItem(custom6);
						player.sendMessage("�eVous avez re�u une pelle l�gendaire");
						
						} else if(args[2].equalsIgnoreCase("�p�e")) {
							ItemStack custom6 = new ItemStack(Material.NETHERITE_SWORD, 1);
							ItemMeta customf = custom6.getItemMeta();
							customf.setDisplayName("�6Ep�e l�gendaire");
							customf.addEnchant(Enchantment.DAMAGE_ALL, 7, true);
							customf.addEnchant(Enchantment.DAMAGE_UNDEAD, 7, true);
							customf.addEnchant(Enchantment.DAMAGE_ARTHROPODS, 7, true);
							customf.addEnchant(Enchantment.LOOT_BONUS_MOBS, 5, true);
							customf.addEnchant(Enchantment.DURABILITY, 5, true);
							customf.addEnchant(Enchantment.MENDING, 1, true);
							custom6.setItemMeta(customf);
							Player player = Bukkit.getPlayer(args[1]);
							player.getInventory().addItem(custom6);
							player.sendMessage("�eVous avez re�u une �p�e l�gendaire");
							} else {
								sender.sendMessage("�6Arg disponnible: pioche, hache, pelle, �p�e");
							}
					}
			}
			}
		}
		else {
			sender.sendMessage("�cErreur, faites /itemleg help pour voir les commandes !");
		}
		return false;
	}
	@Override
	 public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		 ArrayList<String> subcmd = new ArrayList<String>();
	        if (cmd.getName().equalsIgnoreCase("itemleg")) {
	        	if(sender.hasPermission("*")) {
	            if (args.length == 1){
	            	subcmd.add("give");
	            } else if(args.length == 2) {
	            	for(Player player : Bukkit.getOnlinePlayers()) {
	            		subcmd.add(player.getName());
	            	}
	            } else if(args.length == 3){
	        		subcmd.add("pioche");
	        		subcmd.add("hache");
	        		subcmd.add("�p�e");
	        		subcmd.add("pelle");
	        	} else if(args.length >= 4){
	        		subcmd.add("");
	        	}
	        }
	     }
	        Collections.sort(subcmd);
			return subcmd;
	 }
}
