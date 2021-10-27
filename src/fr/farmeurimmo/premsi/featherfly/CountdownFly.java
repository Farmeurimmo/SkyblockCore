package fr.farmeurimmo.premsi.featherfly;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import fr.farmeurimmo.premsi.utils.SendActionBar;

public class CountdownFly {
	
public static HashMap<String, Integer> fly = new HashMap<>();
	
	public static void setCooldown(String uuid, Integer time) {
		if (time == null)
			fly.remove(uuid);
		else
			fly.put(uuid, time);
	}
    public static int getCooldown(Player player) {
        return (fly.get(player.getName()) == null ? 0 : fly.get(player.getName()));
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
    	setCooldown(player.getName(), DurationInSec);
    	player.setAllowFlight(true);
    	if(!player.getWorld().getName().equalsIgnoreCase("world")) {
    	player.setFlying(true);	
    	}
    	CountDown(player.getName());
    }
    public static void CountDown(String player) {
    	if(!fly.containsKey(player)) {
    		return;
    	}
    	if(Bukkit.getPlayer(player) != null) {
    		if(Bukkit.getPlayer(player).isOnline()) {
    			Player p = Bukkit.getPlayer(player);
    		int TimeLeft = getCooldown(p);
    		
    	if(getCooldown(p) - 1 != -1 && getCooldown(p) >= 0) {
    		
    		if(!p.getWorld().getName().equalsIgnoreCase("world")) {
    	TimeLeft = TimeLeft - 1;
    		}
    	
    	if(p.getAllowFlight() == false && !p.getWorld().getName().equalsIgnoreCase("world")) {
    	p.setAllowFlight(true);
    	}
    	
    	int timeforconv = TimeLeft;
    	int nHours = (timeforconv%86400)/3600;
        int nMin = ((timeforconv%86400)%3600) /60;
        int nSec =(((timeforconv%86400)%3600)%60);
        
    	String messagetimeleft = "§aTemps restant: " + nHours + ":" + nMin + ":" + nSec;
    	CountdownFly.setCooldown(p.getName(), TimeLeft);
    	SendActionBar.SendActionBarMsg(p, messagetimeleft);
    	
    	}
    	else {
    		if(p.getGameMode() != GameMode.CREATIVE && p.getGameMode() != GameMode.SPECTATOR) {
    		p.setAllowFlight(false);
    		p.setFlying(false);
    		p.chat("/is home");
    		p.sendMessage("§6Fin du fly, téléportation sur votre île..");
    		}
    		SendActionBar.SendActionBarMsg(p, "§6Fin du fly.");
    		fly.remove(p.getName());
    		return;
    	}
    		}
    	}
    	Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
			public void run() {
				CountDown(player);
			}
		}, 20);
    }
}
