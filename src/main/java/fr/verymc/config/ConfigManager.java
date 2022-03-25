package main.java.fr.verymc.config;

import main.java.fr.verymc.Main;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    public static ConfigManager instance;
    public FileConfiguration dataAtoutsChallenges;
    public FileConfiguration dataFly;
    public FileConfiguration dataEco;
    public FileConfiguration dataBloc;
    public FileConfiguration dataAh;
    public FileConfiguration dataMinion;
    public File fileChallengesAtouts;
    public File flyFile;
    public File ecoFile;
    public File bloflyFile;
    public File ahFile;
    public File minionFile;

    public ConfigManager() {
        instance = this;
        new AsyncSaver();
        setup();
    }

    public void setup() {
        fileChallengesAtouts = new File(Main.instance.getDataFolder(), "Challenges.yml");
        flyFile = new File(Main.instance.getDataFolder(), "Fly.yml");
        ecoFile = new File(Main.instance.getDataFolder(), "Eco.yml");
        bloflyFile = new File(Main.instance.getDataFolder(), "Block.yml");
        ahFile = new File(Main.instance.getDataFolder(), "auctions.yml");
        minionFile = new File(Main.instance.getDataFolder(), "minions.yml");

        if (!fileChallengesAtouts.exists()) {
            try {
                fileChallengesAtouts.createNewFile();
            } catch (IOException e) {
                Main.instance.getLogger().info("§c§lErreur lors de la création de challenges.yml");
            }
        }

        dataAtoutsChallenges = YamlConfiguration.loadConfiguration(fileChallengesAtouts);

        if (!flyFile.exists()) {
            try {
                flyFile.createNewFile();
            } catch (IOException e) {
                Main.instance.getLogger().info("§c§lErreur lors de la création de fly.yml");
            }
        }

        dataFly = YamlConfiguration.loadConfiguration(flyFile);

        if (!ecoFile.exists()) {
            try {
                ecoFile.createNewFile();
            } catch (IOException e) {
                Main.instance.getLogger().info("§c§lErreur lors de la création de eco.yml");
            }
        }

        dataEco = YamlConfiguration.loadConfiguration(ecoFile);

        if (!bloflyFile.exists()) {
            try {
                bloflyFile.createNewFile();
            } catch (IOException e) {
                Main.instance.getLogger().info("§c§lErreur lors de la création de block.yml");
            }
        }

        dataBloc = YamlConfiguration.loadConfiguration(bloflyFile);

        if (!ahFile.exists()) {
            try {
                ahFile.createNewFile();
            } catch (IOException e) {
                Main.instance.getLogger().info("§c§lErreur lors de la création de auctions.yml");
            }
        }

        dataAh = YamlConfiguration.loadConfiguration(ahFile);

        if (!minionFile.exists()) {
            try {
                minionFile.createNewFile();
            } catch (IOException e) {
                Main.instance.getLogger().info("§c§lErreur lors de la création de minions.yml");
            }
        }

        dataMinion = YamlConfiguration.loadConfiguration(minionFile);


    }

    public FileConfiguration getDataFly() {
        return dataFly;
    }

    public FileConfiguration getDataAtoutsChallenges() {
        return dataAtoutsChallenges;
    }

    public FileConfiguration getDataEco() {
        return dataEco;
    }

    public FileConfiguration getDataBloc() {
        return dataBloc;
    }

    public FileConfiguration getDataah() {
        return dataAh;
    }

    public FileConfiguration getDataMinion() {
        return dataMinion;
    }


    public void reloadData() throws IOException {
        try {
            dataAtoutsChallenges.load(fileChallengesAtouts);
        } catch (InvalidConfigurationException e) {
            Main.instance.getLogger().info("§c§lErreur lors de la sauvegarde!");
            e.printStackTrace();
        }
        try {
            dataFly.load(flyFile);
        } catch (InvalidConfigurationException e) {
            Main.instance.getLogger().info("§c§lErreur lors de la sauvegarde!");
            e.printStackTrace();
        }
        try {
            dataEco.load(ecoFile);
        } catch (InvalidConfigurationException e) {
            Main.instance.getLogger().info("§c§lErreur lors de la sauvegarde!");
            e.printStackTrace();
        }
        try {
            dataBloc.load(bloflyFile);
        } catch (InvalidConfigurationException e) {
            Main.instance.getLogger().info("§c§lErreur lors de la sauvegarde!");
            e.printStackTrace();
        }
        try {
            dataAh.load(ahFile);
        } catch (InvalidConfigurationException e) {
            Main.instance.getLogger().info("§c§lErreur lors de la sauvegarde!");
            e.printStackTrace();
        }
        try {
            dataMinion.load(minionFile);
        } catch (InvalidConfigurationException e) {
            Main.instance.getLogger().info("§c§lErreur lors de la sauvegarde!");
            e.printStackTrace();
        }
    }

    public void saveData() {
        try {
            dataBloc.save(bloflyFile);
        } catch (IOException e) {
            Main.instance.getLogger().info("§c§lErreur lors de la sauvegarde!");
        }
    }

    public void saveDataChallenges() {
        try {
            dataAtoutsChallenges.save(fileChallengesAtouts);
        } catch (IOException e) {
            Main.instance.getLogger().info("§c§lErreur lors de la sauvegarde!");
        }
    }

    public void saveDataEco() {
        try {
            dataEco.save(ecoFile);
        } catch (IOException e) {
            Main.instance.getLogger().info("§c§lErreur lors de la sauvegarde!");
        }
    }

    public void saveDataAh() {
        try {
            dataAh.save(ahFile);
        } catch (IOException e) {
            Main.instance.getLogger().info("§c§lErreur lors de la sauvegarde!");
        }
    }

    public void saveDataMinions() {
        try {
            dataMinion.save(minionFile);
        } catch (IOException e) {
            Main.instance.getLogger().info("§c§lErreur lors de la sauvegarde!");
        }
    }


    public void saveDataFly() {
        try {
            dataFly.save(flyFile);
        } catch (IOException e) {
            Main.instance.getLogger().info("§c§lErreur lors de la sauvegarde!");
        }
    }
}
