package com.CodCraft.hardpoint.GM;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import com.CodCraft.api.modules.GameManager;
import com.CodCraft.hardpoint.CodCraft;
import com.CodCraft.hardpoint.MapLoader;


public class Hardpoint extends CodCraft {
	public HashMap<World, HashMap<Integer, ArrayList<Location>>> hardpointspots = new HashMap<World, HashMap<Integer, ArrayList<Location>>>();
	public Integer currentspot = 0;
	public List<Location> currentlocation = new ArrayList<>();
	public boolean contested;
	public boolean team1;
	public boolean team2;
	private com.CodCraft.api.CCAPI api;
	private GameManager gm;
	
	public Hardpoint() {
		gm = api.getModuleForClass(GameManager.class);
		Init();
	}

	private void Init() {
		getHardPointLocation();
		
	}

	private void getHardPointLocation() {
		for(World w : MapLoader.Maps) {
			hardpointspots.put(w, new HashMap<Integer, ArrayList<Location>>());
			
			File worldyml = new File("./CodCraft/hardpoint/"+w.getName()+".yml");
			YamlConfiguration world = YamlConfiguration.loadConfiguration(worldyml);
			
			for(String s : world.getConfigurationSection("Hardpoints").getKeys(false)) {
				if(isInt(s)) {
					int i = Integer.parseInt(s);
					hardpointspots.get(w).put(i, new ArrayList<Location>());
					for(String Location : world.getConfigurationSection("Hardpoints."+s).getKeys(false)) {
						Location loc = new Location(w, Double.parseDouble(world.getString("Hardpoints."+s+"."+Location+".x")), Double.parseDouble(world.getString("Hardpoints."+s+"."+Location+".y")), Double.parseDouble(world.getString("Hardpoints."+s+"."+Location+".z")));
						hardpointspots.get(w).get(i).add(loc);
						
					}
				}
			}
			System.out.println(w.getName()+ hardpointspots.get(w));
		}
	}
	public boolean isInt(String i) {
		try {
			Integer.parseInt(i);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	public boolean nextLocation() {
		Random rnd = new Random();
		int i = rnd.nextInt(hardpointspots.get(gm.GetCurrentWorld()).size());
		ArrayList<Location> temp = hardpointspots.get(gm.GetCurrentWorld()).get(i);
		while (temp == hardpointspots.get(gm.GetCurrentWorld()).get(i)) {
			int i2 = rnd.nextInt(hardpointspots.get(gm.GetCurrentWorld()).size());
			temp = hardpointspots.get(gm.GetCurrentWorld()).get(i2);
		}
		currentlocation = temp;
		return true;
		
	}

	public void firstLocation() {
		Random rnd = new Random();
		int i = rnd.nextInt(hardpointspots.get(gm.GetCurrentWorld()).size());
		ArrayList<Location> temp = hardpointspots.get(gm.GetCurrentWorld()).get(i);
		currentlocation = temp;
		currentspot = i;
	}

}
