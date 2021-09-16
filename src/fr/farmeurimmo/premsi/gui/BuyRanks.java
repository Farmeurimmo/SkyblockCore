package fr.farmeurimmo.premsi.gui;

import java.math.BigDecimal;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.tnemc.core.TNE;
import net.tnemc.core.common.api.TNEAPI;

public class BuyRanks {
	
	public static void BuyRank(String rank, Player player) {
		TNEAPI ecoAPI = TNE.instance().api();
	    int money = ecoAPI.getAccount(player.getName()).getHoldings().intValue();
		
	    if(rank.contains("legende")) {
	    	if(money >= Farm2WinGui.legendeprix);
			ecoAPI.getAccount(player.getName()).removeHoldings(new BigDecimal(Farm2WinGui.legendeprix));
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lpv user " + player.getName() + " parent add legende server=skyblock");
			player.sendMessage("§6Vous avez reçu le grade légende !");
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
			     public void run() {
					 player.closeInventory();
			    	 MakeRanksGui.MakeRankGui(player);
			     }
			}, 2);
	    }
	    if(rank.contains("dieu")) {
	    	if(money >= Farm2WinGui.dieuprix);
			ecoAPI.getAccount(player.getName()).removeHoldings(new BigDecimal(Farm2WinGui.dieuprix));
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lpv user " + player.getName() + " parent add dieu server=skyblock");
			player.sendMessage("§6Vous avez reçu le grade dieu !");
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
			     public void run() {
					 player.closeInventory();
			    	 MakeRanksGui.MakeRankGui(player);
			     }
			}, 2);
	    }
	    if(rank.contains("zeus")) {
	    	if(money >= Farm2WinGui.zeusprix);
			ecoAPI.getAccount(player.getName()).removeHoldings(new BigDecimal(Farm2WinGui.zeusprix));
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lpv user " + player.getName() + " parent add zeus server=skyblock");
			player.sendMessage("§6Vous avez reçu le grade zeus !");
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
			     public void run() {
					 player.closeInventory();
			    	 MakeRanksGui.MakeRankGui(player);
			     }
			}, 2);
	    }
	    
	}

}
