package fr.farmeurimmo.criptmania.evenement;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.tnemc.core.TNE;
import net.tnemc.core.common.api.TNEAPI;

@SuppressWarnings("deprecation")
public class ChatReaction implements Listener {
	
	public static ArrayList<String> mots = new ArrayList<String>();
	static String aaa = null;
	static boolean bbb = false;
	
	public static void StartChatReaction() {
		Random rand = new Random();
        int n = rand.nextInt(mots.size());
        aaa = mots.get(n);
        bbb = false;
		TextComponent hmessage = new TextComponent("§6§lChatReaction §8» §fPassez votre souris ici pour voir le mot à recopier !");
        hmessage.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§fMot à recopier: §6" + aaa).create() ) );
        for(Player player : Bukkit.getOnlinePlayers()) {
        	player.spigot().sendMessage(hmessage);
        }
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
			public void run() {
				if(bbb = false) {
				Bukkit.broadcastMessage("§6§lChatReaction §8» §fPersonne n'a recopié le mot à temps !");
				} else {
				bbb = true;
				}
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
					public void run() {
						bbb = false;
						StartChatReaction();
					}
				}, 11400);
			}
		}, 600);
	}
	@EventHandler
	public void OnMessageSend(PlayerChatEvent e) {
		if(e.getMessage().equalsIgnoreCase(aaa)) {
			if(bbb == false) {
			Player player = e.getPlayer();
			TNEAPI ecoAPI = TNE.instance().api();
			
			bbb = true;
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
				public void run() {
					Bukkit.broadcastMessage("§6§lChatReaction §8» §f" + player.getName() + " vient de gagner le ChatReaction !");
					ecoAPI.getAccount(player.getName()).addHoldings(new BigDecimal(2000));
					player.sendMessage("§6§lChatReaction §8» §fVous avez reçu 2000$.");
				}
			}, 1);
		}
		}
	}

}
