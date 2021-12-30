package main.java.fr.verymc.utils;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class SendActionBar {

    public static void SendActionBarMsg(Player player, String message) {
        TextComponent aa = new TextComponent(message);
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, aa);
    }

}
