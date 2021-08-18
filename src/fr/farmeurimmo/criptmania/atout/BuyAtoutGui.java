package fr.farmeurimmo.criptmania.atout;

import java.math.BigDecimal;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import fr.farmeurimmo.criptmania.Main;
import fr.farmeurimmo.criptmania.gui.Farm2WinGui;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.tnemc.core.TNE;
import net.tnemc.core.common.api.TNEAPI;

public class BuyAtoutGui implements Listener {
	
	static int haste = 600000;
	static int speed = 400000;
	static int jumpboost = 250000;
	
	@SuppressWarnings("deprecation")
	public static void MakeBuyAtoutGui(Player player) {
		User user = LuckPermsProvider.get().getUserManager().getUser(player.getName());
		String Grade = user.getCachedData().getMetaData().getPrefix().replace("&", "§");
		TNEAPI ecoAPI = TNE.instance().api();
		
		
        Inventory inv = Bukkit.createInventory(null, 27, "§6Boutique des atouts");
		
		ItemStack custom2 = new ItemStack(Material.PLAYER_HEAD, 1);
		SkullMeta customb = (SkullMeta) custom2.getItemMeta();
		customb.setOwner(player.getName());
		customb.setDisplayName("§7" + player.getName());
		customb.setLore(Arrays.asList("§7Grade: " + Grade, "§7Argent: " + ecoAPI.getAccount(player.getName()).getHoldings().intValue()));
		custom2.setItemMeta(customb);
		inv.setItem(22, custom2);
		
		if(Main.instance1.getData().getInt("Joueurs."+player.getName()+".Atout.1.Level") == 0) {
			ItemStack custom1 = new ItemStack(Material.GOLDEN_PICKAXE, 1);
			ItemMeta customa = custom1.getItemMeta();
			customa.setDisplayName("§eHaste 2");
			customa.setLore(Arrays.asList("§7","§6Prix: " + haste + "$"));
			custom1.setItemMeta(customa);
			inv.setItem(10, custom1);
	        } else if (Main.instance1.getData().getInt("Joueurs."+player.getName()+".Atout.1.Level") == 2) {
	        	ItemStack custom10 = new ItemStack(Material.BEDROCK, 1);
	    		ItemMeta customi = custom10.getItemMeta();
	    		customi.setDisplayName("§eHaste 2 §c(Déjà possédé)");
	    		custom10.setItemMeta(customi);
	    		inv.setItem(10, custom10);
	        }
		
		
		if(Main.instance1.getData().getInt("Joueurs."+player.getName()+".Atout.2.Level") == 0) {
			ItemStack custom1 = new ItemStack(Material.SUGAR, 1);
			ItemMeta customa = custom1.getItemMeta();
			customa.setDisplayName("§eSpeed 2");
			customa.setLore(Arrays.asList("§7","§6Prix: " + speed + "$"));
			custom1.setItemMeta(customa);
			inv.setItem(12, custom1);
	        } else if (Main.instance1.getData().getInt("Joueurs."+player.getName()+".Atout.2.Level") == 2) {
	        	ItemStack custom10 = new ItemStack(Material.BEDROCK, 1);
	    		ItemMeta customi = custom10.getItemMeta();
	    		customi.setDisplayName("§eSpeed 2 §c(Déjà possédé)");
	    		custom10.setItemMeta(customi);
	    		inv.setItem(12, custom10);
	        }
		
		
		if(Main.instance1.getData().getInt("Joueurs."+player.getName()+".Atout.3.Level") == 0) {
			ItemStack custom1 = new ItemStack(Material.RABBIT_FOOT, 1);
			ItemMeta customa = custom1.getItemMeta();
			customa.setDisplayName("§eJumpboost 3");
			customa.setLore(Arrays.asList("§7","§6Prix: " + jumpboost + "$"));
			custom1.setItemMeta(customa);
			inv.setItem(14, custom1);
	        } else if (Main.instance1.getData().getInt("Joueurs."+player.getName()+".Atout.3.Level") == 3) {
	        	ItemStack custom10 = new ItemStack(Material.BEDROCK, 1);
	    		ItemMeta customi = custom10.getItemMeta();
	    		customi.setDisplayName("§eJumpboost 3 §c(Déjà possédé)");
	    		custom10.setItemMeta(customi);
	    		inv.setItem(14, custom10);
	        }
		
		ItemStack custom8 = new ItemStack(Material.ARROW, 1);
		ItemMeta customh = custom8.getItemMeta();
		customh.setDisplayName("§6Retour §8| §7(clic gauche)");
		custom8.setItemMeta(customh);
		inv.setItem(26, custom8);
		
		ItemStack custom9 = new ItemStack(Material.WHITE_STAINED_GLASS_PANE, 1);
		ItemMeta meta9 = custom9.getItemMeta();
		meta9.setDisplayName("§6");
		custom9.setItemMeta(meta9);
		
		for (int i = 0; i < inv.getSize(); i++) {
            if (inv.getItem(i) == null || inv.getItem(i).getType().equals(Material.AIR)) {
            	inv.setItem(i, custom9);
            }
        }
		
		player.openInventory(inv);
	}
	@EventHandler
	public void InventoryClickEvent(InventoryClickEvent e) {
		Player player = (Player) e.getWhoClicked();
		ItemStack current = e.getCurrentItem();
		if(current == null) {
			return;
		}
		if(e.getView().getTitle().equalsIgnoreCase("§6Boutique des atouts")) {
			e.setCancelled(true);
			if(current.getType() == Material.ARROW) {
				Farm2WinGui.MainBoutiqueGUI(player);
			}
			if(current.getType() == Material.GOLDEN_PICKAXE) {
				BuyAtout(1, player);
			}
			if(current.getType() == Material.SUGAR) {
				BuyAtout(2, player);
			}
			if(current.getType() == Material.RABBIT_FOOT) {
				BuyAtout(3, player);
			}
		}
	}
	public static void BuyAtout(int effect, Player player) {
	    TNEAPI ecoAPI = TNE.instance().api();
	    int money = ecoAPI.getAccount(player.getName()).getHoldings().intValue();
	    
		if(effect == 1) {
			if(money >= haste);
			ecoAPI.getAccount(player.getName()).removeHoldings(new BigDecimal(haste));
			Main.instance1.getData().set("Joueurs."+player.getName()+".Atout.1.Active", true);
			Main.instance1.getData().set("Joueurs."+player.getName()+".Atout.1.Level", 2);
			Main.instance1.saveData();
			player.sendMessage("§6Vous avez reçu l'accès à l'atout haste 2 !");
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
			     public void run() {
					 MakeBuyAtoutGui(player);
			     }
			}, 2);
		}
		if(effect == 2) {
			if(money >= speed);
			ecoAPI.getAccount(player.getName()).removeHoldings(new BigDecimal(speed));
			Main.instance1.getData().set("Joueurs."+player.getName()+".Atout.2.Active", true);
			Main.instance1.getData().set("Joueurs."+player.getName()+".Atout.2.Level", 2);
			Main.instance1.saveData();
			player.sendMessage("§6Vous avez reçu l'accès à l'atout speed 2 !");
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
			     public void run() {
					 MakeBuyAtoutGui(player);
			     }
			}, 2);
		}
		if(effect == 3) {
			if(money >= jumpboost);
			ecoAPI.getAccount(player.getName()).removeHoldings(new BigDecimal(jumpboost));
			Main.instance1.getData().set("Joueurs."+player.getName()+".Atout.3.Active", true);
			Main.instance1.getData().set("Joueurs."+player.getName()+".Atout.3.Level", 3);
			Main.instance1.saveData();
			player.sendMessage("§6Vous avez reçu l'accès à l'atout Jumpboost 3 !");
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
			     public void run() {
					 MakeBuyAtoutGui(player);
			     }
			}, 2);
		}
	}
}
