package com.codcraft.testgame;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.GameState;

public class TestState implements GameState {

	private TestGame plugin;
	private int duration;
	private Game<?> game;
	private BukkitTask task;

	public TestState(TestGame plugin) {
		this.plugin = plugin;
	}

	@Override
	public String getId() {
		return "TestState";
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
				// TODO Auto-generated method stub
				
			}
		};
		return run;
	}

	@Override
	public void setGame(Game<?> game) {
		this.game = game;
		
	}

	@Override
	public Game<?> getGame() {
		return game;
	}

	@Override
	public void Start() {
		duration = 60;
		task = Bukkit.getScheduler().runTaskTimer(getGame().getPlugin(), getTask(), 0, 20);
	}

	@Override
	public void Stop() {
		if(task != null) {
			task.cancel();
		}
	}

}
