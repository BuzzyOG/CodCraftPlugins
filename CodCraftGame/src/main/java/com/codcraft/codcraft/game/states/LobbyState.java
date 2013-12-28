package com.codcraft.codcraft.game.states;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.GameState;
import com.CodCraft.api.model.Team;
import com.CodCraft.api.model.TeamPlayer;
import com.CodCraft.api.modules.Broadcast;
import com.CodCraft.api.modules.ScoreBoard;
import com.codcraft.codcraft.game.CodCraftGame;
import com.codcraft.lobby.Lobby;
import com.codcraft.lobby.LobbyModule;

public class LobbyState implements GameState {
	
	private int defaul;
	private int duration;
	private CodCraftGame<?> game;
	private BukkitTask task;

	public LobbyState(int defaul, Game<?> g) {
		this.defaul = defaul;
		this.game = (CodCraftGame<?>) g;
	}

	@Override
	public String getId() {
		return "Lobby";
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
				if (duration >= 1) {
					duration--;
					ScoreBoard SB = game.getPlugin().getApi().getModuleForClass(ScoreBoard.class);
			        int seconds = getGame().getCurrentState().getTimeLeft() % 60;
			        String seconds1 = "";
			        if (seconds < 10) {
			            seconds1 = "0" + seconds;
				 	} else {
				 		seconds1 = "" + seconds;
			        }
			        int minutes = getGame().getCurrentState().getTimeLeft() / 60;
			        SB.getObjectiveForGame(LobbyState.this.getGame()).setDisplayName(ChatColor.GRAY + "Pre Game " + minutes + ":" + seconds1);
					LobbyModule lm = game.getPlugin().getApi().getModuleForClass(LobbyModule.class);
					Lobby l = lm.getLobby(game.getName());
					if(l != null) {
						lm.UpdateSign(lm.getLobby(getGame().getName()));
					}
					Random rnd = new Random();
					if (game.Map2 == null) {
						game.Map2 = game.getMaps().get(rnd.nextInt(game.getMaps().size()));
					}
					if (game.Map1 == null) {
						game.Map1 = game.getMaps().get(rnd.nextInt(game.getMaps().size()));
					}

					while (game.Map1.getName().equalsIgnoreCase(game.Map2.getName())) {
						game.Map1 = game.getMaps().get(rnd.nextInt(game.getMaps().size()));
					}
					if(duration == 5) {
						Broadcast b = game.getPlugin().getApi().getModuleForClass(Broadcast.class);
						b.BroadCastMessage(getGame(), "Current Votes!");
						b.BroadCastMessage(getGame(), game.Map1.getName()+": "+ game.map1);
						b.BroadCastMessage(getGame(), game.Map2.getName()+": "+ game.map2);
					}
					game.tick();
				} else {
					List<TeamPlayer> teamPlayers = new ArrayList<>();
					for (Team t : game.getTeams()) {
						for (TeamPlayer tp : t.getPlayers()) {
							teamPlayers.add(tp);
						}
					}
					if (teamPlayers.size() == 0) {
						duration = 30;
					} else {
						if (game.map1 > game.map2) {
							game.setCurrentMap(game.Map1.getName());

						} else {
							game.setCurrentMap(game.Map2.getName());
						}
						game.map1 = 0;
						game.map2 = 0;
						game.voters.clear();

						for (Team t : game.getTeams()) {
							for (TeamPlayer t1 : t.getPlayers()) {
								Player p = Bukkit.getPlayer(t1.getName());
								if (!p.isDead()) {
									p.teleport(game.getRespawnLocation(p));
								}

							}
						}
						getGame().setState(new InGameState(0, game));
					}
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
