package main.java.fr.verymc.eco;

import main.java.fr.verymc.core.Main;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class EcoAccountsManager {

    public static EcoAccountsManager instance;
    public HashMap<UUID, Double> Moneys = new HashMap<>();

    public EcoAccountsManager() {
        instance = this;
    }

    public void CheckForAccount(Player player) {
        if (Main.instance.getDataz().get(String.valueOf(player.getUniqueId())) == null) {
            Main.instance.getDataz().set(String.valueOf(player.getUniqueId()), 200);
            Main.instance.saveData();
            Moneys.put(player.getUniqueId(), Main.instance.getDataz().getDouble(String.valueOf(player.getUniqueId())));
        }
    }

    public boolean IsExisting(Player player) {
        boolean a = Main.instance.getDataz().get(String.valueOf(player.getUniqueId())) != null;
        return a;
    }

    public double GetMoney(String playername) {
        Double b = (double) 0;
        Player onl = Bukkit.getPlayer(playername);
        OfflinePlayer off = Bukkit.getOfflinePlayer(playername);
        if (onl != null && onl.isOnline()) {
            if (Moneys.containsKey(onl.getUniqueId())) {
                b = Moneys.get(onl.getUniqueId());
            }
        } else if (off != null) {
            if (Moneys.containsKey(off.getUniqueId())) {
                b = Moneys.get(off.getUniqueId());
            }
        }
        return b;
    }

    public double GetMoneyUUID(UUID playername) {
        Double b = (double) 0;
        if (playername != null) {
            if (Moneys.containsKey(playername)) {
                b = Moneys.get(playername);
            }
        }
        return b;
    }

    public double MoneyGetarrondiNDecimales(Player player, int n) {
        double money = GetMoney(player.getName());
        double pow = Math.pow(10, n);
        return (Math.floor(money * pow)) / pow;
    }

    public boolean CheckForFounds(Player player, Double f) {
        boolean aa = false;
        Double moneyplayer = instance.GetMoney(player.getName());
        Double after = moneyplayer - f;
        if (after >= 0) {
            aa = true;
        }
        return aa;
    }

    public void RemoveFounds(Player player, Double toremove, boolean ase) {
        Double moneybefore = instance.GetMoney(player.getName());
        if (moneybefore - toremove >= 0) {
            double now = moneybefore - toremove;
            Moneys.put(player.getUniqueId(), now);
            Main.instance.getDataz().set(String.valueOf(player.getUniqueId()), now);
            Main.instance.saveData();
            if (player != null) {
                if (ase == true)
                    player.sendMessage("§6§lMonnaie §8» §6" + toremove + "$§f ont été §cretiré §fde votre compte.");
            }
        } else {
            if (player != null) {
                if (ase == true)
                    player.sendMessage("§6§lMonnaie §8» §6" + toremove + "$§f n'ont pas pu être §cretiré §fde votre compte.");
            }
        }
    }

    public void SetFounds(Player player, Double toset) {
        Moneys.put(player.getUniqueId(), toset);
        Main.instance.getDataz().set(String.valueOf(player.getUniqueId()), toset);
        Main.instance.saveData();
        if (player != null) {
            player.sendMessage("§6§lMonnaie §8» §fVotre argent a été §adéfinis§f sur §6" + toset + "$§f.");
        }
    }

    public void AddFounds(Player player, Double aaa, boolean dd) {
        Double moneybefore = instance.GetMoney(player.getName());
        if (moneybefore < Double.MAX_VALUE - aaa) {
            double now = GetMoney(player.getName()) + aaa;
            Moneys.put(player.getUniqueId(), now);
            Main.instance.getDataz().set(String.valueOf(player.getUniqueId()), now);
            Main.instance.saveData();
            if (player != null) {
                if (dd == true)
                    player.sendMessage("§6§lMonnaie §8» §6" + aaa + "$§f ont été §aajouté §f§ votre compte.");
            }
        } else {
            if (player != null) {
                if (dd == true)
                    player.sendMessage("§6§lMonnaie §8» §6" + aaa + "$§f n'ont pas pu être §aajouté §f§votre compte.");
            }
        }
    }

    public void AddFoundsUUID(UUID player, Double aaa, boolean dd) {
        Double moneybefore = instance.GetMoneyUUID(player);
        if (moneybefore < Double.MAX_VALUE - aaa) {
            double now = GetMoneyUUID(player) + aaa;
            Moneys.put(player, now);
            Main.instance.getDataz().set(String.valueOf(player), now);
            Main.instance.saveData();
            if (dd == true && Bukkit.getPlayer(player) != null) {
                Bukkit.getPlayer(player).sendMessage("§6§lMonnaie §8» §6" + aaa + "$§f ont été §aajouté §f§ votre compte.");
            }
        }
    }

    public void UpdateHash() {
        for (String aa : Main.instance.getDataz().getConfigurationSection("").getKeys(false)) {
            Moneys.put(UUID.fromString(aa), Main.instance.getDataz().getDouble(aa));
        }
    }
}
