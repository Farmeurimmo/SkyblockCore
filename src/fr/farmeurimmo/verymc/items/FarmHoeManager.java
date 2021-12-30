package fr.farmeurimmo.verymc.items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import fr.farmeurimmo.verymc.shopgui.BuyShopItem;

public class FarmHoeManager implements Listener {
	
	public static ArrayList<String> replantableblocks = new ArrayList<String>();
	
	public static void addtolist() {
		replantableblocks.addAll(Arrays.asList("WHEAT"));
	}
	
	public static List<Block> getNearbyBlocks(Location location, int radius) {
        List<Block> blocks = new ArrayList<Block>();
        if(radius==1) {
        	blocks.add(location.getWorld().getBlockAt(location.getBlockX(), location.getBlockY(), location.getBlockZ()));
        	return blocks;
        }
        int y = location.getBlockY();
        for(int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
           for(int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
              blocks.add(location.getWorld().getBlockAt(x, y, z));
            }
        }
        return blocks;
    }
	
	@EventHandler
	public void HoeClic(PlayerInteractEvent e) {
		if(e.getAction() == Action.LEFT_CLICK_BLOCK) {
			if(e.getItem()==null) {
				return;
			}
			if(e.getItem().getItemMeta()==null) {
				return;
			}
			if(e.getItem().getType()!=Material.NETHERITE_HOE) {
				return;
			}
			
			Player player = e.getPlayer();
			ItemStack farmhoe = e.getItem();
			Location clicloc = e.getClickedBlock().getLocation();
			
			if(!farmhoe.getDisplayName().contains("I")) {
				return;
			}
			
			int tier = 0;
			if(farmhoe.getDisplayName().contains("IV")) {
				tier=3;
			} else if(farmhoe.getDisplayName().contains("III")) {
				tier=2;
			} else if(farmhoe.getDisplayName().contains("II")) {
				tier=1;
			}
			
			e.setCancelled(true);
			
			World world = player.getWorld();
			for(Block rf : getNearbyBlocks(clicloc, tier)) {
				if(!replantableblocks.contains(rf.getType().toString())) {
					continue;
				}
				Block bltmp = Bukkit.getWorld(world.getName()).getBlockAt(rf.getLocation());
				final Ageable ageable = (Ageable) bltmp.getState().getBlockData();
				int age = ageable.getAge();
				if(age==7) {
					for(ItemStack eed : bltmp.getDrops()) {
						if(eed.getType().toString().contains("SEED")) continue;
						if(BuyShopItem.GetAmountToFillInInv(eed, player)>0) {
							player.getInventory().addItem(eed);
							continue;
						}
						world.dropItemNaturally(rf.getLocation(), eed);
					}
					ageable.setAge(0);
					bltmp.setBlockData(ageable);
					bltmp.getState().update(true);
				} else {
					continue;
				}
			}
		}
	}

}
