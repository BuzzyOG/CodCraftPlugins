package com.codcraft.bunggie;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;

import com.google.common.eventbus.Subscribe;

public class Listener implements net.md_5.bungee.api.plugin.Listener {

	@SuppressWarnings("unused")
	private Main plugin;

	public Listener(Main plugin) {
		this.plugin = plugin;
	}
	
	@Subscribe
	public void onJoin(LoginEvent e) {
		if(e.isCancelled()) {
			e.setCancelReason(ChatColor.RED+"Updating the Lobby. Please reconnect in a few.");
		}
	}
	
	
	@Subscribe
	public void onLeave(PlayerDisconnectEvent e) {
		
		for(ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
			p.sendMessage(ChatColor.YELLOW + "" + e.getPlayer().getName() + "has left CodCraftNetork!");
		}
		
	}

}
