package fr.farmeurimmo.criptmania.arene;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

import fr.farmeurimmo.criptmania.utils.SendActionBar;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.SkinTrait;

public class ArenaSetup implements Listener {
	
	static NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "§6Arène");
	static NPC npca = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "§6Auctions");
	
	static Location holo = new Location(Bukkit.getServer().getWorld("world"), -143.5, 110, -50.5);
	static Plugin plugin = Bukkit.getPluginManager().getPlugin("SkyblockCore");
	static Hologram hologram = HologramsAPI.createHologram(plugin, holo);
	
	static Location holo1 = new Location(Bukkit.getServer().getWorld("world"), -182.5, 111, -65.5);
	static Hologram hologram1 = HologramsAPI.createHologram(plugin, holo1);
	
	@EventHandler
	public void OnInteractWithNPC(NPCRightClickEvent e) {
		Player player = e.getClicker();
		if(e.getNPC().getName().equalsIgnoreCase("§6Auctions")) {
			player.chat("/ah");
		}
		if(player.hasPermission("*")) {
			if(e.getNPC().getName().equalsIgnoreCase("§6Arène")) {
				
		}
		} else {
			SendActionBar.SendActionBarMsg(player, "§cVous n'avez pas la permission !");
		}
	}
	
    @SuppressWarnings("deprecation")
	public static void SpawnPnj(Location loc){
        SkinTrait skin = npc.getTrait(SkinTrait.class);
        npc.setAlwaysUseNameHologram(false);
        npc.setProtected(true);
        npc.setFlyable(true);
        skin.setSkinName("Farmeurimmo");
        npc.spawn(loc);
    }
    @SuppressWarnings("deprecation")
   	public static void SpawnPnj2(Location loc){
           SkinTrait skin = npca.getTrait(SkinTrait.class);
           npca.setAlwaysUseNameHologram(false);
           npca.setProtected(true);
           npca.setFlyable(true);
           skin.setSkinName("Tomashb");
           npca.spawn(loc);
       }
    public static void SpawnCrates() {
		hologram.appendTextLine("§6Arène");
		hologram.appendTextLine("§7Vous souhaitez vous régler un différent?");
		hologram.appendTextLine("§7L'arène est pour vous !");
		
		hologram1.appendTextLine("§6Zone Enchantmenents");
		hologram1.appendTextLine("§7Vous souhaitez rajouter un peu piquant à votre stuff?");
		hologram1.appendTextLine("§7Cette zone est pour vous !");
	}
	public static void RemoveBoxeHolo() {
		hologram.clearLines();
		hologram1.clearLines();
	}
}
