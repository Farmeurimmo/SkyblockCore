package fr.farmeurimmo.criptmania.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.farmeurimmo.criptmania.atout.BuyAtoutGui;
import net.tnemc.core.TNE;
import net.tnemc.core.common.api.TNEAPI;

public class Farm2WinGui implements Listener {
	
	static boolean hatcmd = true;
	static boolean flycmd = true;
	static boolean feedcmd = true;
	static boolean craftcmd = true;
	static boolean isrenamecmd = true;
	static boolean sellallcmd = true;
	static boolean enchantementcmd = true;
	static boolean legendeachat = true;
	static boolean dieueachat = true;
	static boolean zeusachat = true;
	public static int hatprix = 500000;
	public static int sellallprix = 10000000;
	public static int flyprix = 10000000;
	public static int craftprix = 750000;
	public static int enchantementprix = 500000;
	public static int isrenameprix = 1250000;
	public static int feedprix = 750000;
	public static int legendeprix = 2500000;
	public static int dieuprix = 5000000;
	public static int zeusprix = 10000000;
	
	@EventHandler
	public static void OnInventoryClic(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack current = event.getCurrentItem();
		TNEAPI ecoAPI = TNE.instance().api();
		
		if(current == null) {
			return;
		}
		if(current.getType() == null) {
			return;
		}
		if(event.getView().getTitle().equalsIgnoreCase("§6Boutique Farm2Win")) {
			event.setCancelled(true);
			if(current.getType() == Material.EMERALD_BLOCK) {
				player.closeInventory();
				MakeRanksGui.MakeRankGui(player);
			}
			if(current.getType() == Material.ARROW) {
				player.closeInventory();
				MenuGui.OpenMainMenu(player);
			}
			if(current.getType() == Material.POTION) {
				BuyAtoutGui.MakeBuyAtoutGui(player);
			}
			if(current.getType() == Material.COMMAND_BLOCK_MINECART) {
				player.closeInventory();
				MakeCommandsGui.MakeCommandGui(player);
			}
		}
		if(event.getView().getTitle().equalsIgnoreCase("§6Boutique des grades Farm2Win")) {
			event.setCancelled(true);
			if(current.getType() == Material.ARROW) {
				player.closeInventory();
				MainBoutiqueGUI(player);
			}
			if(current.getType() == Material.NETHERITE_BLOCK) {
				if(!player.hasPermission("legende")){
					if(ecoAPI.getAccount(player.getName()).getHoldings().intValue() >= legendeprix) {
					player.sendMessage("§6Vérification de la disponibilité du grade...");
					current.setType(Material.BARRIER);
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
					     public void run() {
					    	 if(legendeachat == false) {
					    	 player.sendMessage("§cErreur, achat indisponible !");
					    	 } else {
					    		 BuyRanks.BuyRank("legende", player);
					    	 }
					    	 current.setType(Material.NETHERITE_BLOCK);
					     }
					}, 60);
				}
					else {
						player.sendMessage("§cFonds insuffisants !");
						current.setType(Material.BARRIER);
						Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
						     public void run() {
						    	 current.setType(Material.NETHERITE_BLOCK);
						     }
						}, 60);
					}
				}
			}
			if(current.getType() == Material.BEACON) {
				if(!player.hasPermission("dieu")){
					if(ecoAPI.getAccount(player.getName()).getHoldings().intValue() >= dieuprix) {
						if(player.hasPermission("legende")) {
					player.sendMessage("§6Vérification de la disponibilité du grade...");
					current.setType(Material.BARRIER);
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
					     public void run() {
					    	 if(legendeachat == false) {
					    	 player.sendMessage("§cErreur, achat indisponible !");
					    	 } else {
					    		 if(player.hasPermission("legende")) {
					    		 BuyRanks.BuyRank("dieu", player);
					    		 } else {
					    			 player.sendMessage("§cErreur, vous devez posséder tous les grades précédents !");
					    		 }
					    	 }
					    	 current.setType(Material.BEACON);
					     }
					}, 60);
				} else {
	    			 player.sendMessage("§cErreur, vous devez posséder tous les grades précédents !");
	    			 current.setType(Material.BARRIER);
						Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
						     public void run() {
						    	 current.setType(Material.BEACON);
						     }
						}, 60);
	    		 }
					}
					else {
						player.sendMessage("§cFonds insuffisants !");
						current.setType(Material.BARRIER);
						Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
						     public void run() {
						    	 current.setType(Material.BEACON);
						     }
						}, 60);
					}
				}
			}
			if(current.getType() == Material.WITHER_SKELETON_SKULL) {
				if(!player.hasPermission("zeus")){
					if(ecoAPI.getAccount(player.getName()).getHoldings().intValue() >= zeusprix) {
						if(player.hasPermission("legende") && player.hasPermission("dieu")) {
					player.sendMessage("§6Vérification de la disponibilité du grade...");
					current.setType(Material.BARRIER);
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
					     public void run() {
					    	 if(legendeachat == false) {
					    	 player.sendMessage("§cErreur, achat indisponible !");
					    	 } else {
					    		 BuyRanks.BuyRank("zeus", player);
					    	 }
					    	 current.setType(Material.WITHER_SKELETON_SKULL);
					     }
					}, 60);
				} else {
	    			 player.sendMessage("§cErreur, vous devez posséder tous les grades précédents !");
	    			 current.setType(Material.BARRIER);
						Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
						     public void run() {
						    	 current.setType(Material.WITHER_SKELETON_SKULL);
						     }
						}, 60);
	    		 }
					}
					else {
						player.sendMessage("§cFonds insuffisants !");
						current.setType(Material.BARRIER);
						Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
						     public void run() {
						    	 current.setType(Material.WITHER_SKELETON_SKULL);
						     }
						}, 60);
					}
				}
			}
		}
		if(event.getView().getTitle().equalsIgnoreCase("§6Boutique des commandes Farm2Win")) {
			
			event.setCancelled(true);
			
			if(current.getType() == Material.ARROW) {
				player.closeInventory();
				MainBoutiqueGUI(player);
			}
			if(current.getType() == Material.LEATHER_HELMET) {
				if(!player.hasPermission("hat")){
					if(ecoAPI.getAccount(player.getName()).getHoldings().intValue() >= hatprix) {
					player.sendMessage("§6Vérification de la disponibilité de la commande...");
					current.setType(Material.BARRIER);
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
					     public void run() {
					    	 if(hatcmd == false) {
					    	 player.sendMessage("§cErreur, achat indisponible !");
					    	 } else {
					    		 BuyCommand.BuyCmd("hat", player);
					    	 }
					    	 current.setType(Material.LEATHER_HELMET);
					     }
					}, 60);
				}
					else {
						player.sendMessage("§cFonds insuffisants !");
						current.setType(Material.BARRIER);
						Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
						     public void run() {
						    	 current.setType(Material.LEATHER_HELMET);
						     }
						}, 60);
					}
				}
			}
			if(current.getType() == Material.OAK_SIGN) {
				if(!player.hasPermission("economyshopgui.sellall")){
					if(ecoAPI.getAccount(player.getName()).getHoldings().intValue() >= sellallprix) {
					player.sendMessage("§6Vérification de la disponibilité de la commande...");
					current.setType(Material.BARRIER);
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
					     public void run() {
					    	 if(hatcmd == false) {
					    	 player.sendMessage("§cErreur, achat indisponible !");
					    	 } else {
					    		 BuyCommand.BuyCmd("sellall", player);
					    	 }
					    	 current.setType(Material.OAK_SIGN);
					     }
					}, 60);
				}
					else {
						player.sendMessage("§cFonds insuffisants !");
						current.setType(Material.BARRIER);
						Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
						     public void run() {
						    	 current.setType(Material.OAK_SIGN);
						     }
						}, 60);
					}
				}
			}
			if(current.getType() == Material.FEATHER) {
				if(!player.hasPermission("fly")){
					if(ecoAPI.getAccount(player.getName()).getHoldings().intValue() >= flyprix) {
					player.sendMessage("§6Vérification de la disponibilité de la commande...");
					current.setType(Material.BARRIER);
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
					     public void run() {
					    	 if(flycmd == false) {
					    	 player.sendMessage("§cErreur, achat indisponible !");
					    	 } else {
					    		 BuyCommand.BuyCmd("fly", player);
					    	 }
					    	 current.setType(Material.FEATHER);
					     }
					}, 60);
				}
					else {
						player.sendMessage("§cFonds insuffisants !");
						current.setType(Material.BARRIER);
						Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
						     public void run() {
						    	 current.setType(Material.FEATHER);
						     }
						}, 60);
					}
				}
			}
			if(current.getType() == Material.COOKED_BEEF) {
				if(!player.hasPermission("feed")){
					if(ecoAPI.getAccount(player.getName()).getHoldings().intValue() >= feedprix) {
					player.sendMessage("§6Vérification de la disponibilité de la commande...");
					current.setType(Material.BARRIER);
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
					     public void run() {
					    	 if(feedcmd == false) {
					    	 player.sendMessage("§cErreur, achat indisponible !");
					    	 } else {
					    		 BuyCommand.BuyCmd("feed", player);
					    	 }
					    	 current.setType(Material.COOKED_BEEF);
					     }
					}, 60);
				}
					else {
						player.sendMessage("§cFonds insuffisants !");
						current.setType(Material.BARRIER);
						Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
						     public void run() {
						    	 current.setType(Material.COOKED_BEEF);
						     }
						}, 60);
					}
				}
			}
			if(current.getType() == Material.CRAFTING_TABLE) {
				if(!player.hasPermission("craft")){
					if(ecoAPI.getAccount(player.getName()).getHoldings().intValue() >= craftprix) {
					player.sendMessage("§6Vérification de la disponibilité de la commande...");
					current.setType(Material.BARRIER);
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
					     public void run() {
					    	 if(craftcmd == false) {
					    	 player.sendMessage("§cErreur, achat indisponible !");
					    	 } else {
					    		 BuyCommand.BuyCmd("craft", player);
					    	 }
					    	 current.setType(Material.CRAFTING_TABLE);
					     }
					}, 60);
				}
					else {
						player.sendMessage("§cFonds insuffisants !");
						current.setType(Material.BARRIER);
						Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
						     public void run() {
						    	 current.setType(Material.CRAFTING_TABLE);
						     }
						}, 60);
					}
				}
			}
			if(current.getType() == Material.NAME_TAG) {
				if(!player.hasPermission("iridiumskyblock.rename")){
					if(ecoAPI.getAccount(player.getName()).getHoldings().intValue() >= isrenameprix) {
					player.sendMessage("§6Vérification de la disponibilité de la commande...");
					current.setType(Material.BARRIER);
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
					     public void run() {
					    	 if(isrenamecmd == false) {
					    	 player.sendMessage("§cErreur, achat indisponible !");
					    	 } else {
					    		 BuyCommand.BuyCmd("isrename", player);
					    	 }
					    	 current.setType(Material.NAME_TAG);
					     }
					}, 60);
				}
					else {
						player.sendMessage("§cFonds insuffisants !");
						current.setType(Material.BARRIER);
						Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
						     public void run() {
						    	 current.setType(Material.NAME_TAG);
						     }
						}, 60);
					}
				}
			}
			if(current.getType() == Material.ENCHANTING_TABLE) {
				if(!player.hasPermission("enchantement")){
					if(ecoAPI.getAccount(player.getName()).getHoldings().intValue() >= enchantementprix) {
					player.sendMessage("§6Vérification de la disponibilité de la commande...");
					current.setType(Material.BARRIER);
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
					     public void run() {
					    	 if(enchantementcmd == false) {
					    	 player.sendMessage("§cErreur, achat indisponible !");
					    	 } else {
					    		 BuyCommand.BuyCmd("enchantement", player);
					    	 }
					    	 current.setType(Material.ENCHANTING_TABLE);
					     }
					}, 60);
				}
					else {
						player.sendMessage("§cFonds insuffisants !");
						current.setType(Material.BARRIER);
						Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
						     public void run() {
						    	 current.setType(Material.ENCHANTING_TABLE);
						     }
						}, 60);
					}
				}
			}
		}
	}
	
	public static void MainBoutiqueGUI(Player player) {
		Inventory inv = Bukkit.createInventory(null, 27, "§6Boutique Farm2Win");
		
		ItemStack custom1 = new ItemStack(Material.EMERALD_BLOCK, 1);
		ItemMeta customa = custom1.getItemMeta();
		customa.setDisplayName("§6Boutique des grades §8| §7(clic gauche)");
		custom1.setItemMeta(customa);
		inv.setItem(10, custom1);
		
		ItemStack custom3 = new ItemStack(Material.CHEST, 1);
		ItemMeta customc = custom3.getItemMeta();
		customc.setDisplayName("§6Boutique des items §8| §7(clic gauche)");
		custom3.setItemMeta(customc);
		inv.setItem(14, custom3);
		
		ItemStack custom2 = new ItemStack(Material.COMMAND_BLOCK_MINECART, 1);
		ItemMeta customb = custom2.getItemMeta();
		customb.setDisplayName("§6Boutique des commandes §8| §7(clic gauche)");
		custom2.setItemMeta(customb);
		inv.setItem(12, custom2);
		
		ItemStack custom4 = new ItemStack(Material.POTION, 1);
		ItemMeta customd = custom4.getItemMeta();
		customd.setDisplayName("§6Boutique des atouts §8| §7(clic gauche)");
		custom4.setItemMeta(customd);
		inv.setItem(16, custom4);
		
		ItemStack custom9 = new ItemStack(Material.ARROW, 1);
		ItemMeta customh = custom9.getItemMeta();
		customh.setDisplayName("§6Retour §8| §7(clic gauche)");
		custom9.setItemMeta(customh);
		inv.setItem(26, custom9);
		
		ItemStack custom8 = new ItemStack(Material.WHITE_STAINED_GLASS_PANE, 1);
		ItemMeta meta8 = custom8.getItemMeta();
		meta8.setDisplayName("§6");
		custom8.setItemMeta(meta8);
		for (int i = 0; i < inv.getSize(); i++) {
            if (inv.getItem(i) == null || inv.getItem(i).getType().equals(Material.AIR)) {
            	inv.setItem(i, custom8);
            }
        }
		
		
		player.openInventory(inv);
	}
}
