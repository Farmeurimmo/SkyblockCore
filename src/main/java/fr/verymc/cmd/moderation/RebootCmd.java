package main.java.fr.verymc.cmd.moderation;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import main.java.fr.verymc.core.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RebootCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(args.length!=1){
            sender.sendMessage("Usage /reboot <temps en seconde>");
            return true;
        }
        int num = Integer.parseInt(args[0]);

        for(Player p : Bukkit.getOnlinePlayers()){
            p.sendTitle("Restart du serveur","dans "+num+"secondes");
        }
        Bukkit.broadcastMessage("\n§c§l RESTART DU SERVEUR DANS "+num+" SECONDES !\n");

        Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(Main.instance, new Runnable() {
            public void run() {
                for(Player player : Bukkit.getOnlinePlayers()){
                    final ByteArrayDataOutput out = ByteStreams.newDataOutput();
                    Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(Main.instance, "BungeeCord");
                    out.writeUTF("Connect");
                    out.writeUTF("lobby");
                    player.sendPluginMessage(Main.instance, "BungeeCord", out.toByteArray());
                }
                Bukkit.shutdown();
            }
        }, 20*num);

        return true;
    }
}
