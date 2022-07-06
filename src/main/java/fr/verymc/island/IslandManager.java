package main.java.fr.verymc.island;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.*;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.session.ClipboardHolder;
import main.java.fr.verymc.Main;
import main.java.fr.verymc.commons.enums.ServerType;
import main.java.fr.verymc.commons.utils.HTTPUtils;
import main.java.fr.verymc.core.cmd.base.SpawnCmd;
import main.java.fr.verymc.core.holos.HoloBlocManager;
import main.java.fr.verymc.core.storage.AsyncConfig;
import main.java.fr.verymc.core.storage.ConfigManager;
import main.java.fr.verymc.island.bank.IslandBank;
import main.java.fr.verymc.island.blocks.Chest;
import main.java.fr.verymc.island.blocks.ChestManager;
import main.java.fr.verymc.island.challenges.IslandChallenge;
import main.java.fr.verymc.island.challenges.IslandChallengesReset;
import main.java.fr.verymc.island.guis.*;
import main.java.fr.verymc.island.minions.Minion;
import main.java.fr.verymc.island.minions.MinionManager;
import main.java.fr.verymc.island.perms.IslandPerms;
import main.java.fr.verymc.island.perms.IslandRank;
import main.java.fr.verymc.island.perms.IslandRanks;
import main.java.fr.verymc.island.upgrade.IslandUpgradeGenerator;
import main.java.fr.verymc.island.upgrade.IslandUpgradeMember;
import main.java.fr.verymc.island.upgrade.IslandUpgradeSize;
import main.java.fr.verymc.utils.PlayerUtils;
import main.java.fr.verymc.utils.WorldBorderUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class IslandManager {

    public static IslandManager instance;
    public static int distanceBetweenIslands = 400;
    public IslandBlocsValues islandBockValues;
    public ArrayList<Island> islands = new ArrayList<>();
    public ArrayList<UUID> bypasser = new ArrayList<>();
    public ArrayList<UUID> spying = new ArrayList<>();
    public File fileSchematic;
    public File fileEmptyIsland;
    public HashMap<Player, ArrayList<Player>> pendingInvites = new HashMap<>();

    public IslandManager() {
        instance = this;
        new IslandChallengesReset();
    }

    public void load() {
        for (File file : Main.instance.getDataFolder().listFiles()) {
            if (file.getName().endsWith("world.schem")) {
                fileSchematic = file;
            }
            if (file.getName().endsWith("clear.schem")) {
                fileEmptyIsland = file;
            }
        }
        new IslandRank();
        new IslandMainGui();
        new IslandMemberGui();
        new IslandUpgradeGui();
        new IslandBankGui();
        new IslandBorderGui();
        new IslandTopGui();
        new IslandRankEditGui();
        new IslandCoopGui();
        new IslandSettingsGui();
        new IslandBlocsValueGui();
        new IslandConfirmationGui();
        LinkedHashMap<Material, Double> blocks = new LinkedHashMap<>();
        blocks.put(Material.IRON_BLOCK, 10.0);
        blocks.put(Material.GOLD_BLOCK, 20.0);
        blocks.put(Material.DIAMOND_BLOCK, 50.0);
        blocks.put(Material.EMERALD_BLOCK, 100.0);
        blocks.put(Material.NETHERITE_BLOCK, 250.0);
        islandBockValues = new IslandBlocsValues(blocks);
        new IslandValueCalcManager();
        Main.instance.saveResource("ileworld.schem", true);
        Main.instance.saveResource("clear.schem", true);
        new WorldBorderUtil(Main.instance);

        new HoloBlocManager();
    }

    public void pasteAndLoadIslands() {
        CompletableFuture.runAsync(() -> {
            ArrayList<String> getted = HTTPUtils.readFromUrl("islands/loaded");
            ArrayList<String> loadeds = new ArrayList<>();
            for (Island island : islands) {
                if (getted.contains(island.getId())) {
                    island.setLoadedHere(false);
                    continue;
                }
                for (File file : Main.instance.getDataFolder().listFiles()) {
                    if (file.getName().contains(island.getId() + ".schem")) {
                        pasteIsland(file, island.getCenter().clone().add(250,
                                0, 250));
                        island.setLoadedHere(true);
                        loadeds.add(String.valueOf(island.getId()));
                        break;
                    }
                }
            }
            try {
                HTTPUtils.postMethod("islands/setloaded", loadeds.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void saveAllIslands() {
        for (Island island : islands) {
            System.out.println(island.isLoadedHere());
            if (island.isLoadedHere()) {
                Location pos1 = island.getCenter().clone().add(250, 0, 250);
                pos1.set(pos1.getBlockX(), 0, pos1.getBlockZ());
                Location pos2 = island.getCenter().clone().add(-250, 0, -250);
                pos2.set(pos2.getBlockX(), 256, pos2.getBlockZ());
                saveSchem(String.valueOf(island.getId()), pos1,
                        pos2, island.getCenter().getWorld(), island.getCenter().clone());
            }
        }
    }

    public ArrayList<UUID> getUUIDs() {
        ArrayList<UUID> toReturn = new ArrayList<>();
        for (Island island : islands) {
            for (Map.Entry<UUID, IslandRanks> entry : island.getMembers().entrySet()) {
                toReturn.add(entry.getKey());
            }
        }
        return toReturn;
    }

    public boolean isAnIslandByLoc(Location loc) {
        for (Island i : islands) {
            if (i.getHome().getBlockX() + i.getSizeUpgrade().getSize() >= loc.getBlockX() && i.getHome().getBlockX() - i.getSizeUpgrade().getSize() <= loc.getBlockX()
                    && i.getHome().getBlockZ() + i.getSizeUpgrade().getSize() >= loc.getBlockZ() && i.getHome().getBlockZ() - i.getSizeUpgrade().getSize() <= loc.getBlockZ()) {
                return true;
            }
        }
        return false;
    }

    public Island getIslandByLoc(Location loc) {
        for (Island i : islands) {
            if (i.getHome().getBlockX() + i.getSizeUpgrade().getSize() >= loc.getBlockX() && i.getHome().getBlockX() - i.getSizeUpgrade().getSize() <= loc.getBlockX()
                    && i.getHome().getBlockZ() + i.getSizeUpgrade().getSize() >= loc.getBlockZ() && i.getHome().getBlockZ() - i.getSizeUpgrade().getSize() <= loc.getBlockZ()) {
                return i;
            }
        }
        return null;
    }

    public void setWorldBorderForAllPlayerOnIsland(Island island) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (getIslandByLoc(p.getLocation()) == island) {
                setWorldBorder(p);
            }
        }
    }

    public boolean isBypassing(UUID uuid) {
        return bypasser.contains(uuid);
    }

    public void addBypassing(UUID uuid) {
        bypasser.add(uuid);
    }

    public void removeBypassing(UUID uuid) {
        bypasser.remove(uuid);
    }

    public boolean isSpying(UUID uuid) {
        return spying.contains(uuid);
    }

    public void addSpying(UUID uuid) {
        spying.add(uuid);
    }

    public void removeSpying(UUID uuid) {
        spying.remove(uuid);
    }

    public void setWorldBorder(Player p) {
        if (Main.instance.serverType != ServerType.ISLAND) return;
        Island i = getIslandByLoc(p.getLocation());
        if (i != null) {
            WorldBorderUtil.instanceClass.sendWorldBorder(p, i.getBorderColor(),
                    i.getSizeUpgrade().getSize(), i.getCenter());
        }
    }

    public void setWorldBorder(Player p, Location loc) {
        if (Main.instance.serverType != ServerType.ISLAND) return;
        Island i = getIslandByLoc(loc);
        if (i != null) {
            WorldBorderUtil.instanceClass.sendWorldBorder(p, i.getBorderColor(),
                    i.getSizeUpgrade().getSize(), i.getCenter());
        }
    }

    public void teleportPlayerToIslandSafe(Player p) {
        for (Island i : islands) {
            if (i.getMembers().containsKey(p.getUniqueId())) {
                p.teleport(i.getHome());
                p.sendMessage("§6§lIles §8» §fTéléportation sur votre île.");
                IslandManager.instance.setWorldBorder(p);
                return;
            }
        }
    }

    public void setIslandNewOwner(Player p) {
        Island playerIsland = getIslandByLoc(p.getLocation());
        String oldOwner = Bukkit.getOfflinePlayer(playerIsland.getOwnerUUID()).getName();
        playerIsland.getMembers().put(playerIsland.getOwnerUUID(), IslandRanks.COCHEF);
        playerIsland.getMembers().put(p.getUniqueId(), IslandRanks.CHEF);
        playerIsland.sendMessageToEveryMember("§6§lIles §8» §f" + p.getName() + " vient de devenir le chef de l'île, transféré par " +
                oldOwner + ".");
    }

    public boolean isOwner(Player p) {
        for (Island i : islands) {
            if (i.getOwnerUUID().equals(p.getUniqueId())) {
                return true;
            }
        }
        return false;
    }

    public void deleteIsland(Player p) {
        Long start = System.currentTimeMillis();
        Island playerIsland = getPlayerIsland(p);

        playerIsland.sendMessageToEveryMember("§6§lIles §8» §4L'île a commencé à être supprimée...");

        Bukkit.getScheduler().scheduleAsyncDelayedTask(Main.instance, new Runnable() {
            @Override
            public void run() {
                try {
                    com.sk89q.worldedit.world.World adaptedWorld = BukkitAdapter.adapt(Main.instance.mainWorld);
                    ClipboardFormat format = ClipboardFormats.findByFile(fileEmptyIsland);
                    ClipboardReader reader = format.getReader(new FileInputStream(fileEmptyIsland));

                    Clipboard clipboard = reader.read();
                    EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(adaptedWorld,
                            -1);

                    Operation operation = new ClipboardHolder(clipboard).createPaste(editSession)
                            .to(BlockVector3.at(playerIsland.getCenter().getBlockX(), playerIsland.getCenter().getBlockY(),
                                    playerIsland.getCenter().getBlockZ())).build();

                    try {
                        Operations.complete(operation);
                        editSession.flushSession();

                        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
                            @Override
                            public void run() {
                                for (Minion m : playerIsland.getMinions()) {
                                    if (getIslandByLoc(m.getBlocLocation()) == playerIsland) {
                                        MinionManager.instance.removeMinion(m, playerIsland);
                                    }
                                }
                                ArrayList<Chest> chests = new ArrayList<>();
                                for (Chest c : playerIsland.getChests()) {
                                    chests.add(c);
                                }
                                for (Chest c : chests) {
                                    if (getIslandByLoc(c.getBlock()) == playerIsland) {
                                        ChestManager.instance.removeChestFromLoc(c.getBlock());
                                    }
                                }
                                if (islands.contains(playerIsland)) {
                                    islands.remove(playerIsland);
                                }
                                Long currentMills = System.currentTimeMillis();
                                playerIsland.sendMessageToEveryMember("§6§lIles §8» §4L'île a été §lsupprimée §4par le chef. §f(en " + (currentMills - start) + "ms)");
                                for (Map.Entry<UUID, IslandRanks> entry : playerIsland.getMembers().entrySet()) {
                                    Player member = Bukkit.getPlayer(entry.getKey());
                                    if (member == null) {
                                        member = Bukkit.getOfflinePlayer(entry.getKey()).getPlayer();
                                    }
                                    PlayerUtils.instance.teleportPlayerFromRequest(member, SpawnCmd.Spawn, 0, ServerType.HUB);
                                }
                                playerIsland.getMembers().clear();
                                HashMap<String, Object> toEdit = new HashMap<>();
                                toEdit.put(playerIsland.getId() + "", null);
                                AsyncConfig.instance.setAndSaveAsync(toEdit, ConfigManager.instance.getDataIslands(), ConfigManager.instance.islandsFile);
                            }
                        }, 0);

                    } catch (WorldEditException e) {
                        p.sendMessage("§6§lIles §8» §cUne erreur est survenue lors de la suppression de l'île. Merci de réessayer.");
                        return;
                    }
                } catch (IOException e) {
                    p.sendMessage("§6§lIles §8» §cUne erreur est survenue lors de la lecture du schématic. Merci de contacter un administrateur.");
                    return;
                }
            }
        }, 0);
    }

    public void leaveIsland(Player p) {
        for (Island i : islands) {
            if (i.getMembers().containsKey(p.getUniqueId())) {
                i.sendMessageToEveryMember("§6§lIles §8» §f" + p.getName() + " a quitté l'île, il était " +
                        i.getMembers().get(p.getUniqueId()).name() + ".");
                i.getMembers().remove(p.getUniqueId());
                p.sendMessage("§6§lIles §8» §fVous avez quitté l'île.");
                PlayerUtils.instance.teleportPlayerFromRequest(p, SpawnCmd.Spawn, 0, ServerType.HUB);
                break;
            }
        }
    }

    public Island getPlayerIsland(Player p) {
        for (Island i : islands) {
            if (i.getMembers().containsKey(p.getUniqueId())) {
                return i;
            }
        }
        return null;
    }

    public Island getIslandFromUUID(UUID id) {
        for (Island i : islands) {
            if (i.getMembers().containsKey(id)) {
                return i;
            }
        }
        return null;
    }

    public boolean acceptInvite(Player p, Player target) {
        if (pendingInvites.containsKey(p)) {
            if (pendingInvites.get(p).contains(target)) {
                pendingInvites.get(p).remove(target);
                if (pendingInvites.get(p).isEmpty()) {
                    pendingInvites.remove(p);
                }
                target.sendMessage("§6§lIles §8» §fVous avez rejoint l'île de " + p.getName() + ".");
                getPlayerIsland(p).addMembers(target.getUniqueId(), IslandRanks.MEMBRE);
                IslandManager.instance.getPlayerIsland(p).sendMessageToEveryMember("§6§lIles §8» §6" + target.getName() +
                        "§f a rejoint l'île par l'invitation de §f" + p.getName() + "§f.");
                teleportPlayerToIslandSafe(target);
                return true;
            }
        }
        return false;
    }

    public boolean promoteAndDemoteAction(Player player, UUID targetUUID, String playerName,
                                          ClickType clickType, Island currentIsland) {
        IslandRanks old = currentIsland.getMembers().get(targetUUID);
        if (IslandManager.instance.isOwner(player)) {
            if (clickType.isLeftClick()) {
                if (currentIsland.promote(targetUUID)) {
                    currentIsland.sendMessageToEveryMember("§6§lIles §8» §f" + player.getName() + " a §apromu§f " +
                            playerName + " au grade §6" + currentIsland.getMembers().get(targetUUID).name() + "§f.");
                    IslandMemberGui.instance.openMemberIslandMenu(player);
                    return true;
                }
            }
            if (clickType.isRightClick()) {
                if (currentIsland.demote(targetUUID)) {
                    currentIsland.sendMessageToEveryMember("§6§lIles §8» §f" + player.getName() + " a §crétrogradé§f " +
                            playerName + " au grade §6" + currentIsland.getMembers().get(targetUUID).name() + "§f.");
                    IslandMemberGui.instance.openMemberIslandMenu(player);
                    return true;
                }
            }
            if (clickType == ClickType.MIDDLE) {
                if (currentIsland.kickFromIsland(targetUUID)) {
                    currentIsland.sendMessageToEveryMember("§6§lIles §8» §f" + player.getName() + " a §4exclu§f " +
                            playerName + " de l'île, il avait le grade §6" + old.name() + "§f.");
                    IslandMemberGui.instance.openMemberIslandMenu(player);
                    if (Bukkit.getPlayer(targetUUID) != null) {
                        Bukkit.getPlayer(targetUUID).sendMessage("§6§lIles §8» §fVous avez été exclu de l'île.");
                    }
                    return true;
                }
            }
            return false;
        }
        if (currentIsland.getPerms(currentIsland.getIslandRankFromUUID(player.getUniqueId())).contains(IslandPerms.PROMOTE)) {
            if (clickType.isLeftClick()) {
                if (IslandRank.isUp(currentIsland.getIslandRankFromUUID(player.getUniqueId()), currentIsland.getIslandRankFromUUID(targetUUID))) {
                    if (currentIsland.promote(targetUUID)) {
                        currentIsland.sendMessageToEveryMember("§6§lIles §8» §f" + currentIsland.getMembers().get(targetUUID).name() + " a §apromu§f " +
                                playerName + " au grade §6" + old.name() + "§f.");
                        IslandMemberGui.instance.openMemberIslandMenu(player);
                        return true;
                    }
                }
            }
        }
        if (currentIsland.getPerms(currentIsland.getIslandRankFromUUID(player.getUniqueId())).contains(IslandPerms.DEMOTE)) {
            if (clickType.isRightClick()) {
                if (IslandRank.isUp(currentIsland.getIslandRankFromUUID(player.getUniqueId()), currentIsland.getIslandRankFromUUID(targetUUID))) {
                    if (currentIsland.demote(targetUUID)) {
                        currentIsland.sendMessageToEveryMember("§6§lIles §8» §f" + player.getName() + " a §crétrogradé§f " +
                                playerName + " au grade §6" + currentIsland.getMembers().get(targetUUID).name() + "§f.");
                        IslandMemberGui.instance.openMemberIslandMenu(player);
                        return true;
                    }
                }
            }
        }
        if (currentIsland.getPerms(currentIsland.getIslandRankFromUUID(player.getUniqueId())).contains(IslandPerms.KICK)) {
            if (clickType == ClickType.MIDDLE) {
                if (IslandRank.isUp(currentIsland.getIslandRankFromUUID(player.getUniqueId()), currentIsland.getIslandRankFromUUID(targetUUID))) {
                    if (currentIsland.kickFromIsland(targetUUID)) {
                        currentIsland.sendMessageToEveryMember("§6§lIles §8» §f" + player.getName() + " a §4exclu§f " +
                                playerName + " de l'île, il avait le grade §6" + old.name() + "§f.");
                        IslandMemberGui.instance.openMemberIslandMenu(player);
                        if (Bukkit.getPlayer(targetUUID) != null) {
                            Bukkit.getPlayer(targetUUID).sendMessage("§6§lIles §8» §fVous avez été exclu de l'île.");
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void cancelInvite(Player player, Player target) {
        Island currentIsland = IslandManager.instance.getPlayerIsland(player);
        if (pendingInvites.containsKey(target)) {
            if (pendingInvites.get(target) != null) {
                for (Player p : pendingInvites.get(target)) {
                    if (p.isOnline()) {
                        p.sendMessage("§6§lIles §8» §f" + player.getName() + " §7a annulé votre invitation.");
                    }
                }
                pendingInvites.get(target).clear();
                target.sendMessage("§6§lIles §8» §f" + player.getName() + " a annulé vos invitations.");
                currentIsland.sendMessageToEveryMember("§6§lIles §8» §f" + player.getName() + " a annulé l'/les invitations de " + target.getName() + ".");
            }
        }

    }

    public boolean invitePlayer(Player p, Player target) {
        Island currentIsland = getPlayerIsland(p);
        if (currentIsland.hasPerms(currentIsland.getIslandRankFromUUID(p.getUniqueId()), IslandPerms.INVITE, p)) {
            ArrayList<Player> pending;
            if (pendingInvites.containsKey(p)) {
                pending = pendingInvites.get(p);
                if (pending.contains(target)) {
                    return false;
                }
            } else {
                pending = new ArrayList<>();
            }
            pending.add(target);
            pendingInvites.put(p, pending);
            target.sendMessage("§6§lIles §8» §fVous avez été invité à rejoindre l'île de §6" + p.getName() + "§f. Faites /is accept " +
                    p.getName() + " pour accepter.");
            IslandManager.instance.getPlayerIsland(p).sendMessageToEveryMember("§6§lIles §8» §f" + target.getName() +
                    " a été invité à rejoindre l'île par §6" + p.getName() + "§f.");
            Bukkit.getScheduler().scheduleAsyncDelayedTask(Main.instance, new Runnable() {
                @Override
                public void run() {
                    Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
                        @Override
                        public void run() {
                            if (pendingInvites.containsKey(p)) {
                                if (pendingInvites.get(p).contains(target)) {
                                    pendingInvites.get(p).remove(target);
                                    target.sendMessage("§6§lIles §8» §fL'invitation de " + p.getName() + " a expiré.");
                                    p.sendMessage("§6§lIles §8» §fL'invitation pour " + target.getName() + " a expiré.");
                                }
                            }
                        }
                    }, 0);
                }
            }, 20 * 60);
            return true;
        }
        return false;
    }

    public boolean asAnIsland(Player p) {
        if (getPlayerIsland(p) == null) {
            return false;
        }
        return true;
    }

    public World getMainWorld() {
        return Main.instance.mainWorld;
    }

    public void genIsland(Player p) {
        Location toReturn = null;
        int id = 0;
        p.sendMessage("§6§lIles §8» §aGénération de l'île en cours...");
        Long start = System.currentTimeMillis();

        if (islands.size() == 0) {
            toReturn = new Location(Main.instance.mainWorld, 0, 80, 0);
        } else {
            int minx = 0;
            int minz = 0;
            int maxx = 0;
            int maxz = 0;
            for (Island i : islands) {
                if (minx > i.getCenter().getBlockX()) {
                    minx = i.getCenter().getBlockX();
                }
                if (minz > i.getCenter().getBlockZ()) {
                    minz = i.getCenter().getBlockZ();
                }
                if (maxx < i.getCenter().getBlockX()) {
                    maxx = i.getCenter().getBlockX();
                }
                if (maxz < i.getCenter().getBlockZ()) {
                    maxz = i.getCenter().getBlockZ();
                }
                if (i.getId() >= id) {
                    id = i.getId();
                }
            }
            Random rand = new Random();
            while (toReturn == null) {
                for (int i = minz; i <= maxz; i += distanceBetweenIslands) {
                    if (toReturn != null) break;
                    if (!isAnIslandByLoc(new Location(Main.instance.mainWorld, minx, 0, i))) {
                        toReturn = new Location(Main.instance.mainWorld, minx, 80, i);
                    }
                    for (int j = minx; j <= maxx; j += distanceBetweenIslands) {
                        if (!isAnIslandByLoc(new Location(Main.instance.mainWorld, j, 0, i))) {
                            toReturn = new Location(Main.instance.mainWorld, j, 80, i);
                        }
                    }
                }
                if (toReturn != null) {
                    break;
                }
                int toSumx = 0;
                int toSumz = 0;
                int randint = rand.nextInt(4);
                if (randint == 0) {
                    toSumx = distanceBetweenIslands;
                } else if (randint == 1) {
                    toSumx = -distanceBetweenIslands;
                } else if (randint == 2) {
                    toSumz = distanceBetweenIslands;
                } else if (randint == 3) {
                    toSumz = -distanceBetweenIslands;
                }
                Location tmp = new Location(Main.instance.mainWorld, maxx + toSumx, 80, minz + toSumz);
                if (!isAnIslandByLoc(tmp)) {
                    toReturn = tmp;
                    break;
                }
            }

        }

        toReturn.setWorld(getMainWorld());

        Location finalToReturn = toReturn;
        Location finalToReturn1 = toReturn;
        int finalId = id;

        pasteIsland(fileSchematic, finalToReturn);

        HashMap<UUID, IslandRanks> members = new HashMap<>();
        members.put(p.getUniqueId(), IslandRanks.CHEF);
        IslandBank islandBank = new IslandBank(0, 0, 0);
        IslandUpgradeSize islandUpgradeSize = new IslandUpgradeSize(50, 0);
        IslandUpgradeMember islandUpgradeMember = new IslandUpgradeMember(0);
        IslandUpgradeGenerator islandUpgradeGenerator = new IslandUpgradeGenerator(0);
        ArrayList<UUID> banneds = new ArrayList<>();
        ArrayList<IslandChallenge> challenges = new ArrayList<>();
        Location home = finalToReturn1.clone();
        home.add(0.5, 0.1, 0.5);
        home.setPitch(0);
        home.setYaw(130);
        islands.add(new Island("Ile de " + p.getName(), home, finalToReturn1, finalId + 1, members,
                islandUpgradeSize, islandUpgradeMember, WorldBorderUtil.Color.BLUE, islandBank, islandUpgradeGenerator, banneds, challenges,
                true, null, true, 0.0, null, null, null, true));
        new BukkitRunnable() {
            @Override
            public void run() {
                if (home.clone().add(0, -2, 0).getBlock().getType() != Material.AIR) {
                    p.sendMessage("§6§lIles §8» §aVous avez généré une nouvelle île avec succès (en " + (System.currentTimeMillis() - start) + "ms).");
                    teleportPlayerToIslandSafe(p);
                    this.cancel();
                }
            }
        }.runTaskTimer(Main.instance, 0, 5L);

    }

    public void pasteIsland(File file, Location pos) {

        CompletableFuture.runAsync(() -> {
            try {
                com.sk89q.worldedit.world.World weWorld = BukkitAdapter.adapt(pos.getWorld());
                ClipboardFormat format = ClipboardFormats.findByFile(file);
                ClipboardReader reader = format.getReader(new FileInputStream(file));

                Clipboard clipboard = reader.read();
                EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(weWorld,
                        -1);

                Operation operation = new ClipboardHolder(clipboard).createPaste(editSession)
                        .to(BlockVector3.at(pos.getX(), pos.getY(), pos.getZ())).ignoreAirBlocks(true).copyBiomes(true).build();


                Operations.complete(operation);
                editSession.flushSession();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /*IslandManager.instance.saveSchem("aaaa", island.getCenter().clone().add(50, -15, 50),
                        island.getCenter().clone().add(-50, 40, -50), island.getCenter().getWorld(), island.getCenter().clone());*/

    public void saveSchem(String filename, Location loc1, Location loc2, World world, Location center) {
        try {
            com.sk89q.worldedit.world.World weWorld = BukkitAdapter.adapt(world);
            BlockVector3 pos1 = BlockVector3.at(loc1.getBlockX(), loc1.getBlockY(), loc1.getBlockZ());
            BlockVector3 pos2 = BlockVector3.at(loc2.getBlockX(), loc2.getBlockY(), loc2.getBlockZ());
            Region cReg = new CuboidRegion(weWorld, pos2, pos1);
            File file = new File(Main.instance.getDataFolder(), filename + ".schem");
            Clipboard clipboard = Clipboard.create(cReg);
            clipboard.setOrigin(BlockVector3.at(center.getX(), center.getY(), center.getZ()));

            try (ClipboardWriter writer = BuiltInClipboardFormat.FAST.getWriter(new FileOutputStream(file))) {
                writer.write(clipboard);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
