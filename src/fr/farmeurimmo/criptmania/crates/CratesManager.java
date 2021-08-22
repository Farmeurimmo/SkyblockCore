package fr.farmeurimmo.criptmania.crates;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.farmeurimmo.criptmania.utils.CheckPlayerInventory;

import org.bukkit.event.inventory.InventoryClickEvent;

public class CratesManager implements Listener {
	
	public static final Location BoxLegendaire = new Location(Bukkit.getServer().getWorld("world"), -186, 96, -63);
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.MONITOR)
	public void OnInteract(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		Block aaa = e.getClickedBlock();
		if(aaa == null) {
			return;
		}
		if(aaa.getType() == Material.TRAPPED_CHEST && aaa.getLocation().getWorld().getName().equalsIgnoreCase("world")) {
			if(aaa.getLocation().getX() == BoxLegendaire.getX() && aaa.getLocation().getZ() == BoxLegendaire.getZ()){
				if(e.getAction() == Action.LEFT_CLICK_BLOCK) {
					LegCratesPreview(player);
				}
				if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
					ItemStack bb = player.getItemInHand();
					if(bb.getType() == Material.TRIPWIRE_HOOK) {
						if(bb.getItemMeta().getDisplayName().equalsIgnoreCase("§6§lClée légendaire") &&
								bb.getItemMeta().getItemFlags().contains(ItemFlag.HIDE_UNBREAKABLE)
								&& CheckPlayerInventory.CheckPlayerInventoryForSlot(player) == true) {
							String loot = LegCratesLoot(player);
							if(!loot.equalsIgnoreCase("reroll")) {
							int amount = player.getItemInHand().getAmount();
							if(amount == 1) {
								player.getItemInHand().setAmount(0);
							} else {
							player.getItemInHand().setAmount(amount - 1);
							}
							Bukkit.broadcastMessage("§6§lCrates §8» §f" + player.getName() + " ouvre une clée légendaire "
									+ "et obtient " + loot);
							for(Player p : Bukkit.getOnlinePlayers()) {
								if(p.getWorld().getName().equalsIgnoreCase("world")) {
							p.playSound(p.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 5, 1);
								}
								}
							} else {
								player.sendMessage("§6§lCrates §8» §fVous avez reçu un grade que vous possédez "
										+ "déjà dans la boxe légendaire, la clée ne vous a donc pas été enlevé !");
							}
						} else {
							player.sendMessage("§6§lCrates §8» §fVous devez avoir un slot libre dans votre inventaire "
									+ "pour ouvrir cette boxe !");
						}
					} else if(bb.getType() != Material.TRIPWIRE_HOOK){
						player.sendMessage("§6§lCrates §8» §fVous devez avoir une clée légendaire dans votre main "
								+ "pour ouvrir cette boxe !");
					}
				}
			}
		}
	}
	public String LegCratesLoot(Player player) {
		String loot = null;
		Random rand = new Random();
            int n = rand.nextInt(16);
            if (n == 0){
            	loot = "x1 Pioche légendaire T4";
            	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "itemleg give " + player.getName() + " 4");
            }
            if (n == 1 || n == 2){
            	loot = "x1 Pioche légendaire T3";
            	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "itemleg give " + player.getName() + " 3");
            }
            if (n == 3 || n == 4 || n == 5){
            	loot = "x1 Pioche légendaire T2";
            	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "itemleg give " + player.getName() + " 2");
            }
            if (n == 6 || n == 7 || n == 8 || n == 9){
            	loot = "x1 Pioche légendaire T1";
            	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "itemleg give " + player.getName() + " 1");
            }
            if (n == 10 || n == 11){
            	if(!player.hasPermission("dieu")) {
            	loot = "Grade §9Dieu";
            	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " +player.getName() + " parent set Dieu server=skyblock");
            	} else {
            		loot = "reroll";
            	}
            }
            if (n == 12){
            	if(!player.hasPermission("zeus")) {
            	loot = "Grade §bZeus";
            	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " +player.getName() + " parent set Zeus server=skyblock");
            	} else {
            		loot = "reroll";
            	}
            }
            if (n == 13 || n == 14 || n == 15){
            	loot = "x1 Spawneur à Iron Golem";
            	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "silkspawners add "+player.getName()+" iron_golem");
            }
            if (n == 16 || n == 17){
            	loot = "x2 Spawneurs à Iron Golem";
            	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "silkspawners add "+player.getName()+" iron_golem 2");
            }
            if (n == 18){
            	loot = "x3 Spawneurs à Iron Golem";
            	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "silkspawners add "+player.getName()+" iron_golem 3");
            }
		
            return loot;
	}
	@EventHandler
	public void CratesBreakEvent(BlockBreakEvent e) {
		Block aa = e.getBlock();
		if(aa.getLocation() == BoxLegendaire) {
			e.setCancelled(true);
			LegCratesPreview(e.getPlayer());
		}
	}
	@EventHandler
	public void InventoryClickEvent(InventoryClickEvent e) {
		ItemStack current = e.getCurrentItem();
		if(current == null) {
			return;
		}
		if(e.getView().getTitle().equalsIgnoreCase("§6Boxe légendaire")) {
			e.setCancelled(true);
		}
	}
	public static void LegCratesPreview(Player player) {
        Inventory inv = Bukkit.createInventory(null, 36, "§6Boxe légendaire");
        
    	ItemStack custom1 = new ItemStack(Material.GRASS_BLOCK, 1);
		ItemMeta meta1 = custom1.getItemMeta();
		meta1.setDisplayName("§6EN dev");
		custom1.setItemMeta(meta1);
		inv.setItem(10, custom1);
		
		
		
		ItemStack custom8 = new ItemStack(Material.WHITE_STAINED_GLASS_PANE, 1);
		ItemMeta meta8 = custom8.getItemMeta();
		meta8.setDisplayName("§6");
		custom8.setItemMeta(meta8);
		
		for (int i = 0; i < inv.getSize(); i++) {
            if (inv.getItem(i) == null || inv.getItem(i).getType().equals(Material.AIR)) {
            	inv.setItem(i, custom8);
            }
        }
		
		player.openInventory(inv);
	}
	public static void SpawnCrates() {
		Bukkit.getWorld("world").getBlockAt(BoxLegendaire).setType(Material.TRAPPED_CHEST);
	}
}
