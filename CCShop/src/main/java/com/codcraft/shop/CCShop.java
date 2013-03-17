package com.codcraft.shop;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import com.CodCraft.api.CCAPI;
import com.CodCraft.api.services.CCGamePlugin;

public class CCShop extends CCGamePlugin {
	public Map<String, Integer> prices = new HashMap<String, Integer>();
	public CCAPI api;

	public void onEnable() {
		final Plugin ccapi = Bukkit.getPluginManager().getPlugin("CodCraftAPI");
		if(ccapi == null) {
			getLogger().log(Level.WARNING, "ccapi not found disabling");
			Bukkit.getPluginManager().disablePlugin(this);
		}
		api = (CCAPI) ccapi;
		getServer().getPluginManager().registerEvents(new CCShopListener(this), this);
	}
	
	@Override
	public String getTag() {
		return "[CCShop]";
	}

	@Override
	public void makegame(String name) {
		// TODO Auto-generated method stub
		
	}

}
