package com.codcraft.diamondrun;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import com.CodCraft.api.CCAPI;
import com.CodCraft.api.modules.GameManager;
import com.CodCraft.api.services.CCGamePlugin;
import com.codcraft.diamondrun.commands.DRCommand;

public class DiamondRun extends CCGamePlugin {
	
	public CCAPI api;
	
	public Map<String, Location> spawns = new HashMap<String, Location>();

	public void onEnable() {
		Plugin api = Bukkit.getPluginManager().getPlugin("CodCraftAPI");
		if(api != null) {
			this.api = (CCAPI) api;
		}
		getServer().getPluginManager().registerEvents(new GameListener(this), this);
		getCommand("DR").setExecutor(new DRCommand());
		loadspawns();
	}

	private void loadspawns() {
		File f = new File("./plugins/DiamondRun/config.yml");
		YamlConfiguration file = YamlConfiguration.loadConfiguration(f);
		for(String maps : file.getConfigurationSection("maps").getKeys(false)) {
			Double xint = convert(file.getString("maps."+maps+".x"));
			Double yint = convert(file.getString("maps."+maps+".y"));
			Double zint = convert(file.getString("maps."+maps+".z"));
			Float yawfloat = convertfloat(file.getString("maps."+maps+".yaw"));
			Float pitchfloat = convertfloat(file.getString("maps."+maps+".pitch"));
			System.out.println(xint);
			System.out.println(yint);
			System.out.println(zint);
			System.out.println(yawfloat);
			System.out.println(pitchfloat);
			Location loc = new Location(Bukkit.getWorld("world"), xint, yint, zint, yawfloat, pitchfloat);
			spawns.put(maps, loc);
		}
		
	}

	private Float convertfloat(String yaw) {
		try {
			return Float.parseFloat(yaw);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	private Double convert(String x) {
		try {
			return Double.parseDouble(x);
		} catch (NumberFormatException e) {
			return null;
		}
		
	}

	@Override
	public String getTag() {
		return "[DiamondRun]";
	}

	@Override
	public void makegame(String name) {
		DRGame game = new DRGame(this);
		game.setName(name);
		api.getModuleForClass(GameManager.class).registerGame(game);
	}

}
