package main.java.fr.verymc.velocity;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import main.java.fr.verymc.JedisManager;
import main.java.fr.verymc.velocity.team.DungeonTeam;
import net.kyori.adventure.text.Component;
import org.json.simple.JSONObject;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class DungeonQueueManager {

    public static final int MAX_QUEUE_TIME = 30;
    public static DungeonQueueManager instance;
    private final ProxyServer server;
    private final Logger logger;
    public LinkedList<DungeonTeam> dungeonQueue = new LinkedList<>();
    public String old = "";
    public int old_num = 0;

    public DungeonQueueManager(ProxyServer server, Logger logger) {
        instance = this;

        this.server = server;
        this.logger = logger;

        queueManager();
    }

    public void queueManager() {
        server.getScheduler()
                .buildTask(Main.instance, () -> {
                    if (dungeonQueue.size() == 0) {
                        queueManager();
                        return;
                    }
                    for (DungeonTeam dungeonTeam1 : dungeonQueue) {
                        int pos = dungeonQueue.indexOf(dungeonTeam1);
                        for (Player player : dungeonTeam1.getPlayers()) {
                            player.sendActionBar(Component.text("§7Position dans la file: §6" + (pos + 1) + " §7sur §6" + dungeonQueue.size()));
                        }
                    }
                    String oldTemp = JedisManager.instance.getFromRedis("tmpDungeonTeam");
                    if (oldTemp != null) {
                        if (oldTemp.equals(old)) {
                            old = oldTemp;
                            old_num++;
                        } else {
                            old = oldTemp;
                            old_num = 0;
                        }
                        if (old_num > MAX_QUEUE_TIME) {
                            old = "";
                            old_num = 0;
                            JedisManager.instance.removeFromRedis("tmpDungeonTeam");
                            queueManager();
                            return;
                        }
                    }
                    DungeonTeam dungeonTeam = dungeonQueue.getFirst();
                    if (dungeonTeam != null) {
                        connectToDungeon(dungeonTeam);
                        dungeonQueue.removeFirst();
                    }
                    queueManager();
                })
                .delay(1L, TimeUnit.SECONDS)
                .schedule();
    }

    public void queueTeam(DungeonTeam dungeonTeam) {
        Optional<Player> player = server.getPlayer(dungeonTeam.getOwner());
        if (dungeonQueue.contains(dungeonTeam)) {
            if (player.isPresent()) {
                player.get().sendMessage(Component.text("§6§lDongeons §8» §cVous êtes déjà dans la file d'attente !"));
            }
            return;
        }
        dungeonTeam.sendMessageToEveryone("§6§lDongeons §8» §fVous venez de rentrer dans la file d'attente des dongeons.");
        if (player.isPresent() && !player.get().hasPermission("dongeon.fast")) {
            dungeonQueue.addLast(dungeonTeam);
        } else {
            dungeonQueue.addFirst(dungeonTeam);
        }
    }

    public void connectToDungeon(DungeonTeam dungeonTeam) {
        JSONObject jsonObject = new JSONObject();
        String serverName = "lobby2";
        ArrayList<String> playerNames = new ArrayList<>();
        RegisteredServer registeredServer = Main.instance.getServerByName(serverName);
        System.out.println(serverName);
        System.out.println(registeredServer);
        dungeonTeam.sendMessageToEveryone("§6§lDongeons §8» §fDongeon démarré, en attente du serveur...");
        for (Player player1 : dungeonTeam.getPlayers()) {
            playerNames.add(player1.getUsername());
            player1.createConnectionRequest(registeredServer).fireAndForget();
        }
        jsonObject.put("players", playerNames);
        jsonObject.put("floor", dungeonTeam.getFloor().toString());
        JedisManager.instance.sendToRedis("tmpDungeonTeam", jsonObject.toString());
    }


}
