package main.java.fr.verymc.island.upgrade;

import main.java.fr.verymc.core.eco.EcoAccountsManager;
import main.java.fr.verymc.island.Island;
import main.java.fr.verymc.island.IslandManager;
import org.bukkit.entity.Player;

public class IslandUpgradeMember {

    private int level;

    public IslandUpgradeMember(int level) {
        ;
        this.level = level;
    }

    public static Integer getMaxMembers(int level) {
        switch (level) {
            case 1:
                return 8;
            case 2:
                return 10;
            case 3:
                return 12;
            case 4:
                return 14;
            case 5:
                return 16;
            default:
                return 6;
        }
    }

    public static double getPriceMoneyFromLevel(int level) {
        if (level == 1) return 20000;
        if (level == 2) return 30000;
        if (level == 3) return 40000;
        if (level == 4) return 50000;
        if (level == 5) return 60000;
        return 0;
    }

    public static double getPriceCrytauxFromLevel(int level) {
        if (level == 1) return 50;
        if (level == 2) return 80;
        if (level == 3) return 120;
        if (level == 4) return 180;
        if (level == 5) return 250;
        return 0;
    }

    public static Integer getMaxLevel() {
        return 5;
    }

    public boolean upOfOneLevel(Player player) {
        if (this.level + 1 > 5) return false;
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
        return true;
    }

    public int getMaxMembers() {
        return getMaxMembers(this.level);
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
