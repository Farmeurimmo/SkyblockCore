package fr.farmeurimmo.criptmania.cmd.base;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.farmeurimmo.criptmania.utils.SendActionBar;

public class HatCmd implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("hat")) {
				ItemStack aaa = player.getItemInHand();
				if(aaa.getType() != null && aaa.getType() != Material.AIR) {
					if(player.getInventory().getHelmet() != null) {
						ItemStack bbb = player.getInventory().getHelmet();
						player.getInventory().setHelmet(aaa);
						aaa.setAmount(0);
						player.setItemInHand(bbb);
					} else {
						player.getInventory().setHelmet(aaa);
					    aaa.setAmount(0);
					}
					SendActionBar.SendActionBarMsg(player, "§aItem en main équipé !");	
			}
			}
			else {
				SendActionBar.SendActionBarMsg(player, "§cPermissions insuffisantes !");
			}
		}
		return false;
	}

}
