package main.java.fr.verymc.eco;

import main.java.fr.verymc.config.AsyncSaver;
import main.java.fr.verymc.config.ConfigManager;
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

    public void checkForAccount(Player player) {
        if (ConfigManager.instance.getDataEco().get(String.valueOf(player.getUniqueId())) == null) {
            HashMap<String, Object> objectHashMap = new HashMap<>();
            objectHashMap.put(String.valueOf(player.getUniqueId()), 200);
            AsyncSaver.instance.setAndSaveAsync(objectHashMap, ConfigManager.instance.getDataEco(),
                    ConfigManager.instance.ecoFile);
            Moneys.put(player.getUniqueId(), ConfigManager.instance.getDataEco().getDouble(String.valueOf(player.getUniqueId())));
        }
    }

    public boolean isExisting(Player player) {
        boolean a = ConfigManager.instance.getDataEco().get(String.valueOf(player.getUniqueId())) != null;
        return a;
    }

    public double getMoney(String playername) {
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

    public double getMoneyUUID(UUID playername) {
        Double b = (double) 0;
        if (playername != null) {
            if (Moneys.containsKey(playername)) {
                b = Moneys.get(playername);
            }
        }
        return b;
    }

    public double moneyGetarrondiNDecimales(Player player, int n) {
        double money = getMoney(player.getName());
        double pow = Math.pow(10, n);
        return (Math.floor(money * pow)) / pow;
    }

    public boolean checkForFounds(Player player, Double f) {
        boolean aa = false;
        Double moneyplayer = instance.getMoney(player.getName());
        if (moneyplayer >= f) {
            aa = true;
        }
        return aa;
    }

    public void removeFounds(Player player, Double toremove, boolean ase) {
        Double moneybefore = instance.getMoney(player.getName());
        if (moneybefore >= toremove) {
            double now = moneybefore - toremove;
            Moneys.put(player.getUniqueId(), now);
            HashMap<String, Object> objectHashMap = new HashMap<>();
            objectHashMap.put(String.valueOf(player.getUniqueId()), now);
            AsyncSaver.instance.setAndSaveAsync(objectHashMap, ConfigManager.instance.getDataEco(),
                    ConfigManager.instance.ecoFile);
            if (player != null) {
                if (ase == true)
                    player.sendMessage("§6§lMonnaie §8» §6" + toremove + "$§f ont été §cretiré §fde votre compte.");
            }
        } else {
            if (ase == true) {
                player.sendMessage("§6§lMonnaie §8» §6" + toremove + "$§f n'ont pas pu être §cretiré §fde votre compte.");
            }
        }
    }

    public void setFounds(Player player, Double toset) {
        Moneys.put(player.getUniqueId(), toset);
        HashMap<String, Object> objectHashMap = new HashMap<>();
        objectHashMap.put(String.valueOf(player.getUniqueId()), toset);
        AsyncSaver.instance.setAndSaveAsync(objectHashMap, ConfigManager.instance.getDataEco(),
                ConfigManager.instance.ecoFile);
        if (player != null) {
            player.sendMessage("§6§lMonnaie §8» §fVotre argent a été §adéfinis§f sur §6" + toset + "$§f.");
        }
    }

    public void addFounds(Player player, Double aaa, boolean dd) {
        Double moneybefore = instance.getMoney(player.getName());
        if (moneybefore < Double.MAX_VALUE - aaa) {
            double now = getMoney(player.getName()) + aaa;
            Moneys.put(player.getUniqueId(), now);
            HashMap<String, Object> objectHashMap = new HashMap<>();
            objectHashMap.put(String.valueOf(player.getUniqueId()), now);
            AsyncSaver.instance.setAndSaveAsync(objectHashMap, ConfigManager.instance.getDataEco(),
                    ConfigManager.instance.ecoFile);
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

    public void addFoundsUUID(UUID player, Double aaa, boolean dd) {
        Double moneybefore = instance.getMoneyUUID(player);
        if (moneybefore < Double.MAX_VALUE - aaa) {
            double now = getMoneyUUID(player) + aaa;
            Moneys.put(player, now);
            HashMap<String, Object> objectHashMap = new HashMap<>();
            objectHashMap.put(String.valueOf(player), now);
            AsyncSaver.instance.setAndSaveAsync(objectHashMap, ConfigManager.instance.getDataEco(),
                    ConfigManager.instance.ecoFile);
            if (dd == true && Bukkit.getPlayer(player) != null) {
                Bukkit.getPlayer(player).sendMessage("§6§lMonnaie §8» §6" + aaa + "$§f ont été §aajouté §f§ votre compte.");
            }
        }
    }

    public void updateHash() {
        for (String aa : ConfigManager.instance.getDataEco().getConfigurationSection("").getKeys(false)) {
            Moneys.put(UUID.fromString(aa), ConfigManager.instance.getDataEco().getDouble(aa));
        }
    }
}
