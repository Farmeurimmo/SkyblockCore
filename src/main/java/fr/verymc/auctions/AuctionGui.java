package main.java.fr.verymc.auctions;

import main.java.fr.verymc.gui.MenuGui;
import org.bukkit.Material;
import org.bukkit.entity.Player;
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

        Player player = (Player) e.getWhoClicked();

        if (e.getView().getTitle().contains("§6Auctions #")) {
            e.setCancelled(true);
            if(current.getType()== Material.IRON_DOOR){
                MenuGui.OpenMainMenu(player);
                return;
            }
        }
    }

}
