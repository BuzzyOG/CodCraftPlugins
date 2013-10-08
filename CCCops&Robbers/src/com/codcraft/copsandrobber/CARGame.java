package com.codcraft.copsandrobber;



import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import com.CodCraft.api.event.GameWinEvent;
import com.CodCraft.api.model.Game;
import com.CodCraft.api.model.GameState;
import com.CodCraft.api.model.Team;
import com.codcraft.copsandrobber.states.InGameState;
import com.codcraft.copsandrobber.states.LobbyState;

/**
 * Game class for Cops&Robber
 *
 */

public class CARGame extends Game<CopsARobber> {
	/**
	 * Default Constructor
	 * 
	 * @param instance
	 * 				- Instance to sent to superclass
	 */
	public CARGame(CopsARobber instance) {
		super(instance);
	}
	/**
	 * List if everyone in jail for current game
	 */
	public List<String> injail = new ArrayList<>();
	/**
	 * List of everyone in lobby for current game
	 */
	public List<String> inlobby = new ArrayList<>();
	/**
	 * Top position of Jail
	 */
	public Location jailtop;
	/**
	 * Bottom Location of jail
	 */
	public Location jailbottom;
	/**
	 * Location for people to teleport for the lobby
	 */
	public Location lobbyspawn;
	/**
	 * Location of spawnpoint for jail
	 */
	public Location jail;
	/**
	 * initializes the game. Sets the 2 teams, makes the world, sets the location
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void initialize() {
		Bukkit.createWorld(new WorldCreator(getName()));
		Team cops = new Team("cops");
		cops.setMaxPlayers(12);
		cops.setColor(ChatColor.DARK_BLUE);
		addTeam(cops);
		Team robbers = new Team("robbers");
		robbers.setMaxPlayers(12);
		robbers.setColor(ChatColor.DARK_RED);
		addTeam(robbers);
		lobbyspawn = new Location(Bukkit.getWorld(getName()), getPlugin().lobbyspawn.getX(), getPlugin().lobbyspawn.getY(), getPlugin().lobbyspawn.getZ());
		knownStates.put(new LobbyState(this).getId(), new LobbyState(this));
		knownStates.put(new InGameState(this).getId(), new InGameState(this));
		setState(new LobbyState(this));
		jail = new Location(lobbyspawn.getWorld(), 103, 8, -245);
		jailtop = new Location(lobbyspawn.getWorld(), 118, 30, -259);
		jailbottom = new Location(lobbyspawn.getWorld(), 89, 5, -230);
	}
	/**
	 * deinitialize the game.
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void deinitialize() {
		Location loc1 = new Location(jail.getWorld(), 135, 8, -262);
		Location loc2 = new Location(loc1.getWorld(), 134, 8, -262);
		Location loc3 = new Location(loc1.getWorld(), 133, 8, -262);
		loc1.getBlock().setTypeId(101);
		loc2.getBlock().setTypeId(101);
		loc3.getBlock().setTypeId(101);
		for(Team t : getTeams()) {
			removeTeam(t);
		}
		injail.clear();
	}
	/**
	 * Unused
	 */
	@Override
	public void preStateSwitch(GameState<CopsARobber> state) {
	}
	/**
	 * Unused
	 */
	@Override
	public void postStateSwitch(GameState<CopsARobber> state) {
	}
	/**
	 * Detects which team won the game
	 */
	public void DetectWin() {
		GameWinEvent event = null;
		if(getTeams().get(1).getPlayers().size() == injail.size()) {
			event = new GameWinEvent(getTeams().get(0).getName()+" have won!", getTeams().get(0), this);
		} else {
			event = new GameWinEvent(getTeams().get(1).getName()+" have won!", getTeams().get(1), this);
		}
		Bukkit.getPluginManager().callEvent(event);
	}
	/**
	 * Checks weather the person is in jail.
	 * 
	 * @param p
	 * 		- Player being checked
	 * @return boolean
	 * 			- Wether in or not.
	 */
	public boolean isInJail(Player p) {
		Location loc = p.getLocation();
		double minX;
		double maxX;
		double maxZ;
		double minZ;
		double maxY;
		double minY;
		if(jailtop.getX() > jailbottom.getX()) {
			maxX = jailtop.getX();
			minX = jailbottom.getX();
		} else {
			maxX = jailbottom.getX();
			minX = jailtop.getX();
		}
		if(jailtop.getY() > jailbottom.getY()) {
			maxY = jailtop.getY();
			minY = jailbottom.getY();
		} else {
			maxY = jailbottom.getY();
			minY = jailtop.getY();
		}
		if(jailtop.getZ() > jailbottom.getZ()) {
			maxZ = jailtop.getZ();
			minZ = jailbottom.getZ();
		} else {
			maxZ = jailbottom.getZ();
			minZ = jailtop.getZ();
		}
		
		if(loc.getX() < minX || loc.getX() > maxX)
			   return false;  
		if(loc.getZ() < minZ || loc.getZ() > maxZ)
			   return false;
		if(loc.getY() < minY || loc.getY() > maxY)
			   return false;   
		return true;
		
	}

}
