package main.java.fr.verymc.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;

import java.util.Arrays;

public class Tabulation implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void OnTabulation(PlayerCommandSendEvent e) {
        if (!e.getPlayer().hasPermission("*")) {
            e.getCommands().clear();
            e.getCommands().addAll(Arrays.asList("is", "shop", "sellall", "warp", "warps", "menu", "farm2win", "spawn", "hat", "feed", "fly", "dailyfly"
                    , "craft", "enchantement", "money", "baltop", "tpa", "tpacancel", "tpyes", "tpno", "pay", "challenges", "c", "atout",
                    "atouts", "ah", "playerwarp","trade","tradeyes","tradeno"));
        }
    }
}
