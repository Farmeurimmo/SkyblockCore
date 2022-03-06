package main.java.fr.verymc.auctions;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class AuctionGui implements Listener {

    @EventHandler
    public void OnInventoryClick(InventoryClickEvent e) {
        ItemStack current = e.getCurrentItem();

        if (current == null) return;
        if (current.getType() == null) return;

        if (e.getView().getTitle().contains("§6Auctions #")) {
            e.setCancelled(true);
        }
    }

}
