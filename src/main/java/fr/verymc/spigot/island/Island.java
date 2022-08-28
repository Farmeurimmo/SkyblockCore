package main.java.fr.verymc.spigot.island;

import main.java.fr.verymc.spigot.Main;
import main.java.fr.verymc.spigot.core.PluginMessageManager;
import main.java.fr.verymc.spigot.core.spawners.Spawner;
import main.java.fr.verymc.spigot.core.storage.StorageManager;
import main.java.fr.verymc.spigot.core.storage.StoragePriorities;
import main.java.fr.verymc.spigot.island.bank.IslandBank;
import main.java.fr.verymc.spigot.island.blocks.Chest;
import main.java.fr.verymc.spigot.island.challenges.IslandChallenge;
import main.java.fr.verymc.spigot.island.minions.Minion;
import main.java.fr.verymc.spigot.island.perms.IslandPerms;
import main.java.fr.verymc.spigot.island.perms.IslandRank;
import main.java.fr.verymc.spigot.island.perms.IslandRanks;
import main.java.fr.verymc.spigot.island.protections.IslandSettings;
import main.java.fr.verymc.spigot.island.upgrade.IslandUpgradeGenerator;
import main.java.fr.verymc.spigot.island.upgrade.IslandUpgradeMember;
import main.java.fr.verymc.spigot.island.upgrade.IslandUpgradeSize;
import main.java.fr.verymc.spigot.utils.ObjectConverter;
import main.java.fr.verymc.spigot.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
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
    private UUID uuid;
    private HashMap<UUID, IslandRanks> members;
    private HashMap<IslandRanks, ArrayList<IslandPerms>> permsPerRanks = new HashMap<>();
    private IslandUpgradeSize sizeUpgrade;
    private IslandUpgradeMember memberUpgrade;
    private IslandUpgradeGenerator generatorUpgrade;
    private IslandBank bank;
    private ArrayList<UUID> banneds;
    private boolean isPublic;
    private ArrayList<IslandChallenge> challenges;
    private ArrayList<IslandSettings> activatedSettings;
    private ArrayList<Chest> chests;
    private ArrayList<Minion> minions;
    private HashMap<Material, Double> valuesBlocs;
    private ArrayList<Spawner> spawners;


    //NE PAS STOCKER
    private boolean loadedHere;
    private Double value;
    private ArrayList<UUID> coops = new ArrayList<>();
    private ArrayList<UUID> chatToggled = new ArrayList<>();

    public Island(String name, Location home, Location center, UUID uuid, HashMap<UUID, IslandRanks> members, IslandUpgradeSize upgradeSize,
                  IslandUpgradeMember upgradeMember, IslandBank bank, IslandUpgradeGenerator generatorUpgrade, ArrayList<UUID> banneds,
                  ArrayList<IslandChallenge> challenges, boolean isDefaultChallenges, HashMap<IslandRanks, ArrayList<IslandPerms>> permsPerRanks,
                  boolean isPublic, double value, ArrayList<IslandSettings> activatedSettings, ArrayList<Chest> chests, ArrayList<Minion> minions,
                  HashMap<Material, Double> stacked, boolean loadedHere, ArrayList<Spawner> spawners) {
        this.name = name;
        this.home = home;
        this.center = center;
        this.uuid = uuid;
        this.members = members;
        this.sizeUpgrade = upgradeSize;
        this.memberUpgrade = upgradeMember;
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
        if (stacked == null) {
            this.valuesBlocs = new HashMap<>();
        } else {
            this.valuesBlocs = stacked;
        }
        this.loadedHere = loadedHere;
        this.spawners = spawners;
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
        jsonObject.put("home", ObjectConverter.instance.locationToString(PlayerUtils.instance.addCenterTo(i.getCenter(), i.getHome())));
        jsonObject.put("id", i.getUUID().toString());
        jsonObject.put("members", new JSONObject(i.getMembers()).toString());
        jsonObject.put("rankPerms", new JSONObject(getReducedMapPerms(i)).toString());
        jsonObject.put("siUp", i.getSizeUpgrade().getLevel());
        jsonObject.put("mbUp", i.getMemberUpgrade().getLevel());
        jsonObject.put("genUp", i.getGeneratorUpgrade().getLevel());
        jsonObject.put("bank", i.getBank().getMoney() + ObjectConverter.SEPARATOR + i.getBank().getCrystaux() + ObjectConverter.SEPARATOR + i.getBank().getXp());
        jsonObject.put("bans", i.getBanneds().toString());
        jsonObject.put("public", i.isPublic());
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
            if (chest.getBlock() != null)
                chest.setBlock(PlayerUtils.instance.addCenterTo(i.getCenter(), chest.getBlock()));
            chestsString += Chest.toString(chest) + ObjectConverter.SEPARATOR_ELEMENT;
        }
        jsonObject.put("chests", chestsString);
        String minionsString = "";
        for (Minion minion : i.getMinions()) {
            if (minion.getChestBloc() != null)
                minion.setChestBloc(PlayerUtils.instance.addCenterTo(i.getCenter(), minion.getChestBloc().getLocation()).getBlock());
            minion.setBlocLocation(PlayerUtils.instance.addCenterTo(i.getCenter(), minion.getBlocLocation()));
            minionsString += Minion.toString(minion) + ObjectConverter.SEPARATOR_ELEMENT;
        }
        jsonObject.put("minions", minionsString);
        jsonObject.put("stacked", new JSONObject(i.getStackedBlocs()).toString());
        String spawners = "";
        for (Spawner spawner : i.getSpawners()) {
            spawner.setLoc(PlayerUtils.instance.addCenterTo(i.getCenter(), spawner.getLoc()));
            spawners += Spawner.spawnerToString(spawner) + ObjectConverter.SEPARATOR_ELEMENT;
        }
        jsonObject.put("spawners", spawners);
        System.out.println(jsonObject);
        return jsonObject;
    }

    public static Island islandFromJSON(String strJSON) {
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) new JSONParser().parse(strJSON);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String name = (String) jsonObject.get("name");
        Location home = ObjectConverter.instance.locationFromString((String) jsonObject.get("home"));
        UUID id = UUID.fromString(String.valueOf(jsonObject.get("id")));
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
                if (s.length() < 3) continue;
                perms.add(IslandPerms.match(s));
            }
            permsPerRanks.put(IslandRanks.valueOf(key), perms);
        }
        int current = permsPerRanks.size() - 1;
        int run = 0;
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
            if (run > 12) {
                break;
            }
            run++;
        }
        IslandUpgradeSize sizeUpgrade = new IslandUpgradeSize(Integer.parseInt(String.valueOf(jsonObject.get("siUp"))));
        IslandUpgradeMember memberUpgrade = new IslandUpgradeMember(Integer.parseInt(String.valueOf(jsonObject.get("mbUp"))));
        IslandUpgradeGenerator generatorUpgrade = new IslandUpgradeGenerator(Integer.parseInt(String.valueOf(jsonObject.get("genUp"))));
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
        System.out.println("§4" + strChest);
        for (int i = 0; i < chestsSplit.length; i++) {
            System.out.println("§2" + chestsSplit[i]);
            if (chestsSplit[i].length() > 1)
                chests.add(Chest.fromString(chestsSplit[i]));
        }
        ArrayList<Minion> minions = new ArrayList<>();
        String strMinion = (String) jsonObject.get("minions");
        String[] minionsSplit = strMinion.split(ObjectConverter.SEPARATOR_ELEMENT);
        for (String str : minionsSplit) {
            if (str.length() > 1) {
                minions.add(Minion.fromString(str));
            }
        }
        HashMap<Material, Double> stacked = new HashMap<>();
        JSONObject jsonObject1 = new JSONObject(ObjectConverter.instance.stringToHashMap((String) jsonObject.get("stacked")));
        for (Object o : jsonObject1.keySet()) {
            String key = (String) o;
            Double value = Double.parseDouble(String.valueOf(jsonObject1.get(key)));
            stacked.put(Material.matchMaterial(key), value);
        }
        ArrayList<Spawner> spawners = new ArrayList<>();
        String strSpawners = (String) jsonObject.get("spawners");
        String[] spawnersSplit = strSpawners.split(ObjectConverter.SEPARATOR_ELEMENT);
        for (String str : spawnersSplit) {
            if (str.length() > 1) {
                spawners.add(Spawner.stringToSpawner(str));
            }
        }
        return new Island(name, home, null, id, members, sizeUpgrade, memberUpgrade, bank1, generatorUpgrade,
                banneds, islandChallenges, false, permsPerRanks, isPublic, 0.0, activatedSettings, chests,
                minions, stacked, false, spawners);
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
        permsMembre.addAll(Arrays.asList(IslandPerms.MINIONS_ADD, IslandPerms.MINIONS_INTERACT));
        permsPerRanks.put(IslandRanks.MEMBRE, permsMembre);

        ArrayList<IslandPerms> permsMod = new ArrayList<>();
        permsMod.addAll(permsMembre);
        permsMod.addAll(Arrays.asList(IslandPerms.KICK, IslandPerms.PROMOTE, IslandPerms.DEMOTE, IslandPerms.MINIONS_REMOVE));
        permsPerRanks.put(IslandRanks.MODERATEUR, permsMod);

        ArrayList<IslandPerms> permsCoChef = new ArrayList<>();
        permsCoChef.addAll(permsMod);
        permsCoChef.addAll(Arrays.asList(IslandPerms.KICK, IslandPerms.PROMOTE, IslandPerms.DEMOTE,
                IslandPerms.INVITE, IslandPerms.BAN));
        permsPerRanks.put(IslandRanks.COCHEF, permsCoChef);

        ArrayList<IslandPerms> permsChef = new ArrayList<>();
        permsChef.add(IslandPerms.ALL_PERMS);
        permsPerRanks.put(IslandRanks.CHEF, permsChef);

        StorageManager.instance.startUpdateIsland(this, StoragePriorities.LOWEST);
    }

    public HashMap<Material, Double> getStackedBlocs() {
        return valuesBlocs;
    }

    public void addDefaultChallenges() {
        this.challenges = IslandManager.instance.getAvailableChallenges();
    }

    public IslandBank getBank() {
        return bank;
    }

    public IslandUpgradeSize getSizeUpgrade() {
        return sizeUpgrade;
    }

    public Location getCenter() {
        return center;
    }

    public void setCenter(Location location) {
        this.center = location;
    }

    public String getName() {
        return name.replace("&", "§");
    }

    public void setName(String name) {
        this.name = name;
        StorageManager.instance.startUpdateIsland(this, StoragePriorities.LOW);
    }

    public Location getHome() {
        return home;
    }

    public void setHome(Location home) {
        this.home = home;
        StorageManager.instance.startUpdateIsland(this, StoragePriorities.NORMAL);
    }

    public UUID getUUID() {
        return uuid;
    }

    public boolean promote(UUID uuid) {
        if (members.containsKey(uuid)) {
            IslandRanks currentRank = getIslandRankFromUUID(uuid);
            IslandRanks nextRank = IslandRank.getNextRank(currentRank);
            if (currentRank == nextRank) {
                return false;
            }
            members.put(uuid, nextRank);
            StorageManager.instance.startUpdateIsland(this, StoragePriorities.NORMAL);
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
            StorageManager.instance.startUpdateIsland(this, StoragePriorities.NORMAL);
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
        StorageManager.instance.startUpdateIsland(this, StoragePriorities.HIGH);
        return true;
    }

    public ArrayList<IslandPerms> getPerms(IslandRanks rank) {
        return permsPerRanks.get(rank);
    }

    public void addPerms(IslandRanks rankTo, IslandPerms perm) {
        permsPerRanks.get(rankTo).add(perm);
        StorageManager.instance.startUpdateIsland(this, StoragePriorities.LOWEST);
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
        StorageManager.instance.startUpdateIsland(this, StoragePriorities.LOW);
        return members;
    }

    public boolean addPermsToRank(IslandRanks islandRank, IslandPerms islandPerms) {
        if (getPerms(islandRank) == null) {
            return false;
        }
        if (getPerms(islandRank).contains(islandPerms)) {
            return false;
        }
        addPerms(islandRank, islandPerms);
        StorageManager.instance.startUpdateIsland(this, StoragePriorities.LOWEST);
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
        StorageManager.instance.startUpdateIsland(this, StoragePriorities.LOWEST);
        return true;
    }

    public void addMembers(UUID member, IslandRanks rank) {
        this.members.put(member, rank);
        StorageManager.instance.startUpdateIsland(this, StoragePriorities.HIGH);
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

    public void sendMessageToEveryMember(String message, Player player) {
        PluginMessageManager.instance.sendMessage(player, "messageToIsland", message, "skyblock:toproxy");
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
        StorageManager.instance.startUpdateIsland(this, StoragePriorities.LOWEST);
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

    public void clearCoops() {
        this.coops.clear();
    }

    public void addIslandChatToggled(UUID uuid) {
        this.chatToggled.add(uuid);
        StorageManager.instance.startUpdateIsland(this, StoragePriorities.LOWEST);
    }

    public void removeIslandChatToggled(UUID uuid) {
        this.chatToggled.remove(uuid);
        StorageManager.instance.startUpdateIsland(this, StoragePriorities.LOWEST);
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
        StorageManager.instance.startUpdateIsland(this, StoragePriorities.LOW);
    }

    public IslandUpgradeGenerator getGeneratorUpgrade() {
        return generatorUpgrade;
    }

    public boolean isBanned(UUID uuid) {
        return banneds.contains(uuid);
    }

    public boolean addBanned(UUID uuid) {
        if (banneds.contains(uuid)) {
            return false;
        }
        banneds.add(uuid);
        StorageManager.instance.startUpdateIsland(this, StoragePriorities.NORMAL);
        return true;
    }

    public boolean removeBanned(UUID uuid) {
        if (!banneds.contains(uuid)) {
            return false;
        }
        banneds.remove(uuid);
        StorageManager.instance.startUpdateIsland(this, StoragePriorities.NORMAL);
        return true;
    }

    public ArrayList<UUID> getBanneds() {
        return banneds;
    }

    public ArrayList<IslandChallenge> getChallenges() {
        return challenges;
    }

    public void addSettingActivated(IslandSettings setting) {
        if (!activatedSettings.contains(setting)) {
            activatedSettings.add(setting);
            StorageManager.instance.startUpdateIsland(this, StoragePriorities.LOWEST);
        }
    }

    public void removeSettingActived(IslandSettings setting) {
        if (activatedSettings.contains(setting)) {
            activatedSettings.remove(setting);
            StorageManager.instance.startUpdateIsland(this, StoragePriorities.LOWEST);
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
        StorageManager.instance.startUpdateIsland(this, StoragePriorities.NORMAL);
    }

    public void removeChest(Chest chest) {
        chests.remove(chest);
        StorageManager.instance.startUpdateIsland(this, StoragePriorities.NORMAL);
    }

    public void addMinion(Minion minion) {
        minions.add(minion);
        StorageManager.instance.startUpdateIsland(this, StoragePriorities.NORMAL);
    }

    public void removeMinion(Minion minion) {
        minions.remove(minion);
        StorageManager.instance.startUpdateIsland(this, StoragePriorities.NORMAL);
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

    public ArrayList<Spawner> getSpawners() {
        return this.spawners;
    }

    public void addSpawner(Spawner spawner) {
        spawners.add(spawner);
        StorageManager.instance.startUpdateIsland(this, StoragePriorities.NORMAL);
    }

    public void removeSpawner(Spawner spawner) {
        if (spawners.contains(spawner)) {
            spawners.remove(spawner);
            StorageManager.instance.startUpdateIsland(this, StoragePriorities.NORMAL);
        }
    }

}
