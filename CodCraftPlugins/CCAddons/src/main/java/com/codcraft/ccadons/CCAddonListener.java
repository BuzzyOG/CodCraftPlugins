package com.codcraft.ccadons;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.CodCraft.api.event.RequestJoinGameEvent;

public class CCAddonListener implements Listener {

	private CCAddons plugin;
	
	public CCAddonListener(CCAddons plugin) {
		this.plugin = plugin;
	}
	@EventHandler
	public void onGameEnter(RequestJoinGameEvent e) {
		Player p = Bukkit.getPlayer(e.getPlayer().getName());
		if(e.getGame().getName().equalsIgnoreCase("BetaGame")) {
			if(!plugin.players.containsKey(p.getName())) {
				plugin.players.put(p.getName(), new CCAddonPlayer());
			}
			CCAddonPlayer ap = plugin.players.get(p.getName());
			if(!ap.isBetaGame()) {
				e.setCancelled(true);
			}
		}
	}

}
