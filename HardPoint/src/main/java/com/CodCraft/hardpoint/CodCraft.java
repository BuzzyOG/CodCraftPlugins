package com.CodCraft.hardpoint;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.CodCraft.api.modules.Cac;
import com.CodCraft.api.modules.GameManager;
import com.CodCraft.api.modules.Teleport;
import com.CodCraft.api.modules.Weapons;
import com.CodCraft.hardpoint.GM.Game;
import com.CodCraft.hardpoint.GM.GameListener;
import com.CodCraft.hardpoint.GM.Hardpoint;
import com.CodCraft.hardpoint.GM.HardpointTimer;
import com.CodCraft.hardpoint.commands.AdminCommands;
import com.CodCraft.hardpoint.commands.BuddyCommand;
import com.CodCraft.hardpoint.commands.CacComamnd;
import com.CodCraft.hardpoint.commands.EndRoundCommand;
import com.CodCraft.hardpoint.commands.GuiCommand;
import com.CodCraft.hardpoint.commands.SpecCommand;
import com.CodCraft.hardpoint.commands.SwitchCommand;
import com.CodCraft.hardpoint.commands.TeamsCommand;
import com.CodCraft.hardpoint.commands.VoteCommand;
import com.CodCraft.hardpoint.listener.BlockListener;
import com.CodCraft.hardpoint.listener.PlayerListener;

public class CodCraft extends JavaPlugin {

   private Game game;
   private Hardpoint hardpoint;
   public com.CodCraft.api.CCAPI api;

   private Map<String, ArrayList<Location>> teamOne = new HashMap<>();
   private Map<String, ArrayList<Location>> teamTwo = new HashMap<>();

   public void onEnable() {
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
      api.getModuleForClass(GameManager.class).SetCurrentWorld("Nuketown");
      game = new Game(this);
      hardpoint = new Hardpoint();
      PluginManager pm = this.getServer().getPluginManager();
      pm.registerEvents(new PlayerListener(this), this);
      pm.registerEvents(new BlockListener(this), this);
      pm.registerEvents(new GameListener(this), this);
      game.Lobby();
      game.MainTimer();
      spawnLoad();
      api.getModuleForClass(Teleport.class).setLocations1(teamOne);
      api.getModuleForClass(Teleport.class).setLocations1(teamTwo);
      api.getModuleForClass(Cac.class).usedefaultspawns();
      api.getModuleForClass(Weapons.class).LoadGuns();
      api.getModuleForClass(GameManager.class).setAmmontofTeams(2);
      Bukkit.getScheduler().runTaskLater(this, new HardpointTimer(this), 10L);
   }

   public void onDisable() {
      //BlockManger.AllRespawnGlassblocks();
      game.Savedata();
   }

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
                  teamOne.put(world.getName(), (ArrayList<Location>) s3.clone());
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
                  teamTwo.put(world.getName(), (ArrayList<Location>) s3.clone());
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

   public Hardpoint getHardpoint() {
      return hardpoint;
   }

   public void setHardpoint(Hardpoint hardpoint) {
      this.hardpoint = hardpoint;
   }

public com.CodCraft.api.CCAPI getApi() {
	return api;
}

public void setApi(com.CodCraft.api.CCAPI api) {
	this.api = api;
}

}
