package com.codcraft.tdm.states;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.GameState;
import com.CodCraft.api.model.Team;
import com.CodCraft.api.model.TeamPlayer;
import com.codcraft.lobby.LobbyModule;
import com.codcraft.tdm.CodCraftTDM;
import com.codcraft.tdm.TDMGame;

public class InGameState implements GameState<CodCraftTDM> {

	private TDMGame game;
	private int duration;
	private BukkitTask task;

	public InGameState(TDMGame game) {
		this.game = game;
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
				if(duration >= 1) {
					duration--;
					LobbyModule lm = getGame().getPlugin().api.getModuleForClass(LobbyModule.class);
					lm.UpdateSign(lm.getLobby(getGame().getName()));
					for (Team t : game.getTeams()) {
						for (TeamPlayer p1 : t.getPlayers()) {
							Player p = Bukkit.getPlayer(p1.getName());
							if (!p.isOnline()) {
								break;
							}
							p.setLevel(duration);
						}
					}
				} else {
					game.detectWin(game);
				}

			}
			
		};
		return run;
	}

	@Override
	public void setGame(Game<CodCraftTDM> game) {
		this.game = (TDMGame) game;
		
	}

	@Override
	public Game<CodCraftTDM> getGame() {
		return game;
	}

	@Override
	public void Start() {
		duration = 600;
		task = Bukkit.getScheduler().runTaskTimer(game.getPlugin(), getTask(), 0, 20);
	}

	@Override
	public void Stop() {
		if(task != null) {
			task.cancel();
		}
	}

}
