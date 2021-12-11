package fr.farmeurimmo.verymc.crates;

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

import fr.farmeurimmo.verymc.utils.CheckPlayerInventory;

import org.bukkit.event.inventory.InventoryClickEvent;

public class CratesManager implements Listener {
	
	public static final Location BoxLegendaire = new Location(Bukkit.getServer().getWorld("world"), -162, 72, -68);
	public static final Location BoxChallenge = new Location(Bukkit.getServer().getWorld("world"), -161, 72, -64);
	public static final Location BoxVote = new Location(Bukkit.getServer().getWorld("world"), -162, 72, -60);
	
	static Location holo = new Location(Bukkit.getServer().getWorld("world"), BoxLegendaire.getX()+0.5,
			BoxLegendaire.getY() + 2.5, BoxLegendaire.getZ()+0.5);
	static Plugin plugin = Bukkit.getPluginManager().getPlugin("SkyblockCore");
	static Hologram hologram = HologramsAPI.createHologram(plugin, holo);
	
	static Location holoc = new Location(Bukkit.getServer().getWorld("world"), BoxChallenge.getX()+0.5,
			BoxChallenge.getY() + 2.5, BoxChallenge.getZ()+0.5);
	static Hologram hologramc = HologramsAPI.createHologram(plugin, holoc);
	
	static Location holod = new Location(Bukkit.getServer().getWorld("world"), BoxVote.getX()+0.5,
			BoxVote.getY() + 2.5, BoxVote.getZ()+0.5);
	static Hologram hologramd = HologramsAPI.createHologram(plugin, holod);
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void OnInteract(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		Block aaa = e.getClickedBlock();
		if(aaa == null) {
			return;
		}
		if(aaa.getType() == Material.END_ROD && aaa.getLocation().getWorld().getName().equalsIgnoreCase("world")) {
			if(aaa.getLocation().getX() == BoxLegendaire.getX() && aaa.getLocation().getZ() == BoxLegendaire.getZ()){
				if(e.getAction() == Action.LEFT_CLICK_BLOCK) {
					e.setCancelled(true);
					LegCrateManager.LegCratePreview(player);
				}
				if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
					ItemStack bb = player.getItemInHand();
						if(bb.getType() == Material.TRIPWIRE_HOOK && bb.getItemMeta().getDisplayName().equalsIgnoreCase("�6�lCl�e l�gendaire") &&
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
									return;
								}
							} else {
								player.sendMessage("�6�lCrates �8� �fVous avez re�u un grade que vous poss�dez "
										+ "d�j� dans la boxe l�gendaire, la cl�e ne vous a donc pas �t� enlev� !");
								return;
						}
					}
				}
			}
		}
		if(aaa.getType() == Material.END_ROD && aaa.getLocation().getWorld().getName().equalsIgnoreCase("world")) {
			if(aaa.getLocation().getX() == BoxChallenge.getX() && aaa.getLocation().getZ() == BoxChallenge.getZ()){
				if(e.getAction() == Action.LEFT_CLICK_BLOCK) {
					ChallengeCrateManager.ChallengeCratePreview(player);
					e.setCancelled(true);
				}
				if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
					ItemStack bb = player.getItemInHand();
						if(bb.getType() == Material.TRIPWIRE_HOOK && bb.getItemMeta().getDisplayName().equalsIgnoreCase("�6�lCl�e challenge") &&
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
							player.sendMessage("�6�lCrates �8� �fVous avez ouvert une cl�e challenge "
									+ "et obtenez " + loot);
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
					} 
				}
			}
		}
		if(aaa.getType() == Material.END_ROD && aaa.getLocation().getWorld().getName().equalsIgnoreCase("world")) {
			if(aaa.getLocation().getX() == BoxVote.getX() && aaa.getLocation().getZ() == BoxVote.getZ()){
				if(e.getAction() == Action.LEFT_CLICK_BLOCK) {
					VoteCrateManager.VoteCratePreview(player);
					e.setCancelled(true);
				}
				if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
					ItemStack bb = player.getItemInHand();
						if(bb.getType() == Material.TRIPWIRE_HOOK && bb.getItemMeta().getDisplayName().equalsIgnoreCase("�6�lCl�e vote") &&
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
							player.sendMessage("�6�lCrates �8� �fVous avez ouvert une cl�e vote "
									+ "et obtenez " + loot);
							for(Player p : Bukkit.getOnlinePlayers()) {
								if(p.getWorld().getName().equalsIgnoreCase("world")) {
							p.playSound(p.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 5, 1);
								}
								}
								} else {
									player.sendMessage("�6�lCrates �8� �fErreur lors de l'ouverture de la "
											+ "boxe vote, la cl�e ne vous a donc pas �t� enlev� !");
							}
						}
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
		if(e.getView().getTitle().equalsIgnoreCase("�6Boxe l�gendaire")) {
			e.setCancelled(true);
		}
		if(e.getView().getTitle().equalsIgnoreCase("�6Boxe challenge")) {
			e.setCancelled(true);
		}
		if(e.getView().getTitle().equalsIgnoreCase("�6Boxe vote")) {
			e.setCancelled(true);
		}
	}
	public static void SpawnCrates() {
		Bukkit.getWorld("world").getBlockAt(BoxLegendaire).setType(Material.END_ROD);
		
		Bukkit.getWorld("world").getBlockAt(BoxChallenge).setType(Material.END_ROD);
		
		Bukkit.getWorld("world").getBlockAt(BoxVote).setType(Material.END_ROD);

		
		hologramc.appendTextLine("�6Boxe challenge");
		hologramc.appendTextLine("�eObtenable dans le /c");
		hologramc.appendTextLine("");
		hologramc.appendTextLine("�7Clic droit ouvrir");
		hologramc.appendTextLine("�7Clic gauche pr�visualiser");
		
		hologramd.appendTextLine("�6Boxe vote");
		hologramd.appendTextLine("�eObtenable avec le /vote");
		hologramd.appendTextLine("");
		hologramd.appendTextLine("�7Clic droit ouvrir");
		hologramd.appendTextLine("�7Clic gauche pr�visualiser");
		
		hologram.appendTextLine("�6Boxe l�gendaire");
		hologram.appendTextLine("�eObtenable dans les boxes");
		hologram.appendTextLine("");
		hologram.appendTextLine("�7Clic droit ouvrir");
		hologram.appendTextLine("�7Clic gauche pr�visualiser");
	}
	public static void RemoveBoxeHolo() {
		hologram.clearLines();
		hologramc.clearLines();
		hologramd.clearLines();
	}
}
