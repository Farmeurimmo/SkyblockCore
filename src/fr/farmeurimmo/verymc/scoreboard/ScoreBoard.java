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
		obj.setDisplayName("ß6ßlVeryßfßlMc");
		
		User user = LuckPermsProvider.get().getUserManager().getUser(player.getName());
		if (user.getCachedData().getMetaData().getPrefix() != null) {
			Grade = user.getCachedData().getMetaData().getPrefix().replace("&", "ß");
		}
		
		
		obj.getScore("ß6ßl" + player.getName()).setScore(14);
		obj.getScore(" ßeplay.verymc.fr").setScore(1);
		obj.getScore("ßa").setScore(15);
		obj.getScore("ßl").setScore(11);
		obj.getScore("ß6ßlIle").setScore(10);
		obj.getScore("ßo").setScore(4);
		obj.getScore("ßd").setScore(2);


		Team rank = board.registerNewTeam("rank");
		Team money = board.registerNewTeam("money");
		Team gradeis = board.registerNewTeam("gradeis");
		Team classementis = board.registerNewTeam("classementis");
		Team ismembre = board.registerNewTeam("ismembre");
		Team iscrystaux = board.registerNewTeam("iscrystaux");
		Team ismoney = board.registerNewTeam("ismoney");
		Team online = board.registerNewTeam("online");
		
		
		online.addEntry("ßk");
		ismoney.addEntry("ß9");
		iscrystaux.addEntry("ß8");
		ismembre.addEntry("ß7");
		classementis.addEntry("ß6");
		gradeis.addEntry("ß5");
		money.addEntry("ß3");
		rank.addEntry("ß2");
		
		
		obj.getScore("ßk").setScore(3);
		obj.getScore("ß9").setScore(6);
		obj.getScore("ß8").setScore(7);
		obj.getScore("ß7").setScore(5);
		obj.getScore("ß6").setScore(8);
		obj.getScore("ß5").setScore(9);
		obj.getScore("ß3").setScore(12);
		obj.getScore("ß2").setScore(13);


		player.setScoreboard(board);
	}

	public static void updateScoreBoard(){
		for(Player player : Bukkit.getOnlinePlayers()) {
		Scoreboard board = player.getScoreboard();
		
		Grade = "ß7N/A";
		User user = LuckPermsProvider.get().getUserManager().getUser(player.getName());
		if (user.getCachedData().getMetaData().getPrefix() != null) {
			Grade = user.getCachedData().getMetaData().getPrefix().replace("&", "ß");
		}
		else {
			Grade = "ß7N/A";
		}
		
		if(board.getTeam("rank") != null) {
			board.getTeam("rank").setPrefix("ßfGrade ß8ª " + Grade);
		} if(board.getTeam("money") != null) {
			Double a = EcoAccountsManager.Moneys.get(player.getName());
			board.getTeam("money").setPrefix("ßfArgent ß8ª ße" + a);
		} if(board.getTeam("online") != null) {
			board.getTeam("online").setPrefix("ßfSkyblock ß8ª ßc" + Bukkit.getServer().getOnlinePlayers().size());
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
					Gradeis = "Mod√©rateur";
				}
				if(Gradeis.equalsIgnoreCase("MEMBER")) {
					Gradeis = "Membre";
				}
				
				board.getTeam("gradeis").setPrefix("ßfGrade d'ile ß8ª ßa" + Gradeis);
				board.getTeam("classementis").setPrefix("ßfClassement ß8ª ßa#" + classement);
				board.getTeam("ismembre").setPrefix("ßfMembres ß8ª ßa" + ismembre);
				board.getTeam("iscrystaux").setPrefix("ßfCrystaux ß8ª ßa" + iscristal);
				board.getTeam("ismoney").setPrefix("ßfArgent ß8ª ßa" + ismoney);
				
			} else {
				board.getTeam("gradeis").setPrefix("ßfGrade d'ile ß8ª ßaN/A");
				board.getTeam("classementis").setPrefix("ßfClassement ß8ª ßaN/A");
				board.getTeam("ismembre").setPrefix("ßfMembres ß8ª ßaN/A");
				board.getTeam("iscrystaux").setPrefix("ßfCrystaux ß8ª ßaN/A");
				board.getTeam("ismoney").setPrefix("ßfArgent ß8ª ßaN/A");
			}
		}
		Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
			public void run() {
				updateScoreBoard();
			}
		}, 20);

	}
}
