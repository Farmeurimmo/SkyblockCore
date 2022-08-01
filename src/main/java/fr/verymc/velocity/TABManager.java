package main.java.fr.verymc.velocity;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.adventure.text.Component;
import org.slf4j.Logger;

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

    public void sendTabToPlayer(Player player) {
        player.sendPlayerListHeaderAndFooter(Component.text("\n§f• §6§lVery§f§lMc §f•\n\n§7Global §7▸ §a" +
                        server.getAllPlayers().size() + "\n§7En Skyblock §7▸ §a" + Main.instance.playerCountOfSkyblock() +
                "\n\n§7Une question/problème ? contacte un §9§lSTAFF §7!\n"),
                Component.text("\n§7Serveur Mini-Jeux Francophone\n§7Vous êtes sur ▸ §fplay.§6§lvery§f§lmc§f.fr §7◂"));
    }

    public void autoTABForPlayer() {
        server.getScheduler()
                .buildTask(Main.instance, () -> {
                    for (Player player : server.getAllPlayers()) {
                        sendTabToPlayer(player);
                    }
                })
                .repeat(1L, TimeUnit.SECONDS)
                .schedule();
    }
}
