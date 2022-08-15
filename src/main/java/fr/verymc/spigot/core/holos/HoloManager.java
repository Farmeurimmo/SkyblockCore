package main.java.fr.verymc.spigot.core.holos;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import org.bukkit.Location;

import java.util.ArrayList;

public class HoloManager {

    public static HoloManager instance;
    public ArrayList<Hologram> holograms = new ArrayList<>();

    public HoloManager() {
        instance = this;
    }

    public Hologram getHoloAtLoc(Location location) {
        for (Hologram hologram : holograms) {
            if (hologram.getLocation().equals(location)) {
                return hologram;
            }
        }
        return null;
    }

    public void addHolo(Hologram hologram) {
        holograms.add(hologram);
    }

    public void removeHolo(Hologram hologram) {
        if (holograms.contains(hologram))
            holograms.remove(hologram);
        destroyHolo(hologram);
    }

    public void destroyHolo(Hologram hologram) {
        hologram.delete();
    }
}
