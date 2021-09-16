package fr.farmeurimmo.premsi.challenges;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import fr.farmeurimmo.premsi.cmd.moderation.BuildCmd;
import fr.farmeurimmo.premsi.core.Main;

public class ChallengesBlockBreak implements Listener {
	
	public static ArrayList<Location> placed = new ArrayList<Location>();
	
	@EventHandler
	public void blockBreakEvent(BlockBreakEvent e) {
		Player player = e.getPlayer();
		Block aaa = e.getBlock();
		if(!e.isCancelled() && !BuildCmd.Build.contains(player) && !placed.contains(aaa.getLocation())) {
		if(aaa.getType() == Material.COBBLESTONE &&
				Main.instance1.getData().getBoolean("Joueurs."+player.getName()+".Challenges.Daily.1.Active") == true) {
			int progress = Main.instance1.getData().getInt("Joueurs."+player.getName()+".Challenges.Daily.1.Progression") + 1;
			Main.instance1.getData().set("Joueurs."+player.getName()+".Challenges.Daily.1.Progression", progress);
			Main.instance1.saveData();
			if(progress >= 320) {
				ChallengesGuis.CompleteChallenge(player, 1);
				Main.instance1.getData().set("Joueurs."+player.getName()+".Challenges.Daily.1.Active", false);
				Main.instance1.getData().set("Joueurs."+player.getName()+".Challenges.Daily.1.Progression", 0);
				Main.instance1.saveData();
			}
		}
		if(aaa.getType() == Material.COAL_ORE &&
				Main.instance1.getData().getBoolean("Joueurs."+player.getName()+".Challenges.Daily.2.Active") == true) {
			int progress = Main.instance1.getData().getInt("Joueurs."+player.getName()+".Challenges.Daily.2.Progression") + 1;
			Main.instance1.getData().set("Joueurs."+player.getName()+".Challenges.Daily.2.Progression", progress);
			Main.instance1.saveData();
			if(progress >= 288) {
				ChallengesGuis.CompleteChallenge(player, 2);
				Main.instance1.getData().set("Joueurs."+player.getName()+".Challenges.Daily.2.Active", false);
				Main.instance1.getData().set("Joueurs."+player.getName()+".Challenges.Daily.2.Progression", 0);
				Main.instance1.saveData();
			}
		}
		if(aaa.getType() == Material.IRON_ORE &&
				Main.instance1.getData().getBoolean("Joueurs."+player.getName()+".Challenges.Daily.3.Active") == true) {
			int progress = Main.instance1.getData().getInt("Joueurs."+player.getName()+".Challenges.Daily.3.Progression") + 1;
			Main.instance1.getData().set("Joueurs."+player.getName()+".Challenges.Daily.3.Progression", progress);
			Main.instance1.saveData();
			if(progress >= 256) {
				ChallengesGuis.CompleteChallenge(player, 3);
				Main.instance1.getData().set("Joueurs."+player.getName()+".Challenges.Daily.3.Active", false);
				Main.instance1.getData().set("Joueurs."+player.getName()+".Challenges.Daily.3.Progression", 0);
				Main.instance1.saveData();
			}
		}
		if(aaa.getType() == Material.GOLD_ORE &&
				Main.instance1.getData().getBoolean("Joueurs."+player.getName()+".Challenges.Daily.4.Active") == true) {
			int progress = Main.instance1.getData().getInt("Joueurs."+player.getName()+".Challenges.Daily.4.Progression") + 1;
			Main.instance1.getData().set("Joueurs."+player.getName()+".Challenges.Daily.4.Progression", progress);
			Main.instance1.saveData();
			if(progress >= 192) {
				ChallengesGuis.CompleteChallenge(player, 4);
				Main.instance1.getData().set("Joueurs."+player.getName()+".Challenges.Daily.4.Active", false);
				Main.instance1.getData().set("Joueurs."+player.getName()+".Challenges.Daily.4.Progression", 0);
				Main.instance1.saveData();
			}
		}
		if(aaa.getType() == Material.DIAMOND_ORE &&
				Main.instance1.getData().getBoolean("Joueurs."+player.getName()+".Challenges.Daily.5.Active") == true) {
			int progress = Main.instance1.getData().getInt("Joueurs."+player.getName()+".Challenges.Daily.5.Progression") + 1;
			Main.instance1.getData().set("Joueurs."+player.getName()+".Challenges.Daily.5.Progression", progress);
			Main.instance1.saveData();
			if(progress >= 128) {
				ChallengesGuis.CompleteChallenge(player, 5);
				Main.instance1.getData().set("Joueurs."+player.getName()+".Challenges.Daily.5.Active", false);
				Main.instance1.getData().set("Joueurs."+player.getName()+".Challenges.Daily.5.Progression", 0);
				Main.instance1.saveData();
			}
		}
		if(aaa.getType() == Material.EMERALD_ORE &&
				Main.instance1.getData().getBoolean("Joueurs."+player.getName()+".Challenges.Daily.6.Active") == true) {
			int progress = Main.instance1.getData().getInt("Joueurs."+player.getName()+".Challenges.Daily.6.Progression") + 1;
			Main.instance1.getData().set("Joueurs."+player.getName()+".Challenges.Daily.6.Progression", progress);
			Main.instance1.saveData();
			if(progress >= 64) {
				ChallengesGuis.CompleteChallenge(player, 6);
				Main.instance1.getData().set("Joueurs."+player.getName()+".Challenges.Daily.6.Active", false);
				Main.instance1.getData().set("Joueurs."+player.getName()+".Challenges.Daily.6.Progression", 0);
				Main.instance1.saveData();
			}
		}
		if(aaa.getType() == Material.ANCIENT_DEBRIS &&
				Main.instance1.getData().getBoolean("Joueurs."+player.getName()+".Challenges.Daily.7.Active") == true) {
			int progress = Main.instance1.getData().getInt("Joueurs."+player.getName()+".Challenges.Daily.7.Progression") + 1;
			Main.instance1.getData().set("Joueurs."+player.getName()+".Challenges.Daily.7.Progression", progress);
			Main.instance1.saveData();
			if(progress >= 16) {
				ChallengesGuis.CompleteChallenge(player, 7);
				Main.instance1.getData().set("Joueurs."+player.getName()+".Challenges.Daily.7.Active", false);
				Main.instance1.getData().set("Joueurs."+player.getName()+".Challenges.Daily.7.Progression", 0);
				Main.instance1.saveData();
			}
		}
		}
		if(e.getBlock().getType() == Material.COBBLESTONE
				&& e.getBlock().getType() == Material.COAL_ORE && e.getBlock().getType() == Material.IRON_ORE
				&& e.getBlock().getType() == Material.GOLD_ORE && e.getBlock().getType() == Material.DIAMOND_ORE
				&& e.getBlock().getType() == Material.EMERALD_ORE && e.getBlock().getType() == Material.ANCIENT_DEBRIS 
				&& aaa.getType() == Material.COAL_ORE && placed.contains(e.getBlock().getLocation())) {
			placed.remove(e.getBlock().getLocation());
		}
	}
	@EventHandler
	public void blockPlaceEvent(BlockPlaceEvent e) {
		if(!placed.contains(e.getBlock().getLocation()) && e.getBlock().getType() == Material.COBBLESTONE
				&& e.getBlock().getType() == Material.COAL_ORE && e.getBlock().getType() == Material.IRON_ORE
				&& e.getBlock().getType() == Material.GOLD_ORE && e.getBlock().getType() == Material.DIAMOND_ORE
				&& e.getBlock().getType() == Material.EMERALD_ORE && e.getBlock().getType() == Material.ANCIENT_DEBRIS) {
			placed.add(e.getBlock().getLocation());
		}
	}
}
