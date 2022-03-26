package main.java.fr.verymc.island;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Island {

    private String name;
    private String owner;
    private Location home;
    private int size;
    private int id;

    public Island(String name, String owner, Location home, int size, int id) {
        this.name = name;
        this.owner = owner;
        this.home = home;
        this.size = size;
        this.id = id;
        Bukkit.broadcastMessage("[Island] " + name + " has been created by " + owner);
        Bukkit.broadcastMessage("[Island] " + name + " is at " + home.getBlockX() + " " + home.getBlockY() + " " + home.getBlockZ());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Location getHome() {
        return home;
    }

    public void setHome(Location home) {
        this.home = home;
    }

    public int getSizeLevel() {
        return size;
    }

    public void setSizeLevel(int size) {
        this.size = size;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
