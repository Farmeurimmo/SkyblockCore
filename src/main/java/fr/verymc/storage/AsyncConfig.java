package main.java.fr.verymc.storage;

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
        CompletableFuture.supplyAsync(() -> {
            for (Map.Entry<String, Object> objectEntry : hashMap.entrySet()) {
                fileConfiguration.set(objectEntry.getKey(), objectEntry.getValue());
            }
            try {
                fileConfiguration.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }).join();

    }

    /*public ArrayList<Object> getObjectsStartup(FileConfiguration fileConfiguration, File file) {
        ArrayList<Object> objects = new ArrayList<>();
        //CompletableFuture<T>#supplyAsync(Supplier<T>)
        CompletableFuture.supplyAsync(() -> {
            //tries to fetch data from a database which doesn’t block the main thread but another thread.
            for (String key : fileConfiguration.getKeys(false)) {
                objects.add(fileConfiguration.get(key));
            }
            return objects;
        }).join(); //makes it blocking

        //never used
        return null;
    }*/
}
