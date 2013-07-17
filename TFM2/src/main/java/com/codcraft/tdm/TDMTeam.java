package com.codcraft.tdm;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;

import com.CodCraft.api.model.Team;

public final class TDMTeam extends Team {
	
	
	private Map<String, List<Location>> spawns = new HashMap<>();

	public void setSpawns(Map<String, List<Location>> spawns) {
		this.spawns = spawns;
	}

	public Map<String, List<Location>> getSpawns() {
		return spawns;
	}
	
	public void removeSpawn(Location spawn) {
		spawns.remove(spawn);
	}

	public void addSpawn(String s, List<Location> spawns) {
		this.spawns.put(s, spawns);
	}
	
	public List<Location> getLocations(String map) {
		return spawns.get(map);
	}

}
