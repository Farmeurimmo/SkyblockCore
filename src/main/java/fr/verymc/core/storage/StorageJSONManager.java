package main.java.fr.verymc.core.storage;

import main.java.fr.verymc.Main;
import main.java.fr.verymc.commons.enums.ServerType;
import main.java.fr.verymc.core.playerwarps.PlayerWarp;
import main.java.fr.verymc.island.Island;
import main.java.fr.verymc.island.IslandManager;
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
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class StorageJSONManager {

    public static StorageJSONManager instance;
    public boolean loading = true;

    public StorageJSONManager() {
        instance = this;
        getData(true);
        loading = false;
        Bukkit.getScheduler().scheduleAsyncDelayedTask(Main.instance, new Runnable() {
            @Override
            public void run() {
                sendDataToAPIAuto(false);
            }
        }, 20 * 5);
    }

    public void getData(boolean blockThread) {
        if (blockThread) {
            CompletableFuture.runAsync(() -> {
                getDataFromAPI();
                IslandManager.instance.pasteAndLoadIslands();
            }).join();
        } else {
            CompletableFuture.runAsync(() -> {
                getDataFromAPI();
            });
        }
    }

    public void getDataFromAPI() {
        //tries to fetch data from a database which doesn’t block the main thread but another thread.

        //API FETCH DATA

        ArrayList<Island> islands = new ArrayList<>();
        ArrayList<SkyblockUser> skyblockUsers = new ArrayList<>();

        for (String str : ConfigManager.instance.getDataIslands().getKeys(false)) {
            if (str == null) continue;
            Island island = islandFromJSON(ConfigManager.instance.getDataIslands().getString(str));
            if (island != null) {
                islands.add(island);
            }
        }
        //DATA ISLANDS

        for (String str : ConfigManager.instance.getDataSkyblockUser().getKeys(false)) {
            if (str == null) continue;
            try {
                UUID uuid = UUID.fromString(str);
                double money = ConfigManager.instance.getDataSkyblockUser().getDouble(str + ".money");
                int flyLeft = ConfigManager.instance.getDataSkyblockUser().getInt(str + ".flyLeft");
                boolean hasHaste = ConfigManager.instance.getDataSkyblockUser().getBoolean(str + ".hasHaste");
                boolean hasSpeed = ConfigManager.instance.getDataSkyblockUser().getBoolean(str + ".hasSpeed");
                boolean hasJump = ConfigManager.instance.getDataSkyblockUser().getBoolean(str + ".hasJump");

                PlayerWarp playerWarp = null;
                if (ConfigManager.instance.getDataSkyblockUser().contains(str + ".pw")) {
                    String name = ConfigManager.instance.getDataSkyblockUser().getString(str + ".pw.name");
                    Location location = ConfigManager.instance.getDataSkyblockUser().getLocation(str + ".pw.loc");
                    double note = ConfigManager.instance.getDataSkyblockUser().getDouble(str + ".pw.note");
                    double vues = ConfigManager.instance.getDataSkyblockUser().getDouble(str + ".pw.vues");
                    boolean promu = ConfigManager.instance.getDataSkyblockUser().getBoolean(str + ".pw.promu");
                    double timeLeftPromu = ConfigManager.instance.getDataSkyblockUser().getDouble(str + ".pw.timeLeftPromu");
                    ArrayList<UUID> alreadyVoted = new ArrayList<>();
                    if (ConfigManager.instance.getDataSkyblockUser().get(str + ".pw.voted") != null) {
                        for (String par : ConfigManager.instance.getDataSkyblockUser().getString(str + ".pw.voted").split(",")) {
                            if (par == null || par.length() != 36) {
                                continue;
                            }
                            alreadyVoted.add(UUID.fromString(par));
                        }
                    }
                    playerWarp = new PlayerWarp(name, location, promu, timeLeftPromu, vues, note, alreadyVoted);

                }

                skyblockUsers.add(new SkyblockUser(Bukkit.getOfflinePlayer(uuid).getName(), uuid, money,
                        hasHaste, hasHaste, hasSpeed, hasSpeed, hasJump, hasJump, flyLeft, false,
                        false, 0, playerWarp));
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
        //DATA USERS

        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
            @Override
            public void run() {
                //SEND Islands to -> IslandManager.instance.islands
                if (Main.instance.serverType == ServerType.ISLAND) {
                    IslandManager.instance.islands = islands;
                }

                //SEND SkyblockUser to -> SkyblockUserManager.instance.users
                SkyblockUserManager.instance.users = skyblockUsers;
            }
        }, 0);
    }

    public void sendDataToAPIAuto(boolean stop) {
        long start = System.currentTimeMillis();

        if (Main.instance.serverType == ServerType.ISLAND) {
            HashMap<String, Object> toSendIslands = new HashMap<>();
            HashMap<String, Object> toRemoveIslands = new HashMap<>(); // NEED TO NULL Island id because yaml don't support override data
            ArrayList<Island> islands = IslandManager.instance.islands;
            for (Island island : islands) {
                try {
                    toSendIslands.put(island.getUUID().toString(), islandToJSON(island).toJSONString());
                } catch (Exception e) {
                    e.printStackTrace();
                    Bukkit.broadcastMessage("§cErreur lors de la sauvegarde de l'île #" + island.getUUID().toString());
                } finally {
                    toRemoveIslands.put(island.getUUID().toString(), null);
                }
            }
            AsyncConfig.instance.setAndSaveAsyncBlockCurrentThread(toRemoveIslands, ConfigManager.instance.getDataIslands(),
                    ConfigManager.instance.islandsFile);
            AsyncConfig.instance.setAndSaveAsync(toSendIslands, ConfigManager.instance.getDataIslands(),
                    ConfigManager.instance.islandsFile);
        }

        ArrayList<SkyblockUser> skyblockUsers = SkyblockUserManager.instance.users;

        HashMap<String, Object> toSendSkyUsers = new HashMap<>();
        HashMap<String, Object> toRemoveSkyUsers = new HashMap<>();
        for (SkyblockUser skyblockUser : skyblockUsers) {
            HashMap<String, Object> toSendSkyUser = new HashMap<>();
            try {
                final String uuid = skyblockUser.getUserUUID().toString();
                toSendSkyUser.put(uuid + ".flyLeft", skyblockUser.getFlyLeft());
                toSendSkyUser.put(uuid + ".money", skyblockUser.getMoney());
                toSendSkyUser.put(uuid + ".hasHaste", skyblockUser.hasHaste());
                toSendSkyUser.put(uuid + ".hasSpeed", skyblockUser.hasSpeed());
                toSendSkyUser.put(uuid + ".hasJump", skyblockUser.hasJump());
                if (skyblockUser.getPlayerWarp() != null) {
                    toSendSkyUser.put(uuid + ".pw.name", skyblockUser.getPlayerWarp().getName());
                    toSendSkyUser.put(uuid + ".pw.loc", skyblockUser.getPlayerWarp().getLocation());
                    toSendSkyUser.put(uuid + ".pw.note", skyblockUser.getPlayerWarp().getNote());
                    toSendSkyUser.put(uuid + ".pw.vues", skyblockUser.getPlayerWarp().getVues());
                    toSendSkyUser.put(uuid + ".pw.promu", skyblockUser.getPlayerWarp().isPromoted);
                    toSendSkyUser.put(uuid + ".pw.timeLeftPromu", skyblockUser.getPlayerWarp().getTimeLeftPromoted());
                    toSendSkyUser.put(uuid + ".pw.voted", skyblockUser.getPlayerWarp().getAlreadyVoted().toString()
                            .replace("[", "").replace("]", "").replace(" ", ""));
                }
            } catch (Exception e) {
                e.printStackTrace();
                Bukkit.broadcastMessage("§6§lData §8§l» §cUne erreur est survenue lors de la sauvegarde des données des utilisateurs (uuid: " +
                        skyblockUser.getUserUUID().toString() + ")");
                toSendSkyUser.clear();
            } finally {
                if (toSendSkyUser.size() > 0) {
                    toRemoveSkyUsers.put(skyblockUser.getUserUUID().toString(), null);
                    toSendSkyUsers.putAll(toSendSkyUser);
                }
            }
        }
        AsyncConfig.instance.setAndSaveAsyncBlockCurrentThread(toRemoveSkyUsers, ConfigManager.instance.getDataSkyblockUser(),
                ConfigManager.instance.skyblockUserFile);
        AsyncConfig.instance.setAndSaveAsync(toSendSkyUsers, ConfigManager.instance.getDataSkyblockUser(),
                ConfigManager.instance.skyblockUserFile);


        Bukkit.broadcastMessage("§6§lData §8§l» §fMise à jour complète de la database en " + (System.currentTimeMillis()
                - start) + "ms.");

        if (stop) {
            return;
        }
        Bukkit.getScheduler().scheduleAsyncDelayedTask(Main.instance, new Runnable() {
            public void run() {
                sendDataToAPIAuto(false);
            }
        }, 20 * 60 * 5);
    }

    public JSONObject islandToJSON(Island i) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", i.getName());
        jsonObject.put("home", ObjectConverter.instance.locationToString(i.getHome()));
        jsonObject.put("center", ObjectConverter.instance.locationToString(i.getCenter()));
        jsonObject.put("id", i.getUUID().toString());
        jsonObject.put("members", new JSONObject(i.getMembers()).toString());
        jsonObject.put("rankPerms", new JSONObject(getReducedMapPerms(i)).toString());
        jsonObject.put("siUp", i.getSizeUpgrade().getLevel());
        jsonObject.put("mbUp", i.getMemberUpgrade().getLevel());
        jsonObject.put("genUp", i.getGeneratorUpgrade().getLevel());
        jsonObject.put("border", WorldBorderUtil.instanceClass.borderToString(i.getBorderColor()));
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

    public Island islandFromJSON(String strJSON) {
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) new JSONParser().parse(strJSON);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String name = (String) jsonObject.get("name");
        Location home = ObjectConverter.instance.locationFromString((String) jsonObject.get("home"));
        Location center = ObjectConverter.instance.locationFromString((String) jsonObject.get("center"));
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

    public HashMap<IslandRanks, ArrayList<IslandPerms>> getReducedMapPerms(Island island) {
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
}
