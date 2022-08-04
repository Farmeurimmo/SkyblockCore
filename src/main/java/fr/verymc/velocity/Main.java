package main.java.fr.verymc.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import main.java.fr.verymc.commons.enums.ServerType;
import main.java.fr.verymc.velocity.cmd.DungeonCmd;
import main.java.fr.verymc.velocity.events.ConnectionListener;
import main.java.fr.verymc.velocity.events.PlayerListener;
import main.java.fr.verymc.velocity.team.DungeonTeamManager;
import net.kyori.adventure.text.Component;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Plugin(id = "skyblockcore", name = "SkyblockCoreVelocity", version = "0.1.0-SNAPSHOT",
        url = "https://verymc.fr", description = "Owned by VeryMc", authors = {"Farmeurimmo"})
public class Main {

    public static Main instance;
    private final ProxyServer server;
    private final Logger logger;

    @Inject
    public Main(ProxyServer server, Logger logger) {
        this.server = server;
        this.logger = logger;

        logger.info("Loading velocity plugin...");

        instance = this;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent e) {
        server.getChannelRegistrar().register(MinecraftChannelIdentifier.create("skyblock", "tospigot"));
        server.getChannelRegistrar().register(MinecraftChannelIdentifier.from("skyblock:toproxy"));

        server.getEventManager().register(this, new ConnectionListener());
        server.getEventManager().register(this, new ChannelsManager(server, logger));
        server.getEventManager().register(this, new PlayerListener());

        new TABManager(server, logger);
        new DungeonTeamManager(server, logger);

        server.getCommandManager().register("dungeon", new DungeonCmd());

        logger.info("§aLoading completed !");
    }

    public int playerCountOfSkyblock() {
        int count = 0;
        for (RegisteredServer server : server.getAllServers()) {
            if (isSkyblockServer(server)) {
                count += server.getPlayersConnected().size();
            }
        }
        return count;
    }

    public boolean isSkyblockServer(RegisteredServer registeredServer) {
        if (registeredServer.getServerInfo().getName().contains(ServerType.SKYBLOCK_HUB.getDisplayName())) return true;
        if (registeredServer.getServerInfo().getName().contains(ServerType.SKYBLOCK_ISLAND.getDisplayName()))
            return true;
        if (registeredServer.getServerInfo().getName().contains(ServerType.SKYBLOCK_DUNGEON.getDisplayName()))
            return true;
        return false;
    }

    public void sendMessageToSkyblock(String message) {
        for (RegisteredServer registeredServer : server.getAllServers()) {
            if (isSkyblockServer(registeredServer)) {
                registeredServer.getPlayersConnected().forEach(player -> player.sendMessage(Component.text(message)));
            }
        }
    }

    public ArrayList<RegisteredServer> getSkyblockServers() {
        ArrayList<RegisteredServer> toReturn = new ArrayList<>();
        for (RegisteredServer registeredServer : server.getAllServers()) {
            if (isSkyblockServer(registeredServer)) {
                toReturn.add(registeredServer);
            }
        }
        return toReturn;
    }

    public RegisteredServer getServeurToLogin() {
        for (RegisteredServer registeredServer : getSkyblockServers()) {
            if (registeredServer.getServerInfo().getName().contains(ServerType.SKYBLOCK_HUB.getDisplayName())) {
                return registeredServer;
            }
        }
        return null;
    }

    public Optional<Player> getPlayer(String str) {
        return server.getPlayer(str);
    }

    public Optional<Player> getPlayer(UUID uuid) {
        return server.getPlayer(uuid);
    }

    public ArrayList<Player> getPlayerWithout(ArrayList<Player> without) {
        ArrayList<Player> playersToReturn = new ArrayList<>();
        for (Player player : server.getAllPlayers()) {
            if (without.contains(player)) continue;
            playersToReturn.add(player);
        }
        return playersToReturn;
    }

}
