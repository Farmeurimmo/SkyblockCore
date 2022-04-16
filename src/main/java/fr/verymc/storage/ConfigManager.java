package main.java.fr.verymc.storage;

import main.java.fr.verymc.Main;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    public static ConfigManager instance;
    public FileConfiguration dataAh;
    public File ahFile;

    public ConfigManager() {
        instance = this;
        new AsyncSaver();
        setup();
    }

    public void setup() {
        ahFile = new File(Main.instance.getDataFolder(), "auctions.yml");

        if (!ahFile.exists()) {
            try {
                ahFile.createNewFile();
            } catch (IOException e) {
                Main.instance.getLogger().info("§c§lErreur lors de la création de auctions.yml");
            }
        }

        dataAh = YamlConfiguration.loadConfiguration(ahFile);


    }

    public FileConfiguration getDataah() {
        return dataAh;
    }


    public void reloadData() throws IOException {
        try {
            dataAh.load(ahFile);
        } catch (InvalidConfigurationException e) {
            Main.instance.getLogger().info("§c§lErreur lors de la sauvegarde!");
            e.printStackTrace();
        }
    }

    public void saveDataAh() {
        try {
            dataAh.save(ahFile);
        } catch (IOException e) {
            Main.instance.getLogger().info("§c§lErreur lors de la sauvegarde!");
        }
    }
}
