package main.java.fr.verymc.spigot.island.upgrade;

import main.java.fr.verymc.spigot.core.eco.EcoAccountsManager;
import main.java.fr.verymc.spigot.island.Island;
import main.java.fr.verymc.spigot.island.IslandManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;

public class IslandUpgradeGenerator {

    private int level;

    private LinkedHashMap<Material, Integer> materials = new LinkedHashMap<>();

    public IslandUpgradeGenerator(int level) {
        this.level = level;
        setGenValues(level);
    }

    public static double getMoneyCostFromLevel(int level) {
        if (level == 0) {
            return 0;
        } else if (level == 1) {
            return 50000;
        } else if (level == 2) {
            return 100000;
        } else if (level == 3) {
            return 150000;
        } else if (level == 4) {
            return 200000;
        } else if (level == 5) {
            return 250000;
        }
        return 0;
    }

    public static double getCrystalCostFromLevel(int level) {
        if (level == 0) {
            return 0;
        } else if (level == 1) {
            return 40;
        } else if (level == 2) {
            return 80;
        } else if (level == 3) {
            return 120;
        } else if (level == 4) {
            return 160;
        } else if (level == 5) {
            return 200;
        }
        return 0;
    }

    public boolean upOfOneLevel(Player player) {
        if (this.level >= 5) {
            return false;
        }
        boolean bankPayMoney = false;
        Island playerIsland = IslandManager.instance.getPlayerIsland(player);
        if (playerIsland.getBank().getCrystaux() <= getCrystalCostFromLevel(this.level + 1)) return false;
        if (playerIsland.getBank().getMoney() >= getMoneyCostFromLevel(this.level + 1)) bankPayMoney = true;
        if (!EcoAccountsManager.instance.checkForFounds(player, getMoneyCostFromLevel((this.level + 1))) && !bankPayMoney)
            return false;
        addLevel();
        if (bankPayMoney) {
            playerIsland.getBank().removeMoney(getMoneyCostFromLevel(this.level));
        } else {
            EcoAccountsManager.instance.removeFounds(player, getMoneyCostFromLevel(this.level), true);
        }
        playerIsland.getBank().removeCrystaux(getCrystalCostFromLevel(this.level));
        setGenValues(this.level);
        return true;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void addLevel() {
        level++;
    }

    public void setGenValues(int level) {
        if (level == 0) {
            materials.clear();
            materials.put(Material.COBBLESTONE, 78); // 78%
            materials.put(Material.COAL_ORE, 10); // 10%
            materials.put(Material.IRON_ORE, 9); // 9%
            materials.put(Material.DIAMOND_ORE, 3); // 3%
        } else if (level == 1) {
            materials.clear();
            materials.put(Material.COBBLESTONE, 73); // 73%
            materials.put(Material.COAL_ORE, 10); // 10%
            materials.put(Material.IRON_ORE, 10); // 10%
            materials.put(Material.GOLD_ORE, 2); // 2%
            materials.put(Material.DIAMOND_ORE, 5); // 5%
        } else if (level == 2) {
            materials.clear();
            materials.put(Material.COBBLESTONE, 70); // 70%
            materials.put(Material.COAL_ORE, 8); // 8%
            materials.put(Material.IRON_ORE, 10); // 10%
            materials.put(Material.GOLD_ORE, 5); // 5%
            materials.put(Material.LAPIS_ORE, 2); // 2%
            materials.put(Material.DIAMOND_ORE, 5); // 5%
        } else if (level == 3) {
            materials.clear();
            materials.put(Material.COBBLESTONE, 63); // 63%
            materials.put(Material.COAL_ORE, 7); // 7%
            materials.put(Material.IRON_ORE, 11); // 11%
            materials.put(Material.GOLD_ORE, 8); // 8%
            materials.put(Material.LAPIS_ORE, 4); // 5%
            materials.put(Material.DIAMOND_ORE, 7); // 7%
        } else if (level == 4) {
            materials.clear();
            materials.put(Material.COBBLESTONE, 55); // 55%
            materials.put(Material.COAL_ORE, 5); // 5%
            materials.put(Material.IRON_ORE, 12); // 12%
            materials.put(Material.GOLD_ORE, 10); // 10%
            materials.put(Material.LAPIS_ORE, 5); // 5%
            materials.put(Material.DIAMOND_ORE, 10); // 10%
            materials.put(Material.EMERALD_ORE, 2); // 2%

        } else if (level == 5) {
            materials.clear();
            materials.put(Material.COBBLESTONE, 45); // 45%
            materials.put(Material.COAL_ORE, 5); // 5%
            materials.put(Material.IRON_ORE, 13); // 13%
            materials.put(Material.GOLD_ORE, 12); // 12%
            materials.put(Material.LAPIS_ORE, 6); // 6%
            materials.put(Material.DIAMOND_ORE, 12); // 12%
            materials.put(Material.EMERALD_ORE, 5); // 5%
            materials.put(Material.ANCIENT_DEBRIS, 2); // 2%
        }

    }

    public LinkedHashMap<Material, Integer> getMaterials() {
        return materials;
    }

}
