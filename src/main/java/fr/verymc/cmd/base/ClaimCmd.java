package main.java.fr.verymc.cmd.base;

import main.java.fr.verymc.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class ClaimCmd implements CommandExecutor, TabCompleter {

    private final HashMap<UUID, HashMap<String, Integer>> cooldowns = new HashMap<>();

    public void cooldown_manager(Player player) {
        new BukkitRunnable() {
            public void run() {
                HashMap<String, Integer> timeMap = cooldowns.get(player.getUniqueId());
                timeMap.forEach((k, v) -> {
                    int timeLeft = timeMap.get(k);
                    if (timeLeft == 0) {
                        timeMap.remove(k);
                        if (timeMap.size() == 0) {
                            this.cancel();
                        }
                    }
                    timeMap.put(k, timeLeft - 1);
                });
                cooldowns.put(player.getUniqueId(), timeMap);
            }
        }.runTaskTimer(Main.instance, 20, 20);
    }
    public void sendPendingMessage(Player player, String kitName) {
        int numSecond = cooldowns.get(player.getUniqueId()).get(kitName);
        int numMin = numSecond / 60;
        numSecond %= 60;
        int numHour = numMin / 60;
        numMin %= 60;
        int numDay = numHour / 24;
        numHour %= 24;
        player.sendMessage("§cErreur, il reste " + numDay + " jour(s), " + numHour + " heure(s), "
                + numMin + " minute(s), " + numSecond + " seconde(s) avant réutilisation du kit " + kitName);
    }
    public void claimDaily(Player player) {
        if (cooldowns.get(player.getUniqueId()).containsKey("daily")) {
            sendPendingMessage(player, "daily");
            return;
        }
        player.sendMessage("§aVous avez récupérer avec succès le kit §6daily");
        cooldowns.get(player.getUniqueId()).put("daily", 86400);
    }
    public void claimWeekly(Player player) {
        if (cooldowns.get(player.getUniqueId()).containsKey("weekly")) {
            sendPendingMessage(player, "weekly");
            return;
        }
        player.sendMessage("§aVous avez récupérer avec succès le kit §6weekly");
        cooldowns.get(player.getUniqueId()).put("weekly", 604800);
    }
    public void claimLegend(Player player) {
        if (cooldowns.get(player.getUniqueId()).containsKey("legend")) {
            sendPendingMessage(player, "legend");
            return;
        }
        player.sendMessage("§aVous avez récupérer avec succès le kit §6legend");
        cooldowns.get(player.getUniqueId()).put("legend", 604800);
    }
    public void claimGod(Player player) {
        if (cooldowns.get(player.getUniqueId()).containsKey("god")) {
            sendPendingMessage(player, "god");
            return;
        }
        player.sendMessage("§aVous avez récupérer avec succès le kit §6god");
        cooldowns.get(player.getUniqueId()).put("god", 604800);
    }
    public void claimZeus(Player player) {
        if (cooldowns.get(player.getUniqueId()).containsKey("zeus")) {
            sendPendingMessage(player, "zeus");
            return;
        }
        player.sendMessage("§aVous avez récupérer avec succès le kit §6zeus");
        cooldowns.get(player.getUniqueId()).put("zeus", 604800);
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }
        if (args.length != 1) {
            player.sendMessage("§4/claim [nom du kit]");
            return false;
        }
        List<String> validKitName = Arrays.asList("daily", "weekly", "legend", "god", "zeus", "all");
        if (!validKitName.contains(args[0])) {
            player.sendMessage("§4Entrez un nom de kit valide");
            return false;
        }
        if (cooldowns.containsKey(player.getUniqueId())
                && cooldowns.get(player.getUniqueId()).containsKey(args[0])) {
            sendPendingMessage(player, args[0]);
            return false;
        }
        if (!cooldowns.containsKey(player.getUniqueId())) {
            cooldowns.put(player.getUniqueId(), new HashMap<>());
        }
        switch (args[0]) {
            case "daily" -> {
                claimDaily(player);
            }
            case "weekly" -> {
                claimWeekly(player);
            }
            case "legend" -> {
                claimLegend(player);
            }
            case "god" -> {
                claimGod(player);
            }
            case "zeus" -> {
                claimZeus(player);
            }
            case "all" -> {
                claimDaily(player);
                claimWeekly(player);
                claimLegend(player);
                claimGod(player);
                claimZeus(player);
            }
        }
        cooldown_manager(player);
        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        ArrayList<String> subcmd = new ArrayList<>();
        if (cmd.getName().equalsIgnoreCase("claim")) {
            if (args.length != 1) {
                subcmd.add("");
            } else {
                subcmd.add("daily");
                subcmd.add("weekly");
                subcmd.add("legend");
                subcmd.add("god");
                subcmd.add("zeus");
                subcmd.add("all");
            }
        }
        Collections.sort(subcmd);
        return subcmd;
    }
}
