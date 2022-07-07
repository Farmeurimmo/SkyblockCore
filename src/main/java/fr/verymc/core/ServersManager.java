package main.java.fr.verymc.core;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import main.java.fr.verymc.Main;
import main.java.fr.verymc.commons.enums.ServerType;
import main.java.fr.verymc.commons.utils.HTTPUtils;
import main.java.fr.verymc.island.IslandManager;
import main.java.fr.verymc.utils.ObjectConverter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServersManager {

    public static ServersManager instance;
    public HashMap<String, JSONArray> stringJSONArrayHashMap = new HashMap<>();
    public HashMap<String, ServerType> servers = new HashMap<>();

    public ServersManager() {
        instance = this;

        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.instance, new Runnable() {
            @Override
            public void run() {
                try {
                    if (Main.instance.serverType == ServerType.ISLAND) {
                        autoSendPlayers();
                    }
                    autoReadServers();
                    autoReadPlayers();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }, 0L, 120L);
    }

    public void autoSendPlayers() throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("serverName", Main.instance.serverName);
        jsonObject.put("players", IslandManager.instance.getUUIDs());
        HTTPUtils.postMethod("players", jsonObject.toJSONString());
    }

    public void autoReadPlayers() {
        ArrayList<String> returned = HTTPUtils.readFromUrl("players/get");
        if (returned != null) {
            JSONParser jsonParser = new JSONParser();
            for (String str : returned) {
                try {
                    JSONObject jsonObject = (JSONObject) jsonParser.parse(str);
                    stringJSONArrayHashMap.put((String) jsonObject.get("serverName"), (JSONArray) jsonObject.get("players"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void autoReadServers() {
        ArrayList<String> returned = HTTPUtils.readFromUrl("get/all");
        if (returned != null) {
            servers.clear();
            JSONParser jsonParser = new JSONParser();
            for (String str : returned) {
                try {
                    JSONObject jsonObject = (JSONObject) jsonParser.parse(str);
                    servers.put((String) jsonObject.get("nom"), ServerType.valueOf((String) jsonObject.get("serverType")));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
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
