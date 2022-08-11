package main.java.fr.verymc.spigot.dungeon;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Dungeon {

    private String name;
    private DungeonFloors floor;
    private ArrayList<Player> players;
    private ArrayList<Player> deadPlayers;
    private Location locDungeon;
    private int size;
    private long time_of_start;
    private int duration_in_minutes;

    public Dungeon(String name, DungeonFloors floor, ArrayList<Player> players, Location locDungeon, int duration_in_minutes) {
        this.name = name;
        this.floor = floor;
        this.players = players;
        this.locDungeon = locDungeon;
        this.size = DungeonFloors.getSizeFromFloor(floor);
        this.time_of_start = System.currentTimeMillis();
        this.deadPlayers = new ArrayList<>();
        this.duration_in_minutes = duration_in_minutes;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public boolean isPlayerInDungeon(Player player) {
        return players.contains(player);
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DungeonFloors getFloor() {
        return floor;
    }

    public Location getLocDungeon() {
        return locDungeon;
    }

    public void setLocDungeon(Location locDungeon) {
        this.locDungeon = locDungeon;
    }

    public long getTime_of_start() {
        return time_of_start;
    }

    public void setTime_of_start(long time_of_start) {
        this.time_of_start = time_of_start;
    }

    public ArrayList<Player> getDeadPlayers() {
        return deadPlayers;
    }

    public void addDeadPlayer(Player player) {
        deadPlayers.add(player);
        DungeonManager.instance.checkIfAllPlayersAreDead(this);
    }

    public int getDuration_in_minutes() {
        return duration_in_minutes;
    }

    public void setDuration_in_minutes(int duration_in_minutes) {
        this.duration_in_minutes = duration_in_minutes;
    }

}
