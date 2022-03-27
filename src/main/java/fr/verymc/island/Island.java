package main.java.fr.verymc.island;

import main.java.fr.verymc.island.perms.IslandRank;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.UUID;

public class Island {

    private String name;
    private String owner;
    private Location home;
    private int size;
    private int id;
    private HashMap<UUID, IslandRank> members = new HashMap<>();

    public Island(String name, String owner, Location home, int size, int id, HashMap<UUID, IslandRank> members) {
        this.name = name;
        this.owner = owner;
        this.home = home;
        this.size = size;
        this.id = id;
        this.members = members;
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

    public HashMap<UUID, IslandRank> getMembers() {
        return members;
    }

    public void setMembers(HashMap<UUID, IslandRank> members) {
        this.members = members;
    }
}
