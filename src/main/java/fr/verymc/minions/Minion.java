package main.java.fr.verymc.minions;

import org.bukkit.Location;

import java.util.UUID;

public class Minion {

    private String ownerName;
    private UUID ownerUUID;
    private int levelInt;
    private long id;
    private Location blocLocation;
    private MinionType minionType;

    public Minion(Long id, String ownerS, UUID ownerUUID, int levelInt, Location blocLocation, MinionType minionType) {
        this.id = id;
        this.ownerName = ownerS;
        this.ownerUUID = ownerUUID;
        this.levelInt = levelInt;
        this.blocLocation = blocLocation;
        this.minionType = minionType;
    }

    public long getID() {
        return id;
    }

    public int getLevelInt() {
        return levelInt;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public UUID getOwnerUUID() {
        return ownerUUID;
    }

    public Location getBlocLocation() {
        return blocLocation;
    }

    public MinionType getMinionType() {
        return minionType;
    }
}
