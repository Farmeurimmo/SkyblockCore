package main.java.fr.verymc.config;

import main.java.fr.verymc.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AsyncSaver {

    public static AsyncSaver instance;

    public AsyncSaver() {
        instance = this;
    }

    public void setAndSaveAsync(HashMap<String, Object> hashMap, FileConfiguration fileConfiguration, File file) {
        Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(Main.instance, new Runnable() {
            public void run() {
                for (Map.Entry<String, Object> objectEntry : hashMap.entrySet()) {
                    fileConfiguration.set(objectEntry.getKey(), objectEntry.getValue());
                }
                try {
                    fileConfiguration.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 0);
    }
}
