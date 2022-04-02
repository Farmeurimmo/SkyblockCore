package main.java.fr.verymc.island.upgrade;

import main.java.fr.verymc.eco.EcoAccountsManager;
import main.java.fr.verymc.island.Island;
import main.java.fr.verymc.island.IslandManager;
import org.bukkit.entity.Player;

public class IslandUpgradeSize {

    private int size;
    private double priceMoney;
    private double priceCrytaux;
    private int level;
    private IslandUpgradesType type;

    public IslandUpgradeSize(int size, double priceMoney, double priceCrytaux, int level, IslandUpgradesType type) {
        this.size = size;
        this.priceMoney = priceMoney;
        this.priceCrytaux = priceCrytaux;
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

    public static double getPriceMoneyFromLevel(int level) {
        if (level == 0) return 0;
        if (level == 1) return 20000;
        if (level == 2) return 30000;
        if (level == 3) return 40000;
        if (level == 4) return 50000;
        return 0;
    }

    public static double getPriceCrytauxFromLevel(int level) {
        if (level == 0) return 0;
        if (level == 1) return 50;
        if (level == 2) return 80;
        if (level == 3) return 120;
        if (level == 4) return 180;
        return 0;
    }

    public int getSize() {
        return size;
    }

    public double getPriceMoney() {
        return priceMoney;
    }

    public double getPriceCrytaux() {
        return priceCrytaux;
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
        boolean bankPayMoney = false;
        Island playerIsland = IslandManager.instance.getPlayerIsland(player);
        if (playerIsland.getBank().getCrystaux() < getPriceCrytauxFromLevel(this.level + 1)) return false;
        if (playerIsland.getBank().getMoney() >= getPriceMoneyFromLevel(this.level + 1)) bankPayMoney = true;
        if (!EcoAccountsManager.instance.checkForFounds(player, getPriceMoneyFromLevel((this.level + 1))) && !bankPayMoney)
            return false;
        this.level++;
        this.size = getSizeFromLevel(this.level);
        this.priceMoney = getPriceMoneyFromLevel(this.level);
        this.priceCrytaux = getPriceCrytauxFromLevel(this.level);
        if (bankPayMoney) {
            playerIsland.getBank().removeMoney(getPriceMoneyFromLevel(this.level));
        } else {
            EcoAccountsManager.instance.removeFounds(player, getPriceMoneyFromLevel(this.level), true);
        }
        playerIsland.getBank().removeCrystaux(getPriceCrytauxFromLevel(this.level));
        IslandManager.instance.setWorldBorderForAllPlayerOnIsland(playerIsland);
        return true;
    }

    public boolean setNewLevel(int level) {
        if (level > 4) return false;
        this.level = level;
        this.size = getSizeFromLevel(level);
        this.priceMoney = getPriceMoneyFromLevel(level);
        this.priceCrytaux = getPriceCrytauxFromLevel(level);
        return true;
    }
}

