package main.java.fr.verymc.storage;

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


    public SkyblockUser(String username, UUID userUUID, double money, boolean hasHaste, boolean hasHasteActvie,
                        boolean hasSpeed, boolean hasSpeedActive, boolean hasJump, boolean hasJumpActive, int flyLeft,
                        boolean isActive) {
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
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UUID getUserUUID() {
        return userUUID;
    }

    public void setUserUUID(UUID userUUID) {
        this.userUUID = userUUID;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
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
    }

    public void setHaste(boolean hasHaste) {
        this.hasHaste = hasHaste;
    }

    public void setHasteActive(boolean hasHasteActvie) {
        this.hasHasteActive = hasHasteActvie;
    }

    public void setSpeed(boolean hasSpeed) {
        this.hasSpeed = hasSpeed;
    }

    public void setSpeedActive(boolean hasSpeedActive) {
        this.hasSpeedActive = hasSpeedActive;
    }

    public void setJump(boolean hasJump) {
        this.hasJump = hasJump;
    }

    public void setJumpActive(boolean hasJumpActive) {
        this.hasJumpActive = hasJumpActive;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

}
