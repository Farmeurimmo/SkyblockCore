package fr.farmeurimmo.premsi.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

import fr.farmeurimmo.premsi.WineLottery.WineGui;
import fr.farmeurimmo.premsi.WineLottery.WineSpawn;
import fr.farmeurimmo.premsi.atout.AtoutCmd;
import fr.farmeurimmo.premsi.atout.AtoutGui;
import fr.farmeurimmo.premsi.atout.BuyAtoutGui;
import fr.farmeurimmo.premsi.challenges.ChallengesBlockBreak;
import fr.farmeurimmo.premsi.challenges.ChallengesCmd;
import fr.farmeurimmo.premsi.challenges.ChallengesGuis;
import fr.farmeurimmo.premsi.challenges.ChallengesReset;
import fr.farmeurimmo.premsi.cmd.base.BarCmd;
import fr.farmeurimmo.premsi.cmd.base.CraftCmd;
import fr.farmeurimmo.premsi.cmd.base.EnchantementCmd;
import fr.farmeurimmo.premsi.cmd.base.Farm2WinCmd;
import fr.farmeurimmo.premsi.cmd.base.FeedCmd;
import fr.farmeurimmo.premsi.cmd.base.FlyCmd;
import fr.farmeurimmo.premsi.cmd.base.HatCmd;
import fr.farmeurimmo.premsi.cmd.base.MenuCmd;
import fr.farmeurimmo.premsi.cmd.base.SpawnCmd;
import fr.farmeurimmo.premsi.cmd.base.TpNoCmd;
import fr.farmeurimmo.premsi.cmd.base.TpYesCmd;
import fr.farmeurimmo.premsi.cmd.base.TpaCancelCmd;
import fr.farmeurimmo.premsi.cmd.base.TpaCmd;
import fr.farmeurimmo.premsi.cmd.base.WarpCmd;
import fr.farmeurimmo.premsi.cmd.base.WarpsCmd;
import fr.farmeurimmo.premsi.cmd.moderation.AntiAfkMineCmd;
import fr.farmeurimmo.premsi.cmd.moderation.BuildCmd;
import fr.farmeurimmo.premsi.cmd.moderation.CheckFlyCmd;
import fr.farmeurimmo.premsi.cmd.moderation.DataCmd;
import fr.farmeurimmo.premsi.cmd.moderation.GmCmd;
import fr.farmeurimmo.premsi.cmd.moderation.GmLCmd;
import fr.farmeurimmo.premsi.cmd.moderation.InvseeCmd;
import fr.farmeurimmo.premsi.cmd.moderation.RedstoneToggleCmd;
import fr.farmeurimmo.premsi.crates.CratesManager;
import fr.farmeurimmo.premsi.crates.KeyCmd;
import fr.farmeurimmo.premsi.evenement.ChatReaction;
import fr.farmeurimmo.premsi.events.AfkMineBreakCheck;
import fr.farmeurimmo.premsi.events.AntiExplo;
import fr.farmeurimmo.premsi.events.Interact;
import fr.farmeurimmo.premsi.events.JoinLeave;
import fr.farmeurimmo.premsi.events.RedstoneCheck;
import fr.farmeurimmo.premsi.events.SwitchWorld;
import fr.farmeurimmo.premsi.events.Tabulation;
import fr.farmeurimmo.premsi.featherfly.DailyFlyCmd;
import fr.farmeurimmo.premsi.featherfly.FeatherFlyCmd;
import fr.farmeurimmo.premsi.featherfly.FeatherFlyInteract;
import fr.farmeurimmo.premsi.gui.AfkMineCaptchaGui;
import fr.farmeurimmo.premsi.gui.Farm2WinGui;
import fr.farmeurimmo.premsi.gui.MenuGui;
import fr.farmeurimmo.premsi.gui.WarpGui;
import fr.farmeurimmo.premsi.holos.HolosSetup;
import fr.farmeurimmo.premsi.items.ItemLegCmd;
import fr.farmeurimmo.premsi.scoreboard.ScoreBoard;
import fr.farmeurimmo.premsi.utils.BossBar;
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
    
    
	@Override
	public void onEnable() {
		saveDefaultConfig();
		instance = this;
		instance1 = this;
		Bukkit.getPluginManager().isPluginEnabled("LuckPerms");
		Bukkit.getPluginManager().isPluginEnabled("Citizens");
		Bukkit.getPluginManager().isPluginEnabled("TheNewEconomy");
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
		if (Bukkit.getPluginManager().getPlugin("TheNewEconomy") != null) {
			System.out.println("Le plugin TNE a été trouvé !");
			System.out.println("-----------------------------------------------------------------------------------------------------");
			System.out.println("Initialisation de l'api TNE en cours...");
			System.out.println("API initialisée !");
			System.out.println("-----------------------------------------------------------------------------------------------------");
		} else {
			getLogger().warning("Le plugin TheNewEconomy est manquant.");
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
		System.out.println("Initialisation des class et des méthodes en cours...");
		setup();
		Main.spawncooldown.clear();
		BossBar.CreateBossBar();
		BuildCmd.Build.clear();
		WineSpawn.SpawnPnj(new Location(Bukkit.getServer().getWorld("world"), 17.5, 95.5, -140.5, 90, 0));
		HolosSetup.SpawnPnj2(new Location(Bukkit.getServer().getWorld("world"), -15.5, 97, -111.5, -90, 0),
				new Location(Bukkit.getServer().getWorld("world"), 1.5, 97, -99.5, 90, 0));
		HolosSetup.SpawnCrates();
		for(Player player : Bukkit.getOnlinePlayers()) {
			ScoreBoard.setScoreBoard(player);
		}
		ChatReaction.mots.addAll(Arrays.asList("PremsiServ","Skyblock","Ile","Farm2Win"));
	    ChatReaction.StartChatReaction();
	    ChallengesReset.CheckForReset();
	    CratesManager.SpawnCrates();
	    ScoreBoard.updateScoreBoard();
	    FeatherFlyInteract.ReadForTempFly();
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
		System.out.println("-----------------------------------------------------------------------------------------------------");
		System.out.println("Plugin stoppé !");
		System.out.println("-----------------------------------------------------------------------------------------------------");
	}
	public FileConfiguration data;
	public FileConfiguration datac;
    public File dfile;
    public File cfile;
   
    public void setup() {
        dfile = new File(this.getDataFolder(), "Challenges.yml");
        cfile = new File(this.getDataFolder(), "Fly.yml");
       
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
        
    }
   
    public FileConfiguration getDatac() {
        return datac;
    }
    
    public FileConfiguration getData() {
        return data;
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
    }
}







