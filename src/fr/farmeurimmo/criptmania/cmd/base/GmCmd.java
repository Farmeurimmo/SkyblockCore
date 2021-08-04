package fr.farmeurimmo.criptmania.cmd.base;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.farmeurimmo.criptmania.utils.SendActionBar;

public class GmCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("gm")) {
				if(args.length == 0) {
					SendActionBar.SendActionBarMsg(player, "§c/gm <0,1,2,3> [Joueur]");
					return true;
				} else if(args.length == 1) {
				if(args[0].equalsIgnoreCase("0")) {
					player.setGameMode(GameMode.SURVIVAL);
					SendActionBar.SendActionBarMsg(player, "§aVous venez de passer en survie");
					return true;
				} else if(args[0].equalsIgnoreCase("1")) {
					player.setGameMode(GameMode.CREATIVE);
					SendActionBar.SendActionBarMsg(player, "§aVous venez de passer en créatif");
					return true;
				} else if(args[0].equalsIgnoreCase("2")) {
					player.setGameMode(GameMode.ADVENTURE);
					SendActionBar.SendActionBarMsg(player, "§aVous venez de passer en adventure");
					return true;
				} else if(args[0].equalsIgnoreCase("3")) {
					player.setGameMode(GameMode.SPECTATOR);
					SendActionBar.SendActionBarMsg(player, "§aVous venez de passer en spectateur");
					return true;
				}
				else {
					SendActionBar.SendActionBarMsg(player, "§cGamemodes disponibles: 0,1,2,3");
					return true;
				}
				} else if(args.length == 2) {
					if(Bukkit.getPlayer(args[1]) != null) {
						if(Bukkit.getPlayer(args[1]).isOnline()) {
						Player p = Bukkit.getPlayer(args[1]);
						if(args[0].equalsIgnoreCase("0")) {
							p.setGameMode(GameMode.SURVIVAL);
							SendActionBar.SendActionBarMsg(p, "§aVous venez de passer en survie");
							return true;
						} else if(args[0].equalsIgnoreCase("1")) {
							p.setGameMode(GameMode.CREATIVE);
							SendActionBar.SendActionBarMsg(p, "§aVous venez de passer en créatif");
							return true;
						} else if(args[0].equalsIgnoreCase("2")) {
							p.setGameMode(GameMode.ADVENTURE);
							SendActionBar.SendActionBarMsg(p, "§aVous venez de passer en adventure");
							return true;
						} else if(args[0].equalsIgnoreCase("3")) {
							p.setGameMode(GameMode.SPECTATOR);
							SendActionBar.SendActionBarMsg(p, "§aVous venez de passer en spectateur");
							return true;
						}
						else {
							SendActionBar.SendActionBarMsg(player, "§cGamemodes disponibles: 0,1,2,3");
							return true;
						}
					}
						else {
							SendActionBar.SendActionBarMsg(player, "§cCe joueur n'est pas en ligne !");
						}
					}
					else {
						SendActionBar.SendActionBarMsg(player, "§cCe joueur n'existe pas !");
					}
				}
				else {
					SendActionBar.SendActionBarMsg(player, "§c/gm <0,1,2,3> [Joueur]");
				}
			}
		}
		return false;
	}

}
