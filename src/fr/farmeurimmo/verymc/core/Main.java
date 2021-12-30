package fr.farmeurimmo.verymc.core;

import com.iridium.iridiumskyblock.api.IridiumSkyblockAPI;
import fr.farmeurimmo.verymc.WineLottery.WineGui;
import fr.farmeurimmo.verymc.WineLottery.WineSpawn;
import fr.farmeurimmo.verymc.atout.AtoutCmd;
import fr.farmeurimmo.verymc.atout.AtoutGui;
import fr.farmeurimmo.verymc.atout.BuyAtoutGui;
import fr.farmeurimmo.verymc.blocks.*;
import fr.farmeurimmo.verymc.challenges.ChallengesBlockBreak;
import fr.farmeurimmo.verymc.challenges.ChallengesCmd;
import fr.farmeurimmo.verymc.challenges.ChallengesGuis;
import fr.farmeurimmo.verymc.challenges.ChallengesReset;
import fr.farmeurimmo.verymc.cmd.base.*;
import fr.farmeurimmo.verymc.cmd.moderation.*;
import fr.farmeurimmo.verymc.crates.CratesManager;
import fr.farmeurimmo.verymc.crates.KeyCmd;
import fr.farmeurimmo.verymc.eco.EcoAccountsManager;
import fr.farmeurimmo.verymc.eco.EconomyImplementer;
import fr.farmeurimmo.verymc.eco.VaultHook;
import fr.farmeurimmo.verymc.evenement.ChatReaction;
import fr.farmeurimmo.verymc.events.*;
import fr.farmeurimmo.verymc.featherfly.CountdownFly;
import fr.farmeurimmo.verymc.featherfly.DailyFlyCmd;
import fr.farmeurimmo.verymc.featherfly.FeatherFlyCmd;
import fr.farmeurimmo.verymc.featherfly.FeatherFlyInteract;
import fr.farmeurimmo.verymc.gui.AfkMineCaptchaGui;
import fr.farmeurimmo.verymc.gui.Farm2WinGui;
import fr.farmeurimmo.verymc.gui.MenuGui;
import fr.farmeurimmo.verymc.gui.WarpGui;
import fr.farmeurimmo.verymc.holos.HolosSetup;
import fr.farmeurimmo.verymc.items.FarmHoeCmd;
import fr.farmeurimmo.verymc.items.FarmHoeGui;
import fr.farmeurimmo.verymc.items.FarmHoeManager;
import fr.farmeurimmo.verymc.items.ItemLegCmd;
import fr.farmeurimmo.verymc.scoreboard.ScoreBoard;
import fr.farmeurimmo.verymc.shopgui.*;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main extends JavaPlugin implements Listener {

    public static ArrayList<Player> pending = new ArrayList<Player>();

    public static ArrayList<Player> haverequest = new ArrayList<Player>();
    public static Main instance1;
    public static HashMap<String, String> tpatarget = new HashMap<>();
    static LuckPerms api;
    private static Main instance;
    private static HashMap<String, Integer> spawncooldown = new HashMap<>();
    public EconomyImplementer economyImplementer;
    public FileConfiguration data;
    public FileConfiguration datac;
    public FileConfiguration dataz;
    public FileConfiguration datablc;
    public File dfile;
    public File cfile;
    public File zfile;
    public File blcfile;
    private VaultHook vaultHook;

    public static Main getInstance() {
        return instance;
    }

    public void setTarget(String uuid, String aaa) {
        if (aaa == null)
            tpatarget.remove(uuid);
        else
            tpatarget.put(uuid, aaa);
    }

    public String getTarget(String player) {
        if (tpatarget.containsKey(player))
            return tpatarget.get(player);
        else
            return null;
    }

    public void setCooldown(String uuid, Integer time) {
        if (time == null)
            spawncooldown.remove(uuid);
        else
            spawncooldown.put(uuid, time);
    }

    public int getCooldown(String player) {
        return (spawncooldown.get(player) == null ? 0 : spawncooldown.get(player));
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        instance = this;
        instance1 = this;
        Bukkit.getPluginManager().isPluginEnabled("LuckPerms");
        Bukkit.getPluginManager().isPluginEnabled("Citizens");
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (Bukkit.getPluginManager().getPlugin("LuckPerms") != null) {
            System.out.println("Le plugin LuckPerms a été trouv§ !");
        } else {
            getLogger().warning("Le plugin LuckPerms est manquant.");
            Bukkit.getPluginManager().disablePlugin(this);
        }
        System.out.println("-----------------------------------------------------------------------------------------------------");
        System.out.println("Initialisation de l'api LuckPerms en cours...");
        if (provider != null) {
            api = provider.getProvider();
            System.out.println("API initialis§e !");
            System.out.println("-----------------------------------------------------------------------------------------------------");
        }
        if (Bukkit.getPluginManager().getPlugin("Citizens") != null) {
            System.out.println("Le plugin Citizens a été trouv§ !");
            System.out.println("Initialisation de l'api Citizens en cours...");
            System.out.println("API initialis§e !");
            System.out.println("-----------------------------------------------------------------------------------------------------");
        } else {
            getLogger().warning("Le plugin Citizens est manquant.");
            Bukkit.getPluginManager().disablePlugin(this);
        }
        if (Bukkit.getPluginManager().getPlugin("HolographicDisplays") != null) {
            System.out.println("Le plugin HolographicDisplays a été trouv§ !");
            System.out.println("Initialisation de l'api HolographicDisplays en cours...");
            System.out.println("API initialis§e !");
            System.out.println("-----------------------------------------------------------------------------------------------------");
        } else {
            getLogger().warning("Le plugin HolographicDisplays est manquant.");
            Bukkit.getPluginManager().disablePlugin(this);
        }
        if (IridiumSkyblockAPI.getInstance() == null) {
            Bukkit.getPluginManager().disablePlugin(this);
        }
        System.out.println("Initialisation des classes et des m§thodes en cours...");
        setup();
        economyImplementer = new EconomyImplementer();
        vaultHook = new VaultHook();
        vaultHook.hook();
        EcoAccountsManager.UpdateHash();
        Main.spawncooldown.clear();
        //BossBar.CreateBossBar();
        BuildCmd.Build.clear();
        WineSpawn.SpawnPnj(new Location(Bukkit.getServer().getWorld("world"), -184.5, 70.5, -77.5, -90, 0));
        HolosSetup.SpawnPnj2(new Location(Bukkit.getServer().getWorld("world"), -155.5, 71, -60.5, 90, 0),
                new Location(Bukkit.getServer().getWorld("world"), -172.5, 71, -64.5, 90, 0));
        HolosSetup.SpawnCrates();
        for (Player player : Bukkit.getOnlinePlayers()) {
            ScoreBoard.setScoreBoard(player);
        }
        ChatReaction.WriteWords();
        ChatReaction.StartChatReaction();
        ChallengesReset.CheckForReset();
        CratesManager.SpawnCrates();
        ScoreBoard.updateScoreBoard();
        FeatherFlyInteract.ReadForTempFly();
        BuyShopItem.GenPriceShopStartup();
        GenShopPage.GetNumberOfPage();
        GenShopPage.GenenerateShopPageStartup("Blocs");
        GenShopPage.GenenerateShopPageStartup("Agriculture");
        GenShopPage.GenenerateShopPageStartup("Nourritures");
        GenShopPage.GenenerateShopPageStartup("Colorants");
        GenShopPage.GenenerateShopPageStartup("Minerais");
        GenShopPage.GenenerateShopPageStartup("Autres");
        GenShopPage.GenenerateShopPageStartup("Drops");
        GenShopPage.GenenerateShopPageStartup("Redstone");
        GenShopPage.GenenerateShopPageStartup("Spawneurs");
        GenAmoutShopGui.GenAmoutShopGuiStartup();
        GenMultiStacksBuyGui.GenMultiShopGuiStartup();
        ChunkCollectorManager.ReadFromFile();
        SellChestManager.ReadFromFile();
        SellChestManager.AutoSellForVeryChest();
        FarmHoeManager.addtolist();
        getServer().getPluginManager().registerEvents(new JoinLeave(), this);
        getServer().getPluginManager().registerEvents(new ScoreBoard(), this);
        getServer().getPluginManager().registerEvents(new Interact(), this);
        getServer().getPluginManager().registerEvents(new AntiExplo(), this);
        getServer().getPluginManager().registerEvents(new Farm2WinGui(), this);
        getServer().getPluginManager().registerEvents(new WineGui(), this);
        getServer().getPluginManager().registerEvents(new WarpGui(), this);
        getServer().getPluginManager().registerEvents(new FeatherFlyInteract(), this);
        getServer().getPluginManager().registerEvents(new SwitchWorld(), this);
        getServer().getPluginManager().registerEvents(new Tabulation(), this);
        getServer().getPluginManager().registerEvents(new AfkMineCaptchaGui(), this);
        getServer().getPluginManager().registerEvents(new AfkMineBreakCheck(), this);
        getServer().getPluginManager().registerEvents(new ChatReaction(), this);
        getServer().getPluginManager().registerEvents(new RedstoneCheck(), this);
        getServer().getPluginManager().registerEvents(new ChallengesGuis(), this);
        getServer().getPluginManager().registerEvents(new ChallengesBlockBreak(), this);
        getServer().getPluginManager().registerEvents(new AtoutGui(), this);
        getServer().getPluginManager().registerEvents(new BuyAtoutGui(), this);
        getServer().getPluginManager().registerEvents(new CratesManager(), this);
        getServer().getPluginManager().registerEvents(new HolosSetup(), this);
        getServer().getPluginManager().registerEvents(new MenuGui(), this);
        getServer().getPluginManager().registerEvents(new MainShopGui(), this);
        getServer().getPluginManager().registerEvents(new CateSelectGui(), this);
        getServer().getPluginManager().registerEvents(new AmountGuiManager(), this);
        getServer().getPluginManager().registerEvents(new MultiStacksShopGuiManager(), this);
        getServer().getPluginManager().registerEvents(new CountdownFly(), this);
        getServer().getPluginManager().registerEvents(new TchatManager(), this);
        getServer().getPluginManager().registerEvents(new ChunkCollector(), this);
        getServer().getPluginManager().registerEvents(new SellChest(), this);
        getServer().getPluginManager().registerEvents(new FarmHoeManager(), this);
        getServer().getPluginManager().registerEvents(new FarmHoeGui(), this);
        this.getCommand("spawn").setExecutor(new SpawnCmd());
        this.getCommand("build").setExecutor(new BuildCmd());
        this.getCommand("farm2win").setExecutor(new Farm2WinCmd());
        this.getCommand("bar").setExecutor(new BarCmd());
        this.getCommand("menu").setExecutor(new MenuCmd());
        this.getCommand("warp").setExecutor(new WarpCmd());
        this.getCommand("warps").setExecutor(new WarpsCmd());
        this.getCommand("featherfly").setExecutor(new FeatherFlyCmd());
        this.getCommand("dailyfly").setExecutor(new DailyFlyCmd());
        this.getCommand("itemleg").setExecutor(new ItemLegCmd());
        this.getCommand("fly").setExecutor(new FlyCmd());
        this.getCommand("feed").setExecutor(new FeedCmd());
        this.getCommand("hat").setExecutor(new HatCmd());
        this.getCommand("craft").setExecutor(new CraftCmd());
        this.getCommand("enchantement").setExecutor(new EnchantementCmd());
        this.getCommand("gm").setExecutor(new GmCmd());
        this.getCommand("gms").setExecutor(new GmLCmd());
        this.getCommand("gmc").setExecutor(new GmLCmd());
        this.getCommand("gmsp").setExecutor(new GmLCmd());
        this.getCommand("gma").setExecutor(new GmLCmd());
        this.getCommand("afkmine").setExecutor(new AntiAfkMineCmd());
        this.getCommand("checkfly").setExecutor(new CheckFlyCmd());
        this.getCommand("redstone").setExecutor(new RedstoneToggleCmd());
        this.getCommand("tpa").setExecutor(new TpaCmd());
        this.getCommand("tpacancel").setExecutor(new TpaCancelCmd());
        this.getCommand("tpyes").setExecutor(new TpYesCmd());
        this.getCommand("tpno").setExecutor(new TpNoCmd());
        this.getCommand("challenges").setExecutor(new ChallengesCmd());
        this.getCommand("atout").setExecutor(new AtoutCmd());
        this.getCommand("datafile").setExecutor(new DataCmd());
        this.getCommand("key").setExecutor(new KeyCmd());
        this.getCommand("money").setExecutor(new MoneyCmd());
        this.getCommand("pay").setExecutor(new PayCmd());
        this.getCommand("baltop").setExecutor(new BaltopCmd());
        this.getCommand("shop").setExecutor(new ShopGuiCmd());
        this.getCommand("sellall").setExecutor(new SellAllCmd());
        this.getCommand("chests").setExecutor(new ChestsCmd());
        this.getCommand("farmhoe").setExecutor(new FarmHoeCmd());
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(this, this);
        System.out.println("§aD§marrage du plugin r§ussi !");
        System.out.println("-----------------------------------------------------------------------------------------------------");
    }

    @Override
    public void onDisable() {
        FeatherFlyInteract.WriteFlyLeft();
        HolosSetup.RemoveBoxeHolo();
        CratesManager.RemoveBoxeHolo();
        //BossBar.RemoveBossBarForPlayers();
        HolosSetup.RemoveNpc();
        WineSpawn.DestroyPnj();
        vaultHook.unhook();
        System.out.println("-----------------------------------------------------------------------------------------------------");
        System.out.println("Plugin stopp§ !");
        System.out.println("-----------------------------------------------------------------------------------------------------");
    }

    public void setup() {
        dfile = new File(this.getDataFolder(), "Challenges.yml");
        cfile = new File(this.getDataFolder(), "Fly.yml");
        zfile = new File(this.getDataFolder(), "Eco.yml");
        blcfile = new File(this.getDataFolder(), "Block.yml");

        if (!dfile.exists()) {
            try {
                dfile.createNewFile();
            } catch (IOException e) {
                getLogger().info("§c§lErreur lors de la cr§ation de Challenges.yml");
            }
        }

        data = YamlConfiguration.loadConfiguration(dfile);

        if (!cfile.exists()) {
            try {
                cfile.createNewFile();
            } catch (IOException e) {
                getLogger().info("§c§lErreur lors de la cr§ation de Fly.yml");
            }
        }

        datac = YamlConfiguration.loadConfiguration(cfile);

        if (!zfile.exists()) {
            try {
                zfile.createNewFile();
            } catch (IOException e) {
                getLogger().info("§c§lErreur lors de la cr§ation de Eco.yml");
            }
        }

        dataz = YamlConfiguration.loadConfiguration(zfile);

        if (!blcfile.exists()) {
            try {
                blcfile.createNewFile();
            } catch (IOException e) {
                getLogger().info("§c§lErreur lors de la cr§ation de Block.yml");
            }
        }

        datablc = YamlConfiguration.loadConfiguration(blcfile);


    }

    public FileConfiguration getDatac() {
        return datac;
    }

    public FileConfiguration getData() {
        return data;
    }

    public FileConfiguration getDataz() {
        return dataz;
    }

    public FileConfiguration getDatablc() {
        return datablc;
    }


    public void reloadData() throws FileNotFoundException, IOException {
        try {
            data.load(dfile);
        } catch (InvalidConfigurationException e) {
            getLogger().info("§c§lErreur lors de la sauvegarde!");
            e.printStackTrace();
        }
        try {
            datac.load(cfile);
        } catch (InvalidConfigurationException e) {
            getLogger().info("§c§lErreur lors de la sauvegarde!");
            e.printStackTrace();
        }
        try {
            dataz.load(zfile);
        } catch (InvalidConfigurationException e) {
            getLogger().info("§c§lErreur lors de la sauvegarde!");
            e.printStackTrace();
        }
        try {
            datablc.load(blcfile);
        } catch (InvalidConfigurationException e) {
            getLogger().info("§c§lErreur lors de la sauvegarde!");
            e.printStackTrace();
        }
    }

    public void saveData() {
        try {
            data.save(dfile);
        } catch (IOException e) {
            getLogger().info("§c§lErreur lors de la sauvegarde!");
        }
        try {
            datac.save(cfile);
        } catch (IOException e) {
            getLogger().info("§c§lErreur lors de la sauvegarde!");
        }
        try {
            dataz.save(zfile);
        } catch (IOException e) {
            getLogger().info("§c§lErreur lors de la sauvegarde!");
        }
        try {
            datablc.save(blcfile);
        } catch (IOException e) {
            getLogger().info("§c§lErreur lors de la sauvegarde!");
        }
    }
}







