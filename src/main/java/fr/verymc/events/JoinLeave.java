package main.java.fr.verymc.events;

import main.java.fr.verymc.challenges.ChallengesReset;
import main.java.fr.verymc.config.ConfigManager;
import main.java.fr.verymc.eco.EcoAccountsManager;
import main.java.fr.verymc.island.Island;
import main.java.fr.verymc.island.IslandManager;
import main.java.fr.verymc.island.guis.IslandTopGui;
import main.java.fr.verymc.island.perms.IslandRanks;
import main.java.fr.verymc.scoreboard.ScoreBoard;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Map;
import java.util.UUID;

public class JoinLeave implements Listener {

    String Grade = "§7N/A";

    @EventHandler
    public void OnJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        player.setGameMode(GameMode.SURVIVAL);

        Island playerIsland = IslandManager.instance.getPlayerIsland(player);

        //BossBar.AddBossBarForPlayer(player);

        ScoreBoard.acces.setScoreBoard(player);

        User user = LuckPermsProvider.get().getUserManager().getUser(player.getName());
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

        ChallengesReset.CreateChallengesForPlayer(player.getUniqueId());

        EcoAccountsManager.instance.checkForAccount(player);

        if (ConfigManager.instance.getDataAtoutsChallenges().getString("Joueurs." + player.getUniqueId() + ".Atout.1.Active") == null) {
            ConfigManager.instance.getDataAtoutsChallenges().set("Joueurs." + player.getUniqueId() + ".Atout.1.Active", false);
            ConfigManager.instance.getDataAtoutsChallenges().set("Joueurs." + player.getUniqueId() + ".Atout.1.Level", 0);
            ConfigManager.instance.saveData();
        }
        if (ConfigManager.instance.getDataAtoutsChallenges().getString("Joueurs." + player.getUniqueId() + ".Atout.2.Active") == null) {
            ConfigManager.instance.getDataAtoutsChallenges().set("Joueurs." + player.getUniqueId() + ".Atout.2.Active", false);
            ConfigManager.instance.getDataAtoutsChallenges().set("Joueurs." + player.getUniqueId() + ".Atout.2.Level", 0);
            ConfigManager.instance.saveData();
        }
        if (ConfigManager.instance.getDataAtoutsChallenges().getString("Joueurs." + player.getUniqueId() + ".Atout.3.Active") == null) {
            ConfigManager.instance.getDataAtoutsChallenges().set("Joueurs." + player.getUniqueId() + ".Atout.3.Active", false);
            ConfigManager.instance.getDataAtoutsChallenges().set("Joueurs." + player.getUniqueId() + ".Atout.3.Level", 0);
            ConfigManager.instance.saveData();
        }
        if (ConfigManager.instance.getDataAtoutsChallenges().getString("Joueurs." + player.getUniqueId() + ".Atout.4.Active") == null) {
            ConfigManager.instance.getDataAtoutsChallenges().set("Joueurs." + player.getUniqueId() + ".Atout.4.Active", false);
            ConfigManager.instance.getDataAtoutsChallenges().set("Joueurs." + player.getUniqueId() + ".Atout.4.Level", 0);
            ConfigManager.instance.saveData();
        }
        if (ConfigManager.instance.getDataAtoutsChallenges().getBoolean("Joueurs." + player.getUniqueId() + ".Atout.1.Active") == true) {
            if (ConfigManager.instance.getDataAtoutsChallenges().getInt("Joueurs." + player.getUniqueId() + ".Atout.1.Level") == 2) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 999999999, 1));
            }
        }
        if (ConfigManager.instance.getDataAtoutsChallenges().getBoolean("Joueurs." + player.getUniqueId() + ".Atout.2.Active") == true) {
            if (ConfigManager.instance.getDataAtoutsChallenges().getInt("Joueurs." + player.getUniqueId() + ".Atout.2.Level") == 2) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999999, 1));
            }
        }
        if (ConfigManager.instance.getDataAtoutsChallenges().getBoolean("Joueurs." + player.getUniqueId() + ".Atout.3.Active") == true) {
            if (ConfigManager.instance.getDataAtoutsChallenges().getInt("Joueurs." + player.getUniqueId() + ".Atout.3.Level") == 3) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 999999999, 2));
            }
        }
        IslandManager.instance.setWorldBorder(player);
    }

    @EventHandler
    public void OnLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Island playerIsland = IslandManager.instance.getPlayerIsland(player);
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
        User user = LuckPermsProvider.get().getUserManager().getUser(player.getName());
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
    }
}
