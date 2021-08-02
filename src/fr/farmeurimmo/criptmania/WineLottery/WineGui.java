package fr.farmeurimmo.criptmania.WineLottery;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import fr.farmeurimmo.criptmania.utils.SendActionBar;
import net.citizensnpcs.api.event.NPCRightClickEvent;

public class WineGui implements Listener {
	
	@EventHandler
	public void OnInteractWithNPC(NPCRightClickEvent e) {
		Player player = e.getClicker();
		SendActionBar.SendActionBarMsg(player, "§cLe bar est encore en développement !");
	}

}
