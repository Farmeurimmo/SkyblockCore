package fr.farmeurimmo.verymc.eco;

import java.util.ArrayList;
import java.util.Map.Entry;

import org.bukkit.entity.Player;

public class BaltopManager {
	
	public static ArrayList<String> alreadyin = new ArrayList<String>();
	
	public static void GetBaltop(Player player, int page, int max) {
		int current = max-10+1;
		int maxi = page*10;
		String un = "",deux = "",trois = "",quatre = "",cinq = "",six = "",sept = "",huit = "",neuf = "",dix = "";
		while (current <= maxi) {
			int bestvalue = 0;
			String playerlayer = "N/A";
		for(Entry<String, Integer> aa : EcoAccountsManager.Moneys.entrySet()) {
			if(alreadyin.contains(aa.getKey())) {
				continue;
			}
			if(aa.getValue() > bestvalue) {
				bestvalue = aa.getValue();
				if(alreadyin.contains(playerlayer)) {
				alreadyin.remove(playerlayer);
				}
				playerlayer = aa.getKey();
				alreadyin.add(aa.getKey());
			}
		}
		if (current == 1) {
			un = playerlayer;
		}
		if (current == 2) {
			deux = playerlayer;
		}
		if (current == 3) {
			trois = playerlayer;
		}
		if (current == 4) {
			quatre = playerlayer;
		}
		if (current == 5) {
			cinq = playerlayer;
		}
		if (current == 6) {
			six = playerlayer;
		}
		if (current == 7) {
			sept = playerlayer;
		}
		if (current == 8) {
			huit = playerlayer;
		}
		if (current == 9) {
			neuf = playerlayer;
		}
		if (current == 10) {
			dix = playerlayer;
		}
		
		current += 1;
		}
		int TotalPlayers = EcoAccountsManager.Moneys.size();
		int TotalPage = TotalPlayers/10+1;
		player.sendMessage("§6----- §f§lBaltop §6----- \n1. "+un+": "+EcoAccountsManager.GetMoney(un)+"\n2. "+deux+": "+EcoAccountsManager.GetMoney(deux)
		+"\n3. "+trois+": "+EcoAccountsManager.GetMoney(trois)+"\n4. "+quatre+": "+EcoAccountsManager.GetMoney(quatre)+"\n5. "+cinq+": "+EcoAccountsManager.GetMoney(cinq)
		+"\n6. "+six+": "+EcoAccountsManager.GetMoney(six)+"\n7. "+sept+": "+EcoAccountsManager.GetMoney(sept)+"\n8. "+huit+": "+EcoAccountsManager.GetMoney(huit)
		+"\n9. "+neuf+": "+EcoAccountsManager.GetMoney(neuf)+"\n10. "+dix+": "+EcoAccountsManager.GetMoney(dix)+"\n§6--- §f§lPage "+ page+"/"+TotalPage+" §6---");
		alreadyin.clear();
	}
}
