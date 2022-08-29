package main.java.fr.verymc.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import main.java.fr.verymc.JedisManager;
import main.java.fr.verymc.commons.enums.ServerType;
import main.java.fr.verymc.velocity.cmd.DungeonCmd;
import main.java.fr.verymc.velocity.cmd.SkyblockCmd;
import main.java.fr.verymc.velocity.events.ConnectionListener;
import main.java.fr.verymc.velocity.events.PlayerListener;
import main.java.fr.verymc.velocity.team.DungeonTeamManager;
import net.kyori.adventure.text.Component;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Plugin(id = "skyblockcore",
        name = "SkyblockCoreVelocity",
        version = "0.1.0-SNAPSHOT",
        url = "https://verymc.fr",
        description = "Owned by VeryMc",
        authors = {"Farmeurimmo"},
        dependencies = {
                @Dependency(id = "luckperms")
        })
public class Main {

    public static Main instance;
    private final ProxyServer server;
    private final Logger logger;
    public boolean maintenance = false;

    public LuckPerms luckPermsAPI;
    public String maintenance_perm = "skyblock.maintenance.acces";

    @Inject
    public Main(ProxyServer server, Logger logger) {
        this.server = server;
        this.logger = logger;

        logger.info("Loading velocity plugin...");

        instance = this;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent e) {
        new JedisManager();

        server.getChannelRegistrar().register(MinecraftChannelIdentifier.create("skyblock", "tospigot"));
        server.getChannelRegistrar().register(MinecraftChannelIdentifier.from("skyblock:toproxy"));

        server.getEventManager().register(this, new ConnectionListener());
        server.getEventManager().register(this, new ChannelsManager(server, logger));
        server.getEventManager().register(this, new PlayerListener());

        new TABManager(server, logger);
        new DungeonTeamManager(server, logger);
        new DungeonQueueManager(server, logger);

        server.getCommandManager().register("dongeon", new DungeonCmd());
        server.getCommandManager().register("skyblock", new SkyblockCmd());

        luckPermsAPI = LuckPermsProvider.get();

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

    public void sendConnectionMessage(Player player) {
        Main.instance.sendMessageToSkyblock("§7[§a+§7] " + getPrefix(player.getUniqueId()).replace("&", "§") + " " + player.getUsername() + " "
                + getSuffix(player.getUniqueId()).replace("&", "§"));
    }

    public void sendDeconnectionMessage(Player player) {
        Main.instance.sendMessageToSkyblock("§7[§c-§7] " + getPrefix(player.getUniqueId()).replace("&", "§") + " " + player.getUsername() +
                " " + getSuffix(player.getUniqueId()).replace("&", "§"));
    }

    public void startMaintenanceModule() {
        for (RegisteredServer server : server.getAllServers()) {
            if (isSkyblockServer(server)) {
                server.getPlayersConnected().forEach(player -> {
                    if (!player.hasPermission(maintenance_perm))
                        player.spoofChatInput("/hub");
                    player.sendMessage(Component.text("§cLe serveur skyblock passe en maintenance !"));
                });
            }
        }
        server.getScheduler()
                .buildTask(Main.instance, () -> {
                    for (RegisteredServer registeredServer : server.getAllServers()) {
                        if (isSkyblockServer(registeredServer)) {
                            registeredServer.getPlayersConnected().forEach(player -> {
                                if (!player.hasPermission(maintenance_perm))
                                    player.disconnect(Component.text("§cLe serveur skyblock passe en maintenance !"));
                            });
                        }
                    }
                })
                .delay(15L, TimeUnit.SECONDS)
                .schedule();
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

    public RegisteredServer getServerByName(String str) {
        for (RegisteredServer registeredServer : server.getAllServers()) {
            if (registeredServer.getServerInfo().getName().equals(str)) {
                return registeredServer;
            }
        }
        return null;
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

    public RegisteredServer getServeurOfType(ServerType serverType) {
        for (RegisteredServer registeredServer : getSkyblockServers()) {
            if (registeredServer.getServerInfo().getName().contains(serverType.getDisplayName())) {
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

    public ArrayList<Player> getSkyblockPlayers() {
        ArrayList<Player> toReturn = new ArrayList<>();
        for (RegisteredServer registeredServer : getSkyblockServers()) {
            toReturn.addAll(registeredServer.getPlayersConnected());
        }
        return toReturn;
    }

    public String getPrefix(UUID uuid) {
        User user = Main.instance.luckPermsAPI.getUserManager().getUser(uuid);
        if (user.getCachedData().getMetaData().getPrefix() != null) {
            return user.getCachedData().getMetaData().getPrefix();
        }
        return "";
    }

    public String getSuffix(UUID uuid) {
        User user = Main.instance.luckPermsAPI.getUserManager().getUser(uuid);
        if (user.getCachedData().getMetaData().getSuffix() != null) {
            return user.getCachedData().getMetaData().getSuffix();
        }
        return "";
    }

}
