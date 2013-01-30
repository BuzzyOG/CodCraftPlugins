package com.CodCraft.sad;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.CodCraft.api.CCAPI;
import com.CodCraft.api.modules.Cac;
import com.CodCraft.api.modules.GameManager;
import com.CodCraft.api.modules.Teleport;
import com.CodCraft.api.modules.Weapons;
import com.CodCraft.sad.GM.Game;
import com.CodCraft.sad.GM.GameListener;
import com.CodCraft.sad.GM.SAD;
import com.CodCraft.sad.GM.SADTimer;
import com.CodCraft.sad.commands.AdminCommands;
import com.CodCraft.sad.commands.BuddyCommand;
import com.CodCraft.sad.commands.CacComamnd;
import com.CodCraft.sad.commands.EndRoundCommand;
import com.CodCraft.sad.commands.GuiCommand;
import com.CodCraft.sad.commands.SpecCommand;
import com.CodCraft.sad.commands.SwitchCommand;
import com.CodCraft.sad.commands.TeamsCommand;
import com.CodCraft.sad.commands.VoteCommand;
import com.CodCraft.sad.listener.BlockListener;
import com.CodCraft.sad.listener.PlayerListener;

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
		   api =  (CCAPI) apiplugin;
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
      api.getModuleForClass(GameManager.class).setAmmontofTeams(2);
      new SAD();
      new SADTimer(this, 20);
      Bukkit.getScheduler().runTaskLater(this, new SADTimer(this, 20), 20);
      api.getModuleForClass(GameManager.class).SetCurrentWorld("Nuketown");
      PluginManager pm = this.getServer().getPluginManager();
      pm.registerEvents(new PlayerListener(this), this);
      pm.registerEvents(new BlockListener(this), this);
      pm.registerEvents(new GameListener(this), this);
      game = new Game(this);
      game.Lobby();
      game.MainTimer();
      spawnLoad();
      api.getModuleForClass(Teleport.class).setLocations1(Teamone);
      api.getModuleForClass(Teleport.class).setLocations2(Teamtwo);
      api.getModuleForClass(Cac.class).usedefaultspawns();
      api.getModuleForClass(Weapons.class).LoadGuns();
      

   }

   public void onDisable() {
      //BlockManger.AllRespawnGlassblocks();
      game.Savedata();
   }

   public CCAPI getApi() {
	   
      return api;
   }

   public void setApi(com.CodCraft.api.CCAPI api) {
      this.api = api;
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
