package fr.farmeurimmo.premsi.featherfly;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import fr.farmeurimmo.premsi.utils.SendActionBar;

public class FeatherFlyInteract implements Listener {
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInteractWithFeather(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		if(player.getItemInHand().getType() == Material.FEATHER && player.getItemInHand().getItemMeta().getItemFlags().contains(ItemFlag.HIDE_UNBREAKABLE)) {
			if(!CountdownFly.fly.containsKey(player.getUniqueId())) {
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
	@EventHandler
	public void OnToggleFly(PlayerToggleFlightEvent e) {
		Player player = e.getPlayer();
		if(player.getGameMode() != GameMode.CREATIVE && player.getGameMode() != GameMode.SPECTATOR) {
		if(player.getWorld().getName().equalsIgnoreCase("world")) {
			e.setCancelled(true);
			SendActionBar.SendActionBarMsg(player, "§cVous ne pouvez pas fly au spawn.");
		}
		else {
			e.setCancelled(false);
		}
		}
	}

}
