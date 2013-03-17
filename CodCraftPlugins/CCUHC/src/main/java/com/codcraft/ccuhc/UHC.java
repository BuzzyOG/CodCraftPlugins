package com.codcraft.ccuhc;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import com.CodCraft.api.CCAPI;
import com.CodCraft.api.model.Game;
import com.CodCraft.api.modules.GameManager;
import com.CodCraft.api.services.CCGamePlugin;

public class UHC extends CCGamePlugin {
	
	public CCAPI api;
	public UHCGame game;
	public boolean running = false;
	public int i = 0;
	
	
	public void onEnable() {
		final Plugin pluginapi = Bukkit.getPluginManager().getPlugin("CodCraftAPI");
		if(pluginapi == null) {
			getServer().getPluginManager().disablePlugin(this);
		} else {
			api = (CCAPI) pluginapi;
		}
		
		GameManager gm = api.getModuleForClass(GameManager.class);
		Game<?> game = new UHCGame(this);
		game.setName("UHC1");
		gm.registerGame(game);
		game = new UHCGame(this);
	}
	
	public void makegame(String name) {
		GameManager gm = api.getModuleForClass(GameManager.class);
		Game<?> game = new UHCGame(this);
		game.setName(name);
		gm.registerGame(game);
	}
	@Override
	public String getTag() {
		return "[UHC]";
	}

}
