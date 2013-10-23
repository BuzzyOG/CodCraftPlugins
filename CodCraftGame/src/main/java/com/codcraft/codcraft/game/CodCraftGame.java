package com.codcraft.codcraft.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.CodCraft.api.event.GameWinEvent;
import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.GameState;
import com.CodCraft.api.services.CCGamePlugin;
import com.codcraft.codcraft.game.states.InGameState;
import com.codcraft.codcraft.game.states.LobbyState;

public abstract class CodCraftGame<T extends CCGamePlugin> extends Game<T> {

	private boolean FF;
	public Map<CodCraftMap, List<Location>> spawnPoints = new HashMap<CodCraftMap, List<Location>>();
	private List<String> allowedWeapons = new ArrayList<>();
	private CodCraftMap currentMap;

	//TODO clean up
	public int map1;
	public int map2;
	public CodCraftMap Map1;
	public CodCraftMap Map2;	
	public ArrayList<String> voters = new ArrayList<>();

	public CodCraftGame(T instance) {
		super(instance);
	}
	
	public abstract boolean checkWin();
	public abstract void onWin(GameWinEvent event);
	public abstract void tick();
	public abstract Location getRespawnLocation(Player p);
	public abstract void onGUIupdate();
	public abstract boolean onDamage(Player p);
	public abstract void onDeath(Player killer, Player dead);
	public abstract void gameOver();
	
	
	public void addDefaultStates(CodCraftGame<?> g) {
		addState(new LobbyState(60, g));
		addState(new InGameState(480, g));
	}
	
	
	public boolean addWeapon(String name) {
		if(allowedWeapons.contains(name)) {
			return false;
		}
		allowedWeapons.add(name);
		return true;
	}
	
	public List<String> getAllowWeapons() {
		return allowedWeapons;
	}
	
	public void setUpTeams(int ammount, int size, boolean FF) {
		this.FF = FF;
		for(int i = 0; i < ammount; i++) {
			CCTeam t = new CCTeam();
			t.setName("Team" + i);
			t.setMaxPlayers(size);
			teams.add(t);
		}
	}
	
	public void addStates(List<GameState> states) {
		for(GameState s : states) {
			knownStates.put(s.getId(),   s);
		}
	}
	
	public void addState(GameState state) {
		knownStates.put(state.getId(), state);
	}
	
	
	public void addMap(CodCraftMap map) {
		spawnPoints.put(map, new ArrayList<Location>());
	}
	
	public void setSpawns(String name, List<Location> locs) {
		spawnPoints.put(getMap(name), locs);
	}
	
	public List<CodCraftMap> getMaps() {
		List<CodCraftMap> maps = new ArrayList<>();
		for(Entry<CodCraftMap, List<Location>> en : spawnPoints.entrySet()) {
			maps.add(en.getKey());
		}
		return maps;
	}
	
	public CodCraftMap getMap(String name) {
		for(Entry<CodCraftMap, List<Location>> en : spawnPoints.entrySet()) {
			if(en.getKey().getName().equalsIgnoreCase(name)) {
				return en.getKey();
			}
		}
		return null;
	}

	public CodCraftMap getCurrentMap() {
		return currentMap;
	}

	public void setCurrentMap(String currentMap) {
		for(Entry<CodCraftMap, List<Location>> en : spawnPoints.entrySet()) {
			if(en.getKey().getName().equalsIgnoreCase(currentMap)) {
				this.currentMap = en.getKey();
			}
		}
	}

	public boolean isFF() {
		return FF;
	}

	public void setFF(boolean fF) {
		FF = fF;
	}



}
