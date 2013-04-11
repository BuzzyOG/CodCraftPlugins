package com.codcraft.shop.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

import com.CodCraft.api.services.CCEvent;
import com.codcraft.shop.CCShopItem;

public class PlayerBuyItemEvent extends CCEvent implements Cancellable {

	private boolean can = false;
	
	private Player player;
	
	private CCShopItem item;
	
	public PlayerBuyItemEvent(Player player, CCShopItem item) {
		this.player = player;
		this.item = item;
	}
	

	public CCShopItem getItem() {
		return item;
	}
	public void setItem(CCShopItem item) {
		this.item = item;
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
		return can;
	}

	@Override
	public void setCancelled(boolean can) {
		this.can = can;
	}

}
