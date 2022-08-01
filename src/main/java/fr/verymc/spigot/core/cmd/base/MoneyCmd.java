package main.java.fr.verymc.spigot.core.cmd.base;

import main.java.fr.verymc.spigot.core.eco.EcoAccountsManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MoneyCmd implements CommandExecutor, TabCompleter {
    public double check_args_2(String[] args, CommandSender sender, String message_to_send) {
        if (args[2].length() <= 9) {
            double amount = Double.parseDouble(args[2]);
            sender.sendMessage(message_to_send);
            return amount;
        } else {
            sender.sendMessage("§6§lMonnaie §8» §fVeuillez choisir un nombre plus petit.");
            return (-1);
        }
    }

    public void parse_args2(String[] args, CommandSender sender) {
        String str = args[1].toLowerCase();
        double amount;
        switch (str) {
            case "give":
                amount = check_args_2(args, sender,
                        "§6§lMonnaie §8» §f" + args[0] + " a reçu §6" + args[2] + "$§f sur son compte avec succès.");
                if (amount != -1) {
                    EcoAccountsManager.instance.addFounds((Player) Bukkit.getOfflinePlayer(args[0]), amount, true);
                    return;
                }
            case "remove":
                amount = check_args_2(args, sender,
                        "§6§lMonnaie §8» §f" + args[0] + " a perdu §6" + args[2] + "$§f sur son compte avec succès.");
                if (amount != -1) {
                    EcoAccountsManager.instance.removeFounds((Player) Bukkit.getOfflinePlayer(args[0]), amount, true);
                    return;
                }
            case "set":
                amount = check_args_2(args, sender,
                        "§6§lMonnaie §8» §fL'argent de " + args[0] + " a été d§finis sur §6" + args[2] + "$§f avec succès.");
                if (amount != -1) {
                    EcoAccountsManager.instance.setFounds((Player) Bukkit.getOfflinePlayer(args[0]), amount);
                    return;
                }
            default:
                sender.sendMessage("§6§lMonnaie §8» §f/money <pseudo> <give/remove> <montant>");
                break;
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        switch (args.length) {
            case 0:
                if (!(sender instanceof Player player)) {
                    return false;
                }
                player.sendMessage("§6§lMonnaie §8» §fVous avez §6"
                        + EcoAccountsManager.instance.moneyGetarrondiNDecimales(player) + "$");
                return false;
            case 1:
                if (Bukkit.getPlayer(args[0]) == null) {
                    sender.sendMessage("§6§lMonnaie §8» §fErreur compte inexistant ou indisponible !");
                    return false;
                }
                if (EcoAccountsManager.instance.isExisting((Player) Bukkit.getOfflinePlayer(args[0]))) {
                    sender.sendMessage("§6§lMonnaie §8» §6" + args[0] + "§f possède §6" +
                            EcoAccountsManager.instance.moneyGetarrondiNDecimalesFromStr(args[0]) + "$");
                    return false;
                }
                break;
            case 2:
                sender.sendMessage("§6§lMonnaie §8» §f/money <pseudo> <give/remove> <montant>");
                return false;
            case 3:
                if (Bukkit.getPlayer(args[0]) == null) {
                    sender.sendMessage("§6§lMonnaie §8» §fErreur compte inexistant ou indisponible !");
                    return false;
                }
                if (!EcoAccountsManager.instance.isExisting((Player) Bukkit.getOfflinePlayer(args[0]))) {
                    sender.sendMessage("§6§lMonnaie §8» §f/money <pseudo> <give/remove> <montant>");
                    return false;
                }
                if (args[0].equalsIgnoreCase("@a")) {
                    double amount = Double.parseDouble(args[2]);
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        EcoAccountsManager.instance.addFounds(p, amount, true);
                        sender.sendMessage("§6§lMonnaie §8» §f" + p.getName() + " a reçu §6" + amount + "$§f sur son compte avec succès.");
                    }
                    return false;
                }
                parse_args2(args, sender);
            default:
                break;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        ArrayList<String> subcmd = new ArrayList<>();
        if (cmd.getName().equalsIgnoreCase("money")) {
            switch (args.length) {
                case 1:
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        subcmd.add(p.getName());
                    }
                    subcmd.add("@a");
                    break;
                case 2:
                    if (sender.hasPermission("give")) {
                        subcmd.add("give");
                        subcmd.add("remove");
                        subcmd.add("set");
                    } else {
                        subcmd.add("");
                    }
                    break;
                default:
                    subcmd.add("");
                    break;
            }
        }
        Collections.sort(subcmd);
        return subcmd;
    }
}
