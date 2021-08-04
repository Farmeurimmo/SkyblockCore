package fr.farmeurimmo.criptmania.cmd.base;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.farmeurimmo.criptmania.utils.SendActionBar;

public class GmLCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("*")) {
				if(cmd.getName().equalsIgnoreCase("gma")) {
					if(args.length == 0) {
						player.setGameMode(GameMode.ADVENTURE);
						SendActionBar.SendActionBarMsg(player, "§aVous venez de passer en adventure");
					} else if (args.length == 1){
						if(Bukkit.getPlayer(args[0]) != null) {
							if(Bukkit.getPlayer(args[0]).isOnline()) {
							Player p = Bukkit.getPlayer(args[0]);
							p.setGameMode(GameMode.ADVENTURE);
							SendActionBar.SendActionBarMsg(p, "§aVous venez de passer en adventure");
							return true;
							} else {
								SendActionBar.SendActionBarMsg(player, "§cCe joueur n'est pas en ligne !");
							}
						} else {
							SendActionBar.SendActionBarMsg(player, "§cCe joueur n'existe pas !");
						}
					} else {
						SendActionBar.SendActionBarMsg(player, "§c/gma [Joueur]");
					}
				} else if(cmd.getName().equalsIgnoreCase("gmc")) {
					if(args.length == 0) {
						player.setGameMode(GameMode.CREATIVE);
						SendActionBar.SendActionBarMsg(player, "§aVous venez de passer en créatif");
					} else if (args.length == 1){
						if(Bukkit.getPlayer(args[0]) != null) {
							if(Bukkit.getPlayer(args[0]).isOnline()) {
							Player p = Bukkit.getPlayer(args[0]);
							p.setGameMode(GameMode.CREATIVE);
							SendActionBar.SendActionBarMsg(p, "§aVous venez de passer en créatif");
							return true;
							} else {
								SendActionBar.SendActionBarMsg(player, "§cCe joueur n'est pas en ligne !");
							}
						} else {
							SendActionBar.SendActionBarMsg(player, "§cCe joueur n'existe pas !");
						}
					} else {
						SendActionBar.SendActionBarMsg(player, "§c/gmc [Joueur]");
					}
				} else if(cmd.getName().equalsIgnoreCase("gms")) {
					if(args.length == 0) {
						player.setGameMode(GameMode.SURVIVAL);
						SendActionBar.SendActionBarMsg(player, "§aVous venez de passer en survie");
					} else if (args.length == 1){
						if(Bukkit.getPlayer(args[0]) != null) {
							if(Bukkit.getPlayer(args[0]).isOnline()) {
							Player p = Bukkit.getPlayer(args[0]);
							p.setGameMode(GameMode.SURVIVAL);
							SendActionBar.SendActionBarMsg(p, "§aVous venez de passer en survie");
							return true;
							} else {
								SendActionBar.SendActionBarMsg(player, "§cCe joueur n'est pas en ligne !");
							}
						} else {
							SendActionBar.SendActionBarMsg(player, "§cCe joueur n'existe pas !");
						}
					} else {
						SendActionBar.SendActionBarMsg(player, "§c/gms [Joueur]");
					}
				} else if(cmd.getName().equalsIgnoreCase("gmsp")) {
					if(args.length == 0) {
						player.setGameMode(GameMode.SPECTATOR);
						SendActionBar.SendActionBarMsg(player, "§aVous venez de passer en spectateur");
					} else if (args.length == 1){
						if(Bukkit.getPlayer(args[0]) != null) {
							if(Bukkit.getPlayer(args[0]).isOnline()) {
							Player p = Bukkit.getPlayer(args[0]);
							p.setGameMode(GameMode.SPECTATOR);
							SendActionBar.SendActionBarMsg(p, "§aVous venez de passer en spectateur");
							return true;
							} else {
								SendActionBar.SendActionBarMsg(player, "§cCe joueur n'est pas en ligne !");
							}
						} else {
							SendActionBar.SendActionBarMsg(player, "§cCe joueur n'existe pas !");
						}
					} else {
						SendActionBar.SendActionBarMsg(player, "§c/gmsp [Joueur]");
					}
				}
			}
		}
		return false;
	}

}
