package com.codcraft.codcraft.game.states;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import com.CodCraft.api.event.GameWinEvent;
import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.GameState;
import com.CodCraft.api.model.Team;
import com.CodCraft.api.model.TeamPlayer;
import com.CodCraft.api.modules.ScoreBoard;
import com.codcraft.codcraft.game.CodCraftGame;
import com.codcraft.lobby.LobbyModule;

public class InGameState implements GameState {
	
	private int defaul;
	private int duration;
	private CodCraftGame<?> game;
	private BukkitTask task;

	public InGameState(int defaul, CodCraftGame<?> game) {
		this.defaul = defaul;
		this.game = (CodCraftGame<?>) game;
	}


	@Override
	public String getId() {
		return "Inagme";
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
					LobbyModule lm = game.getPlugin().api.getModuleForClass(LobbyModule.class);
					lm.UpdateSign(lm.getLobby(getGame().getName()));
					ScoreBoard SB = game.getPlugin().api.getModuleForClass(ScoreBoard.class);
			        int seconds = InGameState.this.getGame().getCurrentState().getTimeLeft() % 60;
			        String seconds1 = "";
			        if (seconds < 10) {
			        	seconds1 = "0" + seconds;
				    } else {
				    	seconds1 = "" + seconds;
			        }
			        int minutes = InGameState.this.getGame().getCurrentState().getTimeLeft() / 60;
			        SB.getObjectiveForGame(InGameState.this.getGame()).setDisplayName(ChatColor.GRAY + "Time Left " + minutes + ":" + seconds1);
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
					game.gameOver();
					game.setState(new LobbyState(0, game));
					GameWinEvent event = new GameWinEvent(game.getTeams().get(0).getName()+" has won!", game.getTeams().get(0), getGame());
					Bukkit.getPluginManager().callEvent(event);
					Bukkit.broadcastMessage(event.getWinMessage());	
				}

			}
			
		};
		return run;
	}

	@Override
	public void setGame(Game<?> game) {
		this.game = (CodCraftGame<?>) game;
	}

	@Override
	public Game<?> getGame() {
		return (Game<?>) game;
	}

	@Override
	public void Start() {
		duration = defaul;
		task = Bukkit.getScheduler().runTaskTimer(game.getPlugin(), getTask(), 0, 20);
		
	}

	@Override
	public void Stop() {
		if(task != null) {
			task.cancel();
		}
	}

}
