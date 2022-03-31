package main.java.fr.verymc.island;

import main.java.fr.verymc.island.bank.IslandBank;
import main.java.fr.verymc.island.perms.IslandPerms;
import main.java.fr.verymc.island.perms.IslandRank;
import main.java.fr.verymc.island.upgrade.IslandUpgradeSize;
import main.java.fr.verymc.utils.WorldBorderUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public class Island {

    private String name;
    private String owner;
    private UUID ownerUUID;
    private Location home;
    private Location center;
    private int id;
    private HashMap<UUID, IslandRank> members = new HashMap<>();
    private HashMap<IslandRank, ArrayList<IslandPerms>> permsPerRanks = new HashMap<>();
    private IslandUpgradeSize sizeUpgrade;
    private WorldBorderUtil.Color borderColor;
    private IslandBank bank;

    public Island(String name, String owner, UUID ownerUUID, Location home, int id, HashMap<UUID, IslandRank> members, boolean defaultPerms,
                  IslandUpgradeSize upgradeSize, WorldBorderUtil.Color borderColor, IslandBank bank) {
        this.name = name;
        this.owner = owner;
        this.ownerUUID = ownerUUID;
        this.home = home.clone().add(0.5, 0.1, 0.5);
        this.center = home;
        this.id = id;
        this.members = members;
        if (defaultPerms) {
            setDefaultPerms();
        }
        this.sizeUpgrade = upgradeSize;
        this.borderColor = borderColor;
        this.bank = bank;
    }

    public void setDefaultPerms() {
        ArrayList<IslandPerms> perms = new ArrayList<>();
        perms.add(IslandPerms.CHANGE_BORDER_COLOR);
        permsPerRanks.put(IslandRank.MEMBRE, perms);
        perms.clear();
        perms.addAll(Arrays.asList(IslandPerms.CHANGE_BORDER_COLOR, IslandPerms.KICK, IslandPerms.PROMOTE, IslandPerms.DEMOTE));
        permsPerRanks.put(IslandRank.MODERATEUR, perms);
        perms.clear();
        perms.addAll(Arrays.asList(IslandPerms.CHANGE_BORDER_COLOR, IslandPerms.KICK, IslandPerms.PROMOTE, IslandPerms.DEMOTE,
                IslandPerms.INVITE, IslandPerms.BAN));
        permsPerRanks.put(IslandRank.COCHEF, perms);
        perms.add(IslandPerms.ALL_PERMS);
        permsPerRanks.put(IslandRank.CHEF, perms);
    }

    public IslandBank getBank() {
        return bank;
    }

    public void setBank(IslandBank bank) {
        this.bank = bank;
    }

    public IslandUpgradeSize getSizeUpgrade() {
        return sizeUpgrade;
    }

    public WorldBorderUtil.Color getBorderColor() {
        return borderColor;
    }

    public Location getCenter() {
        return center;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Location getHome() {
        return home;
    }

    public void setHome(Location home) {
        this.home = home;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean promote(UUID uuid) {
        if (members.containsKey(uuid)) {
            IslandRank currentRank = getIslandRankFromUUID(uuid);
            IslandRank nextRank = IslandRank.getNextRank(currentRank);
            if (currentRank == nextRank) {
                return false;
            }
            members.put(uuid, nextRank);
            return true;
        }
        return false;
    }

    public boolean demote(UUID uuid) {
        if (members.containsKey(uuid)) {
            IslandRank currentRank = getIslandRankFromUUID(uuid);
            IslandRank previousRank = IslandRank.getPreviousRank(currentRank);
            if (currentRank == previousRank) {
                return false;
            }
            members.put(uuid, IslandRank.getPreviousRank(getIslandRankFromUUID(uuid)));
            return true;
        }
        return false;
    }

    public boolean kickFromIsland(UUID uuid) {
        if (uuid.equals(ownerUUID)) {
            return false;
        }
        if (members.containsKey(uuid)) {
            members.remove(uuid);
        }
        Player p;
        if (Bukkit.getPlayer(uuid) != null) {
            p = Bukkit.getPlayer(uuid);
        } else {
            p = (Player) Bukkit.getOfflinePlayer(uuid);
        }
        IslandManager.instance.removePlayerAsAnIsland(p);
        return true;
    }

    public ArrayList<IslandPerms> getPerms(IslandRank rank) {
        return permsPerRanks.get(rank);
    }

    public IslandRank getIslandRankFromUUID(UUID uuid) {
        return members.get(uuid);
    }

    public HashMap<UUID, IslandRank> getMembers() {
        return members;
    }

    public void setMembers(HashMap<UUID, IslandRank> members) {
        this.members = members;
    }

    public void addMembers(UUID member, IslandRank rank) {
        this.members.put(member, rank);
    }

    public UUID getOwnerUUID() {
        return ownerUUID;
    }

    public void setOwnerUUID(UUID ownerUUID) {
        this.ownerUUID = ownerUUID;
    }
}
