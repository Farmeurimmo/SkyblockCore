package fr.farmeurimmo.criptmania.WineLottery;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.farmeurimmo.criptmania.utils.SendActionBar;
import net.citizensnpcs.api.event.NPCRightClickEvent;

public class WineGui implements Listener {
	
	@EventHandler
	public void OnInteractWithNPC(NPCRightClickEvent e) {
		Player player = e.getClicker();
		SendActionBar.SendActionBarMsg(player, "§cLe bar est encore en développement !");
		BuyWinePotion.MakeWinePotionGui(player);
	}
	@SuppressWarnings("deprecation")
	@EventHandler
	public void OnDrink(PlayerItemConsumeEvent e) {
		Player player = e.getPlayer();
		ItemStack aaa = e.getItem();
		if(aaa != null) {
			if(aaa.getType() == Material.POTION) {
				String bbb = aaa.getItemMeta().getDisplayName();
				if(bbb.contains("Cidre")) {
					if(!player.hasPotionEffect(PotionEffectType.CONFUSION)) {
					player.getItemInHand().setAmount(0);
					player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 280, 0));
					player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 280, 0));
					player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 280, 0));
					} else {
						player.getItemInHand().setAmount(0);
						int rest = player.getPotionEffect(PotionEffectType.CONFUSION).getDuration();
						player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, rest + 280, player.getPotionEffect(PotionEffectType.CONFUSION).getAmplifier() + 1));
						player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, rest + 280, player.getPotionEffect(PotionEffectType.CONFUSION).getAmplifier() + 1));
						player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, rest + 280, player.getPotionEffect(PotionEffectType.CONFUSION).getAmplifier() + 1));
					}
				}
			}
		}
	}
}
