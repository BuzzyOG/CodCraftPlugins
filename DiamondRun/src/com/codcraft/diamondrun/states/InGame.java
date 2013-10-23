package com.codcraft.diamondrun.states;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.GameState;
import com.CodCraft.api.model.Team;
import com.CodCraft.api.model.TeamPlayer;
import com.codcraft.diamondrun.DRGame;
import com.codcraft.diamondrun.DiamondRun;
import com.codcraft.lobby.LobbyModule;

public class InGame implements GameState {
	
	private DRGame game;
	private int duration;
	private BukkitTask task;

	public InGame(Game<?> g) {
		this.game = (DRGame) g;
	}

	@Override
	public String getId() {
		return "Game";
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
				if(duration > 1) {
					duration--;
					LobbyModule lm = game.getPlugin().api.getModuleForClass(LobbyModule.class);
					lm.UpdateSign(lm.getLobby(getGame().getName()));
					for(Team t : getGame().getTeams()) {
						for(TeamPlayer tp : t.getPlayers()) {
							Player p = Bukkit.getPlayer(tp.getName());
							if(p != null) {
								p.setLevel(duration);
							}
						}
					}
				} else {
					Random rnd = new Random();
					//String currentmap = game.map;
						List<String> keys      = new ArrayList<String>(game.spawns.keySet());
						String       randomKey = keys.get(rnd.nextInt(keys.size()) );
						game.map = randomKey;
					
					getGame().setRound(0);
					Location loc = game.spawns.get(game.map);
					for(Team t : getGame().getTeams()) {
						for(TeamPlayer tp : t.getPlayers()) {
							Player p = Bukkit.getPlayer(tp.getName());
							if(p != null) {
								p.teleport(loc);
							}
						}
					}
					duration = 150;
				}

			}
		};
		return run;
	}

	@Override
	public void setGame(Game<?> game) {
		this.game = (DRGame) game;
		
	}

	@Override
	public Game<?> getGame() {
		return game;
	}

	@Override
	public void Start() {
		duration = 150;
		task = Bukkit.getScheduler().runTaskTimer(getGame().getPlugin(), getTask(), 0, 20);
		
	}

	@Override
	public void Stop() {
		if(task != null) {
			task.cancel();
		}
	}
}
