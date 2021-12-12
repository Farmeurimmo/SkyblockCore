package fr.farmeurimmo.verymc.WineLottery;

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

import fr.farmeurimmo.verymc.eco.EcoAccountsManager;
import fr.farmeurimmo.verymc.utils.SendActionBar;
import net.citizensnpcs.api.event.NPCRightClickEvent;

public class WineGui implements Listener {
	
	public static ArrayList<String> WineCooldown = new ArrayList<String>();
	
	@EventHandler
	public void OnInteractWithNPC(NPCRightClickEvent e) {
		Player player = e.getClicker();
		if(e.getNPC().getName().equalsIgnoreCase("§6Barman")) {
			BuyWinePotion.MakeWinePotionGui(player);
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
			    Double money = EcoAccountsManager.Moneys.get(player.getName());
			    
				
				if(money >= BuyWinePotion.cidreprice) {
					EcoAccountsManager.RemoveFounds(player.getName(), (double) BuyWinePotion.cidreprice);
				player.sendMessage("§6§lBarman §8» §fMerci de m'avoir acheté du cidre.");
				ItemStack custom1 = new ItemStack(Material.POTION, 1);
				ItemMeta customa = custom1.getItemMeta();
				customa.setDisplayName("§6Cidre");
				customa.setLore(Arrays.asList("§6Gain possible 10000$","§cEn cas de défaite, vous perdez l'alcool"));
				customa.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
				custom1.setItemMeta(customa);
				
				player.getInventory().addItem(custom1);
				} else {
					player.sendMessage("§6§lBarman §8» §fVous n'avez pas asser d'argent.");
				}
			}
			if(current.getType() == Material.POTION && current.getItemMeta().getDisplayName().equalsIgnoreCase("§6Bière")) {

			    Double money = EcoAccountsManager.Moneys.get(player.getName());
			    
				
				if(money >= BuyWinePotion.biereprice) {
					EcoAccountsManager.RemoveFounds(player.getName(), (double) BuyWinePotion.biereprice);
				player.sendMessage("§6§lBarman §8» §fMerci de m'avoir acheté de la bière.");
				ItemStack custom1 = new ItemStack(Material.POTION, 1);
				ItemMeta customa = custom1.getItemMeta();
				customa.setDisplayName("§6Bière");
				customa.setLore(Arrays.asList("§6Gain possible 20000$","§cEn cas de défaite, vous perdez l'alcool"));
				customa.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
				custom1.setItemMeta(customa);
				
				player.getInventory().addItem(custom1);
				} else {
					player.sendMessage("§6§lBarman §8» §fVous n'avez pas asser d'argent.");
				}
			}
			if(current.getType() == Material.POTION && current.getItemMeta().getDisplayName().equalsIgnoreCase("§6Rhum")) {
			    Double money = EcoAccountsManager.Moneys.get(player.getName());
			    
				
				if(money >= BuyWinePotion.rhumprice) {
					EcoAccountsManager.RemoveFounds(player.getName(), (double) BuyWinePotion.rhumprice);
				player.sendMessage("§6§lBarman §8» §fMerci de m'avoir acheté du rhum.");
				ItemStack custom1 = new ItemStack(Material.POTION, 1);
				ItemMeta customa = custom1.getItemMeta();
				customa.setDisplayName("§6Rhum");
				customa.setLore(Arrays.asList("§6Gain possible 50000$","§cEn cas de défaite, vous perdez l'alcool"));
				customa.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
				custom1.setItemMeta(customa);
				
				player.getInventory().addItem(custom1);
				} else {
					player.sendMessage("§6§lBarman §8» §fVous n'avez pas asser d'argent.");
				}
			}
			if(current.getType() == Material.POTION && current.getItemMeta().getDisplayName().equalsIgnoreCase("§6Vodka")) {

			    Double money = EcoAccountsManager.Moneys.get(player.getName());
			    
				
				if(money >= BuyWinePotion.vodkaprice) {
					EcoAccountsManager.RemoveFounds(player.getName(), (double) BuyWinePotion.vodkaprice);
				player.sendMessage("§6§lBarman §8» §fMerci de m'avoir acheté de la vodka.");
				ItemStack custom1 = new ItemStack(Material.POTION, 1);
				ItemMeta customa = custom1.getItemMeta();
				customa.setDisplayName("§6Vodka");
				customa.setLore(Arrays.asList("§6Gain possible 100000$","§cEn cas de défaite, vous perdez l'alcool"));
				customa.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
				custom1.setItemMeta(customa);
				
				player.getInventory().addItem(custom1);
				} else {
					player.sendMessage("§6§lBarman §8» §fVous n'avez pas asser d'argent.");
				}
			}
		}
	}
	
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
		                EcoAccountsManager.AddFounds(player.getName(), (double) (BuyWinePotion.cidreprice*2), false);
		                player.getItemInHand().setAmount(0);
		            } else {
		            	if(!player.hasPotionEffect(PotionEffectType.CONFUSION) && !player.hasPotionEffect(PotionEffectType.WEAKNESS) && !player.hasPotionEffect(PotionEffectType.BLINDNESS)) {
							player.getItemInHand().setAmount(0);
							player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 180, 0));
							player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 180, 0));
							player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 180, 0));
							} else {
								player.getItemInHand().setAmount(0);
								int rest = player.getPotionEffect(PotionEffectType.CONFUSION).getDuration();
								player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, rest + 180, 0));
								player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, rest + 180, 0));
								player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, rest + 180, 0));
							}
		            	player.sendMessage("§6§lBarman §8» §fVous avez perdu, par conséquent vous obtenez un §cmalus§7.");
		            }
		            WineCooldown.add(player.getName());
		            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
					     public void run() {
					    	 WineCooldown.remove(player.getName());
					     }
					}, 50);
				}
				}
				if(bbb.contains("Bière")) {
					if(WineCooldown.contains(player.getName())) {
						SendActionBar.SendActionBarMsg(player, "§aMerci de patienter un peut entre les boissons !");
						e.setCancelled(true);
					}
					else {
					int n = rand.nextInt(2);
		            if (n == 0){
		                player.sendMessage("§6§lBarman §8» §fVous avez gagné " +  BuyWinePotion.biereprice * 2 + "$");
		                EcoAccountsManager.AddFounds(player.getName(), (double) (BuyWinePotion.biereprice*2), false);
		                player.getItemInHand().setAmount(0);
		            } else {
		            	if(!player.hasPotionEffect(PotionEffectType.CONFUSION) && !player.hasPotionEffect(PotionEffectType.WEAKNESS) && !player.hasPotionEffect(PotionEffectType.BLINDNESS)) {
							player.getItemInHand().setAmount(0);
							player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 260, 1));
							player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 260, 1));
							player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 260, 1));
							} else {
								player.getItemInHand().setAmount(0);
								int rest = player.getPotionEffect(PotionEffectType.CONFUSION).getDuration();
								player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, rest + 260, 1));
								player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, rest + 260, 1));
								player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, rest + 260, 1));
							}
		            	player.sendMessage("§6§lBarman §8» §fVous avez perdu, par conséquent vous obtenez un §cmalus§7.");
		            }
		            WineCooldown.add(player.getName());
		            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
					     public void run() {
					    	 WineCooldown.remove(player.getName());
					     }
					}, 50);
				}
				}
				if(bbb.contains("Rhum")) {
					if(WineCooldown.contains(player.getName())) {
						SendActionBar.SendActionBarMsg(player, "§aMerci de patienter un peut entre les boissons !");
						e.setCancelled(true);
					}
					else {
					int n = rand.nextInt(3);
		            if (n == 0){
		                player.sendMessage("§6§lBarman §8» §fVous avez gagné " +  BuyWinePotion.rhumprice * 2 + "$");
		                EcoAccountsManager.AddFounds(player.getName(), (double) (BuyWinePotion.rhumprice*2), false);
		                player.getItemInHand().setAmount(0);
		            } else {
		            	if(!player.hasPotionEffect(PotionEffectType.CONFUSION) && !player.hasPotionEffect(PotionEffectType.WEAKNESS) && !player.hasPotionEffect(PotionEffectType.BLINDNESS)) {
							player.getItemInHand().setAmount(0);
							player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 320, 2));
							player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 320, 2));
							player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 320, 2));
							} else {
								player.getItemInHand().setAmount(0);
								int rest = player.getPotionEffect(PotionEffectType.CONFUSION).getDuration();
								player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, rest + 320, 2));
								player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, rest + 320, 2));
								player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, rest + 320, 2));
							}
		            	player.sendMessage("§6§lBarman §8» §fVous avez perdu, par conséquent vous obtenez un §cmalus§7.");
		            }
		            WineCooldown.add(player.getName());
		            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("SkyblockCore"), new Runnable() {
					     public void run() {
					    	 WineCooldown.remove(player.getName());
					     }
					}, 50);
				}
				}
				if(bbb.contains("Vodka")) {
					if(WineCooldown.contains(player.getName())) {
						SendActionBar.SendActionBarMsg(player, "§aMerci de patienter un peut entre les boissons !");
						e.setCancelled(true);
					}
					else {
					int n = rand.nextInt(3);
		            if (n == 0){
		                player.sendMessage("§6§lBarman §8» §fVous avez gagné " +  BuyWinePotion.vodkaprice * 2 + "$");
		                EcoAccountsManager.AddFounds(player.getName(), (double) (BuyWinePotion.vodkaprice*2), false);
		                player.getItemInHand().setAmount(0);
		            } else {
		            	if(!player.hasPotionEffect(PotionEffectType.CONFUSION) && !player.hasPotionEffect(PotionEffectType.WEAKNESS) && !player.hasPotionEffect(PotionEffectType.BLINDNESS)) {
							player.getItemInHand().setAmount(0);
							player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 400, 3));
							player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 400, 3));
							player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 400, 3));
							} else {
								player.getItemInHand().setAmount(0);
								int rest = player.getPotionEffect(PotionEffectType.CONFUSION).getDuration();
								player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, rest + 400, 3));
								player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, rest + 400, 3));
								player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, rest + 400, 3));
							}
		            	player.sendMessage("§6§lBarman §8» §fVous avez perdu, par conséquent vous obtenez un §cmalus§7.");
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
