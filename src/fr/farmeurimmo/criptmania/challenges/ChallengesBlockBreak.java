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

public class ChallengesBlockBreak implements Listener {
	
	public static ArrayList<Location> placed = new ArrayList<Location>();
	
	@EventHandler
	public void blockBreakEvent(BlockBreakEvent e) {
		Player player = e.getPlayer();
		Block aaa = e.getBlock();
		if(aaa.getType() == Material.COBBLESTONE && !placed.contains(aaa.getLocation()) &&
				Main.instance1.getData().getBoolean("Joueurs."+player.getName()+".Challenges.Daily.1.Active") == true) {
			int progress = Main.instance1.getData().getInt("Joueurs."+player.getName()+".Challenges.Daily.1.Progression") + 1;
			Main.instance1.getData().set("Joueurs."+player.getName()+".Challenges.Daily.1.Progression", progress);
			Main.instance1.saveData();
			if(progress == 64) {
				ChallengesGuis.CompleteChallenge(player, 1);
				Main.instance1.getData().set("Joueurs."+player.getName()+".Challenges.Daily.1.Active", false);
				Main.instance1.getData().set("Joueurs."+player.getName()+".Challenges.Daily.1.Progression", 0);
				Main.instance1.saveData();
			}
			player.sendMessage("progress: " + progress);
		}
		if(aaa.getType() == Material.COBBLESTONE && placed.contains(e.getBlock().getLocation())) {
			placed.remove(e.getBlock().getLocation());
		}
	}
	@EventHandler
	public void blockPlaceEvent(BlockPlaceEvent e) {
		if(!placed.contains(e.getBlock().getLocation()) && e.getBlock().getType() == Material.COBBLESTONE) {
			placed.add(e.getBlock().getLocation());
		}
	}
}
