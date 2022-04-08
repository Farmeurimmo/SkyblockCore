package main.java.fr.verymc.events;

import main.java.fr.verymc.island.Island;
import main.java.fr.verymc.island.IslandManager;
import main.java.fr.verymc.island.guis.IslandTopGui;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

public class TchatManager implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onAsyncChat(PlayerChatEvent e) {
        Player player = e.getPlayer();
        Island playerIsland = IslandManager.instance.getPlayerIsland(player);
        String Prefix = "§7N/A";
        String Suffix = "";
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
        boolean isIslandChat = playerIsland.isIslandChatToggled(player.getUniqueId());

        User user = LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId());
        if (user.getCachedData().getMetaData().getPrefix() != null) {
            Prefix = user.getCachedData().getMetaData().getPrefix();
        }
        if (user.getCachedData().getMetaData().getSuffix() != null) {
            Suffix = " " + user.getCachedData().getMetaData().getSuffix();
        }
        if (!isIslandChat) {
            TextComponent message = new TextComponent();
            TextComponent symbole = new TextComponent();
            if (playerIsland == null) {
                message.setText(Prefix + " " + player.getName() + Suffix + "§7: " + e.getMessage());
            } else {
                String classement = "#N/A";
                if (IslandTopGui.instance.getTopIsland().containsKey(playerIsland)) {
                    classement = "#" + IslandTopGui.instance.getTopIsland().get(playerIsland);
                }
                message.setText("§7[" + classement + "] " + Prefix + " " + player.getName() + Suffix + "§7: " + e.getMessage());
            }
            symbole.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§cCliquez ici pour report le message de " +
                    player.getName()).create()));
            message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("").create()));
            symbole.setText("§cx ");
            symbole.addExtra(message);
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.sendMessage(symbole);
            }
        } else {
            TextComponent message = new TextComponent();
            TextComponent symbole = new TextComponent();
            message.setText("§6§lTchat d'île §8» §f[§a" + playerIsland.getMembers().get(player.getUniqueId()) + "§f] " + Prefix + " " + player.getName() +
                    Suffix + "§7: " + e.getMessage());
            symbole.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§cCliquez ici pour report le message de " +
                    player.getName()).create()));
            message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("").create()));
            symbole.setText("§cx ");
            symbole.addExtra(message);
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (IslandManager.instance.isSpying(player.getUniqueId())) {
                    p.sendMessage(symbole);
                    continue;
                }
                if (playerIsland.isInIsland(p.getUniqueId())) {
                    p.sendMessage(symbole);
                    continue;
                }
            }
        }
    }
}
