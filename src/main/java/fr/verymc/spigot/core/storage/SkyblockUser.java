package main.java.fr.verymc.spigot.core.storage;

import main.java.fr.verymc.spigot.island.playerwarps.PlayerWarp;
import org.json.simple.JSONObject;

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

    public static JSONObject skyblockUserToJSON(SkyblockUser skyblockUser) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("uuid", skyblockUser.getUserUUID().toString());
        jsonObject.put("userN", skyblockUser.getUsername());
        jsonObject.put("money", skyblockUser.getMoney());
        if (skyblockUser.getFlyLeft() > 0) {
            jsonObject.put("flyL", skyblockUser.getFlyLeft());
            jsonObject.put("flyAc", skyblockUser.isActive());
        }
        jsonObject.put("hast", skyblockUser.hasHaste());
        jsonObject.put("hastAct", skyblockUser.hasHasteActive());
        jsonObject.put("speed", skyblockUser.hasSpeed());
        jsonObject.put("speedAct", skyblockUser.hasSpeedActive());
        jsonObject.put("jump", skyblockUser.hasJump());
        jsonObject.put("jumpAct", skyblockUser.hasJumpActive());
        jsonObject.put("exp", skyblockUser.getExp());
        jsonObject.put("lvl", skyblockUser.getLevel());
        if (skyblockUser.getPlayerWarp() != null) {
            jsonObject.put("pw", PlayerWarp.playerWarpToString(skyblockUser.getPlayerWarp()));
        }
        return jsonObject;
    }

    public static SkyblockUser skyblockUserFromJSON(JSONObject jsonObject) {
        UUID uuid = UUID.fromString(String.valueOf(jsonObject.get("uuid")));
        String username = String.valueOf(jsonObject.get("userN"));
        double money = Double.parseDouble(String.valueOf(jsonObject.get("money")));
        int flyLeft = -1;
        boolean isActive = false;
        if (jsonObject.get("flyL") != null) {
            flyLeft = Integer.parseInt(String.valueOf(jsonObject.get("flyL")));
            isActive = Boolean.parseBoolean(String.valueOf(jsonObject.get("flyAc")));
        }
        boolean hasHaste = Boolean.parseBoolean(String.valueOf(jsonObject.get("hast")));
        boolean hasHasteActive = Boolean.parseBoolean(String.valueOf(jsonObject.get("hastAct")));
        boolean hasSpeed = Boolean.parseBoolean(String.valueOf(jsonObject.get("speed")));
        boolean hasSpeedActive = Boolean.parseBoolean(String.valueOf(jsonObject.get("speedAct")));
        boolean hasJump = Boolean.parseBoolean(String.valueOf(jsonObject.get("jump")));
        boolean hasJumpActive = Boolean.parseBoolean(String.valueOf(jsonObject.get("jumpAct")));
        double exp = Double.parseDouble(String.valueOf(jsonObject.get("exp")));
        double lvl = Double.parseDouble(String.valueOf(jsonObject.get("lvl")));
        PlayerWarp playerWarp = null;
        if (jsonObject.get("pw") != null) {
            try {
                playerWarp = PlayerWarp.playerWarpFromString(String.valueOf(jsonObject.get("pw")));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new SkyblockUser(username, uuid, money, hasHaste, hasHasteActive, hasSpeed, hasSpeedActive,
                hasJump, hasJumpActive, flyLeft, isActive, false, 0, playerWarp, exp, lvl);
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
