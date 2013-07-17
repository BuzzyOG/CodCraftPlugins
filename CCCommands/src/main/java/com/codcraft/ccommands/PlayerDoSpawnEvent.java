package com.codcraft.ccommands;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.services.CCEvent;

public class PlayerDoSpawnEvent extends CCEvent {
	
	
	private static final HandlerList handlers = new HandlerList();
	private Player player;
	private Game<?> game;

	   public PlayerDoSpawnEvent(Player p, Game<?> g) {
		this.player = p;
		this.game = g;
	}

	public Game<?> getGame() {
		return game;
	}

	public void setGame(Game<?> game) {
		this.game = game;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public static HandlerList getHandlerList() {
	      return handlers;
	   }

	   @Override
	   public HandlerList getHandlers() {
	      return handlers;
	   }

}
