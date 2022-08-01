package main.java.fr.verymc.spigot.core.gui;

import main.java.fr.verymc.spigot.Main;
import main.java.fr.verymc.spigot.core.eco.EcoAccountsManager;
import main.java.fr.verymc.spigot.utils.PreGenItems;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class RankBuyGui {

    public static void BuyRank(String rank, Player player) {
        Double money = EcoAccountsManager.instance.getMoney(player.getUniqueId());

        if (rank.contains("Légende")) {
            if (money >= Farm2WinGui.legendeprix) {
                EcoAccountsManager.instance.removeFounds(player, (double) Farm2WinGui.legendeprix, true);
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " parent add legende server=skyblock");
                player.sendMessage("§6Vous avez reçu le grade légende !");
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
                    public void run() {
                        RankBuyGui.MakeRankGui(player);
                    }
                }, 2);
            }
        }
        if (rank.contains("Dieu")) {
            if (money >= Farm2WinGui.dieuprix) {
                EcoAccountsManager.instance.removeFounds(player, (double) Farm2WinGui.dieuprix, true);
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " parent add dieu server=skyblock");
                player.sendMessage("§6Vous avez reçu le grade dieu !");
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
                    public void run() {
                        RankBuyGui.MakeRankGui(player);
                    }
                }, 2);
            }
        }
        if (rank.contains("Zeus")) {
            if (money >= Farm2WinGui.zeusprix) {
                EcoAccountsManager.instance.removeFounds(player, (double) Farm2WinGui.zeusprix, true);
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " parent add zeus server=skyblock");
                player.sendMessage("§6Vous avez reçu le grade zeus !");
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
                    public void run() {
                        RankBuyGui.MakeRankGui(player);
                    }
                }, 2);
            }
        }
    }

    public static void MakeRankGui(Player player) {
        Inventory invboutiquefarm2win = Bukkit.createInventory(null, 27, "§6Boutique des grades Farm2Win");

        ItemStack custom8 = new ItemStack(Material.ARROW, 1);
        ItemMeta customh = custom8.getItemMeta();
        customh.setDisplayName("§6Retour §8| §7(clic gauche)");
        custom8.setItemMeta(customh);

        ItemStack custom10 = new ItemStack(Material.IRON_BLOCK, 1);
        ItemMeta customi = custom10.getItemMeta();
        if (!player.hasPermission("group.legende")) {
            customi.setDisplayName("§aLégende");
        } else {
            customi.setDisplayName("§aLégende §c(déjà possédé)");
            custom10.setType(Material.BEDROCK);
        }
        customi.setLore(Arrays.asList("§8» Avantages affichés au spawn."));
        custom10.setItemMeta(customi);
        invboutiquefarm2win.setItem(11, custom10);

        ItemStack custom12 = new ItemStack(Material.GOLD_BLOCK, 1);
        ItemMeta customk = custom12.getItemMeta();
        if (!player.hasPermission("group.dieu")) {
            customk.setDisplayName("§9Dieu");
        } else {
            customk.setDisplayName("§9Dieu §c(déjà possédé)");
            custom12.setType(Material.BEDROCK);
        }
        customk.setLore(Arrays.asList("§8» Avantages affichés au spawn."));
        custom12.setItemMeta(customk);
        invboutiquefarm2win.setItem(13, custom12);

        ItemStack custom13 = new ItemStack(Material.NETHERITE_BLOCK, 1);
        ItemMeta customs = custom13.getItemMeta();
        if (!player.hasPermission("group.zeus")) {
            customs.setDisplayName("§bZeus");
        } else {
            customs.setDisplayName("§bZeus §c(déjà possédé)");
            custom13.setType(Material.BEDROCK);
        }
        customs.setLore(Arrays.asList("§8» Avantages affichés au spawn."));
        custom13.setItemMeta(customs);
        invboutiquefarm2win.setItem(15, custom13);


        invboutiquefarm2win.setItem(26, custom8);
        invboutiquefarm2win.setItem(22, PreGenItems.instance.getOwnerHead(player));

        player.openInventory(invboutiquefarm2win);
    }

}
