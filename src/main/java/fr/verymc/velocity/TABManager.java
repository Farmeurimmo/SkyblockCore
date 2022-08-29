package main.java.fr.verymc.velocity;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.player.TabListEntry;
import net.kyori.adventure.text.Component;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class TABManager {

    public static TABManager instance;

    private final ProxyServer server;
    private final Logger logger;

    public TABManager(ProxyServer server, Logger logger) {
        instance = this;

        this.server = server;
        this.logger = logger;

        autoTABForPlayer();
    }

    public void sendTabToPlayer(Player p) {
        p.sendPlayerListHeaderAndFooter(Component.text("\n§f• §6§lVery§f§lMc §f•\n\n§7Global §7▸ §a" +
                        server.getAllPlayers().size() + "\n§7En Skyblock §7▸ §a" + Main.instance.playerCountOfSkyblock() +
                        "\n\n§7Une question/problème ? contacte un §9§lSTAFF §7!\n"),
                Component.text("\n§7Serveur Mini-Jeux Francophone\n§7Vous êtes sur ▸ §fplay.§6§lvery§f§lmc§f.fr §7◂"));
    }

    public void updatePlayers(ArrayList<Player> players) {
        for (Player player : players) {
            for (Player player1 : players) {
                String rank = Main.instance.luckPermsAPI.getUserManager().getUser(player1.getUniqueId()).getCachedData().getMetaData().getPrefix();
                player.getTabList().removeEntry(player1.getUniqueId());
                player.getTabList().addEntry(
                        TabListEntry.builder()
                                .displayName(Component.text(rank.replace("&", "§") + " " + player1.getUsername()))
                                .profile(player1.getGameProfile())
                                .tabList(player.getTabList())
                                .build()
                );
            }

            for (TabListEntry entry : player.getTabList().getEntries()) {
                UUID uuid = entry.getProfile().getId();
                Optional<Player> playerOptional = server.getPlayer(uuid);
                if (playerOptional.isPresent()) {
                    entry.setLatency((int) player.getPing());
                } else {
                    player.getTabList().removeEntry(uuid);
                }
            }
        }
    }

    public void autoTABForPlayer() {
        server.getScheduler()
                .buildTask(Main.instance, () -> {
                    ArrayList<Player> players = Main.instance.getSkyblockPlayers();
                    updatePlayers(players);
                    for (Player player : players) {
                        sendTabToPlayer(player);
                    }
                })
                .repeat(2L, TimeUnit.SECONDS)
                .schedule();
    }
}
