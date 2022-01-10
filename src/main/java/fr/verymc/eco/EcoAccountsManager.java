package main.java.fr.verymc.eco;

import main.java.fr.verymc.core.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class EcoAccountsManager {

    public static EcoAccountsManager instance;

    public EcoAccountsManager(){
        instance = this;
    }

    public HashMap<String, Double> Moneys = new HashMap<>();

    public void CheckForAccount(Player player) {
        if (Main.instance1.getDataz().get(player.getName()) == null) {
            Main.instance1.getDataz().set(player.getName(), 200);
            Main.instance1.saveData();
            Moneys.put(player.getName(), Main.instance1.getDataz().getDouble(player.getName()));
        }
    }

    public boolean IsExisting(String player) {
        boolean a = false;
        if (Main.instance1.getDataz().get(player) != null) {
            a = true;
        }
        return a;
    }

    public double GetMoney(String a) {
        Double b = (double) 0;
        if (Moneys.containsKey(a)) {
            b = Moneys.get(a);
        }
        return b;
    }

    public double MoneyGetarrondiNDecimales(String playername, int n) {
        double money = GetMoney(playername);
        double pow = Math.pow(10, n);
        return (Math.floor(money * pow)) / pow;
    }

    public boolean CheckForFounds(Player player, Double f) {
        boolean aa = false;
        Double moneyplayer = Moneys.get(player.getName());
        Double after = (Double) moneyplayer - f;
        if (after >= 0) {
            aa = true;
        }
        return aa;
    }

    public void RemoveFounds(String player, Double toremove) {
        Double moneybefore = Moneys.get(player);
        if (moneybefore - toremove >= Double.MIN_VALUE) {
            double now = moneybefore - toremove;
            Moneys.put(player, now);
            Main.instance1.getDataz().set(player, now);
            Main.instance1.saveData();
            if (Bukkit.getPlayer(player) != null) {
                Bukkit.getPlayer(player).sendMessage("§6§lMonnaie §8» §6" + toremove + "$§f ont été §cretiré §fde votre compte.");
            }
        } else {
            if (Bukkit.getPlayer(player) != null) {
                Bukkit.getPlayer(player).sendMessage("§6§lMonnaie §8» §6" + toremove + "$§f n'ont pas pu être §cretiré §fde votre compte.");
            }
        }
    }

    public void SetFounds(String player, Double toset) {
        Moneys.put(player, toset);
        Main.instance1.getDataz().set(player, toset);
        Main.instance1.saveData();
        if (Bukkit.getPlayer(player) != null) {
            Bukkit.getPlayer(player).sendMessage("§6§lMonnaie §8» §fVotre argent a été §adéfinis§f sur §6" + toset + "$§f.");
        }
    }

    public void AddFounds(String player, Double aaa, boolean dd) {
        Double moneybefore = Moneys.get(player);
        if (moneybefore < Double.MAX_VALUE - aaa) {
            double now = Moneys.get(player) + aaa;
            Moneys.put(player, now);
            Main.instance1.getDataz().set(player, now);
            Main.instance1.saveData();
            if (dd == false) {
                if (Bukkit.getPlayer(player) != null) {
                    Bukkit.getPlayer(player).sendMessage("§6§lMonnaie §8» §6" + aaa + "$§f ont été §aajouté §f§ votre compte.");
                } else {
                    if (Bukkit.getPlayer(player) != null) {
                        Bukkit.getPlayer(player).sendMessage("§6§lMonnaie §8» §6" + aaa + "$§f n'ont pas pu être §aajouté §f§votre compte.");
                    }
                }
            }
        }
    }

    public void UpdateHash() {
        for (String aa : Main.instance1.getDataz().getConfigurationSection("").getKeys(false)) {
            Moneys.put(aa, Main.instance1.getDataz().getDouble(aa));
        }
    }
}
