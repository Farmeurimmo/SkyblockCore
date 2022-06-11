package main.java.fr.verymc.core.eco;

import main.java.fr.verymc.storage.SkyblockUser;
import main.java.fr.verymc.storage.SkyblockUserManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.UUID;

public class EcoAccountsManager {

    public static EcoAccountsManager instance;

    public EcoAccountsManager() {
        instance = this;
    }

    public boolean isExisting(Player player) {
        boolean a = SkyblockUserManager.instance.getUser(player.getUniqueId()) != null;
        return a;
    }

    public double getMoney(UUID playername) {
        Double b = (double) 0;
        SkyblockUser skyblockUser = SkyblockUserManager.instance.getUser(playername);
        if (skyblockUser != null) {
            if (skyblockUser.getUserUUID() != null) {
                b = skyblockUser.getMoney();
            }
        }
        return b;
    }

    public double getMoneyUUID(UUID playername) {
        Double b = (double) 0;
        if (playername != null) {
            if (SkyblockUserManager.instance.getUser(playername) != null) {
                b = SkyblockUserManager.instance.getUser(playername).getMoney();
            }
        }
        return b;
    }

    public String moneyGetarrondiNDecimales(Player player) {
        return DecimalFormat.getNumberInstance().format(EcoAccountsManager.instance.getMoneyUUID(player.getUniqueId()));
    }

    public String moneyGetarrondiNDecimalesFromStr(String player) {
        return DecimalFormat.getNumberInstance().format(EcoAccountsManager.instance.getMoney(Bukkit.getOfflinePlayer(player).getPlayer().getUniqueId()));
    }

    public String moneyGetarrondiNDecimalesFromUUID(UUID player) {
        return DecimalFormat.getNumberInstance().format(EcoAccountsManager.instance.getMoneyUUID(player));
    }

    public boolean checkForFounds(Player player, Double f) {
        boolean aa = false;
        Double moneyplayer = instance.getMoney(player.getUniqueId());
        if (moneyplayer >= f) {
            aa = true;
        }
        return aa;
    }

    public boolean checkForFoundsUUID(UUID uuid, Double f) {
        boolean aa = false;
        Double moneyplayer = instance.getMoney(uuid);
        if (moneyplayer >= f) {
            aa = true;
        }
        return aa;
    }

    public void removeFounds(Player player, double toremove, boolean ase) {
        Double moneybefore = instance.getMoney(player.getUniqueId());
        if (moneybefore >= toremove) {
            double now = moneybefore - toremove;
            SkyblockUser skyblockUser = SkyblockUserManager.instance.getUser(player.getUniqueId());
            skyblockUser.setMoney(now);
            if (player != null) {
                if (ase == true)
                    player.sendMessage("§6§lMonnaie §8» §6" + DecimalFormat.getNumberInstance().format(toremove) + "$§f ont été §cretiré §fde votre compte.");
            }
        } else {
            if (ase == true) {
                player.sendMessage("§6§lMonnaie §8» §6" + DecimalFormat.getNumberInstance().format(toremove) + "$§f n'ont pas pu être §cretiré §fde votre compte.");
            }
        }
    }

    public void removeFoundsUUID(UUID player, double toremove, boolean ase) {
        Double moneybefore = instance.getMoney(player);
        if (moneybefore >= toremove) {
            double now = moneybefore - toremove;
            SkyblockUser skyblockUser = SkyblockUserManager.instance.getUser(player);
            skyblockUser.setMoney(now);
        }
    }

    public void setFounds(Player player, Double toset) {
        SkyblockUser skyblockUser = SkyblockUserManager.instance.getUser(player.getUniqueId());
        skyblockUser.setMoney(toset);
        if (player != null) {
            player.sendMessage("§6§lMonnaie §8» §fVotre argent a été §adéfinis§f sur §6" + toset + "$§f.");
        }
    }

    public void addFounds(Player player, double aaa, boolean dd) {
        Double moneybefore = instance.getMoney(player.getUniqueId());
        if (moneybefore < Double.MAX_VALUE - aaa) {
            double now = getMoney(player.getUniqueId()) + aaa;
            SkyblockUser skyblockUser = SkyblockUserManager.instance.getUser(player.getUniqueId());
            skyblockUser.setMoney(now);
            if (player != null) {
                if (dd == true)
                    player.sendMessage("§6§lMonnaie §8» §6" + DecimalFormat.getNumberInstance().format(aaa) + "$§f ont été §aajouté §f§ votre compte.");
            }
        } else {
            if (player != null) {
                if (dd == true)
                    player.sendMessage("§6§lMonnaie §8» §6" + DecimalFormat.getNumberInstance().format(aaa) + "$§f n'ont pas pu être §aajouté §f§votre compte.");
            }
        }
    }

    public void addFoundsUUID(UUID player, double aaa, boolean dd) {
        Double moneybefore = instance.getMoneyUUID(player);
        if (moneybefore < Double.MAX_VALUE - aaa) {
            double now = getMoneyUUID(player) + aaa;
            SkyblockUser skyblockUser = SkyblockUserManager.instance.getUser(player);
            skyblockUser.setMoney(now);
        }
    }
}
