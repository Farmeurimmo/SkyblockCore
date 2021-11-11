package fr.farmeurimmo.verymc.events;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;

public class RedstoneCheck implements Listener {
	
	public static boolean redstone = true;
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onRedstoneActivation(BlockRedstoneEvent e) {
		if(redstone == false) {
			e.getBlock().setType(Material.AIR);
		}
	}

}
