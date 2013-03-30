package com.codcraft.codcraftplayer;


import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class CCPlayerLister implements Listener {
	
	private CCPlayerMain plugin;

	public CCPlayerLister(CCPlayerMain plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onJoin(PlayerJoinEvent e) {
		plugin.getCCDatabase.getp(e.getPlayer().getName());
	}
	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		plugin.getCCDatabase.savep(e.getPlayer());
		plugin.players.remove(e.getPlayer().getName());
	}

}