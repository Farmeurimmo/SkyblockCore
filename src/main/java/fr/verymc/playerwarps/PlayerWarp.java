package main.java.fr.verymc.playerwarps;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.UUID;

public class PlayerWarp {

    public String name;
    public Location location;
    public boolean isPromoted;
    public double timeLeftPromoted;
    public String owner;
    public UUID ownerUUID;
    public double vues;
    public double note;
    public ArrayList<UUID> alreadyVoted;

    public PlayerWarp(String name, Location location, boolean isPromoted, double timeLeftPromoted, String owner, UUID ownerUUID) {
        this.name = name;
        this.location = location;
        this.isPromoted = isPromoted;
        this.timeLeftPromoted = timeLeftPromoted;
        this.owner = owner;
        this.ownerUUID = ownerUUID;
        this.vues = 0.0;
        this.note = -1.0;
        this.alreadyVoted = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public boolean isPromoted() {
        return isPromoted;
    }

    public void setPromoted(boolean isPromoted) {
        this.isPromoted = isPromoted;
    }

    public double getTimeLeftPromoted() {
        return timeLeftPromoted;
    }

    public void setTimeLeftPromoted(double timeLeftPromoted) {
        this.timeLeftPromoted = timeLeftPromoted;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public UUID getOwnerUUID() {
        return ownerUUID;
    }

    public void setOwnerUUID(UUID ownerUUID) {
        this.ownerUUID = ownerUUID;
    }

    public double getVues() {
        return vues;
    }

    public void setVues(double vues) {
        this.vues = vues;
    }

    public void addVue() {
        this.vues++;
    }

    public double getNote() {
        return note;
    }

    public void setNote(double note) {
        this.note = note;
    }

    public ArrayList<UUID> getAlreadyVoted() {
        return alreadyVoted;
    }

    public void addAlreadyVoted(UUID uuid) {
        this.alreadyVoted.add(uuid);
    }

    public void removeAlreadyVoted(UUID uuid) {
        this.alreadyVoted.remove(uuid);
    }

    public boolean alreadyVoted(UUID uuid) {
        return this.alreadyVoted.contains(uuid);
    }

}
