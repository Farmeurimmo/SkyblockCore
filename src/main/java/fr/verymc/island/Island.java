package main.java.fr.verymc.island;

import main.java.fr.verymc.island.perms.IslandPerms;
import main.java.fr.verymc.island.perms.IslandRank;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Island {

    private String name;
    private String owner;
    private UUID ownerUUID;
    private Location home;
    private int size;
    private int id;
    private HashMap<UUID, IslandRank> members = new HashMap<>();
    private HashMap<IslandRank, ArrayList<IslandPerms>> permsPerRanks = new HashMap<>();

    public Island(String name, String owner, UUID ownerUUID, Location home, int size, int id, HashMap<UUID, IslandRank> members) {
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

    public boolean promote(UUID uuid) {
        if (members.containsKey(uuid)) {
            members.put(uuid, IslandRank.getNextRank(getIslandRankFromUUID(uuid)));
            return true;
        }
        return false;
    }

    public boolean demote(UUID uuid) {
        if (members.containsKey(uuid)) {
            members.put(uuid, IslandRank.getPreviousRank(getIslandRankFromUUID(uuid)));
            return true;
        }
        return false;
    }

    public boolean kickFromIsland(UUID uuid) {
        if (members.containsKey(uuid)) {
            members.remove(uuid);
            return true;
        }
        return false;
    }

    public ArrayList<IslandPerms> getPerms(IslandRank rank) {
        return permsPerRanks.get(rank);
    }

    public IslandRank getIslandRankFromUUID(UUID uuid) {
        return members.get(uuid);
    }

    public HashMap<UUID, IslandRank> getMembers() {
        return members;
    }

    public void setMembers(HashMap<UUID, IslandRank> members) {
        this.members = members;
    }

    public UUID getOwnerUUID() {
        return ownerUUID;
    }

    public void setOwnerUUID(UUID ownerUUID) {
        this.ownerUUID = ownerUUID;
    }
}
