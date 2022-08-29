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

public class ServersManager {

    public static ServersManager instance;
    public HashMap<String, JSONArray> stringJSONArrayHashMap = new HashMap<>();

    public ServersManager() {
        instance = this;
    }

    public void sendToServer(String serverName, Player player, Location location, ServerType serverType) {
        if (serverName == null) {
            player.sendMessage("§cErreur lors du changement de serveur, code STS. Merci de réessayer ultérieurement.");
            return;
        }
        if (location != null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("coords", ObjectConverter.instance.locationToString(location));
            jsonObject.put("serverName", serverName);
            PluginMessageManager.instance.sendMessage(player, "subtp", jsonObject.toJSONString(), "skyblock:toproxy");
        }
        if (serverType != null) {
            PluginMessageManager.instance.sendMessage(player, "tpServerType", "none", "skyblock:toproxy");
            return;
        }


        final ByteArrayDataOutput out = ByteStreams.newDataOutput();
        Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(Main.instance, "BungeeCord");
        out.writeUTF("Connect");
        out.writeUTF(serverName);
        player.sendPluginMessage(Main.instance, "BungeeCord", out.toByteArray());
    }
}
