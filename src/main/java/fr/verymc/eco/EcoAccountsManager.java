package main.java.fr.verymc.eco;

import main.java.fr.verymc.core.Main;
import org.bukkit.Bukkit;
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
        if (Main.instance1.getDataz().get(String.valueOf(player.getUniqueId())) == null) {
            Main.instance1.getDataz().set(String.valueOf(player.getUniqueId()), 200);
            Main.instance1.saveData();
            Moneys.put(player.getUniqueId(), Main.instance1.getDataz().getDouble(String.valueOf(player.getUniqueId())));
        }
    }

    public boolean IsExisting(UUID player) {
        boolean a = Main.instance1.getDataz().get(String.valueOf(player)) != null;
        return a;
    }

    public double GetMoney(UUID a) {
        Double b = (double) 0;
        if (Moneys.containsKey(a)) {
            b = Moneys.get(a);
        }
        return b;
    }

    public double MoneyGetarrondiNDecimales(UUID playername, int n) {
        double money = GetMoney(playername);
        double pow = Math.pow(10, n);
        return (Math.floor(money * pow)) / pow;
    }

    public boolean CheckForFounds(UUID player, Double f) {
        boolean aa = false;
        Double moneyplayer = instance.GetMoney(player);
        Double after = moneyplayer - f;
        if (after >= 0) {
            aa = true;
        }
        return aa;
    }

    public void RemoveFounds(UUID player, Double toremove, boolean ase) {
        Double moneybefore = instance.GetMoney(player);
        if (moneybefore - toremove >= Double.MIN_VALUE) {
            double now = moneybefore - toremove;
            Moneys.put(player, now);
            Main.instance1.getDataz().set(String.valueOf(player), now);
            Main.instance1.saveData();
            if (Bukkit.getPlayer(player) != null) {
                if (ase == true)
                    Bukkit.getPlayer(player).sendMessage("§6§lMonnaie §8» §6" + toremove + "$§f ont été §cretiré §fde votre compte.");
            }
        } else {
            if (Bukkit.getPlayer(player) != null) {
                if (ase == true)
                    Bukkit.getPlayer(player).sendMessage("§6§lMonnaie §8» §6" + toremove + "$§f n'ont pas pu être §cretiré §fde votre compte.");
            }
        }
    }

    public void SetFounds(UUID player, Double toset) {
        Moneys.put(player, toset);
        Main.instance1.getDataz().set(String.valueOf(player), toset);
        Main.instance1.saveData();
        if (Bukkit.getPlayer(player) != null) {
            Bukkit.getPlayer(player).sendMessage("§6§lMonnaie §8» §fVotre argent a été §adéfinis§f sur §6" + toset + "$§f.");
        }
    }

    public void AddFounds(UUID player, Double aaa, boolean dd) {
        Double moneybefore = instance.GetMoney(player);
        if (moneybefore < Double.MAX_VALUE - aaa) {
            double now = GetMoney(player) + aaa;
            Moneys.put(player, now);
            Main.instance1.getDataz().set(String.valueOf(player), now);
            Main.instance1.saveData();
            if (Bukkit.getPlayer(player) != null) {
                if (dd == true)
                    Bukkit.getPlayer(player).sendMessage("§6§lMonnaie §8» §6" + aaa + "$§f ont été §aajouté §f§ votre compte.");
            }
        } else {
            if (Bukkit.getPlayer(player) != null) {
                if (dd == true)
                    Bukkit.getPlayer(player).sendMessage("§6§lMonnaie §8» §6" + aaa + "$§f n'ont pas pu être §aajouté §f§votre compte.");
            }
        }
    }

    public void UpdateHash() {
        for (String aa : Main.instance1.getDataz().getConfigurationSection("").getKeys(false)) {
            Moneys.put(UUID.fromString(aa), Main.instance1.getDataz().getDouble(aa));
        }
    }
}
