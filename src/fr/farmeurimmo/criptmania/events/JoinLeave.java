package fr.farmeurimmo.criptmania.events;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.iridium.iridiumskyblock.api.IridiumSkyblockAPI;

import fr.farmeurimmo.criptmania.Main;
import fr.farmeurimmo.criptmania.featherfly.CountdownFly;
import fr.farmeurimmo.criptmania.items.PermanantItem;
import fr.farmeurimmo.criptmania.scoreboard.ScoreBoard;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;

public class JoinLeave implements Listener {
	
	String Grade = "�7N/A";
	
	@EventHandler
	public void OnJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		
		final Location Spawn = new Location(Bukkit.getServer().getWorld("world"), -186.5, 110, -63.5, 0, 0);
		player.teleport(Spawn);
		
		ItemStack custom1 = new ItemStack(Material.NETHER_STAR, 1);
		ItemMeta customS = custom1.getItemMeta();
		customS.setUnbreakable(true);
		customS.setDisplayName(PermanantItem.NameMenuItem);
		custom1.setItemMeta(customS);
		
		player.getInventory().setItem(8, custom1);
		
		player.setGameMode(GameMode.SURVIVAL);
		
		ScoreBoard.setScoreBoard(player);
		
		CountdownFly.CountDown(player);
		
			WorldBorder border = Bukkit.getWorld("world").getWorldBorder();
			border.setCenter(-186, -63);
			border.setSize(200);
			border.setDamageAmount(1);
		
		User user = LuckPermsProvider.get().getUserManager().getUser(player.getName());
		if(user.getCachedData().getMetaData().getPrefix() != null) {
		Grade = user.getCachedData().getMetaData().getPrefix();
		}
		String JoinMessage = null;
		if(!IridiumSkyblockAPI.getInstance().getUser(player).getIsland().isPresent()) {
			JoinMessage = "�7[�a+�7] " + Grade.replace("&", "�").replace("&", "�") + player.getName();
			}
			else {
				int classement = 0;
				classement = IridiumSkyblockAPI.getInstance().getUser(player).getIsland().get().getRank();
				JoinMessage = "�7[�a+�7] [#" + classement + "] " + Grade.replace("&", "�").replace("&", "�") + player.getName();
			}
			event.setJoinMessage(JoinMessage);
			
			if(Main.instance1.getData().getString("Joueurs."+player.getName()+".Challenges.Daily.1.Active") == null) {
				Main.instance1.getData().set("Joueurs."+player.getName()+".Challenges.Daily.1.Active", true);
				Main.instance1.getData().set("Joueurs."+player.getName()+".Challenges.Daily.1.Progression", 0);
				Main.instance1.saveData();
			}
			if(Main.instance1.getData().getString("Joueurs."+player.getName()+".Challenges.Daily.2.Active") == null) {
				Main.instance1.getData().set("Joueurs."+player.getName()+".Challenges.Daily.2.Active", true);
				Main.instance1.getData().set("Joueurs."+player.getName()+".Challenges.Daily.2.Progression", 0);
				Main.instance1.saveData();
			}
			if(Main.instance1.getData().getString("Joueurs."+player.getName()+".Challenges.Daily.3.Active") == null) {
				Main.instance1.getData().set("Joueurs."+player.getName()+".Challenges.Daily.3.Active", true);
				Main.instance1.getData().set("Joueurs."+player.getName()+".Challenges.Daily.3.Progression", 0);
				Main.instance1.saveData();
			}
			if(Main.instance1.getData().getString("Joueurs."+player.getName()+".Challenges.Daily.4.Active") == null) {
				Main.instance1.getData().set("Joueurs."+player.getName()+".Challenges.Daily.4.Active", true);
				Main.instance1.getData().set("Joueurs."+player.getName()+".Challenges.Daily.4.Progression", 0);
				Main.instance1.saveData();
			}
			if(Main.instance1.getData().getString("Joueurs."+player.getName()+".Challenges.Daily.5.Active") == null) {
				Main.instance1.getData().set("Joueurs."+player.getName()+".Challenges.Daily.5.Active", true);
				Main.instance1.getData().set("Joueurs."+player.getName()+".Challenges.Daily.5.Progression", 0);
				Main.instance1.saveData();
			}
			if(Main.instance1.getData().getString("Joueurs."+player.getName()+".Challenges.Daily.6.Active") == null) {
				Main.instance1.getData().set("Joueurs."+player.getName()+".Challenges.Daily.6.Active", true);
				Main.instance1.getData().set("Joueurs."+player.getName()+".Challenges.Daily.6.Progression", 0);
				Main.instance1.saveData();
			}
			if(Main.instance1.getData().getString("Joueurs."+player.getName()+".Challenges.Daily.7.Active") == null) {
				Main.instance1.getData().set("Joueurs."+player.getName()+".Challenges.Daily.7.Active", true);
				Main.instance1.getData().set("Joueurs."+player.getName()+".Challenges.Daily.7.Progression", 0);
				Main.instance1.saveData();
			}
			if(Main.instance1.getData().getString("Joueurs."+player.getName()+".Atout.1.Active") == null) {
				Main.instance1.getData().set("Joueurs."+player.getName()+".Atout.1.Active", false);
				Main.instance1.getData().set("Joueurs."+player.getName()+".Atout.1.Level", 0);
				Main.instance1.saveData();
			}
			if(Main.instance1.getData().getString("Joueurs."+player.getName()+".Atout.2.Active") == null) {
				Main.instance1.getData().set("Joueurs."+player.getName()+".Atout.2.Active", false);
				Main.instance1.getData().set("Joueurs."+player.getName()+".Atout.2.Level", 0);
				Main.instance1.saveData();
			}
			if(Main.instance1.getData().getString("Joueurs."+player.getName()+".Atout.3.Active") == null) {
				Main.instance1.getData().set("Joueurs."+player.getName()+".Atout.3.Active", false);
				Main.instance1.getData().set("Joueurs."+player.getName()+".Atout.3.Level", 0);
				Main.instance1.saveData();
			}
			if(Main.instance1.getData().getString("Joueurs."+player.getName()+".Atout.4.Active") == null) {
				Main.instance1.getData().set("Joueurs."+player.getName()+".Atout.4.Active", false);
				Main.instance1.getData().set("Joueurs."+player.getName()+".Atout.4.Level", 0);
				Main.instance1.saveData();
			}
			if(Main.instance1.getData().getBoolean("Joueurs."+player.getName()+".Atout.1.Active") == true) {
				if(Main.instance1.getData().getInt("Joueurs."+player.getName()+".Atout.1.Level") == 1) {
				player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 999999999, 0));
				} else if(Main.instance1.getData().getInt("Joueurs."+player.getName()+".Atout.1.Level") == 2) {
					player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 999999999, 1));
				}
				}
	}
	@EventHandler
	public void OnLeave(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		User user = LuckPermsProvider.get().getUserManager().getUser(player.getName());
		if(user.getCachedData().getMetaData().getPrefix() != null) {
			Grade = user.getCachedData().getMetaData().getPrefix();
			}
		String LeaveMessage = null;
		if(!IridiumSkyblockAPI.getInstance().getUser(player).getIsland().isPresent()) {
		LeaveMessage = "�7[�c-�7] " + Grade.replace("&", "�").replace("&", "�") + player.getName();
		}
		else {
			int classement = 0;
			classement = IridiumSkyblockAPI.getInstance().getUser(player).getIsland().get().getRank();
			LeaveMessage = "�7[�c-�7] [#" + classement + "] " + Grade.replace("&", "�").replace("&", "�") + player.getName();
		}
		event.setQuitMessage(LeaveMessage);
	}
}
