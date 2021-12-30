package fr.farmeurimmo.verymc.events;

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
                    , "craft", "enchantement", "wiki", "money", "baltop", "bar", "tpa", "tpacancel", "tpyes", "tpno", "pay", "challenges", "c", "atout",
                    "atouts", "chatreactionsuggest"));
        }
    }
}
