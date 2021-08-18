package fr.farmeurimmo.criptmania.challenges;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.event.inventory.InventoryClickEvent;

import fr.farmeurimmo.criptmania.Main;
import fr.farmeurimmo.criptmania.gui.MenuGui;

public class ChallengesGuis implements Listener {
	
	@EventHandler
	public void InventoryClickEvent(InventoryClickEvent e) {
		ItemStack current = e.getCurrentItem();
		if(e.getView().getTitle().equalsIgnoreCase("§6Challenges journaliers")) {
			e.setCancelled(true);
			if(current.getType() == Material.ARROW) {
				MenuGui.OpenMainMenu((Player) e.getWhoClicked());
			}
		}
	}
	public static void CompleteChallenge(Player player, int nombre) {
		if(nombre == 1) {
			Main.instance1.getData().set("Joueurs."+player.getName()+".Challenges.Daily.1.Active", false);
			Main.instance1.getData().set("Joueurs."+player.getName()+".Challenges.Daily.1.Progression", 0);
			Main.instance1.saveData();
		} else if(nombre == 2) {
			Main.instance1.getData().set("Joueurs."+player.getName()+".Challenges.Daily.2.Active", false);
			Main.instance1.getData().set("Joueurs."+player.getName()+".Challenges.Daily.2.Progression", 0);
			Main.instance1.saveData();
		} else {
			return;
		}
		player.sendMessage("§6§lChallenges §8» §fVous venez de compléter le challenge journalier n°" + nombre);
	}
	public static void MakeDailyGui(Player player) {
        Inventory inv = Bukkit.createInventory(null, 54, "§6Challenges journaliers");
		
        if(Main.instance1.getData().getBoolean("Joueurs."+player.getName()+".Challenges.Daily.1.Active") == true) {
		ItemStack custom1 = new ItemStack(Material.COBBLESTONE, 1);
		ItemMeta customa = custom1.getItemMeta();
		customa.setDisplayName("§6Miner 320 pierres");
		customa.setLore(Arrays.asList("§7" + Main.instance1.getData().getInt("Joueurs."+player.getName()+".Challenges.Daily.1.Progression")+
				"/320"));
		custom1.setItemMeta(customa);
		inv.setItem(10, custom1);
        } else {
        	ItemStack custom1 = new ItemStack(Material.COBBLESTONE, 1);
    		ItemMeta customa = custom1.getItemMeta();
    		customa.setDisplayName("§6Miner 288 minerais de charbon");
    		customa.setLore(Arrays.asList("§7Terminé"));
    		customa.addEnchant(Enchantment.ARROW_FIRE, 1, true);
    		customa.addItemFlags(ItemFlag.HIDE_ENCHANTS);
    		custom1.setItemMeta(customa);
    		inv.setItem(10, custom1);
        }
		
        if(Main.instance1.getData().getBoolean("Joueurs."+player.getName()+".Challenges.Daily.2.Active") == true) {
    		ItemStack custom1 = new ItemStack(Material.COAL_ORE, 1);
    		ItemMeta customa = custom1.getItemMeta();
    		customa.setDisplayName("§6Miner 288 charbons");
    		customa.setLore(Arrays.asList("§7" + Main.instance1.getData().getInt("Joueurs."+player.getName()+".Challenges.Daily.2.Progression")+
    				"/288"));
    		custom1.setItemMeta(customa);
    		inv.setItem(11, custom1);
            } else {
            	ItemStack custom1 = new ItemStack(Material.COAL_ORE, 1);
        		ItemMeta customa = custom1.getItemMeta();
        		customa.setDisplayName("§6Miner 288 charbons");
        		customa.setLore(Arrays.asList("§7Terminé"));
        		customa.addEnchant(Enchantment.ARROW_FIRE, 1, true);
        		customa.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        		custom1.setItemMeta(customa);
        		inv.setItem(11, custom1);
            }
		
		ItemStack custom2 = new ItemStack(Material.COMMAND_BLOCK_MINECART, 1);
		ItemMeta customb = custom2.getItemMeta();
		customb.setDisplayName("§6Boutique des commandes §8| §7(clic gauche)");
		custom2.setItemMeta(customb);
		inv.setItem(12, custom2);
		
		ItemStack custom4 = new ItemStack(Material.NETHERITE_BLOCK, 1);
		ItemMeta customd = custom4.getItemMeta();
		customd.setDisplayName("§6Boutique des clées §8| §7(clic gauche)");
		custom4.setItemMeta(customd);
		inv.setItem(16, custom4);
		
		ItemStack custom9 = new ItemStack(Material.ARROW, 1);
		ItemMeta customh = custom9.getItemMeta();
		customh.setDisplayName("§6Retour §8| §7(clic gauche)");
		custom9.setItemMeta(customh);
		inv.setItem(53, custom9);
		
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
