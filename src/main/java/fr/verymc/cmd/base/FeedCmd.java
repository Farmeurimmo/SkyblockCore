package main.java.fr.verymc.cmd.base;

import main.java.fr.verymc.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class FeedCmd implements CommandExecutor, TabCompleter {
    private final HashMap<UUID, Integer> cooldowns = new HashMap<>();
    public void setCooldown(UUID player, Integer time) {
        if (time == null)
            cooldowns.remove(player);
        else
            cooldowns.put(player, time);
    }
    public int getCooldown(UUID player) {
        return (cooldowns.get(player) == null ? 0 : cooldowns.get(player));
    }
    public void cooldown_manager(Player player) {
        new BukkitRunnable() {
            public void run() {
                int timeLeft = getCooldown(player.getUniqueId());
                if (timeLeft == 0) {
                    cooldowns.remove(player.getUniqueId());
                    this.cancel();
                    return;
                }
                setCooldown(player.getUniqueId(), timeLeft - 1);
            }
        }.runTaskTimer(Main.instance, 20, 20);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }
        if (!(player.hasPermission("feed"))) {
            player.sendActionBar("§cPermissions insuffisantes.");
            return false;
        }
        if (!cooldowns.containsKey(player.getUniqueId())) {
            player.setFoodLevel(20);
            player.sendActionBar("§aVous avez été rassasié.");
            int timeLeft = getCooldown(player.getUniqueId());
            if (timeLeft == 0) {
                setCooldown(player.getUniqueId(), 20);
                cooldown_manager(player);
            }
        } else {
            player.sendActionBar("§cErreur, il reste " + getCooldown(player.getUniqueId()) + " seconde(s) avant r§utilisation");
        }
        return false;
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        ArrayList<String> subcmd = new ArrayList<>();
        if (cmd.getName().equalsIgnoreCase("feed")) {
            subcmd.add("");
            Collections.sort(subcmd);
        }
        return subcmd;
    }
}
