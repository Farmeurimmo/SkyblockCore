package main.java.fr.verymc.spigot.core.events;

import main.java.fr.verymc.commons.enums.ServerType;
import main.java.fr.verymc.spigot.Main;
import main.java.fr.verymc.spigot.core.InventorySyncManager;
import main.java.fr.verymc.spigot.core.cmd.base.SpawnCmd;
import main.java.fr.verymc.spigot.core.scoreboard.ScoreBoard;
import main.java.fr.verymc.spigot.core.storage.SkyblockUser;
import main.java.fr.verymc.spigot.core.storage.SkyblockUserManager;
import main.java.fr.verymc.spigot.core.storage.StorageJSONManager;
import main.java.fr.verymc.spigot.dungeon.Dungeon;
import main.java.fr.verymc.spigot.dungeon.DungeonManager;
import main.java.fr.verymc.spigot.hub.invest.InvestManager;
import main.java.fr.verymc.spigot.island.Island;
import main.java.fr.verymc.spigot.island.IslandManager;
import main.java.fr.verymc.spigot.island.perms.IslandRanks;
import main.java.fr.verymc.spigot.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Map;
import java.util.UUID;

public class JoinLeave implements Listener {

    @EventHandler
    public void postLogin(PlayerLoginEvent e) {
        if (!e.getPlayer().hasPermission("maintenance")) {
            e.disallow(PlayerLoginEvent.Result.KICK_OTHER, "§cLe serveur est en maintenance.");
        }
    }

    @EventHandler
    public void OnJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        InventorySyncManager.instance.playerJoin(player);

        SkyblockUserManager.instance.checkForAccount(player);

        SkyblockUser skyblockUser = SkyblockUserManager.instance.getUser(player.getUniqueId());

        player.setGameMode(GameMode.SURVIVAL);


        if (Main.instance.serverType == ServerType.SKYBLOCK_HUB) {
            PlayerUtils.instance.teleportPlayer(player, SpawnCmd.Spawn);
        }

        Island playerIsland = null;
        if (Main.instance.serverType == ServerType.SKYBLOCK_ISLAND) {
            playerIsland = IslandManager.instance.getPlayerIsland(player);
            if (playerIsland != null) {
                playerIsland.toggleTimeAndWeather();
                player.chat("/is go");
                IslandManager.instance.setWorldBorder(player);
            } else {
                IslandManager.instance.addPlayerAwaiting(player);
                Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.instance, () -> {
                    if (!IslandManager.instance.awaiting.contains(player.getUniqueId())) return;
                    if (player.isOnline())
                        player.sendMessage("§6§lIles §8» §cVous n'avez pas d'ile. Merci de taper la commande /is create pour créer une ile. " +
                                "Ou d'attendre qu'une personne vous invite sur son île");
                }, 0L, 20L * 5);
            }
        }

        if (Main.instance.serverType == ServerType.SKYBLOCK_DUNGEON) {
            DungeonManager.instance.checkLogged(event.getPlayer());
        }

        //BossBar.AddBossBarForPlayer(player);

        ScoreBoard.acces.setScoreBoard(player);

        /*User user = LuckPermsProvider.get().getUserManager().getUser(player.getName());
        String Grade = "§7N/A";
        if (user.getCachedData().getMetaData().getPrefix() != null) {
            Grade = user.getCachedData().getMetaData().getPrefix();
        }
        String JoinMessage = null;
        if (playerIsland == null) {
            JoinMessage = "§7[§a+§7] " + Grade.replace("&", "§") + " " + player.getName();
        } else {
            String classement = "#N/A";
            if (IslandTopGui.instance.getTopIsland().containsKey(playerIsland)) {
                classement = "" + IslandTopGui.instance.getTopIsland().get(playerIsland);
            }
            JoinMessage = "§7[§a+§7] [#" + classement + "] " + Grade.replace("&", "§") + " " + player.getName();
        }
        event.setJoinMessage(JoinMessage);*/
        event.setJoinMessage(null);

        if (skyblockUser.hasHasteActive()) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 999999999, 1));
        }
        if (skyblockUser.hasSpeedActive()) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999999, 1));
        }
        if (skyblockUser.hasJumpActive()) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 999999999, 2));
        }
    }

    @EventHandler
    public void OnLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (Main.instance.serverType == ServerType.SKYBLOCK_DUNGEON) {
            Dungeon dungeon = DungeonManager.instance.getDungeonByPlayer(player);
            if (dungeon != null) {
                dungeon.addDeadPlayer(player);
            }
        }
        SkyblockUser skyblockUser = SkyblockUserManager.instance.getUser(player.getUniqueId());
        if (skyblockUser != null) {
            if (skyblockUser.isInInvestMode()) {
                InvestManager.instance.giveReward(skyblockUser);
            }
        }
        Island playerIsland = null;
        if (Main.instance.serverType == ServerType.SKYBLOCK_ISLAND) {
            playerIsland = IslandManager.instance.getPlayerIsland(player);
            if (playerIsland != null) {
                int onlineIs = 0;
                for (Map.Entry<UUID, IslandRanks> entry : playerIsland.getMembers().entrySet()) {
                    Player member = Bukkit.getPlayer(entry.getKey());
                    if (member != null) {
                        if (member.isOnline()) {
                            onlineIs++;
                        }
                    }
                }
                if (onlineIs == 0) {
                    playerIsland.clearCoops();
                }
            } else {
                for (Island island : IslandManager.instance.islands) {
                    if (island.getCoops().contains(player.getUniqueId())) {
                        island.removeCoop(player.getUniqueId());
                    }
                }
            }
        }
        InventorySyncManager.instance.playerQuit(player);
        /*User user = LuckPermsProvider.get().getUserManager().getUser(player.getName());
        String Grade = "§7N/A";
        if (user.getCachedData().getMetaData().getPrefix() != null) {
            Grade = user.getCachedData().getMetaData().getPrefix();
        }
        String LeaveMessage = null;
        if (playerIsland == null) {
            LeaveMessage = "§7[§c-§7] " + Grade.replace("&", "§").replace("&", "§") + " " + player.getName();
        } else {
            String classement = "#N/A";
            if (IslandTopGui.instance.getTopIsland().containsKey(playerIsland)) {
                classement = "#" + IslandTopGui.instance.getTopIsland().get(playerIsland);
            }
            LeaveMessage = "§7[§c-§7] [" + classement + "] " + Grade.replace("&", "§").replace("&", "§") + " " + player.getName();
        }
        event.setQuitMessage(LeaveMessage);*/
        event.setQuitMessage(null);
    }

    @EventHandler
    public void preLogin(AsyncPlayerPreLoginEvent event) {
        if (StorageJSONManager.instance.loading) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER,
                    "§cLe serveur est en cours de démarrage, veuillez patienter.");
        }
    }
}
