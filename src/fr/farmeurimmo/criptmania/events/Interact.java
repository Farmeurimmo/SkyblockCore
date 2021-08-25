package fr.farmeurimmo.criptmania.events;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import fr.farmeurimmo.criptmania.cmd.moderation.BuildCmd;
import fr.farmeurimmo.criptmania.gui.MenuGui;

public class Interact implements Listener {
	
	public static ArrayList<Player> Build = BuildCmd.Build;
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void OnInteract(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		if(player.getItemInHand().getType() == Material.FLINT_AND_STEEL && player.getWorld().getName().equalsIgnoreCase("world")) {
			e.setCancelled(true);
		}
		if((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)){
			if(e.getPlayer().getItemInHand().getType() == Material.NETHER_STAR && e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("�6Menu �8| �7(clic droit)")) {
				MenuGui.OpenMainMenu(player);
			}
		}
		if(e.getClickedBlock() == null) {
			return;
		}
		if(e.getClickedBlock().getType() == Material.ENDER_CHEST) {
			return;
		}
		if(player.getWorld().getName().equalsIgnoreCase("world")) {
			if(player.getItemInHand().getType() == Material.NETHER_STAR && e.getClickedBlock().getType() == Material.BEACON || e.getClickedBlock().getType() == Material.CHEST || e.getClickedBlock().getType() == Material.TRAPPED_CHEST || e.getClickedBlock().getType() == Material.FURNACE || 
					e.getClickedBlock().getType() == Material.BARREL || e.getClickedBlock().getType() == Material.ENDER_CHEST) {
				e.setCancelled(true);
			}
			if(Build.contains(player)) {
				return;
				}
			else {
				e.setCancelled(true);
			}
		}
	}
}
