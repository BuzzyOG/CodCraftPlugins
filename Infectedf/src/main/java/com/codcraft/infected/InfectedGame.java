package com.codcraft.infected;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.GameState;
import com.CodCraft.api.model.Team;

public class InfectedGame extends Game<CodCraftInfected> {	
	
	private CodCraftInfected plugin;
	
	public InfectedGame(CodCraftInfected instance) {
		super(instance);
		for (int count = 1; count <= 2; count++){
			Team t = new Team();
			t.setName("Team"+count);
			t.setMaxPlayers(12);
			this.addTeam(t);
		}
		plugin = instance;
		
		
		
	}
	
	@Override
	public void initialize() {
	    InfectedModel games = new InfectedModel();
	    games.map = "Nuketown";
	    plugin.currentmap.put(getId(), games);
	}
	
	

	@Override
	public void preStateSwitch(GameState<CodCraftInfected> state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postStateSwitch(GameState<CodCraftInfected> state) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void deinitialize() {
		plugin.currentmap.remove(getId());
	}
	



}
