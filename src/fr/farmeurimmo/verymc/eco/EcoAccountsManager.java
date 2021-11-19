package fr.farmeurimmo.verymc.eco;

import java.util.HashMap;

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
	public static boolean IsExisting(String player) {
		boolean a = false;
		if(Main.instance1.getDataz().get(player) != null) {
			a = true;
		}
		return a;
	}
	public static int GetMoney(String a) {
		int b = 0;
		if(Moneys.containsKey(a)) {
			b = Moneys.get(a);
		}
		return b;
	}
	public static boolean CheckForFounds(Player player, int needed) {
		boolean aa = false;
		if(Moneys.get(player.getName()) >= needed) {
			aa = true;
		}
		return aa;
	}
	public static void RemoveFounds(String player, int toremove) {
		int moneybefore = Moneys.get(player);
		if(moneybefore - toremove >= 0) {
		int now = moneybefore - toremove;
		Moneys.put(player, now);
		Main.instance1.getDataz().set(player, now);
		Main.instance1.saveData();
		if(Bukkit.getPlayer(player) != null) {
			Bukkit.getPlayer(player).sendMessage("§6§lMonnaie §8» §6"+toremove+"$§f ont été §cretiré§f de votre compte.");
		}
		} else {
			if(Bukkit.getPlayer(player) != null) {
			Bukkit.getPlayer(player).sendMessage("§6§lMonnaie §8» §6"+toremove+"$§f n'ont pas pu être §cretiré§f de votre compte.");
			}
		}
	}
	public static void SetFounds(String player, int toset) {
		Moneys.put(player, toset);
		Main.instance1.getDataz().set(player, toset);
		Main.instance1.saveData();
		if(Bukkit.getPlayer(player) != null) {
			Bukkit.getPlayer(player).sendMessage("§6§lMonnaie §8» §fVotre argent a été §adéfinis§f sur §6"+toset+"$§f.");
		}
	}
	public static void AddFounds(String player, int toadd) {
		int moneybefore = Moneys.get(player);
		if(moneybefore < 2147483647 - toadd) {
		int now = Moneys.get(player) + toadd;
		Moneys.put(player, now);
		Main.instance1.getDataz().set(player, now);
		Main.instance1.saveData();
		if(Bukkit.getPlayer(player) != null) {
		Bukkit.getPlayer(player).sendMessage("§6§lMonnaie §8» §6"+toadd+"$§f ont été §aajouté§f à votre compte.");
		}
		} else {
			if(Bukkit.getPlayer(player) != null) {
			Bukkit.getPlayer(player).sendMessage("§6§lMonnaie §8» §6"+toadd+"$§f n'ont pas pu être §aajouté§f à votre compte.");
			}
		}
	}
	public static void UpdateHash() {
		for(String aa : Main.instance1.getDataz().getConfigurationSection("").getKeys(false)) {
			Moneys.put(aa, Main.instance1.getDataz().getInt(aa));
		}
	}
}
