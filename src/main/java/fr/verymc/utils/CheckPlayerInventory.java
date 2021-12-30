package main.java.fr.verymc.utils;

import org.bukkit.entity.Player;

public class CheckPlayerInventory {

    public static boolean CheckPlayerInventoryForSlot(Player player) {
        checkFreeSlot:
        {
            for (int i = 0; i < 36; i++) {
                if (player.getInventory().getItem(i) == null) {
                    break checkFreeSlot;
                }
            }

            //Failed check
            return false;
        }

        //Check passed, do something.
        return true;
    }
}
