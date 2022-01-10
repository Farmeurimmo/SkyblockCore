package main.java.fr.verymc.evenement;

import main.java.fr.verymc.eco.EcoAccountsManager;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ChatReaction implements Listener {

    public static ArrayList<String> mots = new ArrayList<String>();
    static String aaa = null;
    static boolean bbb = false;
    static long timestart = 0;
    static long timeend = 0;

    public static void WriteWords() {
        mots.clear();
        mots.addAll(Arrays.asList("VeryMc", "Skyblock", "Farmeurimmo", "Orthographe", "Minecraft", "Téléphone", "Développement", "Forteresse"));
    }

    public static void StartChatReaction() {
        Random rand = new Random();
        int n = rand.nextInt(mots.size());
        aaa = (String) mots.get(n);
        bbb = false;
        TextComponent hmessage = new TextComponent("§6§lChatReaction §8» §fPassez votre souris ici pour voir le mot à recopier !");
        hmessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§fMot à recopier: §6" + aaa).create()));
        timestart = System.currentTimeMillis();
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.spigot().sendMessage(hmessage);
        }
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
            public void run() {
                if (bbb == false) {
                    Bukkit.broadcastMessage("§6§lChatReaction §8» §fPersonne n'a recopié le mot à temps !");
                }
                bbb = true;
            }
        }, 600);
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
            public void run() {
                bbb = false;
                StartChatReaction();
            }
        }, 12000);
    }

    @EventHandler
    public void OnMessageSend(PlayerChatEvent e) {
        if (e.getMessage().equalsIgnoreCase(aaa)) {
            if (bbb == false) {
                Player player = e.getPlayer();

                bbb = true;

                timeend = System.currentTimeMillis();

                long timeelapsed = timeend - timestart;
                long secelasp = TimeUnit.MILLISECONDS.toSeconds(timeelapsed);
                long milliselasp = TimeUnit.SECONDS.toSeconds(timeelapsed);

                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
                    public void run() {
                        Bukkit.broadcastMessage("§6§lChatReaction §8» §f" + player.getName() + " vient de gagner le ChatReaction en " +
                                secelasp + "." + milliselasp + " secondes et remporte 2 000$ !");
                        EcoAccountsManager.instance.AddFounds(player.getName(), (double) 2000, false);
                    }
                }, 1);
            }
        }
    }

}
