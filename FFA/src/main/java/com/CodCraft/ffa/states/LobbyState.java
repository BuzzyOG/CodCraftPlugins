package com.CodCraft.ffa.states;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.services.GameState;
import com.CodCraft.ffa.GM.Round;

public class LobbyState implements GameState {
	private Integer duration;
	private Game<Round> game;

	@Override
	public void setStateDuration(int duration) {
		this.duration = duration;
	}

	@Override
	public int getStateDuration() {
		return duration;
	}

	@Override
	public Runnable getTask() {
		// TODO Auto-generated method stub
		return null;
	}
	   public class LobbyRun implements Runnable {

		      @Override
		      public void run() {
		    	  
		      }
		   }

	@Override
	public void setGame(Game game) {
		this.game = game;
	}

	@Override
	public Game getGame() {
		return game;
	}
	

}
