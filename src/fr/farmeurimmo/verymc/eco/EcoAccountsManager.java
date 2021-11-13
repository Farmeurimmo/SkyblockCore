package fr.farmeurimmo.verymc.eco;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.farmeurimmo.verymc.core.Main;

public class EcoAccountsManager {
	
	public static HashMap <String, Integer > Moneys = new HashMap < > ();
	
	public static void CheckForAccount(Player player) {
		if(Main.instance1.getDataz().get(player.getName()) == null){
			Main.instance1.getDataz().set(player.getName(), 200);
			Main.instance1.saveData();
			Moneys.put(player.getName(), Main.instance1.getDataz().getInt(player.getName()));
		}
	}
	public static boolean CheckForFounds(Player player, int needed) {
		boolean aa = false;
		if(Moneys.get(player.getName()) >= needed) {
			aa = true;
		}
		return aa;
	}
	public static void RemoveFounds(Player player, int toremove) {
		int now = Moneys.get(player.getName()) - toremove;
		Moneys.put(player.getName(), now);
		player.sendMessage("§6§lMonnaie §8» §6"+toremove+"$§f ont été §cenlevé§f de votre compte.");
	}
	public static void AddFounds(Player player, int toadd) {
		int now = Moneys.get(player.getName()) + toadd;
		Moneys.put(player.getName(), now);
		player.sendMessage("§6§lMonnaie §8» §6"+toadd+"$§f ont été §aajouté§f de votre compte.");
	}
	@SuppressWarnings("deprecation")
	public static void UpdateHash() {
		for(Entry<String, Integer> aaa : Moneys.entrySet()) {
			Main.instance1.getDataz().set(aaa.getKey(), aaa.getValue());
		}
		Main.instance1.saveData();
		for(String aa : Main.instance1.getDataz().getConfigurationSection("").getKeys(false)) {
			Moneys.put(aa, Main.instance1.getDataz().getInt(aa));
		}
		
		
		Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
			public void run() {
				UpdateHash();
			}
		}, 900);
	}
}
