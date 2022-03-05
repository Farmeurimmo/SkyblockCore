package main.java.fr.verymc.scoreboard;

import main.java.fr.verymc.core.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class TABManager {

    public static ArrayList<String> Vanished = new ArrayList<String>();

    public TABManager() {
        SendTab();
    }

    public void SendTab() {
        Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(Main.instance, new Runnable() {
            public void run() {
                Vanished.clear();
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (!p.getMetadata("vanished").isEmpty()) {
                        Vanished.add(p.getName());
                    }
                }
                int online = Bukkit.getOnlinePlayers().size() - Vanished.size();
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.setPlayerListHeaderFooter("\n§f• §6§lVery§f§lMc §f•\n\n§7Connecté(s) §7▸ §a" + online +
                                    "\n§7Une question/problème ? contacte un §9§lSTAFF §7!\n",
                            "\n§7Serveur Mini-Jeux Francophone\n§7Vous êtes sur ▸ §fplay.§6§lvery§f§lmc§f.fr §7◂");
                }
                SendTab();
            }
        }, 20);
    }
}
