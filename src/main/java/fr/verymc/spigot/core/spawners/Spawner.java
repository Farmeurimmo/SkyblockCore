package main.java.fr.verymc.spigot.core.spawners;

import main.java.fr.verymc.spigot.utils.ObjectConverter;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

public class Spawner {

    private Location loc;
    private double amount;
    private EntityType entityType;

    public Spawner(Location loc, double amount, EntityType entityType) {
        this.loc = loc;
        this.amount = amount;
        this.entityType = entityType;
    }

    public static String spawnerToString(Spawner spawner) {
        return ObjectConverter.instance.locationToString(spawner.getLoc()) + ObjectConverter.SEPARATOR + spawner.getAmount() +
                ObjectConverter.SEPARATOR + spawner.getEntityType().toString();
    }

    public static Spawner stringToSpawner(String string) {
        String[] split = string.split(ObjectConverter.SEPARATOR);
        return new Spawner(ObjectConverter.instance.locationFromString(split[0]), Double.parseDouble(split[1]), EntityType.valueOf(split[2]));
    }

    public Location getLoc() {
        return loc;
    }

    public void setLoc(Location loc) {
        this.loc = loc;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public void incrementAmount() {
        this.amount++;
    }
}
