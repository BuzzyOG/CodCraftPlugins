package com.codcraft.ccstartgame;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.codcraft.ccommands.CCCommands;

public class CCStartGame extends JavaPlugin {
	private CCCommands ccommands;
	public void onEnable(){
		final Plugin cccommands = Bukkit.getPluginManager().getPlugin("CCCommands");
		if(cccommands == null) {
			getServer().getPluginManager().disablePlugin(this);
		}
		this.ccommands = (CCCommands) cccommands;
		LoadMaps();
	}

	private void LoadMaps() {
		if(!getDataFolder().exists()) {
			getDataFolder().mkdir();
		}
		YamlConfiguration config = new YamlConfiguration().loadConfiguration(new File(getDataFolder(), "config.yml"));
		for(String s : config.getConfigurationSection("games").getKeys(false)) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "a startgame "+ config.getString("games."+s+".game") +" " + s);
		}
		
	}
	
}
