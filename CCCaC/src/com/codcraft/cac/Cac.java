package com.codcraft.cac;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.CodCraft.api.CCAPI;

public class Cac extends JavaPlugin {

	public CCAPI api;
	public CaCLocations locations;
	public Map<String, String> weapons = new HashMap<>();
	public List<String> Attachement = new ArrayList<>();
	public List<String> Perk1 = new ArrayList<>();
	public List<String> Perk2 = new ArrayList<>();
	public List<String> Perk3 = new ArrayList<>();
	public List<String> Equipment = new ArrayList<>();
	public List<String> KillStreak = new ArrayList<>();

	
	public void onEnable() {
		final Plugin ccapi = Bukkit.getPluginManager().getPlugin("CodCraftAPI");
		if(ccapi == null) {
			getServer().getPluginManager().disablePlugin(this);
		}
		api = (CCAPI) ccapi;
		Bukkit.createWorld(new WorldCreator("CreateAClass"));
		locations = new CaCLocations();
		getCommand("cac").setExecutor(new CaCcommands(this));
		api.registerModule(CaCModule.class, new CaCModule(this, api));
		getServer().getPluginManager().registerEvents(new CaCListener(this), this);

	}
}
