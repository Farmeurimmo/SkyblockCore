package fr.farmeurimmo.premsi.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import com.iridium.iridiumskyblock.api.IridiumSkyblockAPI;

import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;

@SuppressWarnings("deprecation")
public class Tchat implements Listener {
	
	@EventHandler
	public void OnMessage(PlayerChatEvent event) {
		Player player = event.getPlayer();
		String message = event.getMessage();
		String Grade = "N/A";
		User user = LuckPermsProvider.get().getUserManager().getUser(player.getName());
		if(user.getCachedData().getMetaData().getPrefix() != null) {
			Grade = user.getCachedData().getMetaData().getPrefix().replace("&l", "").replace("&", "§");
		}
		int level = 0;
		if(IridiumSkyblockAPI.getInstance().getUser(player).getIsland().isPresent()) {
			level = IridiumSkyblockAPI.getInstance().getUser(player).getIsland().get().getRank();
			String NewMessage = "§7[#" + level + "] " + Grade + " " + player.getName() + "§7: " + message;
			event.setFormat(NewMessage);
		}
		else {
			String NewMessage = "§7" + Grade + " " + player.getName() + "§7: " + message;
			event.setFormat(NewMessage);
		}
	}

}
