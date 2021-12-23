package fr.farmeurimmo.verymc.blocks;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import fr.farmeurimmo.verymc.core.Main;

public class SellChest implements Listener {
	
	@EventHandler
	public void SellChestPlaced(BlockPlaceEvent e) {
		if(e.getBlockPlaced().getType()==Material.CHEST&&e.getItemInHand().getItemMeta().getDisplayName().contains("§6SellChest")) {
			String str = e.getItemInHand().getDisplayName().replace("§6", "");
			String numberOnly = str.replaceAll("[^0-9]", "");
			SellChestManager.PlaceChest(e.getPlayer(), e.getBlock().getLocation(), Integer.parseInt(numberOnly));
		}
	}
	@EventHandler
	public void SellChestBreaked(BlockBreakEvent e) {
		if(e.getBlock().getType()==Material.CHEST) {
			Chest blhopper = (Chest) e.getBlock().getState();
			if(!blhopper.getCustomName().contains("§6SellChest")) {
				return;
			}
			String a = blhopper.getCustomName().replace("§6", "");
			String numberOnly = a.replaceAll("[^0-9]", "");
			String owner = SellChestManager.getOwner(e.getBlock().getLocation());
			if(owner==null) {
				return;
			}
			SellChestManager.GiveSellChest(e.getPlayer(), Integer.parseInt(numberOnly));
			e.setDropItems(false);
			Location tda = null;
			Main.getInstance().getDatablc().set("SellChest."+owner+"."+
			numberOnly, tda);
			Main.getInstance().saveData();
		}
	}

}
