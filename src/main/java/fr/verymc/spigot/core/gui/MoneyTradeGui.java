package main.java.fr.verymc.spigot.core.gui;

import main.java.fr.verymc.spigot.Main;
import main.java.fr.verymc.spigot.core.eco.EcoAccountsManager;
import main.java.fr.verymc.spigot.utils.ItemStackBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.NumberFormat;
import java.util.Arrays;

public class MoneyTradeGui implements Listener {

    public Inventory getBalanceGui(InventoryClickEvent e) {
        Player playerOne = null;
        Player playerTwo = null;
        TradeManager trade = null;
        for (int i = 0; i < Main.instance.tradeInProcess.size(); i++) {
            trade = Main.instance.tradeInProcess.get(i);
            if (Bukkit.getPlayer(e.getWhoClicked().getUniqueId()) == trade.playerOne || Bukkit.getPlayer(e.getWhoClicked().getUniqueId()) == trade.playerTwo) {
                playerOne = trade.playerOne;
                playerTwo = trade.playerTwo;
                break;
            }
        }
        ItemStack balanceGoldBlock = null;
        if (e.getWhoClicked() == playerOne) {
            balanceGoldBlock = (new ItemStackBuilder(Material.GOLD_BLOCK).setName("§6Balance : §e" + trade.playerOneMoneyAmount + "$").getItemStack());
        }
        if (e.getWhoClicked() == playerTwo) {
            balanceGoldBlock = (new ItemStackBuilder(Material.GOLD_BLOCK).setName("§6Balance : §e" + trade.playerTwoMoneyAmount + "$").getItemStack());
        }
        Inventory inv = Bukkit.createInventory(null, 27, "§6Balance");
        ItemStack yellowStainedGlass = (new ItemStackBuilder(Material.YELLOW_STAINED_GLASS_PANE)).setName("§a").getItemStack();
        ItemStack greenStainedGlass = (new ItemStackBuilder(Material.GREEN_STAINED_GLASS_PANE)).setName("§aConfirmer").getItemStack();
        ItemStack redStainedGlass = (new ItemStackBuilder(Material.RED_STAINED_GLASS_PANE)).setName("§cAnnuler").getItemStack();
        ItemStack goldNugget = (new ItemStackBuilder(Material.GOLD_NUGGET).setName("§61$").setLore("§a    >>Clique droit : §eajouter le montant", "§4    >>Clique gauche : §e retirer le montant").getItemStack());
        ItemStack goldIngot = (new ItemStackBuilder(Material.GOLD_INGOT).setName("§6100$").setLore("§a    >>Clique droit : §eajouter le montant", "§4    >>Clique gauche : §e retirer le montant").getItemStack());
        ItemStack goldBlock = (new ItemStackBuilder(Material.GOLD_BLOCK).setName("§61 000$").setLore("§a    >>Clique droit : §eajouter le montant", "§4    >>Clique gauche : §e retirer le montant").getItemStack());
        for (int i = 0; i < 10; i++) {
            inv.setItem(i, yellowStainedGlass);
        }
        for (int i = 17; i < 27; i++) {
            inv.setItem(i, yellowStainedGlass);
        }
        inv.setItem(21, redStainedGlass);
        inv.setItem(22, balanceGoldBlock);
        inv.setItem(23, greenStainedGlass);
        inv.setItem(11, goldNugget);
        inv.setItem(13, goldIngot);
        inv.setItem(15, goldBlock);
        return inv;
    }

    @EventHandler
    public void onInventoryDragEvent(InventoryDragEvent e) {
        if (!e.getView().getTitle().equalsIgnoreCase("§6Balance")) {
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
        if (!e.getView().getTitle().equalsIgnoreCase("§6Balance")) {
            return;
        }
        if (TradeGui.balanceGui.contains(Bukkit.getPlayer(e.getPlayer().getUniqueId()))) {
            return;
        }
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
            public void run() {
                e.getPlayer().sendMessage("§6§lTrade §8» §fVous ne pouvez pas quittez en pleine échange.");
                e.getPlayer().openInventory(e.getInventory());
            }
        }, 2);
    }

    public void updateAmount(Inventory inv, int moneyAmount) {
        ItemStack item = inv.getItem(22);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName("§6Balance : §e" + NumberFormat.getInstance().format(moneyAmount) + "$");
        item.setItemMeta(itemMeta);
        inv.setItem(22, item);
    }

    public void addMoneyInTrade(InventoryClickEvent e, int howMuch, TradeManager trade, Player playerOne, Player playerTwo) {
        if (e.getClick().isRightClick()) {
            if (playerOne == e.getWhoClicked()) {
                if (EcoAccountsManager.instance.getMoney(playerOne.getUniqueId()) >= trade.playerOneMoneyAmount + howMuch) {
                    trade.playerOneMoneyAmount += howMuch;
                } else {
                    trade.playerOneMoneyAmount = (int) EcoAccountsManager.instance.getMoney(playerOne.getUniqueId());
                    playerOne.sendMessage("§6§lTrade §8» §4Vous n'avez pas les fonds nécessaires");
                }
            }
            if (playerTwo == e.getWhoClicked()) {
                if (EcoAccountsManager.instance.getMoney(playerTwo.getUniqueId()) >= trade.playerTwoMoneyAmount + howMuch) {
                    trade.playerTwoMoneyAmount += howMuch;
                } else {
                    trade.playerTwoMoneyAmount = (int) EcoAccountsManager.instance.getMoney(playerTwo.getUniqueId());
                    playerTwo.sendMessage("§6§lTrade §8» §4Vous n'avez pas les fonds nécessaires");
                }
            }
        }
        if (e.getClick().isLeftClick()) {
            if (playerOne == e.getWhoClicked()) {
                if (trade.playerOneMoneyAmount > howMuch) {
                    trade.playerOneMoneyAmount -= howMuch;
                } else {
                    trade.playerOneMoneyAmount = 0;
                }
            }
            if (playerTwo == e.getWhoClicked()) {
                if (trade.playerTwoMoneyAmount > howMuch) {
                    trade.playerTwoMoneyAmount -= howMuch;
                } else {
                    trade.playerTwoMoneyAmount = 0;
                }
            }

        }
        if (e.getWhoClicked() == playerOne) {
            updateAmount(e.getInventory(), trade.playerOneMoneyAmount);
        }
        if (e.getWhoClicked() == playerTwo) {
            updateAmount(e.getInventory(), trade.playerTwoMoneyAmount);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!e.getView().getTitle().equalsIgnoreCase("§6Balance")) {
            return;
        }
        e.setCancelled(true);
        TradeManager trade = null;
        Player playerOne = null;
        Player playerTwo = null;
        for (int i = 0; i < Main.instance.tradeInProcess.size(); i++) {
            trade = Main.instance.tradeInProcess.get(i);
            if (Bukkit.getPlayer(e.getWhoClicked().getUniqueId()) == trade.playerOne || Bukkit.getPlayer(e.getWhoClicked().getUniqueId()) == trade.playerTwo) {
                playerOne = trade.playerOne;
                playerTwo = trade.playerTwo;
                break;
            }
        }
        if (trade == null) {
            return;
        }
        int previousAmountOne = trade.playerOneMoneyAmount;
        int previousAmountTwo = trade.playerTwoMoneyAmount;
        if (e.getRawSlot() == 11) {
            addMoneyInTrade(e, 1, trade, playerOne, playerTwo);
        }
        if (e.getRawSlot() == 13) {
            addMoneyInTrade(e, 100, trade, playerOne, playerTwo);
        }
        if (e.getRawSlot() == 15) {
            addMoneyInTrade(e, 1000, trade, playerOne, playerTwo);
        }
        if (e.getRawSlot() == 21) {
            trade.playerOneMoneyAmount = previousAmountOne;
            trade.playerTwoMoneyAmount = previousAmountTwo;
            TradeGui.balanceGui.add(Bukkit.getPlayer(e.getWhoClicked().getUniqueId()));
            e.getWhoClicked().openInventory(trade.tradeInv);
            TradeGui.balanceGui.remove(Bukkit.getPlayer(e.getWhoClicked().getUniqueId()));
        }
        if (e.getRawSlot() == 23 && e.getWhoClicked() == playerOne) {
            TradeGui.balanceGui.add(playerOne);
            ItemStack item = trade.tradeInv.getItem(38);
            ItemMeta meta = item.getItemMeta();
            meta.setLore(Arrays.asList("§6    >> " + NumberFormat.getInstance().format(trade.playerOneMoneyAmount) + "$"));
            item.setItemMeta(meta);
            trade.tradeInv.setItem(38, item);
            playerOne.openInventory(trade.tradeInv);
            TradeGui.balanceGui.remove(playerOne);
        }
        if (e.getRawSlot() == 23 && e.getWhoClicked() == playerTwo) {
            TradeGui.balanceGui.add(playerTwo);
            ItemStack item = trade.tradeInv.getItem(42);
            ItemMeta meta = item.getItemMeta();
            meta.setLore(Arrays.asList("§6    >> " + NumberFormat.getInstance().format(trade.playerTwoMoneyAmount) + "$"));
            item.setItemMeta(meta);
            trade.tradeInv.setItem(42, item);
            playerTwo.openInventory(trade.tradeInv);
            TradeGui.balanceGui.remove(playerTwo);
        }
    }
}
