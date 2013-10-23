package com.codcraft.codcraft;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.CodCraft.api.CCAPI;
import com.codcraft.codcraft.commands.VoteCommand;

public class CodCraftPlugin extends JavaPlugin {
	
	public CCAPI api;

	public void onEnable() {
		
		final Plugin api = this.getServer().getPluginManager().getPlugin("CodCraftAPI");
	    if(api != null || !(api instanceof CCAPI)) {
	    	this.api = (CCAPI) api;
	    } else {
	    	// Disable if we cannot get the api.
	        getLogger().warning("Could not find API. Disabling...");
	        getServer().getPluginManager().disablePlugin(this);
	        return;
	    }
	    getServer().getPluginManager().registerEvents(new CodCraftListener(this), this);
	    getCommand("vote").setExecutor(new VoteCommand(this));
	}


}
