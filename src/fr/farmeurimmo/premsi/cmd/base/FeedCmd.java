package fr.farmeurimmo.premsi.cmd.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.farmeurimmo.premsi.utils.SendActionBar;

public class FeedCmd implements CommandExecutor, TabCompleter {
	
	private HashMap <UUID, Integer > cooldowns = new HashMap < > ();
	public void setCooldown(UUID player, Integer time) {
		if (time == null)
			cooldowns.remove(player);
		else
			cooldowns.put(player, time);
	}
    public int getCooldown(UUID player) {
        return (cooldowns.get(player) == null ? 0 : cooldowns.get(player));
    }

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("feed")) {
				if(!cooldowns.containsKey(player.getUniqueId())) {
				player.setFoodLevel(20);
				SendActionBar.SendActionBarMsg(player, "§aVous avez été rassasié.");
				int timeLeft = getCooldown(player.getUniqueId());
                if (timeLeft == 0) {
                    setCooldown(player.getUniqueId(), 20);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            int timeLeft = getCooldown(player.getUniqueId());
                            if (timeLeft == 0) {
                                cooldowns.remove(player.getUniqueId());
                                this.cancel();
                                return;
                            }
                            setCooldown(player.getUniqueId(), timeLeft - 1);
                        }
                    }.runTaskTimer(Bukkit.getPluginManager().getPlugin("SkyblockCore"), 20, 20);
                }
				}
				else {
					SendActionBar.SendActionBarMsg(player, "§cErreur, il reste " + getCooldown(player.getUniqueId()) + " seconde(s) avant réutilisation");
				}
			}
			else {
				SendActionBar.SendActionBarMsg(player, "§cPermissions insuffisantes.");
			}
		}
		return false;
	}
	@Override
	 public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		 ArrayList<String> subcmd = new ArrayList<String>();
	        if (cmd.getName().equalsIgnoreCase("feed")) {
	            if (args.length >= 0){
	            	subcmd.add("");
	            	Collections.sort(subcmd);
	            }
	        }
			return subcmd;
	 }
}
