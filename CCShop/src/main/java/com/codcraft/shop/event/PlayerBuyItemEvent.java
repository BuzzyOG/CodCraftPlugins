package com.codcraft.shop.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

import com.CodCraft.api.services.CCEvent;
import com.codcraft.shop.CCShopItem;
/**
 *  PlayerBuyItemEvent
 *  - Caled when a Player trys to buy an item from the Shop
 */
public class PlayerBuyItemEvent extends CCEvent implements Cancellable {
	
	/**
 	* Bollean for Canceled 
 	*/
	private boolean can = false;
	/**
	 * Player who is buying Item 
	 */
	private Player player;
	/**
	 * The Shop item being bought.
	 */
	private CCShopItem item;
  	/**
    	* Defult Construstor
    	* 
    	* @param player
    	* @param item
    	* 	
    	*/
	public PlayerBuyItemEvent(Player player, CCShopItem item) {
		this.player = player;
		this.item = item;
	}
	
	/**
    	* Retrieve the ShopItem
    	* 
    	* @return The Shop Item being bought by the player. 
    	*         Can be null if config is wrong. 
    	*/
	public CCShopItem getItem() {
		return item;
	}
	/**
	 * Sets the Shop Item
	 * 
	 * @param item 
	 * 	- The Item to be set. 
	 */
	public void setItem(CCShopItem item) {
		this.item = item;
	}
	/**
	 * Gets the player buying item.
	 * 
	 * @return Player
	 */
	public Player getPlayer() {
		return player;
	}
	/**
	 * Sets player who bought item. 
	 * 
	 * @param player 
	 * 
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}
	//Handlers.
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
