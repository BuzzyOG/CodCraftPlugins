package com.CodCraft.sad.GM;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import com.CodCraft.sad.MapLoader;

public class SAD {
	public HashMap<Location, Block> BlockLocations = new HashMap<Location, Block>(); 
	public static Map<World, Location> bomb = new HashMap<>();
	public static Map<World, String> bombholder = new HashMap<>();
	public static HashMap<Integer, HashMap<String, Integer>> Planters = new HashMap<>();
	public static HashMap<Integer, HashMap<String, Integer>> Defuseers = new HashMap<>();
	public static Location bombdroped;
	public static boolean Bomb1 = false;
	public static boolean Bomb2 = false;
	public static Integer Bomb1Timer = 0;
	public static Integer Bomb2Timer = 0;
	public SAD() {
		init();
		Planters.put(1, new HashMap<String, Integer>());
		Planters.put(2, new HashMap<String, Integer>());
		Defuseers.put(1, new HashMap<String, Integer>());
		Defuseers.put(2, new HashMap<String, Integer>());
	}
	private void init() {
		SpawnFlags();
		PutBombs();
	}
	private void SpawnFlags() {
		for(World w : MapLoader.Maps) {
			bombholder.put(w, "none");
		}
	}
	private void PutBombs() {
		for(Entry<World, ArrayList<Location>> e : MapLoader.Blocations.entrySet()) {
			for(Location loc : e.getValue()) {
				flagblocks(loc);
			}
		}
	}
	private void flagblocks(Location loc) {
		Block block5 = loc.getBlock();
		Location loc5 = loc;
		BlockLocations.put(loc5, block5);
		Location loc1 = new Location(loc.getWorld(), loc.getX() -1, loc.getY(), loc.getZ() +1);
		Block block1 = loc1.getBlock();
		BlockLocations.put(loc1, block1);
		Location loc2 = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ() + 1);
		Block block2 = loc2.getBlock();
		BlockLocations.put(loc2, block2);
		Location loc3 = new Location(loc.getWorld(), loc.getX() + 1, loc.getY(), loc.getZ() +1);
		Block block3 = loc3.getBlock();
		BlockLocations.put(loc3, block3);
		Location loc4 = new Location(loc.getWorld(), loc.getX() - 1, loc.getY(), loc.getZ());
		Block block4 = loc4.getBlock();
		BlockLocations.put(loc4, block4);
		Location loc6 = new Location(loc.getWorld(), loc.getX() +1, loc.getY(), loc.getZ());
		Block block6 = loc4.getBlock();
		BlockLocations.put(loc6, block6);
		
		block1.setType(Material.NETHER_BRICK);
		block2.setType(Material.NETHER_BRICK);
		block3.setType(Material.NETHER_BRICK);
		block4.setType(Material.NETHER_BRICK);
		block5.setType(Material.NETHER_BRICK);
		block6.setType(Material.NETHER_BRICK);
		for(World w : MapLoader.Maps) {
			SAD.bomb.get(w).getBlock().setType(Material.STONE_BUTTON);
		}
	}
	

}
