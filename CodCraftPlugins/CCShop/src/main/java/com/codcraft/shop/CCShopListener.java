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
			Game<?> game = plugin.api.getModuleForClass(GameManager.class).getGameWithPlayer(e.getPlayer());
			if(game.getPlugin() == plugin) {
				if(e.getClickedBlock().getType() == Material.WALL_SIGN || e.getClickedBlock().getType() == Material.SIGN || e.getClickedBlock().getType() == Material.SIGN_POST){
					Sign sign = (Sign) e.getClickedBlock().getState();
					if(sign.getLine(0).equalsIgnoreCase("[CCShop]")) {
						if(plugin.prices.containsKey(sign.getLine(1))) {
							
						}
					}
				}
			}
		}
	}
	

}
