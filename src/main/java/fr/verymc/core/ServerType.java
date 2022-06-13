package main.java.fr.verymc.core;

public enum ServerType {

    HUB("skyblock_hub"),
    ISLAND("skyblock_îles"),
    DUNGEON("skyblock_dungeon");

    private String displayName;

    private ServerType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }


}
