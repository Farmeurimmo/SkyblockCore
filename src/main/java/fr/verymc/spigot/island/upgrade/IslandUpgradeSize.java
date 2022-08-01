package main.java.fr.verymc.spigot.island.upgrade;

import main.java.fr.verymc.spigot.core.eco.EcoAccountsManager;
import main.java.fr.verymc.spigot.island.Island;
import main.java.fr.verymc.spigot.island.IslandManager;
import org.bukkit.entity.Player;

public class IslandUpgradeSize {

    private int level;

    public IslandUpgradeSize(int level) {
        this.level = level;
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
        if (level == 1) return 20000;
        if (level == 2) return 30000;
        if (level == 3) return 40000;
        if (level == 4) return 50000;
        return 0;
    }

    public static double getPriceCrytauxFromLevel(int level) {
        if (level == 1) return 50;
        if (level == 2) return 80;
        if (level == 3) return 120;
        if (level == 4) return 180;
        return 0;
    }

    public int getLevel() {
        return level;
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
        return true;
    }
}

