package main.java.fr.verymc.island.upgrade;

import org.bukkit.Material;

import java.util.HashMap;

public class IslandUpgradeGenerator {

    private int level;
    private IslandUpgradesType type;

    private HashMap<Integer, Material> materials = new HashMap<>();

    public IslandUpgradeGenerator(int level, IslandUpgradesType type, boolean defaultGen) {
        this.level = level;
        this.type = type;
        if (defaultGen) {
            defaultGenValues();
        }
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public IslandUpgradesType getType() {
        return type;
    }

    public void setType(IslandUpgradesType type) {
        this.type = type;
    }

    public void defaultGenValues() {
        for (int i = 0; i <= 87; i++) {
            materials.put(i, Material.COBBLESTONE);
        }
        for (int i = 88; i <= 96; i++) {
            materials.put(i, Material.IRON_ORE);
        }
        for (int i = 97; i <= 99; i++) {
            materials.put(i, Material.DIAMOND_ORE);
        }
    }

    public HashMap<Integer, Material> getMaterials() {
        return materials;
    }

}
