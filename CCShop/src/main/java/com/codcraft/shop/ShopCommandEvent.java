package com.codcraft.shop;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

import com.CodCraft.api.services.CCEvent;

public class ShopCommandEvent extends CCEvent implements Cancellable {
	
	private Player player;
	private CCShopItem item;
	private boolean can = false;

	public ShopCommandEvent(Player p, CCShopItem item) {
		this.player = p;
		this.item = item;
	}
	
	
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}


	public CCShopItem getItem() {
		return item;
	}


	public void setItem(CCShopItem item) {
		this.item = item;
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
		return can ;
	}

	@Override
	public void setCancelled(boolean can) {
		this.can = can;
	}

}
