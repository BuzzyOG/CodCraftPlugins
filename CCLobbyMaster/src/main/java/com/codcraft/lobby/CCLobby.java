package com.codcraft.lobby;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.CodCraft.api.CCAPI;
import com.CodCraft.api.modules.GUI;
import com.CodCraft.api.modules.GameManager;
import com.codcraft.lobby.ping.PingManager;


public class CCLobby extends JavaPlugin {
		
	public List<Module> configmap = new ArrayList<>();
	public PingManager pingManager;
	public CCAPI api;
	public Chat chat;
	public Permission permi;

	
	public void onDisable() {
	}
	
	public void onEnable() {
		
		//Load Config
		LoadConfig();
		
		checkplugins();
		
		//Always Day
		AlwaysDay();
		
		//register events
		
		final Plugin api = this.getServer().getPluginManager().getPlugin("CodCraftAPI");
	    if(api != null || !(api instanceof CCAPI)) {
	    	this.api = (CCAPI) api;
	    }
		
		getServer().getPluginManager().registerEvents(new LobbyListener(this), this);
		getServer().getPluginManager().registerEvents(new LobbyMaker(this), this);
		getCommand("maker").setExecutor(new makercommand());
		getLogger().info(getCommand("maker").getPermission());
		PluginCommand command = getCommand("send");
		command.setExecutor(new SendCommand(this));
		getLogger().info(command.getPermission());
		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new MessageListener(this));
		this.pingManager = new PingManager(this);
		pingManager.setUp(configmap);
		
		startTimer();
		Timer();


		setupPermissions();
		setupChat();
		
	}
    private boolean setupPermissions()
    {
        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) {
        	permi = permissionProvider.getProvider();
        }
        return (permi != null);
    }

    private boolean setupChat()
    {
        RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
        if (chatProvider != null) {
            chat = chatProvider.getProvider();
        }

        return (chat != null);
    }
    
    
	private void Timer() {
		Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
			
			@Override
			public void run() {
				for(Player p : Bukkit.getOnlinePlayers()) {
					guiUpdate();
					Location loc1 = new Location(Bukkit.getWorld("world"), -584, 20, 450);
					Location loc2 = new Location(Bukkit.getWorld("world"), -347, 500, 203);
					if(!isInside(p, loc1, loc2)) {
						p.teleport(new Location(Bukkit.getWorld("world"), -465, 52, 327, (float) 180, (float) 0.6));
					}
				}
				
			}
		}, 0, 20);
		
	}
	
	public void guiUpdate() {
		GameManager gm = api.getModuleForClass(GameManager.class);
		GUI gui = api.getModuleForClass(GUI.class);
		TreeMap<String, String> sorted = new TreeMap<>();
		int i = 0;
		for(Player p : Bukkit.getOnlinePlayers()) {
			i++;
			if(gm.getGameWithPlayer(p) == null) {
				String prefix = chat.getGroupPrefix(Bukkit.getWorld("world"), permi.getPrimaryGroup(p));
				prefix = prefix.substring(0, 2);
				prefix = ChatColor.translateAlternateColorCodes('&', prefix);
				sorted.put(""+i, prefix+p.getName());
			}
		}
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(gm.getGameWithPlayer(p) == null) {
				
				gui.updateplayerlist(p, sorted);
			}

		}
		
	}

	private void startTimer() {
		Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
			
			@Override
			public void run() {
				pingManager.pingServers();
				updateSigns();
			}
		}, 0, 600);
		
	}
	
	private boolean isInside(Player p, Location loc1, Location loc2) {
		Location loc = p.getLocation();
		double minX;
		double maxX;
		double maxZ;
		double minZ;
		double maxY;
		double minY;
		if(loc1.getX() > loc2.getX()) {
			maxX = loc1.getX();
			minX = loc2.getX();
		} else {
			maxX = loc2.getX();
			minX = loc1.getX();
		}
		if(loc1.getY() > loc2.getY()) {
			maxY = loc1.getY();
			minY = loc2.getY();
		} else {
			maxY = loc2.getY();
			minY = loc1.getY();
		}
		if(loc1.getZ() > loc2.getZ()) {
			maxZ = loc1.getZ();
			minZ = loc2.getZ();
		} else {
			maxZ = loc2.getZ();
			minZ = loc1.getZ();
		}
		
		if(loc.getX() < minX || loc.getX() > maxX)
			   return false;  
		if(loc.getZ() < minZ || loc.getZ() > maxZ)
			   return false;
		if(loc.getY() < minY || loc.getY() > maxY)
			   return false;   
		return true;		
	}

	private void AlwaysDay() {
		Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
			
			@Override
			public void run() {
				World world = Bukkit.getWorld("world");
				if(world != null) {
					world.setTime(14000);
				}	
			}
		}, 0, 200);
		
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
				int port = Gamesconfig.getInt("Games."+Gamestring+".Port");
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
				mapModule.port = port;
				configmap.add(mapModule);
			}
		}
		
	}
	
	public void updateSigns() {
		if(configmap != null) {
	    	for (Module ts : configmap) {
	    		Location l = ts.SignBlock1.getBlock().getLocation();
	    		Block b = l.getBlock();
	    		if (b.getState() instanceof Sign) {
	    			Sign s = (Sign)b.getState();
	    			List<String> res = pingManager.getPingForServer(ts.getServer());
	    			if(res != null) {
	    				if(res.get(4).equalsIgnoreCase("true")) {
		    				s.setLine(0, res.get(0));
		    				s.setLine(1, res.get(3));
		    				s.setLine(2, ChatColor.GREEN + res.get(1) + "/" + res.get(2));
		    				s.setLine(3, ChatColor.GREEN + "Online");
		    				s.update();
	    				} else {
	    					s.setLine(0, res.get(0));
	    					s.setLine(2, ChatColor.RED+ "-/-");
	    					s.setLine(3, ChatColor.RED + "Offline");
	    					s.update();
	    				}
	    			} else {
	    				s.setLine(1, "Please Contact");
	    				s.setLine(2, "Admin");
	    				s.update();
	    			}

	    				/*if (res.isOnline()) {
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
	    					s.setLine(1, ts.getName());
	    					s.setLine(2, ChatColor.RED+ "-/-");
	    					s.setLine(3, ChatColor.RED + "Offline");
	    					s.update();
	    				}*/
	       			
	    		}
	    		Location sign2loc = ts.SignBlock2.getBlock().getLocation();
	    		Block sb = sign2loc.getBlock();
	    		if ((sb.getState() instanceof Sign)) {
	    			
	    		}
	    	}
		}
	}
}
