package main.java.fr.verymc.velocity.events;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.*;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import main.java.fr.verymc.velocity.ChannelsManager;
import main.java.fr.verymc.velocity.Main;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

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
        ChannelsManager.instance.sendPluginMessage(e.getPlayer(), "subtp", null,
                ChannelsManager.instance.awaitingServerSwitch.get(e.getPlayer().getUniqueId()));
        ChannelsManager.instance.awaitingServerSwitch.remove(e.getPlayer().getUniqueId());
    }

    @Subscribe
    public void onPlayerSwitchServer(ServerPreConnectEvent e) {
        e.getPlayer().sendMessage(Component.text("Envoi sur " + e.getOriginalServer().getServerInfo().getName() + "...", NamedTextColor.GRAY));
    }

    @Subscribe
    public void switchServer(ServerConnectedEvent e) {
        RegisteredServer registeredServer = Main.instance.getServeurToLogin();
        e.getPlayer().sendMessage(Component.text("Vous avez été envoyé avec succès sur " + registeredServer.getServerInfo().getName(), NamedTextColor.GREEN));
    }

    @Subscribe
    public void kickedFromServer(KickedFromServerEvent e) {
        RegisteredServer registeredServer = Main.instance.getServeurToLogin();
        e.getPlayer().createConnectionRequest(registeredServer);
        e.getPlayer().sendMessage(Component.text("Envoi sur " + registeredServer.getServerInfo().getName() +
                " à la suite d'un kick pour " + e.getServerKickReason(), NamedTextColor.GRAY));
    }

    @Subscribe
    public void onPlayerChat(PlayerChooseInitialServerEvent e) {
        if (!e.getInitialServer().isPresent()) {
            e.setInitialServer(Main.instance.getServeurToLogin());
        }
    }


}
