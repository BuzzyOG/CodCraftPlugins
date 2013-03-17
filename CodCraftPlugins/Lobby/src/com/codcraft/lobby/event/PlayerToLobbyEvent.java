package com.codcraft.lobby.event;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

import com.CodCraft.api.model.TeamPlayer;
import com.CodCraft.api.services.CCEvent;

public class PlayerToLobbyEvent extends CCEvent implements Cancellable {
	private boolean canceled;
	private Player player;
	public PlayerToLobbyEvent(Player p) {
		this.player = p;
	}

	
	public PlayerToLobbyEvent(TeamPlayer player2) {
		this.player = Bukkit.getPlayer(player2.getName());
	}


	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	private static final HandlerList handlers = new HandlerList();
	
	
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
	public void setCancelled(boolean b) {
		this.canceled = b;
	}

}
