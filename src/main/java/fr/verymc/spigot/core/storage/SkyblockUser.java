package main.java.fr.verymc.spigot.core.storage;

import main.java.fr.verymc.spigot.island.playerwarps.PlayerWarp;

import java.util.UUID;

public class SkyblockUser {

    private String username;
    private UUID userUUID;
    private double money;

    private boolean hasHaste;
    private boolean hasHasteActive;
    private boolean hasSpeed;
    private boolean hasSpeedActive;
    private boolean hasJump;
    private boolean hasJumpActive;

    private int flyLeft;
    private boolean isActive;

    private boolean isInInvestMode;
    private double timeInvest;
    private PlayerWarp playerWarp;
    private Double exp;
    private Double level;


    public SkyblockUser(String username, UUID userUUID, double money, boolean hasHaste, boolean hasHasteActvie,
                        boolean hasSpeed, boolean hasSpeedActive, boolean hasJump, boolean hasJumpActive, int flyLeft,
                        boolean isActive, boolean isInInvestMode, double timeInvest, PlayerWarp playerWarp,
                        Double exp, Double level) {
        this.username = username;
        this.userUUID = userUUID;
        this.money = money;
        this.hasHaste = hasHaste;
        this.hasHasteActive = hasHasteActvie;
        this.hasSpeed = hasSpeed;
        this.hasSpeedActive = hasSpeedActive;
        this.hasJump = hasJump;
        this.hasJumpActive = hasJumpActive;
        this.flyLeft = flyLeft;
        this.isActive = isActive;
        this.isInInvestMode = isInInvestMode;
        this.timeInvest = timeInvest;
        this.exp = exp;
        this.level = level;
        if (playerWarp != null) {
            this.playerWarp = playerWarp;
        }
    }

    public String getUsername() {
        return username;
    }

    public UUID getUserUUID() {
        return userUUID;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
        StorageManager.instance.startUpdateUser(this, StoragePriorities.NORMAL);
    }

    public boolean hasHaste() {
        return hasHaste;
    }

    public boolean hasHasteActive() {
        return hasHasteActive;
    }

    public boolean hasSpeed() {
        return hasSpeed;
    }

    public boolean hasSpeedActive() {
        return hasSpeedActive;
    }

    public boolean hasJump() {
        return hasJump;
    }

    public boolean hasJumpActive() {
        return hasJumpActive;
    }

    public int getFlyLeft() {
        return flyLeft;
    }

    public void setFlyLeft(int flyLeft) {
        this.flyLeft = flyLeft;
        if (flyLeft == 0) {
            this.isActive = false;
        } else {
            if (this.isActive == false) {
                this.isActive = true;
            }
        }
        StorageManager.instance.startUpdateUser(this, StoragePriorities.NORMAL);
    }

    public void setHaste(boolean hasHaste) {
        this.hasHaste = hasHaste;
        StorageManager.instance.startUpdateUser(this, StoragePriorities.LOWEST);
    }

    public void setHasteActive(boolean hasHasteActvie) {
        this.hasHasteActive = hasHasteActvie;
        StorageManager.instance.startUpdateUser(this, StoragePriorities.LOWEST);
    }

    public void setSpeed(boolean hasSpeed) {
        this.hasSpeed = hasSpeed;
        StorageManager.instance.startUpdateUser(this, StoragePriorities.LOWEST);
    }

    public void setSpeedActive(boolean hasSpeedActive) {
        this.hasSpeedActive = hasSpeedActive;
        StorageManager.instance.startUpdateUser(this, StoragePriorities.LOWEST);
    }

    public void setJump(boolean hasJump) {
        this.hasJump = hasJump;
        StorageManager.instance.startUpdateUser(this, StoragePriorities.LOWEST);
    }

    public void setJumpActive(boolean hasJumpActive) {
        this.hasJumpActive = hasJumpActive;
        StorageManager.instance.startUpdateUser(this, StoragePriorities.LOWEST);
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
        StorageManager.instance.startUpdateUser(this, StoragePriorities.LOWEST);
    }

    public boolean isInInvestMode() {
        return isInInvestMode;
    }

    public void setInInvestMode(boolean isInInvestMode) {
        this.isInInvestMode = isInInvestMode;
    }

    public double getTimeInvest() {
        return timeInvest;
    }

    public void setTimeInvest(double timeInvest) {
        this.timeInvest = timeInvest;
    }

    public PlayerWarp getPlayerWarp() {
        return playerWarp;
    }

    public void setPlayerWarp(PlayerWarp playerWarp) {
        this.playerWarp = playerWarp;
        StorageManager.instance.startUpdateUser(this, StoragePriorities.NORMAL);
    }

    public Double getExp() {
        return exp;
    }

    public void incrementLevel() {
        this.level++;
        StorageManager.instance.startUpdateUser(this, StoragePriorities.NORMAL);
    }

    public Double getLevel() {
        return level;
    }

    public void setLevel(Double level) {
        this.level = level;
        StorageManager.instance.startUpdateUser(this, StoragePriorities.NORMAL);
    }

    public void addExp(Double exp) {
        this.exp += exp;
        StorageManager.instance.startUpdateUser(this, StoragePriorities.LOWEST);
    }

    public void removeExp(Double exp) {
        this.exp -= exp;
        if (this.exp < 0) {
            this.exp = 0.0;
        }
        StorageManager.instance.startUpdateUser(this, StoragePriorities.LOWEST);
    }
}
