package main.java.fr.verymc.island;

import main.java.fr.verymc.Main;
import main.java.fr.verymc.island.bank.IslandBank;
import main.java.fr.verymc.island.blocks.Chest;
import main.java.fr.verymc.island.challenges.IslandChallenge;
import main.java.fr.verymc.island.minions.Minion;
import main.java.fr.verymc.island.perms.IslandPerms;
import main.java.fr.verymc.island.perms.IslandRank;
import main.java.fr.verymc.island.perms.IslandRanks;
import main.java.fr.verymc.island.protections.IslandSettings;
import main.java.fr.verymc.island.upgrade.IslandUpgradeGenerator;
import main.java.fr.verymc.island.upgrade.IslandUpgradeMember;
import main.java.fr.verymc.island.upgrade.IslandUpgradeSize;
import main.java.fr.verymc.utils.ObjectConverter;
import main.java.fr.verymc.utils.WorldBorderUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WeatherType;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.*;

public class Island {

    //A STOCKER
    private String name;
    private Location home;
    private Location center;
    private int id;
    private HashMap<UUID, IslandRanks> members;
    private HashMap<IslandRanks, ArrayList<IslandPerms>> permsPerRanks = new HashMap<>();
    private IslandUpgradeSize sizeUpgrade;
    private IslandUpgradeMember memberUpgrade;
    private IslandUpgradeGenerator generatorUpgrade;
    private WorldBorderUtil.Color borderColor;
    private IslandBank bank;
    private ArrayList<UUID> banneds;
    private boolean isPublic;
    private ArrayList<IslandChallenge> challenges;
    private ArrayList<IslandSettings> activatedSettings;
    private ArrayList<main.java.fr.verymc.island.blocks.Chest> chests;
    private ArrayList<Minion> minions;


    //NE PAS STOCKER
    private boolean loadedHere;
    private Double value;
    private ArrayList<UUID> coops = new ArrayList<>();
    private ArrayList<UUID> chatToggled = new ArrayList<>();

    public Island(String name, Location home, Location center, int id, HashMap<UUID, IslandRanks> members,
                  IslandUpgradeSize upgradeSize, IslandUpgradeMember upgradeMember, WorldBorderUtil.Color borderColor,
                  IslandBank bank, IslandUpgradeGenerator generatorUpgrade, ArrayList<UUID> banneds, ArrayList<IslandChallenge> challenges,
                  boolean isDefaultChallenges, HashMap<IslandRanks, ArrayList<IslandPerms>> permsPerRanks,
                  boolean isPublic, double value, ArrayList<IslandSettings> activatedSettings, ArrayList<Chest> chests, ArrayList<Minion> minions,
                  boolean loadedHere) {
        this.name = name;
        this.home = home;
        this.center = center;
        this.id = id;
        this.members = members;
        this.sizeUpgrade = upgradeSize;
        this.memberUpgrade = upgradeMember;
        this.borderColor = borderColor;
        this.bank = bank;
        this.isPublic = isPublic;
        this.value = value;
        this.generatorUpgrade = generatorUpgrade;
        this.banneds = banneds;
        this.challenges = challenges;
        if (isDefaultChallenges) {
            addDefaultChallenges();
        }
        if (permsPerRanks == null) {
            setDefaultPerms();
        } else {
            this.permsPerRanks = permsPerRanks;
        }
        IslandManager.instance.setWorldBorderForAllPlayerOnIsland(this);
        if (activatedSettings == null) {
            this.activatedSettings = new ArrayList<>(Arrays.asList(IslandSettings.TIME_DEFAULT, IslandSettings.WEATHER_DEFAULT));
        } else {
            this.activatedSettings = activatedSettings;
        }
        if (chests == null) {
            this.chests = new ArrayList<>();
        } else {
            this.chests = chests;
        }
        if (minions == null) {
            this.minions = new ArrayList<>();
        } else {
            this.minions = minions;
        }
        this.loadedHere = loadedHere;
        if (loadedHere) loadIsland();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.instance, new Runnable() {
            @Override
            public void run() {
                IslandManager.instance.setWorldBorderForAllPlayerOnIsland(Island.this);
                toggleTimeAndWeather();
            }
        }, 0, 100L);
    }

    public static JSONObject islandToJSON(Island i) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", i.getName());
        jsonObject.put("home", ObjectConverter.instance.locationToString(i.getHome()));
        jsonObject.put("center", ObjectConverter.instance.locationToString(i.getCenter()));
        jsonObject.put("id", i.getId());
        jsonObject.put("members", new JSONObject(i.getMembers()).toJSONString());
        jsonObject.put("rankPerms", new JSONObject(Island.getReducedMapPerms(i)).toJSONString());
        jsonObject.put("siUp", i.getSizeUpgrade().getLevel());
        jsonObject.put("mbUp", i.getMemberUpgrade().getLevel());
        jsonObject.put("genUp", i.getGeneratorUpgrade().getLevel());
        jsonObject.put("border", WorldBorderUtil.instanceClass.borderToString(i.getBorderColor()));
        jsonObject.put("bank", i.getBank().getMoney() + ObjectConverter.SEPARATOR + i.getBank().getCrystaux() + ObjectConverter.SEPARATOR + i.getBank().getXp());
        jsonObject.put("bans", i.getBanneds().toString());
        jsonObject.put("public", i.isPublic);
        String challenges = "";
        if (i.getChallenges() != null) {
            for (IslandChallenge islandChallenge : i.getChallenges()) {
                challenges += IslandChallenge.toString(islandChallenge) + ObjectConverter.SEPARATOR_ELEMENT;
            }
        }
        jsonObject.put("cha", challenges);
        jsonObject.put("aS", i.getActivatedSettings().toString());
        String chestsString = "";
        for (Chest chest : i.getChests()) {
            chestsString += Chest.toString(chest) + ObjectConverter.SEPARATOR_ELEMENT;
        }
        jsonObject.put("chests", chestsString);
        String minionsString = "";
        for (Minion minion : i.getMinions()) {
            minionsString += Minion.toString(minion) + ObjectConverter.SEPARATOR_ELEMENT;
        }
        jsonObject.put("minions", minionsString);
        return jsonObject;
    }

    public static Island readFromJSON(String strJSON) {
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) new JSONParser().parse(strJSON);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String name = (String) jsonObject.get("name");
        Location home = ObjectConverter.instance.locationFromString((String) jsonObject.get("home"));
        Location center = ObjectConverter.instance.locationFromString((String) jsonObject.get("center"));
        int id = Integer.parseInt(String.valueOf(jsonObject.get("id")));
        HashMap<UUID, IslandRanks> members = new HashMap<>();
        JSONObject jsonObjectMembers = new JSONObject(ObjectConverter.instance.stringToHashMap((String) jsonObject.get("members")));
        for (Object o : jsonObjectMembers.keySet()) {
            String key = (String) o;
            String value = (String) jsonObjectMembers.get(key);
            members.put(UUID.fromString(key), IslandRanks.match(value));
        }
        HashMap<IslandRanks, ArrayList<IslandPerms>> permsPerRanks = new HashMap<>();
        JSONObject jsonObjectPerms = new JSONObject(ObjectConverter.instance.stringToHashMap((String) jsonObject.get("rankPerms")));
        for (Object o : jsonObjectPerms.keySet()) {
            String key = (String) o;
            String value = (String) jsonObjectPerms.get(key);
            ArrayList<IslandPerms> perms = new ArrayList<>();
            for (String s : ObjectConverter.instance.stringToArrayList(value)) {
                perms.add(IslandPerms.match(s));
            }
            permsPerRanks.put(IslandRanks.valueOf(key), perms);
        }
        int current = permsPerRanks.size() - 1;
        ArrayList<IslandPerms> toHerit = new ArrayList<>();
        while (current > 0) { //POUR LE CHEF PAS BESOIN DE LUI FAIRE HÉRITER LES PERMS VU QU'IL LES A TOUTES
            for (Map.Entry<IslandRanks, ArrayList<IslandPerms>> e : permsPerRanks.entrySet()) {
                for (Map.Entry<IslandRanks, Integer> entry : IslandRank.getIslandRankPos().entrySet()) {
                    if (current == entry.getValue()) {
                        for (IslandPerms islandPerms : e.getValue()) {
                            if (!toHerit.contains(islandPerms)) toHerit.add(islandPerms);
                        }
                        permsPerRanks.put(entry.getKey(), toHerit);
                        current--;
                    }
                }
            }
        }
        IslandUpgradeSize sizeUpgrade = new IslandUpgradeSize(Integer.parseInt(String.valueOf(jsonObject.get("siUp"))));
        IslandUpgradeMember memberUpgrade = new IslandUpgradeMember(Integer.parseInt(String.valueOf(jsonObject.get("mbUp"))));
        IslandUpgradeGenerator generatorUpgrade = new IslandUpgradeGenerator(Integer.parseInt(String.valueOf(jsonObject.get("genUp"))));
        WorldBorderUtil.Color borderColor = WorldBorderUtil.instanceClass.borderFromString((String) jsonObject.get("border"));
        String bank = (String) jsonObject.get("bank");
        String[] bankSplit = bank.split(ObjectConverter.SEPARATOR);
        double money = Double.parseDouble(bankSplit[0]);
        double crystaux = Double.parseDouble(bankSplit[1]);
        int xp = Integer.parseInt(bankSplit[2]);
        IslandBank bank1 = new IslandBank(money, crystaux, xp);
        ArrayList<UUID> banneds = new ArrayList<>();
        for (String str : ObjectConverter.instance.stringToArrayList((String) jsonObject.get("bans"))) {
            if (str.length() == 36) {
                banneds.add(UUID.fromString(str));
            }
        }
        boolean isPublic = Boolean.valueOf(String.valueOf(jsonObject.get("public")));
        ArrayList<IslandChallenge> islandChallenges = new ArrayList<>();
        String strCh = (String) jsonObject.get("cha");
        String[] challenges = strCh.split(ObjectConverter.SEPARATOR_ELEMENT);
        for (String str : challenges) {
            if (str.length() > 1) {
                islandChallenges.add(IslandChallenge.fromString(str));
            }
        }
        ArrayList<IslandSettings> activatedSettings = new ArrayList<>();
        for (String str : ObjectConverter.instance.stringToArrayList((String) jsonObject.get("aS"))) {
            IslandSettings islandSettings = IslandSettings.matchSettings(str);
            if (islandSettings != null) {
                activatedSettings.add(islandSettings);
            }
        }
        ArrayList<Chest> chests = new ArrayList<>();
        String strChest = (String) jsonObject.get("chests");
        String[] chestsSplit = strChest.split(ObjectConverter.SEPARATOR_ELEMENT);
        for (String str : chestsSplit) {
            if (str.length() > 1) {
                chests.add(Chest.fromString(str));
            }
        }
        ArrayList<Minion> minions = new ArrayList<>();
        String strMinion = (String) jsonObject.get("minions");
        String[] minionsSplit = strMinion.split(ObjectConverter.SEPARATOR_ELEMENT);
        for (String str : minionsSplit) {
            if (str.length() > 1) {
                minions.add(Minion.fromString(str));
            }
        }
        return new Island(name, home, center, id, members, sizeUpgrade, memberUpgrade, borderColor,
                bank1, generatorUpgrade, banneds, islandChallenges, false, permsPerRanks, isPublic, 0.0, activatedSettings, chests, minions, false);
    }

    public static HashMap<IslandRanks, ArrayList<IslandPerms>> getReducedMapPerms(Island island) {
        HashMap<IslandRanks, ArrayList<IslandPerms>> map = new HashMap<>();
        ArrayList<IslandPerms> toRemove = new ArrayList<>();
        HashMap<IslandRanks, Integer> islandRanksIntegerHashMap = IslandRank.getIslandRankPos();
        int current = islandRanksIntegerHashMap.size() - 1;
        ArrayList<IslandRanks> done = new ArrayList<>();
        while (done.size() != islandRanksIntegerHashMap.size()) {
            for (Map.Entry<IslandRanks, Integer> entry : islandRanksIntegerHashMap.entrySet()) {
                if (entry.getValue() == current) {
                    ArrayList<IslandPerms> toAdd = new ArrayList<>();
                    for (IslandPerms islandPerms : island.getPerms(entry.getKey())) {
                        if (toRemove.contains(islandPerms)) continue;
                        toAdd.add(islandPerms);
                        toRemove.add(islandPerms);
                    }
                    map.put(entry.getKey(), toAdd);
                    done.add(entry.getKey());
                    current--;
                }
            }
        }
        return map;
    }

    public void loadIsland() {
        Island island = this;
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject = Island.islandToJSON(island);
                System.out.println(jsonObject);
            }
        }, 20);
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
        permsMembre.addAll(Arrays.asList(IslandPerms.CHANGE_BORDER_COLOR, IslandPerms.MINIONS_ADD, IslandPerms.MINIONS_INTERACT));
        permsPerRanks.put(IslandRanks.MEMBRE, permsMembre);

        ArrayList<IslandPerms> permsMod = new ArrayList<>();
        permsMod.addAll(permsMembre);
        permsMod.addAll(Arrays.asList(IslandPerms.CHANGE_BORDER_COLOR, IslandPerms.KICK, IslandPerms.PROMOTE, IslandPerms.DEMOTE, IslandPerms.MINIONS_REMOVE));
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

    public void addDefaultChallenges() {
        this.challenges = IslandManager.instance.getAvailableChallenges();
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
        return name.replace("&", "§");
    }

    public void setName(String name) {
        this.name = name;
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
        if (getMembers().get(uuid) != null) {
            if (getMembers().get(uuid) == IslandRanks.CHEF) {
                return false;
            }
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

    public boolean hasPerms(IslandRanks rank, IslandPerms perm, Player p) {
        if (rank == IslandRanks.CHEF) {
            return true;
        }
        if (getMapPerms().get(rank) != null) {
            if (p != null) {
                if (IslandManager.instance.isBypassing(p.getUniqueId())) {
                    return true;
                }
            }
            if (getMapPerms().get(rank).contains(IslandPerms.ALL_PERMS)) {
                return true;
            }
            if (perm == null) {
                if (p != null)
                    p.sendMessage("§6§lIles §8» §fErreur interne lors de la vérification des permissions (erreur perm inexistante).");
                return false;
            }
            if (getMapPerms().get(rank).contains(perm)) {
                return true;
            }
        }
        if (p != null) p.sendMessage("§6§lIles §8» §fVous n'avez pas la permission de faire cela.");
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
        if (playerRank == islandRank) {
            if (!island.hasPerms(islandRank, null, player)) {
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
            if (!IslandRank.isUp(playerRank, IslandRank.instance.getNextRankForPerm(islandPerms, island))) {
                return false;
            }
        }
        if (playerRank == islandRank) {
            if (!island.hasPerms(islandRank, null, player)) {
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
        for (Map.Entry<UUID, IslandRanks> entry : members.entrySet()) {
            if (entry.getValue() == IslandRanks.CHEF) {
                return entry.getKey();
            }
        }
        return null;
    }

    public Integer getMaxMembers() {
        return memberUpgrade.getMaxMembers();
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
        if (this.value - value >= 0) {
            this.value -= value;
        }
    }

    public boolean isCoop(UUID uuid) {
        return coops.contains(uuid);
    }

    public boolean addCoop(UUID uuid) {
        if (coops.size() > 14) {
            return false;
        }
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

    public IslandUpgradeGenerator getGeneratorUpgrade() {
        return generatorUpgrade;
    }

    public void setGeneratorUpgrade(IslandUpgradeGenerator generatorUpgrade) {
        this.generatorUpgrade = generatorUpgrade;
    }

    public boolean isBanned(UUID uuid) {
        return banneds.contains(uuid);
    }

    public boolean addBanned(UUID uuid) {
        if (banneds.contains(uuid)) {
            return false;
        }
        banneds.add(uuid);
        return true;
    }

    public boolean removeBanned(UUID uuid) {
        if (!banneds.contains(uuid)) {
            return false;
        }
        banneds.remove(uuid);
        return true;
    }

    public ArrayList<UUID> getBanneds() {
        return banneds;
    }

    public ArrayList<IslandChallenge> getChallenges() {
        return challenges;
    }

    public void addChallenge(IslandChallenge challenge) {
        challenges.add(challenge);
    }

    public void removeChallenge(IslandChallenge challenge) {
        challenges.remove(challenge);
    }

    public void addSettingActivated(IslandSettings setting) {
        if (!activatedSettings.contains(setting)) {
            activatedSettings.add(setting);
        }
    }

    public void removeSettingActived(IslandSettings setting) {
        if (activatedSettings.contains(setting)) {
            activatedSettings.remove(setting);
        }
    }

    public boolean hasSettingActivated(IslandSettings islandSetting) {
        return activatedSettings.contains(islandSetting);
    }

    public void applyTimeForMembers(Set<UUID> members, long time, boolean defaultTime) {
        for (UUID uuid : members) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                if (defaultTime) {
                    player.resetPlayerTime();
                    continue;
                }
                player.setPlayerTime(time, false);
            }
        }
    }

    public void applyWeatherForMembers(Set<UUID> members, WeatherType weather, boolean defaultWeather) {
        for (UUID uuid : members) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                if (defaultWeather) {
                    player.resetPlayerWeather();
                    continue;
                }
                player.setPlayerWeather(weather);
            }
        }
    }

    public void toggleTimeAndWeather() {
        IslandSettings islandSettings = IslandSettings.getTimeSetting(activatedSettings);
        switch (islandSettings) {
            case TIME_DEFAULT:
                applyTimeForMembers(members.keySet(), 0, true);
                break;
            case TIME_DAY:
                applyTimeForMembers(members.keySet(), 6000, false);
                break;
            case TIME_CREPUSCULE:
                applyTimeForMembers(members.keySet(), 13000, false);
                break;
            case TIME_NIGHT:
                applyTimeForMembers(members.keySet(), 18000, false);
                break;
        }
        IslandSettings islandSettings1 = IslandSettings.getWeatherSetting(activatedSettings);
        switch (islandSettings1) {
            case WEATHER_DEFAULT:
                applyWeatherForMembers(members.keySet(), WeatherType.CLEAR, true);
                break;
            case WEATHER_RAIN:
                applyWeatherForMembers(members.keySet(), WeatherType.DOWNFALL, false);
                break;
            case WEATHER_CLEAR:
                applyWeatherForMembers(members.keySet(), WeatherType.CLEAR, false);
                break;
        }
    }

    public ArrayList<IslandSettings> getActivatedSettings() {
        return activatedSettings;
    }

    public ArrayList<Chest> getChests() {
        return chests;
    }

    public void addChest(Chest chest) {
        chests.add(chest);
    }

    public void removeChest(Chest chest) {
        chests.remove(chest);
    }

    public void addMinion(Minion minion) {
        minions.add(minion);
    }

    public void removeMinion(Minion minion) {
        minions.remove(minion);
    }

    public ArrayList<Minion> getMinions() {
        return minions;
    }

    public boolean isLoadedHere() {
        return loadedHere;
    }

    public void setLoadedHere(boolean loadedHere) {
        this.loadedHere = loadedHere;
    }

}
