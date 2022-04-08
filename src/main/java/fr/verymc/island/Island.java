package main.java.fr.verymc.island;

import main.java.fr.verymc.island.bank.IslandBank;
import main.java.fr.verymc.island.perms.IslandPerms;
import main.java.fr.verymc.island.perms.IslandRank;
import main.java.fr.verymc.island.perms.IslandRanks;
import main.java.fr.verymc.island.upgrade.IslandUpgradeMember;
import main.java.fr.verymc.island.upgrade.IslandUpgradeSize;
import main.java.fr.verymc.utils.WorldBorderUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;

public class Island {

    private String name;
    private String owner;
    private UUID ownerUUID;
    private Location home;
    private Location center;
    private int id;
    private HashMap<UUID, IslandRanks> members = new HashMap<>();
    private HashMap<IslandRanks, ArrayList<IslandPerms>> permsPerRanks = new HashMap<>();
    private IslandUpgradeSize sizeUpgrade;
    private IslandUpgradeMember memberUpgrade;
    private WorldBorderUtil.Color borderColor;
    private IslandBank bank;
    private Double value;
    private ArrayList<UUID> coops = new ArrayList<>();
    private ArrayList<UUID> chatToggled = new ArrayList<>();
    private boolean isPublic;

    public Island(String name, String owner, UUID ownerUUID, Location home, int id, HashMap<UUID, IslandRanks> members, boolean defaultPerms,
                  IslandUpgradeSize upgradeSize, IslandUpgradeMember upgradeMember, WorldBorderUtil.Color borderColor,
                  IslandBank bank) {
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
        this.memberUpgrade = upgradeMember;
        this.borderColor = borderColor;
        this.bank = bank;
        this.value = 0.0;
        this.isPublic = true;
    }

    public void setDefaultPerms() {
        ArrayList<IslandPerms> permsVisit = new ArrayList<>();
        permsVisit.add(IslandPerms.INTERACT);
        permsPerRanks.put(IslandRanks.VISITEUR, permsVisit);
        ArrayList<IslandPerms> perms = new ArrayList<>();
        perms.addAll(permsVisit);
        perms.addAll(Arrays.asList(IslandPerms.BUILD, IslandPerms.BREAK));
        permsPerRanks.put(IslandRanks.COOP, perms);
        ArrayList<IslandPerms> permsMembre = new ArrayList<>();
        permsMembre.addAll(perms);
        permsMembre.add(IslandPerms.CHANGE_BORDER_COLOR);
        permsPerRanks.put(IslandRanks.MEMBRE, permsMembre);
        ArrayList<IslandPerms> permsMod = new ArrayList<>();
        permsMod.addAll(permsMembre);
        permsMod.addAll(Arrays.asList(IslandPerms.CHANGE_BORDER_COLOR, IslandPerms.KICK, IslandPerms.PROMOTE, IslandPerms.DEMOTE));
        permsPerRanks.put(IslandRanks.MODERATEUR, permsMod);
        ArrayList<IslandPerms> permsCoChef = new ArrayList<>();
        permsCoChef.addAll(permsMod);
        permsCoChef.addAll(Arrays.asList(IslandPerms.CHANGE_BORDER_COLOR, IslandPerms.KICK, IslandPerms.PROMOTE, IslandPerms.DEMOTE,
                IslandPerms.INVITE, IslandPerms.BAN));
        permsPerRanks.put(IslandRanks.COCHEF, permsCoChef);
        ArrayList<IslandPerms> permsChef = new ArrayList<>();
        permsChef.add(IslandPerms.ALL_PERMS);
        permsPerRanks.put(IslandRanks.CHEF, permsChef);
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

    public WorldBorderUtil.Color setBorderColor(WorldBorderUtil.Color color) {
        return borderColor = color;
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
            IslandRanks currentRank = getIslandRankFromUUID(uuid);
            IslandRanks nextRank = IslandRank.getNextRank(currentRank);
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
            IslandRanks currentRank = getIslandRankFromUUID(uuid);
            IslandRanks previousRank = IslandRank.getPreviousRank(currentRank);
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

    public ArrayList<IslandPerms> getPerms(IslandRanks rank) {
        return permsPerRanks.get(rank);
    }

    public void setPerms(IslandRanks rankTo, ArrayList<IslandPerms> perms) {
        permsPerRanks.put(rankTo, perms);
    }

    public void addPerms(IslandRanks rankTo, IslandPerms perm) {
        permsPerRanks.get(rankTo).add(perm);
    }

    public boolean hasPerms(IslandRanks rank, IslandPerms perm) {
        if (getMapPerms().get(rank) != null) {
            if (getMapPerms().get(rank).contains(IslandPerms.ALL_PERMS)) {
                return true;
            }
            if (getMapPerms().get(rank).contains(perm)) {
                return true;
            }
        }
        return false;
    }

    public HashMap<IslandRanks, ArrayList<IslandPerms>> getMapPerms() {
        return permsPerRanks;
    }

    public ArrayList<IslandRanks> getRanks() {
        ArrayList<IslandRanks> ranks = new ArrayList<>();
        for (Map.Entry<IslandRanks, ArrayList<IslandPerms>> entry : permsPerRanks.entrySet()) {
            ranks.add(entry.getKey());
        }
        return ranks;
    }

    public boolean upPerms(Player player, Island island, IslandPerms islandPerms, IslandRanks islandRank) {
        IslandRanks playerRank = island.getIslandRankFromUUID(player.getUniqueId());
        if (!island.getOwnerUUID().equals(player.getUniqueId())) {
            if (!IslandRank.isUp(playerRank, IslandRank.instance.getNextRankForPerm(islandPerms, island))) {
                return false;
            }
        }
        if (islandRank == IslandRanks.COCHEF) {
            if (addPermsToRank(IslandRanks.COCHEF, islandPerms)) {
                return true;
            }
        } else if (islandRank == IslandRanks.MODERATEUR) {
            if (addPermsToRank(IslandRanks.MODERATEUR, islandPerms)) {
                return true;
            }
        } else if (islandRank == IslandRanks.MEMBRE) {
            if (addPermsToRank(IslandRanks.MEMBRE, islandPerms)) {
                return true;
            }
        } else if (islandRank == IslandRanks.COOP) {
            if (addPermsToRank(IslandRanks.COOP, islandPerms)) {
                return true;
            }
        } else if (islandRank == IslandRanks.VISITEUR) {
            if (addPermsToRank(IslandRanks.VISITEUR, islandPerms)) {
                return true;
            }
        }
        return false;
    }

    public boolean downPerms(Player player, Island island, IslandPerms islandPerms, IslandRanks islandRank) {
        IslandRanks playerRank = island.getIslandRankFromUUID(player.getUniqueId());
        if (!island.getOwnerUUID().equals(player.getUniqueId())) {
            if (!IslandRank.isUp(playerRank, IslandRank.getNextRank(playerRank))) {
                return false;
            }
        }
        if (islandRank == IslandRanks.COCHEF) {
            if (removePermsToRank(IslandRanks.COCHEF, islandPerms)) {
                return true;
            }
        } else if (islandRank == IslandRanks.MODERATEUR) {
            if (removePermsToRank(IslandRanks.MODERATEUR, islandPerms)) {
                return true;
            }
        } else if (islandRank == IslandRanks.MEMBRE) {
            if (removePermsToRank(IslandRanks.MEMBRE, islandPerms)) {
                return true;
            }
        } else if (islandRank == IslandRanks.COOP) {
            if (removePermsToRank(IslandRanks.COOP, islandPerms)) {
                return true;
            }
        } else if (islandRank == IslandRanks.VISITEUR) {
            if (removePermsToRank(IslandRanks.VISITEUR, islandPerms)) {
                return true;
            }
        }
        return false;
    }

    public IslandRanks getIslandRankFromUUID(UUID uuid) {
        if (members.containsKey(uuid)) {
            return members.get(uuid);
        } else {
            if (coops.contains(uuid)) {
                return IslandRanks.COOP;
            } else {
                return IslandRanks.VISITEUR;
            }
        }
    }

    public HashMap<UUID, IslandRanks> getMembers() {
        return members;
    }

    public void setMembers(HashMap<UUID, IslandRanks> members) {
        this.members = members;
    }

    public boolean addPermsToRank(IslandRanks islandRank, IslandPerms islandPerms) {
        if (getPerms(islandRank) == null) {
            return false;
        }
        if (getPerms(islandRank).contains(islandPerms)) {
            return false;
        }
        addPerms(islandRank, islandPerms);
        return true;
    }

    public boolean removePermsToRank(IslandRanks islandRank, IslandPerms islandPerms) {
        if (getPerms(islandRank) == null) {
            return false;
        }
        if (!getPerms(islandRank).contains(islandPerms)) {
            return false;
        }
        getPerms(islandRank).remove(islandPerms);
        return true;
    }

    public void addMembers(UUID member, IslandRanks rank) {
        this.members.put(member, rank);
    }

    public UUID getOwnerUUID() {
        return ownerUUID;
    }

    public void setOwnerUUID(UUID ownerUUID) {
        this.ownerUUID = ownerUUID;
    }

    public Integer getMaxMembers() {
        return memberUpgrade.getMaxMembers();
    }

    public void setMaxMembers(Integer maxMembers) {
        memberUpgrade.setMaxMembers(maxMembers);
    }

    public IslandUpgradeMember getMemberUpgrade() {
        return memberUpgrade;
    }

    public void sendMessageToEveryMember(String message) {
        for (UUID uuid : members.keySet()) {
            Player p = Bukkit.getPlayer(uuid);
            if (p != null) {
                p.sendMessage(message);
            }
        }
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public void addValue(Double value) {
        this.value += value;
    }

    public void removeValue(Double value) {
        this.value -= value;
    }

    public boolean isCoop(UUID uuid) {
        return coops.contains(uuid);
    }

    public boolean addCoop(UUID uuid) {
        if (!coops.contains(uuid)) {
            coops.add(uuid);
            return true;
        }
        return false;
    }

    public boolean removeCoop(UUID uuid) {
        if (coops.contains(uuid)) {
            coops.remove(uuid);
            return true;
        }
        return false;
    }

    public ArrayList<UUID> getCoops() {
        return coops;
    }

    public void setCoops(ArrayList<UUID> coops) {
        this.coops = coops;
    }

    public void clearCoops() {
        this.coops.clear();
    }

    public void addIslandChatToggled(UUID uuid) {
        this.chatToggled.add(uuid);
    }

    public void removeIslandChatToggled(UUID uuid) {
        this.chatToggled.remove(uuid);
    }

    public boolean isIslandChatToggled(UUID uuid) {
        return chatToggled.contains(uuid);
    }

    public boolean isInIsland(UUID uuid) {
        return members.containsKey(uuid);
    }

    public boolean isPublic() {
        return this.isPublic;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

}
