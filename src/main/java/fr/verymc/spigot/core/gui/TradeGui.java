package main.java.fr.verymc.spigot.core.gui;

import main.java.fr.verymc.spigot.Main;
import main.java.fr.verymc.spigot.core.eco.EcoAccountsManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TradeGui implements Listener {

    public static List<Player> balanceGui = new ArrayList<>();

    @EventHandler
    public void onInventoryDragEvent(InventoryDragEvent e) {
        if (!e.getView().getTitle().equalsIgnoreCase("§6Echange")) {
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
        if (!e.getView().getTitle().equalsIgnoreCase("§6Echange")) {
            return;
        }
        if (balanceGui.contains(Bukkit.getPlayer(e.getPlayer().getUniqueId()))) {
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
        if (!e.getView().getTitle().equalsIgnoreCase("§6Echange")) {
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
        if (e.getInventory().getItem(37).getType() == Material.GREEN_STAINED_GLASS_PANE
                && e.getInventory().getItem(39).getType() == Material.GREEN_STAINED_GLASS_PANE
                && e.getInventory().getItem(41).getType() == Material.GREEN_STAINED_GLASS_PANE
                && e.getInventory().getItem(43).getType() == Material.GREEN_STAINED_GLASS_PANE) {
            completeExchange(playerOne, playerTwo, slotPlayerOne, slotPlayerTwo, e.getClickedInventory(), trade);
            return;
        }
        if (e.getWhoClicked() == playerOne) {
            if (slotPlayerOne.contains(e.getSlot())
                    && e.getInventory().getItem(37).getType() == Material.RED_STAINED_GLASS_PANE) {
                e.setCancelled(false);
                return;
            }
            if (e.getSlot() == 39) {
                e.getInventory().setItem(37, e.getInventory().getItem(39));
                if (e.getInventory().getItem(37).getType() == Material.GREEN_STAINED_GLASS_PANE
                        && e.getInventory().getItem(39).getType() == Material.GREEN_STAINED_GLASS_PANE
                        && e.getInventory().getItem(41).getType() == Material.GREEN_STAINED_GLASS_PANE
                        && e.getInventory().getItem(43).getType() == Material.GREEN_STAINED_GLASS_PANE) {
                    completeExchange(playerOne, playerTwo, slotPlayerOne, slotPlayerTwo, e.getClickedInventory(), trade);
                }
                return;
            }
            if (e.getSlot() == 37 && e.getInventory().getItem(37).getType().equals(Material.RED_STAINED_GLASS_PANE)) {
                e.getInventory().setItem(39, e.getInventory().getItem(37));
                cancelExchange(playerOne, playerTwo, slotPlayerOne, slotPlayerTwo, e.getInventory(), trade);
                playerOne.sendMessage("§6§lTrade §8» §fVous avez annulez l'échange");
                playerTwo.sendMessage("§6§lTrade §8» §fEchange annulez par §a" + playerOne.getName());
            }
            if (e.getRawSlot() == 38
                    && e.getInventory().getItem(37).getType() != Material.GREEN_STAINED_GLASS_PANE) {
                balanceGui.add(playerOne);
                playerOne.openInventory(new MoneyTradeGui().getBalanceGui(e));
                balanceGui.remove(playerOne);
            }
        } else if (e.getWhoClicked() == playerTwo) {
            if (slotPlayerTwo.contains(e.getSlot())
                    && e.getInventory().getItem(41).getType() == Material.RED_STAINED_GLASS_PANE) {
                e.setCancelled(false);
                return;
            }
            if (e.getSlot() == 43) {
                e.getInventory().setItem(41, e.getInventory().getItem(43));
                e.setCancelled(true);
                if (e.getInventory().getItem(37).getType() == Material.GREEN_STAINED_GLASS_PANE
                        && e.getInventory().getItem(39).getType() == Material.GREEN_STAINED_GLASS_PANE
                        && e.getInventory().getItem(41).getType() == Material.GREEN_STAINED_GLASS_PANE
                        && e.getInventory().getItem(43).getType() == Material.GREEN_STAINED_GLASS_PANE) {
                    completeExchange(playerOne, playerTwo, slotPlayerOne, slotPlayerTwo, e.getClickedInventory(), trade);
                }
                return;
            }
            if (e.getSlot() == 41 && e.getInventory().getItem(41).getType().equals(Material.RED_STAINED_GLASS_PANE)) {
                e.getInventory().setItem(43, e.getInventory().getItem(41));
                cancelExchange(playerOne, playerTwo, slotPlayerOne, slotPlayerTwo, e.getInventory(), trade);
                playerOne.sendMessage("§6§lTrade §8» §fEchange annulez par §a" + playerOne.getName());
                playerTwo.sendMessage("§6§lTrade §8» §fVous avez annulé l'échange");
            }
            if (e.getRawSlot() == 42
                    && e.getInventory().getItem(41).getType() != Material.GREEN_STAINED_GLASS_PANE) {
                balanceGui.add(playerTwo);
                playerTwo.openInventory(new MoneyTradeGui().getBalanceGui(e));
                balanceGui.remove(playerTwo);
            }
        }
    }

    public void completeExchange(Player playerOne, Player playerTwo, ArrayList<Integer> slotPlayerOne, ArrayList<Integer> slotPlayerTwo,
                                 Inventory inv, TradeManager trade) {
        for (Integer value : slotPlayerTwo) {
            ItemStack item = inv.getItem(value);
            if (item != null) {
                playerOne.getInventory().addItem(item);
            }
        }
        for (Integer value : slotPlayerOne) {
            ItemStack item = inv.getItem(value);
            if (item != null) {
                playerTwo.getInventory().addItem(item);
            }
        }
        EcoAccountsManager.instance.addFounds(playerOne, trade.playerTwoMoneyAmount, false);
        EcoAccountsManager.instance.addFounds(playerTwo, trade.playerOneMoneyAmount, false);
        EcoAccountsManager.instance.removeFounds(playerOne, trade.playerOneMoneyAmount, false);
        EcoAccountsManager.instance.removeFounds(playerTwo, trade.playerTwoMoneyAmount, false);
        Main.instance.tradeInProcess.remove(trade);
        playerOne.closeInventory();
        playerTwo.closeInventory();
        playerOne.sendMessage("§6§lTrade §8» §fEchange terminé aver succès");
        playerTwo.sendMessage("§6§lTrade §8» §fEchange terminé aver succès");
    }

    public void cancelExchange(Player playerOne, Player playerTwo, ArrayList<Integer> slotPlayerOne, ArrayList<Integer> slotPlayerTwo,
                               Inventory inv, TradeManager trade) {
        for (Integer value : slotPlayerOne) {
            ItemStack item = inv.getItem(value);
            if (item != null) {
                playerOne.getInventory().addItem(item);
            }
        }
        for (Integer value : slotPlayerTwo) {
            ItemStack item = inv.getItem(value);
            if (item != null) {
                playerTwo.getInventory().addItem(item);
            }
        }
        Main.instance.tradeInProcess.remove(trade);
        playerOne.closeInventory();
        playerTwo.closeInventory();
    }
}
