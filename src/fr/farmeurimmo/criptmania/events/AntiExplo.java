package fr.farmeurimmo.criptmania.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

@SuppressWarnings("deprecation")
public class AntiExplo implements Listener {
	
	@EventHandler
	public void onExplo(ExplosionPrimeEvent e) {
		if(e.getEntity().getWorld().getName().equalsIgnoreCase("world")) {
			e.setCancelled(true);
		}
	}
	@EventHandler
	public void OnDmage(EntityDamageEvent e) {
		if(e.getEntity().getWorld().getName().equalsIgnoreCase("world") && e.getEntity() instanceof Player) {
			e.setCancelled(true);
		}
		if(e.getEntity().getLocation().getY() < 10 && e.getEntity().getWorld().getName().equalsIgnoreCase("world") && e.getEntity() instanceof Player) {
			final Location Spawn = new Location(Bukkit.getServer().getWorld("world"), -186.5, 110, -63.5, 0, 0);
			if(e.getEntity() instanceof Player) {
				Player player = (Player) e.getEntity();
				player.teleport(Spawn);
			}
		}
	}
	@EventHandler
	public void dropItem(PlayerDropItemEvent e) {
		if(e.getPlayer().getWorld().getName().equalsIgnoreCase("world")) {
			e.setCancelled(true);
		}
	}
	@EventHandler
	public void pickupItem(PlayerPickupItemEvent e) {
		if(e.getPlayer().getWorld().getName().equalsIgnoreCase("world")) {
			e.setCancelled(true);
		}
	}
	@EventHandler
	public void aaa(EntitySpawnEvent e) {
		if(e.getEntity().getWorld().getName().equalsIgnoreCase("world")) {
			e.setCancelled(true);
		}
	}
	@EventHandler
	public void blockfire(BlockBurnEvent e) {
		if(e.getBlock().getWorld().getName().equalsIgnoreCase("world")) {
			e.setCancelled(true);
		}
	}
}
