package com.codcraft.shop;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.CodCraft.api.event.PlayerJoinGameEvent;
import com.CodCraft.api.model.Game;
import com.CodCraft.api.modules.GameManager;
import com.codcraft.codcraftplayer.CCPlayer;
import com.codcraft.codcraftplayer.CCPlayerModule;
import com.codcraft.shop.event.PlayerBuyItemEvent;

public class CCShopListener implements Listener {

	private CCShop plugin;
	
	public CCShopListener(CCShop plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onGameEnter(PlayerJoinGameEvent e) {
		if(e.getGame().getPlugin() == plugin) {
			Player p = Bukkit.getPlayer(e.getPlayer().getName());
			Game<CCShop> game = (Game<CCShop>) e.getGame();
			
			game.getTeams().get(0).addPlayer(p);
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_BLOCK) {
			CCPlayerModule ccpm = plugin.api.getModuleForClass(CCPlayerModule.class);
			CCPlayer ccp = ccpm.getPlayer(e.getPlayer());
			
				if(e.getClickedBlock().getType() == Material.WALL_SIGN || e.getClickedBlock().getType() == Material.SIGN || e.getClickedBlock().getType() == Material.SIGN_POST){
					Sign sign = (Sign) e.getClickedBlock().getState();
					if(sign.getLine(0).equalsIgnoreCase("[CCShop]")) {
						for(CCShopItem item : plugin.items) {
							if(item.getName().equalsIgnoreCase(sign.getLine(1))) {
								if(!e.getPlayer().hasPermission(item.getPermisison())) {
									if(ccp.getPoints() >= item.getPrice()) {
										PlayerBuyItemEvent event = new PlayerBuyItemEvent(e.getPlayer(), item);
										if(!event.isCancelled()) {
											ccp = ccpm.getPlayer(event.getPlayer());
											ccp.setPoints(ccp.getPoints() - event.getItem().getPrice());
											plugin.getLogger().info(event.getPlayer().getName() +" has bought " + event.getItem().getName());
											event.getPlayer().sendMessage(event.getItem().getBoughtmessage());
										}else {
											event.getPlayer().sendMessage(event.getItem().getDenyboughtmessage());
										}
									} else {
										e.getPlayer().sendMessage("You dont have enough money for this!");
									}
								} else {
									e.getPlayer().sendMessage("You already have this!");
								}
							}
						}
					}
				
			}
		}
	}
	

}
