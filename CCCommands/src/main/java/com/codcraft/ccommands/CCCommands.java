package com.codcraft.ccommands;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.CodCraft.api.CCAPI;
import com.codcraft.cchat.AntiAd;

public class CCCommands extends JavaPlugin {

	public CCAPI api;
	public Connection con;
	public AntiAd cchat;
	
	


	public void onEnable() {
		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		final Plugin api = this.getServer().getPluginManager().getPlugin("CodCraftAPI");
	      if(api != null || !(api instanceof CCAPI)) {
	         this.api = (CCAPI) api;
	      }
	   final Plugin cchat = this.getServer().getPluginManager().getPlugin("CCChat");
	       if(cchat != null ) {
	    	   this.cchat = (AntiAd) cchat;
		   }
	    getServer().getScheduler().runTaskTimer(this, new Advertisment(), 0, 18000);
	    getServer().getPluginManager().registerEvents(new CCListener(this), this);  
		getCommand("spawn").setExecutor(new SpawnCommand(this));
		getCommand("a").setExecutor(new AdminCommand(this));
		getCommand("gameinfo").setExecutor(new GameInfoCommand(this));
		getCommand("CSP").setExecutor(new CSPCommand(this));
		getCommand("class").setExecutor(new ClassCommand(this)); 
		
		connect();
		Bukkit.getScheduler().runTaskTimer(this, new LeaderBoard(this), 0, 3600);
		
	}
	
	private boolean connect() {
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
	
	
}
