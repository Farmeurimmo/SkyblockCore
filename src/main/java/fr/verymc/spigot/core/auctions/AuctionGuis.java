package main.java.fr.verymc.spigot.core.auctions;

import main.java.fr.verymc.spigot.core.gui.MenuGui;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.LinkedList;
import java.util.UUID;

public class AuctionGuis implements Listener {

    public static AuctionGuis instance;

    public AuctionGuis() {
        instance = this;
    }

    public void openAuction(Player player, int page) {
        ItemStack custom4 = new ItemStack(Material.IRON_DOOR, 1);
        ItemMeta customd = custom4.getItemMeta();
        customd.setDisplayName("§6Retour §8| §7(clic gauche)");
        custom4.setItemMeta(customd);

        ItemStack custom6 = new ItemStack(Material.WRITABLE_BOOK, 1);
        ItemMeta customc = custom6.getItemMeta();
        customc.setDisplayName("§6Items listés §8| §7(clic gauche)");
        custom6.setItemMeta(customc);

        ItemStack custom5 = new ItemStack(Material.ARROW, 1);
        ItemMeta customa = custom5.getItemMeta();
        customa.setDisplayName("§6Page suivante §8| §7(clic gauche)");
        custom5.setItemMeta(customa);

        ItemStack custom3 = new ItemStack(Material.ARROW, 1);
        ItemMeta customb = custom3.getItemMeta();
        customb.setDisplayName("§6Page précédente §8| §7(clic gauche)");
        custom3.setItemMeta(customb);

        Inventory inv = AuctionsManager.instance.getInvFromInt(page);

        if (AuctionsManager.instance.numOfPage() > page + 1) inv.setItem(53, custom5);
        if (page > 1) inv.setItem(45, custom3);
        inv.setItem(49, custom4);
        inv.setItem(47, custom6);

        player.openInventory(inv);
        AuctionsManager.instance.open.put(player.getUniqueId(), page);
    }

    public void auctionnedListGui(Player player) {

        Inventory inv = Bukkit.createInventory(null, 54, AuctionsManager.BASE_STR_LISTED);

        ItemStack custom4 = new ItemStack(Material.IRON_DOOR, 1);
        ItemMeta customd = custom4.getItemMeta();
        customd.setDisplayName("§6Retour §8| §7(clic gauche)");
        custom4.setItemMeta(customd);

        inv.setItem(49, custom4);

        LinkedList<Auction> items = AuctionsManager.instance.listed(player);
        LinkedList<ItemStack> itemStacks = new LinkedList<>();
        for (Auction auction : items) {
            itemStacks.add(AuctionsManager.instance.applyUUIDAuction(auction.getItem().clone(), auction));
        }
        for (Integer slots : AuctionsManager.instance.getSlots()) {
            if (itemStacks.size() == 0) break;
            inv.setItem(slots, itemStacks.removeFirst());
        }

        player.openInventory(inv);
        AuctionsManager.instance.openListed.add(player.getUniqueId());
    }

    @EventHandler
    public void OnInventoryClick(InventoryClickEvent e) {
        ItemStack current = e.getCurrentItem();

        if (current == null) return;
        if (current.getType() == Material.AIR) return;

        Material currenttype = current.getType();

        Player player = (Player) e.getWhoClicked();

        if (e.getView().getTitle().contains(AuctionsManager.BASE_STR_NAME)) {
            e.setCancelled(true);
            if (current.hasAttributeModifiers() && current.getAttributeModifiers(Attribute.GENERIC_FOLLOW_RANGE) != null) {
                UUID uuid;
                for (AttributeModifier attributeModifier : current.getAttributeModifiers(Attribute.GENERIC_FOLLOW_RANGE)) {
                    if (attributeModifier.getName().equals(AuctionsManager.BASE_STR_UUID)) {
                        uuid = attributeModifier.getUniqueId();
                        AuctionsManager.instance.buyItemFromAh(player, AuctionsManager.instance.getFromUUIDProduct(uuid));
                        return;
                    }
                }
            }
            if (currenttype == Material.IRON_DOOR) {
                MenuGui.OpenMainMenu(player);
                return;
            }
            if (currenttype == Material.WRITABLE_BOOK) {
                auctionnedListGui(player);
                return;
            }
            if (currenttype == Material.ARROW) {
                boolean isArrowOfPage = true;
                int page = 0;
                try {
                    page = Integer.parseInt(e.getView().getTitle().replace(AuctionsManager.BASE_STR_NAME, ""));
                } catch (NumberFormatException ex) {
                    isArrowOfPage = false;
                }
                if (isArrowOfPage && current.getDisplayName().contains("§6Page précédente")) {
                    openAuction(player, page - 1);
                    return;
                }
                if (isArrowOfPage && current.getDisplayName().contains("§6Page suivante")) {
                    openAuction(player, page + 1);
                    return;
                }
            }
            return;
        }
        if (e.getView().getTitle().equalsIgnoreCase(AuctionsManager.BASE_STR_LISTED)) {
            e.setCancelled(true);
            if (e.getSlot() == 49) {
                if (current.getDisplayName().equalsIgnoreCase("§6Retour §8| §7(clic gauche)")) {
                    openAuction(player, 0);
                }
                return;
            }
            if (current.hasAttributeModifiers() && current.getAttributeModifiers(Attribute.GENERIC_FOLLOW_RANGE) != null) {
                UUID uuid;
                for (AttributeModifier attributeModifier : current.getAttributeModifiers(Attribute.GENERIC_FOLLOW_RANGE)) {
                    if (attributeModifier.getName().equals(AuctionsManager.BASE_STR_UUID)) {
                        uuid = attributeModifier.getUniqueId();
                        Auction auction = AuctionsManager.instance.getFromUUIDProduct(uuid);
                        if (auction == null) return;
                        if (player.getInventory().firstEmpty() == -1) {
                            player.sendMessage("§6§lAuctions §8» §cVotre inventaire est plein !");
                            return;
                        }
                        AuctionsManager.instance.deListItemFromAh(auction);
                        player.getInventory().addItem(auction.getItem());
                        player.sendMessage("§6§lAuctions §8» §fVous avez délisté l'item. (x" + auction.getItem().getAmount() + " " + auction.getItem().getType() + ")");
                        return;
                    }
                }
            }
        }
    }

    @EventHandler
    public void OnInventoryClose(InventoryCloseEvent e) {
        if (e.getView().getTitle().contains(AuctionsManager.BASE_STR_NAME)) {
            AuctionsManager.instance.open.remove(e.getPlayer().getUniqueId());
        }
        if (e.getView().getTitle().equalsIgnoreCase(AuctionsManager.BASE_STR_LISTED)) {
            AuctionsManager.instance.openListed.remove(e.getPlayer().getUniqueId());
        }
    }
}
