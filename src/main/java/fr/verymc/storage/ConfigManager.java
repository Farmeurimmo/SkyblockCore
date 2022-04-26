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
    public FileConfiguration dataIslands;
    public FileConfiguration dataSkyblockUser;
    public FileConfiguration dataMinions;
    public FileConfiguration dataChests;
    public File ahFile;
    public File islandsFile;
    public File skyblockUserFile;
    public File minionsFile;
    public File chestsFile;


    public File subdir;


    public ConfigManager() {
        instance = this;
        new AsyncConfig();
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

        islandsFile = new File(Main.instance.getDataFolder(), "islands.yml");

        if (!islandsFile.exists()) {
            try {
                islandsFile.createNewFile();
            } catch (IOException e) {
                Main.instance.getLogger().info("§c§lErreur lors de la création de islands.yml");
            }
        }

        dataIslands = YamlConfiguration.loadConfiguration(islandsFile);

        skyblockUserFile = new File(Main.instance.getDataFolder(), "skyblockUser.yml");

        if (!skyblockUserFile.exists()) {
            try {
                skyblockUserFile.createNewFile();
            } catch (IOException e) {
                Main.instance.getLogger().info("§c§lErreur lors de la création de skyblockUser.yml");
            }
        }

        dataSkyblockUser = YamlConfiguration.loadConfiguration(skyblockUserFile);

        minionsFile = new File(Main.instance.getDataFolder(), "minions.yml");

        if (!minionsFile.exists()) {
            try {
                minionsFile.createNewFile();
            } catch (IOException e) {
                Main.instance.getLogger().info("§c§lErreur lors de la création de minions.yml");
            }
        }

        dataMinions = YamlConfiguration.loadConfiguration(minionsFile);

        chestsFile = new File(Main.instance.getDataFolder(), "chests.yml");

        if (!chestsFile.exists()) {
            try {
                chestsFile.createNewFile();
            } catch (IOException e) {
                Main.instance.getLogger().info("§c§lErreur lors de la création de chests.yml");
            }
        }

        dataChests = YamlConfiguration.loadConfiguration(chestsFile);

        subdir = new File(Main.instance.getDataFolder().getPath() + System.getProperty("file.separator") +
                "backups");
        subdir.mkdir();


    }

    public FileConfiguration getDataah() {
        return dataAh;
    }

    public FileConfiguration getDataIslands() {
        return dataIslands;
    }

    public FileConfiguration getDataSkyblockUser() {
        return dataSkyblockUser;
    }

    public FileConfiguration getDataMinions() {
        return dataMinions;
    }

    public FileConfiguration getDataChests() {
        return dataChests;
    }


    public void reloadData() throws IOException {
        try {
            dataAh.load(ahFile);
        } catch (InvalidConfigurationException e) {
            Main.instance.getLogger().info("§c§lErreur lors de la sauvegarde!");
            e.printStackTrace();
        }
        try {
            dataIslands.load(islandsFile);
        } catch (InvalidConfigurationException e) {
            Main.instance.getLogger().info("§c§lErreur lors de la sauvegarde!");
        }
        try {
            dataSkyblockUser.load(skyblockUserFile);
        } catch (InvalidConfigurationException e) {
            Main.instance.getLogger().info("§c§lErreur lors de la sauvegarde!");
        }
        try {
            dataMinions.load(minionsFile);
        } catch (InvalidConfigurationException e) {
            Main.instance.getLogger().info("§c§lErreur lors de la sauvegarde!");
        }
        try {
            dataChests.load(chestsFile);
        } catch (InvalidConfigurationException e) {
            Main.instance.getLogger().info("§c§lErreur lors de la sauvegarde!");
        }
    }
}
