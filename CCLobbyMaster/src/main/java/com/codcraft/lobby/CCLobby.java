package com.codcraft.lobby;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.codcraft.lobby.ping.Ping;
import com.codcraft.lobby.ping.Result;
import com.codcraft.lobby.ping.Update;


public class CCLobby extends JavaPlugin {
		
	public List<Module> configmap = new ArrayList<>();

	public void onEnable() {
		
		
		//Load Config
		LoadConfig();
		
		checkplugins();
		
		//register events
		
		getServer().getPluginManager().registerEvents(new LobbyListener(this), this);
		getServer().getPluginManager().registerEvents(new LobbyMaker(this), this);
		getCommand("maker").setExecutor(new makercommand());
		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		Ping.getInstance().loadConfig2();
		Ping.getInstance().startPing();
		updateSigns();
		getServer().getScheduler().runTaskTimer(this, new Update(this), 20, 20);

	}

	private void checkplugins() {

		
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
				String game = Gamesconfig.getString("Games."+Gamestring+".Server");
				String IP = Gamesconfig.getString("Games."+Gamestring+".IP");
				Location locblock1 = new Location(LobbyWorld, 
						Double.parseDouble(Gamesconfig.getString("Games."+Gamestring+".Block1.x")), 
						Double.parseDouble(Gamesconfig.getString("Games."+Gamestring+".Block1.y")), 
						Double.parseDouble(Gamesconfig.getString("Games."+Gamestring+".Block1.z")));
				Location locblock2 = new Location(LobbyWorld, 
						Double.parseDouble(Gamesconfig.getString("Games."+Gamestring+".Block2.x")), 
						Double.parseDouble(Gamesconfig.getString("Games."+Gamestring+".Block2.y")), 
						Double.parseDouble(Gamesconfig.getString("Games."+Gamestring+".Block2.z")));
				Location SignBlock = new Location(LobbyWorld, 
						Double.parseDouble(Gamesconfig.getString("Games."+Gamestring+".sign1.x")), 
						Double.parseDouble(Gamesconfig.getString("Games."+Gamestring+".sign1.y")), 
						Double.parseDouble(Gamesconfig.getString("Games."+Gamestring+".sign1.z")));	
				Location SignBlock2 = new Location(LobbyWorld, 
						Double.parseDouble(Gamesconfig.getString("Games."+Gamestring+".sign2.x")), 
						Double.parseDouble(Gamesconfig.getString("Games."+Gamestring+".sign2.y")), 
						Double.parseDouble(Gamesconfig.getString("Games."+Gamestring+".sign2.z")));	
				Module mapModule = new Module() {
				};
				mapModule.IP = IP;
				mapModule.setServer(game);
				mapModule.setBlock1(locblock1);
				mapModule.setBlock2(locblock2);
				mapModule.setName(name);
				mapModule.setSignBlock1(SignBlock);
				mapModule.setSignBlock2(SignBlock2);
				configmap.add(mapModule);
			}
		}
		
	}
	
	public void updateSigns() {
		if(configmap != null) {
	    	for (Module ts : configmap) {
	    		Location l = ts.SignBlock1.getBlock().getLocation();
	    		Block b = l.getBlock();
	    		if ((b.getState() instanceof Sign)) {
	    			Sign s = (Sign)b.getState();
	    			Result res = Ping.getInstance().results.get(ts.getServer());
	    			if(res != null) {
	    				if (res.isOnline()) {
	    					
	    					String npl = String.valueOf(res.getPlayersOnline());
	    					String mpl = String.valueOf(res.getMaxPlayers());
	    					String motd = res.getMotd();
	    					s.setLine(0, ts.server);
	    					s.setLine(1, motd);
	    					s.setLine(2, ChatColor.GREEN + npl + "/" + mpl);
	    					s.setLine(3, ChatColor.GREEN+"Online");
	    					s.update();
	    				} else {
	    					s.setLine(0, ts.server);
	    					s.setLine(1, (String)Ping.getInstance().display.get(ts.getServer()));
	    					s.setLine(2, ChatColor.RED+ "-/-");
	    					s.setLine(3, ChatColor.RED + "Offline");
	    					s.update();
	    				}
	       			}
	    		}
	    	}
		}
	}
}
