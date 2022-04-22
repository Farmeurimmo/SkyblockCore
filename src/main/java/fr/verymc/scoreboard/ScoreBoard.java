package main.java.fr.verymc.scoreboard;

import main.java.fr.verymc.Main;
import main.java.fr.verymc.eco.EcoAccountsManager;
import main.java.fr.verymc.evenement.EventManager;
import main.java.fr.verymc.island.Island;
import main.java.fr.verymc.island.IslandManager;
import main.java.fr.verymc.island.challenges.IslandChallengesReset;
import main.java.fr.verymc.island.guis.IslandTopGui;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

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
        obj.setDisplayName("§6VeryMc");

        User user = LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId());
        if (user.getCachedData().getMetaData().getPrefix() != null) {
            Grade = user.getCachedData().getMetaData().getPrefix().replace("&", "§");
        }

        obj.getScore("§6go.verymc.fr").setScore(1);
        obj.getScore("§a").setScore(15);
        obj.getScore("§l").setScore(12);
        obj.getScore("§o").setScore(6);
        obj.getScore("§d").setScore(2);

        Team rank = board.registerNewTeam("rank");
        Team money = board.registerNewTeam("money");
        Team gradeis = board.registerNewTeam("gradeis");
        Team classementis = board.registerNewTeam("classementis");
        Team ismembre = board.registerNewTeam("ismembre");
        Team iscrystaux = board.registerNewTeam("iscrystaux");
        Team ismoney = board.registerNewTeam("ismoney");
        Team online = board.registerNewTeam("online");
        Team challenges = board.registerNewTeam("challenges");
        Team event = board.registerNewTeam("event");

        online.addEntry("§k");
        ismoney.addEntry("§9");
        iscrystaux.addEntry("§8");
        ismembre.addEntry("§7");
        classementis.addEntry("§6");
        gradeis.addEntry("§5");
        money.addEntry("§3");
        rank.addEntry("§2");
        challenges.addEntry("§4");
        event.addEntry("§a");

        obj.getScore("§k").setScore(3);
        obj.getScore("§a").setScore(4);
        obj.getScore("§4").setScore(5);
        obj.getScore("§9").setScore(7);
        obj.getScore("§8").setScore(8);
        obj.getScore("§7").setScore(9);
        obj.getScore("§6").setScore(10);
        obj.getScore("§5").setScore(11);
        obj.getScore("§3").setScore(13);
        obj.getScore("§2").setScore(14);

        player.setScoreboard(board);
    }

    public void updateScoreBoard() {
        CompletableFuture.runAsync(() -> {
            ArrayList<String> Vanished = new ArrayList<String>();
            Vanished.clear();
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (!p.getMetadata("vanished").isEmpty()) {
                    Vanished.add(p.getName());
                }
            }
            long timeBeforeReset = IslandChallengesReset.instance.getTimeBeforeReset();
            String messagetimeleft = EventManager.instance.returnFormattedTime((int) TimeUnit.MILLISECONDS.toSeconds(timeBeforeReset));

            String nextEvent = EventManager.instance.getCurrentEventRandom();

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
                        board.getTeam("rank").setPrefix("§7Grade: " + Grade);
                    } else {
                        board.getTeam("rank").setPrefix("§7Grade: ");
                    }
                }
                if (board.getTeam("money") != null) {
                    String a = EcoAccountsManager.instance.moneyGetarrondiNDecimales(player);
                    board.getTeam("money").setPrefix("§7Argent: §2" + a);
                }
                if (board.getTeam("online") != null) {
                    board.getTeam("online").setPrefix("§7Skyblock: §c" + online);
                }

                Island island = IslandManager.instance.getPlayerIsland(player);
                if (island != null) {
                    String Gradeis = island.getIslandRankFromUUID(player.getUniqueId()).getName();
                    String classement = "#N/A";
                    if (IslandTopGui.instance.getTopIsland().containsKey(island)) {
                        classement = "#" + IslandTopGui.instance.getTopIsland().get(island);
                    }
                    int ismembre = island.getMembers().size();
                    double iscristal = island.getBank().getCrystaux();
                    double ismoney = island.getBank().getMoney();
                    int maxMembers = island.getMaxMembers();


                    if (board.getTeam("gradeis") != null)
                        board.getTeam("gradeis").setPrefix("§7Grade d'ile: §6" + Gradeis);
                    if (board.getTeam("classementis") != null)
                        board.getTeam("classementis").setPrefix("§7Classement: §2" + classement);
                    if (board.getTeam("ismembre") != null)
                        board.getTeam("ismembre").setPrefix("§7Membres: §3" + ismembre + "/" + maxMembers);
                    if (board.getTeam("iscrystaux") != null)
                        board.getTeam("iscrystaux").setPrefix("§7Crystaux: §d" + DecimalFormat.getNumberInstance().format(iscristal));
                    if (board.getTeam("ismoney") != null) board.getTeam("ismoney").setPrefix("§7Argent: §d" +
                            DecimalFormat.getNumberInstance().format(ismoney));

                } else {
                    if (board.getTeam("gradeis") != null) board.getTeam("gradeis").setPrefix("§7Grade d'ile: §6N/A");
                    if (board.getTeam("classementis") != null)
                        board.getTeam("classementis").setPrefix("§7Classement: §2N/A");
                    if (board.getTeam("ismembre") != null) board.getTeam("ismembre").setPrefix("§7Membres: §3N/A");
                    if (board.getTeam("iscrystaux") != null) board.getTeam("iscrystaux").setPrefix("§7Crystaux: §dN/A");
                    if (board.getTeam("ismoney") != null) board.getTeam("ismoney").setPrefix("§7Argent: §dN/A");
                }
                if (board.getTeam("challenges") != null) {
                    board.getTeam("challenges").setPrefix("§7Reset /c: §c" + messagetimeleft);
                }
                if (board.getTeam("event") != null) {
                    board.getTeam("event").setPrefix(nextEvent);
                }
            }
            Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(Main.instance, new Runnable() {
                public void run() {
                    updateScoreBoard();
                }
            }, 20);
        });
    }
}
