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

public class ChallengeCrateManager {
	
	public static String ChallengeCrateLoot(Player player) {
		String loot = null;
		Random rand = new Random();
            int n = rand.nextInt(101);
            if(n <= 100) {
            	loot = "rien miskine";
            }
            return loot;
	}
	
	public static void ChallengeCratePreview(Player player) {
        Inventory inv = Bukkit.createInventory(null, 36, "�6Boxe challenge");
        
        	ItemStack custom1 = new ItemStack(Material.SUNFLOWER, 1);
    		ItemMeta meta1 = custom1.getItemMeta();
    		meta1.setDisplayName("�610 000$");
    		meta1.setLore(Arrays.asList("�7","�e12%"));
    		custom1.setItemMeta(meta1);
    		inv.setItem(10, custom1);
    		
    		ItemStack custom2 = new ItemStack(Material.SUNFLOWER, 2);
    		ItemMeta meta2 = custom2.getItemMeta();
    		meta2.setDisplayName("�625 000$");
    		meta2.setLore(Arrays.asList("�7","�e6%"));
    		custom2.setItemMeta(meta2);
    		inv.setItem(11, custom2);
    		
    		ItemStack custom3 = new ItemStack(Material.SUNFLOWER, 3);
    		ItemMeta meta3 = custom3.getItemMeta();
    		meta3.setDisplayName("�650 000$");
    		meta3.setLore(Arrays.asList("�7","�e4%"));
    		custom3.setItemMeta(meta3);
    		inv.setItem(12, custom3);
    		
    		ItemStack custom4 = new ItemStack(Material.TRIPWIRE_HOOK, 2);
	  		ItemMeta meta4 = custom4.getItemMeta();
	  		meta4.addEnchant(Enchantment.DURABILITY, 10, true);
	  		meta4.setUnbreakable(true);
	  		meta4.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
	  		meta4.setDisplayName("�6�lx2 Cl�e challenge");
	  		meta4.setLore(Arrays.asList("�7","�e7%"));
	  		custom4.setItemMeta(meta4);
	  		inv.setItem(13, custom4);
	  		
	  		ItemStack custom5 = new ItemStack(Material.TRIPWIRE_HOOK, 1);
	  		ItemMeta meta5 = custom5.getItemMeta();
	  		meta5.addEnchant(Enchantment.DURABILITY, 10, true);
	  		meta5.setUnbreakable(true);
	  		meta5.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
	  		meta5.setDisplayName("�6�lx1 Cl�e l�gendaire");
	  		meta5.setLore(Arrays.asList("�7","�e2%"));
	  		custom5.setItemMeta(meta5);
	  		inv.setItem(14, custom5);
	  		
	  		ItemStack custom6 = new ItemStack(Material.NETHERITE_BLOCK, 6);
    		ItemMeta meta6 = custom6.getItemMeta();
    		meta6.setDisplayName("�66 Blocs de netherite");
    		meta6.setLore(Arrays.asList("�7","�e5%"));
    		custom6.setItemMeta(meta6);
    		inv.setItem(15, custom6);
    		
    		ItemStack custom7 = new ItemStack(Material.EMERALD_BLOCK, 12);
    		ItemMeta meta7 = custom7.getItemMeta();
    		meta7.setDisplayName("�612 Blocs d'�meraude");
    		meta7.setLore(Arrays.asList("�7","�e5%"));
    		custom7.setItemMeta(meta7);
    		inv.setItem(16, custom7);
    		
    		ItemStack custom8 = new ItemStack(Material.DIAMOND_BLOCK, 18);
    		ItemMeta meta8 = custom8.getItemMeta();
    		meta8.setDisplayName("�618 Blocs de diamant");
    		meta8.setLore(Arrays.asList("�8","�e8%"));
    		custom8.setItemMeta(meta8);
    		inv.setItem(19, custom8);
    		
    		ItemStack custom9 = new ItemStack(Material.GOLD_BLOCK, 24);
    		ItemMeta meta9 = custom9.getItemMeta();
    		meta9.setDisplayName("�624 Blocs d'or");
    		meta9.setLore(Arrays.asList("�9","�e10%"));
    		custom9.setItemMeta(meta9);
    		inv.setItem(20, custom9);
    		
    		ItemStack custom10 = new ItemStack(Material.IRON_BLOCK, 32);
    		ItemMeta meta10 = custom10.getItemMeta();
    		meta10.setDisplayName("�632 Blocs de fer");
    		meta10.setLore(Arrays.asList("�1","�e12%"));
    		custom10.setItemMeta(meta10);
    		inv.setItem(21, custom10);
    		
    		ItemStack custom11 = new ItemStack(Material.COAL_BLOCK, 48);
    		ItemMeta meta11 = custom11.getItemMeta();
    		meta11.setDisplayName("�648 Blocs de charbon");
    		meta11.setLore(Arrays.asList("�1","�e15%"));
    		custom11.setItemMeta(meta11);
    		inv.setItem(22, custom11);
    		
    		ItemStack custom12 = new ItemStack(Material.FEATHER, 1);
    		ItemMeta meta12 = custom12.getItemMeta();
    		meta12.setDisplayName("�6Fly de 10 minutes");
    		meta12.setLore(Arrays.asList("�1","�e7%"));
    		custom12.setItemMeta(meta12);
    		inv.setItem(23, custom12);
    		
    		ItemStack custom13 = new ItemStack(Material.FEATHER, 1);
    		ItemMeta meta13 = custom13.getItemMeta();
    		meta13.setDisplayName("�6Fly de 20 minutes");
    		meta13.setLore(Arrays.asList("�1","�e5%"));
    		custom13.setItemMeta(meta13);
    		inv.setItem(24, custom13);
    		
    		ItemStack custom14 = new ItemStack(Material.FEATHER, 1);
    		ItemMeta meta14 = custom14.getItemMeta();
    		meta14.setDisplayName("�6Fly de 30 minutes");
    		meta14.setLore(Arrays.asList("�1","�e3%"));
    		custom14.setItemMeta(meta14);
    		inv.setItem(25, custom14);
		
		
		
		ItemStack custom20 = new ItemStack(Material.WHITE_STAINED_GLASS_PANE, 1);
		ItemMeta meta20 = custom20.getItemMeta();
		meta20.setDisplayName("�6");
		custom20.setItemMeta(meta20);
		
		for (int i = 0; i < inv.getSize(); i++) {
            if (inv.getItem(i) == null || inv.getItem(i).getType().equals(Material.AIR)) {
            	inv.setItem(i, custom20);
            }
        }
		
		player.openInventory(inv);
	}

}
