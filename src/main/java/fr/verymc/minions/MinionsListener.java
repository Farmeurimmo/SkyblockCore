package main.java.fr.verymc.minions;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class MinionsListener implements Listener {

    @EventHandler
    public void onInventoryClicEvent(InventoryClickEvent e) {
        ItemStack current = e.getCurrentItem();
        Material currentType = current.getType();
        if (current == null) return;
        if (currentType == null) return;
        if (e.getView().getTitle() == null) return;
        if (e.getView().getTitle().contains("§6Menu du minion " + MinionType.PIOCHEUR.getName(MinionType.PIOCHEUR))) {
            e.setCancelled(true);
            return;
        }
    }

    @EventHandler
    public void interactMinion(PlayerInteractEvent e) {
        Location locBloc = e.getClickedBlock().getLocation();
        Minion minion = null;
        for (Minion minions : MinionManager.instance.minions) {
            if (!locBloc.equals(minions.getBlocLocation())) {
                continue;
            }
            minion = minions;
            break;
        }
        if (minion == null) return;
        MinionsGui.instance.minionMainGui(e.getPlayer(), minion);
        e.setCancelled(true);
        return;
    }

    @EventHandler
    public void placeMinion(PlayerInteractEvent e) {
        if (e.getItem() == null) return;
        if (e.getItem().getType() == null) return;
        if (!e.getItem().isUnbreakable()) {
            return;
        }
        if (e.getItem().getType() == Material.DRAGON_BREATH) {
            if (e.getItem().getDisplayName().contains(MinionType.PIOCHEUR.getName(MinionType.PIOCHEUR))) {
                Player player = e.getPlayer();
                MinionManager.instance.addMinion(player, e.getClickedBlock().getLocation(), MinionType.PIOCHEUR);
                e.setCancelled(true);
                player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
                return;
            }
        }
    }
}
