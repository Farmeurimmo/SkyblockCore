package fr.farmeurimmo.verymc.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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

import com.iridium.iridiumskyblock.api.IridiumSkyblockAPI;

import fr.farmeurimmo.verymc.WineLottery.WineGui;
import fr.farmeurimmo.verymc.WineLottery.WineSpawn;
import fr.farmeurimmo.verymc.atout.AtoutCmd;
import fr.farmeurimmo.verymc.atout.AtoutGui;
import fr.farmeurimmo.verymc.atout.BuyAtoutGui;
import fr.farmeurimmo.verymc.challenges.ChallengesBlockBreak;
import fr.farmeurimmo.verymc.challenges.ChallengesCmd;
import fr.farmeurimmo.verymc.challenges.ChallengesGuis;
import fr.farmeurimmo.verymc.challenges.ChallengesReset;
import fr.farmeurimmo.verymc.cmd.base.BaltopCmd;
import fr.farmeurimmo.verymc.cmd.base.BarCmd;
import fr.farmeurimmo.verymc.cmd.base.CraftCmd;
import fr.farmeurimmo.verymc.cmd.base.EnchantementCmd;
import fr.farmeurimmo.verymc.cmd.base.Farm2WinCmd;
import fr.farmeurimmo.verymc.cmd.base.FeedCmd;
import fr.farmeurimmo.verymc.cmd.base.FlyCmd;
import fr.farmeurimmo.verymc.cmd.base.HatCmd;
import fr.farmeurimmo.verymc.cmd.base.MenuCmd;
import fr.farmeurimmo.verymc.cmd.base.MoneyCmd;
import fr.farmeurimmo.verymc.cmd.base.PayCmd;
import fr.farmeurimmo.verymc.cmd.base.SpawnCmd;
import fr.farmeurimmo.verymc.cmd.base.TpNoCmd;
import fr.farmeurimmo.verymc.cmd.base.TpYesCmd;
import fr.farmeurimmo.verymc.cmd.base.TpaCancelCmd;
import fr.farmeurimmo.verymc.cmd.base.TpaCmd;
import fr.farmeurimmo.verymc.cmd.base.WarpCmd;
import fr.farmeurimmo.verymc.cmd.base.WarpsCmd;
import fr.farmeurimmo.verymc.cmd.moderation.AntiAfkMineCmd;
import fr.farmeurimmo.verymc.cmd.moderation.BuildCmd;
import fr.farmeurimmo.verymc.cmd.moderation.CheckFlyCmd;
import fr.farmeurimmo.verymc.cmd.moderation.DataCmd;
import fr.farmeurimmo.verymc.cmd.moderation.GmCmd;
import fr.farmeurimmo.verymc.cmd.moderation.GmLCmd;
import fr.farmeurimmo.verymc.cmd.moderation.InvseeCmd;
import fr.farmeurimmo.verymc.cmd.moderation.RedstoneToggleCmd;
import fr.farmeurimmo.verymc.crates.CratesManager;
import fr.farmeurimmo.verymc.crates.KeyCmd;
import fr.farmeurimmo.verymc.eco.EcoAccountsManager;
import fr.farmeurimmo.verymc.eco.EconomyImplementer;
import fr.farmeurimmo.verymc.eco.VaultHook;
import fr.farmeurimmo.verymc.evenement.ChatReaction;
import fr.farmeurimmo.verymc.events.AfkMineBreakCheck;
import fr.farmeurimmo.verymc.events.AntiExplo;
import fr.farmeurimmo.verymc.events.Interact;
import fr.farmeurimmo.verymc.events.JoinLeave;
import fr.farmeurimmo.verymc.events.RedstoneCheck;
import fr.farmeurimmo.verymc.events.SwitchWorld;
import fr.farmeurimmo.verymc.events.Tabulation;
import fr.farmeurimmo.verymc.events.TchatManager;
import fr.farmeurimmo.verymc.featherfly.CountdownFly;
import fr.farmeurimmo.verymc.featherfly.DailyFlyCmd;
import fr.farmeurimmo.verymc.featherfly.FeatherFlyCmd;
import fr.farmeurimmo.verymc.featherfly.FeatherFlyInteract;
import fr.farmeurimmo.verymc.gui.AfkMineCaptchaGui;
import fr.farmeurimmo.verymc.gui.Farm2WinGui;
import fr.farmeurimmo.verymc.gui.MenuGui;
import fr.farmeurimmo.verymc.gui.WarpGui;
import fr.farmeurimmo.verymc.holos.HolosSetup;
import fr.farmeurimmo.verymc.items.ItemLegCmd;
import fr.farmeurimmo.verymc.scoreboard.ScoreBoard;
import fr.farmeurimmo.verymc.shopgui.AmountGuiManager;
import fr.farmeurimmo.verymc.shopgui.CateSelectGui;
import fr.farmeurimmo.verymc.shopgui.BuyShopItem;
import fr.farmeurimmo.verymc.shopgui.GenAmoutShopGui;
import fr.farmeurimmo.verymc.shopgui.GenMultiStacksBuyGui;
import fr.farmeurimmo.verymc.shopgui.GenShopPage;
import fr.farmeurimmo.verymc.shopgui.MainShopGui;
import fr.farmeurimmo.verymc.shopgui.MultiStacksShopGuiManager;
import fr.farmeurimmo.verymc.shopgui.SellAllCmd;
import fr.farmeurimmo.verymc.shopgui.ShopGuiCmd;
import fr.farmeurimmo.verymc.utils.BossBar;
import net.luckperms.api.LuckPerms;

public class Main extends JavaPlugin implements Listener {
	
	public static ArrayList<Player> pending = new ArrayList<Player>();
	public static ArrayList<Player> haverequest = new ArrayList<Player>();
	
	static LuckPerms api;
    private static Main instance;
    
    public static Main instance1;
    
    public static Main getInstance() {
        return instance;
    }
    
   private static HashMap<String, String> tpatarget = new HashMap<>();
	
	public void setTarget(String uuid, String aaa) {
		if (aaa == null)
			tpatarget.remove(uuid);
		else
			tpatarget.put(uuid, aaa);
	}
    public String getTarget(String player) {
    	if(tpatarget.containsKey(player))
    		return tpatarget.get(player);
    	else
    		return null;
    }
    public void ClearPlayerAndTarget(String player) {
    	if(tpatarget.containsKey(player))
    	tpatarget.remove(player);
    }
	
   private static HashMap<String, Integer> spawncooldown = new HashMap<>();
	
	public void setCooldown(String uuid, Integer time) {
		if (time == null)
			spawncooldown.remove(uuid);
		else
			spawncooldown.put(uuid, time);
	}
    public int getCooldown(String player) {
        return (spawncooldown.get(player) == null ? 0 : spawncooldown.get(player));
    }
    
    public EconomyImplementer economyImplementer;
    private VaultHook vaultHook;
    
	@Override
	public void onEnable() {
		saveDefaultConfig();
		instance = this;
		instance1 = this;
		Bukkit.getPluginManager().isPluginEnabled("LuckPerms");
		Bukkit.getPluginManager().isPluginEnabled("Citizens");
		RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
		if (Bukkit.getPluginManager().getPlugin("LuckPerms") != null) {
			System.out.println("Le plugin LuckPerms a été trouvé !");
		} else {
			getLogger().warning("Le plugin LuckPerms est manquant.");
			Bukkit.getPluginManager().disablePlugin(this);
		}
		System.out.println("-----------------------------------------------------------------------------------------------------");
		System.out.println("Initialisation de l'api LuckPerms en cours...");
		if (provider != null) {
			api = provider.getProvider();
			System.out.println("API initialisée !");
			System.out.println("-----------------------------------------------------------------------------------------------------");
		}
		if (Bukkit.getPluginManager().getPlugin("Citizens") != null) {
			System.out.println("Le plugin Citizens a été trouvé !");
			System.out.println("-----------------------------------------------------------------------------------------------------");
			System.out.println("Initialisation de l'api Citizens en cours...");
			System.out.println("API initialisée !");
			System.out.println("-----------------------------------------------------------------------------------------------------");
		} else {
			getLogger().warning("Le plugin Citizens est manquant.");
			Bukkit.getPluginManager().disablePlugin(this);
		}
		if (Bukkit.getPluginManager().getPlugin("HolographicDisplays") != null) {
			System.out.println("Le plugin HolographicDisplays a été trouvé !");
			System.out.println("-----------------------------------------------------------------------------------------------------");
			System.out.println("Initialisation de l'api HolographicDisplays en cours...");
			System.out.println("API initialisée !");
			System.out.println("-----------------------------------------------------------------------------------------------------");
		} else {
			getLogger().warning("Le plugin HolographicDisplays est manquant.");
			Bukkit.getPluginManager().disablePlugin(this);
		}
		if(IridiumSkyblockAPI.getInstance() == null) {
			Bukkit.getPluginManager().disablePlugin(this);
		}
		System.out.println("Initialisation des classes et des méthodes en cours...");
		setup();
		economyImplementer = new EconomyImplementer();
        vaultHook = new VaultHook();
        vaultHook.hook();
		EcoAccountsManager.UpdateHash();
		Main.spawncooldown.clear();
		BossBar.CreateBossBar();
		BuildCmd.Build.clear();
		WineSpawn.SpawnPnj(new Location(Bukkit.getServer().getWorld("world"), -184.5, 70.5, -77.5, -90, 0));
		HolosSetup.SpawnPnj2(new Location(Bukkit.getServer().getWorld("world"), -155.5, 71, -62.5, 90, 0),
				new Location(Bukkit.getServer().getWorld("world"), -172.5, 71, -64.5, 90, 0));
		HolosSetup.SpawnCrates();
		for(Player player : Bukkit.getOnlinePlayers()) {
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
	    GenAmoutShopGui.GenAmoutShopGuiStartup();
	    GenMultiStacksBuyGui.GenMultiShopGuiStartup();
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
		this.getCommand("invsee").setExecutor(new InvseeCmd());
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
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this, this);
		System.out.println("§aDémarrage du plugin réussi !");
		System.out.println("-----------------------------------------------------------------------------------------------------");
	}
	@Override
	public void onDisable() {
		FeatherFlyInteract.WriteFlyLeft();
		HolosSetup.RemoveBoxeHolo();
		CratesManager.RemoveBoxeHolo();
		BossBar.RemoveBossBarForPlayers();
		HolosSetup.RemoveNpc();
		WineSpawn.DestroyPnj();
		vaultHook.unhook();
		System.out.println("-----------------------------------------------------------------------------------------------------");
		System.out.println("Plugin stoppé !");
		System.out.println("-----------------------------------------------------------------------------------------------------");
	}
	
	public FileConfiguration data;
	public FileConfiguration datac;
	public FileConfiguration dataz;
    public File dfile;
    public File cfile;
    public File zfile;
   
    public void setup() {
        dfile = new File(this.getDataFolder(), "Challenges.yml");
        cfile = new File(this.getDataFolder(), "Fly.yml");
        zfile = new File(this.getDataFolder(), "Eco.yml");
       
        if(!dfile.exists()) {
            try {
                dfile.createNewFile();
            }
            catch(IOException e) {
                getLogger().info("§c§lErreur lors de la création de Challenges.yml");
            }
        }
       
        data = YamlConfiguration.loadConfiguration(dfile);
        
        if(!cfile.exists()) {
            try {
                cfile.createNewFile();
            }
            catch(IOException e) {
                getLogger().info("§c§lErreur lors de la création de Fly.yml");
            }
        }
        
        datac = YamlConfiguration.loadConfiguration(cfile);
        
        if(!zfile.exists()) {
            try {
                zfile.createNewFile();
            }
            catch(IOException e) {
                getLogger().info("§c§lErreur lors de la création de Eco.yml");
            }
        }
        
        dataz = YamlConfiguration.loadConfiguration(zfile);
        
        
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
    }
   
    public void saveData() {
        try {
            data.save(dfile);
        }
        catch(IOException e) {
            getLogger().info("§c§lErreur lors de la sauvegarde!");
        }
        try {
            datac.save(cfile);
        }
        catch(IOException e) {
            getLogger().info("§c§lErreur lors de la sauvegarde!");
        }
        try {
            dataz.save(zfile);
        }
        catch(IOException e) {
            getLogger().info("§c§lErreur lors de la sauvegarde!");
        }
    }
}







