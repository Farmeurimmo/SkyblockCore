package main.java.fr.verymc.velocity.team;

import com.velocitypowered.api.proxy.Player;
import main.java.fr.verymc.spigot.dungeon.DungeonFloors;
import net.kyori.adventure.text.Component;

import java.util.ArrayList;
import java.util.UUID;

public class DungeonTeam {

    private ArrayList<Player> players;
    private UUID owner;
    private DungeonFloors floor;
    private boolean isOpen;
    private ArrayList<UUID> pendingInvites = new ArrayList<>();
    private ArrayList<UUID> tchatMode = new ArrayList<>();

    public DungeonTeam(ArrayList<Player> players, UUID owner, DungeonFloors floor, boolean isOpen) {
        this.players = players;
        this.owner = owner;
        this.floor = floor;
        this.isOpen = isOpen;
    }

    public boolean isFullForFloor() {
        return players.size() >= DungeonFloors.getPlayersRequiredForFloor(floor);
    }

    public void sendMessageToEveryone(String str) {
        for (Player player : players) {
            player.sendMessage(Component.text(str));
        }
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public UUID getOwner() {
        return owner;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    public DungeonFloors getFloor() {
        return floor;
    }

    public void setFloor(DungeonFloors floor) {
        this.floor = floor;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public boolean isPlayerInTeam(Player player) {
        return players.contains(player);
    }

    public boolean isOwner(Player player) {
        return owner.equals(player.getUniqueId());
    }

    public void setIsOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void addPendingInvite(UUID uuid) {
        pendingInvites.add(uuid);
    }

    public void removePendingInvite(UUID uuid) {
        pendingInvites.remove(uuid);
    }

    public boolean isPendingInvite(UUID uuid) {
        return pendingInvites.contains(uuid);
    }

    public void addTchatMode(UUID uuid) {
        tchatMode.add(uuid);
    }

    public void removeTchatMode(UUID uuid) {
        tchatMode.remove(uuid);
    }

    public boolean isTchatMode(UUID uuid) {
        return tchatMode.contains(uuid);
    }


}
