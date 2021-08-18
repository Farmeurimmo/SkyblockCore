package fr.farmeurimmo.criptmania.challenges;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import fr.farmeurimmo.criptmania.Main;
import fr.farmeurimmo.criptmania.cmd.moderation.BuildCmd;

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
			if(progress == 320) {
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
			if(progress == 288) {
				ChallengesGuis.CompleteChallenge(player, 2);
				Main.instance1.getData().set("Joueurs."+player.getName()+".Challenges.Daily.2.Active", false);
				Main.instance1.getData().set("Joueurs."+player.getName()+".Challenges.Daily.2.Progression", 0);
				Main.instance1.saveData();
			}
		}
		}
		if(aaa.getType() == Material.COBBLESTONE && aaa.getType() == Material.COAL_ORE && placed.contains(e.getBlock().getLocation())) {
			placed.remove(e.getBlock().getLocation());
		}
	}
	@EventHandler
	public void blockPlaceEvent(BlockPlaceEvent e) {
		if(!placed.contains(e.getBlock().getLocation()) && e.getBlock().getType() == Material.COBBLESTONE
				&& e.getBlock().getType() == Material.COAL_ORE) {
			placed.add(e.getBlock().getLocation());
		}
	}
}
