package main.java.fr.verymc.utils;

import main.java.fr.verymc.Main;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public class ObjectConverter {

    public static final String LOCATION_SEPARATOR = ":";
    public static ObjectConverter instance;

    public ObjectConverter() {
        instance = this;
    }

    public String locationToString(@NotNull Location location) {
        return location.getX() + LOCATION_SEPARATOR + location.getY() + LOCATION_SEPARATOR + location.getZ() + LOCATION_SEPARATOR + location.getPitch() +
                LOCATION_SEPARATOR + location.getYaw();
    }

    public Location locationFromString(@NotNull String string) {
        String[] splited = string.split(LOCATION_SEPARATOR);
        double x = Double.parseDouble(splited[0]);
        double y = Double.parseDouble(splited[1]);
        double z = Double.parseDouble(splited[2]);
        float pitch = Float.parseFloat(splited[3]);
        float yaw = Float.parseFloat(splited[4]);
        return new Location(Main.instance.mainWorld, x, y, z, yaw, pitch);
    }

}




