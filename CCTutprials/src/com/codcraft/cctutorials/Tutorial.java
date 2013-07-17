package com.codcraft.cctutorials;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Tutorial {
    
	private String name;
	
	private Map<Integer, Location> locations = new HashMap<>();
	
	private Map<String, Integer> players = new HashMap<String, Integer>();

	public Tutorial(String name) {
		this.name = name;
	}
	
	
	public void addPlayer(Player p) {
		putPlayer(p);
		telportToNext(p);
	}

	public void removePlayer(Player p) {
		players.remove(p.getName());
		Bukkit.dispatchCommand(p, "spawn");
	}

	private void telportToNext(Player p) {
		if(players.containsKey(p.getName()) && locations.containsKey(players.get(p.getName()))) {
			plusLocation(p);
			p.teleport(locations.get(players.get(p.getName())));
		}
		
	}

	private void plusLocation(Player p) {
		if(players.containsKey(p.getName())) {
			players.put(p.getName(), players.get(p.getName()) + 1);
		}
	}

	private void putPlayer(Player p) {
		players.put(p.getName(), 1);
	}
	
	
}
