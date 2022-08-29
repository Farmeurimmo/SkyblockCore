package main.java.fr.verymc.spigot.dungeon;

import main.java.fr.verymc.spigot.Main;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

public class DungeonBossBarManager {

    public static DungeonBossBarManager instance;
    public HashMap<BossBar, LivingEntity> livingEntityHashMap = new HashMap<>();

    public DungeonBossBarManager() {
        instance = this;

        autoUpdate();
    }

    public void createBossBar(LivingEntity livingEntity, String title) {
        BossBar bossBar = Bukkit.createBossBar(title, BarColor.RED, BarStyle.SOLID);
        for (Player player : Bukkit.getOnlinePlayers()) {
            bossBar.addPlayer(player);
        }
        livingEntityHashMap.put(bossBar, livingEntity);
    }

    public void autoUpdate() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.instance, () -> {
            for (Map.Entry<BossBar, LivingEntity> entry : livingEntityHashMap.entrySet()) {
                BossBar bossBar = entry.getKey();
                LivingEntity livingEntity = entry.getValue();
                if (livingEntity.isDead()) {
                    bossBar.removeAll();
                    bossBar.hide();
                    livingEntityHashMap.remove(bossBar);
                    continue;
                }
                bossBar.setProgress(livingEntity.getHealth() / livingEntity.getMaxHealth());
                bossBar.setTitle(livingEntity.getCustomName() + " §7| §6Vie: " + NumberFormat.getInstance().format(livingEntity.getHealth()));
            }
        }, 0, 5L);
    }
}
