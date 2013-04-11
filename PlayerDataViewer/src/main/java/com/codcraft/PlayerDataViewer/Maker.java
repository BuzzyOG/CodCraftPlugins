package com.codcraft.PlayerDataViewer;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class Maker implements Listener {
	
	//private Main plugin;

	public Maker(Main plugin) {
		//this.plugin = plugin;
	}
	
	private static String segmant = null; 
	
	private Location loc1 = null;
	
	private Location loc2 = null;
	
	@EventHandler
	public void onRightClick(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(e.getPlayer().getItemInHand().getType() == Material.WOOD_SWORD) {
				if(loc1 == null) {
					loc1 = e.getClickedBlock().getLocation();
					e.getPlayer().sendMessage("Block 1 clicked");
				} else if(loc1 != null && loc2 == null) {
					loc2 = e.getClickedBlock().getLocation();
					e.getPlayer().sendMessage("Block 2 clicked");
					e.getPlayer().sendMessage("Now trying to save");
					if(Save()){
						
					}
				}
			}
		}
	}

	private boolean Save() {
		new YamlConfiguration();
		if(Maker.segmant != null) {
			
		}
		
		
		
		return false;
	}
	

}
