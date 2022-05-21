package main.java.fr.verymc.events;

import org.bukkit.event.Listener;

public class RedstoneCheck implements Listener {

    public static boolean redstone = true;

    /*@EventHandler(priority = EventPriority.MONITOR)
    public void onRedstoneActivation(BlockRedstoneEvent e) {
        if (redstone == false) {
            e.getBlock().setType(Material.AIR);
        }
    }*/

}
