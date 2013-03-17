package com.codcraft.SuperAmazingJobyGameModeProject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import com.CodCraft.api.CCAPI;
import com.CodCraft.api.modules.GameManager;
import com.CodCraft.api.services.CCGamePlugin;

public class SAJGMP extends CCGamePlugin {
	
	public Map<String, GameModel> games = new HashMap<String, GameModel>();
	public CCAPI api;
	public Map<String, ArrayList<Location>> spawnpoint = new HashMap<String, ArrayList<Location>>();
	public Map<String, Location> mainpoint = new HashMap<>();
	public Timers timers;
	
	public void onEnable() {
		final Plugin ccapi = Bukkit.getPluginManager().getPlugin("CodCraftAPI");
		if(ccapi == null) {
			getServer().getPluginManager().disablePlugin(this);
		}
		this.api = (CCAPI) ccapi;
		getServer().getPluginManager().registerEvents(new GameListener(this), this);
		LoadSpawns();
		LoadSpawns2();
		timers = new Timers(this);
		timers.GunTimer();
		
	}

	private void LoadSpawns2() {
		File f = new File(getDataFolder(), "main.yml");
		if(!getDataFolder().exists()) {
			getDataFolder().mkdir();
		}
		if(!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		for(String map : config.getConfigurationSection("Maps").getKeys(false)) {
			Location loc1 = new Location(Bukkit.getWorld("world"), Double.parseDouble(config.getString("Maps."+map+".x")), Double.parseDouble(config.getString("Maps."+map+".y")),
					Double.parseDouble(config.getString("Maps."+map+".z")));
			mainpoint.put(map, loc1);
		}
		
	}

	private void LoadSpawns()  {
		File f = new File(getDataFolder(), "config.yml");
		if(!getDataFolder().exists()) {
			getDataFolder().mkdir();
		}
		if(!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		for(String map : config.getConfigurationSection("Maps").getKeys(false)) {
			

			spawnpoint.put(map, new ArrayList<Location>());
			for(String team : config.getConfigurationSection("Maps."+map).getKeys(false)) {
				Location loc = new Location(Bukkit.getWorld("world"), Double.parseDouble(config.getString("Maps."+map+"."+team+".x")), Double.parseDouble(config.getString("Maps."+map+"."+team+".y")),
						Double.parseDouble(config.getString("Maps."+map+"."+team+".z")));
				spawnpoint.get(map).add(loc);
			}
		}
	}
	@Override
	public void makegame(String name) {
		GameModel game = new  GameModel(this);
		game.setName(name);
		api.getModuleForClass(GameManager.class).registerGame(game);
	}

	@Override
	public String getTag() {
		return "[SAJGMP]";
	}

}
