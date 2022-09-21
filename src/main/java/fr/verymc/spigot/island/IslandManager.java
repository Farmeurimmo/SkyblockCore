package main.java.fr.verymc.spigot.island;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import main.java.fr.verymc.commons.enums.ServerType;
import main.java.fr.verymc.spigot.Main;
import main.java.fr.verymc.spigot.core.cmd.base.SpawnCmd;
import main.java.fr.verymc.spigot.core.spawners.Spawner;
import main.java.fr.verymc.spigot.core.spawners.SpawnersManager;
import main.java.fr.verymc.spigot.core.storage.AsyncConfig;
import main.java.fr.verymc.spigot.core.storage.ConfigManager;
import main.java.fr.verymc.spigot.core.storage.StorageManager;
import main.java.fr.verymc.spigot.island.bank.IslandBank;
import main.java.fr.verymc.spigot.island.blocks.Chest;
import main.java.fr.verymc.spigot.island.blocks.ChestManager;
import main.java.fr.verymc.spigot.island.challenges.IslandChallenge;
import main.java.fr.verymc.spigot.island.challenges.IslandChallengesListener;
import main.java.fr.verymc.spigot.island.challenges.IslandChallengesReset;
import main.java.fr.verymc.spigot.island.guis.*;
import main.java.fr.verymc.spigot.island.minions.Minion;
import main.java.fr.verymc.spigot.island.minions.MinionManager;
import main.java.fr.verymc.spigot.island.perms.IslandPerms;
import main.java.fr.verymc.spigot.island.perms.IslandRank;
import main.java.fr.verymc.spigot.island.perms.IslandRanks;
import main.java.fr.verymc.spigot.island.upgrade.IslandUpgradeGenerator;
import main.java.fr.verymc.spigot.island.upgrade.IslandUpgradeMember;
import main.java.fr.verymc.spigot.island.upgrade.IslandUpgradeSize;
import main.java.fr.verymc.spigot.utils.FAWEUtils;
import main.java.fr.verymc.spigot.utils.PlayerUtils;
import main.java.fr.verymc.spigot.utils.WorldBorderUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.FileInputStream;
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
    public ArrayList<IslandChallenge> challenges = new ArrayList<>();
    public File fileSchematic;
    public File fileEmptyIsland;
    public HashMap<Player, ArrayList<Player>> pendingInvites = new HashMap<>();
    public ArrayList<UUID> awaiting = new ArrayList<>();

    public IslandManager() {
        instance = this;
        challenges = getAvailableChallenges();
        new WorldBorderUtils(Main.instance);
        new IslandChallengesReset();
        new IslandRank();
    }

    public void addPlayerAwaiting(Player player) {
        awaiting.add(player.getUniqueId());
        player.setAllowFlight(true);
        player.setFlying(true);
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 10));
    }

    public void removePlayerAwaiting(Player player) {
        awaiting.remove(player.getUniqueId());
        player.setFlying(false);
        player.setAllowFlight(false);
        player.removePotionEffect(PotionEffectType.BLINDNESS);
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
        new IslandMainGui();
        new IslandMemberGui();
        new IslandUpgradeGui();
        new IslandBankGui();
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
    }

    public void pasteAndLoadIslands() {
        islands = StorageManager.instance.getIslands();
        for (Island island : islands) {
            if (island.getCenter() == null) {
                island.setCenter(getAFreeCenter());
            }
            for (File file : Objects.requireNonNull(Main.instance.getDataFolder().listFiles())) {
                System.out.println(file.getName());
                if (file.getName().contains(island.getUUID().toString())) {
                    FAWEUtils.instance.pasteSchemWithoutLockingThread(file, island.getCenter().clone().add(250,
                            0, 250));
                    island.setLoadedHere(true);
                    break;
                }
            }
        }
        ChestManager.instance.makeChestRepop();
        SpawnersManager.instance.respawnAllSpawners();
        MinionManager.instance.makeAllMinionRepop();
    }

    public void saveAllIslands() {
        MinionManager.instance.makeAllMinionDepop();
        for (Island island : islands) {
            if (island.isLoadedHere()) {
                Location pos1 = island.getCenter().clone().add(250, 0, 250);
                pos1.set(pos1.getBlockX(), -64, pos1.getBlockZ());
                Location pos2 = island.getCenter().clone().add(-250, 0, -250);
                pos2.set(pos2.getBlockX(), 319, pos2.getBlockZ());
                FAWEUtils.instance.saveSchem(String.valueOf(island.getUUID()), pos1,
                        pos2, island.getCenter().getWorld(), island.getCenter().clone());
            }
        }
    }

    public IslandChallenge getById(int id) {
        for (IslandChallenge islandChallenge : challenges) {
            if (islandChallenge.getId() == id) {
                return islandChallenge;
            }
        }
        return null;
    }

    public ArrayList<IslandChallenge> getAvailableChallenges() {
        ArrayList<IslandChallenge> toReturn = new ArrayList<>();
        int id = 0;
        toReturn.add(new IslandChallenge("Miner de la pierre", 0, Material.COBBLESTONE, 0, id, true, IslandChallengesListener.cobble));
        id++;
        toReturn.add(new IslandChallenge("Miner du charbon", 0, Material.COAL_ORE, 0, id, true, IslandChallengesListener.coal));
        id++;
        toReturn.add(new IslandChallenge("Miner du fer", 0, Material.IRON_ORE, 0, id, true, IslandChallengesListener.iron));
        id++;
        toReturn.add(new IslandChallenge("Miner de l'or", 0, Material.GOLD_ORE, 0, id, true, IslandChallengesListener.gold));
        id++;
        toReturn.add(new IslandChallenge("Miner du diamant", 0, Material.DIAMOND_ORE, 0, id, true, IslandChallengesListener.diamond));
        id++;
        toReturn.add(new IslandChallenge("Miner de l'émeraude", 0, Material.EMERALD_ORE, 0, id, true, IslandChallengesListener.emerald));
        id++;
        toReturn.add(new IslandChallenge("Miner de l'ancient débris", 0, Material.ANCIENT_DEBRIS, 0, id, true, IslandChallengesListener.debris));
        id++;
        toReturn.add(new IslandChallenge("Casser des bûches de chêne", 0, Material.OAK_LOG, 0, id, true, IslandChallengesListener.oak_log));
        id++;
        toReturn.add(new IslandChallenge("Casser des bûches de bouleau", 0, Material.BIRCH_LOG, 0, id, true, IslandChallengesListener.birch_log));
        id++;
        toReturn.add(new IslandChallenge("Casser des bûches d'acacia", 0, Material.ACACIA_LOG, 0, id, true, IslandChallengesListener.acacia_log));
        id++;
        toReturn.add(new IslandChallenge("Casser des bûches de chêne noir", 0, Material.DARK_OAK_LOG, 0, id, true, IslandChallengesListener.dark_oak_log));
        id++;
        toReturn.add(new IslandChallenge("Casser des bûches de jungle", 0, Material.JUNGLE_LOG, 0, id, true, IslandChallengesListener.jungle_log));
        id++;
        toReturn.add(new IslandChallenge("Casser des bûches de sapin", 0, Material.SPRUCE_LOG, 0, id, true, IslandChallengesListener.spruce_log));
        id++;
        toReturn.add(new IslandChallenge("Récolter du blé", 0, Material.WHEAT, 0, id, true, IslandChallengesListener.wheat));
        id++;
        toReturn.add(new IslandChallenge("Récolter du cacao", 0, Material.COCOA, 0, id, true, IslandChallengesListener.cocoa));
        id++;
        toReturn.add(new IslandChallenge("Récolter des patates", 0, Material.POTATOES, 0, id, true, IslandChallengesListener.potato));
        id++;
        toReturn.add(new IslandChallenge("Récolter des carottes", 0, Material.CARROTS, 0, id, true, IslandChallengesListener.carrot));
        id++;
        toReturn.add(new IslandChallenge("Récolter de la nether wart", 0, Material.NETHER_WART, 0, id, true, IslandChallengesListener.nether_wart));
        id++;
        toReturn.add(new IslandChallenge("Récolter du melon", 0, Material.MELON, 0, id, true, IslandChallengesListener.melon));
        id++;
        toReturn.add(new IslandChallenge("Récolter de la citrouille", 0, Material.PUMPKIN, 0, id,
                true, IslandChallengesListener.pumpkin));
        return toReturn;
    }

    public boolean isAnIslandByLoc(Location loc) {
        for (Island i : islands) {
            if (i.getCenter() == null) return false;
            final int size = IslandUpgradeSize.getSizeFromLevel(i.getSizeUpgrade().getLevel());
            if (i.getCenter().getBlockX() + size >= loc.getBlockX() && i.getCenter().getBlockX() - size <= loc.getBlockX()
                    && i.getCenter().getBlockZ() + size >= loc.getBlockZ() && i.getCenter().getBlockZ() - size <= loc.getBlockZ()) {
                return true;
            }
        }
        return false;
    }

    public Island getIslandByLoc(Location loc) {
        for (Island i : islands) {
            if (i.getCenter() == null) return null;
            final int size = IslandUpgradeSize.getSizeFromLevel(i.getSizeUpgrade().getLevel());
            if (i.getCenter().getBlockX() + size >= loc.getBlockX() && i.getCenter().getBlockX() - size <= loc.getBlockX()
                    && i.getCenter().getBlockZ() + size >= loc.getBlockZ() && i.getCenter().getBlockZ() - size <= loc.getBlockZ()) {
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
        if (Main.instance.serverType != ServerType.SKYBLOCK_ISLAND) return;
        Island i = getIslandByLoc(p.getLocation());
        if (i != null) {
            WorldBorderUtils.instanceClass.sendWorldBorder(p,
                    IslandUpgradeSize.getSizeFromLevel(i.getSizeUpgrade().getLevel()), i.getCenter());
        }
    }

    public void setWorldBorder(Player p, Location loc) {
        if (Main.instance.serverType != ServerType.SKYBLOCK_ISLAND) return;
        Island i = getIslandByLoc(loc);
        if (i != null) {
            WorldBorderUtils.instanceClass.sendWorldBorder(p,
                    IslandUpgradeSize.getSizeFromLevel(i.getSizeUpgrade().getLevel()), i.getCenter());
        }
    }

    public void teleportPlayerToIslandSafe(Player p) {
        for (Island i : islands) {
            if (i.getMembers().containsKey(p.getUniqueId())) {
                PlayerUtils.instance.teleportPlayer(p, i.getHome());
                p.sendMessage("§6§lIles §8» §fTéléportation sur votre île...");
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
                oldOwner + ".", p);
    }

    public boolean isOwner(Player p) {
        Island playerIsland = getIslandByLoc(p.getLocation());
        if (playerIsland == null) return false;
        return playerIsland.getOwnerUUID().equals(p.getUniqueId());
    }

    public void deleteIsland(Player p) {
        Long start = System.currentTimeMillis();
        Island playerIsland = getPlayerIsland(p);

        playerIsland.sendMessageToEveryMember("§6§lIles §8» §4L'île a commencé à être supprimée...", p);

        CompletableFuture.runAsync(() -> {
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

                    System.out.println("Destroying island on api");
                    StorageManager.instance.deleteIsland(playerIsland.getUUID());

                    Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, () -> {
                        for (Minion m : playerIsland.getMinions()) {
                            if (getIslandByLoc(m.getBlocLocation()) == playerIsland) {
                                MinionManager.instance.removeMinion(m, playerIsland);
                            }
                        }
                        ArrayList<Chest> chests = new ArrayList<>(playerIsland.getChests());
                        for (Chest c : chests) {
                            if (getIslandByLoc(c.getBlock()) == playerIsland) {
                                ChestManager.instance.removeChestFromLoc(c.getBlock());
                            }
                        }
                        for (Spawner spawner : playerIsland.getSpawners()) {
                            SpawnersManager.instance.destroySpawner(spawner, playerIsland, true);
                        }

                        islands.remove(playerIsland);
                        Long currentMills = System.currentTimeMillis();
                        playerIsland.sendMessageToEveryMember("§6§lIles §8» §4L'île a été §lsupprimée §4par le chef. §f(en " + (currentMills - start) + "ms)", p);
                        for (Map.Entry<UUID, IslandRanks> entry : playerIsland.getMembers().entrySet()) {
                            Player member = Bukkit.getPlayer(entry.getKey());
                            if (member == null) {
                                member = Bukkit.getOfflinePlayer(entry.getKey()).getPlayer();
                            }
                            assert member != null;
                            PlayerUtils.instance.teleportPlayerFromRequest(member, SpawnCmd.Spawn, 0, ServerType.SKYBLOCK_HUB);
                        }
                        playerIsland.getMembers().clear();
                        HashMap<String, Object> toEdit = new HashMap<>();
                        toEdit.put(playerIsland.getUUID().toString(), null);
                        AsyncConfig.instance.setAndSaveAsync(toEdit, ConfigManager.instance.getDataIslands(), ConfigManager.instance.islandsFile);
                    }, 0);

                } catch (WorldEditException e) {
                    p.sendMessage("§6§lIles §8» §cUne erreur est survenue lors de la suppression de l'île. Merci de réessayer.");
                }
            } catch (IOException e) {
                p.sendMessage("§6§lIles §8» §cUne erreur est survenue lors de la lecture du schématic. Merci de contacter un administrateur.");
            }
        });
    }

    public void leaveIsland(Player p) {
        for (Island i : islands) {
            if (i.getMembers().containsKey(p.getUniqueId())) {
                i.sendMessageToEveryMember("§6§lIles §8» §f" + p.getName() + " a quitté l'île, il était " +
                        i.getMembers().get(p.getUniqueId()).name() + ".", p);
                i.getMembers().remove(p.getUniqueId());
                p.sendMessage("§6§lIles §8» §fVous avez quitté l'île.");
                PlayerUtils.instance.teleportPlayerFromRequest(p, SpawnCmd.Spawn, 0, ServerType.SKYBLOCK_HUB);
                break;
            }
        }
    }

    public Island getPlayerIsland(Player p) {
        for (Island island : islands) {
            if (island.getMembers().containsKey(p.getUniqueId())) {
                return island;
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
                        "§f a rejoint l'île par l'invitation de §f" + p.getName() + "§f.", p);
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
                            playerName + " au grade §6" + currentIsland.getMembers().get(targetUUID).name() + "§f.", player);
                    IslandMemberGui.instance.openMemberIslandMenu(player);
                    return true;
                }
            }
            if (clickType.isRightClick()) {
                if (currentIsland.demote(targetUUID)) {
                    currentIsland.sendMessageToEveryMember("§6§lIles §8» §f" + player.getName() + " a §crétrogradé§f " +
                            playerName + " au grade §6" + currentIsland.getMembers().get(targetUUID).name() + "§f.", player);
                    IslandMemberGui.instance.openMemberIslandMenu(player);
                    return true;
                }
            }
            if (clickType == ClickType.MIDDLE) {
                if (currentIsland.kickFromIsland(targetUUID)) {
                    currentIsland.sendMessageToEveryMember("§6§lIles §8» §f" + player.getName() + " a §4exclu§f " +
                            playerName + " de l'île, il avait le grade §6" + old.name() + "§f.", player);
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
                                playerName + " au grade §6" + old.name() + "§f.", player);
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
                                playerName + " au grade §6" + currentIsland.getMembers().get(targetUUID).name() + "§f.", player);
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
                                playerName + " de l'île, il avait le grade §6" + old.name() + "§f.", player);
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
                currentIsland.sendMessageToEveryMember("§6§lIles §8» §f" + player.getName() + " a annulé l'/les invitations de " + target.getName() + ".", player);
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
                    " a été invité à rejoindre l'île par §6" + p.getName() + "§f.", p);
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

    public Location getAFreeCenter() {
        Location toReturn = null;

        if (islands.size() == 0) {
            toReturn = new Location(Main.instance.mainWorld, 0, 80, 0);
        } else {
            int minx = 0;
            int minz = 0;
            int maxx = 0;
            int maxz = 0;
            for (Island i : islands) {
                if (i.getCenter() == null) continue;
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
        return toReturn;
    }

    public void genIsland(Player p) {
        p.sendMessage("§6§lIles §8» §aGénération de l'île en cours...");

        Location toReturn = getAFreeCenter();
        UUID uuid = UUID.randomUUID();
        Long start = System.currentTimeMillis();

        toReturn.setWorld(getMainWorld());


        FAWEUtils.instance.pasteSchemWithoutLockingThread(fileSchematic, toReturn);

        HashMap<UUID, IslandRanks> members = new HashMap<>();
        members.put(p.getUniqueId(), IslandRanks.CHEF);
        IslandBank islandBank = new IslandBank(0, 0, 0);
        IslandUpgradeSize islandUpgradeSize = new IslandUpgradeSize(0);
        IslandUpgradeMember islandUpgradeMember = new IslandUpgradeMember(0);
        IslandUpgradeGenerator islandUpgradeGenerator = new IslandUpgradeGenerator(0);
        ArrayList<UUID> banneds = new ArrayList<>();
        ArrayList<IslandChallenge> challenges = new ArrayList<>();
        Location home = toReturn.clone();
        home.add(0.5, 0.1, 0.5);
        home.setPitch(0);
        home.setYaw(130);
        Island newIsland = new Island("Ile de " + p.getName(), home, toReturn, uuid, members,
                islandUpgradeSize, islandUpgradeMember, islandBank, islandUpgradeGenerator, banneds, challenges,
                true, null, true, 0.0, null, null, null, null, true,
                new ArrayList<>());
        islands.add(newIsland);
        StorageManager.instance.createIsland(newIsland);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (home.clone().add(0, -2, 0).getBlock().getType() != Material.AIR) {
                    p.sendMessage("§6§lIles §8» §aVous avez généré une nouvelle île avec succès (en " + (System.currentTimeMillis() - start) + "ms).");
                    teleportPlayerToIslandSafe(p);
                    this.cancel();
                }
            }
        }.runTaskTimer(Main.instance, 0, 10L);

    }

}
