package com.codcraft.chaos;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import com.CodCraft.api.CCAPI;
import com.CodCraft.api.services.CCGamePlugin;

public class Chaous extends CCGamePlugin {
	public CCAPI api;
	public void onEnable() {
		final Plugin ccapi = Bukkit.getPluginManager().getPlugin("CodCraftAPI");
		if(ccapi == null) {
			this.getServer().getPluginManager().disablePlugin(this);
		} else {
			api = (CCAPI) ccapi;
		}
		
	}

	@Override
	public String getTag() {
		return "[Chaous]";
	}

	@Override
	public void makeGame(String[] name) {
		// TODO Auto-generated method stub
		
	}

}
