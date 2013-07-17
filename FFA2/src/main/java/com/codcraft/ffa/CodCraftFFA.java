package com.codcraft.ffa;

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
import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.Team;
import com.CodCraft.api.modules.GameManager;
import com.CodCraft.api.services.CCGamePlugin;

public class CodCraftFFA extends CCGamePlugin {
	
	public CCAPI api;
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
	      Bukkit.getPluginManager().registerEvents(new GameListener(this), this);
	      alwaysDay();
	    }
	
	   private void alwaysDay() {
		   final CodCraftFFA ffa = this;
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

	private void spawnLoad() {
		     File spawns = new File("./plugins/FFA/config.yml");
		     YamlConfiguration config = YamlConfiguration.loadConfiguration(spawns);
		     for(String s : config.getConfigurationSection("maps").getKeys(false)) {
		      spawnpoints.put(s, new ArrayList<Location>());
		      maps.add(s);
		      for(String location : config.getConfigurationSection("maps."+s).getKeys(false)) {
		    	  Location loc = new Location(Bukkit.getWorld("world"), Double.parseDouble(config.getString("maps."+s+"."+location+".x")),
		                       Double.parseDouble(config.getString("maps."+s+"."+location+".y")), Double.parseDouble(config	
		                             .getString("maps."+s+"."+location+".z")));
		    	  /*Float yaw = Float.parseFloat(config.getString("maps."+s+"."+location+".yaw"));
		    	  if(yaw != null) {
		    		  loc.setYaw(yaw);
		    	  }
		    	  Float pitch = Float.parseFloat(config.getString("maps."+s+"."+location+".pitch"));
		    	  if(pitch != null) {
		    		  loc.setPitch(pitch);
		    	  }*/
		    	  spawnpoints.get(s).add(loc);
		      }
	      }
	   }

	public void onDisable() {
		
	}
	
	
	
	@Override
	public void makegame(String name) {
		GameManager manger = this.api.getModuleForClass(GameManager.class);
		FFAGame game = new FFAGame(this);
	    game.setName(name);
	    manger.registerGame(game);
	    Bukkit.createWorld(new WorldCreator(game.getName()));

	}
	
	
	@Override
	public String getTag() {
		return "[Free For All]";
	}
	
	public enum GameState {
		
		LOBBY,
		INGAME,
		
	}
	/*public void GameTimer() {
		
		Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
			
			@Override
			public void run() {
				GameManager gm = api.getModuleForClass(GameManager.class);
				for(Entry<String, FFAModel> model : currentmap.entrySet()) {
					if(model.getValue().state == null) {
						model.getValue().state = com.codcraft.ffa.CodCraftFFA.GameState.LOBBY;
						model.getValue().gametime = 30;
					}
					Random rnd = new Random();
					if(model.getValue().state == com.codcraft.ffa.CodCraftFFA.GameState.LOBBY) {
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
							for(Team t : gm.getGameWithId(model.getKey()).getTeams()) {
								for(TeamPlayer tp : t.getPlayers()) {
									teamPlayers.add(tp);
								}
							}
							if(teamPlayers.size() == 0) {
								model.getValue().state = com.codcraft.ffa.CodCraftFFA.GameState.LOBBY;
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
								model.getValue().state = com.codcraft.ffa.CodCraftFFA.GameState.INGAME;
								model.getValue().gametime = 600;
								Game<?> g = gm.getGameWithId(model.getKey());
								for(Team t : g.getTeams()) {
									for(TeamPlayer t1 : t.getPlayers()) {
										Player p = Bukkit.getPlayer(t1.getName());
										if(!p.isDead()) {
											p.teleport(Respawn(p, Bukkit.getWorld(g.getName()), model.getValue().map, g));
										}

									}
								}
								
							}
						}
							
					}
					if(model.getValue().state == com.codcraft.ffa.CodCraftFFA.GameState.INGAME) {
						if(model.getValue().gametime > 0) {
							model.getValue().gametime--;
						} else {
							
							detectWin(gm.getGameWithId(model.getKey()));

							model.getValue().state = com.codcraft.ffa.CodCraftFFA.GameState.LOBBY;
							model.getValue().gametime = 30;
							
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
				
			}
		}, 1, 20);
	}*/
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
