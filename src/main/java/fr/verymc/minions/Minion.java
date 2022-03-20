package main.java.fr.verymc.minions;

import main.java.fr.verymc.Main;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.util.UUID;

public class Minion {

    private String ownerName;
    private UUID ownerUUID;
    private Integer levelInt;
    private Long id;
    private Location blocLocation;
    private MinionType minionType;
    private BlockFace blockFace;
    private Boolean chestLinked;
    private Block chestBloc;

    public Minion(Long id, String ownerS, UUID ownerUUID, Integer levelInt, Location blocLocation, MinionType minionType,
                  BlockFace blockFace, Boolean chestLinked, Block chestBloc) {
        this.id = id;
        this.ownerName = ownerS;
        this.ownerUUID = ownerUUID;
        this.levelInt = levelInt;
        this.blocLocation = blocLocation;
        this.minionType = minionType;
        this.blockFace = blockFace;
        this.chestLinked = chestLinked;
        this.chestBloc = chestBloc;
    }

    public Long getID() {
        return id;
    }

    public Integer getLevelInt() {
        return levelInt;
    }

    public void setLevelInt(Integer newlevel) {
        levelInt = newlevel;
        Main.instance.getDataMinion().set("Minions.mineur." + id + ".levelint", newlevel);
        Main.instance.saveDataMinions();
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

    public BlockFace getBlockFace() {
        return blockFace;
    }

    public boolean isChestLinked() {
        return chestLinked;
    }

    public void setChestLinked(Block blocChest) {
        this.chestBloc = blocChest;
        this.chestLinked = true;
        Main.instance.getDataMinion().set("Minions.mineur." + id + ".isChestLinked", true);
        Main.instance.getDataMinion().set("Minions.mineur." + id + ".blocChest", blocChest.getLocation());
        Main.instance.saveDataMinions();
    }

    public Block getChestBloc() {
        return chestBloc;
    }
}
