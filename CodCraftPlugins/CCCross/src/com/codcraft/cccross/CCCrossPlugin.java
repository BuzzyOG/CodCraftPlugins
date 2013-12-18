package com.codcraft.cccross;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.CodCraft.api.CCAPI;

public class CCCrossPlugin extends JavaPlugin {
	
	private CCAPI api;
	
	public void onEnable() {
		Plugin plug = Bukkit.getPluginManager().getPlugin("CodCraftAPI");
		if(plug != null) {
			api = (CCAPI) plug;
		}
		api.registerModule(Cross.class, new Cross(this, api));
	}

}
