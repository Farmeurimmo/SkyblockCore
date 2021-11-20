package fr.farmeurimmo.verymc.shopgui;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.farmeurimmo.verymc.core.Main;
import fr.farmeurimmo.verymc.eco.EcoAccountsManager;

public class BuyShopItem {
	
	public static HashMap <ItemStack, Float> pricesbuy = new HashMap < > ();
	public static HashMap <ItemStack, Float> pricessell = new HashMap < > ();
	
	public static void GenPriceShopStartup() {
		for(String bb : Main.instance1.getConfig().getConfigurationSection("Shops").getKeys(false)) {
		for(String aa : Main.instance1.getConfig().getConfigurationSection("Shops."+bb).getKeys(false)) {
			double a = (float) Main.instance1.getConfig().getDouble("Shops."+bb+"."+aa+".buy");
			double c = (float) Main.instance1.getConfig().getDouble("Shops."+bb+"."+aa+".sell");
			if(Material.valueOf(Main.instance1.getConfig().getString("Shops."+bb+"."+aa+".material")) == null) continue;
			ItemStack b = new ItemStack(Material.valueOf(Main.instance1.getConfig().getString("Shops."+bb+"."+aa+".material")));
			pricesbuy.put(b, (float) a);
			pricessell.put(b, (float) c);
		}
		}
	}
	public static boolean isBuyable(ItemStack a) {
		boolean so = true;
		if(pricessell.get(a) == null || pricesbuy.get(a) == -1) {
			so = false;
		}
		return so;	
	}
	public static boolean isSellable(ItemStack a) {
		boolean so = true;
		if(pricessell.get(a) == null || pricessell.get(a) == -1) {
			so = false;
		}
		return so;	
	}
	public static void removeItems(Inventory inventory, Material type, int amount) {
        if (amount <= 0) return;
        int size = inventory.getSize();
        for (int slot = 0; slot < size; slot++) {
            ItemStack is = inventory.getItem(slot);
            if (is == null) continue;
            if (type == is.getType()) {
                int newAmount = is.getAmount() - amount;
                if (newAmount > 0) {
                    is.setAmount(newAmount);
                    break;
                } else {
                    inventory.clear(slot);
                    amount = -newAmount;
                    if (amount == 0) break;
                }
            }
        }
    }
	public static int GetAmountInInv(ItemStack aa, Player player) {
		int total = 0;
		
		int size = player.getInventory().getSize();
        for (int slot = 0; slot < size; slot++) {
        	ItemStack is = player.getInventory().getItem(slot);
        	if(is == null) continue;
        	if(aa.getType() == is.getType()) {
        		total+= is.getAmount();
        	}
        }
		
		return total;
	}
	public static int GetAmountToFillInInv(ItemStack aa, Player player) {
		int total = 0;
		
		int size = player.getInventory().getSize();
        for (int slot = 0; slot < size; slot++) {
        	ItemStack is = player.getInventory().getItem(slot);
        	if(is == null) {
        		total+=64;
        		continue;
        	} else if(is.getType() == aa.getType()) {
        		total+=64-is.getAmount();
        		continue;
        	}
        }
        	if(player.getInventory().getHelmet() == null) {
        		total -= 64;
        	}
        	if(player.getInventory().getChestplate() == null) {
        		total -= 64;
        	}
        	if(player.getInventory().getLeggings() == null) {
        		total -= 64;
        	}
        	if(player.getInventory().getBoots() == null) {
        		total -= 64;
        	}
        	if(player.getInventory().getItemInOffHand().getType() == Material.AIR) {
        		total -= 64;
        	}
		
		return total;
	}
	public static void BuyOSellItemNonStack(ItemStack a, Player player, boolean buy, double price, int amount) {
		if(buy == true) {
			if(EcoAccountsManager.CheckForFounds(player, (double) (price*amount)) == true) {
				ItemMeta tempmeta = a.getItemMeta();
				tempmeta.setLore(null);
				a.setItemMeta(tempmeta);
				player.closeInventory();
				a.setAmount(amount);
				EcoAccountsManager.RemoveFounds(player.getName(), price*amount);
				int amountininv = GetAmountToFillInInv(new ItemStack(a.getType()), player);
				ItemStack od = new ItemStack(a.getType());
				if(amountininv <= amount) {
					int reste = amount - amountininv;
					od.setAmount(amountininv);
					player.getInventory().addItem(od);
					od.setAmount(1);
					while (reste > 0){
					    player.getWorld().dropItem(player.getLocation(), od);
					    reste -= 1;
					}
				} else {
				player.getInventory().addItem(a);
				}
			} else {
				double loa=price*amount - EcoAccountsManager.GetMoney(player.getName());
				player.sendMessage("§6§lShop §8» §fIl vous manque §6"+loa+"$§f.");
			}
		} else {
			if(player.getInventory().contains(a.getType(), amount)) {
				Double profit = (double) (pricessell.get(new ItemStack(Material.valueOf(a.getType().toString())))*amount);
				player.closeInventory();
				removeItems(player.getInventory(), a.getType(), amount);
				player.sendMessage("§6§lShop §8» §fVous avez vendu §ax"+amount+" "+a.getType().toString()+"§f pour §6"+profit+"$§f.");
				EcoAccountsManager.AddFounds(player.getName(), profit);
			} else {
				player.sendMessage("§6§lShop §8» §fVous avez besoin de plus de "+a.getType().toString()+".");
			}
		}
	}
}
