package com.codcraft.lobby;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.codcraft.ccommands.PlayerDoSpawnEvent;
import com.codcraft.lobby.event.PlayerToLobbyEvent;

public class Listener2 implements Listener {

	private CCLobby plugin;

	public Listener2(CCLobby plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onDisbatch(PlayerDoSpawnEvent e) {
		PlayerToLobbyEvent event = new PlayerToLobbyEvent(e.getPlayer());
		Bukkit.getPluginManager().callEvent(event);
		if(e.getGame() == null) {
			return;
		}
	    LobbyModule lm = (LobbyModule)this.plugin.CCAPI.getModuleForClass(LobbyModule.class);
	    lm.UpdateSign(lm.getLobby(e.getGame().getName()));
	}

}
