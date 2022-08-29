package main.java.fr.verymc.spigot.core.cmd.moderation;

import main.java.fr.verymc.commons.enums.ServerType;
import main.java.fr.verymc.spigot.Main;
import main.java.fr.verymc.spigot.core.ServersManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RebootCmd implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage("Usage /reboot <temps en seconde>");
            return false;
        }
        int num = Integer.parseInt(args[0]);
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendTitle("Restart du serveur", "Dans " + num + " secondes !");
        }
        Bukkit.broadcastMessage("\n§c§l RESTART DU SERVEUR DANS " + num + " SECONDES !\n");
        Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(Main.instance, () -> {
            int numtoshow = num / 3 + num / 3;
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.sendTitle("Restart du serveur", "Dans " + numtoshow + " secondes !");
            }
            Bukkit.broadcastMessage("\n§c§l RESTART DU SERVEUR DANS " + numtoshow + " SECONDES !\n");
            Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(Main.instance, () -> {
                int numtoshow1 = num / 3;
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.sendTitle("Restart du serveur", "Dans " + numtoshow1 + " secondes !");
                }
                Bukkit.broadcastMessage("\n§c§l RESTART DU SERVEUR DANS " + numtoshow1 + " SECONDES !\n");
                Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(Main.instance, () -> {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.sendTitle("Restart du serveur", "Téléportation au hub..");
                        ServersManager.instance.sendToServer("a", player, null, ServerType.SKYBLOCK_HUB);
                    }
                    Bukkit.shutdown();
                }, 20 * num / 3);
            }, 20 * num / 3);
        }, 20 * num / 3);
        return true;
    }
}
