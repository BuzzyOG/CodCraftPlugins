package com.codcraft.CCSG.states;

import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.GameState;
import com.codcraft.CCSG.CCSGGame;
import com.codcraft.CCSG.SurvialGames;
import com.codcraft.lobby.LobbyModule;

public class DeathMatchState implements GameState<SurvialGames> {

	private int duration;
	private CCSGGame game;
	private BukkitTask task;
	public DeathMatchState(Game<?> g) {
		setGame((CCSGGame) g);
	}

	@Override
	public String getId() {
		return "SG DeathMatch";
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
				if(duration == 90) {
					
					for(Entry<Location, String> s : game.map.spawnblocks.entrySet()) {
						if(s.getValue() != null) {
							Player p = Bukkit.getPlayer(s.getValue());
							if(p != null) {
								p.teleport(s.getKey());
							}
						}
					}
				}
				if(duration >= 1) {
					duration--;
					LobbyModule lm = getGame().getPlugin().api.getModuleForClass(LobbyModule.class);
					lm.UpdateSign(lm.getLobby(getGame().getName()));
				} else {
					getGame().setState(new LobbyState(getGame()));
				}
				
			}
		};
		return run;
	}

	@Override
	public void setGame(Game<SurvialGames> game) {
		this.game = (CCSGGame) game;
	}

	@Override
	public Game<SurvialGames> getGame() {
		return game;
	}

	@Override
	public void Start() {
		duration = 120;
		task = Bukkit.getScheduler().runTaskTimer(game.getPlugin(), getTask(), 0, 20);
		
	}

	@Override
	public void Stop() {
		if(task != null) {
			task.cancel();
		}
	}

}
