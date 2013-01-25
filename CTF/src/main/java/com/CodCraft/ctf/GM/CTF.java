package com.CodCraft.ctf.GM;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;

import com.CodCraft.ctf.MapLoader;


public class CTF {
	public static HashMap<Location, Block> BlockLocations = new HashMap<Location, Block>();
	public static HashMap<String, Boolean> WhoHasFlag = new HashMap<String, Boolean>();
	public static HashMap<Item, Integer> pitem = new HashMap<Item, Integer>();
	public static HashMap<Integer, Integer> Flagcaps = new HashMap<Integer, Integer>();
	public CTF() {
		init();
	}
	private void init() {
		SpawnFlags();
		PutFlags();
	}
	private void PutFlags() {
		for(Entry<World, ArrayList<Location>> e : MapLoader.ctflocations.entrySet()) {
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
		Location loc7 = new Location(loc.getWorld(), loc.getX() -1, loc.getY(), loc.getZ() -1);
		Block block7 = loc7.getBlock();
		BlockLocations.put(loc7, block7);
		Location loc8 = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ() - 1);
		Block block8 = loc8.getBlock();
		BlockLocations.put(loc8, block8);
		Location loc9 = new Location(loc.getWorld(), loc.getX() + 1, loc.getY(), loc.getZ() -1);
		Block block9 = loc9.getBlock();
		BlockLocations.put(loc9, block9);
		Location loc10 = new Location(loc.getWorld(), loc.getX(), loc.getY() + 2, loc.getZ());
		Block block10 = loc10.getBlock();
		BlockLocations.put(loc10, block10);
		
		block1.setType(Material.WOOD);
		block2.setType(Material.WOOD);
		block3.setType(Material.WOOD);
		block4.setType(Material.WOOD);
		block5.setType(Material.WOOD);
		block6.setType(Material.WOOD);
		block7.setType(Material.WOOD);
		block8.setType(Material.WOOD);
		block9.setType(Material.WOOD);
		block10.setType(Material.FENCE);
		
	}
	public static void respawnblocks() {
		for (Entry<Location, Block> e : BlockLocations.entrySet()) {
			e.getKey().getBlock().setType(e.getValue().getType());
		}
	}
	private void SpawnFlags() {
		WhoHasFlag.put("Spawn1", true);
		WhoHasFlag.put("Spawn2", true);
	}


}
