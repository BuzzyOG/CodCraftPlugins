package com.codcraft.walls;

import org.bukkit.Bukkit;
import org.bukkit.Location;



public class Board {
	
	private WS plugin;
	private Location[][] pixels;
	
	
	public Board(WS plugin, int x, int y, Location loc, Location lo) {
		this.plugin = plugin;
		 pixels = new Location[x][y];
		 setUpPixels(loc, lo);
		 
	}
	
	private boolean setUpPixels(Location loc1, Location loc2) {
		
		int x1 = 0;
		for(int x = loc1.getBlockZ(); x >= loc2.getBlockZ(); x--) {
			int y1 = 0;
			for(int y = loc1.getBlockY(); y >= loc2.getBlockY(); y--) {
				System.out.println(y + " " + y1);
				pixels[x1][y1] = new Location(Bukkit.getWorld("world"), x, y, 96);
				y1++;
			}
			x1++;
		}
		return true;
	}
	
	public boolean renderBoard(MaterialType[][] pixels) {
		System.out.println("Rendering Board");
		for(int x = 0; x < pixels.length; x++) {
			for(int y = 0; y < pixels[x].length; y++) {
				System.out.println("Old: " + this.pixels[x][y].getBlock().getType() + " Data: " + this.pixels[x][y].getBlock().getData() );
				this.pixels[x][y].getBlock().setType(pixels[x][y].mat);
				this.pixels[x][y].getBlock().setData(pixels[x][y].data, true);
				System.out.println("New: " + this.pixels[x][y].getBlock().getType() + " Data: " + this.pixels[x][y].getBlock().getData() );
			}
		}
		return true;
		
	}
	
	public void sendToBoard(String s) {
		for(int i = 0; i < s.length(); i++) {
			Strip strip = plugin.Strips.get(i);
			
		}
	}


}
