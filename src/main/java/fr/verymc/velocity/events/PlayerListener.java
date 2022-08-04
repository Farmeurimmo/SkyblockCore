package main.java.fr.verymc.velocity.events;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.event.player.PlayerChooseInitialServerEvent;
import com.velocitypowered.api.proxy.Player;
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
    public void onPlayerChat(PlayerChatEvent e) {
        if (Main.instance.isSkyblockServer(e.getPlayer().getCurrentServer().get().getServer())) {
            DungeonTeam team = DungeonTeamManager.instance.getPlayerTeam(e.getPlayer());
            if (team != null) {
                if (team.isTchatMode(e.getPlayer().getUniqueId())) {
                    e.setResult(PlayerChatEvent.ChatResult.denied());
                    for (Player player1 : team.getPlayers()) {
                        player1.sendMessage(Component.text("§6§lDungeon §8» §a" + e.getPlayer().getUsername() + "§8: §f" + e.getMessage()));
                    }
                }
            }
        }
    }
}
