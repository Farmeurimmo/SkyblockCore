package main.java.fr.verymc.velocity.cmd;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
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
            source.sendMessage(Component.text("§6§lDungeon §8» §cVous devez être un joueur pour utiliser cette commande."));
        }

        assert source instanceof Player;
        Player player = (Player) source;
        String[] args = invocation.arguments();

        DungeonTeam dungeonTeam = DungeonTeamManager.instance.getPlayerTeam(player);
        boolean haveATeam = (dungeonTeam != null);

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("create")) {
                if (haveATeam) {
                    player.sendMessage(Component.text("§6§lDungeon §8» §cVous avez déjà une team, pour en recréer une quitter ou " +
                            "supprimez la précédente."));
                    return;
                }
                DungeonTeamManager.instance.createTeam(player);
                player.sendMessage(Component.text("§6§lDungeon §8» §fVous avez créé une team."));
                return;
            }
            if (args[0].equalsIgnoreCase("join")) {
                if (haveATeam) {
                    player.sendMessage(Component.text("§6§lDungeon §8» §cVous avez déjà une team, quittez la ou supprimez la " +
                            "pour en rejoindre une autre."));
                    return;
                }
                player.sendMessage(Component.text("§6§lDungeon §8» §cMerci de préciser le nom d'un joueur de la team."));
                return;
            }
        }
        if (args.length == 2) {
            Optional<Player> playerOptional = Main.instance.getPlayer(args[1]);
            if (playerOptional.isEmpty()) {
                player.sendMessage(Component.text("§6§lDungeon §8» §cCe joueur n'existe pas ou n'est pas en ligne."));
                return;
            }
            if (args[0].equalsIgnoreCase("join")) {
                if (haveATeam) {
                    player.sendMessage(Component.text("§6§lDungeon §8» §cVous avez déjà une team."));
                    return;
                }
                Player target = playerOptional.get();
                DungeonTeam targetTeam = DungeonTeamManager.instance.getPlayerTeam(target);
                if (targetTeam == null) {
                    player.sendMessage(Component.text("§6§lDungeon §8» §cCe joueur n'a pas de team."));
                    return;
                }
                if (!targetTeam.isOpen()) {
                    player.sendMessage(Component.text("§6§lDungeon §8» §cCette team n'accepte que les invitations."));
                    return;
                }
                DungeonTeamManager.instance.addPlayerToTeam(targetTeam, player);
                return;
            }
        }
        if (!haveATeam) {
            player.sendMessage(Component.text("§6§lDungeon §8» §cVous n'avez pas de team. Merci d'en créer une avec /dungeon create."));
            sendErrorUsage(player);
            return;
        }

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("invite")) {
                player.sendMessage(Component.text("§6§lDungeon §8» §cMerci de préciser le nom d'un joueur à inviter."));
                return;
            }
            if (args[0].equalsIgnoreCase("leave")) {
                if (dungeonTeam.isOwner(player)) {
                    player.sendMessage(Component.text("§6§lDungeon §8» §cVous ne pouvez pas quitter votre team car vous êtes le propriétaire," +
                            " supprimer la avec /dungeon delete, vous avez §c10 §csecondes."));
                    return;
                }
                DungeonTeamManager.instance.removePlayerFromTeam(dungeonTeam, player);
                player.sendMessage(Component.text("§6§lDungeon §8» §fVous avez quitté votre équipe."));
                return;
            }
            if (!dungeonTeam.isOwner(player)) {
                player.sendMessage(Component.text("§6§lDungeon §8» §cVous n'êtes pas le propriétaire de cette équipe."));
                return;
            }
            if (args[0].equalsIgnoreCase("delete")) {
                if (DungeonTeamManager.instance.isPlayerInConfirmation(player)) {
                    DungeonTeamManager.instance.removeTeam(dungeonTeam);
                    DungeonTeamManager.instance.removePlayerFromConfirmation(player);
                    player.sendMessage(Component.text("§6§lDungeon §8» §fVous avez supprimé l'équipe."));
                    return;
                }
                DungeonTeamManager.instance.addPlayerToConfirmation(player);
                player.sendMessage(Component.text("§6§lDungeon §8» §fVous avez demandé à supprimer la team, " +
                        "tapez §c/dungeon delete §fpour confirmer."));
                return;
            }
            if (args[0].equalsIgnoreCase("invitation")) {
                if (dungeonTeam.isOpen()) {
                    dungeonTeam.setIsOpen(false);
                    player.sendMessage(Component.text("§6§lDungeon §8» §fPassage de la team en mode invitation seulement."));
                    return;
                }
                dungeonTeam.setIsOpen(true);
                player.sendMessage(Component.text("§6§lDungeon §8» §fConditions pour rejoindre la team passé en mode libre."));
                return;
            }
        }
        if (args.length == 2) {
            Optional<Player> playerOptional = Main.instance.getPlayer(args[1]);
            if (playerOptional.isEmpty()) {
                player.sendMessage(Component.text("§6§lDungeon §8» §cCe joueur n'existe pas ou n'est pas en ligne."));
                return;
            }
            if (!dungeonTeam.isOwner(player)) {
                player.sendMessage(Component.text("§6§lDungeon §8» §cVous n'êtes pas le propriétaire de cette équipe."));
                return;
            }
            if (args[0].equalsIgnoreCase("invite")) {
                Player invitedPlayer = playerOptional.get();
                if (dungeonTeam.isPlayerInTeam(invitedPlayer)) {
                    player.sendMessage(Component.text("§6§lDungeon §8» §cCe joueur est déjà dans votre équipe."));
                    return;
                }
                if (DungeonTeamManager.instance.isInATeam(invitedPlayer)) {
                    player.sendMessage(Component.text("§6§lDungeon §8» §cCe joueur est déjà dans une team."));
                    return;
                }
                if (dungeonTeam.isPendingInvite(invitedPlayer.getUniqueId())) {
                    player.sendMessage(Component.text("§6§lDungeon §8» §cCe joueur a déjà une invitation en attente pour votre team."));
                    return;
                }
                dungeonTeam.addPendingInvite(invitedPlayer.getUniqueId());
                DungeonTeamManager.instance.makeInviteExpireForPlayer(invitedPlayer, dungeonTeam);
                return;
            }
        }

        sendErrorUsage(player);
    }

    public void sendErrorUsage(Player player) {
        player.sendMessage(Component.text("§6§lDungeon §8» §cUsage: /dungeon <join|leave|create|delete|invite|invitation> [Joueur]"));
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
                toReturn.addAll(Arrays.asList("join", "leave", "create", "delete", "invitation", "invite", "create"));
                break;
            case 2:
                if (!haveATeam) {
                    return CompletableFuture.completedFuture(List.of());
                }
                if (args[1].equalsIgnoreCase("invite")) {
                    ArrayList<Player> without = Main.instance.getPlayerWithout(dungeonTeam.getPlayers());
                    without.forEach(player1 -> toReturn.add(player1.getUsername()));
                }
                break;
        }
        return CompletableFuture.completedFuture(toReturn);
    }


}
