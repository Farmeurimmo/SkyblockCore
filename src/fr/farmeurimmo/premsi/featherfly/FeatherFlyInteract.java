package fr.farmeurimmo.premsi.featherfly;

import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import fr.farmeurimmo.premsi.core.Main;
import fr.farmeurimmo.premsi.utils.SendActionBar;

public class FeatherFlyInteract implements Listener {
	
	public static void WriteFlyLeft() {
		for(Entry<String, Integer> aaa : CountdownFly.fly.entrySet()) {
			if(aaa.getValue() >= 1) {
				Main.instance1.getDatac().set("Joueurs."+aaa.getKey()+".Fly.timeleft", aaa.getValue());
				Main.instance1.saveData();
			}
		}
	}
	
	public static void ReadForTempFly() {
		for(String aa : Main.instance1.getDatac().getConfigurationSection("Joueurs").getKeys(false)) {
			int a = 0;
			a = Main.instance1.getDatac().getInt("Joueurs."+aa+".Fly.timeleft");
			if(a >= 1) {
			CountdownFly.setCooldown(aa, a);
			CountdownFly.CountDown(aa);
			Bukkit.broadcastMessage(aa+" " + a);
			Main.instance1.getDatac().set("Joueurs."+aa+".Fly.timeleft", 0);
			}
		}
	}
	
	@SuppressWarnings({ "deprecation" })
	@EventHandler
	public void onInteractWithFeather(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		if(player.getItemInHand().getType() == Material.FEATHER && player.getItemInHand().getItemMeta().getItemFlags().contains(ItemFlag.HIDE_UNBREAKABLE)) {
			if(!CountdownFly.fly.containsKey(player.getName())) {
			String sample = player.getItemInHand().getItemMeta().getDisplayName();
			char[] chars = sample.toCharArray();
		      StringBuilder sb = new StringBuilder();
		      for(char c : chars){
		         if(Character.isDigit(c)){
		            sb.append(c);
		         }
		      }
		      if(!sb.isEmpty()) {
		    	  int count = player.getInventory().getItemInHand().getAmount();
		    	  if(count == 1) {
		    		  CountdownFly.EnableFlyForPlayer(player, sample.replace("§eFly de ", "").replace(sb, "").replace(" ", ""), sb.toString());
			    	  ItemStack aaa = player.getItemInHand();
		    	      player.getInventory().removeItem(aaa);
		    	  }
		    	  else {
		    		  player.sendMessage("§cUtilisez qu'une seule plûme à la fois.");
		    	  }
		      }
		}
			else {
				SendActionBar.SendActionBarMsg(player, "§cErreur, veuillez attendre la fin de votre fly.");
			}
		}
	}
	@EventHandler(priority = EventPriority.MONITOR)
	public void OnToggleFly(PlayerToggleFlightEvent e) {
		Player player = e.getPlayer();
		if(player.getGameMode() != GameMode.CREATIVE && player.getGameMode() != GameMode.SPECTATOR) {
		if(player.getWorld().getName().equalsIgnoreCase("world")) {
			e.setCancelled(true);
			SendActionBar.SendActionBarMsg(player, "§cVous ne pouvez pas fly au spawn.");
			player.setAllowFlight(false);
		}
		else {
			e.setCancelled(false);
		}
		}
	}

}
