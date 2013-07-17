package com.codcraft.tdm;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.CodCraft.api.CCAPI;
import com.CodCraft.api.event.GameWinEvent;
import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.Team;
import com.CodCraft.api.model.TeamPlayer;
import com.CodCraft.api.modules.GameManager;
import com.CodCraft.api.services.CCGamePlugin;

public class CodCraftTDM extends CCGamePlugin {
	
	public CCAPI api;
	public Map<String, ArrayList<Location>> teamonespawns = new HashMap<>();
	public Map<String, ArrayList<Location>> teamtwospawns = new HashMap<>();
	public Map<String, ArrayList<Location>> spawnpoints = new HashMap<>();
	public ArrayList<String> maps  = new ArrayList<>();


	public void onEnable() {
	      final Plugin api = this.getServer().getPluginManager().getPlugin("CodCraftAPI");
	      if(api != null || !(api instanceof CCAPI)) {
	         this.api = (CCAPI) api;
	      } else {
	         // Disable if we cannot get the api.
	         getLogger().warning("Could not find API. Disabling...");
	         getServer().getPluginManager().disablePlugin(this);
	         return;
	      }
	      spawnLoad();
	      team1Load();
	      team2Load();
	      Bukkit.getPluginManager().registerEvents(new GameListener(this), this);  
	      alwaysDay();
	    }
	
	   private void alwaysDay() {
		   final CodCraftTDM ffa = this;
		Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
			
			@Override
			public void run() {
				List<Game<?>> games = api.getModuleForClass(GameManager.class).getGamesForPlugin(ffa);
				for(Game<?> g : games) {
					World world = Bukkit.getWorld(g.getName());
					if(world != null) {
						world.setTime(6062);
					}
				}
			}
		}, 0, 200);
		
	}
	
	private void team2Load() {
	    File spawns = new File("./plugins/TDM/config.yml");
	    YamlConfiguration config = YamlConfiguration.loadConfiguration(spawns);
	    for(String s : config.getConfigurationSection("Team2.maps").getKeys(false)) {
		      teamtwospawns.put(s, new ArrayList<Location>());
		      for(String location : config.getConfigurationSection("Team2.maps."+s).getKeys(false)) {
		    	  Location loc = new Location(Bukkit.getWorld("world"), Double.parseDouble(config.getString("Team2.maps."+s+"."+location+".x")),
		                       Double.parseDouble(config.getString("Team2.maps."+s+"."+location+".y")), Double.parseDouble(config	
		                             .getString("Team2.maps."+s+"."+location+".z")));
		    	  teamtwospawns.get(s).add(loc);
		      }
	      }
	}

	private void team1Load() {
	     File spawns = new File("./plugins/TDM/config.yml");
	     YamlConfiguration config = YamlConfiguration.loadConfiguration(spawns);
		    for(String s : config.getConfigurationSection("Team1.maps").getKeys(false)) {
			      teamonespawns.put(s, new ArrayList<Location>());
			      for(String location : config.getConfigurationSection("Team1.maps."+s).getKeys(false)) {
			    	  Location loc = new Location(Bukkit.getWorld("world"), Double.parseDouble(config.getString("Team1.maps."+s+"."+location+".x")),
			                       Double.parseDouble(config.getString("Team1.maps."+s+"."+location+".y")), Double.parseDouble(config	
			                             .getString("Team1.maps."+s+"."+location+".z")));
			    	  teamonespawns.get(s).add(loc);
			      }
		      }
	}

	private void spawnLoad() {
		     File spawns = new File("./plugins/TDM/config.yml");
		     YamlConfiguration config = YamlConfiguration.loadConfiguration(spawns);
		     for(String s : config.getConfigurationSection("maps").getKeys(false)) {
		      spawnpoints.put(s, new ArrayList<Location>());
		      maps.add(s);
		      for(String location : config.getConfigurationSection("maps."+s).getKeys(false)) {
		    	  Location loc = new Location(Bukkit.getWorld("world"), Double.parseDouble(config.getString("maps."+s+"."+location+".x")),
		                       Double.parseDouble(config.getString("maps."+s+"."+location+".y")), Double.parseDouble(config	
		                             .getString("maps."+s+"."+location+".z")));
		    	  spawnpoints.get(s).add(loc);
		      }
	      }
	   }

	public void onDisable() {
		
	}
	
	
	
	@Override
	public void makegame(String name) {
		GameManager manger = this.api.getModuleForClass(GameManager.class);
		TDMGame game = new TDMGame(this);
	    game.setName(name);
	    manger.registerGame(game);
	    Bukkit.createWorld(new WorldCreator(game.getName()));

	}
	
	
	@Override
	public String getTag() {
		return "[TDM]";
	}
	
	public void detectWin(Game<?> g) {
		Team team = null;
		int score = 0;
		for(Team t : g.getTeams()) {
			if(t.getScore() >= score) {
				team = t;
				score = t.getScore();
			}
		}
		
		GameWinEvent event = new GameWinEvent(team.getName()+" has won!", team, g);
		Bukkit.getPluginManager().callEvent(event);
		Bukkit.broadcastMessage(event.getWinMessage());
		//TODO telport
		
	}
	
	
	public void RespawnAll(String map, Game<?> g) {
		for(Team t : g.getTeams()) {
			TDMTeam team = (TDMTeam) t;
			List<TeamPlayer> players = new ArrayList<>(team.getPlayers());
			for(int i = 0; i < players.size(); i++) {
				TeamPlayer tp = players.get(i);
				Location spawn = team.getLocations(map).get(i);
				if(tp != null) {
					if(spawn != null) {
					Player p = Bukkit.getPlayer(tp.getName());
						if(p != null) {
							p.teleport(spawn);
						}
					}
				}
			}
		}

		
	}
	
	public Location Respawn(Player p,	 World world, String map, Game<?> g) {
	      List<Location> locationlist = spawnpoints.get(map);
	      List<Location> Aloowed = new ArrayList<Location>();
	      int b1 = 0;
	      int b2 = 0;
	      boolean b = false;
	      for(Location loc : locationlist) {
	    	 Team team1 = g.findTeamWithPlayer(p); 
	         for(Player p1 : getNearbyEntities(loc, 5)) {
	        	if(p == p1) {
	        		break;
	        	}
	        	Team team2 = g.findTeamWithPlayer(p1); 
	        	if(team2 == null) {
	        		break;
	        	}
	        	if(team1.getId().equalsIgnoreCase(team2.getId())) {
	        		b1++;
	        	} else {
	        		b2++;
	        	}

	         }
	         if(b1 >= 1) {
	            b = true;
	         }
	         if(b2 >= 1) {
	            b = false;
	         } else {
	            b = true;
	         }
	         if(b) {
	            Aloowed.add(loc);
	         }
	      }
	      if(Aloowed.size() == 0) {
		      Location loc = locationlist.get(0);
		      
		      return new Location(Bukkit.getWorld(g.getName()), loc.getX(), loc.getY(), loc.getZ());
	      }
	      Random rnd = new Random();
	      int i = rnd.nextInt(Aloowed.size() - 1);
	      Location loc = Aloowed.get(i);
	      
	      return new Location(Bukkit.getWorld(g.getName()), loc.getX(), loc.getY(), loc.getZ());

	   }
	   public List<Player> getNearbyEntities(Location where, int range) {
		      List<Player> found = new ArrayList<Player>();
		      for(Player p : where.getWorld().getPlayers()) {
		         if(isInBorder(where, p.getLocation(), range)) {
		            found.add(p);
		       }
		  }
		  
		  return found;
	   }

	public boolean isInBorder(Location center, Location notCenter, int range) {
		int x = center.getBlockX(), z = center.getBlockZ();
		int x1 = notCenter.getBlockX(), z1 = notCenter.getBlockZ();

		if (x1 >= (x + range) || z1 >= (z + range) || x1 <= (x - range)
				|| z1 <= (z - range)) {
			return false;
		}
		return true;
	}

}
