package fr.farmeurimmo.verymc.eco;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.farmeurimmo.verymc.core.Main;

public class EcoAccountsManager {
	
	public static HashMap <String, Float > Moneys = new HashMap < > ();
	
	public static void CheckForAccount(Player player) {
		if(Main.instance1.getDataz().get(player.getName()) == null){
			Main.instance1.getDataz().set(player.getName(), 200);
			Main.instance1.saveData();
			Moneys.put(player.getName(), (Float) Main.instance1.getDataz().get(player.getName()));
		}
	}
	public static boolean IsExisting(String player) {
		boolean a = false;
		if(Main.instance1.getDataz().get(player) != null) {
			a = true;
		}
		return a;
	}
	public static Float GetMoney(String a) {
		Float b = (float) 0;
		if(Moneys.containsKey(a)) {
			b = Moneys.get(a);
		}
		return b;
	}
	public static boolean CheckForFounds(Player player, float needed) {
		boolean aa = false;
		Float moneyplayer = Moneys.get(player.getName());
		float after = moneyplayer-needed;
		if(after >= 0) {
			aa = true;
		}
		return aa;
	}
	public static void RemoveFounds(String player, float toremove) {
		Float moneybefore = Moneys.get(player);
		if(moneybefore - toremove >= 0) {
		float now = moneybefore - toremove;
		Moneys.put(player, now);
		Main.instance1.getDataz().set(player, now);
		Main.instance1.saveData();
		if(Bukkit.getPlayer(player) != null) {
			Bukkit.getPlayer(player).sendMessage("�6�lMonnaie �8� �6"+toremove+"$�f ont �t� �cretir�f de votre compte.");
		}
		} else {
			if(Bukkit.getPlayer(player) != null) {
			Bukkit.getPlayer(player).sendMessage("�6�lMonnaie �8� �6"+toremove+"$�f n'ont pas pu �tre �cretir�f de votre compte.");
			}
		}
	}
	public static void SetFounds(String player, float aaa) {
		Moneys.put(player, aaa);
		Main.instance1.getDataz().set(player, aaa);
		Main.instance1.saveData();
		if(Bukkit.getPlayer(player) != null) {
			Bukkit.getPlayer(player).sendMessage("�6�lMonnaie �8� �fVotre argent a �t� �ad�finis�f sur �6"+aaa+"$�f.");
		}
	}
	public static void AddFounds(String player, float toadd) {
		Float moneybefore = Moneys.get(player);
		if(moneybefore < 2147483647 - toadd) {
		float now = Moneys.get(player) + toadd;
		Moneys.put(player, now);
		Main.instance1.getDataz().set(player, now);
		Main.instance1.saveData();
		if(Bukkit.getPlayer(player) != null) {
		Bukkit.getPlayer(player).sendMessage("�6�lMonnaie �8� �6"+toadd+"$�f ont �t� �aajout�f � votre compte.");
		}
		} else {
			if(Bukkit.getPlayer(player) != null) {
			Bukkit.getPlayer(player).sendMessage("�6�lMonnaie �8� �6"+toadd+"$�f n'ont pas pu �tre �aajout�f � votre compte.");
			}
		}
	}
	public static void UpdateHash() {
		for(String aa : Main.instance1.getDataz().getConfigurationSection("").getKeys(false)) {
			Moneys.put(aa, (float) Main.instance1.getDataz().getInt(aa));
		}
	}
}
