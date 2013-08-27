package com.codcraft.tdm.states;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.scheduler.BukkitTask;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.GameState;
import com.CodCraft.api.model.Team;
import com.CodCraft.api.model.TeamPlayer;
import com.CodCraft.api.modules.Broadcast;
import com.CodCraft.api.modules.ScoreBoard;
import com.codcraft.lobby.Lobby;
import com.codcraft.lobby.LobbyModule;
import com.codcraft.tdm.CodCraftTDM;
import com.codcraft.tdm.TDMGame;

public class LobbyState implements GameState<CodCraftTDM> {

	private int duration;
	private TDMGame game;
	private BukkitTask task;
	
	protected Sign sign1 = null;
	protected Sign sign2 = null;
	protected Sign sign3 = null;
	protected Sign sign4 = null;
	protected Sign sign5 = null;
	protected Sign sign6 = null;
	protected Sign sign7 = null;
	protected Sign sign8 = null;
	protected Sign sign9 = null;
	protected Sign sign10 = null;
	protected Sign sign11 = null;
	protected Sign sign12 = null;
	

	public LobbyState(TDMGame game) {
		this.game = game;
		//System.out.println(""+game);
		//System.out.println(""+game.getName());

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
			        ScoreBoard SB = getGame().getPlugin().api.getModuleForClass(ScoreBoard.class);
			        int seconds = LobbyState.this.getGame().getCurrentState().getTimeLeft() % 60;
			        String seconds1 = "";
			        if (seconds < 10) {
			            seconds1 = "0" + seconds;
			        } else {
			            seconds1 = ""+seconds;
			        }
			        int minutes = LobbyState.this.getGame().getCurrentState().getTimeLeft() / 60;
			        SB.getObjectiveForGame(LobbyState.this.getGame()).setDisplayName(ChatColor.GRAY + "Pre Game " + minutes + ":" + seconds1);
					sign1 = (Sign) new Location(Bukkit.getWorld(game.getName()), 58, 43, 194).getBlock().getState();
					sign2 = (Sign) new Location(Bukkit.getWorld(game.getName()), 58, 43, 195).getBlock().getState();
					sign3 = (Sign) new Location(Bukkit.getWorld(game.getName()), 58, 43, 196).getBlock().getState();
					sign4 = (Sign) new Location(Bukkit.getWorld(game.getName()), 58, 42, 194).getBlock().getState();
					sign5 = (Sign) new Location(Bukkit.getWorld(game.getName()), 58, 42, 195).getBlock().getState();
					sign6 = (Sign) new Location(Bukkit.getWorld(game.getName()), 58, 42, 196).getBlock().getState();
					sign7 = (Sign) new Location(Bukkit.getWorld(game.getName()), 58, 41, 194).getBlock().getState();
					sign8 = (Sign) new Location(Bukkit.getWorld(game.getName()), 58, 41, 195).getBlock().getState();
					sign9 = (Sign) new Location(Bukkit.getWorld(game.getName()), 58, 41, 196).getBlock().getState();
					sign10 = (Sign) new Location(Bukkit.getWorld(game.getName()), 58, 40, 194).getBlock().getState();
					sign11 = (Sign) new Location(Bukkit.getWorld(game.getName()), 58, 40, 195).getBlock().getState();
					sign12 = (Sign) new Location(Bukkit.getWorld(game.getName()), 58, 40, 196).getBlock().getState();
					LobbyModule lm = getGame().getPlugin().api.getModuleForClass(LobbyModule.class);
					Lobby l = lm.getLobby(game.getName());
					if(l == null) {
						return;
					} else {
						lm.UpdateSign(lm.getLobby(getGame().getName()));
					}
					Random rnd = new Random();
					if (game.Map2 == null) {
						game.Map2 = game.getPlugin().maps.get(rnd.nextInt(game
								.getPlugin().maps.size()));
					}
					if (game.Map1 == null) {
						game.Map1 = game.getPlugin().maps.get(rnd.nextInt(game
								.getPlugin().maps.size()));
					}

					while (game.Map1.equalsIgnoreCase(game.Map2)) {
						game.Map1 = game.getPlugin().maps.get(rnd.nextInt(game
								.getPlugin().maps.size()));
					}
					updatesign();
					if(duration == 5) {
						Broadcast b = game.getPlugin().api.getModuleForClass(Broadcast.class);
						b.BroadCastMessage(getGame(), "Current Votes!");
						b.BroadCastMessage(getGame(), game.Map1+": "+ game.map1);
						b.BroadCastMessage(getGame(), game.Map2+": "+ game.map2);
					}
				} else {
					List<TeamPlayer> teamPlayers = new ArrayList<>();
					for (Team t : game.getTeams()) {
						for (TeamPlayer tp : t.getPlayers()) {
							teamPlayers.add(tp);
						}
					}
					if (teamPlayers.size() == 0) {
						duration = 60;
					} else {
						if (game.map1 > game.map2) {
							game.map = game.Map1;
						} else {
							game.map = game.Map2;
						}
						game.map1 = 0;
						game.map2 = 0;
						game.voters.clear();
						game.getPlugin().RespawnAll(game.map, game);
						getGame().setState(new InGameState(game));
					}
					
				}

			}
		};
		return run;
	}
	
	private void updatesign() {
		sign1.setLine(1, "");
		sign1.setLine(2, "");
		sign1.setLine(3, "");
		sign4.setLine(0, "");
		sign4.setLine(1, "");
		sign4.setLine(2, "");
		sign3.setLine(1, "");
		sign3.setLine(2, "");
		sign3.setLine(3, "");
		sign6.setLine(0, "");
		sign6.setLine(1, "");
		sign6.setLine(2, "");
		sign1.setLine(0, "Team1");
		sign3.setLine(0, "Team2");
		sign2.setLine(0, "Votes");
		sign5.setLine(1, game.Map1);
		sign5.setLine(2, ""+game.map1);
		sign8.setLine(1, game.Map2);
		sign8.setLine(2, ""+game.map2);
		sign11.setLine(0, "Timeleft");
		sign11.setLine(1, ""+duration);
		List<TeamPlayer> team1 = new ArrayList<>(game.getTeams().get(0).getPlayers());
		for(int i = 0; i < team1.size(); i++) {
			TeamPlayer tp = team1.get(i);
			if (i == 0) {
				sign1.setLine(1, tp.getName());
			} else if (i == 1) {
				sign1.setLine(2, tp.getName());
			} else if (i == 2) {
				sign1.setLine(3, tp.getName());
			} else if (i == 3) {
				sign4.setLine(0, tp.getName());
			} else if (i == 4) {
				sign4.setLine(1, tp.getName());
			} else if (i == 5) {
				sign4.setLine(2, tp.getName());
			}
		}
		List<TeamPlayer> team2 = new ArrayList<>(game.getTeams().get(1).getPlayers());
		for(int i = 0; i < team2.size(); i++) {
			TeamPlayer tp = team2.get(i);
			if (i == 0) {
				sign3.setLine(1, tp.getName());
			} else if (i == 1) {
				sign3.setLine(2, tp.getName());
			} else if (i == 2) {
				sign3.setLine(3, tp.getName());
			} else if (i == 3) {
				sign6.setLine(0, tp.getName());
			} else if (i == 4) {
				sign6.setLine(1, tp.getName());
			} else if (i == 5) {
				sign6.setLine(2, tp.getName());
			}
		}
		sign1.update();
		sign2.update();
		sign3.update();
		sign4.update();
		sign5.update();
		sign6.update();
		sign7.update();
		sign8.update();
		sign9.update();
		sign10.update();
		sign11.update();
		sign12.update();
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
		duration = 60;

		task = Bukkit.getScheduler().runTaskTimer(getGame().getPlugin(), getTask(), 0, 20);
		
	}

	@Override
	public void Stop() {
		if (task != null) {
			task.cancel();
		}
	}

}
