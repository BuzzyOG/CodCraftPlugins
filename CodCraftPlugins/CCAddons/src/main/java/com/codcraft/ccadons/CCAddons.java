package com.codcraft.ccadons;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.plugin.java.JavaPlugin;


public class CCAddons extends JavaPlugin {
	
	public final Map<String, CCAddonPlayer> players = new HashMap<String, CCAddonPlayer>();
	
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(new CCAddonListener(this), this);
	}

}
