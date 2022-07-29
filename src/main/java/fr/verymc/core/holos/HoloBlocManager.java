package main.java.fr.verymc.core.holos;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import org.bukkit.Location;

import java.util.ArrayList;

public class HoloBlocManager {

    public static HoloBlocManager instance;
    public ArrayList<Hologram> holograms = new ArrayList<>();

    public HoloBlocManager() {
        instance = this;
        //checkForStackerHolo();
    }

    public Hologram getHoloAtLoc(Location location) {
        for (Hologram hologram : holograms) {
            if (hologram.getLocation().equals(location)) {
                return hologram;
            }
        }
        return null;
    }

    /*public void checkForStackerHolo() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.instance, new Runnable() {
            @Override
            public void run() {
                for (Island island : IslandManager.instance.islands) {
                    for (Chest chest : island.getChests()) {
                        if (chest.isStacker() && chest.getBlock().getChunk().isLoaded()) {
                            Location loc = chest.getBlock().clone().add(0.5, 1.3, 0.5);
                            Hologram hologram = getHoloAtLoc(loc);
                            if (hologram != null) {
                                if (chest.getAmount() <= 1) {
                                    hologram.delete();
                                    holograms.remove(hologram);
                                    continue;
                                }
                                hologram.clearLines();
                                hologram.appendTextLine("§6x" + NumberFormat.getInstance().format(chest.getAmount()));
                            } else {
                                if (chest.getAmount() <= 1) continue;
                                hologram = HologramsAPI.createHologram(Main.instance, loc);
                                hologram.appendTextLine("§6x" + NumberFormat.getInstance().format(chest.getAmount()));
                                holograms.add(hologram);
                            }
                        }
                    }
                }
            }
        }, 0, 100L);
    }*/
}
