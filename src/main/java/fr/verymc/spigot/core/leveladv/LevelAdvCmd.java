package main.java.fr.verymc.spigot.core.leveladv;

import main.java.fr.verymc.spigot.core.storage.SkyblockUser;
import main.java.fr.verymc.spigot.core.storage.SkyblockUserManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;

public class LevelAdvCmd implements CommandExecutor {

    @Override
    public boolean onCommand(org.bukkit.command.CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cVous devez être un joueur pour utiliser cette commande.");
            return false;
        }
        Player player = (Player) sender;
        if (player.hasPermission("*")) {
            if (args.length == 3) {
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    player.sendMessage("§cLe joueur n'est pas connecté ou n'existe pas.");
                    return false;
                }
                SkyblockUser skyblockUser = SkyblockUserManager.instance.getUser(target);
                if (args[0].equalsIgnoreCase("get")) {
                    player.sendMessage("§aLe niveau de " + target.getName() + " est " +
                            LevelAdvManager.instance.getLevelFormatted(skyblockUser) + " (" +
                            LevelAdvManager.instance.getExpFormatted(skyblockUser) + "/" +
                            LevelAdvManager.instance.getExpToGetForNextLevelFormatted(skyblockUser.getLevel()) + ")");
                    return false;
                }
                Double lvl = Double.parseDouble(args[2]);
                if (args[0].equalsIgnoreCase("set")) {
                    LevelAdvManager.instance.setLevel(SkyblockUserManager.instance.getUser(player), lvl);
                    player.sendMessage("§aVous avez défini le niveau de " + target.getName() + " à " + lvl);
                }
                return false;
            }
        }
        LevelAdvGuis.instance.openPlayerMainLevel(player);
        return false;
    }
}
