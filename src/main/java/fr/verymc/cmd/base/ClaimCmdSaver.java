package main.java.fr.verymc.cmd.base;

import main.java.fr.verymc.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class ClaimCmdSaver {

    private static BukkitRunnable previousRenable;
    public final HashMap<UUID, HashMap<String, Integer>> cooldowns = getSaveCooldown();

    public ClaimCmdSaver() {
        cooldown_manager();
    }

    public void saveCooldown() {
        File file = new File(Main.instance.getDataFolder() + File.separator + "cooldowClaim.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        boolean createResult;
        if (!file.exists()) {
            try {
                createResult = file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (createResult) {
                cooldowns.forEach((k, v) -> {
                    config.set(k.toString(), v);
                });
            }
        } else {
            cooldowns.forEach((k, v) -> {
                config.set(k.toString(), v);
            });
        }
        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void cooldown_manager() {
        if (previousRenable != null)
            previousRenable.cancel();
        new BukkitRunnable() {
            public void run() {
                cooldowns.forEach((k, v) -> {
                    HashMap<String, Integer> timeMap = cooldowns.get(k);
                    timeMap.forEach((k2, v2) -> {
                        int timeLeft = timeMap.get(k2);
                        if (timeLeft == 0) {
                            timeMap.remove(k2);
                            if (timeMap.size() == 0) {
                                this.cancel();
                            }
                        }
                        timeMap.put(k2, timeLeft - 1);
                    });
                    cooldowns.put(k, timeMap);
                });
                previousRenable = this;
            }
        }.runTaskTimer(Main.instance, 20, 20);
    }

    public HashMap<UUID, HashMap<String, Integer>> getSaveCooldown() {
        File file = new File(Main.instance.getDataFolder() + File.separator + "cooldowClaim.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        Set<String> result = config.getKeys(false);
        HashMap<UUID, HashMap<String, Integer>> map = new HashMap<>();
        for (String s : result) {
            HashMap<String, Integer> timeMap = new HashMap<>();
            int timer = config.getInt(s + ".daily");
            if (timer != 0)
                timeMap.put("daily", timer);
            timer = config.getInt(s + ".weekly");
            if (timer != 0)
                timeMap.put("weekly", timer);
            timer = config.getInt(s + ".legend");
            if (timer != 0)
                timeMap.put("legend", timer);
            timer = config.getInt(s + ".god");
            if (timer != 0)
                timeMap.put("god", timer);
            timer = config.getInt(s + ".zeus");
            if (timer != 0)
                timeMap.put("zeus", timer);
            map.put(UUID.fromString(s), timeMap);
        }
        return map;
    }
}
