package fr.farmeurimmo.criptmania.arene;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.Plugin;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.SkinTrait;

public class ArenaSetup {
	
	static NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "§6Arène");
	
	static Location holo = new Location(Bukkit.getServer().getWorld("world"), -143.5, 110, -50.5);
	static Plugin plugin = Bukkit.getPluginManager().getPlugin("SkyblockCore");
	static Hologram hologram = HologramsAPI.createHologram(plugin, holo);
	
	static Location holo1 = new Location(Bukkit.getServer().getWorld("world"), -182.5, 113, -65.5);
	static Hologram hologram1 = HologramsAPI.createHologram(plugin, holo1);

    @SuppressWarnings("deprecation")
	public static void SpawnPnj(Location loc){
        SkinTrait skin = npc.getTrait(SkinTrait.class);
        npc.setAlwaysUseNameHologram(false);
        npc.setProtected(true);
        npc.setFlyable(true);
        skin.setSkinName("Farmeurimmo");
        npc.spawn(loc);
    }
    public static void RemovePnj() {
    	npc.destroy();
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
