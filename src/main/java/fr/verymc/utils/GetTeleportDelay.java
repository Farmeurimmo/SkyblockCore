package main.java.fr.verymc.utils;

import org.bukkit.entity.Player;

public class GetTeleportDelay {

    public static int GetPlayerTeleportingdelay(Player player) {
        int time = 0;
        if (player.hasPermission("*")) {
            return time;
        } else if (player.hasPermission("zeus")) {
            return time;
        } else if (player.hasPermission("dieu")) {
            return time + 1;
        } else if (player.hasPermission("legende")) {
            return time + 3;
        } else {
            return time + 5;
        }
    }
}
