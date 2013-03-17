package com.codcraft.lobby.event;

import java.util.Map.Entry;

import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

import com.CodCraft.api.model.TeamPlayer;
import com.CodCraft.api.services.CCEvent;
import com.codcraft.lobby.Lobby;

public class PlayerEnterLobbyEvent extends CCEvent implements Cancellable {

	private boolean canceled = false;
	private TeamPlayer player;
	private Entry<String, Lobby> lobby;
	public PlayerEnterLobbyEvent(TeamPlayer p, Entry<String, Lobby> lobby) {
		this.player = p;
		this.lobby = lobby;
	}
	
	private static final HandlerList handlers = new HandlerList();

	public TeamPlayer getPlayer() {
		return player;
	}

	public void setPlayer(TeamPlayer player) {
		this.player = player;
	}

	public Entry<String, Lobby> getLobby() {
		return lobby;
	}

	public void setLobby(Entry<String, Lobby> lobby) {
		this.lobby = lobby;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
	   
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	@Override
	public boolean isCancelled() {
		return canceled;
	}

	@Override
	public void setCancelled(boolean arg0) {
		this.canceled = arg0;
		
	}
	
	

}
