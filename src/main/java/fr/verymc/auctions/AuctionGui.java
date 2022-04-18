package main.java.fr.verymc.auctions;

import main.java.fr.verymc.gui.MenuGui;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class AuctionGui implements Listener {

    @EventHandler
    public void OnInventoryClick(InventoryClickEvent e) {
        ItemStack current = e.getCurrentItem();

        if (current == null) return;
        if (current.getType() == null) return;
        if (current.getType() == Material.AIR) return;

        if (e.getView().getTitle() == null) {
            return;
        }

        Material currenttype = current.getType();

        Player player = (Player) e.getWhoClicked();

        if (e.getView().getTitle().contains("§6Auctions #")) {
            e.setCancelled(true);
            String invname = e.getView().getTitle();
            invname = invname.replace("§6Auctions #", "");
            Integer numpage = Integer.parseInt(invname);
            if (currenttype == Material.IRON_DOOR) {
                if (current.getDisplayName().equalsIgnoreCase("§6Retour §8| §7(clic gauche)")) {
                    MenuGui.OpenMainMenu(player);
                    return;
                }
            }
            if (currenttype == Material.WRITABLE_BOOK) {
                if (current.getDisplayName().equalsIgnoreCase("§6Items listés §8| §7(clic gauche)")) {
                    AuctionsManager.instance.auctionnedListGui(player);
                    return;
                }
            }
            if (currenttype == Material.ARROW) {

                if (numpage == 0) return;

                if (current.getDisplayName().equalsIgnoreCase("§6Page suivante §8| §7(clic gauche)")) {
                    numpage += 1;
                    if (AuctionsManager.instance.ahinv.containsKey(numpage)) {
                        AuctionsManager.instance.openAuction(player, numpage);
                    }
                    return;
                }

                if (numpage == 1) return;

                if (current.getDisplayName().equalsIgnoreCase("§6Page précédente §8| §7(clic gauche)")) {
                    numpage -= 1;
                    if (AuctionsManager.instance.ahinv.containsKey(numpage)) {
                        AuctionsManager.instance.openAuction(player, numpage);
                    }
                    return;
                }
            }
            if (current.getLore() == null) {
                return;
            }
            ItemStack item = current.clone();
            int loresize = item.getLore().size() - 5;
            String toparse = item.getLore().get(loresize);
            long millis = Long.parseLong(toparse);
            if (AuctionsManager.instance.ahtype.containsKey(millis)) {
                if (AuctionsManager.instance.ahsellerUUID.get(millis).equals(player.getUniqueId())) {
                    player.sendMessage("§6§lAuctions §8» §fVous ne pouvez pas acheter vos propres items.");
                    return;
                }
                if (AuctionsManager.instance.ahseller.get(millis).equals(player.getName())) {
                    player.sendMessage("§6§lAuctions §8» §fVous ne pouvez pas acheter vos propres items.");
                    return;
                }
                AuctionsManager.instance.buyItemFromAh(player, millis);
            }
        }
        if (e.getView().getTitle().equalsIgnoreCase("§6Items listés")) {
            e.setCancelled(true);
            if (currenttype == Material.IRON_DOOR) {
                if (current.getDisplayName().equalsIgnoreCase("§6Retour §8| §7(clic gauche)")) {
                    AuctionsManager.instance.openAuction(player, 1);
                }
            }
            if (current.getLore() == null) {
                return;
            }
            ItemStack item = current.clone();
            int loresize = item.getLore().size() - 1;
            String toparse = item.getLore().get(loresize);
            long millis = Long.parseLong(toparse);
            if (AuctionsManager.instance.ahtype.containsKey(millis)) {
                if (player.getInventory().firstEmpty() == -1) {
                    player.sendMessage("§6§lAuctions §8» §fVous n'avez pas assez de place dans votre inventaire.");
                    return;
                }
                ArrayList<String> lore = new ArrayList<>();
                ArrayList<String> lorecloned = new ArrayList<>();
                lore.addAll(item.getLore());
                for (String aa : lore) {
                    if (aa.startsWith("§f§6")) {
                        lorecloned.add(aa);
                    }
                    if (aa.contains(millis + "")) {
                        lorecloned.add(millis + "");
                    }
                }
                for (String ae : lorecloned) {
                    lore.remove(ae + "");
                }
                item.setLore(lore);
                AuctionsManager.instance.deListItemFromAh(millis);
                player.getInventory().addItem(item);
                player.sendMessage("§6§lAuctions §8» §fVous avez rétiré avec succès §ax" + item.getAmount() + " " + item.getType() + "§f.");
            }
        }
    }

    @EventHandler
    public void OnInventoryClose(InventoryCloseEvent e) {
        if (e.getView().getTitle().contains("§6Auctions #")) {
            if (AuctionsManager.instance.open.containsKey(e.getPlayer().getName())) {
                AuctionsManager.instance.open.remove(e.getPlayer().getName());
            }
        }
        if (e.getView().getTitle().contains("§6Items listés")) {
            if (AuctionsManager.instance.auctionned.contains(e.getPlayer().getName())) {
                AuctionsManager.instance.auctionned.remove(e.getPlayer().getName());
            }
        }
    }
}
