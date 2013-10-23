package com.codcraft.ccmapmaker;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.YamlConfiguration;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.GameState;
import com.CodCraft.api.model.Team;

public class MapMaker extends Game<CCMM> {
	
	
	public Map<String, Location> spawns = new HashMap<>();
	
	public List<String> getSpawns = new ArrayList<>();

	public MapMaker(CCMM instance) {
		super(instance);
		Team t = new Team("1");
		t.setMaxPlayers(100);
		teams.add(t);
		knownStates.put(new OnState(this).getId(), new OnState(this));
		knownStates.put(new OffState(this).getId(), new OffState(this));
		setState(new OnState(this));
	}

	@Override
	public void initialize() {
		Bukkit.createWorld(new WorldCreator(getName()));
		File f = new File("./plugins/MapMaker/config.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		for(String location : config.getConfigurationSection("maps."+getName()).getKeys(false)) {
			String x = config.getString("maps."+getName()+"."+location+".x");
			String y = config.getString("maps."+getName()+"."+location+".y");
			String z = config.getString("maps."+getName()+"."+location+".z");
			Material mat = Material.valueOf(config.getString("maps."+getName()+"."+location+".mat"));
			Location loc = new Location(Bukkit.getWorld(getName()), Double.parseDouble(x), Double.parseDouble(y), Double.parseDouble(z));
			spawns.put(mat.name(), loc);
		}

	}

	@Override
	public void preStateSwitch(GameState state) {
		
	}

	@Override
	public void postStateSwitch(GameState state) {
		
	}


}
