package com.codcraft.ccssbb;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.GameState;
import com.codcraft.lobby.LobbyModule;

public class PreGameState implements GameState {
	
	public PreGameState(Game<?> g) {
		this.game = (SSBB) g;
	}

	private int duration;
	private SSBB game;
	private BukkitTask task;

	@Override
	public String getId() {
		return "PreGame";
	}

	@Override
	public void setTimeLeft(int duration) {
		this.duration = duration;
		
	}

	@Override
	public int getTimeLeft() {
		return duration;
	}

	@Override
	public Runnable getTask() {
		Runnable run = new Runnable() {
			
			@Override
			public void run() {
				LobbyModule lm = game.getPlugin().api.getModuleForClass(LobbyModule.class);
				lm.UpdateSign(lm.getLobby(getGame().getName()));
				
			}
		};
		return run;
	}

	@Override
	public void setGame(Game<?> game) {
		this.game = (SSBB) game;
		
	}

	@Override
	public Game<?> getGame() {
		return game;
	}

	@Override
	public void Start() {
		duration = -1;
		task = Bukkit.getScheduler().runTaskTimer(getGame().getPlugin(), getTask(), 0, 20);
		
	}

	@Override
	public void Stop() {
		if(task != null) {
			task.cancel();
		}
		
	}

}
