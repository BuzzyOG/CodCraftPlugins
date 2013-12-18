package com.codcraft.cccross.test;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.GameState;
import com.CodCraft.api.model.Team;
import com.CodCraft.api.model.hook.Hook;
import com.CodCraft.api.model.states.LobbyState;
import com.codcraft.cccross.CCCrossPlugin;

public class TestGame extends Game<CCCrossPlugin> {

	public TestGame(CCCrossPlugin instance) {
		super(instance);
		addHook(new Hook("testHOOK!", TestGame.this) {
			
			@Override
			public void update(String[] arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void initialize() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void deinitialize() {
				// TODO Auto-generated method stub
				
			}
		});
		addTeam(new Team("Test"));
		knownStates.put(new LobbyState(this).getId(), new LobbyState(this));
		setState(new LobbyState(this));
		
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postStateSwitch(GameState arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preStateSwitch(GameState arg0) {
		// TODO Auto-generated method stub
		
	}

}
