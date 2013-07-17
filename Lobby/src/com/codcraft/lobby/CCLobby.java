package com.codcraft.lobby;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.CodCraft.api.CCAPI;

public class CCLobby extends JavaPlugin {
	
	public CCAPI CCAPI;
	public Map<String, Lobby> configmap = new HashMap<String, Lobby>();
	public LobbySign sign;
	
	
	public void onEnable() {
		
		final Plugin CCAPI = this.getServer().getPluginManager().getPlugin("CodCraftAPI");
		if(CCAPI == null) {
			this.getServer().getPluginManager().disablePlugin(this);
		} else {
			this.CCAPI = (CCAPI)  CCAPI;
		}
		this.CCAPI.registerModule(LobbyModule.class, new LobbyModule(this.CCAPI, this));
		
		//Load Config
		LoadConfig();
		
		
		//register events
		getServer().getPluginManager().registerEvents(new LobbyListener(this), this);
		this.sign = new LobbySign(this);
		sign.UpdateSigns();
	}


	private void LoadConfig() {
		File Games = new File(getDataFolder(), "games.yml");
		if(!Games.exists()) {
			Games.mkdir();
		}
		YamlConfiguration Gamesconfig = YamlConfiguration.loadConfiguration(Games);
		if(Gamesconfig.getConfigurationSection("Games") == null) {
			Gamesconfig.createSection("Games");
			getLogger().info("Please Modify Config and Reload");
		} else {
			World LobbyWorld = Bukkit.getWorld(Gamesconfig.getString("LobbyWorld"));
			for(String Gamestring : Gamesconfig.getConfigurationSection("Games").getKeys(false)) {
				String name = Gamesconfig.getString("Games."+Gamestring+".Name");
				String game = Gamesconfig.getString("Games."+Gamestring+".Game");
				Location locblock1 = new Location(LobbyWorld, 
						Double.parseDouble(Gamesconfig.getString("Games."+Gamestring+".Block1.x")), 
						Double.parseDouble(Gamesconfig.getString("Games."+Gamestring+".Block1.y")), 
						Double.parseDouble(Gamesconfig.getString("Games."+Gamestring+".Block1.z")));
				Location locblock2 = new Location(LobbyWorld, 
						Double.parseDouble(Gamesconfig.getString("Games."+Gamestring+".Block2.x")), 
						Double.parseDouble(Gamesconfig.getString("Games."+Gamestring+".Block2.y")), 
						Double.parseDouble(Gamesconfig.getString("Games."+Gamestring+".Block2.z")));
				Location SignBlock = new Location(LobbyWorld, 
						Double.parseDouble(Gamesconfig.getString("Games."+Gamestring+".SignBlock.x")), 
						Double.parseDouble(Gamesconfig.getString("Games."+Gamestring+".SignBlock.y")), 
						Double.parseDouble(Gamesconfig.getString("Games."+Gamestring+".SignBlock.z")));	
				Location lampblock = new Location(LobbyWorld, 
						Double.parseDouble(Gamesconfig.getString("Games."+Gamestring+".LampBlock.x")), 
						Double.parseDouble(Gamesconfig.getString("Games."+Gamestring+".LampBlock.y")), 
						Double.parseDouble(Gamesconfig.getString("Games."+Gamestring+".LampBlock.z")));	
				Lobby mapLobby = new Lobby() {
				};
				mapLobby.setGame(game);
				mapLobby.setBlock1(locblock1);
				mapLobby.setBlock2(locblock2);
				mapLobby.setName(name);
				mapLobby.setSignBlock(SignBlock);
				mapLobby.setLampblock(lampblock);
				configmap.put(name, mapLobby);
			}
		}
		
	}

	

}
