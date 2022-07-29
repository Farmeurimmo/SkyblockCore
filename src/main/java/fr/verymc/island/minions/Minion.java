package main.java.fr.verymc.island.minions;

import main.java.fr.verymc.utils.ObjectConverter;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class Minion {

    private Integer levelInt;
    private Long id;
    private Location blocLocation;
    private MinionType minionType;
    private BlockFace blockFace;
    private Boolean chestLinked;
    private Block chestBloc;
    private Boolean isAutoSmelt;

    public Minion(Long id, Integer levelInt, Location blocLocation, MinionType minionType,
                  BlockFace blockFace, Boolean chestLinked, Block chestBloc, Boolean isAutoSmelt) {
        this.id = id;
        this.levelInt = levelInt;
        this.blocLocation = blocLocation;
        this.minionType = minionType;
        this.blockFace = blockFace;
        this.chestLinked = chestLinked;
        this.chestBloc = chestBloc;
        this.isAutoSmelt = isAutoSmelt;
    }

    public static String toString(Minion m) {
        return m.getID() + ObjectConverter.SEPARATOR + m.getLevelInt() + ObjectConverter.SEPARATOR + ObjectConverter.instance.locationToString(m.getBlocLocation()) +
                ObjectConverter.SEPARATOR + m.getMinionType().toString() + ObjectConverter.SEPARATOR + m.getBlockFace().toString() + ObjectConverter.SEPARATOR +
                m.isChestLinked() + ObjectConverter.SEPARATOR + ObjectConverter.instance.blockToString(m.getChestBloc()) + ObjectConverter.SEPARATOR + m.isAutoSmelt;
    }

    public static Minion fromString(String str) {
        String[] splited = str.split(ObjectConverter.SEPARATOR);
        Long id = Long.parseLong(splited[0]);
        Integer levelInt = Integer.parseInt(splited[1]);
        Location blocLocation = ObjectConverter.instance.locationFromString(splited[2]);
        MinionType minionType = MinionType.valueOf(splited[3]);
        BlockFace blockFace = BlockFace.valueOf(splited[4]);
        Boolean chestLinked = Boolean.parseBoolean(splited[5]);
        Block chestBloc = ObjectConverter.instance.blockFromString(splited[6]);
        Boolean isAutoSmelt = Boolean.parseBoolean(splited[7]);
        return new Minion(id, levelInt, blocLocation, minionType, blockFace, chestLinked, chestBloc, isAutoSmelt);
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
