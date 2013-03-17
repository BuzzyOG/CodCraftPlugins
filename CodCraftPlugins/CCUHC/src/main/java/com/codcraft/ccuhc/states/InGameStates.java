package com.codcraft.ccuhc.states;


import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.GameState;
import com.codcraft.ccuhc.UHC;

public class InGameStates implements GameState<UHC> {

	private Game<UHC> g;
	private String id = "1453645";
	
	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setInterval(int duration) {
		
	}

	@Override
	public int getInterval() {
		return 0;
	}

	@Override
	public Runnable getTask() {
		return null;
	}

	@Override
	public void setGame(Game<UHC> game) {
		this.g = game;
		
	}

	@Override
	public Game<UHC> getGame() {
		return g;
	}

}
