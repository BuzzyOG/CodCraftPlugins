package com.codcraft.CCSG;


import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.WorldCreator;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.GameState;
import com.CodCraft.api.model.Team;
import com.codcraft.CCSG.states.DeathMatchState;
import com.codcraft.CCSG.states.InGameState;
import com.codcraft.CCSG.states.LobbyState;
import com.codcraft.CCSG.states.PreGameState;

public class CCSGGame extends Game<SurvialGames> {

	
	public Map<Location, Material> blocks = new HashMap<Location, Material>();
	
	public Location lobby;
	
	public MAP map;
	
	public Map<String, Integer> votes = new HashMap<String, Integer>();
	
	public Map<String, Integer> voters = new HashMap<String, Integer>();
	
	public Map<String, MAP> maps;
	
	public CCSGGame(SurvialGames instance) {
		super(instance);
		
	}

	
	@Override
	public void initialize() {
		votes.put("TestMap", 0);
		Bukkit.createWorld(new WorldCreator(getName()));
		maps = new ConcurrentHashMap<String, MAP>(getPlugin().maps);
		for(Entry<String, MAP> s: maps.entrySet()) {
			for(Entry<Location, String> i : s.getValue().spawnblocks.entrySet()) {
				i.getKey().setWorld(Bukkit.getWorld(getName()));
			}
		}
		for(Entry<String, MAP> s: maps.entrySet()) {
			for(Entry<Location, String> i : s.getValue().spawnblocks.entrySet()) {
				System.out.println(i.getKey().toString());
			}
		}
		lobby = new Location(Bukkit.getWorld(getName()), -1575, 70, -642);
		Team t = new Team();
		t.setName("Team");
		t.setMaxPlayers(24);
		teams.add(t);
		DeathMatchState dm = new DeathMatchState(this);
		InGameState ig = new InGameState(this);
		LobbyState l = new LobbyState(this);
		PreGameState pg = new PreGameState(this);
		knownStates.put(dm.getId(), dm);
		knownStates.put(ig.getId(), ig);
		knownStates.put(l.getId(), l);
		knownStates.put(pg.getId(), pg);
		setState(l);
	}
	
	@Override
	public void deinitialize() {
		for(Entry<Location, Material> map : blocks.entrySet()) {
			map.getKey().getBlock().setType(map.getValue());
		}
	}

	@Override
	public void preStateSwitch(GameState<SurvialGames> state) {
		
	}

	@Override
	public void postStateSwitch(GameState<SurvialGames> state) {
		
	}
	

}
