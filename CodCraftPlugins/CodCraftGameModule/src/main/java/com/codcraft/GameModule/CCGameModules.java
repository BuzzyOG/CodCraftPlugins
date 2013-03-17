package com.codcraft.GameModule;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import com.CodCraft.api.CCAPI;
import com.CodCraft.api.services.CCGamePlugin;

public class CCGameModules extends CCGamePlugin {
	public CCAPI api;
	public void onEnable() {
		final Plugin ccapi = Bukkit.getPluginManager().getPlugin("CodCraftAPI");
		if(ccapi == null) {
			getServer().getPluginManager().disablePlugin(this);
		}
		api = (CCAPI) ccapi;
		
	}
	@Override
	public String getTag() {
		return null;
	}
	@Override
	public void makegame(String name) {
		
	}


}
