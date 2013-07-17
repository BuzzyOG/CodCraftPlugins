package com.codcraft.copsandrobber.states;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.GameState;
import com.CodCraft.api.model.Team;
import com.CodCraft.api.model.TeamPlayer;
import com.codcraft.copsandrobber.CARGame;
import com.codcraft.copsandrobber.CopsARobber;
import com.codcraft.lobby.LobbyModule;

public class InGameState implements GameState<CopsARobber> {
	/**
	 * Durration of how long the state runs for.
	 */
	private int durration = 30;
	/**
	 * Game instance
	 */
	private CARGame g;
	/**
	 * Task Running
	 */
	private BukkitTask task;
	/**
	 * Default constructor
	 * @param carGame
	 * 			- sets the game.
	 */
	public InGameState(CARGame carGame) {
		setGame(carGame);
	}
	/**
	 * ID for the state
	 */
	@Override
	public String getId() {
		return "C&RInGame";
	}
	/**
	 * Sets how much time is left
	 */
	@Override
	public void setTimeLeft(int duration) {
		this.durration = duration;
		
	}
	/**
	 * Gets how much time is left
	 */
	@Override
	public int getTimeLeft() {
		return durration;
	}
	/**
	 * Runnable for task
	 */
	@Override
	public Runnable getTask() {
		Runnable run = new Runnable() {
			
			@Override
			public void run() {
				if(durration >= 1) {
					if(durration % 2 == 0) {
						for(TeamPlayer tp : getGame().findTeamWithName("cops").getPlayers()) {
							Player p = Bukkit.getPlayer(tp.getName());
							if(p != null) {
								List<Entity> locations = p.getNearbyEntities(500, 265, 500);
								if(!locations.isEmpty()) {
									Location myLocation = p.getLocation();
									Location closest = locations.get(0).getLocation();
									double closestDist = closest.distance(myLocation);
									for (Entity loc : locations) {
										if(loc instanceof Player) {
											Player p1 = (Player) loc;
											if(g.findTeamWithPlayer(p1) != null) {
												if(g.findTeamWithPlayer(p1).getName().equalsIgnoreCase("robbers")) {
													if (loc.getLocation().distance(myLocation) < closestDist) {
														closestDist = loc.getLocation().distance(myLocation);
														closest = loc.getLocation();
													}
												}
											}	
										}
									}
									p.setCompassTarget(closest);
								}
								
							}
						}
					}
					durration--;
					LobbyModule lm = getGame().getPlugin().api.getModuleForClass(LobbyModule.class);
					lm.UpdateSign(lm.getLobby(getGame().getName()));
					for(Team t : g.getTeams()) {
						for(TeamPlayer tp : t.getPlayers()) {
							Player p = Bukkit.getPlayer(tp.getName());
							if(p != null) {
								p.setLevel(durration);
							}
						}
					}
					for(String s : g.injail) {
						Player p = Bukkit.getPlayer(s);
						if(p == null) {
							getGame().injail.remove(s);
						} else {
							if(!getGame().isInJail(p)) {
								p.teleport(getGame().jail);
							}
						}

					}
				} else {
					getGame().DetectWin();

					getGame().setState(new LobbyState(getGame()));
				}
			}
		};
		return run;
	}
	/**
	 * Sets the game
	 */
	@Override
	public void setGame(Game<CopsARobber> game) {
		this.g = (CARGame) game;
		
	}
	/**
	 * Gets the game
	 */
	@Override
	public CARGame getGame() {
		return (CARGame) g;
	}
	/**
	 * Starts the state
	 */
	@Override
	public void Start() {
		durration = 600;
		task = Bukkit.getScheduler().runTaskTimer(g.getPlugin(), getTask(), 20, 20);
	}
	/**
	 * Stops the task
	 */
	@Override
	public void Stop() {
		if(task != null) {
			task.cancel();
		}
	}
}
