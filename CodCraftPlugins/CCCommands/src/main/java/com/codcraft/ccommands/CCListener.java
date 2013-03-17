package com.codcraft.ccommands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.CodCraft.api.modules.GameManager;



public class CCListener implements Listener {
	private CCCommands plugin;
	public CCListener(CCCommands plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onrepsawn(PlayerRespawnEvent e) {
		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		if(gm.getGameWithPlayer(e.getPlayer()) == null) {
			e.getPlayer().teleport(new Location(Bukkit.getWorld("world"), -102, 138, 60));
		}
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		GameManager gm = plugin.api.getModuleForClass(GameManager.class);
		if(gm.getGameWithPlayer(e.getPlayer()) == null) {
			e.getPlayer().teleport(new Location(Bukkit.getWorld("world"), -102, 138, 60));
		}
	}
}
