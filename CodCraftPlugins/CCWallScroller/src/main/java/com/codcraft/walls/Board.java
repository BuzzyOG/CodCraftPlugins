package com.codcraft.walls;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import com.google.common.base.Splitter;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class Board {
	private WS plugin;
	public Board(WS plugin) {
		this.plugin = plugin;
	}
	
	public void sendToBoard(String s) {
		for(int i = 0; i < plugin.Strips.size(); i++) {
			Strip strip = plugin.Strips.get(i);
		}
	}


}
