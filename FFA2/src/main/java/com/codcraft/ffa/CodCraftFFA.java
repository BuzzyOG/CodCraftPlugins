package com.codcraft.ffa;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import com.CodCraft.api.CCAPI;
import com.CodCraft.api.model.Game;
import com.CodCraft.api.modules.GameManager;
import com.CodCraft.api.services.CCGamePlugin;
import com.codcraft.lobby.Lobby;
import com.codcraft.lobby.LobbyModule;

public class CodCraftFFA extends CCGamePlugin {
	
	/**
	 * API instance
	 */
	public CCAPI api;
	
	/**
	 * Public respawn locations :S
	 */
	public Map<String, ArrayList<Location>> spawnpoints = new HashMap<>();
	//@Deprecated
	//public ArrayList<String> maps = new ArrayList<>(); 


	public void onEnable() {
		super.onEnable();
		final Plugin api = this.getServer().getPluginManager().getPlugin("CodCraftAPI");
	    if(api != null || !(api instanceof CCAPI)) {
	    	CodCraftFFA.this.api = (CCAPI) api;
	    	//System.out.println(api);
	    	//System.out.println(this.api);
	    } else {
	    	// Disable if we cannot get the api.
	        getLogger().warning("Could not find API. Disabling...");
	        getServer().getPluginManager().disablePlugin(this);
	        return;
	    }
    	System.out.println(api);
    	System.out.println(this.api);
	    spawnLoad();
	    Bukkit.getPluginManager().registerEvents(new GameListener(this), this);
	    alwaysDay();
	    setupLobbyUpdate();
	}
	
	private void setupLobbyUpdate() {
		final CodCraftFFA plugin = this;
		Bukkit.getScheduler().runTaskLater(this, new Runnable() {
			
			@Override
			public void run() {
				
				GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		        List<Game<?>> games = gm.getGamesForPlugin(plugin);
		        LobbyModule lm = (LobbyModule)CodCraftFFA.this.api.getModuleForClass(LobbyModule.class);
		        
		        for(Entry<Integer, Lobby> en : lm.getLobbys().entrySet()) {
		        	for(Game<?> g : games) {
				          if (g.getName().equalsIgnoreCase(en.getValue().getGame())) {
					            lm.setUpdateCode(en.getKey(), new FFALobbyUpdate(plugin, en.getKey()));
				          }
		        	}
		        }
		   }
		}, 80L);
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
	public void makeGame(String[] name) {
		GameManager manger = this.api.getModuleForClass(GameManager.class);
		FFAGame game = new FFAGame(this);
	    game.setName(name[0]);
	    manger.registerGame(game);
	    Bukkit.createWorld(new WorldCreator(game.getName()));

	}
	@Override
	public String getTag() {
		return "[Free For All]";
	}
}
