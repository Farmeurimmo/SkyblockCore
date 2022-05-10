package main.java.fr.verymc.gui;

import main.java.fr.verymc.utils.ItemStackBuilder;
import main.java.fr.verymc.utils.PreGenItems;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class TradeManager {
    public static TradeManager instance;
    public Player playerOne;
    public Player playerTwo;

    public Inventory tradeInv;
    public int playerOneMoneyAmount;
    public int playerTwoMoneyAmount;

    public TradeManager(Player playerOneParam, Player playerTwoParam, Inventory tradeInvParam, int moneyAmountParam) {
        playerOne = playerOneParam;
        playerTwo = playerTwoParam;
        playerOneMoneyAmount = moneyAmountParam;
        playerTwoMoneyAmount = moneyAmountParam;
        tradeInv = tradeInvParam;
        instance = this;
    }

    public static Inventory TradeGuiBuilder(int moneyAmount, Player playerOne, Player playerTwo) {
        Inventory inv = Bukkit.createInventory(null, 54, "§6Echange");
        ItemStack yellowStainedGlass = (new ItemStackBuilder(Material.YELLOW_STAINED_GLASS_PANE)).setName("§a").getItemStack();
        ItemStack greenStainedGlass = (new ItemStackBuilder(Material.GREEN_STAINED_GLASS_PANE)).setName("§aConfirmer").getItemStack();
        ItemStack redStainedGlass = (new ItemStackBuilder(Material.RED_STAINED_GLASS_PANE)).setName("§cAnnuler").getItemStack();
        ItemStack goldIngot = (new ItemStackBuilder(Material.GOLD_INGOT)).setName("§eBalance de l'échange").setLore("§6    >> " + moneyAmount + "$").getItemStack();
        for (int i = 0; i < 10; i++) {
            inv.setItem(i, yellowStainedGlass);
        }
        inv.setItem(2, PreGenItems.instance.getHead(playerOne));
        inv.setItem(6, PreGenItems.instance.getHead(playerTwo));
        inv.setItem(13, yellowStainedGlass);
        inv.setItem(17, yellowStainedGlass);
        inv.setItem(18, yellowStainedGlass);
        inv.setItem(22, yellowStainedGlass);
        inv.setItem(26, yellowStainedGlass);
        inv.setItem(27, yellowStainedGlass);
        inv.setItem(31, yellowStainedGlass);
        inv.setItem(26, yellowStainedGlass);
        inv.setItem(35, yellowStainedGlass);
        inv.setItem(36, yellowStainedGlass);
        inv.setItem(37, redStainedGlass);
        inv.setItem(38, goldIngot);
        inv.setItem(39, greenStainedGlass);
        inv.setItem(40, yellowStainedGlass);
        inv.setItem(41, redStainedGlass);
        inv.setItem(42, goldIngot);
        inv.setItem(43, greenStainedGlass);
        for (int i = 44; i < 54; i++) {
            inv.setItem(i, yellowStainedGlass);
        }
        return inv;
    }
}
