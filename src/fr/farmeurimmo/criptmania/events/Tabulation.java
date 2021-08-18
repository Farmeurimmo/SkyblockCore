package fr.farmeurimmo.criptmania.events;

import java.util.Arrays;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;

public class Tabulation implements Listener {
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void OnTabulation(PlayerCommandSendEvent e) {
		e.getCommands().clear();
		e.getCommands().addAll(Arrays.asList("is","shop","sellall","warp","warps","menu","farm2win","spawn","hat","feed","fly","dailyfly"
				,"craft","enchantement","wiki","money","baltop","bar","tpa","tpacancel","tpyes","tpno","pay","challenges","c","atout",
				"atouts"));
		if(e.getPlayer().hasPermission("*")) {
			e.getCommands().addAll(Arrays.asList("gm","gmc","gms","gmsp","gma","ban","tempban","mute","ban-ip","pardon","pardon-ip","build","lp","lpb","afkmine","clear","checkfly",
					"ss","sanctionset","invsee","redstone","datafile","key"));
		}
	}
}
