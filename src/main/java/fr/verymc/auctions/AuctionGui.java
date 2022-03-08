package main.java.fr.verymc.auctions;

import main.java.fr.verymc.gui.MenuGui;
import main.java.fr.verymc.utils.HiddenStringUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class AuctionGui implements Listener {

    @EventHandler
    public void OnInventoryClick(InventoryClickEvent e) {
        ItemStack current = e.getCurrentItem();

        if (current == null) return;
        if (current.getType() == null) return;

        Material currenttype = current.getType();

        Player player = (Player) e.getWhoClicked();

        if (e.getView().getTitle().contains("§6Auctions #")) {
            e.setCancelled(true);
            if(currenttype== Material.IRON_DOOR){
                MenuGui.OpenMainMenu(player);
                return;
            }
            if(currenttype == Material.ARROW){
                if(current.getDisplayName().contains("§6Page suivante")){

                    return;
                }
                if(current.getDisplayName().contains("§6Page suivante")){

                    return;
                }
            }
            if(current.getLore()==null){
                return;
            }
            int loresize = current.getLore().size();
            if(loresize<4){
                return;
            }
            current.getLore().remove(loresize-1);
            current.getLore().remove(loresize-2);
            current.getLore().remove(loresize-3);
            current.getLore().remove(loresize-4);
            long millis = Long.parseLong(HiddenStringUtils.extractHiddenString(current.getLore().get(current.getLore().size())));
            if(AuctionsManager.instance.ahtype.containsValue(current)){
                player.sendMessage("aaa "+millis);
            }
        }
    }

    @EventHandler
    public void OnInventoryOpen(InventoryCloseEvent e) {
        if(e.getView().getTitle().contains("§6Auctions #")){
            if(AuctionsManager.instance.open.containsKey(e.getPlayer().getName())){
                AuctionsManager.instance.open.remove(e.getPlayer().getName());
            }
        }
    }

}
