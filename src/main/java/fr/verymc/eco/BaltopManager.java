package main.java.fr.verymc.eco;

import main.java.fr.verymc.utils.Maths;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class BaltopManager {

    public static void GetBaltop(Player player, int page, int max) {
        long millisstart = System.currentTimeMillis();
        String tosend = "§7Classement de §6" + EcoAccountsManager.instance.Moneys.size() + "§7 joueurs en cours...";
        HashMap<Integer, UUID> pos = new HashMap<>();
        int current = max - 10 + 1;
        int maxi = page * 10;
        int position = 1;
        while (current <= maxi) {
            Double bestvalue = (double) 0;
            String playerlayer = "N/A";
            for (Entry<UUID, Double> aa : EcoAccountsManager.instance.Moneys.entrySet()) {
                if (pos.containsValue(aa.getKey())) {
                    continue;
                }
                if (aa.getValue() > bestvalue) {
                    bestvalue = aa.getValue();
                    pos.remove(playerlayer);
                    playerlayer = String.valueOf(aa.getKey());
                    pos.put(position, aa.getKey());
                }
            }
            if (playerlayer.equalsIgnoreCase("N/A")) break;
            pos.put(position, UUID.fromString(playerlayer));
            current += 1;
            position += 1;
        }
        //int TotalPlayers = EcoAccountsManager.Moneys.size();
        //int TotalPage = TotalPlayers/10+1;
        tosend = tosend + "\n§6----- §f§lBaltop §6----- ";
        for (int i = 1; i <= 10; i++) {
            if (!pos.containsKey(i)) break;
            tosend = tosend + "\n§f" + i + ". §6" + Bukkit.getOfflinePlayer(pos.get(i)).getName() + ": " +
                    Maths.arrondiNDecimales(EcoAccountsManager.instance.GetMoney(Bukkit.getOfflinePlayer(pos.get(i)).getName()), 2)+"$";
        }
        long resulttime = System.currentTimeMillis() - millisstart;
        tosend = tosend + "\n§6--- §f§lPage " + page + " §6---" + " Généré en " + TimeUnit.MILLISECONDS.toSeconds(resulttime) + "s";
        player.sendMessage(tosend);
        pos.clear();
    }
}
