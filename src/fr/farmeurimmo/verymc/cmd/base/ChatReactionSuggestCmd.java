package fr.farmeurimmo.verymc.cmd.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.farmeurimmo.verymc.core.Main;

public class ChatReactionSuggestCmd implements CommandExecutor {
	
	List<String> list = Arrays.asList("VeryMc");
	public static ArrayList<String> mots = new ArrayList<String>();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(args.length == 0 || args.length >= 2) {
				player.sendMessage("§6§lChatReaction §8» §fVeuillez utiliser /chatreactionsuggest <mots> pour sugérer un mot au chatreaction.");
			} else {
				String mot = args[0];
				if(mot.length() >= 4) {
					if(Main.instance1.getDataa().getString("suggestedwords") == null) {
						Main.instance1.getDataa().set("suggestedwords", "");
					}
					if(Main.instance1.getDataa().get("suggestedwords."+player.getName()) == null) {
					Main.instance1.getDataa().set("suggestedwords."+player.getName(), mot);
					player.sendMessage("§6§lChatReaction §8» §fLe mot " + mot + " a été sugéré avec succès, il sera proposé à un membre du staff sous peu.");
					} else {
						player.sendMessage("§6§lChatReaction §8» §fLe mot " + mot + " n'a pas pu être sugéré car vous avez déjà une suggestion en cours.");
					}
					Main.instance1.saveData();
				} else {
					player.sendMessage("§6§lChatReaction §8» §fVeuillez sugérer un mot avec plus de 4 lettre !");
				}
			}
		}
		
		return false;
	}

}
