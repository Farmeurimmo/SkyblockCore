package main.java.fr.verymc.eco;

import main.java.fr.verymc.core.Main;
import main.java.fr.verymc.utils.Maths;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

public class BaltopManager {

    public static void GetBaltop(Player player, int page, int max) {
        Bukkit.getScheduler().scheduleAsyncDelayedTask(Main.instance, new Runnable() {
            public void run() {
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
                tosend = tosend + "\n§6----- §f§lBaltop §6----- ";
                for (int i = 1; i <= 10; i++) {
                    int toAdd = 10 * (page - 1);
                    if (!pos.containsKey((i + toAdd))) break;
                    Double aa = Maths.arrondiNDecimales(EcoAccountsManager.instance.getMoney(Bukkit.getOfflinePlayer(pos.get((i + toAdd))).getName()), 2);
                    tosend = tosend + "\n§f" + (i + toAdd) + ". §6" + Bukkit.getOfflinePlayer(pos.get((i + toAdd))).getName() + ": " +
                            aa + "$";
                }
                long resulttime = System.currentTimeMillis() - millisstart;
                tosend = tosend + "\n§6--- §f§lPage " + page + " §6---" + " Généré en " + resulttime + "ms";
                player.sendMessage(tosend);
                pos.clear();
            }
        }, 0);
    }
}
