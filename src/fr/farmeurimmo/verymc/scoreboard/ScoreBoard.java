package fr.farmeurimmo.verymc.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.iridium.iridiumskyblock.api.IridiumSkyblockAPI;

import fr.farmeurimmo.verymc.core.Main;
import fr.farmeurimmo.verymc.eco.EcoAccountsManager;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;

public class ScoreBoard implements Listener {
	
	static LuckPerms api;
	public static Main instance;
	static String Grade = "";
	
	public static void setScoreBoard(Player player){
		Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective obj = board.registerNewObjective("VeryMc", "dummy");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		obj.setDisplayName("�6�lVery�f�lMc");
		
		User user = LuckPermsProvider.get().getUserManager().getUser(player.getName());
		if (user.getCachedData().getMetaData().getPrefix() != null) {
			Grade = user.getCachedData().getMetaData().getPrefix().replace("&", "�");
		}
		
		
		obj.getScore("�6�l" + player.getName()).setScore(14);
		obj.getScore(" �eplay.verymc.fr").setScore(1);
		obj.getScore("�a").setScore(15);
		obj.getScore("�l").setScore(11);
		obj.getScore("�6�lIle").setScore(10);
		obj.getScore("�o").setScore(4);
		obj.getScore("�d").setScore(2);


		Team rank = board.registerNewTeam("rank");
		Team money = board.registerNewTeam("money");
		Team gradeis = board.registerNewTeam("gradeis");
		Team classementis = board.registerNewTeam("classementis");
		Team ismembre = board.registerNewTeam("ismembre");
		Team iscrystaux = board.registerNewTeam("iscrystaux");
		Team ismoney = board.registerNewTeam("ismoney");
		Team online = board.registerNewTeam("online");
		
		
		online.addEntry("�k");
		ismoney.addEntry("�9");
		iscrystaux.addEntry("�8");
		ismembre.addEntry("�7");
		classementis.addEntry("�6");
		gradeis.addEntry("�5");
		money.addEntry("�3");
		rank.addEntry("�2");
		
		
		obj.getScore("�k").setScore(3);
		obj.getScore("�9").setScore(6);
		obj.getScore("�8").setScore(7);
		obj.getScore("�7").setScore(5);
		obj.getScore("�6").setScore(8);
		obj.getScore("�5").setScore(9);
		obj.getScore("�3").setScore(12);
		obj.getScore("�2").setScore(13);


		player.setScoreboard(board);
	}

	public static void updateScoreBoard(){
		for(Player player : Bukkit.getOnlinePlayers()) {
		Scoreboard board = player.getScoreboard();
		
		Grade = "�7N/A";
		User user = LuckPermsProvider.get().getUserManager().getUser(player.getName());
		if (user.getCachedData().getMetaData().getPrefix() != null) {
			Grade = user.getCachedData().getMetaData().getPrefix().replace("&", "�");
		}
		else {
			Grade = "�7N/A";
		}
		
		if(board.getTeam("rank") != null) {
			board.getTeam("rank").setPrefix("�fGrade �8� " + Grade);
		} if(board.getTeam("money") != null) {
			Double a = EcoAccountsManager.Moneys.get(player.getName());
			board.getTeam("money").setPrefix("�fArgent �8� �e" + a);
		} if(board.getTeam("online") != null) {
			board.getTeam("online").setPrefix("�fSkyblock �8� �c" + Bukkit.getServer().getOnlinePlayers().size());
		}
			
			if(IridiumSkyblockAPI.getInstance().getUser(player).getIsland().isPresent()) {
				String Gradeis = "N/a";
				Gradeis = IridiumSkyblockAPI.getInstance().getUser(player).getIslandRank().name();	
				int classement = 0;
				classement = IridiumSkyblockAPI.getInstance().getUser(player).getIsland().get().getRank();
				int ismembre = 0;
				ismembre = IridiumSkyblockAPI.getInstance().getUser(player).getIsland().get().getMembers().size();
				int iscristal = 0;
				iscristal = IridiumSkyblockAPI.getInstance().getUser(player).getIsland().get().getCrystals();
				double ismoney = 0;
				ismoney = IridiumSkyblockAPI.getInstance().getUser(player).getIsland().get().getMoney();
				
				if(Gradeis.equalsIgnoreCase("OWNER")) {
					Gradeis = "Chef";
				}
				if(Gradeis.equalsIgnoreCase("CO-OWNER")) {
					Gradeis = "Co-Chef";
				}
				if(Gradeis.equalsIgnoreCase("MODERATOR")) {
					Gradeis = "Modérateur";
				}
				if(Gradeis.equalsIgnoreCase("MEMBER")) {
					Gradeis = "Membre";
				}
				
				board.getTeam("gradeis").setPrefix("�fGrade d'ile �8� �a" + Gradeis);
				board.getTeam("classementis").setPrefix("�fClassement �8� �a#" + classement);
				board.getTeam("ismembre").setPrefix("�fMembres �8� �a" + ismembre);
				board.getTeam("iscrystaux").setPrefix("�fCrystaux �8� �a" + iscristal);
				board.getTeam("ismoney").setPrefix("�fArgent �8� �a" + ismoney);
				
			} else {
				board.getTeam("gradeis").setPrefix("�fGrade d'ile �8� �aN/A");
				board.getTeam("classementis").setPrefix("�fClassement �8� �aN/A");
				board.getTeam("ismembre").setPrefix("�fMembres �8� �aN/A");
				board.getTeam("iscrystaux").setPrefix("�fCrystaux �8� �aN/A");
				board.getTeam("ismoney").setPrefix("�fArgent �8� �aN/A");
			}
		}
		Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
			public void run() {
				updateScoreBoard();
			}
		}, 20);

	}
}
