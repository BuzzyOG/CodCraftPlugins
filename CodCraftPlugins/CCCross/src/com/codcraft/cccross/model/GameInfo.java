package com.codcraft.cccross.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.plugin.Plugin;

public class GameInfo implements Serializable {
	

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6340182105008059420L;

	/**
	 * The ID of the game this ID should really not change... 
	 * But because of a Deprecated method that allows of change it, this id is not final.
	 */
	private String uuid = UUID.randomUUID().toString();
	
	/**
	 * The name of the game
	 */
	private String name;
	/**
	 * A list containing the game player names. 
	 * At this point the data does not need the player score, 
	 * But for possible TODO to make it be TeamPlayer instead of String
	 */
	private List<String> playernames = new ArrayList<>();
	/**
	 * The name of the plugin that the game is using
	 */
	private final String pluginName;
	/**
	 * The current Game state name that is set in game
	 */
	private String gameState;
	/**
	 * A list of String that are the name of GameStates
	 */
	private List<String> gamestates;
	/**
	 * A list of hooks that a gamemode is using
	 */
	private List<String> hookNames = new ArrayList<>();
	
	public GameInfo(Plugin plugin, String uuid) {
		pluginName = plugin.getName();
		this.uuid = uuid;
	}
	
	public GameInfo(String pluginName, String uuid) {
		this.pluginName = pluginName;
		this.uuid = uuid;
	}
	
	
	//GETTERS AND SETTERS
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getPlayernames() {
		return playernames;
	}
	public void setPlayernames(List<String> playernames) {
		this.playernames = playernames;
	}
	public String getGameState() {
		return gameState;
	}
	public void setGameState(String gameState) {
		this.gameState = gameState;
	}
	public List<String> getGamestates() {
		return gamestates;
	}
	public void setGamestates(List<String> gamestates) {
		this.gamestates = gamestates;
	}
	public List<String> getHookNames() {
		return hookNames;
	}
	public void setHookNames(List<String> hookNames) {
		this.hookNames = hookNames;
	}
	public String getPluginName() {
		return pluginName;
	}
	
	

}
