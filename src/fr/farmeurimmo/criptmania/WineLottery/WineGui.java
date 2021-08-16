package fr.farmeurimmo.criptmania.WineLottery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.farmeurimmo.criptmania.utils.SendActionBar;
import net.citizensnpcs.api.event.NPCRightClickEvent;

public class WineGui implements Listener {
	
	public static ArrayList<String> Double = new ArrayList<String>();
	public static ArrayList<String> WineCooldown = new ArrayList<String>();
    private static HashMap<String, Integer> spawncooldown = new HashMap<>();
	 
	public void setCooldown(String uuid, Integer time) {
		if (time == null)
			spawncooldown.remove(uuid);
		else
			spawncooldown.put(uuid, time);
	}
    public int getCooldown(String player) {
        return (spawncooldown.get(player) == null ? 0 : spawncooldown.get(player));
    }
	
	@EventHandler
	public void OnInteractWithNPC(NPCRightClickEvent e) {
		Player player = e.getClicker();
		if(!player.hasPermission("*")) {
		SendActionBar.SendActionBarMsg(player, "§cLe bar est encore en développement !");
		} else {
		BuyWinePotion.MakeWinePotionGui(player);
		}
	}
	@SuppressWarnings("deprecation")
	@EventHandler
	public void OnDrink(PlayerItemConsumeEvent e) {
		Player player = e.getPlayer();
		ItemStack aaa = e.getItem();
		Random rand = new Random();
		if(aaa != null) {
			if(aaa.getType() == Material.POTION) {
				String bbb = aaa.getItemMeta().getDisplayName();
				if(bbb.contains("Cidre")) {
					if(WineCooldown.contains(player.getName())) {
						SendActionBar.SendActionBarMsg(player, "§aMerci de patienter un peut entre les boissons !");
						e.setCancelled(true);
					}
					else {
					if(!player.hasPotionEffect(PotionEffectType.CONFUSION) && !player.hasPotionEffect(PotionEffectType.WEAKNESS) && !player.hasPotionEffect(PotionEffectType.BLINDNESS)) {
					player.getItemInHand().setAmount(0);
					player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 280, 0));
					player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 280, 0));
					player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 280, 0));
					} else {
						player.getItemInHand().setAmount(0);
						int rest = player.getPotionEffect(PotionEffectType.CONFUSION).getDuration();
						player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, rest + 280, player.getPotionEffect(PotionEffectType.WEAKNESS).getAmplifier() + 1));
						player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, rest + 280, player.getPotionEffect(PotionEffectType.BLINDNESS).getAmplifier() + 1));
						player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, rest + 280, player.getPotionEffect(PotionEffectType.CONFUSION).getAmplifier() + 1));
					}
					if(!spawncooldown.containsKey(player.getName())) {
			            setCooldown(player.getName(), 2);
			            } else {
			            	if(!Double.contains(player.getName())) {
			            	Double.add(player.getName());
			            	} else {
			            		Double.remove(player.getName());
			            		int a = getCooldown(player.getName()) + 1;
				            	setCooldown(player.getName(), a);
			            	}
			            }
					int n = rand.nextInt(getCooldown(player.getName()));
		            if (n == 0){
		                player.sendMessage("gagné");
		            }
		            player.sendMessage("nombre: " + n + "/" + getCooldown(player.getName()));
		            WineCooldown.add(player.getName());
		            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
					     public void run() {
					    	 WineCooldown.remove(player.getName());
					     }
					}, 60);
				}
				}
			}
		}
	}
}
