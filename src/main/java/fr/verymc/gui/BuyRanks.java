package main.java.fr.verymc.gui;

import main.java.fr.verymc.eco.EcoAccountsManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BuyRanks {

    public static void BuyRank(String rank, Player player) {
        Double money = EcoAccountsManager.Moneys.get(player.getName());

        if (rank.contains("L§gende")) {
            if (money >= Farm2WinGui.legendeprix) ;
            EcoAccountsManager.RemoveFounds(player.getName(), (double) Farm2WinGui.legendeprix);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lpv user " + player.getName() + " parent add legende server=skyblock");
            player.sendMessage("§6Vous avez reçu le grade l§gende !");
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
                public void run() {
                    player.closeInventory();
                    MakeRanksGui.MakeRankGui(player);
                }
            }, 2);
        }
        if (rank.contains("Dieu")) {
            if (money >= Farm2WinGui.dieuprix) ;
            EcoAccountsManager.RemoveFounds(player.getName(), (double) Farm2WinGui.dieuprix);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lpv user " + player.getName() + " parent add dieu server=skyblock");
            player.sendMessage("§6Vous avez reçu le grade dieu !");
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
                public void run() {
                    player.closeInventory();
                    MakeRanksGui.MakeRankGui(player);
                }
            }, 2);
        }
    }

}
