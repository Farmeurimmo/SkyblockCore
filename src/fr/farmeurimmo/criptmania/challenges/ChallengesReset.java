package fr.farmeurimmo.criptmania.challenges;

import java.util.Calendar;
import java.util.TimeZone;

import org.bukkit.Bukkit;

import fr.farmeurimmo.criptmania.Main;

public class ChallengesReset {
	
	@SuppressWarnings("deprecation")
	public static void CheckForReset() {
		TimeZone.setDefault(TimeZone.getTimeZone("Europe/Paris"));
		Calendar calendar = Calendar.getInstance();
		if(calendar.getTime().getHours() == 0 && calendar.getTime().getMinutes() == 0 &&
				calendar.getTime().getSeconds() == 0) {
			ResetAllChallenges();
		}
		Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
		     public void run() {
		    	 CheckForReset();
		     }
		}, 20);
	}
	public static void ResetAllChallenges() {
		for(String aa : Main.instance1.getData().getConfigurationSection("Joueurs").getKeys(false)) {
			Main.instance1.getData().set("Joueurs."+aa+".Challenges.Daily.1.Active", true);
			Main.instance1.getData().set("Joueurs."+aa+".Challenges.Daily.1.Progression", 0);
		}
		Main.instance1.saveData();
		Bukkit.broadcastMessage("�6�lChallenges �8� �fTous les challenges journaliers ont �t� r�initialis� !");
	}
}
