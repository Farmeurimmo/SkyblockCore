package main.java.fr.verymc.island;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.HashMap;

public class IslandBlockValues {

    public static IslandBlockValues instance;
    private HashMap<Material, Double> blockValues;

    public IslandBlockValues(HashMap<Material, Double> blockValues) {
        this.blockValues = blockValues;
        instance = this;
    }

    public ArrayList<Material> getMaterials() {
        return new ArrayList<Material>(blockValues.keySet());
    }

    public HashMap<Material, Double> getBlockValues() {
        return blockValues;
    }

    public void setBlockValues(HashMap<Material, Double> blockValues) {
        this.blockValues = blockValues;
    }

    public void addBlockValue(Material block, Double value) {
        blockValues.put(block, value);
    }

    public void removeBlockValue(Material block) {
        blockValues.remove(block);
    }

    public Double getBlockValue(Material block) {
        return blockValues.get(block);
    }

    public boolean hasBlockValue(Material block) {
        return blockValues.containsKey(block);
    }

    public void clearBlockValues() {
        blockValues.clear();
    }
}
