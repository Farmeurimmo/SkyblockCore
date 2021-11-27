package fr.farmeurimmo.verymc.aspawneurs;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

import fr.farmeurimmo.verymc.core.Main;

public class SpawneurManager implements Listener {
	
	public static void GiveSpawneur(Player player, String type) {	
		ItemStack custom1 = new ItemStack(Material.SPAWNER, 1);
		BlockStateMeta bsm = (BlockStateMeta) custom1.getItemMeta();
		CreatureSpawner cs = (CreatureSpawner) bsm.getBlockState();
		
		if(type.equalsIgnoreCase("IG")) {
			cs.setSpawnedType(EntityType.IRON_GOLEM);
			bsm.setDisplayName("§fSpawneur à §6"+cs.getCreatureTypeName().toUpperCase());
		}
		bsm.setBlockState(cs);
		custom1.setItemMeta(bsm);
		
		player.getInventory().addItem(custom1);
	}
	
	@EventHandler
	public void OnPlace(BlockPlaceEvent e) {
		if(e.getBlock().getType() == Material.SPAWNER) {
			Player player = e.getPlayer();
			ItemStack od = e.getItemInHand();
			BlockStateMeta bsm = (BlockStateMeta) od.getItemMeta();
			CreatureSpawner cs = (CreatureSpawner) bsm.getBlockState();
			player.sendMessage("§fVous avez placé un spawneur à §6" + cs.getCreatureTypeName().toUpperCase());
			if(Main.instance1.getDatasp().getConfigurationSection("Spawneurs")==null) {
				Main.instance1.getDatasp().set("Spawneurs.init", true);
				Main.instance1.saveData();
			}
			int c = 0;
			while (Main.instance1.getDatasp().getConfigurationSection("Spawneurs."+c) != null) {
				c+=1;
			}
			String world = e.getBlock().getLocation().getWorld().getName();
			double x = e.getBlock().getLocation().getBlockX();
			double y = e.getBlock().getLocation().getBlockY();
			double z = e.getBlock().getLocation().getBlockZ();
			Main.instance1.getDatasp().set("Spawneurs."+c+".world", world);
			Main.instance1.getDatasp().set("Spawneurs."+c+".x", x);
			Main.instance1.getDatasp().set("Spawneurs."+c+".y", y);
			Main.instance1.getDatasp().set("Spawneurs."+c+".z", z);
			Main.instance1.getDatasp().set("Spawneurs."+c+".type", cs.getCreatureTypeName().toUpperCase());
			Main.instance1.saveData();
		}
	}
	@EventHandler
	public void OnBreakBlock(BlockBreakEvent e) {
		if(e.getBlock().getType() == Material.SPAWNER) {
			e.setCancelled(true);
			Player player = e.getPlayer();
	        EntityType spawnType = null;
            
	        for(String aa : Main.instance1.getDatasp().getConfigurationSection("Spawneurs").getKeys(false)) {
	        	if(aa.equalsIgnoreCase("init")) {
					continue;
				}
	        	Bukkit.broadcastMessage(aa);
	        	String world = e.getBlock().getLocation().getWorld().getName();
	        	double x = e.getBlock().getLocation().getBlockX();
				double y = e.getBlock().getLocation().getBlockY();
				double z = e.getBlock().getLocation().getBlockZ();
				Location tmp = new Location(Bukkit.getWorld(world), x,y,z);
				
				String world2 = e.getBlock().getLocation().getWorld().getName();
				double x2 = Main.instance1.getDatasp().getDouble("Spawneurs."+aa+".x");
				double y2 = Main.instance1.getDatasp().getDouble("Spawneurs."+aa+".y");
				double z2 = Main.instance1.getDatasp().getDouble("Spawneurs."+aa+".z");
				Location tmp2 = new Location(Bukkit.getWorld(world2), x2,y2,z2);
				
				if(tmp==tmp2) {
					Bukkit.broadcastMessage("aaa");
					String tempa = Main.instance1.getDatasp().getString("Spawneurs."+aa+".type");
	            	spawnType = EntityType.valueOf(tempa);
	            	player.sendMessage("§fVous avez cassé un spawner à §6" + spawnType.getName().toUpperCase());
	            	break;
				}
			}
            
            ItemStack custom1 = new ItemStack(Material.SPAWNER, 1);
    		BlockStateMeta bsm = (BlockStateMeta) custom1.getItemMeta();
    		CreatureSpawner cs = (CreatureSpawner) bsm.getBlockState();
    		player.getInventory().addItem(custom1);
    		
    		//cs.setSpawnedType(EntityType.valueOf(spawnType.toString()));
    		
    		bsm.setBlockState(cs);
    		custom1.setItemMeta(bsm);
    		
    		Bukkit.getWorld(e.getBlock().getWorld().getName()).getBlockAt(e.getBlock().getLocation()).setType(Material.AIR);
		}
	}
}
