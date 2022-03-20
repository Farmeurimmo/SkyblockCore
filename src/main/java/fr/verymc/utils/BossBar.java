package main.java.fr.verymc.utils;

import main.java.fr.verymc.Main;
import main.java.fr.verymc.cmd.base.BarCmd;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class BossBar {

    public static int Numberofbossbar = Main.instance.getConfig().getInt("bossbar.number");
    public static int CurrentBossBar = 1;
    public static int DurationProgress = 0;
    public static double BossBarSpeed = 0;
    public static int reverse = 0;
    public static int reset = 0;
    public static ArrayList<Player> Disable = BarCmd.Disable;
    static org.bukkit.boss.BossBar aaa = Bukkit.createBossBar(Main.instance.getConfig().getString("bossbar.display." + CurrentBossBar), BarColor.YELLOW, BarStyle.SOLID, BarFlag.DARKEN_SKY);

    public static void CreateBossBar() {
        BossBarForEveryone();
    }

    public static void RemoveBossBarForPlayers() {
        aaa.removeAll();
    }

    public static void ChangeTitle() {
        reset = 1;
        if (CurrentBossBar == Numberofbossbar) {
            CurrentBossBar = 1;
        } else {
            CurrentBossBar = CurrentBossBar + 1;
        }
        aaa.setTitle(Main.instance.getConfig().getString("bossbar.display." + CurrentBossBar));
        BossBarSpeed = 0.01;
        reverse = 0;
    }

    public static void AddBossBarForPlayer(Player player) {
        aaa.addPlayer(player);
    }

    public static void BossBarForEveryone() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!Disable.contains(player)) {
                aaa.addPlayer(player);
            } else {
                aaa.removePlayer(player);
            }
        }
        aaa.setProgress(0);
        reverse = 0;
        reset = 1;
        BossBarSpeed = 0.01;
        ChangeTitle();
        AddProgressBossbar();
        Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(Main.instance, new Runnable() {
            public void run() {
                BossBarForEveryone();
            }
        }, Main.instance.getConfig().getInt("bossbar.delay"));
    }

    public static void AddProgressBossbar() {
        if (reset != 0) {
            if (reverse == 0) {
                if (BossBarSpeed <= 0.98) {
                    BossBarSpeed = BossBarSpeed + 0.01;
                } else {
                    reverse = 1;
                }
            }
            if (reverse == 1) {
                if (BossBarSpeed <= 0.99 && BossBarSpeed >= 0.06) {
                    BossBarSpeed = BossBarSpeed - 0.01;
                } else {
                    reverse = 2;
                }
            }
            Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(Main.instance, new Runnable() {
                public void run() {
                    if (reverse != 2) {
                        if (BossBarSpeed <= 0.99 && BossBarSpeed >= 0.01) {
                            aaa.setProgress(BossBarSpeed);
                            Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(Main.instance, new Runnable() {
                                public void run() {
                                    AddProgressBossbar();
                                }
                            }, Main.instance.getConfig().getInt("bossbar.intraspeed") - 1);
                            return;
                        } else {
                            return;
                        }
                    } else {
                        return;
                    }
                }
            }, 1);
        } else {
            reset = 1;
            return;
        }
    }

}
