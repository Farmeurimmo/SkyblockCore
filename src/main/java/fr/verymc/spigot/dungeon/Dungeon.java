package main.java.fr.verymc.spigot.dungeon;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Dungeon {

    public String name;
    public DungeonFloors floor;
    public ArrayList<Player> players;
    public Location locDungeon;
    public int size;

    public Dungeon(String name, DungeonFloors floor, ArrayList<Player> players, Location locDungeon) {
        this.name = name;
        this.floor = floor;
        this.players = players;
        this.locDungeon = locDungeon;
        this.size = DungeonFloors.getSizeFromFloor(floor);
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

}
