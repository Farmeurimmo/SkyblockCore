package main.java.fr.verymc.cmd.base;

import main.java.fr.verymc.crates.CratesKeyManager;
import main.java.fr.verymc.eco.EcoAccountsManager;
import main.java.fr.verymc.utils.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class ClaimCmd implements CommandExecutor, TabCompleter {

    private static ClaimCmdSaver saver;

    public ClaimCmd(ClaimCmdSaver instance) {
        saver = instance;
    }

    public void sendPendingMessage(Player player, String kitName) {
        int numSecond = saver.cooldowns.get(player.getUniqueId()).get(kitName);
        int numMin = numSecond / 60;
        numSecond %= 60;
        int numHour = numMin / 60;
        numMin %= 60;
        int numDay = numHour / 24;
        numHour %= 24;
        player.sendMessage("§cErreur, il reste " + numDay + " jour(s), " + numHour + " heure(s), "
                + numMin + " minute(s), " + numSecond + " seconde(s) avant réutilisation du kit §6" + kitName);
    }

    public void claimDaily(Player player) {
        if (saver.cooldowns.get(player.getUniqueId()).containsKey("daily")) {
            sendPendingMessage(player, "daily");
            return;
        }
        player.sendMessage("§aVous avez récupérer avec succès le kit §6daily");
        ItemStack diamondBlock = (new ItemStackBuilder(Material.DIAMOND_BLOCK, 16)).getItemStack();
        player.getInventory().addItem(diamondBlock);
        EcoAccountsManager.instance.addFounds(player, 15000, false);
        CratesKeyManager.GiveCrateKey(player, 2, "challenge");
        saver.cooldowns.get(player.getUniqueId()).put("daily", 86400);
    }

    public void claimWeekly(Player player) {
        if (saver.cooldowns.get(player.getUniqueId()).containsKey("weekly")) {
            sendPendingMessage(player, "weekly");
            return;
        }
        player.sendMessage("§aVous avez récupérer avec succès le kit §6weekly");
        ItemStack netheriteBlock = (new ItemStackBuilder(Material.NETHERITE_BLOCK, 16)).getItemStack();
        player.getInventory().addItem(netheriteBlock);
        EcoAccountsManager.instance.addFounds(player, 25000, false);
        CratesKeyManager.GiveCrateKey(player, 4, "challenge");
        CratesKeyManager.GiveCrateKey(player, 2, "légendaire");
        saver.cooldowns.get(player.getUniqueId()).put("weekly", 604800);
    }

    public void claimLegend(Player player) {
        if (!player.hasPermission("group.legende")) {
            player.sendMessage("§6Vous n'avez pas le rôle légende.");
            return;
        }
        if (saver.cooldowns.get(player.getUniqueId()).containsKey("legend")) {
            sendPendingMessage(player, "legend");
            return;
        }
        player.sendMessage("§aVous avez récupérer avec succès le kit §6legend");
        ItemStack netheriteBlock = (new ItemStackBuilder(Material.NETHERITE_BLOCK, 16)).getItemStack();
        player.getInventory().addItem(netheriteBlock);
        EcoAccountsManager.instance.addFounds(player, 25000, false);
        CratesKeyManager.GiveCrateKey(player, 4, "challenge");
        CratesKeyManager.GiveCrateKey(player, 2, "légendaire");
        saver.cooldowns.get(player.getUniqueId()).put("legend", 604800);
    }

    public void claimGod(Player player) {
        if (!player.hasPermission("group.dieu")) {
            player.sendMessage("§6Vous n'avez pas le rôle dieu.");
            return;
        }
        if (saver.cooldowns.get(player.getUniqueId()).containsKey("god")) {
            sendPendingMessage(player, "god");
            return;
        }
        ItemStack netheriteBlock = (new ItemStackBuilder(Material.NETHERITE_BLOCK, 16)).getItemStack();
        player.getInventory().addItem(netheriteBlock);
        EcoAccountsManager.instance.addFounds(player, 25000, false);
        CratesKeyManager.GiveCrateKey(player, 4, "challenge");
        CratesKeyManager.GiveCrateKey(player, 2, "légendaire");
        player.sendMessage("§aVous avez récupérer avec succès le kit §6god");
        saver.cooldowns.get(player.getUniqueId()).put("god", 604800);
    }

    public void claimZeus(Player player) {
        if (!player.hasPermission("group.zeus")) {
            player.sendMessage("§6Vous n'avez pas le rôle zeus.");
            return;
        }
        if (saver.cooldowns.get(player.getUniqueId()).containsKey("zeus")) {
            sendPendingMessage(player, "zeus");
            return;
        }
        ItemStack netheriteBlock = (new ItemStackBuilder(Material.NETHERITE_BLOCK, 16)).getItemStack();
        player.getInventory().addItem(netheriteBlock);
        EcoAccountsManager.instance.addFounds(player, 25000, false);
        CratesKeyManager.GiveCrateKey(player, 4, "challenge");
        CratesKeyManager.GiveCrateKey(player, 2, "légendaire");
        player.sendMessage("§aVous avez récupérer avec succès le kit §6zeus");
        saver.cooldowns.get(player.getUniqueId()).put("zeus", 604800);
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
        if (saver.cooldowns.containsKey(player.getUniqueId())
                && saver.cooldowns.get(player.getUniqueId()).containsKey(args[0])) {
            sendPendingMessage(player, args[0]);
            return false;
        }
        if (!saver.cooldowns.containsKey(player.getUniqueId())) {
            saver.cooldowns.put(player.getUniqueId(), new HashMap<>());
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
        saver.cooldown_manager();
        return false;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        ArrayList<String> subcmd = new ArrayList<>();
        if (cmd.getName().equalsIgnoreCase("claim")) {
            if (args.length != 1) {
                subcmd.add("");
            } else {
                subcmd.add("daily");
                subcmd.add("weekly");
                subcmd.add("all");
                if (sender.hasPermission("group.legende")) {
                    subcmd.add("legend");
                }
                if (sender.hasPermission("group.dieu")) {
                    subcmd.add("god");
                }
                if (sender.hasPermission("group.zeus")) {
                    subcmd.add("zeus");
                }
            }
        }
        Collections.sort(subcmd);
        return subcmd;
    }
}
