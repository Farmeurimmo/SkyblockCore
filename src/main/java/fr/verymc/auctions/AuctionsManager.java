package main.java.fr.verymc.auctions;

import com.iridium.iridiumskyblock.dependencies.ormlite.stmt.query.In;
import main.java.fr.verymc.core.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class AuctionsManager {

    public static AuctionsManager instance;

    public HashMap<Long, ItemStack> ahtype = new HashMap<>();

    public HashMap<Long, Double> ahprice = new HashMap<>();

    public HashMap<Long, String> ahseller = new HashMap<>();

    public HashMap<Long, UUID> ahsellerUUID = new HashMap<>();

    public HashMap<Integer, Inventory> ahinv = new HashMap<>();

    public ArrayList<Integer> slotstofill = new ArrayList<>();

    public HashMap<Long, ItemStack> alreadysaw = new HashMap<>();

    public AuctionsManager() {
        instance = this;
        addSlots();
        ReadForAuctions();
        autoUpdateInv();
    }

    public void addSlots(){
        slotstofill.clear();
        slotstofill.addAll(Arrays.asList(10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31,
                32, 33, 34, 37, 38, 39, 40, 41, 42, 43));
    }

    public int getNextSlot() {
        int slottoreturn = 0;
        if (slotstofill.size() == 0) {
            return slottoreturn;
        }
        while (slottoreturn != slotstofill.get(0)) {
            slottoreturn += 1;
        }
        return slottoreturn;
    }

    public void autoUpdateInv() {
        ItemStack custom4 = new ItemStack(Material.IRON_DOOR, 1);
        ItemMeta customd = custom4.getItemMeta();
        customd.setDisplayName("§6Retour §8| §7(clic gauche)");
        custom4.setItemMeta(customd);

        addSlots();
        alreadysaw.clear();
        ahinv.clear();
        int currentpage = 1;

        boolean running = true;

        while (running==true){
            Inventory inv = Bukkit.createInventory(null, 54, "§6Auctions #"+currentpage);
            inv.setItem(49, custom4);
            if(ahtype.size()==0){
                running=false;
                break;
            }
            for (Map.Entry<Long, ItemStack> aa : ahtype.entrySet()) {
                    if (slotstofill.size() >= 1) {
                        int slot = getNextSlot();
                        slotstofill.remove(new Integer(slot));
                        if(aa.getValue().getLore()==null){
                            aa.getValue().setLore(Arrays.asList("§f---------------","§6Prix: "+ahprice.get(aa.getKey()),
                                    "§6Vendeur: "+ahseller.get(aa.getKey()),"§f---------------"));
                        } else{
                            List<String> lore = aa.getValue().getLore();
                            lore.addAll(Arrays.asList("§f---------------","§6Prix: "+ahprice.get(aa.getKey()),
                                    "§6Vendeur: "+ahseller.get(aa.getKey()),"§f---------------"));
                        }
                        inv.setItem(slot, aa.getValue());
                        alreadysaw.put(aa.getKey(), aa.getValue());
                    } else {
                        currentpage += 1;
                    }
            }
            ahinv.put(currentpage, inv);
            if(ahtype.size()==alreadysaw.size()){
                running=false;
                break;
            }
        }

        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
            public void run() {
                autoUpdateInv();
            }
        }, 20);




        /*for(Map.Entry<Long, ItemStack> aa : ahtype.entrySet()){
            int nextSlot = GetNextSlot(slotstofill);
            ArrayList<String> lore = new ArrayList<>();
            if(aa.getValue().getLore()!=null) lore.addAll(aa.getValue().getLore());
            lore.addAll(Arrays.asList("§f----------","§6Prix: §a"+ahprice.get(aa.getKey()),
                    "§6Vendeur: §c"+ahseller.get(aa.getKey()),"§f----------"));
            aa.getValue().setLore(lore);
            inv.setItem(nextSlot, aa.getValue());
            slotstofill.remove(0);
        }*/
    }

    public void OpenAuction(Player player, int page) {

        for(Map.Entry<Integer, Inventory> invs : ahinv.entrySet()){
            if(invs.getKey()==page){
                player.openInventory(invs.getValue());
            }
        }

    }

    public void ReadForAuctions() {
        /*if(Main.instance.getDataah().getConfigurationSection("auction")!=null) {
            for (String aa : Main.instance.getDataah().getConfigurationSection("auction").getKeys(false)) {
                for (String bb : Main.instance.getDataah().getConfigurationSection("auction."+aa).getKeys(false)) {
                    ItemStack tosell = Main.instance.getDataah().getItemStack("auction."+aa+"."+bb+".itemStack");
                    Double price = Main.instance.getDataah().getDouble("auction."+aa+"."+bb+".price");
                    Long sysmillis = Main.instance.getDataah().getLong("auction."+aa+"."+bb+".price");
                    ahprice.put(tosell, price);
                    ahtime.put(tosell, sysmillis);
                }
            }
        }*/
    }

    public void addItemToAh(Player player, Double price, ItemStack tosell) {
        Long millis = System.currentTimeMillis();
        /*Main.instance.getDataah().set("auction."+millis+".price", price);
        Main.instance.getDataah().set("auction."+millis+".itemStack", tosell);
        Main.instance.getDataah().set("auction."+millis+".seller", player.getName());
        Main.instance.getDataah().set("auction."+millis+".sellerUUID", player.getUniqueId());
        Main.instance.saveData();*/

        ahtype.put(millis, tosell);
        ahprice.put(millis, price);
        ahseller.put(millis, player.getName());
        ahsellerUUID.put(millis, player.getUniqueId());
    }

}
