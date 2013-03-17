package com.codcraft.bunggie;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;

import com.google.common.eventbus.Subscribe;




public class Main extends Plugin implements Listener {
	

	public void onEnable() {
		BungeeCord.getInstance().pluginManager.registerListener(this, new com.codcraft.bunggie.Listener(this));
	}
	
	
	@Subscribe
	public void postloginevent(PostLoginEvent e) {
		for(ProxiedPlayer pp: BungeeCord.getInstance().getPlayers()){
			pp.sendMessage(ChatColor.YELLOW + e.getPlayer().getName() + "has join CodCraftNetowrk");
		}
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
			p.sendMessage(ChatColor.YELLOW + "" + e.getPlayer() + "has left CodCraftNetork!");
		}
		
	}

}
