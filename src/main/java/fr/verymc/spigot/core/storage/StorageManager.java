package main.java.fr.verymc.spigot.core.storage;

import fr.verymc.api.wrapper.ApiKey;
import fr.verymc.api.wrapper.WrapperConfig;
import fr.verymc.api.wrapper.games.skyblock.islands.SkyblockIsland;
import fr.verymc.api.wrapper.games.skyblock.islands.SkyblockIslandManager;
import fr.verymc.api.wrapper.games.skyblock.islands.dto.CreateIslandDto;
import fr.verymc.api.wrapper.games.skyblock.islands.dto.UpdateIslandDto;
import fr.verymc.api.wrapper.games.skyblock.users.SkyblockUserManager;
import fr.verymc.api.wrapper.games.skyblock.users.dto.CreateUserDto;
import fr.verymc.api.wrapper.games.skyblock.users.dto.UpdateUserDto;
import main.java.fr.verymc.spigot.Main;
import main.java.fr.verymc.spigot.core.spawners.Spawner;
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
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.*;
import java.util.concurrent.CompletableFuture;

import static main.java.fr.verymc.spigot.island.Island.getReducedMapPerms;

public class StorageManager {

    public static final int INTERVAL_TICKS = 3;
    public static final int MAX_EDIT_TIMES = 10;
    public static final ApiKey API_KEY = new ApiKey("games-skyblock", "_LAtT4E3zonMoD4R5Gw2aMl9fWIorY1ESdbal26YKzjFx6D1nHVJ_1f4htTsQ6qQ.cFI1RW0tTkh0NV9CSWdqNQ.-QYLMb9cpRDbpQzZRfN_XsAPaZXgW1Z-id32Jn9NMh0");
    private static final WrapperConfig WRAPPER_CONFIG_SKYBLOCK_USER = new WrapperConfig("games.verymc.fr/skyblock/users", API_KEY);
    private final static fr.verymc.api.wrapper.games.skyblock.users.SkyblockUserManager MANAGER_USER = new SkyblockUserManager(WRAPPER_CONFIG_SKYBLOCK_USER);
    private static final WrapperConfig WRAPPER_CONFIG_SKYBLOCK_ISLANDS = new WrapperConfig("games.verymc.fr/skyblock/islands", API_KEY);
    private final static SkyblockIslandManager MANAGER_ISLAND = new SkyblockIslandManager(WRAPPER_CONFIG_SKYBLOCK_ISLANDS);
    /*private static final WrapperConfig WRAPPER_CONFIG_SKYBLOCK_AUCTIONS = new WrapperConfig("games.verymc.fr/skyblock/auctions",
            new ApiKey("games-skyblock", ""));*/
    public static StorageManager instance;
    //private final static SkyblockAuctionManager MANAGER_AUCTION = new SkyblockAuctionManager(WRAPPER_CONFIG);
    public HashMap<SkyblockUser, Integer> usersQueued = new HashMap<>();
    public HashMap<SkyblockUser, Integer> usersQueuedTimesEdited = new HashMap<>();
    public HashMap<Island, Integer> islandsQueued = new HashMap<>();
    public HashMap<Island, Integer> islandsQueuedTimesEdited = new HashMap<>();

    public StorageManager() {
        instance = this;

        loadAllUsers();
        loadAllIslands();

        skyblockUserCheckForUpdate();
        islandCheckForUpdate();
    }

    //ISLAND

    public void createIsland(Island island) {
        CompletableFuture.runAsync(() -> MANAGER_ISLAND.createIsland(new CreateIslandDto(island.getUUID(), island.getName(),
                ObjectConverter.instance.locationToString(PlayerUtils.instance.toCenterOf(island.getCenter(), island.getHome())),
                new JSONObject(island.getMembers()).toString(), new org.json.simple.JSONObject(getReducedMapPerms(island)).toString(),
                island.getSizeUpgrade().getLevel(), island.getMemberUpgrade().getLevel(), island.getGeneratorUpgrade().getLevel(),
                island.getBank().getMoney() + ObjectConverter.SEPARATOR + island.getBank().getCrystaux() + ObjectConverter.SEPARATOR + island.getBank().getXp(),
                island.getBanneds().toString(), island.isPublic(), Island.getChallengesString(island), island.getActivatedSettings().toString(),
                Island.getChestsString(island), Island.getMinionsString(island), new org.json.simple.JSONObject(island.getStackedBlocs()).toString(),
                Island.getSpawnersString(island))));
    }

    public ArrayList<Island> getIslands() {
        SkyblockIsland[] islands = MANAGER_ISLAND.getIslands();
        ArrayList<Island> islands1 = new ArrayList<>();

        for (SkyblockIsland i : islands) {
            HashMap<UUID, IslandRanks> members = new HashMap<>();
            org.json.simple.JSONObject jsonObjectMembers;
            try {
                jsonObjectMembers = (org.json.simple.JSONObject) new JSONParser().parse(i.getMembres());
            } catch (ParseException e) {
                System.out.println("Error while parsing members of island " + i.getUuid());
                break;
            }
            for (Object o : jsonObjectMembers.keySet()) {
                String key = (String) o;
                String value = (String) jsonObjectMembers.get(key);
                members.put(UUID.fromString(key), IslandRanks.match(value));
            }
            HashMap<IslandRanks, ArrayList<IslandPerms>> permsPerRanks = new HashMap<>();
            org.json.simple.JSONObject jsonObjectPerms = new org.json.simple.JSONObject(ObjectConverter.instance.stringToHashMap(i.getPermsPerRank()));
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
            IslandUpgradeSize sizeUpgrade = new IslandUpgradeSize(i.getSizeUpgrade());
            IslandUpgradeMember memberUpgrade = new IslandUpgradeMember(i.getMembreUpgrade());
            IslandUpgradeGenerator generatorUpgrade = new IslandUpgradeGenerator(i.getGeneratorUpgrade());
            String bank = i.getBank();
            String[] bankSplit = bank.split(ObjectConverter.SEPARATOR);
            double money = Double.parseDouble(bankSplit[0]);
            double crystaux = Double.parseDouble(bankSplit[1]);
            int xp = Integer.parseInt(bankSplit[2]);
            IslandBank bank1 = new IslandBank(money, crystaux, xp);
            ArrayList<UUID> banneds = new ArrayList<>();
            for (String str : ObjectConverter.instance.stringToArrayList(i.getBank())) {
                if (str.length() == 36) {
                    banneds.add(UUID.fromString(str));
                }
            }
            boolean isPublic = i.isPublic();
            ArrayList<IslandChallenge> islandChallenges = new ArrayList<>();
            String strCh = i.getChallenges();
            if (strCh != null) {
                String[] challenges = strCh.split(ObjectConverter.SEPARATOR_ELEMENT);
                for (String str : challenges) {
                    if (str.length() > 1) {
                        islandChallenges.add(IslandChallenge.fromString(str));
                    }
                }
            }
            ArrayList<IslandSettings> activatedSettings = new ArrayList<>();
            for (String str : ObjectConverter.instance.stringToArrayList(i.getSettings())) {
                IslandSettings islandSettings = IslandSettings.matchSettings(str);
                if (islandSettings != null) {
                    activatedSettings.add(islandSettings);
                }
            }
            ArrayList<Chest> chests = new ArrayList<>();
            String strChest = i.getChests();
            if (strChest != null) {
                String[] chestsSplit = strChest.split(ObjectConverter.SEPARATOR_ELEMENT);
                for (String s : chestsSplit) {
                    if (s.length() > 1)
                        chests.add(Chest.fromString(s));
                }
            }
            ArrayList<Minion> minions = new ArrayList<>();
            String strMinion = i.getMinions();
            if (strMinion != null) {
                String[] minionsSplit = strMinion.split(ObjectConverter.SEPARATOR_ELEMENT);
                for (String str : minionsSplit) {
                    if (str.length() > 1) {
                        minions.add(Minion.fromString(str));
                    }
                }
            }
            HashMap<Material, Double> stacked = new HashMap<>();
            org.json.simple.JSONObject jsonObject1 = new org.json.simple.JSONObject(ObjectConverter.instance.stringToHashMap(i.getStacked()));
            for (Object o : jsonObject1.keySet()) {
                String key = (String) o;
                Double value = Double.parseDouble(String.valueOf(jsonObject1.get(key)));
                stacked.put(Material.matchMaterial(key), value);
            }
            ArrayList<Spawner> spawners = new ArrayList<>();
            String strSpawners = i.getSpawners();
            if (strSpawners != null) {
                String[] spawnersSplit = strSpawners.split(ObjectConverter.SEPARATOR_ELEMENT);
                for (String str : spawnersSplit) {
                    if (str.length() > 1) {
                        spawners.add(Spawner.stringToSpawner(str));
                    }
                }
            }
            islands1.add(new Island(i.getIslandName(), ObjectConverter.instance.locationFromString(i.getIslandHome()), null, i.getUuid(), members, sizeUpgrade,
                    memberUpgrade, bank1, generatorUpgrade, banneds, islandChallenges, false, permsPerRanks, isPublic, 0.0, activatedSettings,
                    chests, minions, stacked, false, spawners));
        }
        return islands1;
    }

    public void loadAllIslands() {
        CompletableFuture.runAsync(() -> {
            ArrayList<Island> islands = getIslands();
            if (islands == null) return;
            islands.forEach(island -> {
                if (island != null) {
                    IslandManager.instance.islands.add(island);
                }
            });
        }).join();
    }

    public void updateIsland(Island island) {
        CompletableFuture.runAsync(() -> MANAGER_ISLAND.updateIsland(island.getUUID(), new UpdateIslandDto(island.getUUID(), island.getName(),
                ObjectConverter.instance.locationToString(PlayerUtils.instance.toCenterOf(island.getCenter(), island.getHome())),
                new JSONObject(island.getMembers()).toString(), new org.json.simple.JSONObject(getReducedMapPerms(island)).toString(),
                island.getSizeUpgrade().getLevel(), island.getMemberUpgrade().getLevel(), island.getGeneratorUpgrade().getLevel(),
                island.getBank().getMoney() + ObjectConverter.SEPARATOR + island.getBank().getCrystaux() + ObjectConverter.SEPARATOR + island.getBank().getXp(),
                island.getBanneds().toString(), island.isPublic(), Island.getChallengesString(island), island.getActivatedSettings().toString(),
                Island.getChestsString(island), Island.getMinionsString(island), new org.json.simple.JSONObject(island.getStackedBlocs()).toString(),
                Island.getSpawnersString(island))));
    }

    public void deleteIsland(UUID uuid) {
        CompletableFuture.runAsync(() -> MANAGER_ISLAND.destroyIsland(uuid));
    }


    //USER

    public void createUser(SkyblockUser skyblockUser) {
        CompletableFuture.runAsync(() -> MANAGER_USER.createUser(new CreateUserDto(skyblockUser.getUserUUID(), skyblockUser.getUsername(), skyblockUser.getLevel(), skyblockUser.getExp(),
                skyblockUser.isActive(), skyblockUser.getFlyLeft(), skyblockUser.hasHaste(), skyblockUser.hasHasteActive(), skyblockUser.hasSpeed(),
                skyblockUser.hasSpeedActive(), skyblockUser.hasJump(), skyblockUser.hasJumpActive(), (skyblockUser.getPlayerWarp() != null ? PlayerWarp.playerWarpToString(skyblockUser.getPlayerWarp()) : null),
                skyblockUser.getMoney())));
    }

    public void loadAllUsers() {
        CompletableFuture.runAsync(() -> Arrays.stream(MANAGER_USER.getUsers()).forEach(skyblockUser -> main.java.fr.verymc.spigot.core.storage.SkyblockUserManager.instance.addUser(
                new SkyblockUser(skyblockUser.getUsername(), skyblockUser.getUuid(), skyblockUser.getMoney(), skyblockUser.isHasHaste(),
                        skyblockUser.isHasHasteActive(), skyblockUser.isHasSpeed(), skyblockUser.isHasSpeedActive(), skyblockUser.isHasJump(),
                        skyblockUser.isHasJumpActive(), skyblockUser.getFlyLeft(), skyblockUser.isFlyActive(), false,
                        0, PlayerWarp.playerWarpFromString(skyblockUser.getPlayerWarp()), skyblockUser.getPlayerExp(), skyblockUser.getPlayerLevel())))).join();
    }

    public SkyblockUser getUser(UUID uuid) {
        Optional<fr.verymc.api.wrapper.games.skyblock.users.SkyblockUser> skyblockUser = MANAGER_USER.getUser(uuid);
        if (skyblockUser.isEmpty()) return null;
        return new SkyblockUser(skyblockUser.get().getUsername(), skyblockUser.get().getUuid(), skyblockUser.get().getMoney(), skyblockUser.get().isHasHaste(),
                skyblockUser.get().isHasHasteActive(), skyblockUser.get().isHasSpeed(), skyblockUser.get().isHasSpeedActive(), skyblockUser.get().isHasJump(),
                skyblockUser.get().isHasJumpActive(), skyblockUser.get().getFlyLeft(), skyblockUser.get().isFlyActive(), false,
                0, PlayerWarp.playerWarpFromString(skyblockUser.get().getPlayerWarp()), skyblockUser.get().getPlayerExp(), skyblockUser.get().getPlayerLevel());
    }

    public void updateUser(SkyblockUser skyblockUser) {
        CompletableFuture.runAsync(() -> MANAGER_USER.updateUser(skyblockUser.getUserUUID(), new UpdateUserDto(skyblockUser.getUserUUID(), skyblockUser.getUsername(), skyblockUser.getLevel(), skyblockUser.getExp(),
                skyblockUser.isActive(), skyblockUser.getFlyLeft(), skyblockUser.hasHaste(), skyblockUser.hasHasteActive(), skyblockUser.hasSpeed(),
                skyblockUser.hasSpeedActive(), skyblockUser.hasJump(), skyblockUser.hasJumpActive(), (skyblockUser.getPlayerWarp() != null ? PlayerWarp.playerWarpToString(skyblockUser.getPlayerWarp()) : null),
                skyblockUser.getMoney())));
    }

    public void deleteUser(SkyblockUser skyblockUser) {
        //PAS IMPLÉMENTÉ DANS LE PLUGIN DONC PAS NECESSAIRE
    }


    //QUEUE PART

    //ISLAND
    public void forceUpdateAllQueuedIslands() {
        for (Map.Entry<Island, Integer> entry : islandsQueued.entrySet()) {
            updateIsland(entry.getKey());
        }
        new ArrayList<>(islandsQueued.keySet()).forEach(island -> islandsQueued.remove(island));
    }

    public void startUpdateIsland(Island island, StoragePriorities priority) {
        if (priority.getTicks() == 0) {
            updateIsland(island);
            return;
        }
        if (islandsQueued.containsKey(island)) {
            islandsQueued.replace(island, priority.getTicks());
            if (islandsQueuedTimesEdited.containsKey(island)) {
                if (islandsQueuedTimesEdited.get(island) >= MAX_EDIT_TIMES) {
                    islandsQueuedTimesEdited.remove(island);
                    updateIsland(island);
                    return;
                }
                islandsQueuedTimesEdited.replace(island, islandsQueuedTimesEdited.get(island) + 1);
                return;
            }
            islandsQueuedTimesEdited.put(island, 1);
            return;
        }
        islandsQueued.put(island, priority.getTicks());
    }

    public void islandCheckForUpdate() {
        for (Map.Entry<Island, Integer> entry : islandsQueued.entrySet()) {
            if (entry.getValue() <= 0) {
                updateIsland(entry.getKey());
                islandsQueued.remove(entry.getKey());
            } else {
                islandsQueued.replace(entry.getKey(), entry.getValue() - INTERVAL_TICKS);
            }
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, this::islandCheckForUpdate, INTERVAL_TICKS);
    }


    //SKYBLOCK_USER
    public void forceUpdateAllQueuedUsers() {
        for (Map.Entry<SkyblockUser, Integer> entry : usersQueued.entrySet()) {
            updateUser(entry.getKey());
        }
        new ArrayList<>(usersQueued.keySet()).forEach(usersQueued::remove);
    }

    public void startUpdateUser(SkyblockUser skyblockUser, StoragePriorities priority) {
        if (priority.getTicks() == 0) {
            updateUser(skyblockUser);
            return;
        }
        if (usersQueued.containsKey(skyblockUser.getUserUUID())) {
            usersQueued.replace(skyblockUser, priority.getTicks());
            if (usersQueuedTimesEdited.containsKey(skyblockUser)) {
                if (usersQueuedTimesEdited.get(skyblockUser) >= MAX_EDIT_TIMES) {
                    usersQueuedTimesEdited.remove(skyblockUser);
                    updateUser(skyblockUser);
                    return;
                }
                usersQueuedTimesEdited.replace(skyblockUser, usersQueuedTimesEdited.get(skyblockUser) + 1);
                return;
            }
            usersQueuedTimesEdited.put(skyblockUser, 1);
            return;
        }
        usersQueued.put(skyblockUser, priority.getTicks());
    }

    public void skyblockUserCheckForUpdate() {
        for (Map.Entry<SkyblockUser, Integer> entry : usersQueued.entrySet()) {
            if (entry.getValue() <= 0) {
                updateUser(entry.getKey());
                usersQueued.remove(entry.getKey());
            } else {
                usersQueued.replace(entry.getKey(), entry.getValue() - INTERVAL_TICKS);
            }
        }

        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, this::skyblockUserCheckForUpdate, INTERVAL_TICKS);
    }

}
