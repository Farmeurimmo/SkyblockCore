package fr.farmeurimmo.criptmania.items;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemLegCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		
		if(sender.hasPermission("itemleg.give")) {
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
			      if(!sb.isEmpty() && sb.length() < 2) {
				ItemStack custom6 = new ItemStack(Material.DIAMOND_PICKAXE, 1);
				ItemMeta customf = custom6.getItemMeta();
				customf.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
				customf.setDisplayName("§6Pioche légendaire §8| §eTier §c" + sb);
				Random rand = new Random();
				int Tier = Integer.parseInt(sb.toString());
				int n1 = 0;
				int n2 = 0;
				int n3 = 0;
				if(Tier == 1) {
				n1 = rand.nextInt(3);
				n2 = rand.nextInt(2);
				n1 = n1 + 5;
				n2 = n2 + 5;
				n3 = 5;
				}
				if(Tier == 2) {
					n1 = rand.nextInt(4);
					n2 = rand.nextInt(3);
					n1 = n1 + 6;
					n2 = n2 + 6;
					n3 = 10;
				}
				if(Tier == 3) {
					n1 = rand.nextInt(4);
					n2 = rand.nextInt(3);
					n1 = n1 + 7;
					n2 = n2 + 7;
					n3 = 15;
				}
				if(Tier == 4) {
					n1 = rand.nextInt(4);
					n2 = rand.nextInt(3);
					n1 = n1 + 8;
					n2 = n2 + 8;
					n3 = 20;
				}
				customf.addEnchant(Enchantment.DIG_SPEED, n1, true);
				customf.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, n2, true);
				customf.addEnchant(Enchantment.DURABILITY, n3, true);
				custom6.setItemMeta(customf);
				Player player = Bukkit.getPlayer(args[1]);
				player.getInventory().addItem(custom6);
				player.sendMessage("§eVous avez reçu une pioche légendaire de tier §c" + Tier);
					}
			}
			}
		}
		else {
			sender.sendMessage("§cErreur, faites /itemleg help pour voir les commandes !");
		}
		if(args[0].equalsIgnoreCase("help")) {
			sender.sendMessage("§7/itemleg give <pseudo> <tier>(1 a 4)");
		}
		}
		return false;
	}

}
