package com.codcraft.copsandrobber.states;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;
import org.kitteh.tag.TagAPI;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.GameState;
import com.CodCraft.api.model.Team;
import com.CodCraft.api.model.TeamPlayer;
import com.CodCraft.api.modules.Broadcast;
import com.codcraft.copsandrobber.CARGame;
import com.codcraft.copsandrobber.CopsARobber;
import com.codcraft.lobby.LobbyModule;

public class LobbyState implements GameState<CopsARobber> {
	/**
	 * Durration of how long the state runs for.
	 */
	private int duration = 30;
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
	 * @param CARGame
	 * 			- sets the game.
	 */
	public LobbyState(CARGame g) {
		setGame(g);
	}
	/**
	 * ID for the state
	 */	
	@Override
	public String getId() {
		return "C&Rlobby";
	}
	/**
	 * Sets how much time is left
	 */
	@Override
	public void setTimeLeft(int duration) {
		this.duration = duration;
	}
	/**
	 * Gets how much time is left
	 */
	@Override
	public int getTimeLeft() {
		return duration;
	}
	/**
	 * Runnable for task
	 */
	@Override
	public Runnable getTask() {
		Runnable run = new Runnable() {
			
			@Override
			public void run() {
				if(duration >= 1) {
					duration--;
					LobbyModule lm = getGame().getPlugin().api.getModuleForClass(LobbyModule.class);
					lm.UpdateSign(lm.getLobby(getGame().getName()));
					for(Team t : g.getTeams()) {
						for(TeamPlayer tp : t.getPlayers()) {
							Player p = Bukkit.getPlayer(tp.getName());
							if(p != null) {
								p.setLevel(duration);
							}
						}
					}
					for(String s : getGame().inlobby) {
						Player p = Bukkit.getPlayer(s);
						if(p != null) {
							p.setLevel(duration);
						}
					}
					if(duration == 5) {
						if(getGame().inlobby.size() >= 3) {
							for(String s : getGame().inlobby) {
								Player p = Bukkit.getPlayer(s);
								if(p != null){
									int size = getGame().findTeamWithName("cops").getPlayers().size() * 3;
										getGame().findTeamWithName("robbers").addPlayer(p);
										p.teleport(new Location(getGame().lobbyspawn.getWorld(), 2, 2, 2));
									if(size > getGame().findTeamWithName("robbers").getPlayers().size()) {
										getGame().findTeamWithName("cops").addPlayer(p);
										p.teleport(new Location(getGame().lobbyspawn.getWorld(), 2, 2, -1));
										p.getInventory().addItem(new ItemStack(Material.COMPASS));
									}
									p.sendMessage("You are on " + getGame().findTeamWithPlayer(p).getName());
									TagAPI.refreshPlayer(p);
								}
							}
							getGame().inlobby.clear();
						} else {
							Broadcast b = getGame().getPlugin().api.getModuleForClass(Broadcast.class);
							b.BroadCastMessage(getGame().injail, "You must have atleast 3 people in the game to start!");
							duration = 30;
						}
					}
				} else {
					for(Team t : g.getTeams()) {
						if(t.getName().equalsIgnoreCase("cops")) {
							for(TeamPlayer tp : t.getPlayers()) {
								Player p = Bukkit.getPlayer(tp.getName());
								if(p != null){
									p.teleport(new Location(g.jail.getWorld(), 134, 10, -269));
								}
							}
							Bukkit.getScheduler().runTaskLater(getGame().getPlugin(), new Runnable() {
								
								@Override
								public void run() {
									Location loc1 = new Location(getGame().jail.getWorld(), 135, 8, -262);
									Location loc2 = new Location(loc1.getWorld(), 134, 8, -262);
									Location loc3 = new Location(loc1.getWorld(), 133, 8, -262);
									loc1.getBlock().setType(Material.AIR);
									loc2.getBlock().setType(Material.AIR);
									loc3.getBlock().setType(Material.AIR);
								}
							}, 600);
						} else {
							for(TeamPlayer tp : t.getPlayers()) {
								Player p = Bukkit.getPlayer(tp.getName());
								if(p != null){
									p.teleport(new Location(g.jail.getWorld(), 114, 8, -234));
								}
							}
						}
					}
					getGame().setState(new InGameState(getGame()));
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
		return g;
	}
	/**
	 * Starts the states
	 */
	@Override
	public void Start() {
		duration = 30;
		task = Bukkit.getScheduler().runTaskTimer(g.getPlugin(), getTask(), 20, 20);
	}
	/**
	 * Stops the states
	 */
	@Override
	public void Stop() {
		if(task != null) {
			task.cancel();
		}
	}
}
