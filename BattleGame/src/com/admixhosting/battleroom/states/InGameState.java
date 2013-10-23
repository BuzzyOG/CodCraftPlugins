package com.admixhosting.battleroom.states;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import com.CodCraft.api.event.GameWinEvent;
import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.GameState;
import com.CodCraft.api.model.Team;
import com.CodCraft.api.model.TeamPlayer;
import com.CodCraft.api.modules.ScoreBoard;
import com.admixhosting.battleroom.game.BattleGame;
import com.admixhosting.battleroom.game.BattlePlayer;
import com.codcraft.lobby.Lobby;
import com.codcraft.lobby.LobbyModule;

public class InGameState implements GameState {

	private int duration;
	private BukkitTask task;
	private BattleGame game;
	private int time;

	public InGameState(Game<?> game) {
		this.game = (BattleGame) game;
	}

	@Override
	public String getId() {
		return "InGame";
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
				if(duration == time) {
					game.requestedTeams.clear();
					game.getInLobby().clear();
					if(!game.isFreezeTag()) {
						/*new Location(Bukkit.getWorld(getGame().getName()), -392, 125, 511).getBlock().setType(Material.AIR);
						new Location(Bukkit.getWorld(getGame().getName()), -392, 126, 511).getBlock().setType(Material.AIR);
						new Location(Bukkit.getWorld(getGame().getName()), -392, 127, 511).getBlock().setType(Material.AIR);
						new Location(Bukkit.getWorld(getGame().getName()), -392, 128, 511).getBlock().setType(Material.AIR);
						new Location(Bukkit.getWorld(getGame().getName()), -393, 126, 511).getBlock().setType(Material.AIR);
						new Location(Bukkit.getWorld(getGame().getName()), -393, 127, 511).getBlock().setType(Material.AIR);
						new Location(Bukkit.getWorld(getGame().getName()), -394, 125, 511).getBlock().setType(Material.AIR);
						new Location(Bukkit.getWorld(getGame().getName()), -394, 126, 511).getBlock().setType(Material.AIR);
						new Location(Bukkit.getWorld(getGame().getName()), -394, 127, 511).getBlock().setType(Material.AIR);
						new Location(Bukkit.getWorld(getGame().getName()), -394, 128, 511).getBlock().setType(Material.AIR);
						new Location(Bukkit.getWorld(getGame().getName()), -395, 126, 511).getBlock().setType(Material.AIR);
						new Location(Bukkit.getWorld(getGame().getName()), -395, 127, 511).getBlock().setType(Material.AIR);
						new Location(Bukkit.getWorld(getGame().getName()), -396, 125, 511).getBlock().setType(Material.AIR);
						new Location(Bukkit.getWorld(getGame().getName()), -396, 126, 511).getBlock().setType(Material.AIR);
						new Location(Bukkit.getWorld(getGame().getName()), -396, 127, 511).getBlock().setType(Material.AIR);
						new Location(Bukkit.getWorld(getGame().getName()), -396, 128, 511).getBlock().setType(Material.AIR);
						
						new Location(Bukkit.getWorld(getGame().getName()), -392, 125, 409).getBlock().setType(Material.AIR);
						new Location(Bukkit.getWorld(getGame().getName()), -392, 126, 409).getBlock().setType(Material.AIR);
						new Location(Bukkit.getWorld(getGame().getName()), -392, 127, 409).getBlock().setType(Material.AIR);
						new Location(Bukkit.getWorld(getGame().getName()), -392, 128, 409).getBlock().setType(Material.AIR);
						new Location(Bukkit.getWorld(getGame().getName()), -393, 126, 409).getBlock().setType(Material.AIR);
						new Location(Bukkit.getWorld(getGame().getName()), -393, 127, 409).getBlock().setType(Material.AIR);
						new Location(Bukkit.getWorld(getGame().getName()), -394, 125, 409).getBlock().setType(Material.AIR);
						new Location(Bukkit.getWorld(getGame().getName()), -394, 126, 409).getBlock().setType(Material.AIR);
						new Location(Bukkit.getWorld(getGame().getName()), -394, 127, 409).getBlock().setType(Material.AIR);
						new Location(Bukkit.getWorld(getGame().getName()), -394, 128, 409).getBlock().setType(Material.AIR);
						new Location(Bukkit.getWorld(getGame().getName()), -395, 126, 409).getBlock().setType(Material.AIR);
						new Location(Bukkit.getWorld(getGame().getName()), -395, 127, 409).getBlock().setType(Material.AIR);
						new Location(Bukkit.getWorld(getGame().getName()), -396, 125, 409).getBlock().setType(Material.AIR);
						new Location(Bukkit.getWorld(getGame().getName()), -396, 126, 409).getBlock().setType(Material.AIR);
						new Location(Bukkit.getWorld(getGame().getName()), -396, 127, 409).getBlock().setType(Material.AIR);
						new Location(Bukkit.getWorld(getGame().getName()), -396, 128, 409).getBlock().setType(Material.AIR);*/
					}

					
					for(Team t : getGame().getTeams()) {
						for(TeamPlayer tp : t.getPlayers()) {
							Player p = Bukkit.getPlayer(tp.getName());
							if(p != null) {
								if(t.getName().equalsIgnoreCase("Blue")) {
									p.getInventory().setItem(9 ,new ItemStack(Material.WOOL, 1, (short) 11));
								} else {
									p.getInventory().setItem(9, new ItemStack(Material.WOOL, 1, (short) 14));
								}
							}
						}
					}
				}
				if(duration > 0) {
					duration--;
					if(duration == time - 15) {
						for(Team t : game.getTeams()) {
							for(TeamPlayer tp : t.getPlayers()) {
								Player p = Bukkit.getPlayer(tp.getName());
								if(p != null) {
									if(game.getPlugin().checkBlue(p) || game.getPlugin().checkRed(p)) {
										t.removePlayer(p);
										((BattlePlayer) tp).setFrozen(false);
										((BattlePlayer) tp).setOnWall(false);
										((BattlePlayer) tp).setOldLoc(null);
										game.getPlugin().api.getModuleForClass(ScoreBoard.class).removePlayerFromScoreBoard(Bukkit.getPlayer(tp.getName()));
										Bukkit.getPlayer(tp.getName()).getInventory().setHelmet(null);
										p.performCommand("lobby");
										p.getInventory().clear();
									}
								}
							}
						}
					}
					LobbyModule lm = game.getPlugin().api.getModuleForClass(LobbyModule.class);
					Lobby l = lm.getLobby(game.getName());

					if(l == null) {
						return;
					} else {
						lm.UpdateSign(lm.getLobby(getGame().getName()));
					}
					int red = 0;
					int redF = 0;
					int blue = 0;
					int blueF = 0;
					boolean inHoldB = false;
					boolean inHoldR = false;
					for(Team t : getGame().getTeams()) {

						for(TeamPlayer tp : t.getPlayers()) {
							BattlePlayer bp = (BattlePlayer) tp;
							Player p = Bukkit.getPlayer(tp.getName());
							if(p != null) {
								p.setLevel(duration);
								if(t.getName().equalsIgnoreCase("Blue")) {
									if(bp.getFrozen() || bp.isPermfrozen()) {
										p.getInventory().setHelmet(new ItemStack(Material.WOOL, 1, (short) 3));
									} else {
										p.getInventory().setHelmet(new ItemStack(Material.WOOL, 1, (short) 11));
									}
								} else {
									if(bp.getFrozen() || bp.isPermfrozen()) {
										p.getInventory().setHelmet(new ItemStack(Material.WOOL, 1, (short) 6));
									} else {
										p.getInventory().setHelmet(new ItemStack(Material.WOOL, 1, (short) 14));
									}

								}
								p.getInventory().remove(Material.WOOL);
								if(t.getName().equalsIgnoreCase("Blue")) {
									p.getInventory().setItem(8, new ItemStack(Material.WOOL, 1, (short) 11));
									p.updateInventory();
								} else {
									p.getInventory().setItem(8, new ItemStack(Material.WOOL, 1, (short) 14));
									p.updateInventory();
								}
								if(t.getName().equalsIgnoreCase("Blue")) {
									if(game.getPlugin().checkBlue(p)) {
										inHoldB = true;
									}
									if(bp.getFrozen() || bp.isPermfrozen()) {
										blueF++;
									}
									blue++;
								} else {
									if(game.getPlugin().checkRed(p)) {
										inHoldR = true;
									}
									if(bp.getFrozen() || bp.isPermfrozen()) {
										redF++;
									}
									red++;
								}
							}
							
							
						}
					}
					if(!inHoldB) {
						if(!game.isFreezeTag()) {
							/*new Location(Bukkit.getWorld(getGame().getName()), -392, 125, 511).getBlock().setType(Material.GLASS);
							new Location(Bukkit.getWorld(getGame().getName()), -392, 126, 511).getBlock().setType(Material.GLASS);
							new Location(Bukkit.getWorld(getGame().getName()), -392, 127, 511).getBlock().setType(Material.GLASS);
							new Location(Bukkit.getWorld(getGame().getName()), -392, 128, 511).getBlock().setType(Material.GLASS);
							new Location(Bukkit.getWorld(getGame().getName()), -393, 126, 511).getBlock().setType(Material.GLASS);
							new Location(Bukkit.getWorld(getGame().getName()), -393, 127, 511).getBlock().setType(Material.GLASS);
							new Location(Bukkit.getWorld(getGame().getName()), -394, 125, 511).getBlock().setType(Material.GLASS);
							new Location(Bukkit.getWorld(getGame().getName()), -394, 126, 511).getBlock().setType(Material.GLASS);
							new Location(Bukkit.getWorld(getGame().getName()), -394, 127, 511).getBlock().setType(Material.GLASS);
							new Location(Bukkit.getWorld(getGame().getName()), -394, 128, 511).getBlock().setType(Material.GLASS);
							new Location(Bukkit.getWorld(getGame().getName()), -395, 126, 511).getBlock().setType(Material.GLASS);
							new Location(Bukkit.getWorld(getGame().getName()), -395, 127, 511).getBlock().setType(Material.GLASS);
							new Location(Bukkit.getWorld(getGame().getName()), -396, 125, 511).getBlock().setType(Material.GLASS);
							new Location(Bukkit.getWorld(getGame().getName()), -396, 126, 511).getBlock().setType(Material.GLASS);
							new Location(Bukkit.getWorld(getGame().getName()), -396, 127, 511).getBlock().setType(Material.GLASS);
							new Location(Bukkit.getWorld(getGame().getName()), -396, 128, 511).getBlock().setType(Material.GLASS);*/
						}

					}
					if(!inHoldR) {
						if(!game.isFreezeTag()) {
							/*new Location(Bukkit.getWorld(getGame().getName()), -392, 125, 409).getBlock().setType(Material.GLASS);
							new Location(Bukkit.getWorld(getGame().getName()), -392, 126, 409).getBlock().setType(Material.GLASS);
							new Location(Bukkit.getWorld(getGame().getName()), -392, 127, 409).getBlock().setType(Material.GLASS);
							new Location(Bukkit.getWorld(getGame().getName()), -392, 128, 409).getBlock().setType(Material.GLASS);
							new Location(Bukkit.getWorld(getGame().getName()), -393, 126, 409).getBlock().setType(Material.GLASS);
							new Location(Bukkit.getWorld(getGame().getName()), -393, 127, 409).getBlock().setType(Material.GLASS);
							new Location(Bukkit.getWorld(getGame().getName()), -394, 125, 409).getBlock().setType(Material.GLASS);
							new Location(Bukkit.getWorld(getGame().getName()), -394, 126, 409).getBlock().setType(Material.GLASS);
							new Location(Bukkit.getWorld(getGame().getName()), -394, 127, 409).getBlock().setType(Material.GLASS);
							new Location(Bukkit.getWorld(getGame().getName()), -394, 128, 409).getBlock().setType(Material.GLASS);
							new Location(Bukkit.getWorld(getGame().getName()), -395, 126, 409).getBlock().setType(Material.GLASS);
							new Location(Bukkit.getWorld(getGame().getName()), -395, 127, 409).getBlock().setType(Material.GLASS);
							new Location(Bukkit.getWorld(getGame().getName()), -396, 125, 409).getBlock().setType(Material.GLASS);
							new Location(Bukkit.getWorld(getGame().getName()), -396, 126, 409).getBlock().setType(Material.GLASS);
							new Location(Bukkit.getWorld(getGame().getName()), -396, 127, 409).getBlock().setType(Material.GLASS);
							new Location(Bukkit.getWorld(getGame().getName()), -396, 128, 409).getBlock().setType(Material.GLASS);*/
						}
					}
					ScoreBoard sb = game.getPlugin().api.getModuleForClass(ScoreBoard.class);
					sb.getObjectiveForGame(getGame()).setDisplayName(getId() + ": " + duration);
					sb.setStringScoreForBoard("Red", red, getGame());
					sb.setStringScoreForBoard("Red_Frozen", redF, getGame());
					sb.setStringScoreForBoard("Blue", blue, getGame());
					sb.setStringScoreForBoard("Blue_Frozen", blueF, getGame());
					
				} else {
					int redS = 0;
					int blueS = 0;
					Team blue = null;
					Team red = null;
					for(Team t : getGame().getTeams()) {
						if(t.getName().equalsIgnoreCase("Blue")) {
							blue = t;
						} else {
							red = t;
						}
						for(TeamPlayer tp : t.getPlayers()) {
							BattlePlayer bp = (BattlePlayer) tp;
							if(bp.getFrozen()) {
								if(t.getName().equalsIgnoreCase("Blue")) {
									blueS++;
								} else {
									redS++;
								}
							}
						}
					}
					GameWinEvent event1 = null;
					if(redS > blueS) {
						event1 = new GameWinEvent(blue.getName() + " team won the game", blue, getGame());
					} else if(blueS > redS) {
						event1 = new GameWinEvent(red.getName() + " team won the game", red, getGame());
					} else {
						event1 = new GameWinEvent("Tie no team has won", null, getGame());
					}
					
					Bukkit.getPluginManager().callEvent(event1);
					Bukkit.broadcastMessage(game.getName() + "has finished!");
					game.setState(new LobbyState(getGame()));
					if(!game.isFreezeTag()) {
						/*new Location(Bukkit.getWorld(getGame().getName()), -392, 125, 409).getBlock().setType(Material.GLASS);
						new Location(Bukkit.getWorld(getGame().getName()), -392, 126, 409).getBlock().setType(Material.GLASS);
						new Location(Bukkit.getWorld(getGame().getName()), -392, 127, 409).getBlock().setType(Material.GLASS);
						new Location(Bukkit.getWorld(getGame().getName()), -392, 128, 409).getBlock().setType(Material.GLASS);
						new Location(Bukkit.getWorld(getGame().getName()), -393, 126, 409).getBlock().setType(Material.GLASS);
						new Location(Bukkit.getWorld(getGame().getName()), -393, 127, 409).getBlock().setType(Material.GLASS);
						new Location(Bukkit.getWorld(getGame().getName()), -394, 125, 409).getBlock().setType(Material.GLASS);
						new Location(Bukkit.getWorld(getGame().getName()), -394, 126, 409).getBlock().setType(Material.GLASS);
						new Location(Bukkit.getWorld(getGame().getName()), -394, 127, 409).getBlock().setType(Material.GLASS);
						new Location(Bukkit.getWorld(getGame().getName()), -394, 128, 409).getBlock().setType(Material.GLASS);
						new Location(Bukkit.getWorld(getGame().getName()), -395, 126, 409).getBlock().setType(Material.GLASS);
						new Location(Bukkit.getWorld(getGame().getName()), -395, 127, 409).getBlock().setType(Material.GLASS);
						new Location(Bukkit.getWorld(getGame().getName()), -396, 125, 409).getBlock().setType(Material.GLASS);
						new Location(Bukkit.getWorld(getGame().getName()), -396, 126, 409).getBlock().setType(Material.GLASS);
						new Location(Bukkit.getWorld(getGame().getName()), -396, 127, 409).getBlock().setType(Material.GLASS);
						new Location(Bukkit.getWorld(getGame().getName()), -396, 128, 409).getBlock().setType(Material.GLASS);
						
						new Location(Bukkit.getWorld(getGame().getName()), -392, 125, 511).getBlock().setType(Material.GLASS);
						new Location(Bukkit.getWorld(getGame().getName()), -392, 126, 511).getBlock().setType(Material.GLASS);
						new Location(Bukkit.getWorld(getGame().getName()), -392, 127, 511).getBlock().setType(Material.GLASS);
						new Location(Bukkit.getWorld(getGame().getName()), -392, 128, 511).getBlock().setType(Material.GLASS);
						new Location(Bukkit.getWorld(getGame().getName()), -393, 126, 511).getBlock().setType(Material.GLASS);
						new Location(Bukkit.getWorld(getGame().getName()), -393, 127, 511).getBlock().setType(Material.GLASS);
						new Location(Bukkit.getWorld(getGame().getName()), -394, 125, 511).getBlock().setType(Material.GLASS);
						new Location(Bukkit.getWorld(getGame().getName()), -394, 126, 511).getBlock().setType(Material.GLASS);
						new Location(Bukkit.getWorld(getGame().getName()), -394, 127, 511).getBlock().setType(Material.GLASS);
						new Location(Bukkit.getWorld(getGame().getName()), -394, 128, 511).getBlock().setType(Material.GLASS);
						new Location(Bukkit.getWorld(getGame().getName()), -395, 126, 511).getBlock().setType(Material.GLASS);
						new Location(Bukkit.getWorld(getGame().getName()), -395, 127, 511).getBlock().setType(Material.GLASS);
						new Location(Bukkit.getWorld(getGame().getName()), -396, 125, 511).getBlock().setType(Material.GLASS);
						new Location(Bukkit.getWorld(getGame().getName()), -396, 126, 511).getBlock().setType(Material.GLASS);
						new Location(Bukkit.getWorld(getGame().getName()), -396, 127, 511).getBlock().setType(Material.GLASS);
						new Location(Bukkit.getWorld(getGame().getName()), -396, 128, 511).getBlock().setType(Material.GLASS);*/
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
		int time = 0;
		for(Team t : getGame().getTeams()) {
			for(@SuppressWarnings("unused") TeamPlayer tp : t.getPlayers()) {
				time += 30;
			}
		}
		duration = time;
		this.time = time;
		task = Bukkit.getScheduler().runTaskTimer(getGame().getPlugin(), getTask(), 0, 20);	
	}

	@Override
	public void Stop() {
		if(task != null) {
			task.cancel();
		}
	}

}
