package com.codcraft.CCSG;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;

public class MAP {

	public Map<Location, Integer> chests = new HashMap<Location, Integer>();
	
	public List<Location> spawns = new ArrayList<>();
	
	public String name;
	
	public Map<Location, String> spawnblocks = new HashMap<>();
}
