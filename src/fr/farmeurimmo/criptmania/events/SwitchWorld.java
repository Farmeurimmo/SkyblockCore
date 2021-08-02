package fr.farmeurimmo.criptmania.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import fr.farmeurimmo.criptmania.utils.SendActionBar;

public class SwitchWorld implements Listener {
	
	@EventHandler
	public void SwitchWorldInServer(PlayerChangedWorldEvent e) {
		Player player = e.getPlayer();
		if(player.getWorld().getName().equalsIgnoreCase("world")) {
		if(player.getAllowFlight() == true) {
			player.setAllowFlight(false);
			player.setFlying(false);
			SendActionBar.SendActionBarMsg(player, "§aFly désactivé");
		}
		}
		if(player.getWorld().getName().equalsIgnoreCase("IlesSkyblock")) {
			if(player.hasPermission("fly")) {
				player.setAllowFlight(true);
				player.setFlying(true);
				SendActionBar.SendActionBarMsg(player, "§aFly activé");
			}
		}
	}

}
