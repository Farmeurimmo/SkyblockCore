package main.java.fr.verymc.island.minions;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.util.UUID;

public class Minion {

    private UUID ownerUUID;
    private Integer levelInt;
    private Long id;
    private Location blocLocation;
    private MinionType minionType;
    private BlockFace blockFace;
    private Boolean chestLinked;
    private Block chestBloc;
    private Boolean isAutoSmelt;

    public Minion(Long id, UUID ownerUUID, Integer levelInt, Location blocLocation, MinionType minionType,
                  BlockFace blockFace, Boolean chestLinked, Block chestBloc, Boolean isAutoSmelt) {
        this.id = id;
        this.ownerUUID = ownerUUID;
        this.levelInt = levelInt;
        this.blocLocation = blocLocation;
        this.minionType = minionType;
        this.blockFace = blockFace;
        this.chestLinked = chestLinked;
        this.chestBloc = chestBloc;
        this.isAutoSmelt = isAutoSmelt;
    }

    public Long getID() {
        return id;
    }

    public Integer getLevelInt() {
        return levelInt;
    }

    public void setLevelInt(Integer newlevel) {
        levelInt = newlevel;
    }

    public String getOwnerName() {
        return Bukkit.getOfflinePlayer(ownerUUID).getName();
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

    public BlockFace getBlockFace() {
        return blockFace;
    }

    public boolean isChestLinked() {
        return chestLinked;
    }

    public void setChestLinked(Block blocChest) {
        this.chestBloc = blocChest;
        this.chestLinked = true;
    }

    public Block getChestBloc() {
        return chestBloc;
    }

    public Boolean isAutoSmelt() {
        return isAutoSmelt;
    }

    public void setAutoSmelt(Boolean autoSmelt) {
        isAutoSmelt = autoSmelt;
    }

}
