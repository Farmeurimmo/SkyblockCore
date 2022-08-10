package main.java.fr.verymc.spigot.core.storage;

import main.java.fr.verymc.commons.enums.ServerType;
import main.java.fr.verymc.spigot.Main;
import main.java.fr.verymc.spigot.island.Island;
import main.java.fr.verymc.spigot.island.IslandManager;
import main.java.fr.verymc.spigot.island.bank.IslandBank;
import main.java.fr.verymc.spigot.island.blocks.Chest;
import main.java.fr.verymc.spigot.island.challenges.IslandChallenge;
import main.java.fr.verymc.spigot.island.minions.Minion;
import main.java.fr.verymc.spigot.island.perms.IslandPerms;
import main.java.fr.verymc.spigot.island.perms.IslandRank;
import main.java.fr.verymc.spigot.island.perms.IslandRanks;
import main.java.fr.verymc.spigot.island.playerwarps.PlayerWarp;
import main.java.fr.verymc.spigot.island.protections.IslandSettings;
import main.java.fr.verymc.spigot.island.upgrade.IslandUpgradeGenerator;
import main.java.fr.verymc.spigot.island.upgrade.IslandUpgradeMember;
import main.java.fr.verymc.spigot.island.upgrade.IslandUpgradeSize;
import main.java.fr.verymc.spigot.utils.ObjectConverter;
import main.java.fr.verymc.spigot.utils.PlayerUtils;
import main.java.fr.verymc.spigot.utils.WorldBorderUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
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
            getDataFromAPI();
            IslandManager.instance.pasteAndLoadIslands();
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

                island.setCenter(IslandManager.instance.getAFreeCenter());

                island.setHome(PlayerUtils.instance.toCenterOf(island.getCenter(), island.getHome()));

                for (Chest chest : island.getChests()) {
                    chest.setBlock(PlayerUtils.instance.toCenterOf(island.getCenter(), chest.getBlock()));
                }

                for (Minion minion : island.getMinions()) {
                    minion.setBlocLocation(PlayerUtils.instance.toCenterOf(island.getCenter(), minion.getBlocLocation()));
                    if (minion.getChestBloc() != null) {
                        minion.setChestBloc(PlayerUtils.instance.toCenterOf(island.getCenter(), minion.getChestBloc().getLocation()).getBlock());
                    }
                }

                //SEND Islands to -> IslandManager.instance.islands
                if (Main.instance.serverType == ServerType.SKYBLOCK_ISLAND) {
                    IslandManager.instance.islands.add(island);
                }
            }
        }
        //DATA ISLANDS

        for (String str : ConfigManager.instance.getDataSkyblockUser().getKeys(false)) {
            if (str == null) continue;
            try {
                SkyblockUser skyblockUser = skyblockUserFromJSON((JSONObject) new JSONParser().parse(ConfigManager.instance.getDataSkyblockUser().getString(str)));
                if (skyblockUser != null) {
                    skyblockUsers.add(skyblockUser);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //DATA USERS

        //SEND SkyblockUser to -> SkyblockUserManager.instance.users
        SkyblockUserManager.instance.users = skyblockUsers;
    }

    public void sendDataToAPIAuto(boolean stop) {
        long start = System.currentTimeMillis();

        if (Main.instance.serverType == ServerType.SKYBLOCK_ISLAND) {
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
            try {
                toSendSkyUsers.put(skyblockUser.getUserUUID().toString(), skyblockUserToJSON(skyblockUser).toJSONString());
            } catch (Exception e) {
                e.printStackTrace();
                Bukkit.broadcastMessage("§6§lData §8§l» §cUne erreur est survenue lors de la sauvegarde des données des utilisateurs (uuid: " +
                        skyblockUser.getUserUUID().toString() + ")");
            } finally {
                toRemoveSkyUsers.put(skyblockUser.getUserUUID().toString(), null);
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


    public JSONObject skyblockUserToJSON(SkyblockUser skyblockUser) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("uuid", skyblockUser.getUserUUID().toString());
        jsonObject.put("userN", skyblockUser.getUsername());
        jsonObject.put("money", skyblockUser.getMoney());
        if (skyblockUser.getFlyLeft() > 0) {
            jsonObject.put("flyL", skyblockUser.getFlyLeft());
            jsonObject.put("flyAc", skyblockUser.isActive());
        }
        jsonObject.put("hast", skyblockUser.hasHaste());
        jsonObject.put("hastAct", skyblockUser.hasHasteActive());
        jsonObject.put("speed", skyblockUser.hasSpeed());
        jsonObject.put("speedAct", skyblockUser.hasSpeedActive());
        jsonObject.put("jump", skyblockUser.hasJump());
        jsonObject.put("jumpAct", skyblockUser.hasJumpActive());
        if (skyblockUser.getPlayerWarp() != null) {
            jsonObject.put("pw", PlayerWarp.playerWarpToString(skyblockUser.getPlayerWarp()));
        }
        return jsonObject;
    }

    public SkyblockUser skyblockUserFromJSON(JSONObject jsonObject) {
        UUID uuid = UUID.fromString(String.valueOf(jsonObject.get("uuid")));
        String username = String.valueOf(jsonObject.get("userN"));
        double money = Double.parseDouble(String.valueOf(jsonObject.get("money")));
        int flyLeft = -1;
        boolean isActive = false;
        if (jsonObject.get("flyL") != null) {
            flyLeft = Integer.parseInt(String.valueOf(jsonObject.get("flyL")));
            isActive = Boolean.parseBoolean(String.valueOf(jsonObject.get("flyAc")));
        }
        boolean hasHaste = Boolean.parseBoolean(String.valueOf(jsonObject.get("hast")));
        boolean hasHasteActive = Boolean.parseBoolean(String.valueOf(jsonObject.get("hastAct")));
        boolean hasSpeed = Boolean.parseBoolean(String.valueOf(jsonObject.get("speed")));
        boolean hasSpeedActive = Boolean.parseBoolean(String.valueOf(jsonObject.get("speedAct")));
        boolean hasJump = Boolean.parseBoolean(String.valueOf(jsonObject.get("jump")));
        boolean hasJumpActive = Boolean.parseBoolean(String.valueOf(jsonObject.get("jumpAct")));
        PlayerWarp playerWarp = null;
        if (jsonObject.get("pw") != null) {
            try {
                playerWarp = PlayerWarp.playerWarpFromString(String.valueOf(jsonObject.get("pw")));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new SkyblockUser(username, uuid, money, hasHaste, hasHasteActive, hasSpeed, hasSpeedActive,
                hasJump, hasJumpActive, flyLeft, isActive, false, 0, playerWarp);
    }

    public JSONObject islandToJSON(Island i) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", i.getName());
        jsonObject.put("home", ObjectConverter.instance.locationToString(PlayerUtils.instance.addCenterTo(i.getCenter(), i.getHome())));
        jsonObject.put("id", i.getUUID().toString());
        jsonObject.put("members", new JSONObject(i.getMembers()).toString());
        jsonObject.put("rankPerms", new JSONObject(getReducedMapPerms(i)).toString());
        jsonObject.put("siUp", i.getSizeUpgrade().getLevel());
        jsonObject.put("mbUp", i.getMemberUpgrade().getLevel());
        jsonObject.put("genUp", i.getGeneratorUpgrade().getLevel());
        jsonObject.put("border", WorldBorderUtils.instanceClass.borderToString(i.getBorderColor()));
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
        System.out.println(jsonObject);
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
        WorldBorderUtils.Color borderColor = WorldBorderUtils.instanceClass.borderFromString((String) jsonObject.get("border"));
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
        return new Island(name, home, null, id, members, sizeUpgrade, memberUpgrade, borderColor, bank1, generatorUpgrade,
                banneds, islandChallenges, false, permsPerRanks, isPublic, 0.0, activatedSettings, chests,
                minions, stacked, false);
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
