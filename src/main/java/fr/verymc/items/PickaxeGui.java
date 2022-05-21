package main.java.fr.verymc.items;

import main.java.fr.verymc.utils.ItemStackBuilder;
import main.java.fr.verymc.utils.PreGenItems;
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

import java.util.List;

public class PickaxeGui implements Listener {
    public static Inventory MakeGui(Player player, ItemStack pickaxe) {

        Inventory invPickaxe = Bukkit.createInventory(null, 27, "§6Tiers de la pioche évolutive");
        ItemStack custom8 = (new ItemStackBuilder(Material.IRON_DOOR, 1)
                .setName("§eFermer §8| §7(clic gauche)").getItemStack());
        ItemStack custom10 = (new ItemStackBuilder(Material.ENCHANTED_BOOK, 1))
                .setName("§eAmélioration de l'efficacité")
                .setLore("§6Ajoutez un niveau d'éfficacité | §aClic gauche",
                        "§6Niveau Maximum : §c15 §8| §6Niveau Actuelle : §a"
                                + pickaxe.getEnchantLevel(Enchantment.DIG_SPEED),
                        "§6Cout : §a1 000 §6utilisations").getItemStack();
        custom10.addEnchant(Enchantment.DIG_SPEED, pickaxe.getEnchantLevel(Enchantment.DIG_SPEED) + 1, true);
        invPickaxe.setItem(10, custom10);

        ItemStack custom12 = (new ItemStackBuilder(Material.ENCHANTED_BOOK, 1))
                .setName("§eAmélioration de la fortune")
                .setLore("§6Ajoutez un niveau de fortune | §aClic gauche",
                        "§6Niveau Maximum : §c6 §8| §6Niveau Actuelle : §a"
                                + pickaxe.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS),
                        "§6Cout : §a1 000 §6utilisations").getItemStack();
        custom12.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, pickaxe.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS) + 1, true);
        invPickaxe.setItem(13, custom12);

        ItemStack custom13;
        if (pickaxe.getEnchantments().containsKey(Enchantment.SILK_TOUCH)) {
            custom13 = (new ItemStackBuilder(Material.ENCHANTED_BOOK, 1))
                    .setName("§eDésactivez toucher de soie")
                    .setLore("§6Désactivez toucher de soie §8| §aClic gauche",
                            "§6Cout : §a10 000 §6utilisations").getItemStack();
            if (player.getItemInHand().hasItemFlag(ItemFlag.HIDE_PLACED_ON)) {
                custom13.getLore().set(1, "");
            }
        } else {
            custom13 = (new ItemStackBuilder(Material.ENCHANTED_BOOK, 1))
                    .setName("§eActivez toucher de soie")
                    .setLore("§6Activez toucher de soie §8| §aClic gauche",
                            "§6Cout : §a10 000 §6utilisations").getItemStack();
            if (player.getItemInHand().hasItemFlag(ItemFlag.HIDE_PLACED_ON)) {
                custom13.getLore().set(1, "");
            } else {
                custom13.setDisplayName("§eDébloquez l'activation / désactivation du toucher de soie");
            }
        }
        custom13.addEnchant(Enchantment.SILK_TOUCH, 1, false);
        invPickaxe.setItem(16, custom13);
        invPickaxe.setItem(26, custom8);
        invPickaxe.setItem(22, PreGenItems.instance.getOwnerHead(player));

        return invPickaxe;
    }

    public static int getUsageNumber(ItemStack pickaxe) {
        if (pickaxe.getLore() == null) {
            return -1;
        }
        if (!pickaxe.getLore().get(0).contains("§7")) {
            return -1;
        }
        String tosearch = pickaxe.getLore().get(0).replace("§7", "");
        try {
            Integer.parseInt(tosearch);
        } catch (NumberFormatException ede) {
            ede.printStackTrace();
            return -1;
        }
        return Integer.parseInt(tosearch);
    }

    public static void addNumber(ItemStack pickaxe, int amount) {
        int nbrUsage = getUsageNumber(pickaxe);
        if (nbrUsage < 0) {
            return;
        }
        nbrUsage += amount;
        List<String> lore = pickaxe.getLore();
        lore.set(0, "§7" + nbrUsage);
        pickaxe.setLore(lore);
    }

    @EventHandler
    public void InventoryClickEvent(InventoryClickEvent e) {
        if (!e.getView().getTitle().equalsIgnoreCase("§6Tiers de la pioche évolutive")) {
            return;
        }
        Player player = (Player) e.getWhoClicked();
        ItemStack current = e.getCurrentItem();
        if (current == null) {
            return;
        }
        e.setCancelled(true);
        if (current.getType() == Material.IRON_DOOR) {
            player.closeInventory();
            return;
        }
        if (current.getEnchantments().containsKey(Enchantment.DIG_SPEED)) {
            int enchantementLevel = current.getEnchantLevel(Enchantment.DIG_SPEED);
            if (getUsageNumber(player.getItemInHand()) >= 1000 && enchantementLevel <= 15) {
                addNumber(player.getItemInHand(), -1000);
                player.getItemInHand().addEnchant(Enchantment.DIG_SPEED, enchantementLevel, true);
                player.closeInventory();
                player.sendMessage("§6§lPioche §8» §fVotre pioche évolutive a augmenté son niveau d'éfficité §e1");
                player.sendMessage("Son nouveau niveau d'efficacité est : §a" + enchantementLevel);
                return;
            }
            if (enchantementLevel > 15) {
                player.sendMessage("§cErreur, votre pioche a atteint le niveau maximum d'éfficacité.");
            } else {
                player.sendMessage("§cErreur, vous n'avez pas assez d'utilisation.");
            }
            return;
        }
        if (current.getEnchantments().containsKey(Enchantment.LOOT_BONUS_BLOCKS)) {
            int enchantementLevel = current.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS);
            if (getUsageNumber(player.getItemInHand()) >= 1000 && enchantementLevel <= 6) {
                addNumber(player.getItemInHand(), -1000);
                player.getItemInHand().addEnchant(Enchantment.LOOT_BONUS_BLOCKS, enchantementLevel, true);
                player.closeInventory();
                player.sendMessage("§6§lPioche §8» §fVotre pioche évolutive a augmenté son niveau de fortune §e1");
                player.sendMessage("Son nouveau niveau de fortune est : §a" + enchantementLevel);
                return;
            }
            if (enchantementLevel > 6) {
                player.sendMessage("§cErreur, votre pioche a atteint le niveau maximum de fortune.");
            } else {
                player.sendMessage("§cErreur, vous n'avez pas assez d'utilisation.");
            }
            return;
        }
        if (current.getEnchantments().containsKey(Enchantment.SILK_TOUCH)) {
            if (player.getItemInHand().hasItemFlag(ItemFlag.HIDE_PLACED_ON)) {
                if (player.getItemInHand().getEnchantments().containsKey(Enchantment.SILK_TOUCH)) {
                    player.getItemInHand().removeEnchant(Enchantment.SILK_TOUCH);
                    player.closeInventory();
                    player.sendMessage("§6§lPioche §8» §fToucher de soie désactivé.");
                } else {
                    player.getItemInHand().addEnchant(Enchantment.SILK_TOUCH, 1, false);
                    player.closeInventory();
                    player.sendMessage("§6§lPioche §8» §fToucher de soie activé.");
                }
            } else {
                if (getUsageNumber(player.getItemInHand()) >= 10000) {
                    addNumber(player.getItemInHand(), -10000);
                    player.getItemInHand().addItemFlags(ItemFlag.HIDE_PLACED_ON);
                    player.closeInventory();
                    player.sendMessage("§6§lPioche §8» §fVous avez débloqué la possibilitée d'activer toucher de soie.");
                } else {
                    player.sendMessage("§cErreur, vous n'avez pas assez d'utilisation.");
                }
            }
        }
    }
}
