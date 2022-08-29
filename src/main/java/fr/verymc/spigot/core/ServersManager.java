package main.java.fr.verymc.spigot.core;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import main.java.fr.verymc.commons.enums.ServerType;
import main.java.fr.verymc.spigot.Main;
import main.java.fr.verymc.spigot.utils.ObjectConverter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ServersManager {

    public static ServersManager instance;
    public HashMap<String, JSONArray> stringJSONArrayHashMap = new HashMap<>();
    public HashMap<String, ServerType> servers = new HashMap<>();

    public ServersManager() {
        instance = this;
    }

    public void sendToServer(String serverName, Player player, Location location) {
        if (serverName == null) {
            player.sendMessage("§cErreur lors du changement de serveur, code STS. Merci de réessayer ultérieurement.");
            return;
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("coords", ObjectConverter.instance.locationToString(location));
        jsonObject.put("serverName", serverName);
        PluginMessageManager.instance.sendMessage(player, "subtp", jsonObject.toJSONString(), "skyblock:toproxy");

        final ByteArrayDataOutput out = ByteStreams.newDataOutput();
        Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(Main.instance, "BungeeCord");
        out.writeUTF("Connect");
        out.writeUTF(serverName);
        player.sendPluginMessage(Main.instance, "BungeeCord", out.toByteArray());
    }

    public String getServerOfType(ServerType serverType) {
        for (Map.Entry<String, ServerType> entry : servers.entrySet()) {
            if (entry.getValue().equals(serverType)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
