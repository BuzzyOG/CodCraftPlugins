package com.codcraft.ccuhc.states;


import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.GameState;
import com.codcraft.ccuhc.UHC;
import com.codcraft.ccuhc.UHCGame;
import com.codcraft.lobby.LobbyModule;

public class InGameStates implements GameState {
	
	public InGameStates(Game<?> g) {
		this.g = (UHCGame) g;
	}

	private UHCGame g;
	private String id = "Game";
	private BukkitTask task;
	
	@Override
	public String getId() {
		return id;
	}


	@Override
	public Runnable getTask() {
		Runnable run = new Runnable() {
			
			@Override
			public void run() {
				LobbyModule lm = g.getPlugin().api.getModuleForClass(LobbyModule.class);
				lm.UpdateSign(lm.getLobby(getGame().getName()));
				
			}
		};
		return run;
	}

	@Override
	public void setGame(Game<?> game) {
		this.g = (UHCGame) game;
		
	}

	@Override
	public Game<?> getGame() {
		return g;
	}

	@Override
	public void setTimeLeft(int duration) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getTimeLeft() {
		// TODO Auto-generated method stub
		return -2;
	}

	@Override
	public void Start() {
		task = Bukkit.getScheduler().runTaskTimer(getGame().getPlugin(), getTask(), 0, 20);
		
	}

	@Override
	public void Stop() {
		if(task != null) {
			task.cancel();
		}
		
	}

}
