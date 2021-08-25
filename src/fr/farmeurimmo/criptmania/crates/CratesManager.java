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
	
	public static final Location BoxLegendaire = new Location(Bukkit.getServer().getWorld("world"), -181, 109, -53);
	
	static Location holo = new Location(Bukkit.getServer().getWorld("world"), BoxLegendaire.getX()+0.5,
			BoxLegendaire.getY() + 2, BoxLegendaire.getZ()+0.5);
	static Plugin plugin = Bukkit.getPluginManager().getPlugin("SkyblockCore");
	static Hologram hologram = HologramsAPI.createHologram(plugin, holo);
	
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
						if(bb.getItemMeta().getDisplayName().equalsIgnoreCase("§6§lClée légendaire") &&
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
							Bukkit.broadcastMessage("§6§lCrates §8» §f" + player.getName() + " ouvre une clée légendaire "
									+ "et obtient " + loot);
							for(Player p : Bukkit.getOnlinePlayers()) {
								if(p.getWorld().getName().equalsIgnoreCase("world")) {
							p.playSound(p.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 5, 1);
								}
								}
								} else {
									player.sendMessage("§6§lCrates §8» §fErreur lors de l'ouverture de la "
											+ "boxe légendaire, la clée ne vous a donc pas été enlevé !");
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
		if(e.getView().getTitle().equalsIgnoreCase("§6Boxe légendaire")) {
			e.setCancelled(true);
		}
	}
	public static void SpawnCrates() {
		Bukkit.getWorld("world").getBlockAt(BoxLegendaire).setType(Material.TRAPPED_CHEST);
		
		hologram.appendTextLine("§6Boxe légendaire");
		hologram.appendTextLine("§7Clic droit pour ouvrir");
		hologram.appendTextLine("§7Clic gauche pour prévisualiser");
	}
	public static void RemoveLegBoxeHolo() {
		hologram.clearLines();
	}
}
