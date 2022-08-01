package main.java.fr.verymc.spigot.hub.crates;

import main.java.fr.verymc.spigot.utils.InventoryUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CratesKeyManager {

    public static void GiveCrateKey(Player player, int nombre, String type) {
        ItemStack custom1 = new ItemStack(Material.TRIPWIRE_HOOK, nombre);
        ItemMeta customa = custom1.getItemMeta();
        if (type.equalsIgnoreCase("légendaire")) {
            customa.addEnchant(Enchantment.DURABILITY, 10, true);
            customa.setUnbreakable(true);
            customa.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            customa.setDisplayName("§6§lClée légendaire");
            custom1.setItemMeta(customa);

        } else if (type.equalsIgnoreCase("challenge")) {
            customa.addEnchant(Enchantment.DURABILITY, 10, true);
            customa.setUnbreakable(true);
            customa.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            customa.setDisplayName("§6§lClée challenge");
            custom1.setItemMeta(customa);

        } else {
            player.sendMessage("§6§lCrates §8» §fErreur, veuillez contacter un administrateur ! (Stack trace: " + type + ")");
            return;
        }
        player.sendMessage("§6§lCrates §8» §fVous avez reçu x" + nombre + " " + custom1.getDisplayName() + " !");
        if (InventoryUtils.instance.hasPlaceWithStackCo(custom1, player.getInventory(), player) >= 1) {
            player.getInventory().addItem(custom1);
        } else {
            player.getWorld().dropItemNaturally(player.getLocation().add(0, 0.5, 0), custom1);
        }
    }
}
