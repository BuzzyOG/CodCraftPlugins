package com.codcraft.PP;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.GameState;
import com.codcraft.lobby.LobbyModule;

public class State implements GameState {
	
	private PPGame game;
	private BukkitTask task;

	public State(Game<?> g) {
		this.game = (PPGame) g;
	}

	@Override
	public String getId() {
		return "Game";
	}

	@Override
	public void setTimeLeft(int duration) {

	}

	@Override
	public int getTimeLeft() {
		return 0;
	}

	@Override
	public Runnable getTask() {
		return new Runnable() {
			
			@Override
			public void run() {
				LobbyModule lm = game.getPlugin().api.getModuleForClass(LobbyModule.class);
				lm.UpdateSign(lm.getLobby(game.getName()));
				
			}
		};
	}

	@Override
	public void setGame(Game<?> game) {
		this.game = (PPGame) game;

	}

	@Override
	public Game<?> getGame() {
		return game;
	}

	@Override
	public void Start() {
		task = Bukkit.getScheduler().runTaskTimer(game.getPlugin(), getTask(), 0, 20);
	}

	@Override
	public void Stop() {	
		if(task != null) {
			task.cancel();
		}
	}

}
