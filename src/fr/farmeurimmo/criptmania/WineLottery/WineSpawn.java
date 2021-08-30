package fr.farmeurimmo.criptmania.WineLottery;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.SkinTrait;

public class WineSpawn {
	
	static NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "§6Barman");

    @SuppressWarnings("deprecation")
	public static void SpawnPnj(Location loc){
        SkinTrait skin = npc.getTrait(SkinTrait.class);
        npc.setAlwaysUseNameHologram(false);
        npc.setProtected(true);
        npc.setFlyable(true);
        skin.setSkinName("Platinewold");
        npc.spawn(loc);
    }
}
