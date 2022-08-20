package main.java.fr.verymc.velocity.events;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.event.player.PlayerChooseInitialServerEvent;
import com.velocitypowered.api.event.player.ServerPostConnectEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import main.java.fr.verymc.velocity.Main;
import main.java.fr.verymc.velocity.team.DungeonTeam;
import main.java.fr.verymc.velocity.team.DungeonTeamManager;
import net.kyori.adventure.text.Component;

public class PlayerListener {

    @Subscribe
    public void onPlayerInitialConnexion(PlayerChooseInitialServerEvent e) {
        if (!e.getInitialServer().isPresent()) {
            e.setInitialServer(Main.instance.getServeurToLogin());
        }
    }

    @Subscribe
    public void joinSkyblock(ServerPostConnectEvent e) {
        Player player = e.getPlayer();
        RegisteredServer pServer = player.getCurrentServer().get().getServer();
        if (e.getPreviousServer() == null) {
            if (Main.instance.isSkyblockServer(pServer)) {
                Main.instance.sendConnectionMessage(player);
            }
        } else {
            if (!Main.instance.isSkyblockServer(e.getPreviousServer())) {
                if (Main.instance.isSkyblockServer(pServer)) {
                    Main.instance.sendConnectionMessage(player);
                }
            } else {
                if (!Main.instance.isSkyblockServer(pServer)) {
                    Main.instance.sendDeconnectionMessage(player);
                }
            }
        }
    }

    @Subscribe
    public void leaveServer(DisconnectEvent e) {
        Player player = e.getPlayer();
        RegisteredServer pServer = player.getCurrentServer().get().getServer();
        if (Main.instance.isSkyblockServer(pServer)) {
            Main.instance.sendDeconnectionMessage(player);
        }
    }

    @Subscribe
    public void onPlayerChat(PlayerChatEvent e) {
        if (Main.instance.isSkyblockServer(e.getPlayer().getCurrentServer().get().getServer())) {
            Player player = e.getPlayer();
            DungeonTeam team = DungeonTeamManager.instance.getPlayerTeam(e.getPlayer());
            if (team != null) {
                if (team.isTchatMode(e.getPlayer().getUniqueId())) {
                    e.setResult(PlayerChatEvent.ChatResult.denied());
                    for (Player player1 : team.getPlayers()) {
                        player1.sendMessage(Component.text("§6§lDongeons §8» §a" + e.getPlayer().getUsername() + "§8: §f" + e.getMessage()));
                    }
                }
                return;
            }
            String color = (player.hasPermission("staff") ? "§f" : "§7");
            String Prefix = Main.instance.getPrefix(player.getUniqueId());
            String Suffix = Main.instance.getSuffix(player.getUniqueId());
            String message = Prefix + " " + player.getUsername() + Suffix + "§7: " + color + e.getMessage();
            e.setResult(PlayerChatEvent.ChatResult.message(message));
        }
    }
}
