package com.codcraft.codcraftplayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.CodCraft.api.CCAPI;

public class CCPlayerMain extends JavaPlugin {
	public Connection con;
	public Map<String, CCPlayer> players = new HashMap<String, CCPlayer>();
	public CCAPI api;
	public CCdatabase getCCDatabase;

	public void onEnable() {
		getCCDatabase = new CCdatabase(this);
		final CCAPI ccapi = (CCAPI) Bukkit.getPluginManager().getPlugin("CodCraftAPI");
		if(ccapi == null) {
			getLogger().log(Level.WARNING, "API NOT FOUND");
		} else {
			this.api = ccapi;
			api.registerModule(CCPlayerModule.class, new CCPlayerModule(this, api));
		}
		
		getServer().getPluginManager().registerEvents(new CCPlayerLister(this), this);
		connect();
		for(Player p : Bukkit.getOnlinePlayers()) {
			getCCDatabase.getp(p.getName());
		}
		getCommand("CCAP").setExecutor(new CCAPCommand(this));

	}
	
	public boolean connect() {
		con = null;
		try {
			con = DriverManager.getConnection("jdbc:mysql://" + "localhost/" + "minecraft", "root", "YDsD9Cz3");
			getLogger().info("Got connection!");
		} catch (SQLException e) {
			getLogger().log(Level.WARNING, "Connection not found");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public void onDisable() {
		api.deregisterModuleForClass(CCPlayerModule.class);
		for(Player p : Bukkit.getOnlinePlayers()) {
			getCCDatabase.savep(p);
		}
		
	}
}
