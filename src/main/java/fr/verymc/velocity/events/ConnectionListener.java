package main.java.fr.verymc.velocity.events;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.KickedFromServerEvent;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.event.player.ServerPostConnectEvent;
import com.velocitypowered.api.event.player.ServerPreConnectEvent;
import com.velocitypowered.api.proxy.ServerConnection;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import main.java.fr.verymc.velocity.ChannelsManager;
import main.java.fr.verymc.velocity.Main;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.Optional;

public class ConnectionListener {

    @Subscribe
    public void postServerConnection(ServerPostConnectEvent e) {
        if (!e.getPlayer().getCurrentServer().isPresent()) {
            return;
        }
        RegisteredServer registeredServer = e.getPlayer().getCurrentServer().get().getServer();
        if (!Main.instance.isSkyblockServer(registeredServer)) {
            return;
        }
        if (!ChannelsManager.instance.awaitingServerSwitch.containsKey(e.getPlayer().getUniqueId())) {
            return;
        }
        ChannelsManager.instance.sendPluginMessage(e.getPlayer(), "subtp",
                ChannelsManager.instance.awaitingServerSwitch.get(e.getPlayer().getUniqueId()));
        ChannelsManager.instance.awaitingServerSwitch.remove(e.getPlayer().getUniqueId());
    }

    @Subscribe
    public void onPlayerSwitchServer(ServerPreConnectEvent e) {
        Optional<ServerConnection> registeredServer = e.getPlayer().getCurrentServer();
        if (registeredServer.isEmpty()) {
            return;
        }
        if (!Main.instance.isSkyblockServer(registeredServer.get().getServer())) {
            return;
        }
        if (Main.instance.maintenance && !e.getPlayer().hasPermission("skyblock.maintenance.acces")) {
            e.setResult(ServerPreConnectEvent.ServerResult.denied());
            e.getPlayer().sendMessage(Component.text("§6§lSkyblock §8§l» §cLes serveurs Skyblock sont actuellement en maintenance."));
            return;
        }
        e.getPlayer().sendMessage(Component.text("Envoi sur " + e.getOriginalServer().getServerInfo().getName() + "...", NamedTextColor.GRAY));
    }

    @Subscribe
    public void switchServer(ServerConnectedEvent e) {
        RegisteredServer registeredServer = e.getServer();
        if (!Main.instance.isSkyblockServer(registeredServer)) {
            return;
        }
        e.getPlayer().sendMessage(Component.text("Vous avez été envoyé avec succès sur " + registeredServer.getServerInfo().getName(), NamedTextColor.GREEN));
    }

    @Subscribe
    public void kickedFromServer(KickedFromServerEvent e) {
        RegisteredServer registeredServer = e.getPlayer().getCurrentServer().get().getServer();
        if (!Main.instance.isSkyblockServer(registeredServer)) {
            return;
        }
        registeredServer = Main.instance.getServeurToLogin();
        e.getPlayer().createConnectionRequest(registeredServer);
        e.getPlayer().sendMessage(Component.text("Envoi sur " + registeredServer.getServerInfo().getName() +
                " à la suite d'un kick pour " + e.getServerKickReason(), NamedTextColor.GRAY));
    }

}
