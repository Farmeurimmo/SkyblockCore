package fr.farmeurimmo.premsi.holos;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.SkinTrait;

public class HolosSetup implements Listener {
	
	static NPC npca = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "§6Auctions");
	
	static Plugin plugin = Bukkit.getPluginManager().getPlugin("SkyblockCore");
	
	static Location holo1 = new Location(Bukkit.getServer().getWorld("world"), -175.5, 114, -55.5);
	static Hologram hologram1 = HologramsAPI.createHologram(plugin, holo1);
	
	@EventHandler
	public void OnInteractWithNPC(NPCRightClickEvent e) {
		Player player = e.getClicker();
		if(e.getNPC().getName().equalsIgnoreCase("§6Auctions")) {
			player.chat("/ah");
		}
	}
	
    @SuppressWarnings("deprecation")
   	public static void SpawnPnj2(Location loc){
           SkinTrait skin = npca.getTrait(SkinTrait.class);
           npca.setAlwaysUseNameHologram(false);
           npca.setProtected(true);
           npca.setFlyable(true);
           skin.setSkinName("Farmeurimmo");
           npca.spawn(loc);
       }
    public static void RemoveNpc() {
    	npca.destroy();
    }
    public static void SpawnCrates() {
		
		hologram1.appendTextLine("§6Zone Enchantmenents");
		hologram1.appendTextLine("§7Vous souhaitez rajouter un peu piquant à votre stuff?");
		hologram1.appendTextLine("§7Cette zone est pour vous !");
	}
	public static void RemoveBoxeHolo() {
		hologram1.clearLines();
	}
}
