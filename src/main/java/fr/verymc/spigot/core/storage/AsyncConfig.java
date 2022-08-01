package main.java.fr.verymc.spigot.core.storage;

import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class AsyncConfig {

    public static AsyncConfig instance;

    public AsyncConfig() {
        instance = this;
    }

    public void setAndSaveAsync(HashMap<String, Object> hashMap, FileConfiguration fileConfiguration, File file) {
        CompletableFuture.runAsync(() -> {
            for (Map.Entry<String, Object> objectEntry : hashMap.entrySet()) {
                fileConfiguration.set(objectEntry.getKey(), objectEntry.getValue());
            }
            try {
                fileConfiguration.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void setAndSaveAsyncBlockCurrentThread(HashMap<String, Object> hashMap, FileConfiguration fileConfiguration, File file) {
        for (Map.Entry<String, Object> objectEntry : hashMap.entrySet()) {
            fileConfiguration.set(objectEntry.getKey(), objectEntry.getValue());
        }
        try {
            fileConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
