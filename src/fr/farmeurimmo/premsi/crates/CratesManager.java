package fr.farmeurimmo.premsi.crates;

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
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

import fr.farmeurimmo.premsi.utils.CheckPlayerInventory;

import org.bukkit.event.inventory.InventoryClickEvent;

public class CratesManager implements Listener {
	
	public static final Location BoxLegendaire = new Location(Bukkit.getServer().getWorld("world"), -208, 112, -45);
	public static final Location BoxChallenge = new Location(Bukkit.getServer().getWorld("world"), -202, 112, -51);
	public static final Location BoxVote = new Location(Bukkit.getServer().getWorld("world"), -205, 112, -48);
	
	static Location holo = new Location(Bukkit.getServer().getWorld("world"), BoxLegendaire.getX()+0.5,
			BoxLegendaire.getY() + 2.3, BoxLegendaire.getZ()+0.5);
	static Plugin plugin = Bukkit.getPluginManager().getPlugin("SkyblockCore");
	static Hologram hologram = HologramsAPI.createHologram(plugin, holo);
	
	static Location holoc = new Location(Bukkit.getServer().getWorld("world"), BoxChallenge.getX()+0.5,
			BoxChallenge.getY() + 2, BoxChallenge.getZ()+0.5);
	static Hologram hologramc = HologramsAPI.createHologram(plugin, holoc);
	
	static Location holod = new Location(Bukkit.getServer().getWorld("world"), BoxVote.getX()+0.5,
			BoxVote.getY() + 2, BoxVote.getZ()+0.5);
	static Hologram hologramd = HologramsAPI.createHologram(plugin, holod);
	
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
					LegCrateManager.LegCratePreview(player);
				}
				if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
					ItemStack bb = player.getItemInHand();
					if(bb.getType() == Material.TRIPWIRE_HOOK) {
						if(bb.getItemMeta().getDisplayName().equalsIgnoreCase("§6§lClée légendaire") &&
								bb.getItemMeta().getItemFlags().contains(ItemFlag.HIDE_UNBREAKABLE)
								&& CheckPlayerInventory.CheckPlayerInventoryForSlot(player) == true) {
							String loot = LegCrateManager.LegCrateLoot(player);
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
		if(aaa.getType() == Material.TRAPPED_CHEST && aaa.getLocation().getWorld().getName().equalsIgnoreCase("world")) {
			if(aaa.getLocation().getX() == BoxChallenge.getX() && aaa.getLocation().getZ() == BoxChallenge.getZ()){
				if(e.getAction() == Action.LEFT_CLICK_BLOCK) {
					ChallengeCrateManager.ChallengeCratePreview(player);
				}
				if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
					ItemStack bb = player.getItemInHand();
					if(bb.getType() == Material.TRIPWIRE_HOOK) {
						if(bb.getItemMeta().getDisplayName().equalsIgnoreCase("§6§lClée challenge") &&
								bb.getItemMeta().getItemFlags().contains(ItemFlag.HIDE_UNBREAKABLE)
								&& CheckPlayerInventory.CheckPlayerInventoryForSlot(player) == true) {
							String loot = ChallengeCrateManager.ChallengeCrateLoot(player);
							if(!loot.equalsIgnoreCase("reroll")) {
								if(!loot.equalsIgnoreCase("error")) {
							int amount = player.getItemInHand().getAmount();
							if(amount == 1) {
								player.getItemInHand().setAmount(0);
							} else {
							player.getItemInHand().setAmount(amount - 1);
							}
							player.sendMessage("§6§lCrates §8» §fVous avez ouvert une clée challenge "
									+ "et obtenez " + loot);
							for(Player p : Bukkit.getOnlinePlayers()) {
								if(p.getWorld().getName().equalsIgnoreCase("world")) {
							p.playSound(p.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 5, 1);
								}
								}
								} else {
									player.sendMessage("§6§lCrates §8» §fErreur lors de l'ouverture de la "
											+ "boxe challenge, la clée ne vous a donc pas été enlevé !");
								}
							}
						} else {
							player.sendMessage("§6§lCrates §8» §fVous devez avoir un slot libre dans votre inventaire "
									+ "pour ouvrir cette boxe !");
						}
					} else if(bb.getType() != Material.TRIPWIRE_HOOK){
						player.sendMessage("§6§lCrates §8» §fVous devez avoir une clée challenge dans votre main "
								+ "pour ouvrir cette boxe !");
					}
				}
			}
		}
		if(aaa.getType() == Material.TRAPPED_CHEST && aaa.getLocation().getWorld().getName().equalsIgnoreCase("world")) {
			if(aaa.getLocation().getX() == BoxVote.getX() && aaa.getLocation().getZ() == BoxVote.getZ()){
				if(e.getAction() == Action.LEFT_CLICK_BLOCK) {
					VoteCrateManager.VoteCratePreview(player);
				}
				if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
					ItemStack bb = player.getItemInHand();
					if(bb.getType() == Material.TRIPWIRE_HOOK) {
						if(bb.getItemMeta().getDisplayName().equalsIgnoreCase("§6§lClée vote") &&
								bb.getItemMeta().getItemFlags().contains(ItemFlag.HIDE_UNBREAKABLE)
								&& CheckPlayerInventory.CheckPlayerInventoryForSlot(player) == true) {
							String loot = "error";
							if(!loot.equalsIgnoreCase("reroll")) {
								if(!loot.equalsIgnoreCase("error")) {
							int amount = player.getItemInHand().getAmount();
							if(amount == 1) {
								player.getItemInHand().setAmount(0);
							} else {
							player.getItemInHand().setAmount(amount - 1);
							}
							player.sendMessage("§6§lCrates §8» §fVous avez ouvert une clée vote "
									+ "et obtenez " + loot);
							for(Player p : Bukkit.getOnlinePlayers()) {
								if(p.getWorld().getName().equalsIgnoreCase("world")) {
							p.playSound(p.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 5, 1);
								}
								}
								} else {
									player.sendMessage("§6§lCrates §8» §fErreur lors de l'ouverture de la "
											+ "boxe vote, la clée ne vous a donc pas été enlevé !");
								}
							}
						} else {
							player.sendMessage("§6§lCrates §8» §fVous devez avoir un slot libre dans votre inventaire "
									+ "pour ouvrir cette boxe !");
						}
					} else if(bb.getType() != Material.TRIPWIRE_HOOK){
						player.sendMessage("§6§lCrates §8» §fVous devez avoir une clée challenge dans votre main "
								+ "pour ouvrir cette boxe !");
					}
				}
			}
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
		if(e.getView().getTitle().equalsIgnoreCase("§6Boxe challenge")) {
			e.setCancelled(true);
		}
		if(e.getView().getTitle().equalsIgnoreCase("§6Boxe vote")) {
			e.setCancelled(true);
		}
	}
	public static void SpawnCrates() {
		Bukkit.getWorld("world").getBlockAt(BoxLegendaire).setType(Material.TRAPPED_CHEST);
		
		Bukkit.getWorld("world").getBlockAt(BoxChallenge).setType(Material.TRAPPED_CHEST);
		
		Bukkit.getWorld("world").getBlockAt(BoxVote).setType(Material.TRAPPED_CHEST);

		
		hologramc.appendTextLine("§6Boxe challenge");
		hologramc.appendTextLine("§eObtenable dans le /c");
		hologramc.appendTextLine("§7Clic droit ouvrir");
		hologramc.appendTextLine("§7Clic gauche prévisualiser");
		
		hologramd.appendTextLine("§6Boxe vote");
		hologramd.appendTextLine("§eObtenable avec le /vote");
		hologramd.appendTextLine("§7Clic droit ouvrir");
		hologramd.appendTextLine("§7Clic gauche prévisualiser");
		
		hologram.appendTextLine("§6Boxe légendaire");
		hologram.appendTextLine("§eObtenable sur le site");
		hologram.appendTextLine("§eou dans les boxes");
		hologram.appendTextLine("§7Clic droit ouvrir");
		hologram.appendTextLine("§7Clic gauche prévisualiser");
	}
	public static void RemoveBoxeHolo() {
		hologram.clearLines();
		hologramc.clearLines();
		hologramd.clearLines();
	}
}
