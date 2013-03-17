package com.codcraft.infected;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
import com.CodCraft.api.modules.Broadcast;
import com.CodCraft.api.modules.GameManager;
import com.CodCraft.api.services.CCGamePlugin;

public class CodCraftInfected extends CCGamePlugin {
	
	public CCAPI api;
	public Map<String, ArrayList<Location>> teamonespawns = new HashMap<>();
	public Map<String, ArrayList<Location>> teamtwospawns = new HashMap<>();
	
	
	public Map<String, ArrayList<Location>> spawnpoints = new HashMap<>();
	public Map<String, InfectedModel> currentmap = new HashMap<>();
	public ArrayList<String> maps  = new ArrayList<>();
	private CodCraftInfected plugin;

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
	      getCommand("vote").setExecutor(new VoteCommand(this));
	      
	      	plugin = this;
	      

	      GameTimer();
	    }
	
	private void team2Load() {
	    File spawns = new File("./plugins/TDM/team2.yml");
	    YamlConfiguration config = YamlConfiguration.loadConfiguration(spawns);
	    for(String s : config.getConfigurationSection("maps").getKeys(false)) {
		      teamtwospawns.put(s, new ArrayList<Location>());
		      for(String location : config.getConfigurationSection("maps."+s).getKeys(false)) {
		    	  Location loc = new Location(Bukkit.getWorld("world"), Double.parseDouble(config.getString("maps."+s+"."+location+".x")),
		                       Double.parseDouble(config.getString("maps."+s+"."+location+".y")), Double.parseDouble(config	
		                             .getString("maps."+s+"."+location+".z")));
		    	  teamtwospawns.get(s).add(loc);
		      }
	      }
		
	}

	private void team1Load() {
	     File spawns = new File("./plugins/TDM/team1.yml");
	     YamlConfiguration config = YamlConfiguration.loadConfiguration(spawns);
		    for(String s : config.getConfigurationSection("maps").getKeys(false)) {
			      teamonespawns.put(s, new ArrayList<Location>());
			      for(String location : config.getConfigurationSection("maps."+s).getKeys(false)) {
			    	  Location loc = new Location(Bukkit.getWorld("world"), Double.parseDouble(config.getString("maps."+s+"."+location+".x")),
			                       Double.parseDouble(config.getString("maps."+s+"."+location+".y")), Double.parseDouble(config	
			                             .getString("maps."+s+"."+location+".z")));
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
		InfectedGame game = new InfectedGame(this);
	    game.setName(name);
	    manger.registerGame(game);
	    Bukkit.createWorld(new WorldCreator(game.getName()));
	}
	
	
	@Override
	public String getTag() {
		return "[Infected]";
	}
	
	public enum GameState {
		
		LOBBY,
		PREGAME,
		INGAME,
		
	}
	public void GameTimer() {
		
		Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
			
			@Override
			public void run() {
				GameManager gm = api.getModuleForClass(GameManager.class);
				for(Entry<String, InfectedModel> model : currentmap.entrySet()) {
					if(model.getValue().state == null) {
						model.getValue().state = com.codcraft.infected.CodCraftInfected.GameState.LOBBY;
						model.getValue().gametime = 30;
					}
					Random rnd = new Random();
					if(model.getValue().state == com.codcraft.infected.CodCraftInfected.GameState.LOBBY) {
						if(model.getValue().Map2 == null) {
							model.getValue().Map2 = maps.get(rnd.nextInt(maps.size()));
						}
						if(model.getValue().Map1 == null) {
							model.getValue().Map1 = maps.get(rnd.nextInt(maps.size()));
						}
						
						while(model.getValue().Map1.equalsIgnoreCase(model.getValue().Map2)) {
							model.getValue().Map1 = maps.get(rnd.nextInt(maps.size()));
						}
						
						if(model.getValue().gametime > 0) {
							model.getValue().gametime--;
						} else {
							List<TeamPlayer> teamPlayers = new ArrayList<>();
							for(TeamPlayer tp : gm.getGameWithId(model.getKey()).getTeams().get(0).getPlayers()) {
								teamPlayers.add(tp);
							}
							if(teamPlayers.size() == 0) {
								model.getValue().state = com.codcraft.infected.CodCraftInfected.GameState.LOBBY;
								model.getValue().gametime = 30;
							} else {
								if(model.getValue().map1 > model.getValue().map2) {
									model.getValue().map = model.getValue().Map1;
									
								} else {
									model.getValue().map = model.getValue().Map2;
								}
								model.getValue().map1 = 0;
								model.getValue().map2 = 0;
								model.getValue().voters.clear();
								model.getValue().state = com.codcraft.infected.CodCraftInfected.GameState.PREGAME;
								model.getValue().gametime = 5;
								Game<?> g = gm.getGameWithId(model.getKey());

								RespawnAll(Bukkit.getWorld(g.getName()), model.getValue().map, g);
							}
							


							
						}
					}
					if(model.getValue().state == com.codcraft.infected.CodCraftInfected.GameState.INGAME) {
						if(model.getValue().gametime > 0) {
							model.getValue().gametime--;
						} else {
							
							detectWin(gm.getGameWithId(model.getKey()));

							model.getValue().state = com.codcraft.infected.CodCraftInfected.GameState.LOBBY;
							model.getValue().gametime = 30;
							
						}
					}
					if(model.getValue().state == com.codcraft.infected.CodCraftInfected.GameState.PREGAME) {
						if(model.getValue().gametime > 0) {
							model.getValue().gametime--;
						} else {
							model.getValue().state = com.codcraft.infected.CodCraftInfected.GameState.INGAME;
							model.getValue().gametime = 600;
							ArrayList<TeamPlayer> teamplayers = new ArrayList<>();
							for(TeamPlayer tp : gm.getGameWithId(model.getKey()).getTeams().get(0).getPlayers()) {
								teamplayers.add(tp);
							}
							Random rnd1 = new Random();
							int player = rnd1.nextInt(teamplayers.size());
							TeamPlayer tp2 = teamplayers.get(player);
							api.getModuleForClass(Broadcast.class).BroadCastMessage(gm.getGameWithId(model.getKey()), tp2.getName() + " is now infected!");
							gm.getGameWithId(model.getKey()).getTeams().get(0).removePlayer(tp2);
							gm.getGameWithId(model.getKey()).getTeams().get(1).addPlayer(tp2);
							//model.getValue().infected = tp2;
						}
					}
					
					
				}
				for(Game<?> g1 : api.getModuleForClass(GameManager.class).getGamesForPlugin(plugin)) {
					for(Team t : g1.getTeams()) {
						for(TeamPlayer p1 : t.getPlayers()) {
							Player p = Bukkit.getPlayer(p1.getName());
							if(!p.isOnline()) {
								break;
							}
							p.setLevel(currentmap.get(g1.getId()).gametime);
						}
					}
				}
				
				
			}

			private void detectWin(Game<?> g) {
				Team t = g.getTeams().get(0);
				Team t2 = null;
				if(t.getPlayers().size() == 0) {
					t2 = g.getTeams().get(1);
				} else {
					t2 = t;
				}
				GameWinEvent event = new GameWinEvent(t2.getName()+" has won!", t2, g);
				Bukkit.getPluginManager().callEvent(event);
				Bukkit.broadcastMessage(event.getWinMessage());
				
			}
		}, 1, 20);
	}
	
	public void RespawnAll(World world, String map, Game<?> g) {
		
		int i = 0;
		for(TeamPlayer tp : g.getTeams().get(0).getPlayers()) {
			Player p = Bukkit.getPlayer(tp.getName());
			Location loc = teamonespawns.get(map).get(i);
			p.teleport(new Location(world, loc.getX(), loc.getY(), loc.getZ()));
			i++;
		}
		i = 0;
		for(TeamPlayer tp : g.getTeams().get(1).getPlayers()) {
			Player p = Bukkit.getPlayer(tp.getName());
			Location loc = teamtwospawns.get(map).get(i);
			p.teleport(new Location(world, loc.getX(), loc.getY(), loc.getZ()));
			i++;
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
	        		getLogger().info("null");
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
	      int i = rnd.nextInt(Aloowed.size());
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

		      if(x1 >= (x + range) || z1 >= (z + range) || x1 <= (x - range) || z1 <= (z - range)) {
		         return false;
		      }
		      return true;
		   }

}
