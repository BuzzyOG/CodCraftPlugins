package com.codcraft.ccssbb;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.ChatColor;

import com.CodCraft.api.CCAPI;
import com.CodCraft.api.modules.GameManager;
import com.CodCraft.api.services.CCGamePlugin;

public class CCSSBB extends CCGamePlugin {

	public CCAPI api;
	public Map<String, ArrayList<Location>> spawnpoints = new HashMap<>();
	public Map<String, ArrayList<Location>> pregamespots = new HashMap<>();
	public Map<String, Location> specspot = new HashMap<>();
	public void onEnable() {
		final Plugin ccapi = Bukkit.getPluginManager().getPlugin("CodCraftAPI");
		if(ccapi == null) {
			getLogger().info("CCAPI not found!");
			getServer().getPluginManager().disablePlugin(this);
		} 
		api = (CCAPI) ccapi;
		loadmap();
		specspot();
		Bukkit.getPluginManager().registerEvents(new SSBlistener(this), this);
	}
	
	private void specspot() {
		specspot.put("Netherfortress", new Location(Bukkit.getWorld("world"), 1071, 88, 0));
		specspot.put("Village", new Location(Bukkit.getWorld("world"), 2088, 85, -2));
		specspot.put("MushroomIsland", new Location(Bukkit.getWorld("world"), 3063, 83, -5));
		specspot.put("Stronghold", new Location(Bukkit.getWorld("world"), 4060, 85, -1));
		specspot.put("TheEnd", new Location(Bukkit.getWorld("world"), 5071, 93, -2));
		specspot.put("Towerdefence", new Location(Bukkit.getWorld("world"), 6087, 90, -3));
		
	}

	@Override
	public String getTag() {
		return "[CCSSBB]";
	}
	
	public void makegame(String[] name) {
		SSBB game = new SSBB(this);
		game.setName(name[0]);
		api.getModuleForClass(GameManager.class).registerGame(game);
	}
	private void loadmap() {
	      File spawns = new File("./plugins/SSB/config.yml");
	      YamlConfiguration config = YamlConfiguration.loadConfiguration(spawns);
	      for(String s : config.getConfigurationSection("maps").getKeys(false)) {
	    	  spawnpoints.put(s, new ArrayList<Location>());
	    	  for(String location : config.getConfigurationSection("maps."+s).getKeys(false)) {
	    		  Location loc = new Location(Bukkit.getWorld("world"), Double.parseDouble(config.getString("maps."+s+"."+location+".x")),
	                        Double.parseDouble(config.getString("maps."+s+"."+location+".y")), Double.parseDouble(config	
	                              .getString("maps."+s+"."+location+".z")));
	    		  spawnpoints.get(s).add(loc);
	    	  }
	    	  
	      }
	      for(String s : config.getConfigurationSection("lobby").getKeys(false)) {
	    	  pregamespots.put(s, new ArrayList<Location>());
	    	  for(String location : config.getConfigurationSection("lobby."+s).getKeys(false)) {
	    		  Location loc = new Location(Bukkit.getWorld("world"), Double.parseDouble(config.getString("lobby."+s+"."+location+".x")),
	                        Double.parseDouble(config.getString("lobby."+s+"."+location+".y")), Double.parseDouble(config	
	                              .getString("lobby."+s+"."+location+".z")));
	    		  pregamespots.get(s).add(loc);
	    	  }
	    	  
	      }
	}
	public static ItemStack Skull(SkullType type) {
	    return new ItemStack(Material.SKULL_ITEM, 1, (short)type.ordinal());
	  }

	  public static String format(String text, String[] replacement) {
	    String output = text;
	    for (int i = 0; i < replacement.length; i++) {
	      output = output.replace("%" + (i + 1) + "%", replacement[i]);
	    }
	    return ChatColor.translateAlternateColorCodes('&', output);
	  }
	  
	  
	  public static ItemStack Skull(CustomSkullType type) {
		    return Skull(type.getOwner(), type.getDisplayName());
		  }



	  public static ItemStack Skull(String skullOwner, String displayName)
	  {
	    ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short)SkullType.PLAYER.ordinal());
	    SkullMeta skullMeta = (SkullMeta)skull.getItemMeta();
	    skullMeta.setOwner(skullOwner);
	    if (displayName != null) {
	      skullMeta.setDisplayName(ChatColor.RESET + displayName);
	    }
	    skull.setItemMeta(skullMeta);
	    return skull;
	  }

}
