package fr.farmeurimmo.criptmania.utils;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class SendActionBar {
	
	public static void SendActionBarMsg(Player player, String message) {
		
		TextComponent aa = new TextComponent(message);
		player.spigot().sendMessage(ChatMessageType.ACTION_BAR, aa);
		
	}

}
