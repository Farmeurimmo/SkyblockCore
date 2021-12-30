package main.java.fr.verymc.eco;

import main.java.fr.verymc.utils.Maths;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Map.Entry;

public class BaltopManager {

    public static ArrayList<String> alreadyin = new ArrayList<String>();

    public static void GetBaltop(Player player, int page, int max) {
        int current = max - 10 + 1;
        int maxi = page * 10;
        String un = "", deux = "", trois = "", quatre = "", cinq = "", six = "", sept = "", huit = "", neuf = "", dix = "";
        while (current <= maxi) {
            Double bestvalue = (double) 0;
            String playerlayer = "N/A";
            for (Entry<String, Double> aa : EcoAccountsManager.Moneys.entrySet()) {
                if (alreadyin.contains(aa.getKey())) {
                    continue;
                }
                if (aa.getValue() > bestvalue) {
                    bestvalue = aa.getValue();
                    if (alreadyin.contains(playerlayer)) {
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
        //int TotalPlayers = EcoAccountsManager.Moneys.size();
        //int TotalPage = TotalPlayers/10+1;
        player.sendMessage("§6----- §f§lBaltop §6----- " +
                "\n1. " + un + ": " + Maths.arrondiNDecimales(EcoAccountsManager.GetMoney(un), 2) +
                "\n2. " + deux + ": " + Maths.arrondiNDecimales(EcoAccountsManager.GetMoney(deux), 2) +
                "\n3. " + trois + ": " + Maths.arrondiNDecimales(EcoAccountsManager.GetMoney(trois), 2) +
                "\n4. " + quatre + ": " + Maths.arrondiNDecimales(EcoAccountsManager.GetMoney(quatre), 2) +
                "\n5. " + cinq + ": " + Maths.arrondiNDecimales(EcoAccountsManager.GetMoney(cinq), 2) +
                "\n6. " + six + ": " + Maths.arrondiNDecimales(EcoAccountsManager.GetMoney(six), 2) +
                "\n7. " + sept + ": " + Maths.arrondiNDecimales(EcoAccountsManager.GetMoney(sept), 2) +
                "\n8. " + huit + ": " + Maths.arrondiNDecimales(EcoAccountsManager.GetMoney(huit), 2) +
                "\n9. " + neuf + ": " + Maths.arrondiNDecimales(EcoAccountsManager.GetMoney(neuf), 2) +
                "\n10. " + dix + ": " + Maths.arrondiNDecimales(EcoAccountsManager.GetMoney(dix), 2) +
                "\n§6--- §f§lPage " + page + " §6---");
        alreadyin.clear();
    }
}
