package fr.farmeurimmo.criptmania.crates;

import java.util.Arrays;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class LegCrateManager {
	
	public static String LegCratesLoot(Player player) {
		String loot = null;
		Random rand = new Random();
            int n = rand.nextInt(101);
            if (n >= 0 && n <= 5){
            	loot = "x1 Pioche l�gendaire T4";
            	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "itemleg give " + player.getName() + " 4");
            }
            if (n >= 6 && n <= 11){
            	loot = "x1 Pioche l�gendaire T3";
            	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "itemleg give " + player.getName() + " 3");
            }
            if (n >= 12 && n <= 16){
            	loot = "x1 Pioche l�gendaire T2";
            	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "itemleg give " + player.getName() + " 2");
            }
            if (n >= 17 && n <= 20){
            	loot = "x1 Pioche l�gendaire T1";
            	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "itemleg give " + player.getName() + " 1");
            }
            if (n >= 21 && n <= 31){
            	loot = "100 000$";
            	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "money give "+player.getName()+" 100000");
            }
            if (n >= 32 && n <= 33){
            	if(!player.hasPermission("dieu")) {
            	loot = "Grade �9Dieu";
            	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " +player.getName() + " parent set Dieu server=skyblock");
            	} else {
            		loot = "reroll";
            	}
            }
            if (n >= 34 && n <= 37){
            	if(!player.hasPermission("legende")) {
            	loot = "Grade �eL�gende";
            	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " +player.getName() + " parent set Legende server=skyblock");
            	} else {
            		loot = "reroll";
            	}
            }
            if (n >= 38 && n <= 43){
            	loot = "x1 Spawneur � Iron Golem";
            	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "silkspawners add "+player.getName()+" iron_golem");
            }
            if (n >= 44 && n <= 48){
            	loot = "x2 Spawneurs � Iron Golem";
            	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "silkspawners add "+player.getName()+" iron_golem 2");
            }
            if (n >= 49 && n <= 52){
            	loot = "x3 Spawneurs � Iron Golem";
            	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "silkspawners add "+player.getName()+" iron_golem 3");
            }
            if (n >= 53 && n <= 64){
            	loot = "x48 Blocs d'�meraude";
            	player.getInventory().addItem(new ItemStack(Material.EMERALD_BLOCK, 48));
            }
            if (n >= 65 && n <= 68){
            	loot = "x1 SellChest";
            	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "chests give "+player.getName()+" sell_chest");
            }
            if (n >= 69 && n <= 72){
            	loot = "x2 SellChest";
            	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "chests give "+player.getName()+" sell_chest 2");
            }
            if (n >= 73 && n <= 78){
            	loot = "x2 Balises";
            	player.getInventory().addItem(new ItemStack(Material.BEACON, 2));
            }
            if (n >= 79 && n <= 84){
            	loot = "x3 Balises";
            	player.getInventory().addItem(new ItemStack(Material.BEACON, 3));
            }
            if (n >= 85 && n <= 90){
            	loot = "x18 Blocs de netherite";
            	player.getInventory().addItem(new ItemStack(Material.NETHERITE_BLOCK, 18));
            }
            if (n >= 91 && n <= 97){
            	loot = "x24 Blocs de netherite";
            	player.getInventory().addItem(new ItemStack(Material.NETHERITE_BLOCK, 24));
            }
            if (n >= 55 && n <= 62){
            	loot = "250 000$";
            	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "money give "+player.getName()+" 250000");
            }
            if (n >= 98 && n <= 100){
            	loot = "500 000$";
            	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "money give "+player.getName()+" 500000");
            }
            if(loot == null) {
            	loot = "error";
            	Bukkit.broadcastMessage("aaa: " + n);
            }
            return loot;
	}
	
	public static void LegCratesPreview(Player player) {
        Inventory inv = Bukkit.createInventory(null, 45, "�6Boxe l�gendaire");
        
    	ItemStack custom1 = new ItemStack(Material.NETHERITE_PICKAXE, 1);
		ItemMeta meta1 = custom1.getItemMeta();
		meta1.setDisplayName("�6Pioche l�gendaire �8| �eTier �c1");
		meta1.setLore(Arrays.asList("�74%"));
		meta1.addEnchant(Enchantment.DIG_SPEED, 5, true);
		meta1.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		custom1.setItemMeta(meta1);
		inv.setItem(10, custom1);
		
		ItemStack custom2 = new ItemStack(Material.NETHERITE_PICKAXE, 1);
		ItemMeta meta2 = custom2.getItemMeta();
		meta2.setDisplayName("�6Pioche l�gendaire �8| �eTier �c2");
		meta2.setLore(Arrays.asList("�75%"));
		meta2.addEnchant(Enchantment.DIG_SPEED, 5, true);
		meta2.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		custom2.setItemMeta(meta2);
		inv.setItem(11, custom2);
		
		ItemStack custom3 = new ItemStack(Material.NETHERITE_PICKAXE, 1);
		ItemMeta meta3 = custom3.getItemMeta();
		meta3.setDisplayName("�6Pioche l�gendaire �8| �eTier �c3");
		meta3.setLore(Arrays.asList("�75%"));
		meta3.addEnchant(Enchantment.DIG_SPEED, 5, true);
		meta3.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		custom3.setItemMeta(meta3);
		inv.setItem(12, custom3);
		
		ItemStack custom4 = new ItemStack(Material.NETHERITE_PICKAXE, 1);
		ItemMeta meta4 = custom4.getItemMeta();
		meta4.setDisplayName("�6Pioche l�gendaire �8| �eTier �c4");
		meta4.setLore(Arrays.asList("�76%"));
		meta4.addEnchant(Enchantment.DIG_SPEED, 5, true);
		meta4.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		custom4.setItemMeta(meta4);
		inv.setItem(13, custom4);
		
		ItemStack custom5 = new ItemStack(Material.SPAWNER, 1);
		ItemMeta meta5 = custom5.getItemMeta();
		meta5.setDisplayName("�6Spawneur � Iron Golem x1");
		meta5.setLore(Arrays.asList("�76%"));
		custom5.setItemMeta(meta5);
		inv.setItem(14, custom5);
		
		ItemStack custom6 = new ItemStack(Material.SPAWNER, 2);
		ItemMeta meta6 = custom6.getItemMeta();
		meta6.setDisplayName("�6Spawneur � Iron Golem x2");
		meta6.setLore(Arrays.asList("�75%"));
		custom6.setItemMeta(meta6);
		inv.setItem(15, custom6);
		
		ItemStack custom7 = new ItemStack(Material.SPAWNER, 3);
		ItemMeta meta7 = custom7.getItemMeta();
		meta7.setDisplayName("�6Spawneur � Iron Golem x3");
		meta7.setLore(Arrays.asList("�74%"));
		custom7.setItemMeta(meta7);
		inv.setItem(16, custom7);
		
		ItemStack custom9 = new ItemStack(Material.CHEST, 1);
		ItemMeta meta9 = custom9.getItemMeta();
		meta9.setDisplayName("�6SellChest x1");
		meta9.setLore(Arrays.asList("�74%"));
		custom9.setItemMeta(meta9);
		inv.setItem(19, custom9);
		
		ItemStack custom10 = new ItemStack(Material.CHEST, 2);
		ItemMeta meta10 = custom10.getItemMeta();
		meta10.setDisplayName("�6SellChest x2");
		meta10.setLore(Arrays.asList("�74%"));
		custom10.setItemMeta(meta10);
		inv.setItem(20, custom10);
		
		ItemStack custom11 = new ItemStack(Material.BEACON, 2);
		ItemMeta meta11 = custom11.getItemMeta();
		meta11.setDisplayName("�6Balise x2");
		meta11.setLore(Arrays.asList("�76%"));
		custom11.setItemMeta(meta11);
		inv.setItem(21, custom11);
		
		ItemStack custom12 = new ItemStack(Material.BEACON, 3);
		ItemMeta meta12 = custom12.getItemMeta();
		meta12.setDisplayName("�6Balise x3");
		meta12.setLore(Arrays.asList("�76%"));
		custom12.setItemMeta(meta12);
		inv.setItem(22, custom12);
		
		ItemStack custom13 = new ItemStack(Material.NETHERITE_BLOCK, 18);
		ItemMeta meta13 = custom13.getItemMeta();
		meta13.setDisplayName("�6Bloc de netherite x18");
		meta13.setLore(Arrays.asList("�76%"));
		custom13.setItemMeta(meta13);
		inv.setItem(23, custom13);
		
		ItemStack custom14 = new ItemStack(Material.NETHERITE_BLOCK, 24);
		ItemMeta meta14 = custom14.getItemMeta();
		meta14.setDisplayName("�6Bloc de netherite x24");
		meta14.setLore(Arrays.asList("�77%"));
		custom14.setItemMeta(meta14);
		inv.setItem(24, custom14);
		
		ItemStack custom21 = new ItemStack(Material.EMERALD_BLOCK, 48);
		ItemMeta meta21 = custom21.getItemMeta();
		meta21.setLore(Arrays.asList("�710%"));
		meta21.setDisplayName("�6Bloc d'�meraude x48");
		custom21.setItemMeta(meta21);
		inv.setItem(25, custom21);
		
		ItemStack custom16 = new ItemStack(Material.SUNFLOWER, 1);
		ItemMeta meta16 = custom16.getItemMeta();
		meta16.setDisplayName("�6100 000$");
		meta16.setLore(Arrays.asList("�78%"));
		custom16.setItemMeta(meta16);
		inv.setItem(28, custom16);
		
		ItemStack custom17 = new ItemStack(Material.SUNFLOWER, 1);
		ItemMeta meta17 = custom17.getItemMeta();
		meta17.setDisplayName("�6250 000$");
		meta17.setLore(Arrays.asList("�77%"));
		custom17.setItemMeta(meta17);
		inv.setItem(29, custom17);
		
		ItemStack custom18 = new ItemStack(Material.SUNFLOWER, 1);
		ItemMeta meta18 = custom18.getItemMeta();
		meta18.setDisplayName("�6500 000$");
		meta18.setLore(Arrays.asList("�73%"));
		custom18.setItemMeta(meta18);
		inv.setItem(30, custom18);
		
		ItemStack custom19 = new ItemStack(Material.PAPER, 1);
		ItemMeta meta19 = custom19.getItemMeta();
		meta19.setDisplayName("�6Grade �eL�gende");
		meta19.setLore(Arrays.asList("�73%"));
		custom19.setItemMeta(meta19);
		inv.setItem(31, custom19);
		
		ItemStack custom20 = new ItemStack(Material.PAPER, 1);
		ItemMeta meta20 = custom20.getItemMeta();
		meta20.setDisplayName("�6Grade �9Dieu");
		meta20.setLore(Arrays.asList("�71%"));
		custom20.setItemMeta(meta20);
		inv.setItem(32, custom20);
		
		
		
		ItemStack custom8 = new ItemStack(Material.WHITE_STAINED_GLASS_PANE, 1);
		ItemMeta meta8 = custom8.getItemMeta();
		meta8.setDisplayName("�6");
		custom8.setItemMeta(meta8);
		
		for (int i = 0; i < inv.getSize(); i++) {
            if (inv.getItem(i) == null || inv.getItem(i).getType().equals(Material.AIR)) {
            	inv.setItem(i, custom8);
            }
        }
		
		player.openInventory(inv);
	}

}
