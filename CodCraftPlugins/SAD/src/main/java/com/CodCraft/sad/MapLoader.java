package com.CodCraft.sad;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.YamlConfiguration;

import com.CodCraft.sad.GM.Game;
import com.CodCraft.sad.GM.SAD;


public class MapLoader extends CodCraft {
	
	public final static File Worldsyml = new File("./CodCraft/worlds.yml");
	public static HashMap<String, Integer>		mapTime		= new HashMap<String, Integer>();
	public static HashMap<String, Boolean>		mapWeather		= new HashMap<String, Boolean>();
	public static ArrayList<World>				Maps			= new ArrayList<World>();
	public static HashMap<World, ArrayList<Location>> Blocations		=new HashMap<World, ArrayList<Location>>();
	public MapLoader() {
		if(!Worldsyml.exists()) {
			try { Worldsyml.createNewFile(); 
			} catch (IOException e) { e.printStackTrace(); }
		}
		YamlConfiguration config = YamlConfiguration.loadConfiguration(Worldsyml);
		for(String s : config.getConfigurationSection("Worlds").getKeys(false)) {
			Bukkit.createWorld(new WorldCreator(s));
			int a = config.getInt("Worlds."+s+".Time");
			String Weather = config.getString("Worlds."+s+".Weather");
			Boolean map = config.getBoolean("Worlds."+s+".Map");
			if(map) {
				World maps = Bukkit.getWorld(s);
				Maps.add(maps);
				Game.worlds.add(maps);
			}
			World w = Bukkit.getWorld(s);
			Location loc = new Location(w, Double.parseDouble(config.getString("Worlds."+s+".x3")), Double.parseDouble(config.getString("Worlds."+s+".y3")), Double.parseDouble(config.getString("Worlds."+s+".z3")));
			Location loc2 = new Location(w, Double.parseDouble(config.getString("Worlds."+s+".x4")), Double.parseDouble(config.getString("Worlds."+s+".y4")), Double.parseDouble(config.getString("Worlds."+s+".z4")));
			Location loc3 = new Location(w, Double.parseDouble(config.getString("Worlds."+s+".x5")), Double.parseDouble(config.getString("Worlds."+s+".y5")), Double.parseDouble(config.getString("Worlds."+s+".z5")));
			Blocations.put(w, new ArrayList<Location>());
			Blocations.get(w).add(loc);
			Blocations.get(w).add(loc2);
			SAD.bomb.put(w, loc3);
			if(map == true) {
				
			} else {
				
			}

			Boolean b = null;
			if(Weather.equalsIgnoreCase("sun")) {
				b = false;
			}
			if(Weather.equalsIgnoreCase("rain")) {
				b = true;
			}
			if(Weather.equalsIgnoreCase("sun")) {
				b =true; 
			}
			if(b != null) {
				mapWeather.put(s, b);
				mapTime.put(s, a);
			}			
		}

	}
	@Deprecated
	public static void LoadWorlds() {
		if(!Worldsyml.exists()) {
			try { Worldsyml.createNewFile(); 
			} catch (IOException e) { e.printStackTrace(); }
		}
		YamlConfiguration config = YamlConfiguration.loadConfiguration(Worldsyml);
		for(String s : config.getConfigurationSection("Worlds").getKeys(false)) {
			Bukkit.createWorld(new WorldCreator(s));
			int a = config.getInt("Worlds."+s+".Time");
			String Weather = config.getString("Worlds."+s+".Weather");
			Boolean map = config.getBoolean("Worlds."+s+".Map");
			if(map) {
				World maps = Bukkit.getWorld(s);
				Maps.add(maps);
			}
			if(map == true) {
				
			} else {
				
			}

			Boolean b = null;
			if(Weather.equalsIgnoreCase("sun")) {
				b = false;
			}
			if(Weather.equalsIgnoreCase("rain")) {
				b = true;
			}
			if(Weather.equalsIgnoreCase("sun")) {
				b =true; 
			}
			if(b != null) {
				mapWeather.put(s, b);
				mapTime.put(s, a);
			}			
		}
	}
		
		
}