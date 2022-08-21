package main.java.fr.verymc.velocity.cmd;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import main.java.fr.verymc.spigot.dungeon.DungeonFloors;
import main.java.fr.verymc.velocity.DungeonQueueManager;
import main.java.fr.verymc.velocity.Main;
import main.java.fr.verymc.velocity.team.DungeonTeam;
import main.java.fr.verymc.velocity.team.DungeonTeamManager;
import net.kyori.adventure.text.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public final class DungeonCmd implements SimpleCommand {

    @Override
    public void execute(final Invocation invocation) {
        CommandSource source = invocation.source();
        if (!(source instanceof Player)) {
            source.sendMessage(Component.text("§6§lDongeons §8» §cVous devez être un joueur pour utiliser cette commande."));
        }

        assert source instanceof Player;
        Player player = (Player) source;
        String[] args = invocation.arguments();

        /*if (!Main.instance.isSkyblockServer(player.getCurrentServer().get().getServer())) {
            player.sendMessage(Component.text("§6§lDongeons §8» §cVous devez être sur un serveur Skyblock pour utiliser cette commande."));
            return;
        }*/

        DungeonTeam dungeonTeam = DungeonTeamManager.instance.getPlayerTeam(player);
        boolean haveATeam = (dungeonTeam != null);

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("create")) {
                if (haveATeam) {
                    player.sendMessage(Component.text("§6§lDongeons §8» §cVous avez déjà une team, pour en recréer une quitter ou " +
                            "supprimez la précédente."));
                    return;
                }
                DungeonTeamManager.instance.createTeam(player);
                player.sendMessage(Component.text("§6§lDongeons §8» §fVous avez créé une team. L'étage le plus bas a été séléctionné par défaut," +
                        " /dongeon etage <étage> pour la changer."));
                player.sendMessage(Component.text("§c⚠ §lLa perte de stuff est active dans les dungeons§c ⚠"));
                return;
            }
            if (args[0].equalsIgnoreCase("join")) {
                if (haveATeam) {
                    player.sendMessage(Component.text("§6§lDongeons §8» §cVous avez déjà une team, quittez la ou supprimez la " +
                            "pour en rejoindre une autre."));
                    return;
                }
                player.sendMessage(Component.text("§6§lDongeons §8» §cMerci de préciser le nom d'un joueur de la team."));
                return;
            }
        }
        if (args.length == 2) {
            if (haveATeam) {
                if (args[0].equalsIgnoreCase("etage")) {
                    if (!dungeonTeam.isOwner(player)) {
                        player.sendMessage(Component.text("§6§lDongeons §8» §cVous devez être le propriétaire de la team pour faire ceci."));
                        return;
                    }
                    if (DungeonFloors.valueOf(args[1]) != null) {
                        DungeonFloors floor = DungeonFloors.valueOf(args[1]);
                        dungeonTeam.setFloor(floor);
                        player.sendMessage(Component.text("§6§lDongeons §8» §fVous avez changé l'éatge du dongeon en " + floor + " (" + floor.getName() + ")."));
                    } else {
                        player.sendMessage(Component.text("§6§lDongeons §8» §cCet étage n'existe pas."));
                    }
                    return;
                }
            }
            Optional<Player> playerOptional = Main.instance.getPlayer(args[1]);
            if (playerOptional.isEmpty()) {
                player.sendMessage(Component.text("§6§lDongeons §8» §cCe joueur n'existe pas ou n'est pas en ligne."));
                return;
            }
            if (args[0].equalsIgnoreCase("join")) {
                if (haveATeam) {
                    player.sendMessage(Component.text("§6§lDongeons §8» §cVous avez déjà une team."));
                    return;
                }
                Player target = playerOptional.get();
                DungeonTeam targetTeam = DungeonTeamManager.instance.getPlayerTeam(target);
                if (targetTeam == null) {
                    player.sendMessage(Component.text("§6§lDongeons §8» §cCe joueur n'a pas de team."));
                    return;
                }
                if (!targetTeam.isOpen() && !targetTeam.isPendingInvite(player.getUniqueId())) {
                    player.sendMessage(Component.text("§6§lDongeons §8» §cCette team n'accepte que les invitations et vous n'en avez pas reçu."));
                    return;
                }
                if (targetTeam.isFullForFloor()) {
                    player.sendMessage(Component.text("§6§lDongeons §8» §cCette team est déjà complète."));
                    return;
                }
                if (targetTeam.isPendingInvite(player.getUniqueId())) {
                    targetTeam.removePendingInvite(player.getUniqueId());
                }
                targetTeam.sendMessageToEveryone("§6§lDongeons §8» §f" + player.getUsername() + " a rejoint la team.");
                DungeonTeamManager.instance.addPlayerToTeam(targetTeam, player);
                player.sendMessage(Component.text("§6§lDongeons §8» §fVous avez rejoint la team de " + target.getUsername() + "."));
                player.sendMessage(Component.text("§c⚠ §lLa perte de stuff est active dans les dungeons§c ⚠"));
                return;
            }
        }
        if (!haveATeam) {
            player.sendMessage(Component.text("§6§lDongeons §8» §cVous n'avez pas de team. Merci d'en créer une avec /dongeon create."));
            sendErrorUsage(player);
            return;
        }

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("start")) {
                if (!dungeonTeam.isFullForFloor()) {
                    player.sendMessage(Component.text("§6§lDongeons §8» §cVous n'avez pas assez de joueurs dans votre team pour cet étage."));
                    return;
                }
                DungeonQueueManager.instance.queueTeam(dungeonTeam);
                return;
            }
            if (args[0].equalsIgnoreCase("invite")) {
                player.sendMessage(Component.text("§6§lDongeons §8» §cMerci de préciser le nom d'un joueur à inviter."));
                return;
            }
            if (args[0].equalsIgnoreCase("kick")) {
                player.sendMessage(Component.text("§6§lDongeons §8» §cMerci de préciser le nom d'un joueur à kick."));
                return;
            }
            if (args[0].equalsIgnoreCase("tchat")) {
                if (dungeonTeam.isTchatMode(player.getUniqueId())) {
                    dungeonTeam.removeTchatMode(player.getUniqueId());
                    player.sendMessage(Component.text("§6§lDongeons §8» §fVous avez désactivé le tchat de team."));
                } else {
                    dungeonTeam.addTchatMode(player.getUniqueId());
                    player.sendMessage(Component.text("§6§lDongeons §8» §fVous avez activé le tchat de team."));
                }
                return;
            }
            if (args[0].equalsIgnoreCase("membres")) {
                String players = "§7";
                for (Player player1 : dungeonTeam.getPlayers()) {
                    players += (dungeonTeam.isOwner(player1) ? "§a" : "§7") + player1.getUsername() + " ";
                }
                player.sendMessage(Component.text("§6§lDongeons §8» §fIl y a " + dungeonTeam.getPlayers().size() + " membres.\n" + players));
                return;
            }
            if (args[0].equalsIgnoreCase("leave")) {
                if (dungeonTeam.isOwner(player)) {
                    player.sendMessage(Component.text("§6§lDongeons §8» §cVous ne pouvez pas quitter votre team car vous êtes le propriétaire," +
                            " supprimer la avec /dongeon delete."));
                    return;
                }
                DungeonTeamManager.instance.removePlayerFromTeam(dungeonTeam, player);
                dungeonTeam.sendMessageToEveryone("§6§lDongeons §8» §f" + player.getUsername() + " a quitté la team.");
                player.sendMessage(Component.text("§6§lDongeons §8» §fVous avez quitté votre équipe."));
                return;
            }
            if (!dungeonTeam.isOwner(player)) {
                player.sendMessage(Component.text("§6§lDongeons §8» §cVous n'êtes pas le propriétaire de cette équipe."));
                return;
            }
            if (args[0].equalsIgnoreCase("delete")) {
                if (DungeonTeamManager.instance.isPlayerInConfirmation(player)) {
                    DungeonTeamManager.instance.removeTeam(dungeonTeam);
                    DungeonTeamManager.instance.removePlayerFromConfirmation(player);
                    dungeonTeam.sendMessageToEveryone("§6§lDongeons §8» §f" + player.getUsername() + " a supprimé la team.");
                    return;
                }
                DungeonTeamManager.instance.addPlayerToConfirmation(player);
                player.sendMessage(Component.text("§6§lDongeons §8» §fVous avez demandé à supprimer la team, " +
                        "tapez §c/dongeon delete §fpour confirmer, vous avez §c10 §csecondes."));
                return;
            }
            if (args[0].equalsIgnoreCase("invitation")) {
                if (dungeonTeam.isOpen()) {
                    dungeonTeam.setIsOpen(false);
                    player.sendMessage(Component.text("§6§lDongeons §8» §fPassage de la team en mode invitation seulement."));
                    return;
                }
                dungeonTeam.setIsOpen(true);
                player.sendMessage(Component.text("§6§lDongeons §8» §fConditions pour rejoindre la team passé en mode libre."));
                return;
            }
        }
        if (args.length == 2) {
            Optional<Player> playerOptional = Main.instance.getPlayer(args[1]);
            if (playerOptional.isEmpty()) {
                player.sendMessage(Component.text("§6§lDongeons §8» §cCe joueur n'existe pas ou n'est pas en ligne."));
                return;
            }
            if (!dungeonTeam.isOwner(player)) {
                player.sendMessage(Component.text("§6§lDongeons §8» §cVous n'êtes pas le propriétaire de cette équipe."));
                return;
            }
            if (args[0].equalsIgnoreCase("invite")) {
                Player invitedPlayer = playerOptional.get();
                if (dungeonTeam.isPlayerInTeam(invitedPlayer)) {
                    player.sendMessage(Component.text("§6§lDongeons §8» §cCe joueur est déjà dans votre équipe."));
                    return;
                }
                if (DungeonTeamManager.instance.isInATeam(invitedPlayer)) {
                    player.sendMessage(Component.text("§6§lDongeons §8» §cCe joueur est déjà dans une team."));
                    return;
                }
                if (dungeonTeam.isPendingInvite(invitedPlayer.getUniqueId())) {
                    player.sendMessage(Component.text("§6§lDongeons §8» §cCe joueur a déjà une invitation en attente pour votre team."));
                    return;
                }
                dungeonTeam.addPendingInvite(invitedPlayer.getUniqueId());
                DungeonTeamManager.instance.makeInviteExpireForPlayer(invitedPlayer, dungeonTeam, player.getUsername());
                invitedPlayer.sendMessage(Component.text("§6§lDongeons §8» §f" + player.getUsername() + " vous a invité à rejoindre sa team. " +
                        "Faites /dongeon join " + player.getUsername() + " pour rejoindre sa team. L'invitation expire dans §c30 secondes."));
                player.sendMessage(Component.text("§6§lDongeons §8» §fVous avez invité " + invitedPlayer.getUsername() + " à rejoindre votre team."));
                return;
            }
            if (args[0].equalsIgnoreCase("kick")) {
                Player kickedPlayer = playerOptional.get();
                if (!dungeonTeam.isPlayerInTeam(kickedPlayer)) {
                    player.sendMessage(Component.text("§6§lDongeons §8» §cCe joueur n'est pas dans votre team."));
                    return;
                }
                if (!dungeonTeam.isOwner(player)) {
                    player.sendMessage(Component.text("§6§lDongeons §8» §cVous n'avez pas la permission de faire ceci."));
                    return;
                }
                if (kickedPlayer.equals(player)) {
                    player.sendMessage(Component.text("§6§lDongeons §8» §cVous ne pouvez pas vous expulser de votre team."));
                    return;
                }
                DungeonTeamManager.instance.removePlayerFromTeam(dungeonTeam, kickedPlayer);
                dungeonTeam.sendMessageToEveryone("§6§lDongeons §8» §f" + player.getUsername() + " a kick " + kickedPlayer.getUsername() + ".");
                kickedPlayer.sendMessage(Component.text("§6§lDongeons §8» §fVous avez été kick de la team par " + player.getUsername() + "."));
                return;
            }
        }

        sendErrorUsage(player);
    }

    public void sendErrorUsage(Player player) {
        player.sendMessage(Component.text("§6§lDongeons §8» §cUsage: /dongeon <join|leave|create|delete|invite|invitation|kick|tchat|start|etage> [Joueur/Étage]"));
    }

    /*@Override
    public boolean hasPermission(final Invocation invocation) {
        return invocation.source().hasPermission("dungeon");
    }*/

    @Override
    public CompletableFuture<List<String>> suggestAsync(final Invocation invocation) {
        if (!(invocation.source() instanceof Player)) {
            return CompletableFuture.completedFuture(List.of());
        }
        ArrayList<String> toReturn = new ArrayList<>();
        String[] args = invocation.arguments();
        Player player = (Player) invocation.source();
        DungeonTeam dungeonTeam = DungeonTeamManager.instance.getPlayerTeam(player);
        boolean haveATeam = (dungeonTeam != null);
        switch (args.length) {
            case 1:
                toReturn.addAll(Arrays.asList("join", "leave", "create", "delete", "invitation", "invite", "create", "kick", "membres", "tchat", "start", "etage", "invite"));
                break;
            case 2:
                if (args[0].equalsIgnoreCase("etage")) {
                    for (DungeonFloors floor : DungeonFloors.values()) {
                        toReturn.add(floor.toString());
                    }
                    return CompletableFuture.completedFuture(toReturn);
                }
                if (args[0].equalsIgnoreCase("join")) {
                    ArrayList<Player> hasTeam = DungeonTeamManager.instance.getPlayerWhoAreInATeam();
                    hasTeam.forEach(player1 -> toReturn.add(player1.getUsername()));
                    return CompletableFuture.completedFuture(toReturn);
                }

                if (!haveATeam) return CompletableFuture.completedFuture(toReturn);

                if (args[0].equalsIgnoreCase("invite")) {
                    ArrayList<Player> without = Main.instance.getPlayerWithout(dungeonTeam.getPlayers());
                    without.forEach(player1 -> toReturn.add(player1.getUsername()));
                    return CompletableFuture.completedFuture(toReturn);
                }

                if (args[0].equalsIgnoreCase("kick")) {
                    ArrayList<Player> inTeam = dungeonTeam.getPlayers();
                    inTeam.forEach(player1 -> toReturn.add(player1.getUsername()));
                    toReturn.remove(player.getUsername());
                    return CompletableFuture.completedFuture(toReturn);
                }
                break;
        }
        return CompletableFuture.completedFuture(toReturn);
    }


}
