package main.java.fr.verymc.spigot.core.storage;

public enum StoragePriorities {

    INSTANT(0),
    HIGHEST(20 * 3),
    HIGH(20 * 5),
    NORMAL(20 * 10),
    LOW(20 * 20),
    LOWEST(20 * 30);

    private int ticks;

    StoragePriorities(int ticks) {
        this.ticks = ticks;
    }

    public int getTicks() {
        return ticks;
    }
}
