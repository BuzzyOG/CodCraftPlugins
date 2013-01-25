package com.CodCraft.ctf;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.CodCraft.api.CCAPI;
import com.CodCraft.api.model.location.LocationGroup;
import com.CodCraft.api.model.location.LocationMorph;
import com.CodCraft.api.modules.BlockManager;
import com.CodCraft.api.modules.Cac;
import com.CodCraft.api.modules.GameManager;
import com.CodCraft.api.modules.Teleport;
import com.CodCraft.api.modules.Weapons;
import com.CodCraft.ctf.GM.CTF;
import com.CodCraft.ctf.GM.CTFTimer;
import com.CodCraft.ctf.GM.Game;
import com.CodCraft.ctf.GM.GameListener;
import com.CodCraft.ctf.commands.AdminCommands;
import com.CodCraft.ctf.commands.BuddyCommand;
import com.CodCraft.ctf.commands.CacComamnd;
import com.CodCraft.ctf.commands.EndRoundCommand;
import com.CodCraft.ctf.commands.GuiCommand;
import com.CodCraft.ctf.commands.SpecCommand;
import com.CodCraft.ctf.commands.SwitchCommand;
import com.CodCraft.ctf.commands.TeamsCommand;
import com.CodCraft.ctf.commands.VoteCommand;
import com.CodCraft.ctf.listener.BlockListener;
import com.CodCraft.ctf.listener.PlayerListener;

public class CodCraft extends JavaPlugin {
   public static HashMap<Player, Integer> PlayerKills = new HashMap<Player, Integer>();
   public static HashMap<Player, Integer> PlayerDeaths = new HashMap<Player, Integer>();
   public static HashMap<Player, Integer> PlayerWins = new HashMap<Player, Integer>();
   public static HashMap<Player, Integer> PlayerLosses = new HashMap<Player, Integer>();
   public static HashMap<String, ArrayList<Location>> Teamone = new HashMap<String, ArrayList<Location>>();
   public static HashMap<String, ArrayList<Location>> Teamtwo = new HashMap<String, ArrayList<Location>>();
   private CCAPI api;
   private Game game;

   public void onEnable() {
	   final Plugin apiplugin = this.getServer().getPluginManager().getPlugin("CodCraftAPI");
	   if(apiplugin != null) {
		   api = (CCAPI) apiplugin;
	   } else {
		   System.out.println("Did Not Get Plugin");
	   }
      //System.out.println(api.checkversion(this.getDescription().getVersion()));// Bukkit.getPluginManager().disablePlugin(plugin);
      getCommand("CaC").setExecutor(new CacComamnd(this));
      getCommand("EndRound").setExecutor(new EndRoundCommand(this));
      getCommand("Spec").setExecutor(new SpecCommand(this));
      getCommand("vote").setExecutor(new VoteCommand(this));
      getCommand("a").setExecutor(new AdminCommands(this));
      getCommand("team").setExecutor(new TeamsCommand(this));
      getCommand("Gui").setExecutor(new GuiCommand(this));
      getCommand("buddy").setExecutor(new BuddyCommand(this));
      getCommand("switch").setExecutor(new SwitchCommand(this));
      new MapLoader();
      GameManager manger = api.getModuleForClass(GameManager.class);
      manger.SetCurrentWorld("Nuketown");
      PluginManager pm = this.getServer().getPluginManager();
      pm.registerEvents(new PlayerListener(this), this);
      pm.registerEvents(new BlockListener(this), this);
      pm.registerEvents(new GameListener(this), this);
      game = new Game(this);
      game.Lobby();
      game.MainTimer();
      spawnLoad();
      Teleport telport = api.getModuleForClass(Teleport.class);
      Cac CaC = api.getModuleForClass(Cac.class);
      Weapons weap = api.getModuleForClass(Weapons.class);
      telport.setLocations1(Teamone);
      telport.setLocations2(Teamtwo);
      CaC.usedefaultspawns();
      weap.LoadGuns();
      manger.setAmmontofTeams(2);
      new CTF();

      Bukkit.getScheduler().runTaskLater(this, new CTFTimer(this, 10L), 10L);
   }

   public void onDisable() {
      CTF.respawnblocks();
      
      game.Savedata();
   }

   public com.CodCraft.api.CCAPI getApi() {
      return api;
   }

   public void setApi(com.CodCraft.api.CCAPI api) {
      this.api = api;
   }
   
   private static final String GLASS = "GLASS";
   
   public void blah() {
	   	LocationGroup group = new LocationGroup();
	   	//String id = group.getId(); //Store this somewhere as a reference so you have a way to get back to it.
	   	//For all locations that you want to manage, add it to
	   	group.getLocations().add(new LocationMorph(null)); //Don't use null.
	   
	   	LocationMorph loc1 = new LocationMorph(null);
	   	loc1.addMorph(GLASS, Material.THIN_GLASS);
	   
	   	group.setReset(true);
	   
//	  	 Morph(Material);
//	  	 morph.setBreakable(true);
	   
	   	LocationMorph loc2 = new LocationMorph(null);
	   	loc2.addMorph("bl;ah", Material.ANVIL);
	   
	   	group.getLocations().add(loc1);
	   	group.getLocations().add(loc2);
	   
	   	BlockManager manager = api.getModuleForClass(BlockManager.class);
	   	manager.addLocationGroup(group);
	   
	   
	   	manager.morphLocationGroup(group.getId(), GLASS);
	   }

   /*
    * public boolean checkversion(String pversion) { int v
    * =com.CodCraft.api.CodCraft.Version; if(v ==
    * Integer.parseInt(plugin.getDescription().getVersion())) {
    * System.out.println("Plugin can run with this API Version. Enabling!");
    * return true; } else if(v <
    * Integer.parseInt(plugin.getDescription().getVersion())) {
    * System.out.println
    * ("Plugin is unable to run corectly with the API Version. Disabling");
    * return false; } else if (v >
    * Integer.parseInt(plugin.getDescription().getVersion())) {
    * System.out.println(
    * "API is a higher version then plugin. Although plugin will enable this is not recommended! Enabling"
    * ); return true; } else {
    * System.out.println("Error in getting Versions Disabling!"); return false;
    * } }
    */
   @SuppressWarnings("unchecked")
   private void spawnLoad() {
      File spawns = new File("./plugins/CodCraftTDM/spawns.yml");
      YamlConfiguration config = YamlConfiguration.loadConfiguration(spawns);
      for(String s : config.getConfigurationSection("Teams").getKeys(false)) {
         if(s.equalsIgnoreCase("Team1")) {
            for(String Worlds : config.getConfigurationSection("Teams.Team1").getKeys(false)) {
               World world = Bukkit.getWorld(Worlds);
               ArrayList<Location> s3 = new ArrayList<Location>();
               for(String spawn : config.getConfigurationSection("Teams.Team1." + world.getName()).getKeys(false)) {
                  Location loc = new Location(world, Double.parseDouble(config.getString("Teams.Team1." + world.getName() + "." + spawn + ".x")),
                        Double.parseDouble(config.getString("Teams.Team1." + world.getName() + "." + spawn + ".y")), Double.parseDouble(config
                              .getString("Teams.Team1." + world.getName() + "." + spawn + ".z")));
                  s3.add(loc);
                  getLogger().info(
                        "  " + spawn + ": X=" + config.getString(world.getName() + "." + spawn + ".x") + " Y="
                              + config.getString(world.getName() + "." + spawn + ".y") + " Z="
                              + config.getString(world.getName() + "." + spawn + ".z"));
                  Teamone.put(world.getName(), (ArrayList<Location>) s3.clone());
               }
            }
         } else if(s.equalsIgnoreCase("Team2")) {
            for(String Worlds : config.getConfigurationSection("Teams.Team2").getKeys(false)) {
               World world = Bukkit.getWorld(Worlds);
               ArrayList<Location> s3 = new ArrayList<Location>();
               for(String spawn : config.getConfigurationSection("Teams.Team2." + world.getName()).getKeys(false)) {

                  Location loc = new Location(world, Double.parseDouble(config.getString("Teams.Team2." + world.getName() + "." + spawn + ".x")),
                        Double.parseDouble(config.getString("Teams.Team2." + world.getName() + "." + spawn + ".y")), Double.parseDouble(config
                              .getString("Teams.Team2." + world.getName() + "." + spawn + ".z")));
                  s3.add(loc);
                  getLogger().info(
                        "  " + spawn + ": X=" + config.getString(world.getName() + "." + spawn + ".x") + " Y="
                              + config.getString(world.getName() + "." + spawn + ".y") + " Z="
                              + config.getString(world.getName() + "." + spawn + ".z"));
                  Teamtwo.put(world.getName(), (ArrayList<Location>) s3.clone());
               }
            }
         }
      }
   }

   public Game getGame() {
      return game;
   }

   public void setGame(Game game) {
      this.game = game;
   }

}
