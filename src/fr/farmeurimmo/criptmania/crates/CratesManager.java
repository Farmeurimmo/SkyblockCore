package fr.farmeurimmo.criptmania.crates;

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
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

import fr.farmeurimmo.criptmania.utils.CheckPlayerInventory;

import org.bukkit.event.inventory.InventoryClickEvent;

public class CratesManager implements Listener {
	
	public static final Location BoxLegendaire = new Location(Bukkit.getServer().getWorld("world"), -180, 109, -53);
	public static final Location BoxChallenge = new Location(Bukkit.getServer().getWorld("world"), -183, 109, -51);
	
	static Location holo = new Location(Bukkit.getServer().getWorld("world"), BoxLegendaire.getX()+0.5,
			BoxLegendaire.getY() + 2, BoxLegendaire.getZ()+0.5);
	static Plugin plugin = Bukkit.getPluginManager().getPlugin("SkyblockCore");
	static Hologram hologram = HologramsAPI.createHologram(plugin, holo);
	
	static Location holoc = new Location(Bukkit.getServer().getWorld("world"), BoxChallenge.getX()+0.5,
			BoxChallenge.getY() + 2, BoxChallenge.getZ()+0.5);
	static Hologram hologramc = HologramsAPI.createHologram(plugin, holoc);
	
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
					LegCrateManager.LegCratesPreview(player);
				}
				if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
					ItemStack bb = player.getItemInHand();
					if(bb.getType() == Material.TRIPWIRE_HOOK) {
						if(bb.getItemMeta().getDisplayName().equalsIgnoreCase("�6�lCl�e l�gendaire") &&
								bb.getItemMeta().getItemFlags().contains(ItemFlag.HIDE_UNBREAKABLE)
								&& CheckPlayerInventory.CheckPlayerInventoryForSlot(player) == true) {
							String loot = LegCrateManager.LegCratesLoot(player);
							if(!loot.equalsIgnoreCase("reroll")) {
								if(!loot.equalsIgnoreCase("error")) {
							int amount = player.getItemInHand().getAmount();
							if(amount == 1) {
								player.getItemInHand().setAmount(0);
							} else {
							player.getItemInHand().setAmount(amount - 1);
							}
							Bukkit.broadcastMessage("�6�lCrates �8� �f" + player.getName() + " ouvre une cl�e l�gendaire "
									+ "et obtient " + loot);
							for(Player p : Bukkit.getOnlinePlayers()) {
								if(p.getWorld().getName().equalsIgnoreCase("world")) {
							p.playSound(p.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 5, 1);
								}
								}
								} else {
									player.sendMessage("�6�lCrates �8� �fErreur lors de l'ouverture de la "
											+ "boxe l�gendaire, la cl�e ne vous a donc pas �t� enlev� !");
								}
							} else {
								player.sendMessage("�6�lCrates �8� �fVous avez re�u un grade que vous poss�dez "
										+ "d�j� dans la boxe l�gendaire, la cl�e ne vous a donc pas �t� enlev� !");
							}
						} else {
							player.sendMessage("�6�lCrates �8� �fVous devez avoir un slot libre dans votre inventaire "
									+ "pour ouvrir cette boxe !");
						}
					} else if(bb.getType() != Material.TRIPWIRE_HOOK){
						player.sendMessage("�6�lCrates �8� �fVous devez avoir une cl�e l�gendaire dans votre main "
								+ "pour ouvrir cette boxe !");
					}
				}
			}
		}
		if(aaa.getType() == Material.TRAPPED_CHEST && aaa.getLocation().getWorld().getName().equalsIgnoreCase("world")) {
			if(aaa.getLocation().getX() == BoxChallenge.getX() && aaa.getLocation().getZ() == BoxChallenge.getZ()){
				if(e.getAction() == Action.LEFT_CLICK_BLOCK) {
					
				}
				if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
					ItemStack bb = player.getItemInHand();
					if(bb.getType() == Material.TRIPWIRE_HOOK) {
						if(bb.getItemMeta().getDisplayName().equalsIgnoreCase("�6�lCl�e challenge") &&
								bb.getItemMeta().getItemFlags().contains(ItemFlag.HIDE_UNBREAKABLE)
								&& CheckPlayerInventory.CheckPlayerInventoryForSlot(player) == true) {
							String loot = "";
							if(!loot.equalsIgnoreCase("reroll")) {
								if(!loot.equalsIgnoreCase("error")) {
							int amount = player.getItemInHand().getAmount();
							if(amount == 1) {
								player.getItemInHand().setAmount(0);
							} else {
							player.getItemInHand().setAmount(amount - 1);
							}
							for(Player p : Bukkit.getOnlinePlayers()) {
								if(p.getWorld().getName().equalsIgnoreCase("world")) {
							p.playSound(p.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 5, 1);
								}
								}
								} else {
									player.sendMessage("�6�lCrates �8� �fErreur lors de l'ouverture de la "
											+ "boxe challenge, la cl�e ne vous a donc pas �t� enlev� !");
								}
							}
						} else {
							player.sendMessage("�6�lCrates �8� �fVous devez avoir un slot libre dans votre inventaire "
									+ "pour ouvrir cette boxe !");
						}
					} else if(bb.getType() != Material.TRIPWIRE_HOOK){
						player.sendMessage("�6�lCrates �8� �fVous devez avoir une cl�e challenge dans votre main "
								+ "pour ouvrir cette boxe !");
					}
				}
			}
		}
	}
	@EventHandler
	public void CratesBreakEvent(BlockBreakEvent e) {
		Block aa = e.getBlock();
		if(aa.getLocation() == BoxLegendaire) {
			e.setCancelled(true);
			LegCrateManager.LegCratesPreview(e.getPlayer());
		}
	}
	@EventHandler
	public void InventoryClickEvent(InventoryClickEvent e) {
		ItemStack current = e.getCurrentItem();
		if(current == null) {
			return;
		}
		if(e.getView().getTitle().equalsIgnoreCase("�6Boxe l�gendaire")) {
			e.setCancelled(true);
		}
	}
	public static void SpawnCrates() {
		Bukkit.getWorld("world").getBlockAt(BoxLegendaire).setType(Material.TRAPPED_CHEST);
		
		Bukkit.getWorld("world").getBlockAt(BoxChallenge).setType(Material.TRAPPED_CHEST);
		
		hologramc.appendTextLine("�6Boxe challenge");
		hologramc.appendTextLine("�7Clic droit ouvrir");
		hologramc.appendTextLine("�7Clic gauche pr�visualiser");
		
		hologram.appendTextLine("�6Boxe l�gendaire");
		hologram.appendTextLine("�7Clic droit ouvrir");
		hologram.appendTextLine("�7Clic gauche pr�visualiser");
	}
	public static void RemoveBoxeHolo() {
		hologram.clearLines();
		hologramc.clearLines();
	}
}
