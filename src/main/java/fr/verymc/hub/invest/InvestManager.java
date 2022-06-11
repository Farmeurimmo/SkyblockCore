package main.java.fr.verymc.hub.invest;

import main.java.fr.verymc.Main;
import main.java.fr.verymc.core.cmd.base.SpawnCmd;
import main.java.fr.verymc.core.eco.EcoAccountsManager;
import main.java.fr.verymc.core.storage.SkyblockUser;
import main.java.fr.verymc.core.storage.SkyblockUserManager;
import main.java.fr.verymc.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.text.NumberFormat;

public class InvestManager {

    public static InvestManager instance;

    public InvestManager() {
        instance = this;
        autoInvest();
    }

    public void toggleInvestMode(Player player) {
        SkyblockUserManager.instance.checkForAccount(player);
        SkyblockUser skyblockUser = SkyblockUserManager.instance.getUser(player.getUniqueId());
        if (!skyblockUser.isInInvestMode()) {
            PlayerUtils.instance.teleportPlayerFromRequest(player, SpawnCmd.Spawn, 0);
            skyblockUser.setInInvestMode(true);
        } else {
            giveReward(skyblockUser);
        }
    }

    public void giveReward(SkyblockUser skyblockUser) {
        double gained = skyblockUser.getTimeInvest() * 1.75;
        EcoAccountsManager.instance.addFoundsUUID(skyblockUser.getUserUUID(), gained, false);
        if (Bukkit.getPlayer(skyblockUser.getUserUUID()) != null) {
            Bukkit.getPlayer(skyblockUser.getUserUUID()).sendMessage("§6§lInvest §8» §fVous êtes sortis du mode invest. Vous avez gagné §6" +
                    NumberFormat.getInstance().format(gained) + "$§f en " + getConverted((int) skyblockUser.getTimeInvest()) + "§f.");
            Bukkit.getPlayer(skyblockUser.getUserUUID()).sendTitle("§6Invest terminé", "§fVous avez gagné " + NumberFormat.getInstance().format(gained) + "$");
        }
        skyblockUser.setTimeInvest(0);
        skyblockUser.setInInvestMode(false);
    }

    public void addTimeInvest(SkyblockUser skyblockUser) {
        skyblockUser.setTimeInvest(skyblockUser.getTimeInvest() + 1);
        Player player = Bukkit.getPlayer(skyblockUser.getUserUUID());
        if (player != null) {
            player.sendTitle("§6Invest en cours...", "§fTemps écoulé: " + getConverted((int) skyblockUser.getTimeInvest()), 0, 60, 0);
        }
    }

    public String getConverted(int seconds) {

        int nHours = (seconds % 86400) / 3600;
        int nMin = ((seconds % 86400) % 3600) / 60;
        int nSec = (((seconds % 86400) % 3600) % 60);

        String nhoursnew;
        String nminnew;
        String nsecnew;
        if (nHours <= 9) {
            nhoursnew = "0" + nHours;
        } else {
            nhoursnew = "" + nHours;
        }
        if (nMin <= 9) {
            nminnew = "0" + nMin;
        } else {
            nminnew = "" + nMin;
        }
        if (nSec <= 9) {
            nsecnew = "0" + nSec;
        } else {
            nsecnew = "" + nSec;
        }

        return "§a" + nhoursnew + ":" + nminnew + ":" + nsecnew;
    }

    public void autoInvest() {
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(Main.instance, new Runnable() {
            @Override
            public void run() {
                for (SkyblockUser skyblockUser : SkyblockUserManager.instance.users) {
                    if (skyblockUser.isInInvestMode()) {
                        Player player = Bukkit.getPlayer(skyblockUser.getUserUUID());
                        if (player != null) {
                            boolean add = false;
                            if (player.isOnline() && player.getWorld().getName().equalsIgnoreCase("world")) {
                                add = true;
                            }
                            boolean finalAdd = add;
                            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
                                @Override
                                public void run() {
                                    if (finalAdd) {
                                        addTimeInvest(skyblockUser);
                                    } else {
                                        giveReward(skyblockUser);
                                    }
                                }
                            }, 0);
                        }
                    }
                }
            }
        }, 0L, 20L);
    }

}
