package com.codcraft.PP;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.GameState;
import com.CodCraft.api.model.Team;

public class PPGame extends Game<Parcore> {

	public PPGame(Parcore instance) {
		super(instance);
		Team t = new Team("Team");
		t.setMaxPlayers(50);
		addTeam(t);
		knownStates.put(new State(this).getId(), new State(this));
		setState(new State(this));
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
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
