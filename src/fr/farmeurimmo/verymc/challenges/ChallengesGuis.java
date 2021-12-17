package fr.farmeurimmo.verymc.challenges;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.iridium.iridiumskyblock.api.IridiumSkyblockAPI;

import org.bukkit.event.inventory.InventoryClickEvent;

import fr.farmeurimmo.verymc.core.Main;
import fr.farmeurimmo.verymc.crates.CratesKeyManager;
import fr.farmeurimmo.verymc.gui.MenuGui;

public class ChallengesGuis implements Listener {
	
	@EventHandler
	public void InventoryClickEvent(InventoryClickEvent e) {
		ItemStack current = e.getCurrentItem();
		if(current == null) {
			return;
		}
		if(e.getView().getTitle().equalsIgnoreCase("§6Challenges")) {
			e.setCancelled(true);
			if(current.getType() == Material.ARROW) {
				MenuGui.OpenMainMenu((Player) e.getWhoClicked());
				return;
			}
			if(current.getType() == Material.CLOCK) {
				ChallengesGuis.MakeDailyGui((Player) e.getWhoClicked());
				return;
			}
		}
		if(e.getView().getTitle().equalsIgnoreCase("§6Challenges journaliers")) {
			e.setCancelled(true);
			if(current.getType() == Material.ARROW) {
				ChallengesGuis.MakeMainGui((Player) e.getWhoClicked());
				return;
			}
		}
	}
	public static void CompleteChallenge(Player player, int nombre) {
		if(IridiumSkyblockAPI.getInstance().getUser(player).getIsland().isPresent()) {
			Main.instance1.getData().set("Joueurs."+player.getName()+".Challenges.Daily."+nombre+".Progression", 0);
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "is bank give " + player.getName() + " crystaux 2");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "is bank give " + player.getName() + " argent 5000");
			CratesKeyManager.GiveCrateKey(player, 1, "Challenge");
			
			if(Main.instance1.getData().getInt("Joueurs."+player.getName()+".Challenges.Daily.1.Palier") >= 5) {
				Main.instance1.getData().set("Joueurs."+player.getName()+".Challenges.Daily."+nombre+".Active", false);
				Main.instance1.getData().set("Joueurs."+player.getName()+".Challenges.Daily."+nombre+".Palier", 5);
			} else {
				Main.instance1.getData().set("Joueurs."+player.getName()+".Challenges.Daily."+nombre+".Active", true);
			}
			Main.instance1.saveData();
			
			player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 5, 1);
			player.sendMessage("§6§lChallenges §8» §fVous venez de compléter le challenge journalier n°" + nombre+".");
			player.sendMessage("§6§lChallenges §8» §fVous avez reçu 2 crystaux, 5000$ et x1 clée Challenge.");
			
		} else {
			player.sendMessage("§6§lChallenges §8» §fVous pouvez uniquement compléter les challenges en possédant ou en"
					+ " faisant partie d'une ile.");
		}
	}
	public static void MakeMainGui(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, "§6Challenges");
        
        ItemStack custom1 = new ItemStack(Material.CLOCK, 1);
		ItemMeta customa = custom1.getItemMeta();
		customa.setDisplayName("§6Challenges journaliers");
		custom1.setItemMeta(customa);
		inv.setItem(11, custom1);
		
		ItemStack custom2 = new ItemStack(Material.BUCKET, 1);
		ItemMeta customb = custom2.getItemMeta();
		customb.setDisplayName("§6Challenges normaux");
		custom2.setItemMeta(customb);
		inv.setItem(15, custom2);
		
        
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
	public static void MakeDailyGui(Player player) {
        Inventory inv = Bukkit.createInventory(null, 36, "§6Challenges journaliers");
		
        if(Main.instance1.getData().getBoolean("Joueurs."+player.getName()+".Challenges.Daily.1.Active") == true) {
		ItemStack custom1 = new ItemStack(Material.COBBLESTONE, 1);
		ItemMeta customa = custom1.getItemMeta();
		int palier = Main.instance1.getData().getInt("Joueurs."+player.getName()+".Challenges.Daily.1.Palier");
		customa.setDisplayName("§6Miner " + ChallengesBlockBreak.cobble*palier + " de pierre");
		customa.setLore(Arrays.asList("§7" + Main.instance1.getData().getInt("Joueurs."+player.getName()+".Challenges.Daily.1.Progression")+"/"
		+ChallengesBlockBreak.cobble*palier,"§7Palier: "+palier+"/5"));
		custom1.setItemMeta(customa);
		inv.setItem(10, custom1);
        } else {
        	ItemStack custom1 = new ItemStack(Material.COBBLESTONE, 1);
    		ItemMeta customa = custom1.getItemMeta();
    		customa.setDisplayName("§6Minage de pierre");
    		customa.setLore(Arrays.asList("§7Terminé"));
    		customa.addEnchant(Enchantment.ARROW_FIRE, 1, true);
    		customa.addItemFlags(ItemFlag.HIDE_ENCHANTS);
    		custom1.setItemMeta(customa);
    		inv.setItem(10, custom1);
        }
        if(Main.instance1.getData().getBoolean("Joueurs."+player.getName()+".Challenges.Daily.2.Active") == true) {
    		ItemStack custom1 = new ItemStack(Material.COAL, 1);
    		ItemMeta customa = custom1.getItemMeta();
    		int palier = Main.instance1.getData().getInt("Joueurs."+player.getName()+".Challenges.Daily.2.Palier");
    		customa.setDisplayName("§6Miner " + ChallengesBlockBreak.coal*palier + " minerais de charbon");
    		customa.setLore(Arrays.asList("§7" + Main.instance1.getData().getInt("Joueurs."+player.getName()+".Challenges.Daily.2.Progression")+"/"
    		+ChallengesBlockBreak.coal*palier,"§7Palier: "+palier+"/5"));
    		custom1.setItemMeta(customa);
    		inv.setItem(11, custom1);
            } else {
            	ItemStack custom1 = new ItemStack(Material.COAL, 1);
        		ItemMeta customa = custom1.getItemMeta();
        		customa.setDisplayName("§6Minage de charbon");
        		customa.setLore(Arrays.asList("§7Terminé"));
        		customa.addEnchant(Enchantment.ARROW_FIRE, 1, true);
        		customa.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        		custom1.setItemMeta(customa);
        		inv.setItem(11, custom1);
            }
        if(Main.instance1.getData().getBoolean("Joueurs."+player.getName()+".Challenges.Daily.3.Active") == true) {
    		ItemStack custom1 = new ItemStack(Material.IRON_ORE, 1);
    		ItemMeta customa = custom1.getItemMeta();
    		int palier = Main.instance1.getData().getInt("Joueurs."+player.getName()+".Challenges.Daily.3.Palier");
    		customa.setDisplayName("§6Miner " + ChallengesBlockBreak.iron*palier + " minerais de fer");
    		customa.setLore(Arrays.asList("§7" + Main.instance1.getData().getInt("Joueurs."+player.getName()+".Challenges.Daily.3.Progression")+"/"
    		+ChallengesBlockBreak.iron*palier,"§7Palier: "+palier+"/5"));
    		custom1.setItemMeta(customa);
    		inv.setItem(12, custom1);
            } else {
            	ItemStack custom1 = new ItemStack(Material.IRON_ORE, 1);
        		ItemMeta customa = custom1.getItemMeta();
        		customa.setDisplayName("§6Minage de fer");
        		customa.setLore(Arrays.asList("§7Terminé"));
        		customa.addEnchant(Enchantment.ARROW_FIRE, 1, true);
        		customa.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        		custom1.setItemMeta(customa);
        		inv.setItem(12, custom1);
            }
        if(Main.instance1.getData().getBoolean("Joueurs."+player.getName()+".Challenges.Daily.4.Active") == true) {
    		ItemStack custom1 = new ItemStack(Material.GOLD_ORE, 1);
    		ItemMeta customa = custom1.getItemMeta();
    		int palier = Main.instance1.getData().getInt("Joueurs."+player.getName()+".Challenges.Daily.4.Palier");
    		customa.setDisplayName("§6Miner " + ChallengesBlockBreak.gold*palier + " minerais d'or");
    		customa.setLore(Arrays.asList("§7" + Main.instance1.getData().getInt("Joueurs."+player.getName()+".Challenges.Daily.4.Progression")+"/"
    		+ChallengesBlockBreak.gold*palier,"§7Palier: "+palier+"/5"));
    		custom1.setItemMeta(customa);
    		inv.setItem(13, custom1);
            } else {
            	ItemStack custom1 = new ItemStack(Material.GOLD_ORE, 1);
        		ItemMeta customa = custom1.getItemMeta();
        		customa.setDisplayName("§6Minage d'or");
        		customa.setLore(Arrays.asList("§7Terminé"));
        		customa.addEnchant(Enchantment.ARROW_FIRE, 1, true);
        		customa.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        		custom1.setItemMeta(customa);
        		inv.setItem(13, custom1);
            }
        if(Main.instance1.getData().getBoolean("Joueurs."+player.getName()+".Challenges.Daily.5.Active") == true) {
    		ItemStack custom1 = new ItemStack(Material.DIAMOND_ORE, 1);
    		ItemMeta customa = custom1.getItemMeta();
    		int palier = Main.instance1.getData().getInt("Joueurs."+player.getName()+".Challenges.Daily.5.Palier");
    		customa.setDisplayName("§6Miner " + ChallengesBlockBreak.diamond*palier + " minerais de diamant");
    		customa.setLore(Arrays.asList("§7" + Main.instance1.getData().getInt("Joueurs."+player.getName()+".Challenges.Daily.5.Progression")+"/"
    		+ChallengesBlockBreak.diamond*palier,"§7Palier: "+palier+"/5"));
    		custom1.setItemMeta(customa);
    		inv.setItem(14, custom1);
            } else {
            	ItemStack custom1 = new ItemStack(Material.DIAMOND_ORE, 1);
        		ItemMeta customa = custom1.getItemMeta();
        		customa.setDisplayName("§6Minage de diamant");
        		customa.setLore(Arrays.asList("§7Terminé"));
        		customa.addEnchant(Enchantment.ARROW_FIRE, 1, true);
        		customa.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        		custom1.setItemMeta(customa);
        		inv.setItem(14, custom1);
            }
        if(Main.instance1.getData().getBoolean("Joueurs."+player.getName()+".Challenges.Daily.6.Active") == true) {
    		ItemStack custom1 = new ItemStack(Material.EMERALD_ORE, 1);
    		ItemMeta customa = custom1.getItemMeta();
    		int palier = Main.instance1.getData().getInt("Joueurs."+player.getName()+".Challenges.Daily.6.Palier");
    		customa.setDisplayName("§6Miner " + ChallengesBlockBreak.emerald*palier + " émeraudes");
    		customa.setLore(Arrays.asList("§7" + Main.instance1.getData().getInt("Joueurs."+player.getName()+".Challenges.Daily.6.Progression")+"/"
    		+ChallengesBlockBreak.emerald*palier,"§7Palier: "+palier+"/5"));
    		custom1.setItemMeta(customa);
    		inv.setItem(15, custom1);
            } else {
            	ItemStack custom1 = new ItemStack(Material.EMERALD_ORE, 1);
        		ItemMeta customa = custom1.getItemMeta();
        		customa.setDisplayName("§6Minage d'émeraude");
        		customa.setLore(Arrays.asList("§7Terminé"));
        		customa.addEnchant(Enchantment.ARROW_FIRE, 1, true);
        		customa.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        		custom1.setItemMeta(customa);
        		inv.setItem(15, custom1);
            }
        if(Main.instance1.getData().getBoolean("Joueurs."+player.getName()+".Challenges.Daily.7.Active") == true) {
    		ItemStack custom1 = new ItemStack(Material.ANCIENT_DEBRIS, 1);
    		ItemMeta customa = custom1.getItemMeta();
    		int palier = Main.instance1.getData().getInt("Joueurs."+player.getName()+".Challenges.Daily.7.Palier");
    		customa.setDisplayName("§6Miner " + ChallengesBlockBreak.debris*palier + " ancients débris");
    		customa.setLore(Arrays.asList("§7" + Main.instance1.getData().getInt("Joueurs."+player.getName()+".Challenges.Daily.7.Progression")+"/"
    		+ChallengesBlockBreak.debris*palier,"§7Palier: "+palier+"/5"));
    		custom1.setItemMeta(customa);
    		inv.setItem(16, custom1);
            } else {
            	ItemStack custom1 = new ItemStack(Material.ANCIENT_DEBRIS, 1);
        		ItemMeta customa = custom1.getItemMeta();
        		customa.setDisplayName("§6Minage d'ancients débris");
        		customa.setLore(Arrays.asList("§7Terminé"));
        		customa.addEnchant(Enchantment.ARROW_FIRE, 1, true);
        		customa.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        		custom1.setItemMeta(customa);
        		inv.setItem(16, custom1);
            }
		
        
        
        
		
		ItemStack custom9 = new ItemStack(Material.ARROW, 1);
		ItemMeta customh = custom9.getItemMeta();
		customh.setDisplayName("§6Retour §8| §7(clic gauche)");
		custom9.setItemMeta(customh);
		inv.setItem(35, custom9);
		
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
