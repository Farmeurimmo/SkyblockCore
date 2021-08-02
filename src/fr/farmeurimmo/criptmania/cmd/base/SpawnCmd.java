package fr.farmeurimmo.criptmania.cmd.base;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.farmeurimmo.criptmania.Main;
import fr.farmeurimmo.criptmania.utils.SendActionBar;

public class SpawnCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		
		
		if(cmd.getName().equalsIgnoreCase("spawn")) {
			if(sender instanceof Player) {
				final Location Spawn = new Location(Bukkit.getServer().getWorld("world"), -186.5, 110, -63.5, 0, 0);
				final Player player = (Player) sender;
				final int timeLeft = Main.instance1.getCooldown(player.getName());
				if(player.hasPermission("spawninstant.*")) {
					player.teleport(Spawn);
					SendActionBar.SendActionBarMsg(player, "§6Téléportation effectuée !");
					return true;
				}
				else {
					if (timeLeft == 0) {
						SendActionBar.SendActionBarMsg(player, "§6Téléportation dans 5 secondes...");
						int cooldown = 5;
						Main.instance1.setCooldown(player.getName(), cooldown);
						new BukkitRunnable() {
							@Override
							public void run() {
								int timeLeft = Main.instance1.getCooldown(player.getName());
								if (timeLeft == 0) {
									Main.instance1.setCooldown(player.getName(), 0);
									player.teleport(Spawn);
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
		return false;
	}

}
