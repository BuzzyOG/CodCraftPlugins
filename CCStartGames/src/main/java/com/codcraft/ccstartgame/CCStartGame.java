package com.codcraft.ccstartgame;
import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
public class CCStartGame extends JavaPlugin {
	public void onEnable(){
		if(!getDataFolder().exists()) getDataFolder().mkdir();	
		new YamlConfiguration();
		YamlConfiguration config = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "config.yml"));
		for(String s : config.getConfigurationSection("games").getKeys(false)) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "a startgame "+ config.getString("games."+s+".game") +" " + s);
		}
	}
}
