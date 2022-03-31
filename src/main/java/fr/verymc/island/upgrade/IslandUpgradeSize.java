package main.java.fr.verymc.island.upgrade;

import main.java.fr.verymc.eco.EcoAccountsManager;
import org.bukkit.entity.Player;

public class IslandUpgradeSize {

    private int size;
    private double price;
    private int level;
    private IslandUpgradesType type;

    public IslandUpgradeSize(int size, double price, int level, IslandUpgradesType type) {
        this.size = size;
        this.price = price;
        this.level = level;
        this.type = type;
    }

    public static int getSizeFromLevel(int level) {
        if (level == 0) return 50;
        if (level == 1) return 100;
        if (level == 2) return 150;
        if (level == 3) return 200;
        if (level == 4) return 250;
        return 50;
    }

    public static double getPriceFromLevel(int level) {
        if (level == 0) return 0;
        if (level == 1) return 20000;
        if (level == 2) return 30000;
        if (level == 3) return 40000;
        if (level == 4) return 50000;
        return 0;
    }

    public int getSize() {
        return size;
    }

    public double getPrice() {
        return price;
    }

    public int getLevel() {
        return level;
    }

    public IslandUpgradesType getType() {
        return type;
    }

    public void setType(IslandUpgradesType type) {
        this.type = type;
    }

    public boolean upOfOneLevel(Player player) {
        if (this.level + 1 > 4) return false;
        if (!EcoAccountsManager.instance.checkForFounds(player, getPriceFromLevel((this.level + 1)))) return false;
        this.level++;
        this.size = getSizeFromLevel(this.level);
        this.price = getPriceFromLevel(this.level);
        EcoAccountsManager.instance.removeFounds(player, getPriceFromLevel(this.level), true);
        return true;
    }

    public boolean setNewLevel(int level) {
        if (level > 4) return false;
        this.size = getSizeFromLevel(level);
        this.price = getPriceFromLevel(level);
        this.level = level;
        return true;
    }
}

