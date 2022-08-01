package main.java.fr.verymc.velocity.events;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.ServerPostConnectEvent;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import main.java.fr.verymc.velocity.ChannelsManager;
import main.java.fr.verymc.velocity.Main;

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
        ChannelsManager.instance.sendPluginMessage(e.getPlayer(), "subtp", null, ChannelsManager.instance.awaitingServerSwitch.get(e.getPlayer().getUniqueId()));
    }


}
