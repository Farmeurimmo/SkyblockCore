package main.java.fr.verymc.gui;

import main.java.fr.verymc.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

public class TradeGui implements Listener {
    @EventHandler
    public void onInventoryDragEvent(InventoryDragEvent e) {
        if (!e.getView().getTitle().equals("§6Echange")) {
            return;
        }
        int size = e.getRawSlots().size();
        Object[] array = e.getRawSlots().toArray();
        for (int i = 0; i < size; i++) {
            if ((int) array[i] < 54) {
                e.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void onCloseInventory(InventoryCloseEvent e) {
        if (!e.getView().getTitle().equals("§6Echange")) {
            return;
        }
        if (e.getInventory().getItem(39).getType() == Material.GREEN_STAINED_GLASS_PANE
                && e.getInventory().getItem(43).getType() == Material.GREEN_STAINED_GLASS_PANE
                && (e.getInventory().getItem(37).getType() == Material.RED_STAINED_GLASS_PANE
                || e.getInventory().getItem(41).getType() == Material.RED_STAINED_GLASS_PANE)) {
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
                public void run() {
                    e.getPlayer().sendMessage("§6§lTrade §8» §fVous ne pouvez pas quittez en pleine échange.");
                    e.getPlayer().openInventory(e.getInventory());
                }
            }, 2);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!e.getView().getTitle().equals("§6Echange")) {
            return;
        }
        Player playerOne = null;
        Player playerTwo = null;
        ArrayList<Integer> slotPlayerOne = new ArrayList<>(Arrays.asList(10, 11, 12, 19, 20, 21, 28, 29, 30));
        ArrayList<Integer> slotPlayerTwo = new ArrayList<>(Arrays.asList(14, 15, 16, 23, 24, 25, 32, 33, 34));
        TradeManager trade = null;
        for (int i = 0; i < Main.instance.tradeInProcess.size(); i++) {
            trade = Main.instance.tradeInProcess.get(i);
            if (Bukkit.getPlayer(e.getWhoClicked().getUniqueId()) == trade.playerOne || Bukkit.getPlayer(e.getWhoClicked().getUniqueId()) == trade.playerTwo) {
                playerOne = trade.playerOne;
                playerTwo = trade.playerTwo;
                break;
            }
        }
        if (playerOne == null || playerTwo == null) {
            return;
        }
        if (e.getClickedInventory() == null) {
            return;
        }
        e.setCancelled(true);
        if (e.getClick().equals(ClickType.DOUBLE_CLICK)) {
            return;
        }
        if (e.isShiftClick() && e.getClickedInventory().getType().equals(InventoryType.PLAYER)) {
            return;
        }
        if (e.getClickedInventory().getType().equals(InventoryType.PLAYER)) {
            e.setCancelled(false);
            return;
        }
        if (e.getWhoClicked() == playerOne) {
            if (slotPlayerOne.contains(e.getSlot())) {
                e.setCancelled(false);
                return;
            }
            if (e.getSlot() == 39) {
                e.getInventory().setItem(37, e.getInventory().getItem(39));
            }
            if (e.getInventory().getItem(37) == e.getInventory().getItem(39)) {
                e.setCancelled(true);
            }
            if (e.getSlot() == 37 && e.getInventory().getItem(37).getType().equals(Material.RED_STAINED_GLASS_PANE)) {
                e.getInventory().setItem(39, e.getInventory().getItem(37));
                playerOne.closeInventory();
                playerOne.sendMessage("§6§lTrade §8» §fVous avez annulez l'échange");
                playerTwo.closeInventory();
                playerTwo.sendMessage("§6§lTrade §8» §fEchange annulez par §a" + playerOne.getName());
            }
        } else if (e.getWhoClicked() == playerTwo) {
            if (slotPlayerTwo.contains(e.getSlot())) {
                e.setCancelled(false);
                return;
            }
            if (e.getSlot() == 43) {
                e.getInventory().setItem(41, e.getInventory().getItem(43));
            }
            if (e.getInventory().getItem(41) == e.getInventory().getItem(43)) {
                e.setCancelled(true);
            }
            if (e.getSlot() == 41 && e.getInventory().getItem(41).getType().equals(Material.RED_STAINED_GLASS_PANE)) {
                e.getInventory().setItem(43, e.getInventory().getItem(41));
                for (Integer value : slotPlayerOne) {
                    ItemStack item = e.getInventory().getItem(value);
                    if (item != null) {
                        playerOne.getInventory().addItem(item);
                    }
                }
                for (Integer value : slotPlayerTwo) {
                    ItemStack item = e.getInventory().getItem(value);
                    if (item != null) {
                        playerTwo.getInventory().addItem(item);
                    }
                }
                playerOne.closeInventory();
                playerOne.sendMessage("§6§lTrade §8» §fEchange annulez par §a" + playerOne.getName());
                playerTwo.closeInventory();
                playerTwo.sendMessage("§6§lTrade §8» §fVous avez annulez l'échange");
                Main.instance.tradeInProcess.remove(trade);
            }
        }
        if (e.getInventory().getItem(37).getType() == Material.GREEN_STAINED_GLASS_PANE
                && e.getInventory().getItem(39).getType() == Material.GREEN_STAINED_GLASS_PANE
                && e.getInventory().getItem(41).getType() == Material.GREEN_STAINED_GLASS_PANE
                && e.getInventory().getItem(43).getType() == Material.GREEN_STAINED_GLASS_PANE) {
            for (Integer value : slotPlayerTwo) {
                ItemStack item = e.getInventory().getItem(value);
                if (item != null) {
                    playerOne.getInventory().addItem(item);
                }
            }
            for (Integer value : slotPlayerOne) {
                ItemStack item = e.getInventory().getItem(value);
                if (item != null) {
                    playerTwo.getInventory().addItem(item);
                }
            }
            Main.instance.tradeInProcess.remove(trade);
            playerOne.closeInventory();
            playerTwo.closeInventory();
            playerOne.sendMessage("§6§lTrade §8» §fEchange terminé aver succès");
            playerTwo.sendMessage("§6§lTrade §8» §fEchange terminé aver succès");
        }
    }
}
