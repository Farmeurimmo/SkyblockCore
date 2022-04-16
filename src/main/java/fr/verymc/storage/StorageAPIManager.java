package main.java.fr.verymc.storage;

import main.java.fr.verymc.Main;
import main.java.fr.verymc.island.Island;
import main.java.fr.verymc.island.IslandManager;
import main.java.fr.verymc.minions.Minion;
import main.java.fr.verymc.minions.MinionManager;
import org.bukkit.Bukkit;

import java.util.ArrayList;

public class StorageAPIManager {

    public static StorageAPIManager instance;

    public StorageAPIManager() {
        instance = this;
        getDataFromAPI();
        sendDataToAPIAuto();
    }

    public void getDataFromAPI() {

        Bukkit.getScheduler().scheduleAsyncDelayedTask(Main.instance, new Runnable() {
            public void run() {

        //API FETCH DATA

        ArrayList<Minion> minions = new ArrayList<>();
        //DATA MINIONS

        ArrayList<Island> islands = new ArrayList<>();
        //DATA ISLANDS

        ArrayList<SkyblockUser> skyblockUsers = new ArrayList<>();
        //DATA USERS


                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
                    public void run() {

                        //SET NEW VALUE WITHOUT THREAD CONFLICTS
                        //CurrentThreadModificationException can happen here ?????

                        //SEND Minions to -> MinionManager.instance.minions

                        //SEND Islands to -> IslandManager.instance.islands

                        //SEND SkyblockUser to -> SkyblockUserManager.instance.users

                    }
                }, 0);
            }
        }, 0);
    }

    public void sendDataToAPIAuto() {
        Bukkit.getScheduler().scheduleAsyncDelayedTask(Main.instance, new Runnable() {
            public void run() {


                // API SEND DATA

                //CLEAR ALL DATAS WHO ARE NOT STORED IN THE PLUGIN THAT ARE IN THE API


                ArrayList<Minion> minions = MinionManager.instance.minions;
                //SEND TO API
                ArrayList<Island> islands = IslandManager.instance.islands;
                //SEND TO API
                ArrayList<SkyblockUser> skyblockUsers = SkyblockUserManager.instance.users;
                //SEND TO API


                sendDataToAPIAuto();
            }
        }, 20 * 120);
    }
}
