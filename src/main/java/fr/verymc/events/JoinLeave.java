package main.java.fr.verymc.events;

import com.iridium.iridiumskyblock.api.IridiumSkyblockAPI;
import main.java.fr.verymc.challenges.ChallengesReset;
import main.java.fr.verymc.core.Main;
import main.java.fr.verymc.eco.EcoAccountsManager;
import main.java.fr.verymc.scoreboard.ScoreBoard;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class JoinLeave implements Listener {

    String Grade = "§7N/A";

    @EventHandler
    public void OnJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        player.setGameMode(GameMode.SURVIVAL);

        //BossBar.AddBossBarForPlayer(player);

        ScoreBoard.acces.setScoreBoard(player);

        User user = LuckPermsProvider.get().getUserManager().getUser(player.getName());
        if (user.getCachedData().getMetaData().getPrefix() != null) {
            Grade = user.getCachedData().getMetaData().getPrefix();
        }
        String JoinMessage = null;
        if (!IridiumSkyblockAPI.getInstance().getUser(player).getIsland().isPresent()) {
            JoinMessage = "§7[§a+§7] " + Grade.replace("&", "§") + " " + player.getName();
        } else {
            int classement = 0;
            classement = IridiumSkyblockAPI.getInstance().getUser(player).getIsland().get().getRank();
            JoinMessage = "§7[§a+§7] [#" + classement + "] " + Grade.replace("&", "§") + " " + player.getName();
        }
        event.setJoinMessage(JoinMessage);

        ChallengesReset.CreateChallengesForPlayer(player.getUniqueId());

        EcoAccountsManager.instance.checkForAccount(player);

        if (Main.instance.getData().getString("Joueurs." + player.getUniqueId() + ".Atout.1.Active") == null) {
            Main.instance.getData().set("Joueurs." + player.getUniqueId() + ".Atout.1.Active", false);
            Main.instance.getData().set("Joueurs." + player.getUniqueId() + ".Atout.1.Level", 0);
            Main.instance.saveData();
        }
        if (Main.instance.getData().getString("Joueurs." + player.getUniqueId() + ".Atout.2.Active") == null) {
            Main.instance.getData().set("Joueurs." + player.getUniqueId() + ".Atout.2.Active", false);
            Main.instance.getData().set("Joueurs." + player.getUniqueId() + ".Atout.2.Level", 0);
            Main.instance.saveData();
        }
        if (Main.instance.getData().getString("Joueurs." + player.getUniqueId() + ".Atout.3.Active") == null) {
            Main.instance.getData().set("Joueurs." + player.getUniqueId() + ".Atout.3.Active", false);
            Main.instance.getData().set("Joueurs." + player.getUniqueId() + ".Atout.3.Level", 0);
            Main.instance.saveData();
        }
        if (Main.instance.getData().getString("Joueurs." + player.getUniqueId() + ".Atout.4.Active") == null) {
            Main.instance.getData().set("Joueurs." + player.getUniqueId() + ".Atout.4.Active", false);
            Main.instance.getData().set("Joueurs." + player.getUniqueId() + ".Atout.4.Level", 0);
            Main.instance.saveData();
        }
        if (Main.instance.getData().getBoolean("Joueurs." + player.getUniqueId() + ".Atout.1.Active") == true) {
            if (Main.instance.getData().getInt("Joueurs." + player.getUniqueId() + ".Atout.1.Level") == 2) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 999999999, 1));
            }
        }
        if (Main.instance.getData().getBoolean("Joueurs." + player.getUniqueId() + ".Atout.2.Active") == true) {
            if (Main.instance.getData().getInt("Joueurs." + player.getUniqueId() + ".Atout.2.Level") == 2) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999999, 1));
            }
        }
        if (Main.instance.getData().getBoolean("Joueurs." + player.getUniqueId() + ".Atout.3.Active") == true) {
            if (Main.instance.getData().getInt("Joueurs." + player.getUniqueId() + ".Atout.3.Level") == 3) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 999999999, 2));
            }
        }
    }

    @EventHandler
    public void OnLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        User user = LuckPermsProvider.get().getUserManager().getUser(player.getName());
        if (user.getCachedData().getMetaData().getPrefix() != null) {
            Grade = user.getCachedData().getMetaData().getPrefix();
        }
        String LeaveMessage = null;
        if (!IridiumSkyblockAPI.getInstance().getUser(player).getIsland().isPresent()) {
            LeaveMessage = "§7[§c-§7] " + Grade.replace("&", "§").replace("&", "§") + " " + player.getName();
        } else {
            int classement = 0;
            classement = IridiumSkyblockAPI.getInstance().getUser(player).getIsland().get().getRank();
            LeaveMessage = "§7[§c-§7] [#" + classement + "] " + Grade.replace("&", "§").replace("&", "§") + " " + player.getName();
        }
        event.setQuitMessage(LeaveMessage);
    }
}
