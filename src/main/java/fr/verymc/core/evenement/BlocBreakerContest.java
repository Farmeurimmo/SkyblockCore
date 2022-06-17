package main.java.fr.verymc.core.evenement;

import main.java.fr.verymc.Main;
import main.java.fr.verymc.commons.enums.ServerType;
import main.java.fr.verymc.core.eco.EcoAccountsManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.text.NumberFormat;
import java.util.*;

public class BlocBreakerContest {

    public static BlocBreakerContest instance;
    public HashMap<UUID, Integer> pointMap = new HashMap<>();
    public Material material;
    public boolean isActive = false;
    public int hour = 18;
    public int min = 30;
    public int duration = 60 * 10;
    public long timeStarting = 0;

    public ArrayList<Material> materialArrayList = new ArrayList<>(Arrays.asList(Material.COBBLESTONE, Material.OAK_WOOD, Material.ACACIA_WOOD,
            Material.BIRCH_WOOD, Material.DARK_OAK_WOOD, Material.JUNGLE_WOOD, Material.SPRUCE_WOOD));

    public BlocBreakerContest() {
        instance = this;
        checkForIt();
    }

    public void checkForIt() {
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(Main.instance, new Runnable() {
            @Override
            public void run() {
                TimeZone.setDefault(TimeZone.getTimeZone("Europe/Paris"));
                Calendar calendar = Calendar.getInstance();
                if (hour == calendar.getTime().getHours() && calendar.getTime().getMinutes() == min
                        && calendar.getTime().getSeconds() == 0) {
                    startContest();
                }
            }
        }, 0L, 20L);
    }

    public void startContest() {
        isActive = true;
        Random random = new Random();
        material = materialArrayList.get(random.nextInt(materialArrayList.size()));
        timeStarting = System.currentTimeMillis();

        if (Main.instance.serverType == ServerType.ISLAND) {
            Bukkit.broadcastMessage("§6§lBlocBreakerContest §8» §fLe concours de cassage blocs a commencé, le bloc choisit est §e"
                    + material.toString() + "§f. Minez en le plus possible et obtenez des récompenses en fonction de votre position " +
                    "dans le classement. Il se terminera dans §e" + duration / 60 + " minutes§f.");
        } else {
            Bukkit.broadcastMessage("§6§lBlocBreakerContest §8» §fLe concours de cassage blocs a commencé, merci de se rendre sur les serveurs d'îles " +
                    "pour participer. Toutes les informations sont disponnibles vous seront données à votre connexion à ceux-ci.");
        }

        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
            @Override
            public void run() {
                isActive = false;
                material = null;
                makeTopAndGiveReward();
            }
        }, 20 * duration);
    }

    public void addBlock(UUID uuid) {
        if (pointMap.containsKey(uuid)) {
            pointMap.put(uuid, pointMap.get(uuid) + 1);
        } else {
            pointMap.put(uuid, 1);
        }
    }

    public void makeTopAndGiveReward() {
        if (Main.instance.serverType != ServerType.ISLAND) {
            return;
        }
        HashMap<Integer, UUID> position = new HashMap<>();

        ArrayList<UUID> uuidsPositionned = new ArrayList<>();

        int currentPos = 1;

        int totalBreaked = 0;
        for (Map.Entry<UUID, Integer> entry : pointMap.entrySet()) {
            totalBreaked += entry.getValue();
        }

        while (currentPos <= pointMap.size()) {
            int best = 0;
            UUID last = null;
            for (Map.Entry<UUID, Integer> entry : pointMap.entrySet()) {
                if (uuidsPositionned.contains(entry.getKey())) continue;
                if (entry.getValue() > best) {
                    best = entry.getValue();
                    if (last != null) {
                        uuidsPositionned.remove(last);
                    }
                    last = entry.getKey();
                    position.put(currentPos, entry.getKey());
                    uuidsPositionned.add(entry.getKey());
                }
            }
            currentPos++;
        }
        String toSend = "\n§6Classement du Bloc Breaker Contest: (" + totalBreaked + " blocs cassés)\n";
        for (int i = 1; i <= 10; i++) {
            if (!position.containsKey(i)) {
                break;
            }
            toSend += "§f" + i + ". §6" + Bukkit.getOfflinePlayer(position.get(i)).getName() + "  §e" +
                    pointMap.get(position.get(i)) + " points\n";
        }
        Bukkit.broadcastMessage(toSend);
        final int totalPlayers = position.size();
        double toGive = totalPlayers * 18000;
        for (Map.Entry<Integer, UUID> entry : position.entrySet()) {
            Player player = Bukkit.getPlayer(entry.getValue());
            double breaked = pointMap.get(entry.getValue());
            double wonPerCent = breaked / totalBreaked;
            double moneyWon = wonPerCent * toGive;
            EcoAccountsManager.instance.addFoundsUUID(player.getUniqueId(), moneyWon, true);
            if (player != null) {
                player.sendMessage("§6§lBlocBreakerContest §8» §fVous avez été classé §6#" + entry.getKey() +
                        " §favec §e" + pointMap.get(entry.getValue()) + " points§f. Vous avez cassé §6" + NumberFormat.getInstance().format(wonPerCent * 100) +
                        "% §fdu total des blocs cassés. Vous avez donc gagné §6" + NumberFormat.getInstance().format(moneyWon) + "$§f.\n");
            }
        }
        position.clear();
        pointMap.clear();
        uuidsPositionned.clear();
    }

}
