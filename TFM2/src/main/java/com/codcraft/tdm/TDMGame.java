package com.codcraft.tdm;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.GameState;
import com.CodCraft.api.model.Team;

public class TDMGame extends Game<CodCraftTDM> {

	private CodCraftTDM plugin;
	
	
	public TDMGame(CodCraftTDM instance) {
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
	    TDMModel games = new TDMModel();
	    games.map = "Nuketown";
	    plugin.currentmap.put(getId(), games);
	}

	@Override
	public void preStateSwitch(GameState<CodCraftTDM> state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postStateSwitch(GameState<CodCraftTDM> state) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void deinitialize() {
		plugin.currentmap.remove(getId());
	}
	



}
