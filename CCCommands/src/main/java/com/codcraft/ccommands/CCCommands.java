package com.codcraft.ccommands;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.CodCraft.api.CCAPI;
import com.CodCraft.api.modules.InventoryManager;
import com.codcraft.cchat.AntiAd;
import com.codcraft.ccinventory.Teleports;

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
	    getServer().getPluginManager().registerEvents(new DisguiseCommand(this), this); 
	    YamlConfiguration config = YamlConfiguration.loadConfiguration(new File("./plugins/CCCommands/ads.yml"));
		double x = config.getDouble("x");
		double y = config.getDouble("y");
		double z = config.getDouble("z");
		System.out.println("X: " + x + " Y: " + y + " Z: " + z);
		getCommand("lobby").setExecutor(new SpawnCommand(this, x, y, z));
		getCommand("a").setExecutor(new AdminCommand(this));
		getCommand("gameinfo").setExecutor(new GameInfoCommand(this));
		getCommand("CSP").setExecutor(new CSPCommand(this));
		getCommand("class").setExecutor(new ClassCommand(this));
		getCommand("setwarp").setExecutor(new SetWarpCommand(this)); // Don't forget to add these to plugin.yml! :D
		getCommand("delwarp").setExecutor(new SetWarpCommand(this));
		getCommand("remwarp").setExecutor(new SetWarpCommand(this));
		getCommand("w").setExecutor(new SetWarpCommand(this));
		invSetup();
		
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
	
	  public void invSetup(){
	  Inventory inv = Bukkit.createInventory(null, 9, "§bTELEPORT MENU");
	  api.getModuleForClass(InventoryManager.class).addToInv(Material.WOOL, "§b§lHUB", 0, "§9TP to the hub", 7, inv, false, "§bTELEPORT MENU");
	  api.getModuleForClass(InventoryManager.class).addToInv(Material.BOW, "§b§lCODCRAFT", 1, "§9TP to codcraft", 1, inv, false, "§bTELEPORT MENU");
	  api.getModuleForClass(InventoryManager.class).addToInv(Material.ICE, "§b§lFREEZE-TAG", 2, "§9TP to freeze-tag", 1, inv, false, "§bTELEPORT MENU");
	  api.getModuleForClass(InventoryManager.class).addToInv(Material.FEATHER, "§b§lBATTLEROOM", 3, "§9TP to battleroom", 1, inv, false, "§bTELEPORT MENU");
	  api.getModuleForClass(InventoryManager.class).addToInv(Material.FLINT, "§b§l<<---", 8, "§9§oPrevious Menu", 1, inv, true, "§bTELEPORT MENU");
	  
	  Teleports.itemList.add(Material.WOOL);
	  Teleports.itemList.add(Material.BOW);
	  Teleports.itemList.add(Material.FEATHER);
	  Teleports.itemList.add(Material.ICE);
	  Teleports.itemList.add(Material.FLINT);
	  }
	
	
}
