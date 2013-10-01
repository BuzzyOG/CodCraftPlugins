package com.codcraft.walls;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.CodCraft.api.CCAPI;

public class WS extends JavaPlugin implements Listener {
	
	public CCAPI api;
	public List<Strip> Strips = new ArrayList<>();
	//private Location[][] locs = new Location[51][12];
 	public Map<String, Letter> letters = new HashMap<String, Letter>();
	@SuppressWarnings("unused")
	private boolean locked;
	
	public void onEnable() {
		final Plugin ccapi = Bukkit.getPluginManager().getPlugin("CodCraftAPI");
		if(ccapi == null) {
			Bukkit.getPluginManager().disablePlugin(this);
		}
		api =(CCAPI) ccapi;
		api.registerModule(WallFrame.class, new WallFrame(api, this, 65, 19));
		Location loc1 = new Location(Bukkit.getWorld("world"), -454, 225, 492);
		Location loc2 = new Location(Bukkit.getWorld("world"), -454, 207, 428);
		final Board board = new Board(this, 65, 19, loc1, loc2);
		/*int x1 = 0;
		for(int x = loc1.getBlockX(); x >= loc2.getBlockX(); x--) {
			int y1 = 0;
			for(int y = loc1.getBlockY(); y >= loc2.getBlockY(); y--) {
				System.out.println(y + " " + y1);
				locs[x1][y1] = new Location(Bukkit.getWorld("world"), x, y, 96);
				y1++;
			}
			x1++;
		}*/
		getCommand("write").setExecutor(new CommandExecutor() {
			
			@Override
			public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
				if(sender instanceof Player) {
					if(label.equalsIgnoreCase("write")) {
						String color = args[0];
						if(color == null) {
							color = "0";
						}
						WallFrame wf = api.getModuleForClass(WallFrame.class);
						MaterialType[][] pixels = new MaterialType[65][19];
						for(int x = 0; x < 65; x++) {
							for(int y = 0; y < 19; y++) {

							pixels[x][y] = new MaterialType(Material.WOOL, (byte) Integer.parseInt(color));
							}
						}
						Bukkit.broadcastMessage("TEST"+pixels);
						wf.setPixels(pixels, board);
						
					}
				}
				return true;
			}
		});
		//getServer().getPluginManager().registerEvents(new WSListener(this), this);
		//LoadConfig();
		//LetterT();
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



	/*protected boolean setPixels(MaterialType[][] pixels) {
		if(!locked) {
			locked = true;
			for(int b = 0; b < locs.length; b++) {
				for(int c = 0; c < pixels[b].length; c++) {
					
					System.out.println("Location: " + b + "  " + c);
					System.out.println("Length: " + locs[b].length);
					System.out.println("Null?: " + locs[b][c]);
					locs[b][c].getBlock().setType(pixels[b][c].mat);
					locs[b][c].getBlock().setData(pixels[b][c].data);	
				}
			}
			locked = false;
			return true;
		}

		return true;
	}*/

}
