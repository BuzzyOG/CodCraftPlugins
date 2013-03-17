package com.codcraft.ccssbb;

import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.GameState;
import com.CodCraft.api.model.Team;
import com.codcraft.ccssbb.CCSSBB.states;

public class SSBB extends Game<CCSSBB> {
	private CCSSBB plugin;
	
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
		SSBmodel game = new SSBmodel();
		game.setState(states.LOBBY);
		
		plugin.games.put(getId(), game);
	}
	
	@Override
	public void deinitialize() {
		Bukkit.unloadWorld(getName(), false);
	}
	
	

	@Override
	public void preStateSwitch(GameState<CCSSBB> state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postStateSwitch(GameState<CCSSBB> state) {
		// TODO Auto-generated method stub
		
	}

}
