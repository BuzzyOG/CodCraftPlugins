package com.admixhosting.battleroom.states;

import java.util.Map.Entry;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;
import org.kitteh.tag.TagAPI;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.GameState;
import com.CodCraft.api.model.Team;
import com.CodCraft.api.model.TeamPlayer;
import com.CodCraft.api.modules.ScoreBoard;
import com.admixhosting.battleroom.game.BattleGame;
import com.admixhosting.battleroom.game.BattlePlayer;
import com.codcraft.lobby.Lobby;
import com.codcraft.lobby.LobbyModule;

public class LobbyState implements GameState {

	private int duration;
	private BattleGame game;
	private BukkitTask task;
	private final int min_players = 0;

	public LobbyState(Game<?> g) {
		this.game = (BattleGame) g;
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
			
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				//ScoreBoard SB = getGame().getPlugin().api.getModuleForClass(ScoreBoard.class);
				LobbyModule lm = game.getPlugin().api.getModuleForClass(LobbyModule.class);
				Lobby l = lm.getLobby(game.getName());
				
				for(String s : game.getInLobby()) {
					Player p = Bukkit.getPlayer(s);
					if(p != null) {
						/*if(game.isFreezeTag()) {
							if(game.requestedTeams.get(s).getName().equalsIgnoreCase("Blue")) {
								if(!game.getPlugin().checkBlueFreeze(p)) {
									p.teleport(((BattleTeam)game.requestedTeams.get(s)).getSpawn());
								}
							} else {
								if(!game.getPlugin().checkRedFreeze(p)) {
									p.teleport(((BattleTeam)game.requestedTeams.get(s)).getSpawn());
								}
							}
						}*/
					}


				}

				if(l == null) {
					return;
				} else {
					lm.UpdateSign(lm.getLobby(getGame().getName()));
				}
				int players = game.getInLobby().size();
				if(players > min_players) {
					duration--;
					game.updateGUI();
					ScoreBoard sb = game.getPlugin().api.getModuleForClass(ScoreBoard.class);
					int red = 0;
					int blue = 0;
					for(Entry<String, Team> en : game.requestedTeams.entrySet()) {
						Player p = Bukkit.getPlayer(en.getKey());
						if(p != null) {
							
							if(en.getValue().getName().equalsIgnoreCase("Blue")) {
								p.getInventory().setHelmet(new ItemStack(Material.WOOL, 1, (short) 11));
							} else {
								p.getInventory().setHelmet(new ItemStack(Material.WOOL, 1, (short) 14));
							}
							p.getInventory().remove(Material.WOOL);
							if(en.getValue().getName().equalsIgnoreCase("Blue")) {
								p.getInventory().setItem(8, new ItemStack(Material.WOOL, 1, (short) 11));
								p.updateInventory();
							} else {
								p.getInventory().setItem(8, new ItemStack(Material.WOOL, 1, (short) 14));
								p.updateInventory();
							}
						}

						if(en.getValue().getName().equalsIgnoreCase("Blue")) {
							blue++;
						} else {
							red++;
						}
					}
					sb.getObjectiveForGame(getGame()).setDisplayName("Game starts in " + duration);
					sb.setStringScoreForBoard(ChatColor.DARK_RED+"Red", red, getGame());
					sb.setStringScoreForBoard(ChatColor.RED+"Red_Frozen", 0, getGame());
					sb.setStringScoreForBoard(ChatColor.DARK_BLUE+"Blue", blue, getGame());
					sb.setStringScoreForBoard(ChatColor.BLUE+"Blue_Frozen", 0, getGame());
					if(duration < 5) {
						for(String name : game.getInLobby()) {
							Player p = Bukkit.getPlayer(name);
							if(p != null) {
								p.playSound(p.getLocation(), Sound.CLICK, 1, 0);
							}
						}
					}
					if(duration == 0) {
						if(game.getCurrentmap() == null) {
							game.setCurrentmap("LoadingBay");
						}
						/*for(Team t : game.getTeams()) {
							BattleTeam team = (BattleTeam) t; 
							if(team.getName().equalsIgnoreCase("Blue")) {
								team.setSpawn(game.bluespawn.get(game.getCurrentmap()));
							} else {
								team.setSpawn(game.redspawn.get(game.getCurrentmap()));
							}
						}*/
						for(String s : game.getInLobby()) {
							Player p = Bukkit.getPlayer(s);
							if(p != null) {
								p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 0);
								if(game.requestedTeams.containsKey(p.getName())) {
									game.findTeamWithId(game.requestedTeams.get(s).getId()).addPlayer(new BattlePlayer(p.getName()));

									
								}
							}
						}
						for(Team t : getGame().getTeams()) {
							if(t.getPlayers().size() != 0) {
								int r = new Random().nextInt(t.getPlayers().size());
								
								int i = 0;
								for(TeamPlayer tp : t.getPlayers()) {
									if(i == r) {
										((BattlePlayer)tp).medic = true;
									}
									i++;
								}
							}
	
						}
						for(Team t : getGame().getTeams()) {
							for(TeamPlayer tp : t.getPlayers()) {
								BattlePlayer bp = (BattlePlayer) tp;
								if(bp.medic) {
									for(TeamPlayer tp1 : t.getPlayers()) {
										Player p = Bukkit.getPlayer(tp1.getName());
										if(p != null) {
											
											p.sendMessage(t.getColor() + tp.getName() + ChatColor.WHITE + " is a defroster");
										}
									}
								}
							}
						}
						getGame().setState(new InGameState(getGame()));
						for(Team t : getGame().getTeams()) {
							for(TeamPlayer tp : t.getPlayers()) {
								Player p = Bukkit.getPlayer(tp.getName());
								if(p != null) {
									TagAPI.refreshPlayer(p);
								}
							}
						}
						
						game.requestedTeams.clear();
						game.getInLobby().clear();
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
				
			}
		};
		return run;
	}

	@Override
	public void setGame(Game<?> game) {
		this.game = (BattleGame) game;
		
	}

	@Override
	public Game<?> getGame() {
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
