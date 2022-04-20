package main.java.fr.verymc.storage;

import main.java.fr.verymc.Main;
import main.java.fr.verymc.blocks.Chest;
import main.java.fr.verymc.island.Island;
import main.java.fr.verymc.island.IslandManager;
import main.java.fr.verymc.island.bank.IslandBank;
import main.java.fr.verymc.island.perms.IslandRanks;
import main.java.fr.verymc.island.upgrade.IslandUpgradeGenerator;
import main.java.fr.verymc.island.upgrade.IslandUpgradeMember;
import main.java.fr.verymc.island.upgrade.IslandUpgradeSize;
import main.java.fr.verymc.minions.Minion;
import main.java.fr.verymc.minions.MinionManager;
import main.java.fr.verymc.utils.WorldBorderUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class StorageYAMLManager {

    public static StorageYAMLManager instance;
    ArrayList<Object> toSave = new ArrayList<>();

    public StorageYAMLManager() {
        instance = this;
        getDataFromAPI();
        sendDataToAPIAuto();
    }

    public void getDataFromAPI() {

        Bukkit.getScheduler().scheduleAsyncDelayedTask(Main.instance, new Runnable() {
            public void run() {

                //API FETCH DATA

                ArrayList<Minion> minions = new ArrayList<>();
                //DATA MINIONS

                ArrayList<Island> islands = new ArrayList<>();
                for (String str : ConfigManager.instance.getDataIslands().getKeys(false)) {
                    String name = ConfigManager.instance.getDataIslands().getString(str + ".name");
                    Location home = ConfigManager.instance.getDataIslands().getLocation(str + ".home");
                    Location center = ConfigManager.instance.getDataIslands().getLocation(str + ".center");
                    int id = ConfigManager.instance.getDataIslands().getInt(str);

                    HashMap<UUID, IslandRanks> members = new HashMap<>();
                    String parts[] = ConfigManager.instance.getDataIslands().getString(str + ".players")
                            .replace("{", "").replace("}", "").split(",");
                    for (String part : parts) {
                        String stuData[] = part.split("=");
                        members.put(UUID.fromString(stuData[0]), IslandRanks.valueOf(stuData[1]));
                    }

                    IslandUpgradeSize islandUpgradeSize = new IslandUpgradeSize(
                            ConfigManager.instance.getDataIslands().getInt(str + ".upgradeSizeSize"),
                            ConfigManager.instance.getDataIslands().getInt(str + ".upgradeSizePrice"));

                    IslandUpgradeMember islandUpgradeMember = new IslandUpgradeMember(
                            ConfigManager.instance.getDataIslands().getInt(str + ".upgradeMemberLevel"));

                    WorldBorderUtil.Color color = WorldBorderUtil.Color.valueOf(ConfigManager.instance.getDataIslands().
                            getString(str + ".borderColor"));

                    IslandBank islandBank = new IslandBank(
                            ConfigManager.instance.getDataIslands().getInt(str + ".bank.money"),
                            ConfigManager.instance.getDataIslands().getInt(str + ".bank.crystaux"),
                            ConfigManager.instance.getDataIslands().getInt(str + ".bank.xp"));

                    double value = ConfigManager.instance.getDataIslands().getDouble(str + ".value");

                    boolean isPublic = ConfigManager.instance.getDataIslands().getBoolean(str + ".isPublic");

                    IslandUpgradeGenerator islandUpgradeGenerator = new IslandUpgradeGenerator(
                            ConfigManager.instance.getDataIslands().getInt(str + ".upgradeGeneratorLevel"));

                    ArrayList<UUID> banneds = new ArrayList<>();
                    for (String par : ConfigManager.instance.getDataIslands().getString(str + ".banneds").split(",")) {
                        banneds.add(UUID.fromString(par));
                    }
                    Bukkit.broadcastMessage(banneds.toString());


                }
                //DATA ISLANDS

                ArrayList<SkyblockUser> skyblockUsers = new ArrayList<>();
                for (String str : ConfigManager.instance.getDataSkyblockUser().getKeys(false)) {
                    UUID uuid = UUID.fromString(str);
                    double money = ConfigManager.instance.getDataSkyblockUser().getDouble(str + ".money");
                    int flyLeft = ConfigManager.instance.getDataSkyblockUser().getInt(str + ".flyLeft");
                    boolean hasHaste = ConfigManager.instance.getDataSkyblockUser().getBoolean(str + ".hasHaste");
                    boolean hasSpeed = ConfigManager.instance.getDataSkyblockUser().getBoolean(str + ".hasSpeed");
                    boolean hasJump = ConfigManager.instance.getDataSkyblockUser().getBoolean(str + ".hasJump");
                    skyblockUsers.add(new SkyblockUser(Bukkit.getOfflinePlayer(uuid).getName(), uuid, money,
                            hasHaste, hasHaste, hasSpeed, hasSpeed, hasJump, hasJump, flyLeft, false,
                            false, 0));
                }
                //DATA USERS

                ArrayList<Chest> chests = new ArrayList<>();
                //DATA CHESTS

                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
                    public void run() {

                        //SET NEW VALUE WITHOUT THREAD CONFLICTS
                        //CurrentThreadModificationException can happen here ?????

                        //SEND Minions to -> MinionManager.instance.minions

                        //SEND Islands to -> IslandManager.instance.islands

                        //SEND SkyblockUser to -> SkyblockUserManager.instance.users

                        SkyblockUserManager.instance.users = skyblockUsers;

                        //SEND Chests to -> ChestManager.instance.chests

                    }
                }, 0);
            }
        }, 0);
    }

    public void sendDataToAPIAuto() {
        Bukkit.getScheduler().scheduleAsyncDelayedTask(Main.instance, new Runnable() {
            public void run() {


                // API SEND DATA

                //CLEAR ALL DATAS WHO ARE NOT STORED IN THE PLUGIN THAT ARE IN THE API


                ArrayList<Minion> minions = MinionManager.instance.minions;
                //SEND TO API

                HashMap<String, Object> toSendIsland = new HashMap<>();
                ArrayList<Island> islands = IslandManager.instance.islands;
                for (Island island : islands) {
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
                }
                AsyncConfig.instance.setAndSaveAsync(toSendIsland, ConfigManager.instance.getDataIslands(),
                        ConfigManager.instance.islandsFile);


                //SEND TO API
                ArrayList<SkyblockUser> skyblockUsers = SkyblockUserManager.instance.users;
                //SEND TO API

                HashMap<String, Object> toSendSkyUser = new HashMap<>();
                for (SkyblockUser skyblockUser : skyblockUsers) {
                    final String uuid = skyblockUser.getUserUUID().toString();
                    toSendSkyUser.put(uuid + ".flyLeft", skyblockUser.getFlyLeft());
                    toSendSkyUser.put(uuid + ".money", skyblockUser.getMoney());
                    toSendSkyUser.put(uuid + ".hasHaste", skyblockUser.hasHaste());
                    toSendSkyUser.put(uuid + ".hasSpeed", skyblockUser.hasSpeed());
                    toSendSkyUser.put(uuid + ".hasJump", skyblockUser.hasJump());
                }
                AsyncConfig.instance.setAndSaveAsync(toSendSkyUser, ConfigManager.instance.getDataSkyblockUser(),
                        ConfigManager.instance.skyblockUserFile);


                sendDataToAPIAuto();
            }
        }, 20 * 10);
    }
}
