package fr.farmeurimmo.criptmania.crates;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CratesKeyManager {
	
	public static void GiveCrateKey(Player player, int nombre, String type) {
		if(type.equalsIgnoreCase("Légendaire")) {
			ItemStack custom1 = new ItemStack(Material.TRIPWIRE_HOOK, nombre);
	  		ItemMeta customa = custom1.getItemMeta();
	  		customa.addEnchant(Enchantment.DURABILITY, 10, true);
	  		customa.setUnbreakable(true);
	  		customa.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
	  		customa.setDisplayName("§6§lClée légendaire");
	  		custom1.setItemMeta(customa);
	  		player.getInventory().addItem(custom1);
	  		Bukkit.broadcastMessage("§6§lCrates §8» §fVous avez reçu x" + nombre + " clée(s) légendaire !");
		}
	}
}
