package main.java.fr.verymc.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import main.java.fr.verymc.commons.enums.ServerType;
import main.java.fr.verymc.velocity.events.ConnectionListener;
import net.kyori.adventure.text.Component;
import org.slf4j.Logger;

import java.util.ArrayList;

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

        new TABManager(server, logger);

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
}
