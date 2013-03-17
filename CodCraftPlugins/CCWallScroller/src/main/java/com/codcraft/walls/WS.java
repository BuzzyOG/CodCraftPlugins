package com.codcraft.walls;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.CodCraft.api.CCAPI;

public class WS extends JavaPlugin implements Listener {
	
	public CCAPI api;
	public List<Strip> Strips = new ArrayList<>();
	public Map<String, Letter> letters = new HashMap<String, Letter>();
	
	public void onEnable() {
		final Plugin ccapi = Bukkit.getPluginManager().getPlugin("CodCraftAPI");
		if(ccapi == null) {
			Bukkit.getPluginManager().disablePlugin(this);
		}
		api =(CCAPI) ccapi;
		//getServer().getPluginManager().registerEvents(new WSListener(this), this);
		LoadConfig();
		LetterT();
	}

	
	
	private void LetterT() {
		Letter t = new Letter();
		LStrip left = t.left;
		left.m1 = 0;
		LStrip leftmiddle = t.leftmiddle;
		leftmiddle.m1 = 0;
		LStrip middle = t.middle;
		middle.m1 = 0;
		middle.m2 = 0;
		middle.m3 = 0;
		middle.m4 = 0;
		middle.m5 = 0;
		middle.m6 = 0;
		middle.m7 = 0;
		middle.m8 = 0;
		middle.m9 = 0;
		middle.m10 = 0;
		middle.m11 = 0;
		middle.m12 = 0;
		LStrip right = t.right;
		right.m1 = 0;
		LStrip tightmiddle = t.rightmiddle;
		tightmiddle.m1 = 0;
		t.left = left;
		t.leftmiddle = leftmiddle;
		t.middle = middle;
		t.rightmiddle = tightmiddle;
		t.right = right;
		letters.put("t", t);
	}



	public void LoadConfig(){
	     File spawns = new File("./plugins/WS/config.yml");
	     YamlConfiguration config = YamlConfiguration.loadConfiguration(spawns);
	     for(String strip : config.getConfigurationSection("Strips").getKeys(false)) {
	    	 Strip stri = new Strip();
	    	 Location loc1 = new Location(Bukkit.getWorld("world"), Double.parseDouble(config.getString("Strips."+strip+".Location1.x")),
	    			 Double.parseDouble(config.getString("Strips."+strip+".Location1.y")), Double.parseDouble(config.getString("Strips."+strip+".Location1.z")));
	    	 stri.Location1 = loc1;
	    	 Location loc2 = new Location(Bukkit.getWorld("world"), Double.parseDouble(config.getString("Strips."+strip+".Location2.x")),
	    			 Double.parseDouble(config.getString("Strips."+strip+".Location2.y")), Double.parseDouble(config.getString("Strips."+strip+".Location2.z")));
	    	 stri.Location1 = loc2;
	    	 Location loc3 = new Location(Bukkit.getWorld("world"), Double.parseDouble(config.getString("Strips."+strip+".Location3.x")),
	    			 Double.parseDouble(config.getString("Strips."+strip+".Location3.y")), Double.parseDouble(config.getString("Strips."+strip+".Location3.z")));
	    	 stri.Location1 = loc3;
	    	 Location loc4 = new Location(Bukkit.getWorld("world"), Double.parseDouble(config.getString("Strips."+strip+".Location4.x")),
	    			 Double.parseDouble(config.getString("Strips."+strip+".Location4.y")), Double.parseDouble(config.getString("Strips."+strip+".Location4.z")));
	    	 stri.Location1 = loc4;
	    	 Location loc5 = new Location(Bukkit.getWorld("world"), Double.parseDouble(config.getString("Strips."+strip+".Location5.x")),
	    			 Double.parseDouble(config.getString("Strips."+strip+".Location5.y")), Double.parseDouble(config.getString("Strips."+strip+".Location5.z")));
	    	 stri.Location1 = loc5;
	    	 Location loc6 = new Location(Bukkit.getWorld("world"), Double.parseDouble(config.getString("Strips."+strip+".Location6.x")),
	    			 Double.parseDouble(config.getString("Strips."+strip+".Location6.y")), Double.parseDouble(config.getString("Strips."+strip+".Location6.z")));
	    	 stri.Location1 = loc6;
	    	 Location loc7 = new Location(Bukkit.getWorld("world"), Double.parseDouble(config.getString("Strips."+strip+".Location7.x")),
	    			 Double.parseDouble(config.getString("Strips."+strip+".Location7.y")), Double.parseDouble(config.getString("Strips."+strip+".Location7.z")));
	    	 stri.Location1 = loc7;
	    	 Location loc8 = new Location(Bukkit.getWorld("world"), Double.parseDouble(config.getString("Strips."+strip+".Location8.x")),
	    			 Double.parseDouble(config.getString("Strips."+strip+".Location8.y")), Double.parseDouble(config.getString("Strips."+strip+".Location8.z")));
	    	 stri.Location1 = loc8;
	    	 Location loc9 = new Location(Bukkit.getWorld("world"), Double.parseDouble(config.getString("Strips."+strip+".Location9.x")),
	    			 Double.parseDouble(config.getString("Strips."+strip+".Location9.y")), Double.parseDouble(config.getString("Strips."+strip+".Location9.z")));
	    	 stri.Location1 = loc9;
	    	 Location loc10 = new Location(Bukkit.getWorld("world"), Double.parseDouble(config.getString("Strips."+strip+".Location10.x")),
	    			 Double.parseDouble(config.getString("Strips."+strip+".Location10.y")), Double.parseDouble(config.getString("Strips."+strip+".Location10.z")));
	    	 stri.Location1 = loc10;
	    	 Location loc11 = new Location(Bukkit.getWorld("world"), Double.parseDouble(config.getString("Strips."+strip+".Location11.x")),
	    			 Double.parseDouble(config.getString("Strips."+strip+".Location11.y")), Double.parseDouble(config.getString("Strips."+strip+".Location11.z")));
	    	 stri.Location1 = loc11;
	    	 Location loc12 = new Location(Bukkit.getWorld("world"), Double.parseDouble(config.getString("Strips."+strip+".Location12.x")),
	    			 Double.parseDouble(config.getString("Strips."+strip+".Location12.y")), Double.parseDouble(config.getString("Strips."+strip+".Location12.z")));
	    	 stri.Location1 = loc12;
	    	 Strips.add(stri);
	     }
	}

}
