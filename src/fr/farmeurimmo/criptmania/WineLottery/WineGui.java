package fr.farmeurimmo.criptmania.WineLottery;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.farmeurimmo.criptmania.utils.SendActionBar;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.tnemc.core.TNE;
import net.tnemc.core.common.api.TNEAPI;

public class WineGui implements Listener {
	
	public static ArrayList<String> WineCooldown = new ArrayList<String>();
	
	@EventHandler
	public void OnInteractWithNPC(NPCRightClickEvent e) {
		Player player = e.getClicker();
		if(player.hasPermission("*")) {
			if(e.getNPC().getName().equalsIgnoreCase("§6Barman §c(en développement)")) {
				BuyWinePotion.MakeWinePotionGui(player);
		}
		} else {
			SendActionBar.SendActionBarMsg(player, "§cVous n'avez pas la permission !");
		}
	}
	@EventHandler
	public void InventoryClickEvent(InventoryClickEvent e) {
		ItemStack current = e.getCurrentItem();
		Player player = (Player) e.getWhoClicked();
		if(current == null) {
			return;
		}
		if(current.getItemMeta() == null) {
			return;
		}
		if(e.getView().getTitle().equalsIgnoreCase("§6Boutique des vins")) {
			e.setCancelled(true);
			if(current.getType() == Material.POTION && current.getItemMeta().getDisplayName().equalsIgnoreCase("§6Cidre")) {
				TNEAPI ecoAPI = TNE.instance().api();
			    int money = ecoAPI.getAccount(player.getName()).getHoldings().intValue();
			    
				
				if(money >= BuyWinePotion.cidreprice) {
				ecoAPI.getAccount(player.getName()).removeHoldings(new BigDecimal(BuyWinePotion.cidreprice));
				player.sendMessage("§6§lBarman §8» §fMerci de m'avoir acheté du cidre.");
				ItemStack custom1 = new ItemStack(Material.POTION, 1);
				ItemMeta customa = custom1.getItemMeta();
				customa.setDisplayName("§6Cidre");
				customa.setLore(Arrays.asList("§6Gain possible 10000$","§cEn cas de défaite, vous perdez","§cle vin"));
				customa.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
				custom1.setItemMeta(customa);
				
				player.getInventory().addItem(custom1);
				} else {
					player.sendMessage("§6§lBarman §8» §fVous n'avez pas asser d'argent.");
				}
			}
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
					int n = rand.nextInt(2);
		            if (n == 0){
		                player.sendMessage("§6§lBarman §8» §fVous avez gagné " +  BuyWinePotion.cidreprice * 2 + "$");
		                TNEAPI ecoAPI = TNE.instance().api();
					    ecoAPI.getAccount(player.getName()).addHoldings(new BigDecimal(BuyWinePotion.cidreprice * 2));
		                player.getItemInHand().setAmount(0);
		            } else {
		            	if(!player.hasPotionEffect(PotionEffectType.CONFUSION) && !player.hasPotionEffect(PotionEffectType.WEAKNESS) && !player.hasPotionEffect(PotionEffectType.BLINDNESS)) {
							player.getItemInHand().setAmount(0);
							player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 220, 0));
							player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 220, 0));
							player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 220, 0));
							} else {
								player.getItemInHand().setAmount(0);
								int rest = player.getPotionEffect(PotionEffectType.CONFUSION).getDuration();
								player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, rest + 220, player.getPotionEffect(PotionEffectType.WEAKNESS).getAmplifier() + 1));
								player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, rest + 220, player.getPotionEffect(PotionEffectType.BLINDNESS).getAmplifier() + 1));
								player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, rest + 220, player.getPotionEffect(PotionEffectType.CONFUSION).getAmplifier() + 1));
							}
		            }
		            WineCooldown.add(player.getName());
		            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
					     public void run() {
					    	 WineCooldown.remove(player.getName());
					     }
					}, 50);
				}
				}
			}
		}
	}
}
