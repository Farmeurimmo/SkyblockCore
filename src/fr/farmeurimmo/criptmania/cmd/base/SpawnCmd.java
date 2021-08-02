package fr.farmeurimmo.criptmania.cmd.base;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.farmeurimmo.criptmania.Main;
import fr.farmeurimmo.criptmania.utils.GetTeleportDelay;
import fr.farmeurimmo.criptmania.utils.TeleportPlayer;

public class SpawnCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		
		
		if(cmd.getName().equalsIgnoreCase("spawn")) {
			if(sender instanceof Player) {
				final Location Spawn = new Location(Bukkit.getServer().getWorld("world"), -186.5, 110, -63.5, 0, 0);
				final Player player = (Player) sender;
				final int timeLeft = Main.instance1.getCooldown(player.getName());
				if(timeLeft == 0) {
					TeleportPlayer.TeleportPlayerFromRequest(player, Spawn, GetTeleportDelay.GetPlayerTeleportingdelay(player));
				}
				}
			}
		return false;
	}

}
