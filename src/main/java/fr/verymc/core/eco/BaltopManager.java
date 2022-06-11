package main.java.fr.verymc.core.eco;

import main.java.fr.verymc.Main;
import main.java.fr.verymc.storage.SkyblockUser;
import main.java.fr.verymc.storage.SkyblockUserManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class BaltopManager {

    public static void GetBaltop(Player player, int page, int max) {
        Bukkit.getScheduler().scheduleAsyncDelayedTask(Main.instance, new Runnable() {
            public void run() {
                long millisstart = System.currentTimeMillis();
                String tosend = "§7Classement de §6" + SkyblockUserManager.instance.users.size() + "§7 joueurs en cours...";
                HashMap<Integer, SkyblockUser> users = new HashMap<Integer, SkyblockUser>();
                int current = max - 10 + 1;
                int maxi = page * 10;
                int position = 1;
                while (current <= maxi) {
                    Double bestvalue = (double) 0;
                    SkyblockUser playerlayer = null;
                    for (SkyblockUser user : SkyblockUserManager.instance.users) {
                        if (users.containsValue(user)) {
                            continue;
                        }
                        if (user.getMoney() > bestvalue) {
                            bestvalue = user.getMoney();
                            if (playerlayer != null) {
                                users.remove(user);
                            }
                            playerlayer = user;
                            users.put(position, user);
                        }
                    }
                    if (playerlayer == null) break;
                    current += 1;
                    position += 1;
                }
                tosend = tosend + "\n§6----- §f§lBaltop §6----- ";
                for (int i = 1; i <= 10; i++) {
                    int toAdd = 10 * (page - 1);
                    if (!users.containsKey((i + toAdd))) break;
                    String aa = EcoAccountsManager.instance.moneyGetarrondiNDecimalesFromUUID(users.get((i + toAdd)).getUserUUID());
                    tosend = tosend + "\n§f" + (i + toAdd) + ". §6" + users.get((i + toAdd)).getUsername() + ": " +
                            aa + "$";
                }
                long resulttime = System.currentTimeMillis() - millisstart;
                tosend = tosend + "\n§6--- §f§lPage " + page + " §6---" + " Généré en " + resulttime + "ms";
                player.sendMessage(tosend);
                users.clear();
            }
        }, 0);
    }
}
