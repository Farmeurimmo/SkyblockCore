package main.java.fr.verymc.core.events;

import main.java.fr.verymc.Main;
import main.java.fr.verymc.commons.enums.ServerType;
import main.java.fr.verymc.core.cmd.base.SpawnCmd;
import main.java.fr.verymc.core.scoreboard.ScoreBoard;
import main.java.fr.verymc.core.storage.SkyblockUser;
import main.java.fr.verymc.core.storage.SkyblockUserManager;
import main.java.fr.verymc.core.storage.StorageYAMLManager;
import main.java.fr.verymc.hub.invest.InvestManager;
import main.java.fr.verymc.island.Island;
import main.java.fr.verymc.island.IslandManager;
import main.java.fr.verymc.island.guis.IslandTopGui;
import main.java.fr.verymc.island.perms.IslandRanks;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
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

        if (Main.instance.serverType == ServerType.HUB) {
            player.teleport(SpawnCmd.Spawn);
        }

        SkyblockUserManager.instance.checkForAccount(player);

        SkyblockUser skyblockUser = SkyblockUserManager.instance.getUser(player.getUniqueId());

        player.setGameMode(GameMode.SURVIVAL);


        Island playerIsland = null;
        if (Main.instance.serverType == ServerType.ISLAND) {
            playerIsland = IslandManager.instance.getPlayerIsland(player);
            IslandManager.instance.setWorldBorder(player);
            if (playerIsland != null) {
                playerIsland.toggleTimeAndWeather();
            }
        }

        //BossBar.AddBossBarForPlayer(player);

        ScoreBoard.acces.setScoreBoard(player);

        User user = LuckPermsProvider.get().getUserManager().getUser(player.getName());
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
        event.setJoinMessage(JoinMessage);

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
        SkyblockUser skyblockUser = SkyblockUserManager.instance.getUser(player.getUniqueId());
        Island playerIsland = null;
        if (Main.instance.serverType == ServerType.ISLAND) {
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
        User user = LuckPermsProvider.get().getUserManager().getUser(player.getName());
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
        event.setQuitMessage(LeaveMessage);
        if (skyblockUser != null) {
            if (skyblockUser.isInInvestMode()) {
                InvestManager.instance.giveReward(skyblockUser);
            }
        }
    }

    @EventHandler
    public void preLogin(AsyncPlayerPreLoginEvent event) {
        if (StorageYAMLManager.instance.loading) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER,
                    "§cLe serveur est en cours de démarrage, veuillez patienter.");
        }
    }
}
