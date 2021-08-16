package fr.farmeurimmo.criptmania.events;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.farmeurimmo.criptmania.utils.SendActionBar;

public class SwitchWorld implements Listener {
	
	@EventHandler
	public void SwitchWorldInServer(PlayerChangedWorldEvent e) {
		Player player = e.getPlayer();
		if(player.getWorld().getName().equalsIgnoreCase("world")) {
			player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 999999999, 0));
		if(player.getAllowFlight() == true) {
			if(player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) return;
			player.setAllowFlight(false);
			player.setFlying(false);
			player.setAbsorptionAmount(20);
			SendActionBar.SendActionBarMsg(player, "§aFly désactivé");
		}
		}
		if(!player.getWorld().getName().equalsIgnoreCase("world")) {
			player.removePotionEffect(PotionEffectType.SATURATION);
		}
		if(player.getWorld().getName().equalsIgnoreCase("IlesSkyblock")) {
			if(player.hasPermission("fly")) {
				if(player.getAllowFlight() == false) {
				player.setAllowFlight(true);
				player.setFlying(true);
				SendActionBar.SendActionBarMsg(player, "§aFly activé");
				}
			}
		}
	}

}
