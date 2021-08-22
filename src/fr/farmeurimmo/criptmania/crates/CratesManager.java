package fr.farmeurimmo.criptmania.crates;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
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
            int n = rand.nextInt(32);
            if (n == 0){
            	loot = "x1 Pioche légendaire T4";
            	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "itemleg give " + player.getName() + " 4");
            }
            if (n == 1 || n == 27){
            	loot = "x1 Pioche légendaire T3";
            	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "itemleg give " + player.getName() + " 3");
            }
            if (n == 2 || n == 3){
            	loot = "x1 Pioche légendaire T2";
            	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "itemleg give " + player.getName() + " 2");
            }
            if (n == 4 || n == 5 || n == 6){
            	loot = "x1 Pioche légendaire T1";
            	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "itemleg give " + player.getName() + " 1");
            }
            if (n == 7){
            	if(!player.hasPermission("dieu")) {
            	loot = "Grade §9Dieu";
            	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " +player.getName() + " parent set Dieu server=skyblock");
            	} else {
            		loot = "reroll";
            	}
            }
            if (n == 8){
            	if(!player.hasPermission("legende")) {
            	loot = "Grade §eLégende";
            	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " +player.getName() + " parent set Legende server=skyblock");
            	} else {
            		loot = "reroll";
            	}
            }
            if (n == 10 || n == 11 || n == 12){
            	loot = "x1 Spawneur à Iron Golem";
            	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "silkspawners add "+player.getName()+" iron_golem");
            }
            if (n == 13 || n == 14){
            	loot = "x2 Spawneurs à Iron Golem";
            	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "silkspawners add "+player.getName()+" iron_golem 2");
            }
            if (n == 15){
            	loot = "x3 Spawneurs à Iron Golem";
            	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "silkspawners add "+player.getName()+" iron_golem 3");
            }
            if (n == 16 || n == 17){
            	loot = "x1 SellChest";
            	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "chests give "+player.getName()+" sell_chest");
            }
            if (n == 18){
            	loot = "x2 SellChest";
            	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "chests give "+player.getName()+" sell_chest 2");
            }
            if (n == 19 || n == 20){
            	loot = "x1 Balise";
            	player.getInventory().addItem(new ItemStack(Material.BEACON, 1));
            }
            if (n == 21){
            	loot = "x2 Balises";
            	player.getInventory().addItem(new ItemStack(Material.BEACON, 2));
            }
            if (n == 22 || n == 23 || n == 24){
            	loot = "x12 Blocs de netherite";
            	player.getInventory().addItem(new ItemStack(Material.NETHERITE_BLOCK, 12));
            }
            if (n == 25){
            	loot = "x18 Blocs de netherite";
            	player.getInventory().addItem(new ItemStack(Material.NETHERITE_BLOCK, 18));
            }
            if (n == 9){
            	loot = "x24 Blocs de netherite";
            	player.getInventory().addItem(new ItemStack(Material.NETHERITE_BLOCK, 24));
            }
            if (n == 26 || n == 27 || n == 28 || n == 29){
            	loot = "100 000$";
            	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "money give "+player.getName()+" 100000");
            }
            if (n == 30){
            	loot = "250 000$";
            	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "money give "+player.getName()+" 250000");
            }
            if (n == 31){
            	loot = "500 000$";
            	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "money give "+player.getName()+" 500000");
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
        Inventory inv = Bukkit.createInventory(null, 45, "§6Boxe légendaire");
        
    	ItemStack custom1 = new ItemStack(Material.NETHERITE_PICKAXE, 1);
		ItemMeta meta1 = custom1.getItemMeta();
		meta1.setDisplayName("§6Pioche légendaire §8| §eTier §c1");
		meta1.addEnchant(Enchantment.DIG_SPEED, 5, true);
		meta1.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		custom1.setItemMeta(meta1);
		inv.setItem(10, custom1);
		
		ItemStack custom2 = new ItemStack(Material.NETHERITE_PICKAXE, 1);
		ItemMeta meta2 = custom2.getItemMeta();
		meta2.setDisplayName("§6Pioche légendaire §8| §eTier §c2");
		meta2.addEnchant(Enchantment.DIG_SPEED, 5, true);
		meta2.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		custom2.setItemMeta(meta2);
		inv.setItem(11, custom2);
		
		ItemStack custom3 = new ItemStack(Material.NETHERITE_PICKAXE, 1);
		ItemMeta meta3 = custom3.getItemMeta();
		meta3.setDisplayName("§6Pioche légendaire §8| §eTier §c3");
		meta3.addEnchant(Enchantment.DIG_SPEED, 5, true);
		meta3.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		custom3.setItemMeta(meta3);
		inv.setItem(12, custom3);
		
		ItemStack custom4 = new ItemStack(Material.NETHERITE_PICKAXE, 1);
		ItemMeta meta4 = custom4.getItemMeta();
		meta4.setDisplayName("§6Pioche légendaire §8| §eTier §c4");
		meta4.addEnchant(Enchantment.DIG_SPEED, 5, true);
		meta4.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		custom4.setItemMeta(meta4);
		inv.setItem(13, custom4);
		
		ItemStack custom5 = new ItemStack(Material.SPAWNER, 1);
		ItemMeta meta5 = custom5.getItemMeta();
		meta5.setDisplayName("§6Spawneur à Iron Golem x1");
		custom5.setItemMeta(meta5);
		inv.setItem(14, custom5);
		
		ItemStack custom6 = new ItemStack(Material.SPAWNER, 2);
		ItemMeta meta6 = custom6.getItemMeta();
		meta6.setDisplayName("§6Spawneur à Iron Golem x2");
		custom6.setItemMeta(meta6);
		inv.setItem(15, custom6);
		
		ItemStack custom7 = new ItemStack(Material.SPAWNER, 3);
		ItemMeta meta7 = custom7.getItemMeta();
		meta7.setDisplayName("§6Spawneur à Iron Golem x3");
		custom7.setItemMeta(meta7);
		inv.setItem(16, custom7);
		
		ItemStack custom9 = new ItemStack(Material.CHEST, 1);
		ItemMeta meta9 = custom9.getItemMeta();
		meta9.setDisplayName("§6SellChest x1");
		custom9.setItemMeta(meta9);
		inv.setItem(19, custom9);
		
		ItemStack custom10 = new ItemStack(Material.CHEST, 2);
		ItemMeta meta10 = custom10.getItemMeta();
		meta10.setDisplayName("§6SellChest x2");
		custom10.setItemMeta(meta10);
		inv.setItem(20, custom10);
		
		ItemStack custom11 = new ItemStack(Material.BEACON, 1);
		ItemMeta meta11 = custom11.getItemMeta();
		meta11.setDisplayName("§6Balise x1");
		custom11.setItemMeta(meta11);
		inv.setItem(21, custom11);
		
		ItemStack custom12 = new ItemStack(Material.BEACON, 2);
		ItemMeta meta12 = custom12.getItemMeta();
		meta12.setDisplayName("§6Balise x2");
		custom12.setItemMeta(meta12);
		inv.setItem(22, custom12);
		
		ItemStack custom13 = new ItemStack(Material.NETHERITE_BLOCK, 8);
		ItemMeta meta13 = custom13.getItemMeta();
		meta13.setDisplayName("§6Bloc de netherite x8");
		custom13.setItemMeta(meta13);
		inv.setItem(23, custom13);
		
		ItemStack custom14 = new ItemStack(Material.NETHERITE_BLOCK, 12);
		ItemMeta meta14 = custom14.getItemMeta();
		meta14.setDisplayName("§6Bloc de netherite x12");
		custom14.setItemMeta(meta14);
		inv.setItem(24, custom14);
		
		ItemStack custom15 = new ItemStack(Material.NETHERITE_BLOCK, 16);
		ItemMeta meta15 = custom15.getItemMeta();
		meta15.setDisplayName("§6Bloc de netherite x16");
		custom15.setItemMeta(meta15);
		inv.setItem(25, custom15);
		
		ItemStack custom16 = new ItemStack(Material.SUNFLOWER, 1);
		ItemMeta meta16 = custom16.getItemMeta();
		meta16.setDisplayName("§6100 000$");
		custom16.setItemMeta(meta16);
		inv.setItem(28, custom16);
		
		ItemStack custom17 = new ItemStack(Material.SUNFLOWER, 1);
		ItemMeta meta17 = custom17.getItemMeta();
		meta17.setDisplayName("§6250 000$");
		custom17.setItemMeta(meta17);
		inv.setItem(29, custom17);
		
		ItemStack custom18 = new ItemStack(Material.SUNFLOWER, 1);
		ItemMeta meta18 = custom18.getItemMeta();
		meta18.setDisplayName("§6500 000$");
		custom18.setItemMeta(meta18);
		inv.setItem(30, custom18);
		
		ItemStack custom19 = new ItemStack(Material.PAPER, 1);
		ItemMeta meta19 = custom19.getItemMeta();
		meta19.setDisplayName("§6Grade §eLégende");
		custom19.setItemMeta(meta19);
		inv.setItem(31, custom19);
		
		ItemStack custom20 = new ItemStack(Material.PAPER, 1);
		ItemMeta meta20 = custom20.getItemMeta();
		meta20.setDisplayName("§6Grade §9Dieu");
		custom20.setItemMeta(meta20);
		inv.setItem(32, custom20);
		
		
		
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
