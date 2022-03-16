package main.java.fr.verymc.minions;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;

import java.util.UUID;

public class Minion {

    private String ownerName;
    private UUID ownerUUID;
    private Integer levelInt;
    private Long id;
    private Location blocLocation;
    private MinionType minionType;
    private Integer delay;
    private BlockFace blockFace;

    public Minion(Long id, String ownerS, UUID ownerUUID, Integer levelInt, Location blocLocation, MinionType minionType, Integer delay, BlockFace blockFace) {
        this.id = id;
        this.ownerName = ownerS;
        this.ownerUUID = ownerUUID;
        this.levelInt = levelInt;
        this.blocLocation = blocLocation;
        this.minionType = minionType;
        this.delay = delay;
        this.blockFace = blockFace;
    }

    public Long getID() {
        return id;
    }

    public Integer getLevelInt() {
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

    public Integer getDelay() { return delay; }

    public BlockFace getBlockFace() { return blockFace; }

}
