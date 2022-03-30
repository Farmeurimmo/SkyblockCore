package main.java.fr.verymc.island.upgrade;

public class IslandUpgrade {

    private int level;
    private int cost;
    private int size;
    private IslandUpgrades upgrade;

    public IslandUpgrade(int level, int cost, IslandUpgrades upgrade) {
        this.level = level;
        this.cost = cost;
        this.upgrade = upgrade;
        this.size = upgrade.getSize();
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(IslandUpgrade upgrade) {
        this.level = upgrade.getLevel();
        this.size = upgrade.getSize();
        this.cost = upgrade.getCost();
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getSize() {
        return size;
    }

    public IslandUpgrades getUpgrade() {
        return upgrade;
    }

    public void addLevel() {
        this.level++;
    }

}
