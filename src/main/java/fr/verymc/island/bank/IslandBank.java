package main.java.fr.verymc.island.bank;

public class IslandBank {

    private double crystaux;
    private double money;
    private double xp;
    private double level;

    public IslandBank(double crystaux, double money, double xp, double level) {
        this.crystaux = crystaux;
        this.money = money;
        this.xp = xp;
        this.level = level;
    }

    public double getCrystaux() {
        return this.crystaux;
    }

    public void setCrystaux(double crystaux) {
        this.crystaux = crystaux;
    }

    public double getMoney() {
        return this.money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getXp() {
        return this.xp;
    }

    public void setXp(double xp) {
        this.xp = xp;
    }

    public double getLevel() {
        return this.level;
    }

    public void setLevel(double level) {
        this.level = level;
    }

    public void addCrystaux(double crystaux) {
        this.crystaux += crystaux;
    }

    public void addMoney(double money) {
        this.money += money;
    }

    public void addXp(double xp) {
        this.xp += xp;
    }

    public void addLevel(double level) {
        this.level += level;
    }

    public void removeCrystaux(double crystaux) {
        this.crystaux -= crystaux;
    }

    public void removeMoney(double money) {
        this.money -= money;
    }

    public void removeXp(double xp) {
        this.xp -= xp;
    }

    public void removeLevel(double level) {
        this.level -= level;
    }

    public void removeAll() {
        this.crystaux = 0;
        this.money = 0;
        this.xp = 0;
        this.level = 0;
    }

}
