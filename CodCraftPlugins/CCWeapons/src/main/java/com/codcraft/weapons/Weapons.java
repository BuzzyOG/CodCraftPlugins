package com.codcraft.weapons;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.CodCraft.api.CCAPI;
import com.codcraft.cac.Cac;


public class Weapons extends JavaPlugin {
	
	public CCAPI api;
	public Cac cac;
	public List<String> reloaders = new ArrayList<>();

	public void onEnable() {
		final Plugin ccapi = Bukkit.getPluginManager().getPlugin("CodCraftAPI");
		if(ccapi == null) {
			getServer().getPluginManager().disablePlugin(this);
		}
		api = (CCAPI) ccapi;
		final Plugin cccac = Bukkit.getPluginManager().getPlugin("Cac");
		if(cccac == null) {
			getServer().getPluginManager().disablePlugin(this);
		}
		cac = (Cac) cccac;
		getServer().getPluginManager().registerEvents(new SniperWeapon(this), this);
		getServer().getPluginManager().registerEvents(new SMGWeapon(this), this);
		getServer().getPluginManager().registerEvents(new LMGWeapon(this), this);
		getServer().getPluginManager().registerEvents(new ShotGunWeapon(this), this);
		getServer().getPluginManager().registerEvents(new RoitSheildWeapon(this), this);
	}
	
	
	

}
