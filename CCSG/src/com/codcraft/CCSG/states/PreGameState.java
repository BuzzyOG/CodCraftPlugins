package com.codcraft.CCSG.states;

import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.GameState;
import com.CodCraft.api.model.Team;
import com.CodCraft.api.model.TeamPlayer;
import com.codcraft.CCSG.CCSGGame;
import com.codcraft.CCSG.SurvialGames;
import com.codcraft.lobby.LobbyModule;

public class PreGameState implements GameState<SurvialGames> {

	private int duration;
	private CCSGGame game;
	private BukkitTask task;
	
	public PreGameState(Game<?> g) {
		setGame((CCSGGame) g);
	}

	@Override
	public String getId() {
		return "SG PreGame";
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
				if(duration >= 1) {
					duration--;

					LobbyModule lm = getGame().getPlugin().api.getModuleForClass(LobbyModule.class);
					lm.UpdateSign(lm.getLobby(getGame().getName()));
					for(Entry<Location, String> ss : getGame().map.spawnblocks.entrySet()) {
						if(ss.getValue() != null) {
							Player p = Bukkit.getPlayer(ss.getValue());
							if(p != null) {
								p.teleport(ss.getKey());
							}
						}
						
					}
					for(Team t : getGame().getTeams()) {
						for(TeamPlayer tp : t.getPlayers()) {
							Player p = Bukkit.getPlayer(tp.getName());
							if(p != null) {
								p.setLevel(duration);
							}
						}
					}
				} else {
					getGame().setState(new InGameState(getGame()));
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
	public CCSGGame getGame() {
		return game;
	}

	@Override
	public void Start() {
		duration = 30;
		task = Bukkit.getScheduler().runTaskTimer(game.getPlugin(), getTask(), 0, 20);
		
	}

	@Override
	public void Stop() {
		if(task != null) {
			task.cancel();
		}
	}

}
