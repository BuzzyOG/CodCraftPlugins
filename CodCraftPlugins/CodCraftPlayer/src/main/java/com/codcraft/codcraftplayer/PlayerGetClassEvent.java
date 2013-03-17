package com.codcraft.codcraftplayer;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import com.CodCraft.api.model.Game;
import com.CodCraft.api.services.CCEvent;
import com.google.common.collect.ImmutableList;

public class PlayerGetClassEvent extends CCEvent {
	
	private Player player;
	
	private Game<?> game;
	
	private List<ItemStack> items = new ArrayList<>();
	
	public PlayerGetClassEvent(Player p, Game<?> g) {
		this.player = p;
		this.game = g;
	}
	
	public List<ItemStack> getItems() {
		return ImmutableList.copyOf(items);
	}
	
	public void addItem(ItemStack item) {
		items.add(item);
	}
	
	public void removeItem(ItemStack item) {
		items.remove(item);
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



	private static final HandlerList handlers = new HandlerList();

	   public static HandlerList getHandlerList() {
	      return handlers;
	   }
	   
	   @Override
	   public HandlerList getHandlers() {
	      return handlers;
	   }

}
