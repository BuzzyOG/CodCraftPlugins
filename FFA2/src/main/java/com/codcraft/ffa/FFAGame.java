package com.codcraft.ffa;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.GameState;
import com.CodCraft.api.model.Team;

public class FFAGame extends Game<CodCraftFFA> {

	private CodCraftFFA plugin;
	
	
	public FFAGame(CodCraftFFA instance) {
		super(instance);
		for (int count = 1; count <= 8; count++){
			Team t = new Team();
			t.setName("Team"+count);
			t.setMaxPlayers(1);
			this.addTeam(t);
		}
		this.plugin = instance;
		
		
	}
	
	@Override
	public void initialize() {
	    FFAModel games = new FFAModel();
	    games.map = "Nuketown";
		plugin.currentmap.put(getId(), games);
	}

	@Override
	public void preStateSwitch(GameState<CodCraftFFA> state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postStateSwitch(GameState<CodCraftFFA> state) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void deinitialize() {
		plugin.currentmap.remove(getId());
	}
	



}
