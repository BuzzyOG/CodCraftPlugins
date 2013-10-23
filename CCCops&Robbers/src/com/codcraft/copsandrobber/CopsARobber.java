package com.codcraft.copsandrobber;

import org.bukkit.Location;

import com.CodCraft.api.CCAPI;
import com.CodCraft.api.modules.GameManager;
import com.CodCraft.api.services.CCGamePlugin;

public class CopsARobber extends CCGamePlugin {
	/**
	 * CodCraft API instance
	 */
	public CCAPI api;
	
	/**
	 * Default location of the lobby.
	 */
	public Location lobbyspawn;
	
	/**
	 * The enable method. 
	 * 			- Sets the API instance
	 * 			- Register the listener
	 * 			- Set the default lobby spawn
	 */
	public void onEnable() {
		this.api = (CCAPI) getServer().getPluginManager().getPlugin("CodCraftAPI");
		getServer().getPluginManager().registerEvents(new CarListener(this), this);
		lobbyspawn = new Location(getServer().getWorld("world"), -2, 3, 0);
	}
	
	/**
	 * The tag for Cops And Robbers
	 */
	@Override
	public String getTag() {
		return "C&R";
	}

	
	/**
	 * Method to make the game.
	 */
	@Override
	public void makegame(String[] args) {
		CARGame game = new CARGame(this);
		game.setName(args[0]);
		api.getModuleForClass(GameManager.class).registerGame(game);
	}

}
