package fr.farmeurimmo.verymc.shopgui;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.farmeurimmo.verymc.eco.EcoAccountsManager;

public class SellAllCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(!(sender instanceof Player)) {
			return true;
		}
		Player player = (Player) sender;
		double total = 0;
		for(ItemStack a : player.getInventory().getStorageContents()) {
			if(a == null) continue;
			if(a.getType() == null) continue;
			if(BuyShopItem.isSellable(a)==true) {
				int amount = BuyShopItem.GetAmountInInv(a, player);
				Double price = BuyShopItem.pricessell.get(a);
				price = amount*price;
				BuyShopItem.removeItems(player.getInventory(), a.getType(), amount);
				total+=price;
			}
		}
		if(total > 0) {
		EcoAccountsManager.AddFounds(player.getName(), total);
		player.sendMessage("§6§lShop §8» §fVous avez vendu tout les items vendables de votre inventaire pour §a"+total+"$.");
		} else {
			player.sendMessage("§6§lShop §8» §fVous n'avez rien à vendre dans votre inventaire.");
		}
		
		return false;
	}

}
