package main.java.fr.verymc.scoreboard;

import main.java.fr.verymc.Main;
import main.java.fr.verymc.eco.EcoAccountsManager;
import main.java.fr.verymc.island.Island;
import main.java.fr.verymc.island.IslandManager;
import main.java.fr.verymc.island.guis.IslandTopGui;
import main.java.fr.verymc.utils.Maths;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;

public class ScoreBoard implements Listener {

    public static ScoreBoard acces;
    static String Grade = "";


    public ScoreBoard() {
        updateScoreBoard();
        acces = this;
    }

    public void setScoreBoard(Player player) {
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = board.registerNewObjective("VeryMc", "dummy");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName("§6§lVery§f§lMc");

        User user = LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId());
        if (user.getCachedData().getMetaData().getPrefix() != null) {
            Grade = user.getCachedData().getMetaData().getPrefix().replace("&", "§");
        }

        obj.getScore("§6§l" + player.getName()).setScore(14);
        obj.getScore(" §eplay.verymc.fr").setScore(1);
        obj.getScore("§a").setScore(15);
        obj.getScore("§l").setScore(11);
        obj.getScore("§6§lIle").setScore(10);
        obj.getScore("§o").setScore(4);
        obj.getScore("§d").setScore(2);

        Team rank = board.registerNewTeam("rank");
        Team money = board.registerNewTeam("money");
        Team gradeis = board.registerNewTeam("gradeis");
        Team classementis = board.registerNewTeam("classementis");
        Team ismembre = board.registerNewTeam("ismembre");
        Team iscrystaux = board.registerNewTeam("iscrystaux");
        Team ismoney = board.registerNewTeam("ismoney");
        Team online = board.registerNewTeam("online");

        online.addEntry("§k");
        ismoney.addEntry("§9");
        iscrystaux.addEntry("§8");
        ismembre.addEntry("§7");
        classementis.addEntry("§6");
        gradeis.addEntry("§5");
        money.addEntry("§3");
        rank.addEntry("§2");

        obj.getScore("§k").setScore(3);
        obj.getScore("§9").setScore(6);
        obj.getScore("§8").setScore(7);
        obj.getScore("§7").setScore(5);
        obj.getScore("§6").setScore(8);
        obj.getScore("§5").setScore(9);
        obj.getScore("§3").setScore(12);
        obj.getScore("§2").setScore(13);

        player.setScoreboard(board);
    }

    public void updateScoreBoard() {
        ArrayList<String> Vanished = new ArrayList<String>();
        Vanished.clear();
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!p.getMetadata("vanished").isEmpty()) {
                Vanished.add(p.getName());
            }
        }
        int online = Bukkit.getOnlinePlayers().size() - Vanished.size();
        for (Player player : Bukkit.getOnlinePlayers()) {
            Scoreboard board = player.getScoreboard();

            Grade = "§fN/A";
            User user = LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId());
            if (user.getCachedData().getMetaData().getPrefix() != null) {
                Grade = user.getCachedData().getMetaData().getPrefix().replace("&", "§");
            } else {
                Grade = "§fN/A";
            }

            if (board.getTeam("rank") != null) {
                if (Grade.length() < 64) {
                    board.getTeam("rank").setPrefix("§fGrade §8▸ " + Grade);
                } else {
                    board.getTeam("rank").setPrefix("§fGrade §8▸ ");
                }
            }
            if (board.getTeam("money") != null) {
                String a = Maths.reducedNumberWithLetter(EcoAccountsManager.instance.moneyGetarrondiNDecimales(player, 1));
                board.getTeam("money").setPrefix("§fArgent §8▸ §e" + a);
            }
            if (board.getTeam("online") != null) {
                board.getTeam("online").setPrefix("§fSkyblock §8▸ §c" + online);
            }

            Island island = IslandManager.instance.getPlayerIsland(player);
            if (island != null) {
                String Gradeis = island.getMembers().get(player.getUniqueId()).getName();
                String classement = "#N/A";
                if (IslandTopGui.instance.getTopIsland().containsKey(island)) {
                    classement = "#" + IslandTopGui.instance.getTopIsland().get(island);
                }
                int ismembre = island.getMembers().size();
                double iscristal = island.getBank().getCrystaux();
                double ismoney = island.getBank().getMoney();
                int maxMembers = island.getMaxMembers();

                if (board.getTeam("gradeis") != null)
                    board.getTeam("gradeis").setPrefix("§fGrade d'ile §8▸ §a" + Gradeis);
                if (board.getTeam("classementis") != null)
                    board.getTeam("classementis").setPrefix("§fClassement §8▸ §a" + classement);
                if (board.getTeam("ismembre") != null)
                    board.getTeam("ismembre").setPrefix("§fMembres §8▸ §a" + ismembre + "/" + maxMembers);
                if (board.getTeam("iscrystaux") != null)
                    board.getTeam("iscrystaux").setPrefix("§fCrystaux §8▸ §a" + iscristal);
                if (board.getTeam("ismoney") != null) board.getTeam("ismoney").setPrefix("§fArgent §8▸ §a" + ismoney);

            } else {
                if (board.getTeam("gradeis") != null) board.getTeam("gradeis").setPrefix("§fGrade d'ile §8▸ §aN/A");
                if (board.getTeam("classementis") != null)
                    board.getTeam("classementis").setPrefix("§fClassement §8▸ §aN/A");
                if (board.getTeam("ismembre") != null) board.getTeam("ismembre").setPrefix("§fMembres §8▸ §aN/A");
                if (board.getTeam("iscrystaux") != null) board.getTeam("iscrystaux").setPrefix("§fCrystaux §8▸ §aN/A");
                if (board.getTeam("ismoney") != null) board.getTeam("ismoney").setPrefix("§fArgent §8▸ §aN/A");
            }
        }
        Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(Main.instance, new Runnable() {
            public void run() {
                updateScoreBoard();
            }
        }, 20);

    }
}
