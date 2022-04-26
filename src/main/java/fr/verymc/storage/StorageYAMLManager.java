package main.java.fr.verymc.storage;

import main.java.fr.verymc.Main;
import main.java.fr.verymc.blocks.Chest;
import main.java.fr.verymc.blocks.ChestManager;
import main.java.fr.verymc.island.Island;
import main.java.fr.verymc.island.IslandManager;
import main.java.fr.verymc.island.bank.IslandBank;
import main.java.fr.verymc.island.challenges.IslandChallenge;
import main.java.fr.verymc.island.perms.IslandPerms;
import main.java.fr.verymc.island.perms.IslandRanks;
import main.java.fr.verymc.island.upgrade.IslandUpgradeGenerator;
import main.java.fr.verymc.island.upgrade.IslandUpgradeMember;
import main.java.fr.verymc.island.upgrade.IslandUpgradeSize;
import main.java.fr.verymc.minions.Minion;
import main.java.fr.verymc.minions.MinionManager;
import main.java.fr.verymc.minions.MinionType;
import main.java.fr.verymc.playerwarps.PlayerWarp;
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
        getDataFromAPI();
        loading = false;
        Bukkit.getScheduler().scheduleAsyncDelayedTask(Main.instance, new Runnable() {
            @Override
            public void run() {
                sendDataToAPIAuto(false);
            }
        }, 30);
    }

    public boolean getDataFromAPI() {
        CompletableFuture.supplyAsync(() -> {
            //tries to fetch data from a database which doesn’t block the main thread but another thread.

            //API FETCH DATA

            ArrayList<Minion> minions = new ArrayList<>();
            ArrayList<Island> islands = new ArrayList<>();
            ArrayList<SkyblockUser> skyblockUsers = new ArrayList<>();
            ArrayList<Chest> chests = new ArrayList<>();

            for (String str : ConfigManager.instance.getDataMinions().getKeys(false)) {
                if (str == null) continue;
                try {
                    String ownerUUIDstr = ConfigManager.instance.getDataMinions().getString(str + ".uuid");
                    if (ownerUUIDstr == null || ownerUUIDstr.length() != 36) {
                        continue;
                    }
                    UUID owner = UUID.fromString(ownerUUIDstr);
                    MinionType minionType = MinionType.valueOf(ConfigManager.instance.getDataMinions().getString(str + ".type"));
                    BlockFace blockFace = BlockFace.valueOf(ConfigManager.instance.getDataMinions().getString(str + ".blFace"));
                    int lvl = ConfigManager.instance.getDataMinions().getInt(str + ".lvl");
                    Location loc = ConfigManager.instance.getDataMinions().getLocation(str + ".loc");
                    Location locChest = ConfigManager.instance.getDataMinions().getLocation(str + ".locChest");
                    boolean linked = ConfigManager.instance.getDataMinions().getBoolean(str + ".linked");
                    boolean smelft = ConfigManager.instance.getDataMinions().getBoolean(str + ".smelt");
                    long id = Long.parseLong(str.replace("'", ""));

                    Block block = (locChest == null) ? null : locChest.getBlock();
                    minions.add(new Minion(id, owner, lvl, loc, minionType, blockFace, linked, block, smelft));
                } catch (Exception e) {
                    e.printStackTrace();
                    Bukkit.broadcastMessage("§6§lData §8» §4§lErreur lors de la récupération des données de la base de donnée sur le minion #" + str);
                    continue;
                }
            }
            //DATA MINIONS

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
                            ConfigManager.instance.getDataIslands().getInt(str + ".upgradeSizeSize"),
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

                    ArrayList<IslandChallenge> list = new ArrayList<>();
                    if (ConfigManager.instance.getDataIslands().contains(str + ".c")) {
                        for (String part : ConfigManager.instance.getDataIslands().getConfigurationSection(str + ".c").getKeys(false)) {
                            if (part == null) continue;
                            int prog = ConfigManager.instance.getDataIslands().getInt(str + ".c." + part + ".prog");
                            int max = ConfigManager.instance.getDataIslands().getInt(str + ".c." + part + ".max");
                            int type = ConfigManager.instance.getDataIslands().getInt(str + ".c." + part + ".type");
                            int palier = ConfigManager.instance.getDataIslands().getInt(str + ".c." + part + ".pal");
                            boolean act = ConfigManager.instance.getDataIslands().getBoolean(str + ".c." + part + ".act");
                            String nameC = ConfigManager.instance.getDataIslands().getString(str + ".c." + part + ".name");
                            Material material = Material.valueOf(ConfigManager.instance.getDataIslands().getString(str + ".c." + part + ".mat"));
                            ArrayList<Material> mats = new ArrayList<>();
                            if (ConfigManager.instance.getDataIslands().getString(str + ".c." + part + ".mats") != null) {
                                for (String par : ConfigManager.instance.getDataIslands().getString(str + ".c." + part + ".mats").split(",")) {
                                    if (par != null && Material.matchMaterial(par) != null) {
                                        mats.add(Material.valueOf(par));
                                    }
                                }
                            }
                            list.add(new IslandChallenge(nameC, prog, material, palier, Integer.parseInt(part)
                                    , act, max, type, mats));
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

                    islands.add(new Island(name, home, center, id, members, islandUpgradeSize, islandUpgradeMember,
                            color, islandBank, islandUpgradeGenerator, banneds, list, false,
                            permsPerRanks, isPublic, value));
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

            for (String str : ConfigManager.instance.getDataChests().getKeys(false)) {
                if (str == null) continue;
                try {
                    String uuidString = ConfigManager.instance.getDataChests().getString(str + ".uuid");
                    if (uuidString == null || uuidString.length() != 36) {
                        continue;
                    }
                    UUID uuid = UUID.fromString(uuidString);
                    long id = Long.parseLong(str.replace("'", ""));
                    ItemStack itemStack = ConfigManager.instance.getDataChests().getItemStack(str + ".item");
                    int type = ConfigManager.instance.getDataChests().getInt(str + ".type");
                    Location loc = ConfigManager.instance.getDataChests().getLocation(str + ".loc");
                    boolean isSell = ConfigManager.instance.getDataChests().getBoolean(str + ".isSell");
                    long chunk = ConfigManager.instance.getDataChests().getLong(str + ".chunk");
                    double price = ConfigManager.instance.getDataChests().getDouble(str + ".price");
                    boolean active = ConfigManager.instance.getDataChests().getBoolean(str + ".active");

                    chests.add(new Chest(type, loc, uuid, chunk, itemStack, price, isSell, active, id));
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
            }
            //DATA CHESTS


            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
                @Override
                public void run() {
                    //SEND Minions to -> MinionManager.instance.minions
                    MinionManager.instance.minions = minions;

                    //SEND Islands to -> IslandManager.instance.islands
                    IslandManager.instance.islands = islands;

                    //SEND SkyblockUser to -> SkyblockUserManager.instance.users
                    SkyblockUserManager.instance.users = skyblockUsers;

                    //SEND Chests to -> ChestManager.instance.chests
                    ChestManager.instance.chests = chests;
                }
            }, 0);
            return true;
        }).join(); //makes it blocking
        return true;
    }

    public void sendDataToAPIAuto(boolean force) {
        CompletableFuture.supplyAsync(() -> {
            long start = System.currentTimeMillis();

            HashMap<String, Object> toSendMinions = new HashMap<>();
            HashMap<String, Object> toRemoveMinions = new HashMap<>(); // NEED TO NULL Minion id because yaml don't support override data
            ArrayList<Minion> minions = MinionManager.instance.minions;
            for (Minion minion : minions) {
                HashMap<String, Object> toSendMinion = new HashMap<>();
                try {
                    toSendMinion.put(minion.getID() + ".uuid", minion.getOwnerUUID().toString());
                    toSendMinion.put(minion.getID() + ".type", minion.getMinionType().toString());
                    toSendMinion.put(minion.getID() + ".lvl", minion.getLevelInt());
                    toSendMinion.put(minion.getID() + ".blFace", minion.getBlockFace().toString());
                    toSendMinion.put(minion.getID() + ".loc", minion.getBlocLocation());
                    toSendMinion.put(minion.getID() + ".locChest", minion.getChestBloc());
                    toSendMinion.put(minion.getID() + ".linked", minion.isChestLinked());
                    toSendMinion.put(minion.getID() + ".smelt", minion.isAutoSmelt());
                } catch (Exception e) {
                    e.printStackTrace();
                    Bukkit.broadcastMessage("§6§lData §8§l» §c§lErreur lors de la lecture du minion #" + minion.getID());
                    toSendMinion.clear();
                } finally {
                    if (toSendMinion.size() > 0) {
                        toRemoveMinions.put(minion.getID() + "", null);
                        toSendMinions.putAll(toSendMinion);
                    }
                }
            }
            AsyncConfig.instance.setAndSaveAsyncBlockCurrentThread(toRemoveMinions, ConfigManager.instance.getDataMinions(),
                    ConfigManager.instance.minionsFile);
            AsyncConfig.instance.setAndSaveAsync(toSendMinions, ConfigManager.instance.getDataMinions(),
                    ConfigManager.instance.minionsFile);


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
                    toSendIsland.put(island.getId() + ".upgradeSizeSize", island.getSizeUpgrade().getSize());
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
                    for (IslandChallenge islandChallenge : island.getChallenges()) {
                        toSendIsland.put(island.getId() + ".c." + islandChallenge.getId() + ".prog", islandChallenge.getProgress());
                        toSendIsland.put(island.getId() + ".c." + islandChallenge.getId() + ".pal", islandChallenge.getPalier());
                        toSendIsland.put(island.getId() + ".c." + islandChallenge.getId() + ".act", islandChallenge.isActive());
                        toSendIsland.put(island.getId() + ".c." + islandChallenge.getId() + ".mat", islandChallenge.getMaterial().toString());
                        toSendIsland.put(island.getId() + ".c." + islandChallenge.getId() + ".max", islandChallenge.getMaxProgress());
                        toSendIsland.put(island.getId() + ".c." + islandChallenge.getId() + ".name", islandChallenge.getName());
                        toSendIsland.put(island.getId() + ".c." + islandChallenge.getId() + ".type", islandChallenge.getType());
                        if (islandChallenge.getToGet() != null) {
                            toSendIsland.put(island.getId() + ".c." + islandChallenge.getId() + ".mats", islandChallenge.getToGet().toString().
                                    replace("[", "").replace("]", "").replace(" ", ""));
                        }
                    }
                    for (Map.Entry<IslandRanks, ArrayList<IslandPerms>> entry : island.getMapPerms().entrySet()) {
                        toSendIsland.put(island.getId() + ".perm." + entry.getKey().toString(), entry.getValue().toString()
                                .replace("[", "").replace("]", "").
                                replace(" ", ""));
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


            ArrayList<Chest> chests = ChestManager.instance.chests;
            HashMap<String, Object> toSendChests = new HashMap<>();
            HashMap<String, Object> toRemoveChests = new HashMap<>();
            for (Chest chest : chests) {
                HashMap<String, Object> toSendChest = new HashMap<>();
                try {
                    toSendChest.put(chest.getId() + ".loc", chest.getBlock());
                    toSendChest.put(chest.getId() + ".uuid", chest.getOwner().toString());
                    toSendChest.put(chest.getId() + ".type", chest.getType());
                    toSendChest.put(chest.getId() + ".isSell", chest.isSell());
                    toSendChest.put(chest.getId() + ".active", chest.isActiveSellOrBuy());
                    toSendChest.put(chest.getId() + ".price", chest.getPrice());
                    toSendChest.put(chest.getId() + ".item", chest.getItemToBuySell());
                    toSendChest.put(chest.getId() + ".chunk", chest.getChunkKey());
                } catch (Exception e) {
                    e.printStackTrace();
                    Bukkit.broadcastMessage("§6§lData §8§l» §cUne erreur est survenue lors de la sauvegarde du coffre #" + chest.getId());
                    toSendChest.clear();
                } finally {
                    if (toSendChest.size() > 0) {
                        toRemoveChests.put(chest.getId() + "", null);
                        toSendChests.putAll(toSendChest);
                    }
                }
            }
            AsyncConfig.instance.setAndSaveAsyncBlockCurrentThread(toRemoveChests, ConfigManager.instance.getDataChests(),
                    ConfigManager.instance.chestsFile);
            AsyncConfig.instance.setAndSaveAsync(toSendChests, ConfigManager.instance.getDataChests(),
                    ConfigManager.instance.chestsFile);


            Bukkit.broadcastMessage("§6§lData §8§l» §fMise à jour complète de la database en " + (System.currentTimeMillis()
                    - start) + "ms.");

            if (force) {
                return true;
            }
            Bukkit.getScheduler().scheduleAsyncDelayedTask(Main.instance, new Runnable() {
                public void run() {
                    sendDataToAPIAuto(false);
                }
            }, 20 * 60 * 10);
            return true;
        }).join(); //makes it blocking
    }
}
