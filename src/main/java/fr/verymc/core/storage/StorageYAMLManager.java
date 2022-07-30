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
import main.java.fr.verymc.island.minions.MinionType;
import main.java.fr.verymc.island.perms.IslandPerms;
import main.java.fr.verymc.island.perms.IslandRanks;
import main.java.fr.verymc.island.protections.IslandSettings;
import main.java.fr.verymc.island.upgrade.IslandUpgradeGenerator;
import main.java.fr.verymc.island.upgrade.IslandUpgradeMember;
import main.java.fr.verymc.island.upgrade.IslandUpgradeSize;
import main.java.fr.verymc.utils.WorldBorderUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class StorageYAMLManager {

    public static StorageYAMLManager instance;
    public boolean loading = true;

    public StorageYAMLManager() {
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
            try {
                String name = ConfigManager.instance.getDataIslands().getString(str + ".name");
                Location home = ConfigManager.instance.getDataIslands().getLocation(str + ".home");
                Location center = ConfigManager.instance.getDataIslands().getLocation(str + ".center");
                int id = Integer.parseInt(str.replace("'", ""));

                HashMap<UUID, IslandRanks> members = new HashMap<>();
                if (ConfigManager.instance.getDataIslands().contains(str + ".players")) {
                    String parts[] = ConfigManager.instance.getDataIslands().getString(str + ".players")
                            .replace("{", "").replace("}", "").split(",");
                    for (String part : parts) {
                        if (part == null) continue;
                        String stuData[] = part.split("=");
                        members.put(UUID.fromString(stuData[0].replace(" ", "")),
                                IslandRanks.valueOf(stuData[1].replace(" ", "")));
                    }
                }

                IslandUpgradeSize islandUpgradeSize = new IslandUpgradeSize(
                        ConfigManager.instance.getDataIslands().getInt(str + ".upgradeSizePrice"));

                IslandUpgradeMember islandUpgradeMember = new IslandUpgradeMember(
                        ConfigManager.instance.getDataIslands().getInt(str + ".upgradeMemberLevel"));

                WorldBorderUtil.Color color = null;
                try {
                    if (ConfigManager.instance.getDataIslands().contains(str + ".borderColor")) {
                        color = WorldBorderUtil.Color.valueOf(ConfigManager.instance.getDataIslands().
                                getString(str + ".borderColor"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    color = WorldBorderUtil.Color.BLUE;
                }

                IslandBank islandBank = new IslandBank(
                        ConfigManager.instance.getDataIslands().getInt(str + ".bank.money"),
                        ConfigManager.instance.getDataIslands().getInt(str + ".bank.crystaux"),
                        ConfigManager.instance.getDataIslands().getInt(str + ".bank.xp"));

                double value = ConfigManager.instance.getDataIslands().getDouble(str + ".value");

                boolean isPublic = ConfigManager.instance.getDataIslands().getBoolean(str + ".isPublic");

                IslandUpgradeGenerator islandUpgradeGenerator = new IslandUpgradeGenerator(ConfigManager.instance.getDataIslands().getInt(str + ".upgradeGeneratorLevel"));

                ArrayList<UUID> banneds = new ArrayList<>();
                if (ConfigManager.instance.getDataIslands().getString(str + ".banneds") != null) {
                    for (String par : ConfigManager.instance.getDataIslands().getString(str + ".banneds").split(",")) {
                        if (par == null) continue;
                        if (par.length() == 36) {
                            banneds.add(UUID.fromString(par));
                        }
                    }
                }

                ArrayList<IslandSettings> settings = new ArrayList<>();
                if (ConfigManager.instance.getDataIslands().getString(str + ".settings") != null) {
                    for (String par : ConfigManager.instance.getDataIslands().getString(str + ".settings").split(",")) {
                        if (par == null) continue;
                        if (par.length() < 4) continue;
                        settings.add(IslandSettings.matchSettings(par));
                    }
                } else {
                    settings = null;
                }

                ArrayList<IslandChallenge> list = new ArrayList<>();
                if (ConfigManager.instance.getDataIslands().contains(str + ".c")) {
                    for (String part : ConfigManager.instance.getDataIslands().getConfigurationSection(str + ".c").getKeys(false)) {
                        if (part == null) continue;
                        int prog = ConfigManager.instance.getDataIslands().getInt(str + ".c." + part + ".prog");
                        int max = ConfigManager.instance.getDataIslands().getInt(str + ".c." + part + ".max");
                        int palier = ConfigManager.instance.getDataIslands().getInt(str + ".c." + part + ".pal");
                        boolean act = ConfigManager.instance.getDataIslands().getBoolean(str + ".c." + part + ".act");
                        String nameC = ConfigManager.instance.getDataIslands().getString(str + ".c." + part + ".name");
                        Material material = Material.valueOf(ConfigManager.instance.getDataIslands().getString(str + ".c." + part + ".mat"));
                        list.add(new IslandChallenge(nameC, prog, material, palier, Integer.parseInt(part)
                                , act, max));
                    }
                }

                HashMap<IslandRanks, ArrayList<IslandPerms>> permsPerRanks = new HashMap<>();
                if (ConfigManager.instance.getDataIslands().contains(str + ".perm")) {
                    final String permsListToStr = IslandPerms.getAllPerms().toString().replace("[", "").replace("]", "");
                    for (String part : ConfigManager.instance.getDataIslands().getConfigurationSection(str + ".perm").getKeys(false)) {
                        if (part == null) continue;
                        ArrayList<IslandPerms> perms = new ArrayList<>();
                        for (String par : ConfigManager.instance.getDataIslands().getString(str + ".perm." + part).split(",")) {
                            if (par == null) continue;
                            if (par.length() <= 3) continue;
                            par = par.replace(" ", "");
                            if (permsListToStr.contains(par)) {
                                perms.add(IslandPerms.valueOf(par));
                            }
                        }
                        permsPerRanks.put(IslandRanks.valueOf(part), perms);
                    }
                }
                ArrayList<Chest> chests = new ArrayList<>();
                if (ConfigManager.instance.getDataIslands().getConfigurationSection(str + ".chests") != null) {
                    for (String str1 : ConfigManager.instance.getDataIslands().getConfigurationSection(str + ".chests").getKeys(false)) {
                        if (str1 == null) continue;
                        try {
                            String uuidString = ConfigManager.instance.getDataIslands().getString(str + ".chests." + str1 + ".uuid");
                            if (uuidString == null || uuidString.length() != 36) {
                                continue;
                            }
                            UUID uuid = UUID.fromString(uuidString);
                            long idChest = Long.parseLong(str1.replace("'", ""));
                            ItemStack itemStack = ConfigManager.instance.getDataIslands().getItemStack(str + ".chests." + str1 + ".item");
                            int type = ConfigManager.instance.getDataIslands().getInt(str + ".chests." + str1 + ".type");
                            Location loc = ConfigManager.instance.getDataIslands().getLocation(str + ".chests." + str1 + ".loc");
                            boolean isSell = ConfigManager.instance.getDataIslands().getBoolean(str + ".chests." + str1 + ".isSell");
                            long chunk = ConfigManager.instance.getDataIslands().getLong(str + ".chests." + str1 + ".chunk");
                            double price = ConfigManager.instance.getDataIslands().getDouble(str + ".chests." + str1 + ".price");
                            boolean active = ConfigManager.instance.getDataIslands().getBoolean(str + ".chests." + str1 + ".active");
                            chests.add(new Chest(type, loc, uuid, chunk, itemStack, price, isSell, active, idChest));
                        } catch (Exception e) {
                            e.printStackTrace();
                            Bukkit.broadcastMessage("§6§lData §8» §4§lErreur lors de la récupération des données de la base de donnée sur le chest #" + str);
                        }
                    }
                }
                ArrayList<Minion> minions = new ArrayList<>();
                if (ConfigManager.instance.getDataIslands().getConfigurationSection(str + ".minions") != null) {
                    for (String str1 : ConfigManager.instance.getDataIslands().getConfigurationSection(str + ".minions").getKeys(false)) {
                        if (str1 == null) continue;
                        try {
                            MinionType minionType = MinionType.valueOf(ConfigManager.instance.getDataIslands().getString(str + ".minions." + str1 + ".type"));
                            BlockFace blockFace = BlockFace.valueOf(ConfigManager.instance.getDataIslands().getString(str + ".minions." + str1 + ".blFace"));
                            int lvl = ConfigManager.instance.getDataIslands().getInt(str + ".minions." + str1 + ".lvl");
                            Location loc = ConfigManager.instance.getDataIslands().getLocation(str + ".minions." + str1 + ".loc");
                            Location locChest = null;
                            if (ConfigManager.instance.getDataIslands().getLocation(str + ".minions." + str1 + ".locChest") != null) {
                                locChest = ConfigManager.instance.getDataIslands().getLocation(str + ".minions." + str1 + ".locChest");
                            }
                            boolean linked = ConfigManager.instance.getDataIslands().getBoolean(str + ".minions." + str1 + ".linked");
                            boolean smelft = ConfigManager.instance.getDataIslands().getBoolean(str + ".minions." + str1 + ".smelt");
                            long idMinion = Long.parseLong(str1.replace("'", ""));

                            Block block = (locChest == null) ? null : locChest.getBlock();
                            minions.add(new Minion(idMinion, lvl, loc, minionType, blockFace, linked, block, smelft));
                        } catch (Exception e) {
                            e.printStackTrace();
                            Bukkit.broadcastMessage("§6§lData §8» §4§lErreur lors de la récupération des données de la base de donnée sur le minion #" + str);
                            continue;
                        }
                    }
                }
                boolean loadHere = false;
                if (Main.instance.serverType == ServerType.ISLAND) {
                    loadHere = true;
                }

                islands.add(new Island(name, home, center, id, members, islandUpgradeSize, islandUpgradeMember,
                        color, islandBank, islandUpgradeGenerator, banneds, list, false,
                        permsPerRanks, isPublic, value, settings, chests, minions, loadHere));
            } catch (Exception e) {
                e.printStackTrace();
                continue;
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

    public void sendDataToAPIAuto(boolean force) {
        CompletableFuture.supplyAsync(() -> {
            long start = System.currentTimeMillis();

            if (Main.instance.serverType == ServerType.ISLAND) {
                HashMap<String, Object> toSendIslands = new HashMap<>();
                HashMap<String, Object> toRemoveIslands = new HashMap<>(); // NEED TO NULL Island id because yaml don't support override data
                ArrayList<Island> islands = IslandManager.instance.islands;
                for (Island island : islands) {
                    HashMap<String, Object> toSendIsland = new HashMap<>();
                    try {
                        toSendIsland.put(island.getId() + ".name", island.getName());
                        toSendIsland.put(island.getId() + ".home", island.getHome());
                        toSendIsland.put(island.getId() + ".center", island.getCenter());
                        toSendIsland.put(island.getId() + ".players", island.getMembers().toString());
                        toSendIsland.put(island.getId() + ".upgradeSizeLevel", island.getSizeUpgrade().getLevel());
                        toSendIsland.put(island.getId() + ".upgradeMemberLevel", island.getMemberUpgrade().getLevel());
                        toSendIsland.put(island.getId() + ".borderColor", island.getBorderColor().toString());
                        toSendIsland.put(island.getId() + ".bank.money", island.getBank().getMoney());
                        toSendIsland.put(island.getId() + ".bank.crystaux", island.getBank().getCrystaux());
                        toSendIsland.put(island.getId() + ".bank.xp", island.getBank().getXp());
                        toSendIsland.put(island.getId() + ".value", island.getValue());
                        toSendIsland.put(island.getId() + ".isPublic", island.isPublic());
                        toSendIsland.put(island.getId() + ".upgradeGeneratorLevel", island.getGeneratorUpgrade().getLevel());
                        toSendIsland.put(island.getId() + ".banneds", island.getBanneds().toString().
                                replace("[", "").replace("]", "").replace(" ", ""));
                        toSendIsland.put(island.getId() + ".settings", island.getActivatedSettings().toString().
                                replace("[", "").replace("]", "").replace(" ", ""));
                        for (IslandChallenge islandChallenge : island.getChallenges()) {
                            toSendIsland.put(island.getId() + ".c." + islandChallenge.getId() + ".prog", islandChallenge.getProgress());
                            toSendIsland.put(island.getId() + ".c." + islandChallenge.getId() + ".pal", islandChallenge.getPalier());
                            toSendIsland.put(island.getId() + ".c." + islandChallenge.getId() + ".act", islandChallenge.isActive());
                            toSendIsland.put(island.getId() + ".c." + islandChallenge.getId() + ".mat", islandChallenge.getMaterial().toString());
                            toSendIsland.put(island.getId() + ".c." + islandChallenge.getId() + ".max", islandChallenge.getMaxProgress());
                            toSendIsland.put(island.getId() + ".c." + islandChallenge.getId() + ".name", islandChallenge.getName());
                        }
                        for (Map.Entry<IslandRanks, ArrayList<IslandPerms>> entry : island.getMapPerms().entrySet()) {
                            toSendIsland.put(island.getId() + ".perm." + entry.getKey().toString(), entry.getValue().toString()
                                    .replace("[", "").replace("]", "").
                                    replace(" ", ""));
                        }
                        for (Chest chest : island.getChests()) {
                            toSendIsland.put(island.getId() + ".chests." + chest.getId() + ".loc", chest.getBlock());
                            toSendIsland.put(island.getId() + ".chests." + chest.getId() + ".uuid", chest.getOwner().toString());
                            toSendIsland.put(island.getId() + ".chests." + chest.getId() + ".type", chest.getType());
                            toSendIsland.put(island.getId() + ".chests." + chest.getId() + ".isSell", chest.isSell());
                            toSendIsland.put(island.getId() + ".chests." + chest.getId() + ".active", chest.isActiveSellOrBuy());
                            toSendIsland.put(island.getId() + ".chests." + chest.getId() + ".price", chest.getPrice());
                            toSendIsland.put(island.getId() + ".chests." + chest.getId() + ".item", chest.getItemToBuySell());
                            toSendIsland.put(island.getId() + ".chests." + chest.getId() + ".chunk", chest.getChunkKey());
                        }
                        for (Minion minion : island.getMinions()) {
                            toSendIsland.put(island.getId() + ".minions." + minion.getID() + ".type", minion.getMinionType().toString());
                            toSendIsland.put(island.getId() + ".minions." + minion.getID() + ".lvl", minion.getLevelInt());
                            toSendIsland.put(island.getId() + ".minions." + minion.getID() + ".blFace", minion.getBlockFace().toString());
                            toSendIsland.put(island.getId() + ".minions." + minion.getID() + ".loc", minion.getBlocLocation());
                            toSendIsland.put(island.getId() + ".minions." + minion.getID() + ".linked", minion.isChestLinked());
                            toSendIsland.put(island.getId() + ".minions." + minion.getID() + ".smelt", minion.isAutoSmelt());
                            if (minion.getChestBloc() != null) {
                                toSendIsland.put(island.getId() + ".minions." + minion.getID() + ".locChest", minion.getChestBloc().getLocation());
                            } else {
                                toSendIsland.put(island.getId() + ".minions." + minion.getID() + ".locChest", null);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Bukkit.broadcastMessage("§cErreur lors de la sauvegarde de l'île #" + island.getId());
                        toSendIsland.clear();
                    } finally {
                        if (toSendIsland.size() > 0) {
                            toRemoveIslands.put(island.getId() + "", null);
                            toSendIslands.putAll(toSendIsland);
                        }
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

            if (force) {
                return true;
            }
            Bukkit.getScheduler().scheduleAsyncDelayedTask(Main.instance, new Runnable() {
                public void run() {
                    sendDataToAPIAuto(false);
                }
            }, 20 * 60 * 5);
            return true;
        }).join(); //makes it blocking
    }
}
