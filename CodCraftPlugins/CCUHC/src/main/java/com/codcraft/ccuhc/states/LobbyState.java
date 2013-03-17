package com.codcraft.ccuhc.states;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.GameState;
import com.codcraft.ccuhc.UHC;

public class LobbyState implements GameState<UHC> {

	private Game<UHC> g;
	
	@Override
	public String getId() {
		return "1";
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
		// TODO Auto-generated method stub
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
