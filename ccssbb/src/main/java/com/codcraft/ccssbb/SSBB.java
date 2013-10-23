package com.codcraft.ccssbb;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.GameState;
import com.CodCraft.api.model.Team;

public class SSBB extends Game<CCSSBB> {
	
	@SuppressWarnings("unused")
	private CCSSBB plugin;
	
	public String map;
	
	protected Map<String, String> playerclass = new HashMap<String, String>();
	
	public SSBB(CCSSBB instance) {
		super(instance);
		plugin = instance;
	}

	@Override
	public void initialize() {	
		for (int count = 1; count <= 4; count++){
			Team t = new Team();
			t.setName(""+count);
			t.setMaxPlayers(1);
			this.addTeam(t);
		}
		Bukkit.createWorld(new WorldCreator(getName()));
		knownStates.put(new PreGameState(this).getId(), new PreGameState(this));
		knownStates.put(new InGameState(this).getId(), new InGameState(this));
		setState(new PreGameState(this));
	}
	
	@Override
	public void deinitialize() {
		Bukkit.unloadWorld(getName(), false);
		for(Team t : getTeams()) {
			removeTeam(t);
		}
	}
	
	

	@Override
	public void preStateSwitch(GameState state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postStateSwitch(GameState state) {
		// TODO Auto-generated method stub
		
	}

}
