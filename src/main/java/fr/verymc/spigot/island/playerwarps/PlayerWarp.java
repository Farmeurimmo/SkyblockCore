package main.java.fr.verymc.spigot.island.playerwarps;

import main.java.fr.verymc.spigot.utils.ObjectConverter;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.UUID;

public class PlayerWarp {

    public String name;
    public Location location;
    public double timeLeftPromoted;
    public double vues;
    public double note;
    public ArrayList<UUID> alreadyVoted;

    public PlayerWarp(String name, Location location, double timeLeftPromoted, double vues,
                      double note, ArrayList<UUID> alreadyVoted) {
        this.name = name;
        this.location = location;
        this.timeLeftPromoted = timeLeftPromoted;
        this.vues = vues;
        this.note = note;
        this.alreadyVoted = alreadyVoted;
    }

    public static String playerWarpToString(PlayerWarp playerWarp) {
        return playerWarp.getName() + ObjectConverter.SEPARATOR + ObjectConverter.instance.locationToString(playerWarp.getLocation()) + ObjectConverter.SEPARATOR +
                playerWarp.getVues() + ObjectConverter.SEPARATOR + playerWarp.getNote() + ObjectConverter.SEPARATOR + playerWarp.getTimeLeftPromoted() +
                ObjectConverter.SEPARATOR + playerWarp.getAlreadyVoted().toString();
    }

    public static PlayerWarp playerWarpFromString(String string) {
        if (string == null) return null;
        String[] str = string.split(ObjectConverter.SEPARATOR);
        String name = str[0];
        Location location = ObjectConverter.instance.locationFromString(str[1]);
        double vues = Double.parseDouble(str[2]);
        double note = Double.parseDouble(str[3]);
        double timeLeftPromoted = Double.parseDouble(str[4]);
        ArrayList<UUID> alreadyVoted = new ArrayList<>();
        for (String str1 : ObjectConverter.instance.stringToArrayList(str[5])) {
            if (str1.length() != 36) continue;
            alreadyVoted.add(UUID.fromString(str1));
        }
        return new PlayerWarp(name, location, timeLeftPromoted, vues, note, alreadyVoted);
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

    public double getTimeLeftPromoted() {
        return timeLeftPromoted;
    }

    public void setTimeLeftPromoted(double timeLeftPromoted) {
        this.timeLeftPromoted = timeLeftPromoted;
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
