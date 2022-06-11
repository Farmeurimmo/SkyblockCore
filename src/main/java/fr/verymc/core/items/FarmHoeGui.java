package main.java.fr.verymc.core.items;

import main.java.fr.verymc.core.eco.EcoAccountsManager;
import main.java.fr.verymc.utils.PreGenItems;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class FarmHoeGui implements Listener {

    public static boolean CheckForValidity(ItemStack farmhoe) {
        if (farmhoe.getLore() == null) {
            return false;
        }
        if (!farmhoe.getLore().get(0).contains("§")) {
            return false;
        }
        String tosearch = farmhoe.getLore().get(0).replace("§7", "");
        try {
            Integer.parseInt(tosearch);
        } catch (NumberFormatException ede) {
            return false;
        }
        if (!tosearch.contains(".")) {
            if (farmhoe == null) {
                return false;
            }
            if (farmhoe.getType() == Material.AIR) {
                return false;
            }
            if (farmhoe.getItemMeta() == null) {
                return false;
            }
            if (farmhoe.getType() != Material.NETHERITE_HOE) {
                return false;
            }
            if (!farmhoe.isUnbreakable()) {
                return false;
            }

            if (farmhoe.getDisplayName().contains("§cIII")) {
                return true;
            } else if (farmhoe.getDisplayName().contains("§cII")) {
                return true;
            } else return farmhoe.getDisplayName().contains("§cI");
        } else {
            return false;
        }
    }

    public static void MakeGui(Player player, int tier) {

        Inventory invboutiquefarm2win = Bukkit.createInventory(null, 27, "§6Tiers de farmhoe");

        ItemStack custom8 = new ItemStack(Material.IRON_DOOR, 1);
        ItemMeta customh = custom8.getItemMeta();
        customh.setDisplayName("§6Fermer §8| §7(clic gauche)");
        custom8.setItemMeta(customh);

        ItemStack custom10 = new ItemStack(Material.GOLDEN_HOE, 1);
        ItemMeta customi = custom10.getItemMeta();
        if (tier < 0) {
            customi.setDisplayName("§6Tier I");
        } else {
            customi.setDisplayName("§6Tier I §c(déjà possédé)");
            custom10.setType(Material.BEDROCK);
        }
        customi.setLore(Arrays.asList("§7Casse et replante en 1X1", "§7Coût: N/A",
                "§7Utilisations minimales pour débloquer: N/A"));
        custom10.setItemMeta(customi);
        invboutiquefarm2win.setItem(10, custom10);

        ItemStack custom12 = new ItemStack(Material.DIAMOND_HOE, 1);
        ItemMeta customk = custom12.getItemMeta();
        if (tier < 1) {
            customk.setDisplayName("§6Tier II");
        } else {
            customk.setDisplayName("§6Tier II §c(déjà possédé)");
            custom12.setType(Material.BEDROCK);
        }
        customk.setLore(Arrays.asList("§7Casse et replante en 3X3", "§7Coût: 500 000$",
                "§7Utilisations minimales pour débloquer: 50 000"));
        custom12.setItemMeta(customk);
        invboutiquefarm2win.setItem(13, custom12);

        ItemStack custom13 = new ItemStack(Material.NETHERITE_HOE, 1);
        ItemMeta customl = custom13.getItemMeta();
        if (tier < 2) {
            customl.setDisplayName("§6Tier III");
        } else {
            customl.setDisplayName("§6Tier III §c(déjà possédé)");
            custom13.setType(Material.BEDROCK);
        }
        customl.setLore(Arrays.asList("§7Casse et replante en 5X5", "§7Coût: 1 000 000$",
                "§7Utilisations minimales pour débloquer: 500 000"));
        custom13.setItemMeta(customl);
        invboutiquefarm2win.setItem(16, custom13);


        invboutiquefarm2win.setItem(26, custom8);
        invboutiquefarm2win.setItem(22, PreGenItems.instance.getOwnerHead(player));

        player.openInventory(invboutiquefarm2win);
    }

    @EventHandler
    public void InventoryClickEvent(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        ItemStack current = e.getCurrentItem();
        String title = e.getView().getTitle();
        if (!title.equalsIgnoreCase("§6Tiers de farmhoe")) return;
        if (current == null) return;
        if (current.getType() == null) ;
        e.setCancelled(true);
        if (current.getType() == Material.DIAMOND_HOE) {
            if (CheckForValidity(player.getItemInHand())) {
                if (EcoAccountsManager.instance.checkForFounds(player, 500000.0) &&
                        FarmHoeManager.GetBlockHaversted(player.getItemInHand()) >= 50000) {
                    EcoAccountsManager.instance.removeFounds(player, 500000.0, true);
                    player.getItemInHand().setDisplayName("§6FarmHoe Tier §cII (3X3)");
                    player.closeInventory();
                }
            }
        }
        if (current.getType() == Material.NETHERITE_HOE) {
            if (CheckForValidity(player.getItemInHand())) {
                if (EcoAccountsManager.instance.checkForFounds(player, 1000000.0) &&
                        FarmHoeManager.GetBlockHaversted(player.getItemInHand()) >= 500000) {
                    EcoAccountsManager.instance.removeFounds(player, 1000000.0, true);
                    player.getItemInHand().setDisplayName("§6FarmHoe Tier §cIII (5X5)");
                    player.closeInventory();
                }
            }
        }
        if (current.getType() == Material.IRON_DOOR) {
            player.closeInventory();
        }
    }
}
