package com.codcraft.ccmapmaker;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.GameState;
import com.codcraft.lobby.LobbyModule;

public class OnState implements GameState<CCMM> {

	private MapMaker game;
	private BukkitTask task;
	
	public OnState(MapMaker game) {
		this.game = game;
	}

	@Override
	public String getId() {
		return "On";
	}

	@Override
	public void setTimeLeft(int duration) {
		
	}

	@Override
	public int getTimeLeft() {
		return -1;
	}

	@Override
	public Runnable getTask() {
		Runnable run = new Runnable() {
			
			@Override
			public void run() {
				LobbyModule lm = getGame().getPlugin().api.getModuleForClass(LobbyModule.class);
				lm.UpdateSign(lm.getLobby(getGame().getName()));
			}
		};
		return run;
	}

	@Override
	public void setGame(Game<CCMM> game) {
		this.game = (MapMaker) game;
	}

	@Override
	public Game<CCMM> getGame() {
		return game;
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
