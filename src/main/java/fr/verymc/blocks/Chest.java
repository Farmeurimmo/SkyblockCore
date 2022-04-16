package main.java.fr.verymc.blocks;

import org.bukkit.Location;

import java.util.UUID;

public class Chest {

    public int type;
    public Location block;
    public UUID owner;
    public long id;
    public String ownerName;
    private Long chunkKey;

    public Chest(int type, Location block, UUID owner, String ownerName, long id, Long chunkKey) {
        this.type = type;
        this.block = block;
        this.owner = owner;
        this.ownerName = ownerName;
        this.id = id;
        this.chunkKey = chunkKey;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Location getBlock() {
        return block;
    }

    public void setBlock(Location block) {
        this.block = block;
    }

    public UUID getOwner() {
        return owner;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Long getChunkKey() {
        return chunkKey;
    }

    public void setChunkKey(Long chunkKey) {
        this.chunkKey = chunkKey;
    }
}
