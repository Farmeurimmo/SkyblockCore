package fr.farmeurimmo.criptmania.atout;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.farmeurimmo.criptmania.Main;
import fr.farmeurimmo.criptmania.gui.MenuGui;
import fr.farmeurimmo.criptmania.utils.SendActionBar;

public class AtoutGui implements Listener{
	
	public static void MakeAtoutGui(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, "§6Atouts");
		
        if(Main.instance1.getData().getInt("Joueurs."+player.getName()+".Atout.1.Level") == 0) {
		ItemStack custom1 = new ItemStack(Material.GOLDEN_PICKAXE, 1);
		ItemMeta customa = custom1.getItemMeta();
		customa.setDisplayName("§6Haste 2");
		customa.setLore(Arrays.asList("§7","§cAchetez cet atout dans le /farm2win"));
		custom1.setItemMeta(customa);
		inv.setItem(10, custom1);
        } else if (Main.instance1.getData().getInt("Joueurs."+player.getName()+".Atout.1.Level") == 2) {
        	ItemStack custom1 = new ItemStack(Material.GOLDEN_PICKAXE, 1);
    		ItemMeta customa = custom1.getItemMeta();
    		if(Main.instance1.getData().getBoolean("Joueurs."+player.getName()+".Atout.1.Active") == true) {
    			customa.addEnchant(Enchantment.ARROW_FIRE, 1, true);
        		customa.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        		customa.setDisplayName("§6Haste 2 §a(Actif)");
    		} else {
    			customa.setDisplayName("§6Haste 2 §c(Inactif)");
    		}
    		custom1.setItemMeta(customa);
    		inv.setItem(10, custom1);
        }
        
        
        if(Main.instance1.getData().getInt("Joueurs."+player.getName()+".Atout.2.Level") == 0) {
    		ItemStack custom1 = new ItemStack(Material.SUGAR, 1);
    		ItemMeta customa = custom1.getItemMeta();
    		customa.setDisplayName("§6Speed 2");
    		customa.setLore(Arrays.asList("§7","§cAchetez cet atout dans le /farm2win"));
    		custom1.setItemMeta(customa);
    		inv.setItem(12, custom1);
            } else if (Main.instance1.getData().getInt("Joueurs."+player.getName()+".Atout.2.Level") == 2) {
            	ItemStack custom1 = new ItemStack(Material.SUGAR, 1);
        		ItemMeta customa = custom1.getItemMeta();
        		if(Main.instance1.getData().getBoolean("Joueurs."+player.getName()+".Atout.2.Active") == true) {
        			customa.addEnchant(Enchantment.ARROW_FIRE, 1, true);
            		customa.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            		customa.setDisplayName("§6Speed 2 §a(Actif)");
        		} else {
        			customa.setDisplayName("§6Speed 2 §c(Inactif)");
        		}
        		custom1.setItemMeta(customa);
        		inv.setItem(12, custom1);
            }
        
        
        if(Main.instance1.getData().getInt("Joueurs."+player.getName()+".Atout.3.Level") == 0) {
    		ItemStack custom1 = new ItemStack(Material.RABBIT_FOOT, 1);
    		ItemMeta customa = custom1.getItemMeta();
    		customa.setDisplayName("§6Jumpboost 3");
    		customa.setLore(Arrays.asList("§7","§cAchetez cet atout dans le /farm2win"));
    		custom1.setItemMeta(customa);
    		inv.setItem(14, custom1);
            } else if (Main.instance1.getData().getInt("Joueurs."+player.getName()+".Atout.3.Level") == 3) {
            	ItemStack custom1 = new ItemStack(Material.RABBIT_FOOT, 1);
        		ItemMeta customa = custom1.getItemMeta();
        		if(Main.instance1.getData().getBoolean("Joueurs."+player.getName()+".Atout.3.Active") == true) {
        			customa.addEnchant(Enchantment.ARROW_FIRE, 1, true);
            		customa.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            		customa.setDisplayName("§6Jumpboost 3 §a(Actif)");
        		} else {
        			customa.setDisplayName("§6Jumpboost 3 §c(Inactif)");
        		}
        		custom1.setItemMeta(customa);
        		inv.setItem(14, custom1);
            }
		
        
		
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
	@EventHandler
	public void InventoryClickEvent(InventoryClickEvent e) {
		Player player = (Player) e.getWhoClicked();
		ItemStack current = e.getCurrentItem();
		if(current == null) {
			return;
		}
		if(e.getView().getTitle().equalsIgnoreCase("§6Atouts")) {
			e.setCancelled(true);
			if(current.getType() == Material.ARROW) {
				MenuGui.OpenMainMenu(player);
			}
			if(current.getType() == Material.GOLDEN_PICKAXE && current.getItemMeta().getItemFlags().contains(ItemFlag.HIDE_ENCHANTS)) {
				player.removePotionEffect(PotionEffectType.FAST_DIGGING);
				Main.instance1.getData().set("Joueurs."+player.getName()+".Atout.1.Active", false);
				Main.instance1.saveData();
				SendActionBar.SendActionBarMsg(player, "§6Atout haste §c§ldésactivé !");
				AtoutGui.MakeAtoutGui(player);
				return;
			}
			if(current.getType() == Material.GOLDEN_PICKAXE) {
				if(Main.instance1.getData().getInt("Joueurs."+player.getName()+".Atout.1.Level") == 2) {
					player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 999999999, 1));
					SendActionBar.SendActionBarMsg(player, "§6Atout haste §a§lactivé !");
					Main.instance1.getData().set("Joueurs."+player.getName()+".Atout.1.Active", true);
					Main.instance1.saveData();
					AtoutGui.MakeAtoutGui(player);
					return;
				} else {
					SendActionBar.SendActionBarMsg(player, "§cAchetez cet atout dans le /farm2win");
				}
			}
			
			
			if(current.getType() == Material.SUGAR && current.getItemMeta().getItemFlags().contains(ItemFlag.HIDE_ENCHANTS)) {
				player.removePotionEffect(PotionEffectType.SPEED);
				Main.instance1.getData().set("Joueurs."+player.getName()+".Atout.2.Active", false);
				Main.instance1.saveData();
				SendActionBar.SendActionBarMsg(player, "§6Atout speed §c§ldésactivé !");
				AtoutGui.MakeAtoutGui(player);
				return;
			}
			if(current.getType() == Material.SUGAR) {
				if(Main.instance1.getData().getInt("Joueurs."+player.getName()+".Atout.2.Level") == 2) {
					player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999999, 1));
					SendActionBar.SendActionBarMsg(player, "§6Atout speed §a§lactivé !");
					Main.instance1.getData().set("Joueurs."+player.getName()+".Atout.2.Active", true);
					Main.instance1.saveData();
					AtoutGui.MakeAtoutGui(player);
					return;
				} else {
					SendActionBar.SendActionBarMsg(player, "§cAchetez cet atout dans le /farm2win");
				}
			}
			
			
			if(current.getType() == Material.RABBIT_FOOT && current.getItemMeta().getItemFlags().contains(ItemFlag.HIDE_ENCHANTS)) {
				player.removePotionEffect(PotionEffectType.JUMP);
				Main.instance1.getData().set("Joueurs."+player.getName()+".Atout.3.Active", false);
				Main.instance1.saveData();
				SendActionBar.SendActionBarMsg(player, "§6Atout Jumpboost §c§ldésactivé !");
				AtoutGui.MakeAtoutGui(player);
				return;
			}
			if(current.getType() == Material.RABBIT_FOOT) {
				if(Main.instance1.getData().getInt("Joueurs."+player.getName()+".Atout.3.Level") == 3) {
					player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 999999999, 2));
					SendActionBar.SendActionBarMsg(player, "§6Atout Jumpboost §a§lactivé !");
					Main.instance1.getData().set("Joueurs."+player.getName()+".Atout.3.Active", true);
					Main.instance1.saveData();
					AtoutGui.MakeAtoutGui(player);
					return;
				} else {
					SendActionBar.SendActionBarMsg(player, "§cAchetez cet atout dans le /farm2win");
				}
			}
		}	
	}
}
