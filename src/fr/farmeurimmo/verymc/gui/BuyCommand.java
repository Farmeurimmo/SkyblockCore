package fr.farmeurimmo.verymc.gui;

import fr.farmeurimmo.verymc.eco.EcoAccountsManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BuyCommand {

    public static void BuyCmd(String cmd, Player player) {
        Double money = EcoAccountsManager.Moneys.get(player.getName());

        if (cmd.contains("hat")) {
            if (money >= Farm2WinGui.hatprix) ;
            EcoAccountsManager.RemoveFounds(player.getName(), (double) Farm2WinGui.hatprix);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lpv user " + player.getName() + " permission set hat server=skyblock");
            player.sendMessage("§6Vous avez reçu l'accès à la commande /hat !");
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
                public void run() {
                    player.closeInventory();
                    MakeCommandsGui.MakeCommandGui(player);
                }
            }, 2);
        }
        if (cmd.contains("fly")) {
            if (money >= Farm2WinGui.flyprix) ;
            EcoAccountsManager.RemoveFounds(player.getName(), (double) Farm2WinGui.flyprix);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lpv user " + player.getName() + " permission set fly server=skyblock");
            player.sendMessage("§6Vous avez reçu l'accès à la commande /fly !");
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
                public void run() {
                    player.closeInventory();
                    MakeCommandsGui.MakeCommandGui(player);
                }
            }, 2);
        }
        if (cmd.contains("feed")) {
            if (money >= Farm2WinGui.feedprix) ;
            EcoAccountsManager.RemoveFounds(player.getName(), (double) Farm2WinGui.feedprix);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lpv user " + player.getName() + " permission set feed server=skyblock");
            player.sendMessage("§6Vous avez reçu l'accès à la commande /feed !");
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
                public void run() {
                    player.closeInventory();
                    MakeCommandsGui.MakeCommandGui(player);
                }
            }, 2);
        }
        if (cmd.contains("craft")) {
            if (money >= Farm2WinGui.craftprix) ;
            EcoAccountsManager.RemoveFounds(player.getName(), (double) Farm2WinGui.craftprix);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lpv user " + player.getName() + " permission set craft server=skyblock");
            player.sendMessage("§6Vous avez reçu l'accès à la commande /craft !");
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
                public void run() {
                    player.closeInventory();
                    MakeCommandsGui.MakeCommandGui(player);
                }
            }, 2);
        }
        if (cmd.contains("isrename")) {
            if (money >= Farm2WinGui.isrenameprix) ;
            EcoAccountsManager.RemoveFounds(player.getName(), (double) Farm2WinGui.isrenameprix);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lpv user " + player.getName() + " permission set iridiumskyblock.rename server=skyblock");
            player.sendMessage("§6Vous avez reçu l'accès à la commande /is rename !");
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
                public void run() {
                    player.closeInventory();
                    MakeCommandsGui.MakeCommandGui(player);
                }
            }, 2);
        }
        if (cmd.contains("enchantement")) {
            if (money >= Farm2WinGui.enchantementprix) ;
            EcoAccountsManager.RemoveFounds(player.getName(), (double) Farm2WinGui.enchantementprix);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lpv user " + player.getName() + " permission set enchantement server=skyblock");
            player.sendMessage("§6Vous avez reçu l'accès à la commande /enchantement !");
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
                public void run() {
                    player.closeInventory();
                    MakeCommandsGui.MakeCommandGui(player);
                }
            }, 2);
        }
        if (cmd.contains("sellall")) {
            if (money >= Farm2WinGui.sellallprix) ;
            EcoAccountsManager.RemoveFounds(player.getName(), (double) Farm2WinGui.sellallprix);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lpv user " + player.getName() + " permission set economyshopgui.sellall server=skyblock");
            player.sendMessage("§6Vous avez reçu l'accès à la commande /sellall !");
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
                public void run() {
                    player.closeInventory();
                    MakeCommandsGui.MakeCommandGui(player);
                }
            }, 2);
        }
    }

}
