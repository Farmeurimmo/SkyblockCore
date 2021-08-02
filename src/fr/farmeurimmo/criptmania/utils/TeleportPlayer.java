package fr.farmeurimmo.criptmania.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.farmeurimmo.criptmania.Main;

public class TeleportPlayer {
	
	public static void TeleportPlayerFromRequest(Player player, Location loc, int temp) {
		
		final int timeLeft = Main.instance1.getCooldown(player.getName());
		if(GetTeleportDelay.GetPlayerTeleportingdelay(player) == 0) {
			player.teleport(loc);
			SendActionBar.SendActionBarMsg(player, "§6Téléportation effectuée !");
			return;
		}
		else {
			if (timeLeft == 0) {
				if(timeLeft == 1) {
				SendActionBar.SendActionBarMsg(player, "§6Téléportation dans " + timeLeft + " seconde...");
				} else {
					SendActionBar.SendActionBarMsg(player, "§6Téléportation dans " + timeLeft + " secondes...");
				}
				Main.instance1.setCooldown(player.getName(), temp);
				new BukkitRunnable() {
					@Override
					public void run() {
						int timeLeft = Main.instance1.getCooldown(player.getName());
						if (timeLeft == 0) {
							Main.instance1.setCooldown(player.getName(), 0);
							player.teleport(loc);
							SendActionBar.SendActionBarMsg(player, "§6Téléportation effectuée !");
							this.cancel();
							return;
						}
						Main.instance1.setCooldown(player.getName(), timeLeft - 1);
					if(timeLeft != 0 && timeLeft != 1) {
						SendActionBar.SendActionBarMsg(player, "§6Téléportation dans " + timeLeft + " secondes...");
					}
					else if(timeLeft == 1) {
						SendActionBar.SendActionBarMsg(player, "§6Téléportation dans " + timeLeft + " seconde...");
					}
					}
				}.runTaskTimer(Bukkit.getPluginManager().getPlugin("SkyblockCore"), 20, 20);
			}
			
			}
		
	}
}
