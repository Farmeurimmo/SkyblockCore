package main.java.fr.verymc.spigot.island.bank;

public class IslandBank {

    private double crystaux;
    private double money;
    private int xp;

    public IslandBank(double money, double crystaux, int xp) {
        this.crystaux = crystaux;
        this.money = money;
        this.xp = xp;
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

    public int getXp() {
        return this.xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public void addCrystaux(double crystaux) {
        this.crystaux += crystaux;
    }

    public void addMoney(double money) {
        this.money += money;
    }

    public void addXp(int xp) {
        this.xp += xp;
    }

    public void removeCrystaux(double crystaux) {
        this.crystaux -= crystaux;
    }

    public void removeMoney(double money) {
        this.money -= money;
    }

    public void removeXp(int xp) {
        this.xp -= xp;
    }

}
