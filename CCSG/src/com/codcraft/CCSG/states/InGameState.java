package com.codcraft.CCSG.states;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.GameState;
import com.CodCraft.api.model.Team;
import com.CodCraft.api.model.TeamPlayer;
import com.codcraft.CCSG.CCSGGame;
import com.codcraft.CCSG.SurvialGames;
import com.codcraft.lobby.LobbyModule;

public class InGameState implements GameState<SurvialGames> {

	private int duration = 90;
	private CCSGGame game;
	private BukkitTask task;
	public InGameState(Game<?> g) {
		setGame((CCSGGame) g);
	}

	@Override
	public String getId() {
		return "SG Game";
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
					if(duration % 5 == 0) {
						for(Team t : game.getTeams()) {
							if(t.getPlayers().size() < 3) {
								for(TeamPlayer tp : t.getPlayers()) {
									Player p = Bukkit.getPlayer(tp.getName());
									p.getWorld().strikeLightningEffect(p.getLocation());
								}
							}
						}
					}
				} else {
					getGame().setState(new DeathMatchState(getGame()));
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
		duration = 3600;
		task = Bukkit.getScheduler().runTaskTimer(game.getPlugin(), getTask(), 0, 20);
		
	}

	@Override
	public void Stop() {
		if(task != null) {
			task.cancel();
		}
	}

}
