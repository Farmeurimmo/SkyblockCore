package main.java.fr.verymc.velocity.cmd;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ServerConnection;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import main.java.fr.verymc.velocity.Main;
import net.kyori.adventure.text.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class SkyblockCmd implements SimpleCommand {

    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        if (!(source instanceof Player)) {
            source.sendMessage(Component.text("§6§lDongeons §8» §cVous devez être un joueur pour utiliser cette commande."));
        }

        assert source instanceof Player;
        Player player = (Player) source;
        String[] args = invocation.arguments();

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("maintenance")) {
                if (!player.hasPermission("skyblock.maintenance")) {
                    player.sendMessage(Component.text("§6§lDongeons §8» §cVous n'avez pas la permission pour utiliser cette commande."));
                    return;
                }
                if (Main.instance.maintenance) {
                    Main.instance.maintenance = false;
                    player.sendMessage(Component.text("§6§lSkyblock §8» §cMaintenance désactivée."));
                } else {
                    Main.instance.maintenance = true;
                    player.sendMessage(Component.text("§6§lSkyblock §8» §aMaintenance activée."));
                    Main.instance.startMaintenanceModule();
                }
                return;
            }
            if (args[0].equalsIgnoreCase("status")) {
                player.sendMessage(Component.text("§6§lSkyblock §8» §aStatus : " + (Main.instance.maintenance ? "§cMaintenance" : "§aEn ligne")));
                return;
            }
        }

        Optional<ServerConnection> currentServer = player.getCurrentServer();
        if (currentServer == null) {
            player.sendMessage(Component.text("§6§lSkyblock §8» §cErreur lors de la récupération de votre serveur actuel."));
            return;
        }
        if (Main.instance.isSkyblockServer(currentServer.get().getServer())) {
            player.sendMessage(Component.text("§6§lSkyblock §8» §cVous êtes déjà sur un serveur Skyblock."));
            return;
        }
        if (player.getProtocolVersion().getProtocol() < 754) {
            player.sendMessage(Component.text("§6§lSkyblock §8» §cVous devez être connecté avec une version supérieure " +
                    "ou égale à la 1.16.5 pour pouvoir jouer en skyblock."));
            return;
        }
        if (Main.instance.maintenance) {
            player.sendMessage(Component.text("§6§lSkyblock §8» §cLes serveurs Skyblock sont actuellement en maintenance."));
            return;
        }
        RegisteredServer registeredServer = Main.instance.getServeurToLogin();
        if (registeredServer == null) {
            player.sendMessage(Component.text("§6§lSkyblock §8» §cErreur lors de la récupération d'un serveur Skyblock, " +
                    "merci de réessayer plus tard."));
            return;
        }
        player.createConnectionRequest(registeredServer);
    }

    /*@Override
    public boolean hasPermission(Invocation invocation) {
        return SimpleCommand.super.hasPermission(invocation);
    }*/

    @Override
    public CompletableFuture<List<String>> suggestAsync(Invocation invocation) {
        ArrayList<String> toReturn = new ArrayList<>();
        return CompletableFuture.completedFuture(toReturn);
    }
}
