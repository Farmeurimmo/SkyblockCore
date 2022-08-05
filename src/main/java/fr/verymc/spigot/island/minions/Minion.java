package main.java.fr.verymc.spigot.island.minions;

import main.java.fr.verymc.spigot.utils.ObjectConverter;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class Minion {

    private Integer levelInt;
    private Location blocLocation;
    private MinionType minionType;
    private BlockFace blockFace;
    private Boolean chestLinked;
    private Block chestBloc;
    private Boolean isAutoSmelt;

    public Minion(Integer levelInt, Location blocLocation, MinionType minionType,
                  BlockFace blockFace, Boolean chestLinked, Block chestBloc, Boolean isAutoSmelt) {
        this.levelInt = levelInt;
        this.blocLocation = blocLocation;
        this.minionType = minionType;
        this.blockFace = blockFace;
        this.chestLinked = chestLinked;
        this.chestBloc = chestBloc;
        this.isAutoSmelt = isAutoSmelt;
    }

    public static String toString(Minion m) {
        return m.getLevelInt() + ObjectConverter.SEPARATOR + ObjectConverter.instance.locationToString(m.getBlocLocation()) +
                ObjectConverter.SEPARATOR + m.getMinionType().toString() + ObjectConverter.SEPARATOR + m.getBlockFace().toString() + ObjectConverter.SEPARATOR +
                m.isChestLinked() + ObjectConverter.SEPARATOR + (m.getChestBloc() == null ? "a" : ObjectConverter.instance.blockToString(m.getChestBloc())) +
                ObjectConverter.SEPARATOR + m.isAutoSmelt;
    }

    public static Minion fromString(String str) {
        String[] splited = str.split(ObjectConverter.SEPARATOR);
        Integer levelInt = Integer.parseInt(splited[0]);
        Location blocLocation = ObjectConverter.instance.locationFromString(splited[1]);
        MinionType minionType = MinionType.valueOf(splited[2]);
        BlockFace blockFace = BlockFace.valueOf(splited[3]);
        Boolean chestLinked = Boolean.parseBoolean(splited[4]);
        Block chestBloc = (splited[5].equalsIgnoreCase("a") ? null : ObjectConverter.instance.blockFromString(splited[5]));
        Boolean isAutoSmelt = Boolean.parseBoolean(splited[6]);
        return new Minion(levelInt, blocLocation, minionType, blockFace, chestLinked, chestBloc, isAutoSmelt);
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

    public void setBlocLocation(Location blocLocation) {
        this.blocLocation = blocLocation;
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

    public void setChestBloc(Block blocChest) {
        this.chestBloc = blocChest;
    }

    public Boolean isAutoSmelt() {
        return isAutoSmelt;
    }

    public void setAutoSmelt(Boolean autoSmelt) {
        isAutoSmelt = autoSmelt;
    }

}
