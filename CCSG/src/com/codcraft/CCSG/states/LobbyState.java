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

public class LobbyState implements GameState<SurvialGames> {
	
	private int durration = 150;
	private CCSGGame game;
	private BukkitTask task;

	public LobbyState(Game<?> g) {
		setGame((CCSGGame) g);
	}
	
	@Override
	public String getId() {
		return "SG Lobby";
	}

	@Override
	public void setTimeLeft(int duration) {
		this.durration = duration;
		
	}

	@Override
	public int getTimeLeft() {
		return durration;
	}

	@Override
	public Runnable getTask() {
		Runnable run = new Runnable() {
			
			@Override
			public void run() {
				if(durration > 1) {
					durration--;
					if(durration == 4) {
						for(Team t : game.getTeams()) {
							for(TeamPlayer tp : t.getPlayers()) {
								for(Entry<Location, String> sss : game.map.spawnblocks.entrySet()) {
									if(sss.getValue() == null) {
										sss.setValue(tp.getName());
										break;
									}
								}
							}
						}
					}
					if(durration == 5) {
						String map = null;
						int i = 0;
						for(Entry<String, Integer> s : game.votes.entrySet()){
							if(map == null) {
								map = s.getKey();
								i = s.getValue();
							} else {
								if(i < s.getValue()) {
									map = s.getKey();
									i = s.getValue();
								}
							}
						}
						game.map = game.maps.get(map);
						
					}
					LobbyModule lm = getGame().getPlugin().api.getModuleForClass(LobbyModule.class);
					lm.UpdateSign(lm.getLobby(getGame().getName()));
					for(Team t : getGame().getTeams()) {
						for(TeamPlayer tp : t.getPlayers()) {
							Player p = Bukkit.getPlayer(tp.getName());
							if(p != null) {
								p.setLevel(durration);
							}
						}
					}
				} else {
					for(Team t : getGame().getTeams()) {
						for(TeamPlayer tp : t.getPlayers()) {
							Player p = Bukkit.getPlayer(tp.getName());
							if(p != null) {
								p.setLevel(0);
							}
						}
					}
					getGame().setState(new PreGameState(getGame()));
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
		durration = 150;
		task = Bukkit.getScheduler().runTaskTimer(game.getPlugin(), getTask(), 0, 20);
		
	}

	@Override
	public void Stop() {
		if(task != null) {
			task.cancel();
		}
	}

}
