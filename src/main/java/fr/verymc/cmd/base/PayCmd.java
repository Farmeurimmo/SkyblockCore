package main.java.fr.verymc.cmd.base;

import main.java.fr.verymc.eco.EcoAccountsManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PayCmd implements CommandExecutor, TabCompleter {
    public boolean get_if_args_is_float(String args) {
        boolean digit;
        try {
            @SuppressWarnings("unused")
            Double intValue = Double.parseDouble(args);
            digit = true;
        } catch (NumberFormatException e) {
            digit = false;
        }
        return digit;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }
        if (args.length != 2) {
            player.sendMessage("§6§lMonnaie §8» §fUtilisation, /pay <joueur> <montant>");
            return false;
        }
        if (Bukkit.getPlayer(args[0]) == null) {
            player.sendMessage("§6§lMonnaie §8» §fErreur compte inexistant ou indisponible !");
            return true;
        }
        if (!EcoAccountsManager.instance.isExisting(Bukkit.getOfflinePlayer(args[0]).getPlayer())) {
            player.sendMessage("§6§lMonnaie §8» §fCe compte n'existe pas.");
            return false;
        }
        if (args[0].equals(player.getName())) {
            player.sendMessage("§6§lMonnaie §8» §fVous ne pouvez pas vous envoyer de l'argent à vous même.");
            return false;
        }
        if (!get_if_args_is_float(args[1]) && args[1].contains("-") && args[1].contains(",")) {
            player.sendMessage("§6§lMonnaie §8» §fMerci d'entrer un nombre valide et positif.");
            return false;
        }
        if (args[1].length() > 9) {
            player.sendMessage("§6§lMonnaie §8» §fVeuillez choisir un nombre plus petit.");
            return false;
        }
        Double amount = Double.parseDouble(args[1]);
        if (!EcoAccountsManager.instance.checkForFounds(player, amount)) {
            player.sendMessage("§6§lMonnaie §8» §fVous n'avez pas les fonds requis.");
            return false;
        }
        if (amount < 5) {
            player.sendMessage("§6§lMonnaie §8» §fVous devez entrer un montant égal ou supérieur à 5$.");
            return false;
        }
        EcoAccountsManager.instance.addFounds((Player) Bukkit.getOfflinePlayer(args[0]), amount, false);
        EcoAccountsManager.instance.removeFounds(player, amount, true);
        player.sendMessage("§6§lMonnaie §8» §fVous avez envoyé avec §asuccès §6" + amount + "$§f au joueur " + args[0]);
        if (Bukkit.getPlayer(args[0]) != null) {
            Bukkit.getPlayer(args[0]).sendMessage("§6§lMonnaie §8» §fVous avez reçu avec §asuccès §6" + amount + "$§f du joueur " + player.getName() + ".");
        }
        return false;
    }

    public void get_all_player_for_tab_complete(ArrayList<String> subcmd, CommandSender sender) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!p.getName().equals(sender.getName())) {
                subcmd.add(p.getName());
            }
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        ArrayList<String> subcmd = new ArrayList<>();
        if (cmd.getName().equalsIgnoreCase("pay")) {
            if (args.length == 1) {
                get_all_player_for_tab_complete(subcmd, sender);
            } else {
                subcmd.add("");
            }
        }
        Collections.sort(subcmd);
        return subcmd;
    }
}
