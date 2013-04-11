package com.codcraft.ccommands;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.CodCraft.api.CCAPI;

public class CCCommands extends JavaPlugin {

	public CCAPI api;


	public void onEnable() {
		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		final Plugin api = this.getServer().getPluginManager().getPlugin("CodCraftAPI");
	      if(api != null || !(api instanceof CCAPI)) {
	         this.api = (CCAPI) api;
	      }
	    getServer().getPluginManager().registerEvents(new CCListener(this), this);  
		getCommand("spawn").setExecutor(new SpawnCommand(this));
		getCommand("a").setExecutor(new AdminCommand(this));

	      
	}
	
	
}
