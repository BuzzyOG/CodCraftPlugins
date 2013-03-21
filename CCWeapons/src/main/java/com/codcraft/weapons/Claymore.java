package com.codcraft.weapons;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class Claymore implements Listener {
	
	private Map<Location, Face> claymore = new HashMap<Location, Claymore.Face>();
	//@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Bukkit.broadcastMessage("3");
		for(Entry<Location, Face> entry : claymore.entrySet()) {
			ClaymoreDetect(entry.getKey(), entry.getValue(), e.getPlayer());
		}
	}
	//@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		if(e.getBlockPlaced().getType() == Material.LEVER) {
			claymore.put(e.getBlockPlaced().getLocation(), Face.NORTH);
			Bukkit.broadcastMessage("2");
		}
	}
	
	
	
	private boolean ClaymoreDetect(Location claymore, Face f, Player p) {
		int x = claymore.getBlockX();
		int y = claymore.getBlockY();
		int z = claymore.getBlockZ();
		int x1 = p.getLocation().getBlockX();
		int y1 = p.getLocation().getBlockX();
		int z1 = p.getLocation().getBlockZ();
		if(f == Face.NORTH) {
			Bukkit.broadcastMessage("Claymore: "+z+ " Player: "+z1);
			if(z <= z1 && z <= z1 - 3)  {
				Bukkit.broadcastMessage("4");
				if(x1 >= (x + 2) || x1 <= (x - 2) || y1 >= (y + 2) || y1 <= (y - 2)  ) {
				}	
			}
		}
		return false;
	}
	
	

	private enum Face {
		
		NORTH,
		EAST,
		SOUTH,
		WEST,
	}
	
}
