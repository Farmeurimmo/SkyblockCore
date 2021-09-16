package fr.farmeurimmo.premsi.featherfly;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import fr.farmeurimmo.premsi.utils.SendActionBar;

public class CountdownFly {
	
public static HashMap<UUID, Integer> fly = new HashMap<>();
	
	public static void setCooldown(UUID uuid, Integer time) {
		if (time == null)
			fly.remove(uuid);
		else
			fly.put(uuid, time);
	}
    public static int getCooldown(UUID player) {
        return (fly.get(player) == null ? 0 : fly.get(player));
    }
    
    public static void EnableFlyForPlayer(Player player, String dure, String sb) {
    	int DurationInSec = 0;
    	int DurationGlobal = Integer.parseInt(sb);
    	if(dure.equalsIgnoreCase("heures")) {
    		DurationInSec = DurationGlobal * 60 * 60;
    	}
    	if(dure.equalsIgnoreCase("minutes")) {
    		DurationInSec = DurationGlobal * 60;
    	}
    	if(dure.equalsIgnoreCase("secondes")) {
    		DurationInSec = DurationGlobal;
    	}
    	setCooldown(player.getUniqueId(), DurationInSec);
    	player.setAllowFlight(true);
    	if(!player.getWorld().getName().equalsIgnoreCase("world")) {
    	player.setFlying(true);	
    	}
    	CountDown(player);
    }
    public static void CountDown(Player player) {
    	if(player.isOnline() && fly.containsKey(player.getUniqueId())) {
    		int TimeLeft = getCooldown(player.getUniqueId());
    		
    	if(getCooldown(player.getUniqueId()) - 1 != -1 && getCooldown(player.getUniqueId()) >= 0) {
    		
    		if(!player.getWorld().getName().equalsIgnoreCase("world")) {
    	TimeLeft = TimeLeft - 1;
    		}
    	
    	if(player.getAllowFlight() == false && !player.getWorld().getName().equalsIgnoreCase("world")) {
    	player.setAllowFlight(true);
    	}
    	
    	int timeforconv = TimeLeft;
    	int nHours = (timeforconv%86400)/3600;
        int nMin = ((timeforconv%86400)%3600) /60;
        int nSec =(((timeforconv%86400)%3600)%60);
        
    	String messagetimeleft = "§aTemps restant: " + nHours + ":" + nMin + ":" + nSec;
    	CountdownFly.setCooldown(player.getUniqueId(), TimeLeft);
    	SendActionBar.SendActionBarMsg(player, messagetimeleft);
    	
    	Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
			public void run() {
				CountDown(player);
			}
		}, 20);
    	}
    	else {
    		if(player.getGameMode() != GameMode.CREATIVE && player.getGameMode() != GameMode.SPECTATOR) {
    		player.setAllowFlight(false);
    		player.setFlying(false);
    		player.chat("/is home");
    		player.sendMessage("§6Fin du fly, téléportation sur votre île..");
    		}
    		SendActionBar.SendActionBarMsg(player, "§6Fin du fly.");
    		fly.remove(player.getUniqueId());
    	}
    	}
    	else {
    		return;
    	}
    }
}
