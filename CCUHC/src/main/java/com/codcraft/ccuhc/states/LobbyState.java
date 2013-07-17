package com.codcraft.ccuhc.states;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.GameState;
import com.CodCraft.api.model.Team;
import com.CodCraft.api.model.TeamPlayer;
import com.CodCraft.api.modules.Broadcast;
import com.codcraft.ccuhc.AlwaysDayTImer;
import com.codcraft.ccuhc.GameType;
import com.codcraft.ccuhc.ScatterTimer;
import com.codcraft.ccuhc.UHC;
import com.codcraft.ccuhc.UHCGame;
import com.codcraft.lobby.LobbyModule;

public class LobbyState implements GameState<UHC> {

	private UHCGame g;
	private int duration;
	private BukkitTask task;
	
	
	public LobbyState(Game<?> g) {
		this.g = (UHCGame) g;
	}
	
	@Override
	public String getId() {
		return "Lobby";
	}


	@Override
	public Runnable getTask() {
		Runnable run = new Runnable() {
			
			@Override
			public void run() {
				if(duration > 0) {
					duration--;
					LobbyModule lm = getGame().getPlugin().api.getModuleForClass(LobbyModule.class);
					lm.UpdateSign(lm.getLobby(getGame().getName()));
					for(Team t : getGame().getTeams()) {
						for(TeamPlayer tp : t.getPlayers()) {
							Player p = Bukkit.getPlayer(tp.getName());
							if(p != null) {
								p.setLevel(duration);
							}
						}
					}
					if(duration == 9) {
						Broadcast bro = getGame().getPlugin().api.getModuleForClass(Broadcast.class);
						bro.BroadCastMessage(getGame(), "Starting making border!");
						g.BuildBox(Bukkit.getWorld(getGame().getId()));
						
					}
					if(duration == 1) {
						for(Team t : getGame().getTeams()) {
							TeamPlayer tp1 = null;
							for(TeamPlayer tp : t.getPlayers()) {
								tp1 = tp;
								break;
							}
							
							Location loc = getGame().getPlugin().scatterPlayerRandom(Bukkit.getPlayer(tp1.getName()), Bukkit.getWorld(g.getId()));
							for(TeamPlayer tp : t.getPlayers()) {
								Player p = Bukkit.getPlayer(tp.getName());
								if(p != null) {
									p.teleport(loc);
									if(g.types.contains(GameType.SCATTER)) {
										Bukkit.getScheduler().runTaskLater(getGame().getPlugin(), new ScatterTimer(g), new Random().nextInt(g.getRadius()));
									}
									if(g.types.contains(GameType.ALWAYSDAY)) {
										Bukkit.getScheduler().runTaskLater(getGame().getPlugin(), new AlwaysDayTImer(getGame(), 200), 200);
									}
									p.setLevel(0);
								}
							}
						}
					}
				} else {
					getGame().setState(new InGameStates(getGame()));
				}
				
			}
		};
		return run;
	}

	@Override
	public void setGame(Game<UHC> game) {
		this.g = (UHCGame) game;
		
	}

	@Override
	public Game<UHC> getGame() {
		return g;
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
	public void Start() {
		duration = 5000;
		task = Bukkit.getScheduler().runTaskTimer(getGame().getPlugin(), getTask(), 0, 20);
		
	}

	@Override
	public void Stop() {
		if(task != null) {
			task.cancel();
		}
		
	}

}
