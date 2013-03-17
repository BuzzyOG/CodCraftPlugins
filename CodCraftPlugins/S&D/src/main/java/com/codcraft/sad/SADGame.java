package com.codcraft.sad;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.GameState;
import com.CodCraft.api.model.Team;

public class SADGame extends Game<CodCraftSAD> {

	private CodCraftSAD plugin;
	
	
	public SADGame(CodCraftSAD instance) {
		super(instance);
		for (int count = 1; count <= 2; count++){
			Team t = new Team();
			t.setName("Team"+count);
			t.setMaxPlayers(6);
			this.addTeam(t);
		}
		
		this.plugin = instance;
		
	}
	
	@Override
	public void initialize() {
	    SADModel games = new SADModel();
	    games.map = "Nuketown";
	    plugin.currentmap.put(getId(), games);
	}

	@Override
	public void preStateSwitch(GameState<CodCraftSAD> state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postStateSwitch(GameState<CodCraftSAD> state) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void deinitialize() {
		plugin.currentmap.remove(getId());
	}
	



}
